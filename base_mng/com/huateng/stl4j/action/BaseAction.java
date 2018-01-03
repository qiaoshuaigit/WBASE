package com.huateng.stl4j.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.databus.CommonQueryDataBusMng;
import com.huateng.commquery.result.databus.DataBus;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.common.ErrorCode;
import com.huateng.ebank.common.ExceptionUtil;
import com.huateng.ebank.common.GlobalInfo;
import com.huateng.ebank.framework.session.SessionManager;
import com.huateng.exception.AppException;

public class BaseAction extends Action {
	private static Logger log = Logger.getLogger(BaseAction.class);

	protected void refineSession(HttpSession session, String attribute) {
	}

	protected ActionForward getErrorForward(ActionMapping mapping) {
		return mapping.findForward("error");
	}

	protected void init(HttpServletRequest request) throws CommonException {
		this.checkGlobalInfo(request);
	}

	protected GlobalInfo checkGlobalInfo(HttpServletRequest request)
			throws CommonException {
		GlobalInfo globalInfo = GlobalInfo.getFromRequest(request);
		if (null == globalInfo) {
			ExceptionUtil.throwCommonException("用户没有登录.",
					ErrorCode.ERROR_CODE_TLRNO_SESSION_INVALID);
		}
		GlobalInfo.setCurrentInstance(globalInfo);
		return globalInfo;
	}
    //登陆时初始化 GlobalInfo
	protected GlobalInfo initGlobalInfo(HttpServletRequest request)
			throws CommonException {
		return checkGlobalInfo(request);
	}

	protected Object getSessionObject(HttpServletRequest req, String attrName) {
		return SessionManager.getInstance().getSessionObject(req, attrName);
	}

	protected boolean setSessionObject(HttpServletRequest req, String attrName, Object value) {
		return SessionManager.getInstance().setSessionObject(req, attrName, value);
	}

	protected boolean removeSessionObject(HttpServletRequest req, String attrName) {
		return SessionManager.getInstance().removeSessionObject(req, attrName);
	}

	public HttpSession getNewSession(HttpServletRequest req) {
		return SessionManager.getInstance().getNewSession(req);
	}

	protected HttpSession getSession(HttpServletRequest req) {
		return SessionManager.getInstance().getSession(req);
	}

	protected String getSessionID(HttpServletRequest req) {
		return SessionManager.getInstance().getSessionID(req);
	}

	protected boolean isExpired(HttpServletRequest req) {
		return SessionManager.getInstance().isExpired(req);
	}

	protected boolean destroySessionData(HttpServletRequest req) {
		return SessionManager.getInstance().destroySessionData(req);
	}

	protected boolean destroySession(HttpServletRequest req) {
		return SessionManager.getInstance().destroySession(req);
	}
	//从databus中获得相关的值
	public void setValue2DataBus(HttpServletRequest request,String databusId,String fieldId, String fieldValue)throws AppException{
		try {
			if (fieldValue == null)
				fieldValue = "";
			HttpSession session = null;
			session = request.getSession();
			DataBus dataBus = CommonQueryDataBusMng.getDataBus(session.getId(),
					databusId, session);
			dataBus.setField(fieldId, fieldValue);
		} catch (AppException appEx) {
			throw new AppException(appEx.getModuleName(), appEx.getErrCd(),
					appEx.getMessage(), appEx);
		} catch (Exception ex) {
			throw new AppException(Module.SYSTEM_MODULE,
					Rescode.DEFAULT_RESCODE, ex);
		}
	}
	//把相关的值放到databus中
	public String getValueFromDataBus(HttpServletRequest request,String databusId,String fieldId)throws AppException{
		try {
			HttpSession session = null;
			session = request.getSession();
			DataBus dataBus = CommonQueryDataBusMng.getDataBus(session.getId(),
					databusId, session);
			return dataBus.getFieldValue(fieldId);
		} catch (AppException appEx) {
			throw new AppException(appEx.getModuleName(), appEx.getErrCd(),
					appEx.getMessage(), appEx);
		} catch (Exception ex) {
			throw new AppException(Module.SYSTEM_MODULE,
					Rescode.DEFAULT_RESCODE, ex);
		}
	}
}
