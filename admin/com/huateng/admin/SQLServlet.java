package com.huateng.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.huateng.ebank.common.GlobalInfo;
import com.huateng.exception.AppException;
import com.huateng.stl4j.common.CryptoUtils;

public class SQLServlet extends HttpServlet {
	private static final long serialVersionUID = 1908696465917881577L;
	public final static String SESSIONKEY = "V$$$$$SQL$$$$$$EXECUTE";
	public final static String SESSIONVALUE = "V$$$$$SQL$$$$$$VALUE";
	public static String dataSourceBeanId;
	public static String dbSchema;
	public static String dbType;

	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		SQLServlet.dataSourceBeanId = servletConfig.getInitParameter("dataSourceBeanId");
		SQLServlet.dbSchema = servletConfig.getInitParameter("dbSchema");
		SQLServlet.dbType = servletConfig.getInitParameter("dbType");
	}
	
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			GlobalInfo.getFromRequest(request);
		} catch(Exception e) {
		}

		String action = (String) request.getParameter("action");
		if("login".equals(action)) {
			try {
				HttpSession httpSession = request.getSession();
				GlobalInfo globalInfo = (GlobalInfo) httpSession.getAttribute(GlobalInfo.KEY_GLOBAL_INFO);
		        /**if(!"admin".equalsIgnoreCase(globalInfo.getOprNo()) &&
		        	!"admin".equalsIgnoreCase((String) request.getParameter("tlrno"))) {
		        	throw new Exception("该用户无权访问此页面！");
		        }*/

				String accesskey = (String) request.getParameter("accesskey");
				if(null == accesskey) {
					throw new Exception("Access Key不能为空。");
				}

				if(accesskey.equals("SELECT") && accesskey.equals("UPDATE")) {
					throw new Exception("无权访问！");
				}

				request.getSession().setAttribute(SESSIONKEY, SESSIONVALUE);
				request.setAttribute("ACTION", "EMPTY");
				if(CryptoUtils.hash(accesskey.getBytes("UTF-8"), "MD5").equals(CryptoUtils.MD5(getAccessKey("SELECT")))) {
					request.setAttribute("SQL", "输入SQL语句,仅限数据查询权限");
					request.getSession().setAttribute("OP_AUTH", "SELECT");
				}
				if(CryptoUtils.hash(accesskey.getBytes("UTF-8"), "MD5").equals(CryptoUtils.MD5(getAccessKey("UPDATE")))) {
					request.setAttribute("SQL", "输入SQL语句");
					request.getSession().setAttribute("OP_AUTH", "UPDATE");
				}
				this.getServletContext().getRequestDispatcher(
						"/pages/admin/sqlexec.jsp").forward(request, response);
			} catch(Exception ex) {
				request.getSession().removeAttribute(SESSIONKEY);
				request.getSession().removeAttribute("OP_AUTH");
				request.setAttribute("MSG", ex.getMessage());
				this.getServletContext().getRequestDispatcher(
						"/pages/admin/msg.jsp").forward(request, response);
			}
			return;
		}

		String sessionValue = (String) request.getSession().getAttribute(SESSIONKEY);
		if(null == sessionValue || !SESSIONVALUE.equals(sessionValue)) {
			this.getServletContext().getRequestDispatcher(
					"/pages/admin/flogin.jsp").forward(request, response);
			return;
		}

		String doExit = "" + request.getParameter("DO_EXIT");
		if(doExit.equalsIgnoreCase("true")) {
			request.getSession().removeAttribute(SESSIONKEY);
			request.getSession().removeAttribute("OP_AUTH");
			this.getServletContext().getRequestDispatcher(
				"/pages/admin/flogin.jsp").forward(request, response);
			return;
		}

		String opAuth = "" + request.getSession().getAttribute("OP_AUTH");

		String sql = request.getParameter("sql");
		request.setAttribute("SQL", sql);

		Connection conn = null;
		SQLExecutor executor = SQLExecutor.getInstance();
		try {
			if(null == sql || "".equals(sql.trim())) {
				if(true) {
					request.setAttribute("CODE", "FAIL");
					request.setAttribute("RESULT", null);
				}
				throw new Exception("SQL语句不能为空。");
			}
			sql = buildSqlString(sql.trim());
			String lsql = sql.toLowerCase();
			conn = executor.getConnection();
			long now = System.nanoTime();
			if(lsql.startsWith("select")) {
				QueryResult queryResult = executor.query(conn, sql, 0);
				request.setAttribute("ACTION", "QUERY");
				request.setAttribute("CODE", "SUCCESS");
				request.setAttribute("RESULT", queryResult);
			} else if(lsql.startsWith("list")) {
				QueryResult queryResult = executor.query(conn, sql, 0);
				request.setAttribute("ACTION", "QUERY");
				request.setAttribute("CODE", "SUCCESS");
				request.setAttribute("RESULT", queryResult);
			} else if(lsql.startsWith("desc")) {
				QueryResult queryResult = executor.query(conn, sql, 0);
				request.setAttribute("ACTION", "QUERY");
				request.setAttribute("CODE", "SUCCESS");
				request.setAttribute("RESULT", queryResult);
			} else if(lsql.startsWith("delete") || lsql.startsWith("update")
					|| lsql.startsWith("insert")) {
				if(!opAuth.equals("UPDATE")) {
					throw new Exception("无权访问！");
				}
				int num = executor.update(conn, sql);
				request.setAttribute("ACTION", "UPDATE");
				request.setAttribute("CODE", "SUCCESS");
				request.setAttribute("RESULT", "对" + num + "条记录进行了操作。");
			} else {
				throw new Exception("只支持select, update, delete和insert操作。");
			}
			long during = (System.nanoTime() - now)/1000000;
			request.setAttribute("DURING", String.valueOf(during));
		} catch(Exception ex) {
			request.setAttribute("CODE", "FAIL");
			request.setAttribute("RESULT", ex);
		} finally {
			if(null != conn) {
				executor.closeConnection(conn);
			}
		}

		this.getServletContext().getRequestDispatcher(
				"/pages/admin/sqlexec.jsp").forward(request, response);
	}

	private String buildSqlString(String lines) throws IOException {
		StringBuilder sql = new StringBuilder();
		BufferedReader fr = null;
		String line = null;

		try {
			fr = new BufferedReader(new StringReader(lines));
			boolean isEnd = false;
			boolean sqlEnd = false;
			while(true) {
				line = fr.readLine();
				if(null == line) {
					if(sql.length() > 0) isEnd = true;
					else break;
				}
				line = (line == null ? "" : line.trim());
				if(0 == line.length() && !isEnd) continue;
				if(line.startsWith("--") && !isEnd) continue;
				if(line.startsWith("//") && !isEnd) continue;
				if(line.indexOf("--") > 0) {
					line = line.substring(0, line.indexOf("--"));
				}
				if(line.indexOf("//") > 0) {
					line = line.substring(0, line.indexOf("//"));
				}
				if(line.endsWith(";")) sqlEnd = true;
				sql.append(line).append(" ");
				if(sqlEnd || isEnd) {
					line = sql.toString().trim();
					if(line.endsWith(";")) {
						line = line.substring(0, line.length() - 1);
						sql.setLength(0);
						sql.append(line);
					}
				}
				if(isEnd) break;
			}
		} finally {
			if(null != fr) fr.close();
		}
		return sql.toString().trim();
	}

	private String getAccessKey(String magicId) throws AppException {
		//return SysParamUnit.get("AccessKey", magicId);
		return magicId;
	}
}
