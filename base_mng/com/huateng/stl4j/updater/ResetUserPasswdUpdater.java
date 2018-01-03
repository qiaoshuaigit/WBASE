package com.huateng.stl4j.updater;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import om.util.OmUtils;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.MultiUpdateResultBean;
import com.huateng.commquery.result.UpdateResultBean;
import com.huateng.commquery.result.UpdateReturnBean;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.common.ErrorCode;
import com.huateng.ebank.common.ExceptionUtil;
import com.huateng.ebank.framework.web.commQuery.BaseUpdate;
import com.huateng.exception.AppException;
import com.huateng.stl4j.bean.ResetPasswdBean;
import com.huateng.stl4j.common.OPCaller;
import com.huateng.stl4j.common.OperationContext;
import com.huateng.stl4j.operation.ResetPasswdOperator;

public class ResetUserPasswdUpdater extends BaseUpdate {
	private static Logger logger = Logger.getLogger(ResetUserPasswdUpdater.class);
	public UpdateReturnBean saveOrUpdate(
			MultiUpdateResultBean multiUpdateResultBean,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		try {
			UpdateReturnBean updateReturnBean = new UpdateReturnBean();
			UpdateResultBean updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID("ResetPasswd");
			ResetPasswdBean bean = null;
			while(updateResultBean.hasNext()) {
				bean = new ResetPasswdBean();
				mapToObject(bean, updateResultBean.next());
			}
			if(null != bean) {
				preDataCheck(bean);
				OperationContext oc = new OperationContext();
				oc.set(ResetPasswdOperator.IN_BEAN, bean);
				OPCaller.call(ResetPasswdOperator.ID, oc);
				updateReturnBean.setParameter("resultMsg", "密码重置成功！");
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
	
	private void preDataCheck(ResetPasswdBean bean) throws CommonException {
		if(OmUtils.isEmpty(bean.getNewPasswd1()) || OmUtils.isEmpty(bean.getNewPasswd2())) {
			ExceptionUtil.throwCommonException("新密码或确认密码不能为空！", ErrorCode.ERROR_CODE_CANNOT_SUBMIT);
		}
		if(!OmUtils.trim(bean.getNewPasswd1()).equals(OmUtils.trim(bean.getNewPasswd2()))) {
			ExceptionUtil.throwCommonException("新密码与确认密码不一致！", ErrorCode.ERROR_CODE_CANNOT_SUBMIT);
		}
	}
}
