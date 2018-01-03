package com.huateng.ebank.framework.web.commQuery;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.huateng.common.DataObjectUtils;
import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.process.call._CallGetter;
import com.huateng.commquery.result.ResultParamBean;
import com.huateng.commquery.result.databus.CommonQueryDataBusMng;
import com.huateng.commquery.result.databus.DataBus;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.common.ErrorCode;
import com.huateng.ebank.common.ExceptionUtil;
import com.huateng.ebank.common.GlobalInfo;
import com.huateng.ebank.framework.session.SessionManager;
import com.huateng.exception.AppException;

public abstract class BaseGetter extends _CallGetter {
	private static final Logger log = Logger.getLogger(BaseGetter.class);
	private String databusId = "";

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.commquery.process.call._CallGetter#process(java.util.Map,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ResultParamBean process(Map param, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		try {
			return super.process(param, request, response);
		} catch (CommonException cex) {
			throw new AppException("", cex.getKey(), cex.getMessage(), cex);
		} catch (AppException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, ex);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.commquery.process.call.ICallGetter#preProcess(java.util.Map,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Map preProcess(Map param, HttpServletRequest request)
			throws AppException {
		Map paramMap = super.preProcess(param, request);
		databusId = (String) (param.get("databusId"));
		if (StringUtils.isEmpty(databusId)) {
			databusId = getCommonQueryBean().getAnyValue("databusid");
		}
		init(request);
		return paramMap;
	}

	protected void init(HttpServletRequest request) throws CommonException {
		checkGlobalInfo(request);
		setGlobalInfoToThreadLoacl(request);
	}

	/*
	 * set GlobalInfo to ThreadLocal
	 */
	protected void setGlobalInfoToThreadLoacl(HttpServletRequest request) throws CommonException {
		HttpSession httpSession = request.getSession();
		GlobalInfo globalInfo = (GlobalInfo) httpSession.getAttribute(GlobalInfo.KEY_GLOBAL_INFO);
		if (null != globalInfo) {
			GlobalInfo.setCurrentInstance(globalInfo);
			String sessionId = httpSession.getId();
			globalInfo.setSessionId(sessionId);
		}
	}

	protected GlobalInfo checkGlobalInfo(HttpServletRequest request)
			throws CommonException {
		GlobalInfo globalInfo = GlobalInfo.getFromRequest(request);
		if (null == globalInfo) {
			request.getSession().invalidate();
			ExceptionUtil.throwCommonException("用户没有登录.",
					ErrorCode.ERROR_CODE_TLRNO_SESSION_INVALID);
		}
		GlobalInfo.setCurrentInstance(globalInfo);
		return globalInfo;
	}

	/**
	 * @author yjw 注册 java.utils.date 到beanutils中
	 * @param bean
	 * @param map
	 */
	public void BeanUtilsEx(Object bean, Map map) throws AppException {
		try {
			// BeanUtils.populate(bean, map);
			DataObjectUtils.mapToObject2(bean, map);
		} catch (Exception e) {
			throw new AppException(Module.SYSTEM_MODULE,
					Rescode.DEFAULT_RESCODE, "属性拷贝出错", e);
		}
	}

	public void setValue2DataBus(String fieldId, String fieldValue)
			throws AppException {
		try {
			if (fieldValue == null)
				fieldValue = "";
			HttpSession session = null;
			if (httpReq instanceof HttpServletRequest) {
				session = (httpReq).getSession();
			}
			DataBus dataBus = CommonQueryDataBusMng.getDataBus(session.getId(),
					databusId, session);
			dataBus.setField(fieldId, fieldValue);
		} catch (AppException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new AppException(Module.SYSTEM_MODULE,
					Rescode.DEFAULT_RESCODE, ex);
		}
	}

	public String getValueFromDataBus(String fieldId) throws AppException {
		try {
			HttpSession session = null;
			if (httpReq instanceof HttpServletRequest) {
				session = (httpReq).getSession();
			}
			DataBus dataBus = CommonQueryDataBusMng.getDataBus(session.getId(),
					databusId, session);
			return dataBus.getFieldValue(fieldId);
		} catch (AppException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new AppException(Module.SYSTEM_MODULE,
					Rescode.DEFAULT_RESCODE, ex);
		}
	}

	/**
	 * Retrieve a session object based on the request and the attribute name.
	 */
	protected Object getSessionObject(String attrName) {
		Object object = SessionManager.getInstance().getSessionObject(httpReq, attrName);
		if (object != null && !object.equals("")) {
			removeSessionObject(attrName);
			return object;
		} else {
			return null;
		}
	}

	/**
	 * Set a session object based on the request and the attribute name.
	 */
	protected boolean setSessionObject(String attrName, Object value) {
		return SessionManager.getInstance().setSessionObject(httpReq, attrName, value);
	}

	/**
	 * Remove a session object based on the request and the attribute name.
	 */
	protected boolean removeSessionObject(String attrName) {
		return SessionManager.getInstance().removeSessionObject(httpReq, attrName);
	}
	
	@Override
	public ResultParamBean postProcess() throws AppException {
		ResultParamBean rpb = super.postProcess();
		String isExport = getCommQueryServletRequest().getParameter("export");
		if ("1".equals(isExport)) {
			setSessionObject("print_dataset", result.getContent());
		}
		return rpb;
	}
}
