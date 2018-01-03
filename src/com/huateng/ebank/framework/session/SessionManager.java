package com.huateng.ebank.framework.session;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.huateng.ebank.common.SystemConstant;

public class SessionManager {
	private static final Logger logger = Logger.getLogger(SessionManager.class);
	private static SessionManager instance = null;

	protected SessionManager() {
	}

	public static SessionManager getInstance() {
		if (null == instance) {
			instance = new SessionManager();
		}
		return instance;
	}

	public HttpSession getNewSession(HttpServletRequest req) {
		HttpSession session = req.getSession(true);
		long currentTime = System.currentTimeMillis();
		if (true == session.isNew())
			session.setAttribute("sessionCreationTime", String
					.valueOf(currentTime));
		session.setAttribute("sessionLastAccessedTime", String
				.valueOf(currentTime));
		return session;
	}

	public HttpSession getSession(HttpServletRequest req) {
		HttpSession returnHttpSession = req.getSession(false);
		return returnHttpSession;
	}

	public void getSessionAndRefresh(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (null != session) {
			long currentTime = System.currentTimeMillis();
			if (true == session.isNew())
				session.setAttribute("sessionCreationTime", String
						.valueOf(currentTime));
			session.setAttribute("sessionLastAccessedTime", String
					.valueOf(currentTime));
		}

		return;
	}

	public String getSessionID(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (null == session)
			return null;
		return session.getId();
	}

	public String getSessionIDFromContext(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (null == session)
			return null;
		return session.getAttribute(SystemConstant.WEB_SESSION_ID).toString();
	}

	public boolean isValid(HttpServletRequest req) {
		return req.isRequestedSessionIdValid();
	}

	public boolean isExpired(HttpServletRequest req) {
		return true;
	}

	public boolean destroySessionData(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (null == session)
			return false;
		String strAttrName = null;
		Enumeration names = session.getAttributeNames();
		while (names.hasMoreElements()) {
			strAttrName = (String) names.nextElement();
			session.removeAttribute(strAttrName);
		}
		return true;
	}

	public boolean destroySession(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (null == session)
			return false;
		session.invalidate();
		return true;
	}

	/**
	 * Retrieve a session object based on the request and the attribute name.
	 */
	public Object getSessionObject(HttpServletRequest req, String attrName) {
		Object sessionObj = null;
		HttpSession session = req.getSession(false);
		if (null == session)
			return sessionObj;
		sessionObj = session.getAttribute(attrName);
		return sessionObj;
	}

	/**
	 * Set a session object based on the request and the attribute name.
	 */
	public boolean setSessionObject(HttpServletRequest req, String attrName,
			Object value) {
		HttpSession session = req.getSession(false);
		if (null == session)
			return false;
		session.setAttribute(attrName, value);
		return true;
	}

	/**
	 * Remove a session object based on the request and the attribute name.
	 */
	public boolean removeSessionObject(HttpServletRequest req, String attrName) {
		HttpSession session = req.getSession(false);
		if (null == session)
			return false;
		session.removeAttribute(attrName);
		return true;
	}

	/**
	 * Returns the current session identifier string an HTML hidden field.
	 * 
	 * @return String - The <I>session id</I> field html.
	 */
	public String getSessionIdHtmlField(HttpServletRequest req) {
		return "<input type=\"hidden\" name=\""
				+ SystemConstant.WEB_SESSION_ID + "\" value=\""
				+ this.getSessionIDFromContext(req) + "\">";
	}

	/**
	 * Returns the current session identifier string a URL parameter.
	 * 
	 * @return String - The <I>session id</I> parameter.
	 */
	public String getSessionIdParameter(HttpServletRequest req) {
		return SystemConstant.WEB_SESSION_ID + "="
				+ this.getSessionIDFromContext(req);
	}
}
