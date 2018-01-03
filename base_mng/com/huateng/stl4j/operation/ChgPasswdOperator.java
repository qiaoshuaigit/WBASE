package com.huateng.stl4j.operation;

import org.apache.log4j.Logger;

import om.util.OmUtils;

import com.huateng.ebank.common.ErrorCode;
import com.huateng.ebank.common.ExceptionUtil;
import com.huateng.ebank.entity.Operator;
import com.huateng.stl4j.bean.ChgPasswdBean;
import com.huateng.stl4j.common.BaseOperation;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.CryptoUtils;
import com.huateng.stl4j.common.OperationContext;


public class ChgPasswdOperator extends BaseOperation {
	private static Logger logger = Logger.getLogger(ChgPasswdOperator.class);
	public static String ID = ChgPasswdOperator.class.getName();
	public static String IN_BEAN = "IN_BEAN";
	private CommonDAO dao;

	public void beforeProc(OperationContext context) throws Exception {
	}
	
	public void execute(OperationContext context) throws Exception {
		ChgPasswdBean bean = (ChgPasswdBean) context.get(IN_BEAN);
		String oldPasswdMD5 = CryptoUtils.MD5(OmUtils.trim(bean.getOldPasswd()));
		Operator opr = (Operator) dao.get(Operator.class, bean.getId());
		if(!oldPasswdMD5.equals(OmUtils.trim(opr.getPassword()))) {
			ExceptionUtil.throwCommonException("旧密码不正确！", ErrorCode.ERROR_CODE_CANNOT_SUBMIT);
		}
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
