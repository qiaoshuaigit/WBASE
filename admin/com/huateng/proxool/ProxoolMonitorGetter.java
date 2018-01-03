package com.huateng.proxool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.logicalcobwebs.proxool.ConnectionInfoIF;
import org.logicalcobwebs.proxool.ConnectionPoolDefinitionIF;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.admin.SnapshotIF;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;

public class ProxoolMonitorGetter extends BaseGetter {
	private static Logger logger = Logger.getLogger(ProxoolMonitorGetter.class);
	private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final String[] STATUS_CLASSES = { "null", "available", "active", "offline" };
	
	public Result call() throws AppException {
		try {
			List<ProxollBean> list = getProxollMonitorList();
			ResultMng.fillResultByList(getCommonQueryBean(), getCommQueryServletRequest(),
					list, getResult());
			result.setContent(list);
			result.getPage().setTotalPage(1);
			result.init();
			return result;
		} catch (CommonException e) {
			logger.error(e.getClass().getSimpleName(), e);
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, e.getMessage());
		} catch (AppException appEx) {
			logger.error(appEx.getClass().getSimpleName(), appEx);
			throw appEx;
		} catch(Exception ex) {
			logger.error(ex.getClass().getSimpleName(), ex);
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}
	}
	
	private static String formatMilliseconds(long time) {
		if (time > 2147483647L) {
			return time + "ms";
		}
		Calendar c = Calendar.getInstance();
		c.clear();
		c.add(14, (int) time);
		return TIME_FORMAT.format(c.getTime());
	}
	
	private static String getStatusClass(ConnectionInfoIF info) {
		try {
			return STATUS_CLASSES[info.getStatus()];
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return "unknown-" + info.getStatus();
	}
	
	public List<ProxollBean> getProxollMonitorList() throws Exception {
		List<ProxollBean> list = new ArrayList<ProxollBean>();
		String[] aliases = ProxoolFacade.getAliases();
		if(aliases.length < 1) return list;
		String alias = aliases[0];
		ConnectionPoolDefinitionIF def = ProxoolFacade.getConnectionPoolDefinition(alias);
		//list.add(new ProxollBean("alias", def.getAlias()));
		//list.add(new ProxollBean("driver-url", def.getUrl()));
		//list.add(new ProxollBean("driver-class", def.getDriver()));
		SnapshotIF snapshot = ProxoolFacade.getSnapshot(def.getAlias(), false);
		if (snapshot != null) {
			list.add(new ProxollBean("Start date", DATE_FORMAT.format(snapshot.getDateStarted())));
			list.add(new ProxollBean("Snapshot", TIME_FORMAT.format(snapshot.getSnapshotDate())));
			list.add(new ProxollBean("Active Connection", String.valueOf(snapshot.getActiveConnectionCount())));
			list.add(new ProxollBean("Available Connection", String.valueOf(snapshot.getAvailableConnectionCount())));
			list.add(new ProxollBean("Offline Connection", String.valueOf(snapshot.getOfflineConnectionCount())));
			list.add(new ProxollBean("Maximum Connection", String.valueOf(snapshot.getMaximumConnectionCount())));
			list.add(new ProxollBean("Served", String.valueOf(snapshot.getServedCount())));
			list.add(new ProxollBean("Refused", String.valueOf(snapshot.getRefusedCount())));
		}
		list.add(new ProxollBean("minimum-connection-count", String.valueOf(def.getMinimumConnectionCount())));
		list.add(new ProxollBean("maximum-connection-count", String.valueOf(def.getMaximumConnectionCount())));
		list.add(new ProxollBean("prototype-count", def.getPrototypeCount() > 0 ? String.valueOf(def.getPrototypeCount()) : null));
		list.add(new ProxollBean("simultaneous-build-throttle", String.valueOf(def.getSimultaneousBuildThrottle())));
		list.add(new ProxollBean("maximum-connection-lifetime", formatMilliseconds(def.getMaximumConnectionLifetime())));
		list.add(new ProxollBean("maximum-active-time", formatMilliseconds(def.getMaximumActiveTime())));
		list.add(new ProxollBean("house-keeping-sleep-time", def.getHouseKeepingSleepTime() / 1000L + "s"));
		list.add(new ProxollBean("house-keeping-test-sql", def.getHouseKeepingTestSql()));
		list.add(new ProxollBean("test-before-use", String.valueOf(def.isTestBeforeUse())));
		list.add(new ProxollBean("test-after-use", String.valueOf(def.isTestAfterUse())));
		list.add(new ProxollBean("recently-started-threshold", formatMilliseconds(def.getRecentlyStartedThreshold())));
		list.add(new ProxollBean("overload-without-refusal-lifetime", formatMilliseconds(def.getOverloadWithoutRefusalLifetime())));
		list.add(new ProxollBean("injectable-connection-interface", String.valueOf(def.getInjectableConnectionInterface())));
		list.add(new ProxollBean("injectable-statement-interface", String.valueOf(def.getInjectableStatementInterface())));
		list.add(new ProxollBean("injectable-callable-statement-interface", String.valueOf(def.getInjectableCallableStatementInterface())));
		list.add(new ProxollBean("injectable-prepared-statement-interface", String.valueOf(def.getInjectablePreparedStatementInterface())));
		String fatalSqlExceptions = null;
		if ((def.getFatalSqlExceptions() != null)
				&& (def.getFatalSqlExceptions().size() > 0)) {
			StringBuffer fatalSqlExceptionsBuffer = new StringBuffer();
			Iterator i = def.getFatalSqlExceptions().iterator();
			while (i.hasNext()) {
				String s = (String) i.next();
				fatalSqlExceptionsBuffer.append(s);
				fatalSqlExceptionsBuffer.append(i.hasNext() ? ", " : "");
			}
			fatalSqlExceptions = fatalSqlExceptionsBuffer.toString();
		}
		list.add(new ProxollBean("fatal-sql-exception", fatalSqlExceptions));
		list.add(new ProxollBean("fatal-sql-exception-wrapper-class", def.getFatalSqlExceptionWrapper()));
		list.add(new ProxollBean("statistics", def.getStatistics()));
		list.add(new ProxollBean("statistics-log-level", def.getStatisticsLogLevel()));
		list.add(new ProxollBean("verbose", String.valueOf(def.isVerbose())));
		list.add(new ProxollBean("trace", String.valueOf(def.isTrace())));
		/**
		Properties p = def.getDelegateProperties();
		Iterator i = p.keySet().iterator();
		while (i.hasNext()) {
			String name = (String) i.next();
			String value = p.getProperty(name);
			if ((name.toLowerCase().indexOf("password") > -1)
					|| (name.toLowerCase().indexOf("passwd") > -1)) {
				value = "******";
			}
			list.add(new ProxollBean(name, value));
		}*/
		
		return list;
	}
}
