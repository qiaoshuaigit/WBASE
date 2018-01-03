package com.huateng.ebank.common;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.huateng.admin.SystemMonitor;

public class OnStartupServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(OnStartupServlet.class);

	private static final long serialVersionUID = 5193446530228861914L;
	private SystemMonitor sm = null;

	@Override
	public void init() throws ServletException{
			super.init();
		 
		try {
			sm = SystemMonitor.getInstance();
			initSystemMonitor();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage(), ex);
		}
	}
	
	private void initSystemMonitor() {
		sm.setRunningFlag(true);
		sm.start();
	}
	
	@Override
	public void destroy() {
		if(null != sm) {
			sm.setRunningFlag(false);
		}
		super.destroy();
	}
}
