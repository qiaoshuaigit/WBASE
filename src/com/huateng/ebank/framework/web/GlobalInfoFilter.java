package com.huateng.ebank.framework.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.huateng.ebank.common.GlobalInfo;

public class GlobalInfoFilter implements Filter {
	private static Logger logger = Logger.getLogger(GlobalInfoFilter.class);
	private static final String LOGIN_REF = "LOGIN_REF";
	private static final String EXPIRED_PAGE = "EXPIRED_PAGE";
	private String expiredPageName = null;
	private String loginRef = null;

	public void init(FilterConfig arg0) throws ServletException {
		this.loginRef = arg0.getInitParameter(LOGIN_REF);
		this.expiredPageName = arg0.getInitParameter(EXPIRED_PAGE);
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession httpSession = request.getSession();
		if(logger.isDebugEnabled()) {
			logger.debug("session id = " + httpSession.getId());
		}

		if(null != httpSession) {
			GlobalInfo globalInfo = (GlobalInfo) httpSession.getAttribute(GlobalInfo.KEY_GLOBAL_INFO);
			if (null != globalInfo) {
				GlobalInfo.setCurrentInstance(globalInfo);
				String sessionId = httpSession.getId();
				globalInfo.setSessionId(sessionId);
			} else {
				if(expiredSystem((HttpServletRequest) req, (HttpServletResponse) resp)) return;
			}
		} else {
			if(expiredSystem((HttpServletRequest) req, (HttpServletResponse) resp)) return;
		}
		filterChain.doFilter(req, resp);

		try {
			String uri = request.getRequestURI();

			if (uri.endsWith(".do") || uri.endsWith(".jsp")) {
				HttpServletResponse response = (HttpServletResponse) resp;
				if (!response.isCommitted()) {
					response.setHeader("Pragma", "No-cache");
					response.setHeader("Cache-Control", "no-cache,no-store,max-age=0");
					response.setDateHeader("Expires", 1);
				}
			}
		} catch (Exception e) {
			logger.error("doFilter(ServletRequest, ServletResponse, FilterChain)", e);
		}
	}
	
	private boolean expiredSystem(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		String uriStr = StringUtils.substringAfterLast(req.getServletPath(), "/");
		if (StringUtils.indexOf(loginRef, uriStr) == -1) {
			resp.setHeader("Pragma", "No-cache");
			resp.setHeader("Cache-Control", "no-cache,no-store,max-age=0");
			resp.setDateHeader("Expires", 1);
			RequestDispatcher rd = (req).getRequestDispatcher(expiredPageName);
			rd.forward(req, resp);
			return true;
		}
		return false;
	}

	public void destroy() {

	}

	public String getExpiredPageName() {
		return expiredPageName;
	}

	public void setExpiredPageName(String expiredPageName) {
		this.expiredPageName = expiredPageName;
	}

	public String getLoginRef() {
		return loginRef;
	}

	public void setLoginRef(String loginRef) {
		this.loginRef = loginRef;
	}
}
