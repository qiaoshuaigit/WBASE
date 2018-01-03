package com.huateng.stl4j.operation;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.ebank.entity.SystemParam;
import com.huateng.stl4j.common.BaseOperation;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.OperationContext;



public class ParamMngOperation extends BaseOperation {
	private static Logger logger = Logger.getLogger(ParamMngOperation.class);
	public static String ID = ParamMngOperation.class.getName();
	public static String DEL_LIST = "DEL_LIST";
	public static String INS_LIST = "INS_LIST";
	public static String UPD_LIST = "UPD_LIST";
	private CommonDAO dao;

	public void beforeProc(OperationContext context) throws Exception {
	}
	
	public void execute(OperationContext context) throws Exception {
		List<SystemParam> delList = (List<SystemParam>) context.get(DEL_LIST);
		List<SystemParam> updList = (List<SystemParam>) context.get(UPD_LIST);
		List<SystemParam> insList = (List<SystemParam>) context.get(INS_LIST);
		
		//删除
		StringBuilder idList = new StringBuilder();
		Iterator<SystemParam> delit = delList.iterator();
		while (delit.hasNext()) {
			SystemParam bean = delit.next();
			idList.append(bean.getId().toString()).append(",");
		}
		if(idList.length() > 0 && idList.charAt(idList.length() - 1) == ',') {
			idList.setLength(idList.length() - 1);
		}
		if(idList.length() > 0) {
			StringBuilder delHql = new StringBuilder("delete from SystemParam po where po.id in(");
			delHql.append(idList.toString()).append(")");
			dao.executeUpdate(delHql.toString());
			logger.info(delHql.toString());
		}
		//修改
		Iterator<SystemParam> updit = updList.iterator();
		while (updit.hasNext()) {
			SystemParam bean = updit.next();
			SystemParam param = (SystemParam) dao.get(SystemParam.class, bean.getId());
			param.setMagicId(bean.getMagicId());
			param.setParamId(bean.getParamId());
			param.setParamDesc(bean.getParamDesc());
			param.setParamValue(bean.getParamValue());

			dao.update(param);
		}
		//新增
		Iterator<SystemParam> insit = insList.iterator();
		while (insit.hasNext()) {
			SystemParam bean = insit.next();
			dao.insert(bean);
		}
	}

	public void afterProc(OperationContext context) throws Exception {
	}

	public CommonDAO getDao() {
		return dao;
	}

	public void setDao(CommonDAO dao) {
		this.dao = dao;
	}
}
