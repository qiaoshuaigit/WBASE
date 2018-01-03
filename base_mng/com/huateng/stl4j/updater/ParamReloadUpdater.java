package com.huateng.stl4j.updater;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import om.impdata.FileFormatFactory;
import om.param.ReloadService;

import org.apache.log4j.Logger;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.MultiUpdateResultBean;
import com.huateng.commquery.result.UpdateResultBean;
import com.huateng.commquery.result.UpdateReturnBean;
import com.huateng.ebank.business.common.DataDicUtils;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.framework.web.commQuery.BaseUpdate;
import com.huateng.exception.AppException;
import com.huateng.stl4j.bean.ParamReloadBean;
import com.huateng.stl4j.service.SystemParamService;

public class ParamReloadUpdater extends BaseUpdate {
	private static Logger logger = Logger.getLogger(ParamReloadUpdater.class);
	public UpdateReturnBean saveOrUpdate(
			MultiUpdateResultBean multiUpdateResultBean,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		try {
			UpdateReturnBean updateReturnBean = new UpdateReturnBean();
			UpdateResultBean updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID("ReloadList");
			StringBuilder resultMsg = new StringBuilder();
			while(updateResultBean.hasNext()) {
				ParamReloadBean bean = new ParamReloadBean();
				mapToObject(bean, updateResultBean.next());
				if(!bean.getSelect()) continue;
				try {
					paramReload(bean);
					resultMsg.append("重新加载参数成功: ").append(bean.getMagicId()).append("-").append(bean.getParamValue()).append("\n");
				} catch(Exception e) {
					logger.error("重新加载参数" + bean.getMagicId() + "失败：", e);
					resultMsg.append("重新加载参数").append(bean.getMagicId()).append("失败: ").append(e.getMessage()).append("\n");
				}
			}
			updateReturnBean.setParameter("resultMsg", resultMsg.toString());
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
	
	private void paramReload(ParamReloadBean bean) throws Exception {
		String paramType = bean.getMagicId();
		ReloadService reloadService = ReloadService.getInstance();
		if(paramType.equals("DATADIC")) {
			DataDicUtils.initDataDic();
		}
		else if(reloadService.registedReloader(paramType)) {
			reloadService.reload(paramType);
		}
		else {
			throw new Exception("未定义的参数类型");
		}
	}
}
