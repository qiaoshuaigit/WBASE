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
import com.huateng.ebank.common.GlobalInfo;
import com.huateng.ebank.framework.web.commQuery.BaseUpdate;
import com.huateng.exception.AppException;
import com.huateng.stl4j.bean.ChgPasswdBean;
import com.huateng.stl4j.common.OPCaller;
import com.huateng.stl4j.common.OperationContext;
import com.huateng.stl4j.operation.ChgPasswdOperator;

public class ChgUserPasswdUpdater extends BaseUpdate {
	private static Logger logger = Logger.getLogger(ChgUserPasswdUpdater.class);
	public UpdateReturnBean saveOrUpdate(
			MultiUpdateResultBean multiUpdateResultBean,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		try {
			UpdateReturnBean updateReturnBean = new UpdateReturnBean();
			UpdateResultBean updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID("ChgPasswd");
			ChgPasswdBean bean = null;
			while(updateResultBean.hasNext()) {
				bean = new ChgPasswdBean();
				mapToObject(bean, updateResultBean.next());
			}
			if(null != bean) {
				GlobalInfo g = GlobalInfo.getCurrentInstance();
				if(g.getUserId().intValue() != bean.getId().intValue() ||
					!g.getOprNo().equals(bean.getOprNo())) {
					ExceptionUtil.throwCommonException("修改密码用户与会话信息不符！", ErrorCode.ERROR_CODE_CANNOT_SUBMIT);
				}
				preDataCheck(bean);
				OperationContext oc = new OperationContext();
				oc.set(ChgPasswdOperator.IN_BEAN, bean);
				OPCaller.call(ChgPasswdOperator.ID, oc);
				updateReturnBean.setParameter("resultMsg", "密码修改成功！");
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
	
	private void preDataCheck(ChgPasswdBean bean) throws CommonException {
		if(OmUtils.isEmpty(bean.getNewPasswd1()) || OmUtils.isEmpty(bean.getNewPasswd2())) {
			ExceptionUtil.throwCommonException("新密码或确认密码不能为空！", ErrorCode.ERROR_CODE_CANNOT_SUBMIT);
		}
		if(!OmUtils.trim(bean.getNewPasswd1()).equals(OmUtils.trim(bean.getNewPasswd2()))) {
			ExceptionUtil.throwCommonException("新密码与确认密码不一致！", ErrorCode.ERROR_CODE_CANNOT_SUBMIT);
		}
	}
}
