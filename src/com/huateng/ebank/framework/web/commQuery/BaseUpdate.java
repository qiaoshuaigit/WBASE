package com.huateng.ebank.framework.web.commQuery;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.huateng.common.DataObjectUtils;
import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.process.call._CallUpdate;
import com.huateng.commquery.result.MultiUpdateResultBean;
import com.huateng.commquery.result.UpdateReturnBean;
import com.huateng.commquery.result.databus.CommonQueryDataBusMng;
import com.huateng.commquery.result.databus.DataBus;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.common.ErrorCode;
import com.huateng.ebank.common.ExceptionUtil;
import com.huateng.ebank.common.GlobalInfo;
import com.huateng.ebank.framework.session.SessionManager;
import com.huateng.exception.AppException;

public abstract class BaseUpdate extends _CallUpdate {
	private static final Logger log = Logger.getLogger(BaseUpdate.class);
	public String databusId = "";

	@Override
	public UpdateReturnBean process(
			MultiUpdateResultBean multiUpdateResultBean,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		try {
			databusId = (String) ((multiUpdateResultBean.getParamMap()).get("databusId"));
			this.multiUpdateResultBean = multiUpdateResultBean;
			init(request);
			return super.process(multiUpdateResultBean, request, response);
		} catch (CommonException cex) {
			throw new AppException("", cex.getKey(), cex.getMessage(), cex);
		} catch (AppException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new AppException(Module.SYSTEM_MODULE,
					Rescode.DEFAULT_RESCODE, ex);
		}
	}

	protected void init(HttpServletRequest request) throws CommonException {
		httpReq = request;
		checkGlobalInfo(request);
		setGlobalInfoToThreadLoacl(request);
	}

	/*
	 * set GlobalInfo to ThreadLocal
	 */
	protected void setGlobalInfoToThreadLoacl(HttpServletRequest request) throws CommonException {
		HttpSession httpSession = request.getSession();
		GlobalInfo globalInfo = (GlobalInfo) httpSession.getAttribute(GlobalInfo.KEY_GLOBAL_INFO);
		if (null != globalInfo) {
			GlobalInfo.setCurrentInstance(globalInfo);
			String sessionId = httpSession.getId();
			globalInfo.setSessionId(sessionId);
		}
	}

	protected GlobalInfo checkGlobalInfo(HttpServletRequest request)
			throws CommonException {
		GlobalInfo globalInfo = GlobalInfo.getFromRequest(request);
		log.info("Session id = " + request.getSession().getId());
		if (null == globalInfo) {
			ExceptionUtil.throwCommonException("用户没有登录.",
					ErrorCode.ERROR_CODE_TLRNO_SESSION_INVALID);
		}
		GlobalInfo.setCurrentInstance(globalInfo);
		return globalInfo;
	}
	
	/**
	 * 把map拷贝到object中,对时间设置默认值
	 * 
	 * @param object
	 * @param map
	 * @throws AppException
	 */
	public static void mapToObject(Object object, Map map) throws AppException {
		try {
			DataObjectUtils.mapToObject(object, map);
		} catch (Exception e) {
			throw new AppException(Module.SYSTEM_MODULE,
					Rescode.DEFAULT_RESCODE, "属性拷贝出错", e);
		}
	}

	/**
	 * 把map拷贝到object中,不对时间设置默认值
	 * 
	 * @param object
	 * @param map
	 * @throws AppException
	 */
	public static void mapToObject2(Object object, Map map) throws AppException {
		try {
			DataObjectUtils.mapToObject2(object, map);
		} catch (Exception e) {
			throw new AppException(Module.SYSTEM_MODULE,
					Rescode.DEFAULT_RESCODE, "属性拷贝出错", e);
		}
	}

	public void setValue2DataBus(String fieldId, String fieldValue)
			throws AppException {
		try {
			if (fieldValue == null)
				fieldValue = "";
			HttpSession session = null;
			if (httpReq instanceof HttpServletRequest) {
				session = (httpReq).getSession();
			}
			DataBus dataBus = CommonQueryDataBusMng.getDataBus(session.getId(),
					databusId, session);
			dataBus.setField(fieldId, fieldValue);
		} catch (AppException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new AppException(Module.SYSTEM_MODULE,
					Rescode.DEFAULT_RESCODE, ex);
		}
	}

	public String getValueFromDataBus(String fieldId) throws AppException {
		try {
			HttpSession session = null;
			if (httpReq instanceof HttpServletRequest) {
				session = (httpReq).getSession();
			}
			DataBus dataBus = CommonQueryDataBusMng.getDataBus(session.getId(),
					databusId, session);
			return dataBus.getFieldValue(fieldId);
		} catch (AppException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new AppException(Module.SYSTEM_MODULE,
					Rescode.DEFAULT_RESCODE, ex);
		}
	}

	/**
	 * Retrieve a session object based on the request and the attribute name.
	 */
	protected Object getSessionObject(String attrName) {
		Object object = SessionManager.getInstance().getSessionObject(httpReq,
				attrName);
		if (object != null && !object.equals("")) {
			removeSessionObject(attrName);
			return object;
		} else {
			return null;
		}
	}

	/**
	 * Set a session object based on the request and the attribute name.
	 */
	protected boolean setSessionObject(String attrName, Object value) {
		return SessionManager.getInstance().setSessionObject(httpReq, attrName, value);
	}

	/**
	 * Remove a session object based on the request and the attribute name.
	 */
	protected boolean removeSessionObject(String attrName) {
		return SessionManager.getInstance().removeSessionObject(httpReq, attrName);
	}
}
