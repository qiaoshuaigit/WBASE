package com.huateng.stl4j.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import om.exception.IMessageGenerator;
import om.param.IReloadable;
import om.param.ReloadService;
import om.util.OmUtils;

import org.apache.commons.collections.map.LRUMap;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.huateng.stl4j.common.ApplicationContextUtils;

public class ErrorCodeService implements IMessageGenerator,IReloadable {
	private static Logger logger = Logger.getLogger(ErrorCodeService.class);
	private static LRUMap loadedMap = new LRUMap(200);
	private DataSource dataSource;

	public static ErrorCodeService getInstance() {
		return (ErrorCodeService) ApplicationContextUtils.getBean(ErrorCodeService.class.getName());
	}
	
	private ErrorCodeService() {
		ReloadService.getInstance().registReloader("ERRORCODE", this);
	}

	private static final String sql = "select ERROR_MESSAGE from ERROR_CODE " +
									  "where SYS_NAME='%s' and ERROR_CODE='%s'";
	public String code2Message(String sysName, String errorCode) {
		String message = "";
		String mapKey = sysName + "," + errorCode;
		if(loadedMap.containsKey(mapKey)) {
			return (String) loadedMap.get(mapKey);
		}
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DataSourceUtils.getConnection(dataSource);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(String.format(sql, sysName, errorCode));
			if(rs.next()) {
				message = OmUtils.trim(rs.getString("ERROR_MESSAGE"));
				loadedMap.put(mapKey, message);
			}
			else if(logger.isDebugEnabled()) {
				logger.debug("No ERROR_CODE(" + sysName + "," + errorCode + ")");
			}
		} catch(Exception e) {
			logger.error("code2Message(" + sysName + "," + errorCode + ") error", e);
		} finally {
			if(null != conn) {
				DataSourceUtils.releaseConnection(conn, dataSource);
			}
		}
		return message;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void reload() throws Exception {
		loadedMap.clear();
		logger.info("loadedMap.clear()");
	}
}
