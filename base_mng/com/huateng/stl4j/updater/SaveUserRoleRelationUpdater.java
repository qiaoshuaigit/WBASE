package com.huateng.stl4j.updater;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.MultiUpdateResultBean;
import com.huateng.commquery.result.UpdateResultBean;
import com.huateng.commquery.result.UpdateReturnBean;
import com.huateng.ebank.business.common.service.CommonService;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.framework.web.commQuery.BaseUpdate;
import com.huateng.exception.AppException;
import com.huateng.stl4j.bean.UserRoleRelationBean;
import com.huateng.stl4j.common.OPCaller;
import com.huateng.stl4j.common.OperationContext;
import com.huateng.stl4j.operation.UserRoleRelationOperator;

public class SaveUserRoleRelationUpdater extends BaseUpdate {
	private static Logger logger = Logger.getLogger(SaveUserRoleRelationUpdater.class);
	public UpdateReturnBean saveOrUpdate(
			MultiUpdateResultBean multiUpdateResultBean,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		try {
			CommonService.getInstance().checkSysMngOprNos();
			UpdateReturnBean updateReturnBean = new UpdateReturnBean();
			UpdateResultBean updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID("UserRoleRelation");
			List<UserRoleRelationBean> delList = new ArrayList<UserRoleRelationBean>();
			List<UserRoleRelationBean> insList = new ArrayList<UserRoleRelationBean>();
			int lineCount = 0;
			while(updateResultBean.hasNext()) {
				UserRoleRelationBean bean = new UserRoleRelationBean();
				mapToObject(bean, updateResultBean.next());
				if(bean.getSelect() && 0 == bean.getId()) {
					insList.add(bean);
					lineCount ++;
				}
				else if(!bean.getSelect() && 0 != bean.getId()) {
					delList.add(bean);
					lineCount ++;
				}
			}
			if(lineCount > 0) {
				OperationContext oc = new OperationContext();
				oc.set(UserRoleRelationOperator.USER_ROLE_RELATION_DEL_LIST, delList);
				oc.set(UserRoleRelationOperator.USER_ROLE_RELATION_INS_LIST, insList);
				OPCaller.call(UserRoleRelationOperator.ID, oc);
				updateReturnBean.setParameter("resultMsg", "保存成功：共更新 " + lineCount + " 条数据！");
			}
			
			return updateReturnBean;
		} catch (CommonException e) {
			logger.error(e.getClass().getSimpleName(), e);
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, e.getMessage());
		} catch (AppException appEx) {
			logger.error(appEx.getClass().getSimpleName(), appEx);
			throw appEx;
		} catch (Exception ex) {
			logger.error(ex.getClass().getSimpleName(), ex);
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}
	}
}
