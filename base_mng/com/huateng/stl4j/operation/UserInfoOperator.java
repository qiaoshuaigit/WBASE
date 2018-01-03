package com.huateng.stl4j.operation;

import java.util.Iterator;
import java.util.List;

import om.util.OmUtils;

import org.apache.log4j.Logger;

import com.huateng.ebank.common.SystemConstant;
import com.huateng.ebank.entity.Operator;
import com.huateng.stl4j.common.BaseOperation;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.CryptoUtils;
import com.huateng.stl4j.common.OperationContext;


public class UserInfoOperator extends BaseOperation {
	private static Logger logger = Logger.getLogger(UserInfoOperator.class);
	public static String ID = UserInfoOperator.class.getName();
	public static String USERINFO_DEL_LIST = "USERINFO_DEL_LIST";
	public static String USERINFO_INS_LIST = "USERINFO_INS_LIST";
	public static String USERINFO_UPD_LIST = "USERINFO_UPD_LIST";
	private CommonDAO dao;

	public void beforeProc(OperationContext context) throws Exception {
	}
	
	public void execute(OperationContext context) throws Exception {
		List<Operator> oprDelList = (List<Operator>) context.get(USERINFO_DEL_LIST);
		List<Operator> oprUpdList = (List<Operator>) context.get(USERINFO_UPD_LIST);
		List<Operator> oprInsList = (List<Operator>) context.get(USERINFO_INS_LIST);
		//删除
		StringBuilder idList = new StringBuilder();
		Iterator<Operator> delit = oprDelList.iterator();
		while (delit.hasNext()) {
			Operator bean = delit.next();
			idList.append(bean.getId().toString()).append(",");
		}
		if(idList.length() > 0 && idList.charAt(idList.length() - 1) == ',') {
			idList.setLength(idList.length() - 1);
		}
		
		if(idList.length() > 0) {
			StringBuilder delHql = new StringBuilder("delete from Operator po where po.id in(");
			delHql.append(idList.toString()).append(")");
			dao.executeUpdate(delHql.toString());
			logger.info(delHql.toString());
			delHql.setLength(0);
			delHql.append("delete from UserRoleRelation po where po.oprId in(");
			delHql.append(idList.toString()).append(")");
			dao.executeUpdate(delHql.toString());
			logger.info(delHql.toString());
		}
		
		//修改
		Iterator<Operator> updit = oprUpdList.iterator();
		while (updit.hasNext()) {
			Operator bean = updit.next();
			Operator opr = (Operator) dao.get(Operator.class, bean.getId());
			opr.setOprNo(bean.getOprNo());
			opr.setUserName(bean.getUserName());
			opr.setBrhNo(bean.getBrhNo());
			opr.setStatus(bean.getStatus());
			opr.setTeleno(bean.getTeleno());
			
			dao.update(opr);
		}
		
		//新增
		String defaultPasswd = OmUtils.trim(CryptoUtils.MD5(SystemConstant.DEFAULT_PASSWD));
		Iterator<Operator> insit = oprInsList.iterator();
		while (insit.hasNext()) {
			Operator bean = insit.next();
			//bean.setPassword("E10ADC3949BA59ABBE56E057F20F883E");
			bean.setPassword(defaultPasswd);
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
