package com.huateng.stl4j.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.apache.log4j.Logger;

import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.common.GlobalInfo;
import com.huateng.ebank.entity.FunctionInfo;
import com.huateng.ebank.entity.Operator;
import com.huateng.ebank.entity.RoleFuncRelation;
import com.huateng.ebank.entity.RoleInfo;
import com.huateng.stl4j.common.ApplicationContextUtils;
import com.huateng.stl4j.common.CommonDAO;

public class UserMangerService {
	private static Logger logger = Logger.getLogger(UserMangerService.class);
	/** TREE_ROOT = 0 */
	public static int TREE_ROOT = 0;
	/** TREE_TREE_FLAG = 1 */
	public static int TREE_TREE_FLAG = 1;
	/** TREE_MENU_FLAG = 2 */
	public static int TREE_MENU_FLAG = 2;
	public List<RoleInfo> roleList = null;
	private CommonDAO dao;
	
	private UserMangerService() {
	}
	
	public static UserMangerService getInstance() {
		return (UserMangerService) ApplicationContextUtils.getBean(UserMangerService.class.getName());
	}
	
	public Operator getOperator(Integer userId) {
		return (Operator) dao.get(Operator.class, userId);
	}
	
	public List getUserFunctionInfo(Integer userId) {
		StringBuilder hql = new StringBuilder("select distinct fc from FunctionInfo fc,");
		hql.append("RoleInfo rl,RoleFuncRelation rr,Operator op,UserRoleRelation ur ");
		hql.append("where fc.isShow='1' and fc.id=rr.funcCode and rl.id=rr.roleCode and rl.status='1' ");
		hql.append("and op.id=").append(userId).append(" and op.id=ur.oprId ");
		hql.append("and ur.roleCode=rl.id or fc.id=999999 or fc.id=100000 ");
		hql.append("order by fc.lastDirectory, fc.showseq asc");
		//return dao.findByWhere(FunctionInfo.class, "1=1 order by po.lastDirectory,po.showseq asc");
		return dao.findByHQL(hql.toString());
	}
	
	public String getFullPath(List<FunctionInfo> list, Integer id) {
		for(int i = 0; i < list.size(); i ++) {
			FunctionInfo fi = list.get(i);
			if(id.intValue() == fi.getId().intValue()) {
				if(0 == fi.getLastDirectory()) {
					return fi.getId().toString();
				}
				else {
					return getFullPath(list, fi.getLastDirectory()) + "," + fi.getId().toString();
				}
			}
		}
		return "";
	}
	
	public boolean existsRoleFuncRelation(List<RoleFuncRelation> list, Integer roleCode, Integer funcCode) {
		if(999999 == funcCode.intValue() || 100000 == funcCode.intValue()) {
			return true;
		}
		for(int i = 0; null != list && i < list.size(); i ++) {
			RoleFuncRelation bean = list.get(i);
			if(bean.getRoleCode().intValue() == roleCode.intValue() &&
				bean.getFuncCode().intValue() == funcCode.intValue()) {
				return true;
			}
		}
		return false;
	}
	
	public List<String> getRoleFuncRelation(Integer roleCode) throws CommonException  {
		List<String> result = new ArrayList<String>();
		StringBuilder hql = new StringBuilder("select po from FunctionInfo po where po.isShow='1' order by po.lastDirectory, po.showseq asc");
		List<FunctionInfo> list = dao.findByHQL(hql.toString());
		List<RoleFuncRelation> relationList = new ArrayList<RoleFuncRelation>();
		if(null != roleCode && 0 != roleCode.intValue()) {
			relationList = dao.findByWhere(RoleFuncRelation.class,
				"po.roleCode=?", new Object[] {roleCode});
		}
		Iterator it = list.iterator();
		StringBuilder fi = new StringBuilder();
		while(it.hasNext()) {
			fi.setLength(0);
			FunctionInfo funcInfo = (FunctionInfo)it.next();
			fi.append(funcInfo.getId()).append("|");
			fi.append(funcInfo.getLastDirectory()).append("|");
			if(1 == funcInfo.getIsDirectory()) {
				fi.append(funcInfo.getFuncName());
			}
			else {
				boolean checked = existsRoleFuncRelation(relationList, roleCode, funcInfo.getId());
				String fullpath = getFullPath(list, funcInfo.getId());
				fi.append("<input type='checkbox' id='id");
				fi.append(funcInfo.getId()).append("' ");
				fi.append("style='height:16px' name='id' height='14pt' ");
				if(checked) {
					fi.append("checked ");
				}
				fi.append("value='").append(fullpath).append("'>");
				fi.append(funcInfo.getFuncName()).append("</input>");
				//fi.append("<input type='checkbox'>").append(funcInfo.getFuncName()).append("</input>");
			}
			result.add(fi.toString());
		}
		return result;
	}
	
	public StringBuffer getRoleFunction(int iRoot, List list, int iloc) throws CommonException  {
		GlobalInfo global = GlobalInfo.getCurrentInstance();
		String sPath = global.getContextPath();
		StringBuffer sb = new StringBuffer();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			FunctionInfo f = (FunctionInfo)it.next();
			if((f.getLastDirectory() == iRoot) && (f.getLocation() == iloc)) {
				if(f.getIsDirectory() == 1) {
					sb.append("0,'");
					sb.append(f.getFuncName() + "','" + f.getId() + "','" + f.getFuncName() + "','','',4,'','','',-1,");
					sb.append(getRoleFunction(f.getId(), list, iloc));
					sb.append("1,");
					if(iRoot == 0) {
						sb.append("5,");
					}
				}
				else {
					sb.append("2,'" + f.getFuncName() + "','" + f.getId() + "','" + f.getFuncName() + "','',3,'','" + sPath + f.getPagePath() + "','',");
				}
			}
		}
		return sb;
	}
	
	public StringBuffer getDefinedMenu(Integer userId) throws CommonException {
		StringBuffer menu = new StringBuffer();
		menu.append(getRoleFunction(TREE_ROOT, getUserFunctionInfo(userId), TREE_MENU_FLAG));
		if(menu.length() != 0) {
			menu.deleteCharAt(menu.length() - 1);
		}
		return menu;
	}
	
	public StringBuffer getDefinedTree(Integer userId) throws CommonException {
		StringBuffer menu = new StringBuffer();
		menu.append(getRoleFunction(TREE_ROOT, getUserFunctionInfo(userId), TREE_TREE_FLAG));
		if(menu.length() != 0) {
			menu.deleteCharAt(menu.length() - 1);
		}
		return menu;
	}
	
	public CommonDAO getDao() {
		return dao;
	}
	
	public void setDao(CommonDAO dao) {
		this.dao = dao;
	}

	public List<RoleInfo> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleInfo> roleList) {
		this.roleList = roleList;
	}
}
