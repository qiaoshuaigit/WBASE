package com.huateng.stl4j.updater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.MultiUpdateResultBean;
import com.huateng.commquery.result.UpdateResultBean;
import com.huateng.commquery.result.UpdateReturnBean;
import com.huateng.ebank.business.common.ClusterNoticeProcesser;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.entity.SystemParam;
import com.huateng.ebank.framework.web.commQuery.BaseUpdate;
import com.huateng.exception.AppException;
import com.huateng.stl4j.common.OPCaller;
import com.huateng.stl4j.common.OperationContext;
import com.huateng.stl4j.operation.ParamMngOperation;
import com.huateng.stl4j.service.SuperBranchListService;
import com.huateng.stl4j.service.SystemParamService;

public class SaveParamUpdater extends BaseUpdate {
	private static Logger logger = Logger.getLogger(SaveParamUpdater.class);
	public UpdateReturnBean saveOrUpdate(
			MultiUpdateResultBean multiUpdateResultBean,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		try {

			UpdateReturnBean updateReturnBean = new UpdateReturnBean();
			UpdateResultBean updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID("ParamMng");
			List<SystemParam> delList = new ArrayList<SystemParam>();
			List<SystemParam> updList = new ArrayList<SystemParam>();
			List<SystemParam> insList = new ArrayList<SystemParam>();
			int lineCount = 0;
			while(updateResultBean.hasNext()) {
				SystemParam bean = new SystemParam();
				Map map = updateResultBean.next();
				mapToObject(bean, map);
				bean.setParamValue((String) map.get("paramValue"));
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
				oc.set(ParamMngOperation.DEL_LIST, delList);
				oc.set(ParamMngOperation.INS_LIST, insList);
				oc.set(ParamMngOperation.UPD_LIST, updList);
				OPCaller.call(ParamMngOperation.ID, oc);
				updateReturnBean.setParameter("resultMsg", "保存成功：共更新 " + lineCount + " 条数据！");
				SystemParamService.getInstance().reload();
				ClusterNoticeProcesser.getInstance().sendNoticeToCluster(ClusterNoticeProcesser.MSGTYPE_SYSTEMPARAM_RELOAD, "系统参数表修改通知");
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
