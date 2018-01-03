package com.huateng.stl4j.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.huateng.ebank.entity.UserRoleRelation;
import com.huateng.stl4j.bean.UserRoleRelationBean;
import com.huateng.stl4j.common.BaseOperation;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.OperationContext;


public class UserRoleRelationOperator extends BaseOperation {
	private static Logger logger = Logger.getLogger(UserRoleRelationOperator.class);
	public static String ID = UserRoleRelationOperator.class.getName();
	public static String USER_ROLE_RELATION_DEL_LIST = "USER_ROLE_RELATION_DEL_LIST";
	public static String USER_ROLE_RELATION_INS_LIST = "USER_ROLE_RELATION_INS_LIST";
	private CommonDAO dao;

	public void beforeProc(OperationContext context) throws Exception {
	}
	
	public void execute(OperationContext context) throws Exception {
		List<UserRoleRelationBean> delList = (List<UserRoleRelationBean>) context.get(USER_ROLE_RELATION_DEL_LIST);
		List<UserRoleRelationBean> insList = (List<UserRoleRelationBean>) context.get(USER_ROLE_RELATION_INS_LIST);
		//删除
		StringBuilder idList = new StringBuilder();
		Iterator<UserRoleRelationBean> delit = delList.iterator();
		while (delit.hasNext()) {
			UserRoleRelationBean bean = delit.next();
			idList.append(bean.getId().toString()).append(",");
		}
		if(idList.length() > 0 && idList.charAt(idList.length() - 1) == ',') {
			idList.setLength(idList.length() - 1);
		}
		
		if(idList.length() > 0) {
			StringBuilder delHql = new StringBuilder("delete from UserRoleRelation po where po.id in(");
			delHql.append(idList.toString()).append(")");
			dao.executeUpdate(delHql.toString());
			logger.info(delHql.toString());
		}
		
		//新增
		Iterator<UserRoleRelationBean> insit = insList.iterator();
		while (insit.hasNext()) {
			UserRoleRelationBean bean = insit.next();
			UserRoleRelation urr = new UserRoleRelation();
			BeanUtils.copyProperties(bean, urr);
			dao.insert(urr);
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
