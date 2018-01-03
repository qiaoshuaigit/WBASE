package com.huateng.stl4j.updater;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import om.util.OmUtils;

import org.apache.log4j.Logger;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.MultiUpdateResultBean;
import com.huateng.commquery.result.UpdateResultBean;
import com.huateng.commquery.result.UpdateReturnBean;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.framework.web.commQuery.BaseUpdate;
import com.huateng.exception.AppException;
import com.huateng.stl4j.common.OPCaller;
import com.huateng.stl4j.common.OperationContext;
import com.huateng.stl4j.operation.TerminalMngOperation;

public class SaveTerminalUpDwonUpdater extends BaseUpdate {
	private static Logger logger = Logger.getLogger(SaveTerminalUpDwonUpdater.class);

	@Override
	public UpdateReturnBean saveOrUpdate(
			MultiUpdateResultBean multiupdateresultbean,
			HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse) throws AppException {
		try {
			UpdateReturnBean updateReturnBean = new UpdateReturnBean();
			UpdateResultBean updateResultBean = null;
			if(multiUpdateResultBean.getUpdateResult().containsKey("TerminalMng")) {
				updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID("TerminalMng");
			}else if(multiUpdateResultBean.getUpdateResult().containsKey("TerminalApprove")){
				updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID("TerminalApprove");
			}
			
			String terminalCode = OmUtils.trim(updateResultBean.getParameter("terminalCode"));
			String type = OmUtils.trim(updateResultBean.getParameter("type"));
			String chkDsc = OmUtils.trim(updateResultBean.getParameter("chkDsc"));
			
			OperationContext oc = new OperationContext();
			oc.set(TerminalMngOperation.TERMINAL_CODE, terminalCode);
			oc.set(TerminalMngOperation.OP_TYPE, type);
			oc.set(TerminalMngOperation.CHK_DSC, chkDsc);
			OPCaller.call(TerminalMngOperation.ID, oc);
			String resultMsg = "";
			if("1".equals(type)){
				resultMsg = "自助终端"+terminalCode+"申请停用成功！";
			}
			else if("2".equals(type)){
				resultMsg = "自助终端"+terminalCode+"申请启用成功！";
			}
			else if("4".equals(type)){
				resultMsg = "自助终端"+terminalCode+"审核通过！";
			}
			else if("8".equals(type)){
				resultMsg = "自助终端"+terminalCode+"审核拒绝！";
			}
			updateReturnBean.setParameter("resultMsg", resultMsg);
			return updateReturnBean;
		} catch (CommonException e) {
			logger.error(e.getClass().getSimpleName(), e);
			throw new AppException(Module.SYSTEM_MODULE,
					Rescode.DEFAULT_RESCODE, e.getMessage());
		} catch (AppException appEx) {
			logger.error(appEx.getClass().getSimpleName(), appEx);
			throw appEx;
		} catch (Exception ex) {
			logger.error(ex.getClass().getSimpleName(), ex);
			throw new AppException(Module.SYSTEM_MODULE,
					Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}
	}

}
