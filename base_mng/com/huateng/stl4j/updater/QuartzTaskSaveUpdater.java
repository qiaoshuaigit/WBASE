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
import com.huateng.ebank.business.common.ClusterNoticeProcesser;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.framework.web.commQuery.BaseUpdate;
import com.huateng.exception.AppException;
import com.huateng.stl4j.bean.QuartzTaskBean;
import com.huateng.stl4j.common.OPCaller;
import com.huateng.stl4j.common.OperationContext;
import com.huateng.stl4j.operation.QuartzTaskManagerOP;
import com.huateng.stl4j.service.SystemParamService;

public class QuartzTaskSaveUpdater extends BaseUpdate {
	private static Logger logger = Logger.getLogger(QuartzTaskSaveUpdater.class);
	
	public UpdateReturnBean saveOrUpdate(
			MultiUpdateResultBean multiUpdateResultBean,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		try {
			UpdateReturnBean updateReturnBean = new UpdateReturnBean();
			UpdateResultBean updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID("QuartzTaskManager");
			List<QuartzTaskBean> updList = new ArrayList<QuartzTaskBean>();
			while(updateResultBean.hasNext()) {
				QuartzTaskBean bean = new QuartzTaskBean();
				mapToObject(bean, updateResultBean.next());
				switch (updateResultBean.getRecodeState()) {
				case UpdateResultBean.MODIFY:
					updList.add(bean);
					break;
				default:
					break;
				}
			}
			if(updList.size() > 0) {
				OperationContext oc = new OperationContext();
				oc.set("UPD_LIST", updList);
				oc.set(QuartzTaskManagerOP.CMD, "UPDATE");
				OPCaller.call(QuartzTaskManagerOP.ID, oc);
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
