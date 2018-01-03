package com.huateng.admin;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import om.util.OmUtils;

import org.apache.log4j.Logger;
import org.logicalcobwebs.proxool.ConnectionPoolDefinitionIF;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.admin.SnapshotIF;

import com.huateng.proxool.ProxollBean;

public class SystemManager extends HttpServlet {
	private static final long serialVersionUID = 440442997941766567L;
	private static final Logger logger = Logger.getLogger(SystemManager.class);
	private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public SystemManager() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Enumeration<String> paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()) {
			String name = paramNames.nextElement();
			String value = (String) request.getParameter(name);
			if("command".equalsIgnoreCase(name) && "help".equalsIgnoreCase(value)) {
				doViewHelp(request, response);
			}
			else if("command".equalsIgnoreCase(name) && "jvmgc".equalsIgnoreCase(value)) {
				doJvmSystemGC(request, response);
			}
			else if("command".equalsIgnoreCase(name) && "datasource".equalsIgnoreCase(value)) {
				doViewDataSource(request, response);
			}
		}
	}
	
	/**
	 * 显示帮助信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void doViewHelp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.getWriter().println("command=jvmgc : Call System.gc()<br>");
		response.getWriter().println("command=datasource : View ProxoolDataSource snapshot<br>");
	}
	
	/**
	 * 强制JVM-GC
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void doJvmSystemGC(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.getWriter().println("Before doJvmSystemGC<br>");
		response.getWriter().println(SystemMonitor.getJVMInfo());
		response.getWriter().println("<br>");
		response.getWriter().println("doJvmSystemGC ......");
		response.getWriter().println("<br>");
		logger.info("doJvmSystemGC");
		System.gc();
		response.getWriter().println("After doJvmSystemGC<br>");
		response.getWriter().println(SystemMonitor.getJVMInfo());
		response.getWriter().println("<br>");
	}
	
	/**
	 * 查看DataSource快照
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void doViewDataSource(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String[] aliases = ProxoolFacade.getAliases();
		if(aliases.length < 1) return;
		String alias = aliases[0];
		try {
			ConnectionPoolDefinitionIF def = ProxoolFacade
					.getConnectionPoolDefinition(alias);
			SnapshotIF snapshot = ProxoolFacade.getSnapshot(def.getAlias(),
					false);
			if (snapshot != null) {
				response.getWriter().printf("Start date: %s<br>", DATE_FORMAT.format(snapshot.getDateStarted()));
				response.getWriter().printf("Snapshot: %s<br>", TIME_FORMAT.format(snapshot.getSnapshotDate()));
				response.getWriter().printf("Maximum Connection: %d<br>", snapshot.getMaximumConnectionCount());
				response.getWriter().printf("Available Connection: %d<br>", snapshot.getAvailableConnectionCount());
				response.getWriter().printf("Active Connection: %d<br>", snapshot.getActiveConnectionCount());
				response.getWriter().printf("Offline Connection: %d<br>", snapshot.getOfflineConnectionCount());
				response.getWriter().printf("Served: %d<br>", snapshot.getServedCount());
				response.getWriter().printf("Refused: %d<br>", snapshot.getRefusedCount());
			}
		} catch (Exception e) {
			response.getWriter().println(OmUtils.getStackTrace(e).replaceAll("\n|\r", "<br>"));
		}
	}
}
