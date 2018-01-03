package com.huateng.stl4j.operation;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.ebank.entity.RoleFuncRelation;
import com.huateng.ebank.entity.RoleInfo;
import com.huateng.stl4j.common.BaseOperation;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.OperationContext;


public class RoleInfoOperation extends BaseOperation {
	private static Logger logger = Logger.getLogger(RoleInfoOperation.class);
	public static String ID = RoleInfoOperation.class.getName();
	public static String ROLEINFO_DEL_LIST = "ROLEINFO_DEL_LIST";
	public static String ROLEINFO_INS_LIST = "ROLEINFO_INS_LIST";
	public static String ROLEINFO_UPD_LIST = "ROLEINFO_UPD_LIST";
	private CommonDAO dao;

	public void beforeProc(OperationContext context) throws Exception {
	}
	
	public void execute(OperationContext context) throws Exception {
		List<RoleInfo> roleInfoDelList = (List<RoleInfo>) context.get(ROLEINFO_DEL_LIST);
		List<RoleInfo> roleInfoUpdList = (List<RoleInfo>) context.get(ROLEINFO_UPD_LIST);
		List<RoleInfo> roleInfoInsList = (List<RoleInfo>) context.get(ROLEINFO_INS_LIST);
		
		//删除
		StringBuilder idList = new StringBuilder();
		Iterator<RoleInfo> delit = roleInfoDelList.iterator();
		while (delit.hasNext()) {
			RoleInfo bean = delit.next();
			idList.append(bean.getId().toString()).append(",");
		}
		if(idList.length() > 0 && idList.charAt(idList.length() - 1) == ',') {
			idList.setLength(idList.length() - 1);
		}
		
		if(idList.length() > 0) {
			StringBuilder delHql = new StringBuilder("delete from RoleInfo po where po.id in(");
			delHql.append(idList.toString()).append(")");
			dao.executeUpdate(delHql.toString());
			logger.info(delHql.toString());
			delHql.setLength(0);
			delHql.append("delete from RoleFuncRelation po where po.roleCode in(");
			delHql.append(idList.toString()).append(")");
			dao.executeUpdate(delHql.toString());
			logger.info(delHql.toString());
			delHql.setLength(0);
			delHql.append("delete from UserRoleRelation po where po.roleCode in(");
			delHql.append(idList.toString()).append(")");
			dao.executeUpdate(delHql.toString());
			logger.info(delHql.toString());
		}
		
		//修改
		Iterator<RoleInfo> updit = roleInfoUpdList.iterator();
		while (updit.hasNext()) {
			RoleInfo bean = updit.next();
			RoleInfo roleInfo = (RoleInfo) dao.get(RoleInfo.class, bean.getId());
			roleInfo.setRoleName(bean.getRoleName());
			roleInfo.setStatus(bean.getStatus());
			dao.update(roleInfo);
		}
		
		//新增
		Iterator<RoleInfo> insit = roleInfoInsList.iterator();
		while (insit.hasNext()) {
			RoleInfo bean = insit.next();
			bean.setId(getNextRoleCode());
			dao.insert(bean);
		}
	}
	
	private Integer getNextRoleCode() throws Exception {
		String startType = "";
		startType = "10";
		String minValue = startType + "0001";
		String maxValue = startType + "9999";
		List list = dao.findByHQL("select po from RoleInfo po where po.id>=? and po.id<? order by po.id desc",
				new Object[]{new Integer(minValue), new Integer(maxValue)});
		if(list.size() > 0) {
			RoleInfo po = (RoleInfo) list.get(0);
			minValue = String.format("%6d", po.getId().intValue() + 1);
		}
		return new Integer(minValue);
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

