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

public class TransFilter implements Filter {
	private static final Logger logger = Logger.getLogger(TransFilter.class);
	private static final String EXPIRED_PAGE = "EXPIRED_PAGE";
	private static final String LOGIN_REF = "LOGIN_REF";
	private FilterConfig filterConfig;
	private String expiredPageName = null;
	private String loginRef = null;
	
	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
		this.loginRef = this.filterConfig.getInitParameter(LOGIN_REF);
		this.expiredPageName = this.filterConfig.getInitParameter(EXPIRED_PAGE);
	}
	
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession httpSession = request.getSession(false);
		// 获取请求URL
		String uri = request.getRequestURI();
		if(null != httpSession) {
			GlobalInfo globalInfo = (GlobalInfo) httpSession.getAttribute(GlobalInfo.KEY_GLOBAL_INFO);
			if (null != globalInfo) {
				GlobalInfo.setCurrentInstance(globalInfo);
				String sessionId = httpSession.getId();
				globalInfo.setSessionId(sessionId);
			} else {
				if(expiredSystem((HttpServletRequest) req,
						(HttpServletResponse) resp)){
					return;
				}
			}
		} else {
			if(expiredSystem((HttpServletRequest) req, (HttpServletResponse) resp)){
				return;
			}
		}
		filterChain.doFilter(req, resp);
		
		try {
			uri = request.getRequestURI();

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
	
	private boolean expiredSystem(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		String uriStr = StringUtils.substringAfterLast(req.getServletPath(), "/");
		if(StringUtils.indexOf(loginRef, uriStr)==-1) {
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
		this.filterConfig = null;
	}
	
	public FilterConfig getFilterConfig() {
		return filterConfig;
	}
	
	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
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
