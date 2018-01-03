package com.huateng.stl4j.getter;

import java.sql.Connection;
import java.sql.ResultSet;
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

public class TableListGetter extends BaseGetter {
	public Result call() throws AppException {
		try {
			List<TableListBean> list = new ArrayList<TableListBean>();
			StringBuilder sql = new StringBuilder();
			String tabname = OmUtils.trim(this.getCommQueryServletRequest().getParameter("iTabName"));
			sql.append("select TABNAME,REMARKS from syscat.tables where 1=1 ");
			sql.append("and tabschema='EPS' ");
			sql.append("and tabname not like 'ADVISE%' and tabname not like 'EXPLAIN%' ");
			sql.append("and tabname not like 'TOPBPM%' and TYPE='T' ");
			if(!OmUtils.isEmpty(tabname)) {
				sql.append("and tabname like '%").append(tabname).append("%' ");
			}
			sql.append("order by tabname");
			
			DataSource dataSource = CommonDAO.getInstance().getDataSource();
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				conn = DataSourceUtils.getConnection(dataSource);
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql.toString());
				while(rs.next()) {
					TableListBean bean = new TableListBean();
					bean.setTabName(OmUtils.trim(rs.getString("TABNAME")));
					bean.setTabDesc(OmUtils.trim(rs.getString("REMARKS")));
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
}
