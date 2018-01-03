package com.huateng.stl4j.getter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import om.util.OmUtils;

import org.springframework.jdbc.datasource.DataSourceUtils;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.stl4j.bean.TableListBean;
import com.huateng.stl4j.common.CommonDAO;

public class TableDefineInfoGetter extends BaseGetter {
	public Result call() throws AppException {
		try {
			List<TableListBean> list = new ArrayList<TableListBean>();
			String tabname = OmUtils.trim(this.getCommQueryServletRequest().getParameter("iTabName"));
			DataSource dataSource = CommonDAO.getInstance().getDataSource();
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				conn = DataSourceUtils.getConnection(dataSource);
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select * from " + tabname + " where 1=2");
				ResultSetMetaData metaData = rs.getMetaData();
				for(int i = 0; i < metaData.getColumnCount(); i ++) {
					TableListBean bean = new TableListBean();
					bean.setSeqNo(i + 1);
					bean.setColName(metaData.getColumnLabel(i + 1));
					bean.setDataType(metaData.getColumnTypeName(i + 1));
					if(metaData.getColumnClassName(i + 1).equals("java.math.BigDecimal")) {
						bean.setColSize(String.valueOf(metaData.getPrecision(i + 1)) + "," + String.valueOf(metaData.getScale(i + 1)));
					}
					else {
						bean.setColSize(String.valueOf(metaData.getPrecision(i + 1)));
					}
					bean.setColDesc(getColumnDescDB2(conn, tabname, bean.getColName()));
					bean.setNullAble(ResultSetMetaData.columnNoNulls == metaData.isNullable(i + 1) ? " NOT NULL" : "");
					list.add(bean);
				}
			} finally {
				if(null != rs)      try { rs.close(); }      catch(Exception e) { }
				if(null != stmt)    try { stmt.close(); }    catch(Exception e) { }
				if(null != conn)    DataSourceUtils.releaseConnection(conn, dataSource);
			}
			ResultMng.fillResultByList(getCommonQueryBean(), getCommQueryServletRequest(),
					list, getResult());
			result.setContent(list);
			result.getPage().setTotalPage(1);
			result.init();
			return result;
		} catch(Exception ex) {
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}
	}
	
	private String getColumnDescDB2(Connection conn, String tableName, String columnName) throws Exception {
		String result = "";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String sql = "select REMARKS from syscat.COLUMNS where tabschema='EPS' and TABNAME='" + tableName.toUpperCase() + "' and COLNAME='" + columnName.toUpperCase() + "'";
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
}
