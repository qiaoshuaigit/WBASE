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
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.entity.SystemParam;
import com.huateng.ebank.framework.web.commQuery.BaseUpdate;
import com.huateng.exception.AppException;
import com.huateng.stl4j.common.OPCaller;
import com.huateng.stl4j.common.OperationContext;
import com.huateng.stl4j.operation.ParamMngOperation;

public class SaveLogInSwitchUpdater extends BaseUpdate {
	private static Logger logger = Logger.getLogger(SaveLogInSwitchUpdater.class);
	public UpdateReturnBean saveOrUpdate(
			MultiUpdateResultBean multiUpdateResultBean,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		try {
			UpdateReturnBean updateReturnBean = new UpdateReturnBean();
			UpdateResultBean updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID("LogInSwitch");
			SystemParam bean = null;
			while(updateResultBean.hasNext()) {
				bean = new SystemParam();
				mapToObject(bean, updateResultBean.next());
			}
			if(null != bean) {
				List<SystemParam> updList = new ArrayList<SystemParam>();
				updList.add(bean);
				OperationContext oc = new OperationContext();
				oc.set(ParamMngOperation.DEL_LIST, new ArrayList() );
				oc.set(ParamMngOperation.UPD_LIST, updList);
				oc.set(ParamMngOperation.INS_LIST, new ArrayList());
				OPCaller.call(ParamMngOperation.ID, oc);
				updateReturnBean.setParameter("resultMsg", "更新成功！");
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
