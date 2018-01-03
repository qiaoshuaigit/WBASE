package com.huateng.stl4j.getter;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import om.util.OmUtils;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.common.GlobalInfo;
import com.huateng.ebank.entity.Operator;
import com.huateng.ebank.entity.RoleInfo;
import com.huateng.ebank.entity.UserRoleRelation;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.stl4j.bean.UserRoleRelationBean;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.service.UserMangerService;

public class UserRoleRelationGetter extends BaseGetter {
	private static Logger logger = Logger.getLogger(UserRoleRelationGetter.class);
	public Result call() throws AppException {
		try {
			List<UserRoleRelationBean> content = new ArrayList<UserRoleRelationBean>();
			String sOprId = OmUtils.trim(this.getCommQueryServletRequest().getParameter("oprId"));
			if(OmUtils.isEmpty(sOprId) || "0".equals(sOprId)) {
				List<RoleInfo> roleList = UserMangerService.getInstance().getRoleList();
				if(null == roleList) {
					roleList = CommonDAO.getInstance().findByWhere(RoleInfo.class, "po.status='1' ");
					UserMangerService.getInstance().setRoleList(roleList);
				}
				for(int i = 0; null != roleList && i < roleList.size(); i ++) {
					RoleInfo ri = roleList.get(i);
					UserRoleRelationBean bean = new UserRoleRelationBean();
					bean.setRoleCode(ri.getRoleCode());
					bean.setRoleName(ri.getRoleName());
					bean.setSelect(false);
					bean.setOprId(0);
					bean.setId(0);
					content.add(bean);
				}
				result.setContent(content);
				ResultMng.fillResultByList(getCommonQueryBean(), getCommQueryServletRequest(),
						content, getResult());
			}
			else {
				Integer oprId = new Integer(sOprId);
				List<UserRoleRelation> relationList = CommonDAO.getInstance().findByWhere(UserRoleRelation.class,"po.oprId=?", new Object[]{oprId});
				List<RoleInfo> roleList = CommonDAO.getInstance().findByWhere(RoleInfo.class, "po.status='1' ");
				UserMangerService.getInstance().setRoleList(roleList);
				for(int i = 0; null != roleList && i < roleList.size(); i ++) {
					RoleInfo ri = roleList.get(i);
					UserRoleRelationBean bean = new UserRoleRelationBean();
					bean.setRoleCode(ri.getRoleCode());
					bean.setRoleName(ri.getRoleName());
					bean.setSelect(false);
					bean.setOprId(oprId);
					bean.setId(0);
					for(int j = 0; j < relationList.size(); j ++) {
						UserRoleRelation ur = relationList.get(j);
						if(ur.getOprId().intValue() == oprId.intValue() &&
							ur.getRoleCode().intValue() == ri.getRoleCode().intValue()) {
							bean.setSelect(true);
							bean.setId(ur.getId());
						}
					}
					content.add(bean);
				}
				ResultMng.fillResultByList(getCommonQueryBean(), getCommQueryServletRequest(),
						content, getResult());
			}
			result.setContent(content);
			result.getPage().setTotalPage(1);
			result.init();
			return result;
		} catch (CommonException e) {
			logger.error(e.getClass().getSimpleName(), e);
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, e.getMessage());
		} catch (AppException appEx) {
			logger.error(appEx.getClass().getSimpleName(), appEx);
			throw appEx;
		} catch(Exception ex) {
			logger.error(ex.getClass().getSimpleName(), ex);
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}
	}
}
