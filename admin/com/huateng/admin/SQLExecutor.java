package com.huateng.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import com.huateng.stl4j.common.ApplicationContextUtils;

public class SQLExecutor {
	private static SQLExecutor singleton = null;
	private static DataSource dataSource = null;
	private int saveTransactionIsolation = -1;

	private int pageSize = 50;

	protected SQLExecutor() {
		dataSource = (DataSource) ApplicationContextUtils.getBean(SQLServlet.dataSourceBeanId);
	}

	public synchronized static SQLExecutor getInstance() {
		if(null == singleton) {
			singleton = new SQLExecutor();
		}
		return singleton;
	}

	public int update(Connection connection, String sql) throws Exception {
		int num = 0;
		PreparedStatement statement = null;
		boolean saveAutoCommit = connection.getAutoCommit();
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			num = statement.executeUpdate();
			connection.commit();
		} catch(Exception e) {
			connection.rollback();
			throw e;
		} finally {
			if(saveAutoCommit != connection.getAutoCommit()) {
				connection.setAutoCommit(saveAutoCommit);
			}
			if(null != statement) statement.close();
		}
		return num;
	}

	private void describeTable(Connection conn, String param, QueryResult queryResult) throws Exception {
		String tabName = "";
		String[] params = param.split(" |\t|\n");

		for(int i = 1; i < params.length; i ++) {
			if(params[i].trim().length() > 0) {
				tabName = params[i].trim();
			}
		}

		if(tabName.length() == 0) {
			throw new Exception("表名不能为空");
		}

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from " + tabName + " where 1=2");
			ResultSetMetaData metaData = rs.getMetaData();
			queryResult.getFieldNameList().clear();
			queryResult.getFieldNameList().add("ROW");
			queryResult.getFieldNameList().add("COLUMN_NAME");
			queryResult.getFieldNameList().add("DATA_TYPE");
			queryResult.getFieldNameList().add("COLUMN_SIZE");
			queryResult.getFieldNameList().add("COLUMN_DESC");
			queryResult.getFieldNameList().add("NULLABLE");
			
			for(int i = 0; i < metaData.getColumnCount(); i ++) {
				String[] stringArray = new String[6];
				stringArray[0] = String.valueOf(i + 1);
				stringArray[1] = metaData.getColumnLabel(i + 1);
				stringArray[2] = metaData.getColumnTypeName(i + 1);
				if(metaData.getColumnClassName(i + 1).equals("java.math.BigDecimal")) {
					stringArray[3] = String.valueOf(metaData.getPrecision(i + 1)) + "," + String.valueOf(metaData.getScale(i + 1));
				}
				else {
					stringArray[3] = String.valueOf(metaData.getPrecision(i + 1));
				}
				if(SQLServlet.dbType.equals("DB2")) {
					stringArray[4] = getColumnDescDB2(conn, tabName, stringArray[1]);
				}
				else {
					stringArray[4] = "";
				}
				stringArray[5] = ResultSetMetaData.columnNoNulls == metaData.isNullable(i + 1) ? " NOT NULL" : "";
				queryResult.getValueList().add(stringArray);
			}
		} finally {
			if(null != rs) rs.close();
			if(null != stmt) stmt.close();
		}
	}
	
	private String getColumnDescDB2(Connection conn, String tableName, String columnName) throws Exception {
		String result = "";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String sql = "select REMARKS from syscat.COLUMNS where tabschema='" + SQLServlet.dbSchema.toUpperCase() + "' and TABNAME='" + tableName.toUpperCase() + "' and COLNAME='" + columnName.toUpperCase() + "'";
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				result = rs.getString("REMARKS");
				if(null == result) {
					result = ""; 
				}
				result = result.trim();
			}
		} finally {
			if(null != rs) rs.close();
			if(null != stmt) stmt.close();
		}
		return result;
	}
 
	private void listTablesDB2(Connection conn, String param, QueryResult queryResult) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String sql = "select tabname from syscat.tables where tabschema='" + SQLServlet.dbSchema + "' and type='T'";
			if(null != param && param.trim().length() > 0) {
				param = param.trim();
				while(param.indexOf('*') >= 0) {
					param = param.replace('*', '%');
				}
				sql += (" and tabname like '" + param.toUpperCase() + "'");
			}
			sql += " order by tabname";
			rs = stmt.executeQuery(sql);
			queryResult.getFieldNameList().clear();
			queryResult.getFieldNameList().add("ROW");
			queryResult.getFieldNameList().add("TABLE_NAME");
			int index = 0;
			while(rs.next()) {
				index ++;
				String tabName = rs.getString("tabname").trim().toUpperCase();
				String[] stringArray = new String[2];
				stringArray[0] = String.valueOf(index);
				stringArray[1] = tabName;
				queryResult.getValueList().add(stringArray);
			}
		} finally {
			if(null != rs) rs.close();
			if(null != stmt) stmt.close();
		}
	}
	
	private void listTablesMYSQL(Connection conn, String param, QueryResult queryResult) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String sql = "SHOW TABLES FROM " + SQLServlet.dbSchema;
			String like = "";
			boolean left = false;
			boolean right = false;
			if(null != param && param.trim().length() > 0) {
				param = param.trim();
				char[] pc = param.toCharArray();
				for(int i = 0; i < pc.length; i ++) {
					char c = pc[i];
					if(c == '*' && i == 0) {
						left = true;
					}
					if(c == '*' && i == pc.length - 1) {
						right = true;
					}
					if(c != '*') {
						like += c;
					}
				}
			}
			rs = stmt.executeQuery(sql);
			queryResult.getFieldNameList().clear();
			queryResult.getFieldNameList().add("ROW");
			queryResult.getFieldNameList().add("TABLE_NAME");
			int index = 0;
			while(rs.next()) {
				index ++;
				String tabName = rs.getString(1).trim().toUpperCase();
				String[] stringArray = new String[2];
				stringArray[0] = String.valueOf(index);
				stringArray[1] = tabName;
				if(like.length() > 0) {
					if(left && !right) {
						if(tabName.endsWith(like.toUpperCase())) {
							queryResult.getValueList().add(stringArray);
						}
					}
					else if(!left && right) {
						if(tabName.startsWith(like.toUpperCase())) {
							queryResult.getValueList().add(stringArray);
						}
					}
					else if(left && right) {
						if(tabName.contains(like.toUpperCase())) {
							queryResult.getValueList().add(stringArray);
						}
					}
					else if(!left && !right) {
						if(tabName.equals(like.toUpperCase())) {
							queryResult.getValueList().add(stringArray);
						}
					}
				}
				else {
					queryResult.getValueList().add(stringArray);
				}
			}
		} finally {
			if(null != rs) rs.close();
			if(null != stmt) stmt.close();
		}
	}

	public QueryResult query(Connection connection, String sql, int pageno)
			throws Exception {
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			String lsql = sql.toLowerCase();
			QueryResult queryResult = new QueryResult();
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			if(lsql.matches("^list[ \t]+tables$") || lsql.matches("^list[ \t]+tables[ \t]+.*")) {
				if(SQLServlet.dbType.equals("DB2")) {
					listTablesDB2(connection, sql.substring(lsql.indexOf("tables") + 6), queryResult);
				}
				else if(SQLServlet.dbType.equals("MYSQL")) {
					listTablesMYSQL(connection, sql.substring(lsql.indexOf("tables") + 6), queryResult);
				}
				return queryResult;
			}
			if(lsql.matches("^desc.*")) {
				describeTable(connection, sql, queryResult);
				return queryResult;
			}
			resultSet = statement.executeQuery(sql);

			if(false == resultSet.absolute(pageno * pageSize + 1)) {
				return queryResult;
			}

			ResultSetMetaData metaData = resultSet.getMetaData();
			queryResult.getFieldNameList().add("ROW");
			for(int i = 0; i < metaData.getColumnCount(); i++) {
				queryResult.getFieldNameList().add(metaData.getColumnName(i + 1));
			}

			int count = 0;
			while(true) {
				count ++;
				if(count > pageSize)
					break;
				String[] stringArray = new String[metaData.getColumnCount() + 1];
				stringArray[0] = String.valueOf(count);
				for(int i = 0; i < metaData.getColumnCount(); i++) {
					switch(metaData.getColumnType(i + 1)) {
						case Types.ARRAY:
						case Types.BINARY:
						case Types.BIT:
						case Types.BLOB:
						case Types.CLOB:
						case Types.DATALINK:
						case Types.DISTINCT:
						case Types.JAVA_OBJECT:
						case Types.LONGVARBINARY:
						case Types.OTHER:
						case Types.STRUCT:
							stringArray[i] = "";
							break;
						default:
							Object value = resultSet.getObject(i + 1);
							stringArray[i + 1] = ("" + value).trim()
								.replaceAll("&",  "&amp;")
								.replaceAll("'",  "&apos;")
								.replaceAll("\"", "&quot;")
								.replaceAll("<",  "&lt;")
								.replaceAll("/>", "/&gt;");
							break;
					}
				}

				queryResult.getValueList().add(stringArray);
				if(!resultSet.next()) {
					break;
				}
			}

			return queryResult;
		} finally {
			if(null != resultSet) resultSet.close();
			if(null != statement) statement.close();
		}
	}

	public void closeConnection(Connection connection) {
		if(-1 != saveTransactionIsolation) {
			try {
				connection.setTransactionIsolation(saveTransactionIsolation);
			} catch(Exception e) {
			}
		}
		DataSourceUtils.releaseConnection(connection, dataSource);
	}

	public Connection getConnection() throws Exception {
		Connection connection = DataSourceUtils.getConnection(dataSource);
		saveTransactionIsolation = connection.getTransactionIsolation();
		connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		return connection;
	}
}
