package com.huateng.commquery.config;

import com.huateng.common.Constants;
import com.huateng.common.SystemDProperty;
import com.huateng.common.log.LoggerConstants;
import com.huateng.commquery.cfieldmodel.FieldModelParser;
import com.huateng.commquery.config.bean.base.ICommonQueryBean;
import com.huateng.commquery.config.bean.base.include.ICommonQueryIncludeBean;
import com.huateng.commquery.config.bean.base.meta.ICommonQueryConfigMeta;
import com.huateng.commquery.config.bean.base.meta.ICommonQueryFMMeta;
import com.huateng.commquery.config.bean.base.meta.ICommonQueryIncludeMeta;
import com.huateng.commquery.config.bean.meta.CommonQueryConfigMeta;
import com.huateng.commquery.config.bean.meta.CommonQueryFMMeta;
import com.huateng.commquery.config.bean.meta.CommonQueryIncludeMeta;
import com.huateng.commquery.config.bean.meta.CommonQueryMetaBean;
import com.huateng.commquery.config.parser.CommonQueryIncludeParser;
import com.huateng.commquery.config.parser.CommonQueryParser;
import com.huateng.exception.AppException;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @Override CommonQueryUtil.initConfFile()
 * @author gang.xu
 * @date 2010-12-28
 */
public class CommonQueryUtil implements Serializable {
	private static final long serialVersionUID = -7625170819761667077L;

	private static Logger logger = Logger.getLogger("SystemStartup");

	private static Map configMap = new HashMap(2);

	private static Map includeMap = new HashMap(1);

	private static Map metaBeanMap = new HashMap();

	private static ServletContext servletContext = null;

	private static boolean init = false;

	public static void init(String[] confFiles) throws AppException {
		initMeta(confFiles);

		initIncludeFile();

		initFieldModelFile();

		initConfFile();
	}

	public static void init(String[] confFiles, ServletContext context)
			throws AppException {
		servletContext = context;
		init(confFiles);
		init = true;
	}

	public static ICommonQueryBean getCommonQueryBean(String id)
			throws AppException {
		if (SystemDProperty.isProductionMode()) {
			ICommonQueryBean bean = (ICommonQueryBean) configMap.get(id);
			if (bean == null) {
				throw new AppException("SY", "CQ29", "Common Query Bean id = "
						+ id + "not found!");
			}
			return bean;
		}

		initFieldModelFile();
		return genConfig(id);
	}

	public static ICommonQueryIncludeBean getCommonQueryIncludeBean(String id)
			throws AppException {
		ICommonQueryIncludeBean bean = (ICommonQueryIncludeBean) includeMap
				.get(id);
		if (bean == null) {
			throw new AppException("SY", "CQ95",
					"Common Query Include Bean id = " + id + "not found!");
		}
		return bean;
	}

	private static void initMeta(String[] confFiles) throws AppException {
		int num = confFiles.length;

		String confFilePath = null;
		try {
			SAXReader saxReader = new SAXReader();
			for (int i = 0; i < num; i++) {
				confFilePath = confFiles[i].trim();
				if (logger.isDebugEnabled())
					logger.info(LoggerConstants.traceInfo("Paser Config File:"
							+ confFilePath + " Begin"));
				Document document = saxReader.read(servletContext
						.getResourceAsStream(confFilePath));

				CommonQueryMetaBean commonQueryMetaBean = initMetaBean(document);
				metaBeanMap.put(confFilePath, commonQueryMetaBean);
			}
		} catch (AppException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new AppException("SY", "CQ04", ex);
		}
	}

	private static CommonQueryMetaBean initMetaBean(Document document)
			throws AppException {
		CommonQueryMetaBean commonQueryMetaBean = new CommonQueryMetaBean();
		initFieldModelMetaBean(commonQueryMetaBean, document);
		initIncludeMetaBean(commonQueryMetaBean, document);
		initQueryMetaBean(commonQueryMetaBean, document);
		return commonQueryMetaBean;
	}

	private static void initQueryMetaBean(
			CommonQueryMetaBean commonQueryMetaBean, Document document)
			throws AppException {
		String desc = "";
		List list = document.selectNodes("//META/Query");

		for (int j = 0; j < list.size(); j++) {
			List txnList = new ArrayList(0);
			Element element = (Element) list.get(j);
			List l = element.selectNodes("@id");
			if (l.size() != 1) {
				throw new AppException("SY", "CQ05", "Query.id error");
			}
			Attribute id = (Attribute) l.get(0);
			l = element.selectNodes("@path");
			if (l.size() != 1) {
				throw new AppException("SY", "CQ06", "Query.path error");
			}
			Attribute path = (Attribute) l.get(0);
			l = element.selectNodes("@txn");
			if (l.size() == 1) {
				Attribute txn = (Attribute) l.get(0);
				if (!StringUtils.isEmpty(txn.getValue().trim())) {
					String[] txnAry = txn.getValue().split(",");
					for (int i = 0; i < txnAry.length; i++) {
						txnList.add(txnAry[i]);
					}
				}
			}
			l = element.selectNodes("@desc");
			if (l.size() == 1) {
				desc = ((Attribute) l.get(0)).getValue();
			}
			CommonQueryConfigMeta commonQueryConfigMeta = new CommonQueryConfigMeta();
			commonQueryConfigMeta.setId(id.getValue());
			commonQueryConfigMeta.setPath(path.getValue());
			commonQueryConfigMeta.setDesc(desc);
			commonQueryConfigMeta.setTxn(txnList);
			commonQueryMetaBean.addConfig(commonQueryConfigMeta.getId(),
					commonQueryConfigMeta);
		}
	}

	private static void initIncludeMetaBean(
			CommonQueryMetaBean commonQueryMetaBean, Document document)
			throws AppException {
		List list = document.selectNodes("//META/Include");

		for (int j = 0; j < list.size(); j++) {
			Element element = (Element) list.get(j);
			List l = element.selectNodes("@id");
			if (l.size() != 1) {
				throw new AppException("SY", "CQ76", "Include.id error");
			}
			Attribute id = (Attribute) l.get(0);
			l = element.selectNodes("@path");

			if (l.size() != 1) {
				throw new AppException("SY", "CQ77", "Include.path error");
			}
			Attribute path = (Attribute) l.get(0);
			CommonQueryIncludeMeta commonQueryIncludeMeta = new CommonQueryIncludeMeta();
			commonQueryIncludeMeta.setId(id.getValue());
			commonQueryIncludeMeta.setPath(path.getValue());
			commonQueryMetaBean.addInclude(id.getValue(),
					commonQueryIncludeMeta);
		}
	}

	private static void initFieldModelMetaBean(
			CommonQueryMetaBean commonQueryMetaBean, Document document)
			throws AppException {
		List list = document.selectNodes("//META/FieldModel");

		for (int j = 0; j < list.size(); j++) {
			Element element = (Element) list.get(j);
			List l = element.selectNodes("@id");

			if (l.size() != 1) {
				throw new AppException("SY", "CQC7", "FieldModel.id error");
			}
			Attribute id = (Attribute) l.get(0);
			l = element.selectNodes("@path");

			if (l.size() != 1) {
				throw new AppException("SY", "CQC8", "FieldModel.path error");
			}
			Attribute path = (Attribute) l.get(0);
			CommonQueryFMMeta commonQueryFMMeta = new CommonQueryFMMeta();
			commonQueryFMMeta.setId(id.getValue());
			commonQueryFMMeta.setPath(path.getValue());
			commonQueryMetaBean.addFieldModel(id.getValue(), commonQueryFMMeta);
		}
	}

	public static void initConfFile() throws AppException {
		String path = "";
		try {
			CommonQueryParser parser = new CommonQueryParser();
			Iterator it = metaBeanMap.keySet().iterator();

			while (it.hasNext()) {
				String metaId = (String) it.next();
				CommonQueryMetaBean commonQueryMetaBean = (CommonQueryMetaBean) metaBeanMap
						.get(metaId);
				if (logger.isDebugEnabled()) {
					logger.info(LoggerConstants.traceInfo("Meta Config File:"
							+ metaId + " Begin"));
				}
				Iterator itMeta = commonQueryMetaBean
						.getCommonQueryConfigMetaList().iterator();

				while (itMeta.hasNext()) {
					ICommonQueryConfigMeta commonQueryConfigMeta = (ICommonQueryConfigMeta) itMeta
							.next();
					path = commonQueryConfigMeta.getPath();
					if (logger.isDebugEnabled())
						logger.info(LoggerConstants
								.traceInfo("Paser Config File:" + path
										+ " Begin"));
					ICommonQueryBean bean;
					if (SystemDProperty.isProductionMode()) {
						InputStream ips = servletContext
								.getResourceAsStream(path);
						if (ips == null) {
							logger.error("Common Query Config File :" + path
									+ " no exists");
							throw new AppException("SY", "CQ08",
									"Common Query Config File :" + path
											+ " no exists");
						}
						bean = parser.parse(ips);
					} else {
						File confFile = new File(Constants.rootPath
								+ File.separator + path);
						if (!confFile.exists()) {
							logger.error("Common Query Config File :" + path
									+ " no exists");
							throw new AppException("SY", "CQ08",
									"Common Query Config File :" + path
											+ " no exists");
						}
						bean = parser.parse(confFile);
					}

					bean.setAnyValue("_META_BEAN_PATH_", metaId);
					bean.setId(commonQueryConfigMeta.getId());
					configMap.put(commonQueryConfigMeta.getId(), bean);
				}
			}
		} catch (AppException appEx) {
			// throw appEx;
			throw new AppException("SY", "CQ04", "Init Config file: " + path
					+ " failed!", appEx);
		} catch (Exception ex) {
			throw new AppException("SY", "CQ04", ex);
		}
	}

	public static void initIncludeFile() throws AppException {
		try {
			CommonQueryIncludeParser parser = new CommonQueryIncludeParser();
			Iterator it = metaBeanMap.keySet().iterator();

			while (it.hasNext()) {
				String metaId = (String) it.next();
				CommonQueryMetaBean commonQueryMetaBean = (CommonQueryMetaBean) metaBeanMap
						.get(metaId);
				ICommonQueryIncludeMeta commonQueryIncludeMeta = null;
				Iterator includeIt = commonQueryMetaBean.getIncludeList()
						.iterator();
				while (includeIt.hasNext()) {
					commonQueryIncludeMeta = (ICommonQueryIncludeMeta) includeIt
							.next();
					String id = commonQueryIncludeMeta.getId();
					String path = commonQueryIncludeMeta.getPath();
					if (logger.isDebugEnabled())
						logger.info(LoggerConstants
								.traceInfo("Paser Include File:" + path
										+ " Begin"));
					ICommonQueryIncludeBean bean;
					if (SystemDProperty.isProductionMode()) {
						bean = parser.parse(servletContext
								.getResourceAsStream(path));
					} else {
						File confFile = new File(Constants.rootPath
								+ File.separator + path);
						if (!confFile.exists()) {
							logger.error("Common Query Include File :" + path
									+ " no exists");
							throw new AppException("SY", "CQ93",
									"Common Query Include File :" + path
											+ " no exists");
						}
						bean = parser.parse(confFile);
					}
					includeMap.put(id, bean);
				}
			}
		} catch (AppException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new AppException("SY", "CQ94", ex);
		}
	}

	public static void initFieldModelFile() throws AppException {
		Iterator it = metaBeanMap.keySet().iterator();
		FieldModelParser fieldModelParser = new FieldModelParser();

		while (it.hasNext()) {
			String metaId = (String) it.next();
			CommonQueryMetaBean commonQueryMetaBean = (CommonQueryMetaBean) metaBeanMap
					.get(metaId);
			Iterator fmIt = commonQueryMetaBean.getFieldModelList().iterator();
			ICommonQueryFMMeta commonQueryFMMeta = null;
			while (fmIt.hasNext()) {
				commonQueryFMMeta = (ICommonQueryFMMeta) fmIt.next();
				String id = commonQueryFMMeta.getId();
				String path = commonQueryFMMeta.getPath();
				if (SystemDProperty.isProductionMode()) {
					fieldModelParser.parse(servletContext
							.getResourceAsStream(path));
				} else {
					File confFile = new File(Constants.rootPath
							+ File.separator + path);
					if (!confFile.exists()) {
						logger.error("FieldModel File :" + path + " no exists");
						throw new AppException("SY", "CQC9",
								"FieldModel File :" + path + " no exists");
					}
					fieldModelParser.parse(confFile);
				}
			}
		}
	}

	private static ICommonQueryBean genConfig(String id) throws AppException {
		CommonQueryParser parser = new CommonQueryParser();

		ICommonQueryBean commonQueryBean = (ICommonQueryBean) configMap.get(id);
		if (commonQueryBean == null) {
			throw new AppException("SY", "CQ08", "Common Query Config :" + id
					+ " no exists");
		}
		String metaId = commonQueryBean.getAnyValue("_META_BEAN_PATH_");
		CommonQueryMetaBean commonQueryMetaBean = (CommonQueryMetaBean) metaBeanMap
				.get(metaId);
		String path = commonQueryMetaBean.getCommonQueryConfigMeta(id)
				.getPath();
		File confFile = new File(Constants.rootPath + File.separator + path);
		if (!confFile.exists()) {
			throw new AppException("SY", "CQ08", "Common Query Config File :"
					+ path + " no exists");
		}
		if (logger.isDebugEnabled()) {
			logger.info(LoggerConstants.traceInfo("Paser Config File:"
					+ confFile.getName() + " Begin"));
		}

		ICommonQueryBean bean = parser.parse(confFile);
		bean.setId(id);
		if (logger.isDebugEnabled()) {
			logger.info(LoggerConstants.traceInfo("Paser Config File:"
					+ confFile.getName() + " End"));
		}
		return bean;
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static List getCommonQueryConfigTxn(String cqId) throws AppException {
		ICommonQueryBean commonQueryBean = (ICommonQueryBean) configMap
				.get(cqId);
		String metaId = commonQueryBean.getAnyValue("_META_BEAN_PATH_");
		CommonQueryMetaBean commonQueryMetaBean = (CommonQueryMetaBean) metaBeanMap
				.get(metaId);
		ICommonQueryConfigMeta commonQueryConfigMeta = commonQueryMetaBean
				.getCommonQueryConfigMeta(cqId);
		return commonQueryConfigMeta.getTxn();
	}

	public static void destroy() {
		metaBeanMap.clear();
		configMap.clear();
		includeMap.clear();
		metaBeanMap = null;
		configMap = null;
		includeMap = null;
	}

	public static boolean isInit() {
		return init;
	}

	public static void setInit(boolean pInit) {
		init = pInit;
	}
}
