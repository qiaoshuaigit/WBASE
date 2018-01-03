package com.huateng.ebank.framework.servlet.listener;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import om.util.OmUtils;

import com.huateng.ebank.business.common.service.CommonService;
import com.huateng.ebank.common.GlobalInfo;

public class HttpSessionListener implements
		javax.servlet.http.HttpSessionListener {
	private static String SESSION_BETWEEN_SESSION_TIME = "sessionBetweenSessionTime";

	public void sessionCreated(HttpSessionEvent arg0) {
		HttpSession httpSession = arg0.getSession();
		int sessionTimeOut = Integer.valueOf(OmUtils.trim(System
				.getProperty(SESSION_BETWEEN_SESSION_TIME)));
		httpSession.setMaxInactiveInterval(sessionTimeOut);
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession httpSession = arg0.getSession();
		GlobalInfo globalInfo = (GlobalInfo) httpSession
				.getAttribute(GlobalInfo.KEY_GLOBAL_INFO);
		@SuppressWarnings("unchecked")
		Enumeration<String> e = httpSession.getAttributeNames();
		while(e.hasMoreElements()) {
			httpSession.removeAttribute(e.nextElement());
		}
		httpSession.invalidate();
		if (null != globalInfo) {
			CommonService.getInstance().oprInOutLog(globalInfo.getOprNo(), "out");
		}
	}

}
