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
import com.huateng.ebank.entity.RoleInfo;
import com.huateng.ebank.framework.web.commQuery.BaseUpdate;
import com.huateng.exception.AppException;
import com.huateng.stl4j.common.OPCaller;
import com.huateng.stl4j.common.OperationContext;
import com.huateng.stl4j.operation.RoleInfoOperation;

public class SaveRoleInfoUpdater extends BaseUpdate {
	private static Logger logger = Logger.getLogger(SaveRoleInfoUpdater.class);
	public UpdateReturnBean saveOrUpdate(
			MultiUpdateResultBean multiUpdateResultBean,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		try {
			CommonService.getInstance().checkSysMngOprNos();
			UpdateReturnBean updateReturnBean = new UpdateReturnBean();
			UpdateResultBean updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID("RoleInfo");
			List<RoleInfo> delList = new ArrayList<RoleInfo>();
			List<RoleInfo> updList = new ArrayList<RoleInfo>();
			List<RoleInfo> insList = new ArrayList<RoleInfo>();
			int lineCount = 0;
			while(updateResultBean.hasNext()) {
				RoleInfo bean = new RoleInfo();
				mapToObject(bean, updateResultBean.next());
				switch (updateResultBean.getRecodeState()) {
					case UpdateResultBean.DELETE:
						lineCount ++;
						delList.add(bean);
						break;
					case UpdateResultBean.MODIFY:
						lineCount ++;
						updList.add(bean);
						break;
					case UpdateResultBean.INSERT:
						lineCount ++;
						insList.add(bean);
						break;
					default:
						break;
				}
			}
			if(lineCount > 0) {
				OperationContext oc = new OperationContext();
				oc.set(RoleInfoOperation.ROLEINFO_DEL_LIST, delList);
				oc.set(RoleInfoOperation.ROLEINFO_INS_LIST, insList);
				oc.set(RoleInfoOperation.ROLEINFO_UPD_LIST, updList);
				OPCaller.call(RoleInfoOperation.ID, oc);
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
