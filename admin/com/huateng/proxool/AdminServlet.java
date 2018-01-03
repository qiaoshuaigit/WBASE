package com.huateng.proxool;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.logicalcobwebs.proxool.ConnectionInfoIF;
import org.logicalcobwebs.proxool.ConnectionPoolDefinitionIF;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.Version;
import org.logicalcobwebs.proxool.admin.SnapshotIF;
import org.logicalcobwebs.proxool.admin.StatisticsIF;

public class AdminServlet extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(AdminServlet.class);

	private static final String[] STATUS_CLASSES = { "null", "available", "active", "offline" };
	public static final String OUTPUT_FULL = "full";
	public static final String OUTPUT_SIMPLE = "simple";
	private String output;
	private String cssFile;
	private String contextPath = null;
	private static final String STATISTIC = "statistic";
	private static final String CORE_PROPERTY = "core-property";
	private static final String STANDARD_PROPERTY = "standard-property";
	private static final String DELEGATED_PROPERTY = "delegated-property";
	private static final String SNAPSHOT = "snapshot";
	private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
	private static final String DETAIL = "detail";
	private static final String DETAIL_MORE = "more";
	private static final String DETAIL_LESS = "less";
	private static final String TAB = "tab";
	private static final String TAB_DEFINITION = "definition";
	private static final String TAB_SNAPSHOT = "snapshot";
	private static final String TAB_STATISTICS = "statistics";
	private static final String ALIAS = "alias";
	private static final String CONNECTION_ID = "id";

	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		this.output = servletConfig.getInitParameter("output");
		if (this.output != null) {
			if (this.output.equalsIgnoreCase("full")) {
				this.output = "full";
			} else if (this.output.equalsIgnoreCase("simple")) {
				this.output = "simple";
			} else {
				LOG.warn("Unrecognised output parameter for "
						+ getClass().getName() + ". Expected: " + "full"
						+ " or " + "simple");
				this.output = null;
			}
		}
		if (this.output == null) {
			this.output = "full";
		}

		this.cssFile = servletConfig.getInitParameter("cssFile");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Pragma", "no-cache");
		String link = request.getRequestURI();
		String alias = request.getParameter("alias");
		this.contextPath = request.getContextPath();
		if(!this.cssFile.startsWith(this.contextPath)) {
			this.cssFile = this.contextPath + this.cssFile;
		}

		ConnectionPoolDefinitionIF def = null;
		if (alias != null) {
			try {
				def = ProxoolFacade.getConnectionPoolDefinition(alias);
			} catch (ProxoolException e) {
				alias = null;
			}
		}
		String[] aliases = ProxoolFacade.getAliases();
		if ((alias == null) && (aliases.length > 0)) {
			alias = aliases[0];
		}

		if ((def == null) && (alias != null)) {
			try {
				def = ProxoolFacade.getConnectionPoolDefinition(alias);
			} catch (ProxoolException e) {
				throw new ServletException("Couldn't find pool with alias "
						+ alias);
			}
		}

		String tab = request.getParameter("tab");
		if (tab == null) {
			tab = "definition";
		}

		String snapshotDetail = request.getParameter("detail");

		String snapshotConnectionId = request.getParameter("id");
		try {
			if (this.output.equals("full")) {
				response.setContentType("text/html");
				openHtml(response.getOutputStream());
			}
			/**
			response.getOutputStream().println(
					"<div class=\"version\">Proxool " + Version.getVersion()
							+ "</div>");*/
			response.getOutputStream().println(
					"<div class=\"version\">Proxool JDBC Connection Pool Monitor</div>");
			doList(response.getOutputStream(), alias, tab, link);

			if ((aliases != null) && (aliases.length > 0)) {
				StatisticsIF[] statisticsArray = ProxoolFacade
						.getStatistics(alias);
				boolean statisticsAvailable = (statisticsArray != null)
						&& (statisticsArray.length > 0);
				boolean statisticsComingSoon = def.getStatistics() != null;

				if ((!statisticsComingSoon) && (tab.equals("statistics"))) {
					tab = "definition";
				}
				doTabs(response.getOutputStream(), alias, link, tab,
						statisticsAvailable, statisticsComingSoon);
				if (tab.equals("definition"))
					doDefinition(response.getOutputStream(), def);
				else if (tab.equals("snapshot"))
					doSnapshot(response.getOutputStream(), def, link,
							snapshotDetail, snapshotConnectionId);
				else if (tab.equals("statistics"))
					doStatistics(response.getOutputStream(), statisticsArray, def);
				else
					throw new ServletException("Unrecognised tab '" + tab + "'");
			}
		} catch (ProxoolException e) {
			throw new ServletException("Problem serving Proxool Admin", e);
		}

		if (this.output.equals("full"))
			closeHtml(response.getOutputStream());
	}

	private void doTabs(ServletOutputStream out, String alias, String link,
			String tab, boolean statisticsAvailable,
			boolean statisticsComingSoon) throws IOException {
		out.println("<ul>");
		out.println("<li class=\""
				+ (tab.equals("definition") ? "active" : "inactive")
				+ "\"><a class=\"quiet\" href=\"" + link + "?alias=" + alias
				+ "&tab=" + "definition" + "\">Definition</a></li>");
		out.println("<li class=\""
				+ (tab.equals("snapshot") ? "active" : "inactive")
				+ "\"><a class=\"quiet\" href=\"" + link + "?alias=" + alias
				+ "&tab=" + "snapshot" + "\">Snapshot</a></li>");
		if (statisticsAvailable)
			out
					.println("<li class=\""
							+ (tab.equals("statistics") ? "active" : "inactive")
							+ "\"><a class=\"quiet\" href=\"" + link
							+ "?alias=" + alias + "&tab=" + "statistics"
							+ "\">Statistics</a></li>");
		else if (statisticsComingSoon) {
			out.println("<li class=\"disabled\">Statistics</li>");
		}
		out.println("</ul>");
	}

	private void doStatistics(ServletOutputStream out,
			StatisticsIF[] statisticsArray, ConnectionPoolDefinitionIF cpd)
			throws IOException {
		for (int i = 0; i < statisticsArray.length; i++) {
			StatisticsIF statistics = statisticsArray[i];

			openDataTable(out);

			printDefinitionEntry(out, "alias", cpd.getAlias(), "core-property");

			printDefinitionEntry(out, "Period", TIME_FORMAT.format(statistics
					.getStartDate())
					+ " to " + TIME_FORMAT.format(statistics.getStopDate()),
					"statistic");

			printDefinitionEntry(out, "Served", statistics.getServedCount()
					+ " ("
					+ DECIMAL_FORMAT.format(statistics.getServedPerSecond())
					+ "/s)", "statistic");

			printDefinitionEntry(out, "Refused", statistics.getRefusedCount()
					+ " ("
					+ DECIMAL_FORMAT.format(statistics.getRefusedPerSecond())
					+ "/s)", "statistic");

			printDefinitionEntry(out, "Average active time", DECIMAL_FORMAT
					.format(statistics.getAverageActiveTime() / 1000.0D)
					+ "s", "statistic");

			StringBuffer activityLevelBuffer = new StringBuffer();
			int activityLevel = (int) (100.0D * statistics
					.getAverageActiveCount() / cpd.getMaximumConnectionCount());
			activityLevelBuffer.append(activityLevel);
			activityLevelBuffer.append("%<br/>");
			String[] colours = { "0000ff", "eeeeee" };
			int[] lengths = { activityLevel, 100 - activityLevel };
			drawBarChart(activityLevelBuffer, colours, lengths);
			printDefinitionEntry(out, "Activity level", activityLevelBuffer
					.toString(), "statistic");

			closeTable(out);
		}
	}

	private void drawBarChart(StringBuffer out, String[] colours, int[] lengths) {
		out
				.append("<table style=\"margin: 8px; font-size: 50%;\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>");

		int totalLength = 0;
		for (int i = 0; i < colours.length; i++) {
			totalLength += lengths[i];
		}

		for (int j = 0; j < colours.length; j++) {
			String colour = colours[j];
			int length = lengths[j];
			if (length > 0) {
				out.append("<td style=\"background-color: #");
				out.append(colour);
				out.append("\" width=\"");
				out.append(100 * length / totalLength);
				out.append("%\">&nbsp;</td>");
			}
		}
		out.append("</tr></table>");
	}

	private void doDefinition(ServletOutputStream out,
			ConnectionPoolDefinitionIF cpd) throws IOException {
		openDataTable(out);

		printDefinitionEntry(out, "alias", cpd.getAlias(), "core-property");
		printDefinitionEntry(out, "driver-url", cpd.getUrl(), "core-property");
		printDefinitionEntry(out, "driver-class", cpd.getDriver(),
				"core-property");
		printDefinitionEntry(out, "minimum-connection-count", String
				.valueOf(cpd.getMinimumConnectionCount()), "standard-property");
		printDefinitionEntry(out, "maximum-connection-count", String
				.valueOf(cpd.getMaximumConnectionCount()), "standard-property");
		printDefinitionEntry(out, "prototype-count",
				cpd.getPrototypeCount() > 0 ? String.valueOf(cpd
						.getPrototypeCount()) : null, "standard-property");
		printDefinitionEntry(out, "simultaneous-build-throttle", String
				.valueOf(cpd.getSimultaneousBuildThrottle()),
				"standard-property");
		printDefinitionEntry(out, "maximum-connection-lifetime",
				formatMilliseconds(cpd.getMaximumConnectionLifetime()),
				"standard-property");
		printDefinitionEntry(out, "maximum-active-time", formatMilliseconds(cpd
				.getMaximumActiveTime()), "standard-property");
		printDefinitionEntry(out, "house-keeping-sleep-time", cpd
				.getHouseKeepingSleepTime()
				/ 1000L + "s", "standard-property");
		printDefinitionEntry(out, "house-keeping-test-sql", cpd
				.getHouseKeepingTestSql(), "standard-property");
		printDefinitionEntry(out, "test-before-use", String.valueOf(cpd
				.isTestBeforeUse()), "standard-property");
		printDefinitionEntry(out, "test-after-use", String.valueOf(cpd
				.isTestAfterUse()), "standard-property");
		printDefinitionEntry(out, "recently-started-threshold",
				formatMilliseconds(cpd.getRecentlyStartedThreshold()),
				"standard-property");
		printDefinitionEntry(out, "overload-without-refusal-lifetime",
				formatMilliseconds(cpd.getOverloadWithoutRefusalLifetime()),
				"standard-property");
		printDefinitionEntry(out, "injectable-connection-interface", String
				.valueOf(cpd.getInjectableConnectionInterface()),
				"standard-property");
		printDefinitionEntry(out, "injectable-statement-interface", String
				.valueOf(cpd.getInjectableStatementInterface()),
				"standard-property");
		printDefinitionEntry(out, "injectable-callable-statement-interface",
				String.valueOf(cpd.getInjectableCallableStatementInterface()),
				"standard-property");
		printDefinitionEntry(out, "injectable-prepared-statement-interface",
				String.valueOf(cpd.getInjectablePreparedStatementInterface()),
				"standard-property");

		String fatalSqlExceptions = null;
		if ((cpd.getFatalSqlExceptions() != null)
				&& (cpd.getFatalSqlExceptions().size() > 0)) {
			StringBuffer fatalSqlExceptionsBuffer = new StringBuffer();
			Iterator i = cpd.getFatalSqlExceptions().iterator();
			while (i.hasNext()) {
				String s = (String) i.next();
				fatalSqlExceptionsBuffer.append(s);
				fatalSqlExceptionsBuffer.append(i.hasNext() ? ", " : "");
			}
			fatalSqlExceptions = fatalSqlExceptionsBuffer.toString();
		}
		printDefinitionEntry(out, "fatal-sql-exception", fatalSqlExceptions,
				"standard-property");
		printDefinitionEntry(out, "fatal-sql-exception-wrapper-class", cpd
				.getFatalSqlExceptionWrapper(), "standard-property");
		printDefinitionEntry(out, "statistics", cpd.getStatistics(),
				"standard-property");
		printDefinitionEntry(out, "statistics-log-level", cpd
				.getStatisticsLogLevel(), "standard-property");
		printDefinitionEntry(out, "verbose", String.valueOf(cpd.isVerbose()),
				"standard-property");
		printDefinitionEntry(out, "trace", String.valueOf(cpd.isTrace()),
				"standard-property");

		Properties p = cpd.getDelegateProperties();
		Iterator i = p.keySet().iterator();
		while (i.hasNext()) {
			String name = (String) i.next();
			String value = p.getProperty(name);

			if ((name.toLowerCase().indexOf("password") > -1)
					|| (name.toLowerCase().indexOf("passwd") > -1)) {
				value = "******";
			}
			printDefinitionEntry(out, name + " (delegated)", value,
					"delegated-property");
		}

		closeTable(out);
	}

	private void doSnapshot(ServletOutputStream out,
			ConnectionPoolDefinitionIF cpd, String link, String level,
			String connectionId) throws IOException, ProxoolException {
		boolean detail = (level != null) && (level.equals("more"));
		SnapshotIF snapshot = ProxoolFacade.getSnapshot(cpd.getAlias(), detail);

		if (snapshot != null) {
			openDataTable(out);

			printDefinitionEntry(out, "alias", cpd.getAlias(), "core-property");

			printDefinitionEntry(out, "Start date", DATE_FORMAT.format(snapshot
					.getDateStarted()), "snapshot");

			printDefinitionEntry(out, "Snapshot", TIME_FORMAT.format(snapshot
					.getSnapshotDate()), "snapshot");

			StringBuffer connectionsBuffer = new StringBuffer();
			connectionsBuffer.append(snapshot.getActiveConnectionCount());
			connectionsBuffer.append(" (active), ");
			connectionsBuffer.append(snapshot.getAvailableConnectionCount());
			connectionsBuffer.append(" (available), ");
			if (snapshot.getOfflineConnectionCount() > 0) {
				connectionsBuffer.append(snapshot.getOfflineConnectionCount());
				connectionsBuffer.append(" (offline), ");
			}
			connectionsBuffer.append(snapshot.getMaximumConnectionCount());
			connectionsBuffer.append(" (max)<br/>");
			String[] colours = { "ff9999", "66cc66", "cccccc" };
			int[] lengths = {
					snapshot.getActiveConnectionCount(),
					snapshot.getAvailableConnectionCount(),
					snapshot.getMaximumConnectionCount()
							- snapshot.getActiveConnectionCount()
							- snapshot.getAvailableConnectionCount() };

			drawBarChart(connectionsBuffer, colours, lengths);
			printDefinitionEntry(out, "Connections", connectionsBuffer
					.toString(), "snapshot");

			printDefinitionEntry(out, "Served", String.valueOf(snapshot
					.getServedCount()), "snapshot");

			printDefinitionEntry(out, "Refused", String.valueOf(snapshot
					.getRefusedCount()), "snapshot");

			if (!detail) {
				out.println("    <tr>");
				out
						.print("        <td colspan=\"2\" align=\"right\"><form action=\""
								+ link + "\" method=\"GET\">");
				out.print("<input type=\"hidden\" name=\"alias\" value=\""
						+ cpd.getAlias() + "\">");
				out
						.print("<input type=\"hidden\" name=\"tab\" value=\"snapshot\">");
				out
						.print("<input type=\"hidden\" name=\"detail\" value=\"more\">");
				out
						.print("<input type=\"submit\" value=\"More information&gt;\" style=\"BACKGROUND-IMAGE: url(" + contextPath + "/templets/lib/themes/default/button.gif);\" + class=button>");
				out.println("</form></td>");
				out.println("    </tr>");
			} else {
				out.println("    <tr>");
				out.print("      <th width=\"200\" valign=\"top\">");
				out.print("Details:<br>(click ID to drill down)");
				out.println("</th>");
				out.print("      <td>");

				doSnapshotDetails(out, cpd, snapshot, link, connectionId);

				out.println("</td>");
				out.println("    </tr>");

				if (connectionId != null) {
					long drillDownConnectionId = Long.valueOf(connectionId)
							.longValue();
					ConnectionInfoIF drillDownConnection = snapshot
							.getConnectionInfo(drillDownConnectionId);
					if (drillDownConnection != null) {
						out.println("    <tr>");
						out.print("      <th valign=\"top\">");
						out.print("Connection #" + connectionId);
						out.println("</td>");
						out.print("      <td>");

						doDrillDownConnection(out, drillDownConnection);

						out.println("</td>");
						out.println("    </tr>");
					}
				}

				out.println("    <tr>");
				out
						.print("        <td colspan=\"2\" align=\"right\"><form action=\""
								+ link + "\" method=\"GET\">");
				out.print("<input type=\"hidden\" name=\"alias\" value=\""
						+ cpd.getAlias() + "\">");
				out
						.print("<input type=\"hidden\" name=\"tab\" value=\"snapshot\">");
				out
						.print("<input type=\"hidden\" name=\"detail\" value=\"less\">");
				out
						.print("<input type=\"submit\" value=\"&lt; Less information\" style=\"BACKGROUND-IMAGE: url(" + contextPath + "/templets/lib/themes/default/button.gif);\" class=button>");
				out.println("</form></td>");
				out.println("    </tr>");
			}

			closeTable(out);
		}
	}

	private void doSnapshotDetails(ServletOutputStream out,
			ConnectionPoolDefinitionIF cpd, SnapshotIF snapshot, String link,
			String connectionId) throws IOException {
		long drillDownConnectionId = 0L;
		if (connectionId != null) {
			drillDownConnectionId = Long.valueOf(connectionId).longValue();
		}

		if ((snapshot.getConnectionInfos() != null)
				&& (snapshot.getConnectionInfos().length > 0)) {
			out
					.println("<table cellpadding=\"2\" cellspacing=\"0\" border=\"0\">");
			out.println("  <tbody>");

			out.print("<tr>");
			out.print("<td>#</td>");
			out.print("<td align=\"center\">born</td>");
			out.print("<td align=\"center\">last<br>start</td>");
			out.print("<td align=\"center\">lap<br>(ms)</td>");
			out.print("<td>&nbsp;thread</td>");
			out.print("</tr>");

			ConnectionInfoIF[] connectionInfos = snapshot.getConnectionInfos();
			for (int i = 0; i < connectionInfos.length; i++) {
				ConnectionInfoIF connectionInfo = connectionInfos[i];

				if (connectionInfo.getStatus() == 0)
					continue;
				out.print("<tr>");

				out.print("<td style=\"background-color: #");
				if (connectionInfo.getStatus() == 2)
					out.print("ffcccc");
				else if (connectionInfo.getStatus() == 1)
					out.print("ccffcc");
				else if (connectionInfo.getStatus() == 3) {
					out.print("ccccff");
				}
				out.print("\" style=\"");

				if (drillDownConnectionId == connectionInfo.getId()) {
					out.print("border: 1px solid black;");
					out.print("\">");
					out.print(connectionInfo.getId());
				} else {
					out.print("border: 1px solid transparent;");
					out.print("\"><a href=\"");
					out.print(link);
					out.print("?");
					out.print("alias");
					out.print("=");
					out.print(cpd.getAlias());
					out.print("&");
					out.print("tab");
					out.print("=");
					out.print("snapshot");
					out.print("&");
					out.print("detail");
					out.print("=");
					out.print("more");
					out.print("&");
					out.print("id");
					out.print("=");
					out.print(connectionInfo.getId());
					out.print("\">");
					out.print(connectionInfo.getId());
					out.print("</a>");
				}
				out.print("</td>");

				out.print("<td>&nbsp;");
				out.print(TIME_FORMAT.format(connectionInfo.getBirthDate()));
				out.print("</td>");

				out.print("<td>&nbsp;");
				out
						.print(connectionInfo.getTimeLastStartActive() > 0L ? TIME_FORMAT
								.format(new Date(connectionInfo
										.getTimeLastStartActive()))
								: "-");
				out.print("</td>");

				out.print("<td align=\"right\" class=\"");
				out.print(getStatusClass(connectionInfo));
				out.print("\">");
				String active = "&nbsp;";
				if (connectionInfo.getTimeLastStopActive() > 0L)
					active = String.valueOf((int) (connectionInfo
							.getTimeLastStopActive() - connectionInfo
							.getTimeLastStartActive()));
				else if (connectionInfo.getTimeLastStartActive() > 0L) {
					active = String.valueOf((int) (snapshot.getSnapshotDate()
							.getTime() - connectionInfo
							.getTimeLastStartActive()));
				}
				out.print(active);
				out.print("&nbsp;&nbsp;</td>");

				out.print("<td>&nbsp;");
				out
						.print(connectionInfo.getRequester() != null ? connectionInfo
								.getRequester()
								: "-");
				out.print("</td>");

				out.println("</tr>");
			}

			out.println("  </tbody>");
			out.println("</table>");
		} else {
			out.println("No connections yet");
		}
	}

	private static String getStatusClass(ConnectionInfoIF info) {
		try {
			return STATUS_CLASSES[info.getStatus()];
		} catch (ArrayIndexOutOfBoundsException e) {
			LOG.warn("Unknown status: " + info.getStatus());
		}
		return "unknown-" + info.getStatus();
	}

	private void doDrillDownConnection(ServletOutputStream out,
			ConnectionInfoIF drillDownConnection) throws IOException {
		String[] sqlCalls = drillDownConnection.getSqlCalls();
		for (int i = 0; (sqlCalls != null) && (i < sqlCalls.length); i++) {
			String sqlCall = sqlCalls[i];
			out.print("<div class=\"drill-down\">");
			out.print("sql = ");
			out.print(sqlCall);
			out.print("</div>");
		}

		out.print("<div class=\"drill-down\">");
		out.print("proxy = ");
		out.print(drillDownConnection.getProxyHashcode());
		out.print("</div>");

		out.print("<div class=\"drill-down\">");
		out.print("delegate = ");
		out.print(drillDownConnection.getDelegateHashcode());
		out.print("</div>");

		out.print("<div class=\"drill-down\">");
		out.print("url = ");
		out.print(drillDownConnection.getDelegateUrl());
		out.print("</div>");
	}

	private void openHtml(ServletOutputStream out) throws IOException {
		out.println("<html><header><title>Proxool Admin</title>");
		out.println("<style media=\"screen\">");
		out
				.println("div.version {font-weight: bold; font-size: 100%; margin-bottom: 8px;}\nh1 {font-weight: bold; font-size: 100%}\noption {padding: 2px 24px 2px 4px;}\ninput {margin: 0px 0px 4px 12px;}\ntable.data {font-size: 90%; border-collapse: collapse; border: 1px solid black;}\ntable.data th {background: #bddeff; width: 25em; text-align: left; padding-right: 8px; font-weight: normal; border: 1px solid black;}\ntable.data td {background: #ffffff; vertical-align: top; padding: 0px 2px 0px 2px; border: 1px solid black;}\ntd.null {background: yellow;}\ntd.available {color: black;}\ntd.active {color: red;}\ntd.offline {color: blue;}\ndiv.drill-down {}\nul {list-style: none; padding: 0px; margin: 0px; position: relative; font-size: 90%;}\nli {padding: 0px; margin: 0px 4px 0px 0px; display: inline; border: 1px solid black; border-width: 1px 1px 0px 1px;}\nli.active {background: #bddeff;}\nli.inactive {background: #eeeeee;}\nli.disabled {background: #dddddd; color: #999999; padding: 0px 4px 0px 4px;}\na.quiet {color: black; text-decoration: none; padding: 0px 4px 0px 4px; }\na.quiet:hover {background: white;}\n");

		out.println("</style>");

		if (this.cssFile != null) {
			out
					.println("<link rel=\"stylesheet\" media=\"screen\" type=\"text/css\" href=\""
							+ this.cssFile + "\"></script>");
		}
		out.println("</header><body>");
	}

	private void closeHtml(ServletOutputStream out) throws IOException {
		out.println("</body></html>");
	}

	private void openDataTable(ServletOutputStream out) throws IOException {
		out
				.println("<table cellpadding=\"2\" cellspacing=\"0\" border=\"1\" class=\"data\">");
		out.println("  <tbody>");
	}

	private void closeTable(ServletOutputStream out) throws IOException {
		out.println("  </tbody>");
		out.println("</table>");
		out.println("<br/>");
	}

	private void printDefinitionEntry(ServletOutputStream out, String name,
			String value, String type) throws IOException {
		out.println("    <tr>");
		out.print("      <th valign=\"top\">");
		out.print(name);
		out.println(":</th>");
		out.print("      <td class=\"" + type + "\"nowrap>");
		if ((value != null) && (!value.equals("null")))
			out.print(value);
		else {
			out.print("-");
		}
		out.print("</td>");
		out.println("    </tr>");
	}

	private void doList(ServletOutputStream out, String alias, String tab,
			String link) throws IOException {
		String[] aliases = ProxoolFacade.getAliases();

		if (aliases.length == 0) {
			out.println("<p>No pools have been registered.</p>");
		} else if (aliases.length != 1) {
			out.println("<form action=\"" + link
					+ "\" method=\"GET\" name=\"alias\">");
			out.println("<select name=\"alias\" size=\""
					+ Math.min(aliases.length, 5) + "\">");
			for (int i = 0; i < aliases.length; i++) {
				out.print("  <option value=\"");
				out.print(aliases[i]);
				out.print("\"");
				out.print(aliases[i].equals(alias) ? " selected" : "");
				out.print(">");
				out.print(aliases[i]);
				out.println("</option>");
			}
			out.println("</select>");
			out.println("<input name=\"tab\" value=\"" + tab
					+ "\" type=\"hidden\">");
			out.println("<input value=\"Show\" type=\"submit\">");
			out.println("</form>");
		}
	}

	private String formatMilliseconds(long time) {
		if (time > 2147483647L) {
			return time + "ms";
		}
		Calendar c = Calendar.getInstance();
		c.clear();
		c.add(14, (int) time);
		return TIME_FORMAT.format(c.getTime());
	}
}
