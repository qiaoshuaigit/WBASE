package com.huateng.stl4j.operation;


import org.apache.log4j.Logger;

import om.util.OmUtils;

import com.huateng.ebank.entity.Operator;
import com.huateng.stl4j.bean.ResetPasswdBean;
import com.huateng.stl4j.common.BaseOperation;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.CryptoUtils;
import com.huateng.stl4j.common.OperationContext;

public class ResetPasswdOperator extends BaseOperation {
	private static Logger logger = Logger.getLogger(ResetPasswdOperator.class);
	public static String ID = ResetPasswdOperator.class.getName();
	public static String IN_BEAN = "IN_BEAN";
	private CommonDAO dao;

	public void beforeProc(OperationContext context) throws Exception {
	}
	
	public void execute(OperationContext context) throws Exception {
		ResetPasswdBean bean = (ResetPasswdBean) context.get(IN_BEAN);
		Operator opr = (Operator) dao.get(Operator.class, bean.getId());
		opr.setPassword(CryptoUtils.MD5(OmUtils.trim(bean.getNewPasswd1())));
		dao.update(opr);
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
