package com.huateng.stl4j.operation;

import java.io.Serializable;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.huateng.ebank.entity.RoleFuncRelation;
import com.huateng.stl4j.common.BaseOperation;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.OperationContext;


public class RoleFuncRelationOperation extends BaseOperation {
	private static Logger logger = Logger.getLogger(RoleFuncRelationOperation.class);
	public static String ID = RoleFuncRelationOperation.class.getName();
	public static String ADD_ITERATOR = "ADD_ITERATOR";
	public static String DEL_ITERATOR = "DEL_ITERATOR";
	private CommonDAO dao;
	
	public void beforeProc(OperationContext context) throws Exception {
	}
	
	public void execute(OperationContext context) throws Exception {
		Iterator<RoleFuncRelation> addIt = (Iterator<RoleFuncRelation>) context.get(ADD_ITERATOR);
		Iterator<RoleFuncRelation> delIt = (Iterator<RoleFuncRelation>) context.get(DEL_ITERATOR);
		while(delIt.hasNext()) {
			RoleFuncRelation bean = delIt.next();
			dao.executeUpdate("delete from RoleFuncRelation po where po.roleCode=? and funcCode=?",
				new Serializable[]{bean.getRoleCode(), bean.getFuncCode()});
		}
		while(addIt.hasNext()) {
			RoleFuncRelation bean = addIt.next();
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
