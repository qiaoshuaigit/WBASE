package com.huateng.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import om.util.OmUtils;

import org.apache.commons.collections.map.LRUMap;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.huateng.ebank.business.common.DataDicUtils;
import com.huateng.ebank.common.GlobalInfo;
import com.huateng.ebank.common.SystemConstant;
import com.huateng.report.ReportService;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.entity.TblAtmStat;
import com.huateng.stl4j.service.BranchInfoService;
import com.huateng.stl4j.service.SystemParamService;

public class FileDownloadService {
	private static Logger logger = Logger.getLogger(FileDownloadService.class);
	private static LRUMap appnoMap = new LRUMap(50);

	private static void readFileToOutputStream(File file, OutputStream os,
			boolean deleteSrcFileFlag) throws Exception {
		int readed = 0;
		byte[] readBuffer = new byte[4096];
		FileInputStream is = null;
		try {
			is = new FileInputStream(file);
			while ((readed = is.read(readBuffer)) > 0) {
				os.write(readBuffer, 0, readed);
			}
		} finally {
			if (null != is)
				is.close();
		}
		if (deleteSrcFileFlag) {
			file.delete();
		}
	}

	private static void downloader(String fileName,
			HttpServletResponse response, boolean deleteSrcFileFlag)
			throws Exception {
		File file = new File(fileName);
		logger.info("下载文件为: " + fileName);
		String contentType = "application/x-msdownload";
		response.reset();
		response.setContentType(contentType);
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ new String(file.getName().getBytes("GBK"), "ISO-8859-1")
				+ "\"");
		response.setContentLength((int) file.length());
		OutputStream os = new BufferedOutputStream(response.getOutputStream());
		readFileToOutputStream(file, os, deleteSrcFileFlag);
		os.flush();
		os.close();
	}

	private static void downloader(String fileName, byte[] content,
			HttpServletResponse response) throws Exception {
		String contentType = "application/x-msdownload";
		response.reset();
		response.setContentType(contentType);
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ new String(fileName.getBytes("GBK"), "ISO-8859-1") + "\"");
		response.setContentLength(content.length);
		OutputStream os = new BufferedOutputStream(response.getOutputStream());
		os.write(content);
		os.flush();
		os.close();
	}

	public static void processDownloadFile(String bussType,
			HttpServletResponse response, String primaryKey) throws Exception {
		logger.info("已接收到下载文件请求: primaryKey=" + primaryKey + ",bussType="
				+ bussType);
		Method method = null;
		try {
			method = FileDownloadService.class.getMethod("downloadByBussType"
					+ bussType, String.class, HttpServletResponse.class,
					String.class);
		} catch (Exception e) {
			throw new Exception("未定义bussType=" + bussType + "的下载文件处理方法", e);
		}
		try {
			method.invoke(null, bussType, response, primaryKey);
		} catch (InvocationTargetException ite) {
			throw (Exception) ite.getTargetException();
		}
		logger.info("成功处理下载文件请求: primaryKey=" + primaryKey + ",bussType="
				+ bussType);
	}

	/**
	 * 测试
	 * @param bussType
	 * @param response
	 * @param primaryKey
	 * @throws Exception
	 */
	public static void downloadByBussType001(String bussType,
			HttpServletResponse response, String primaryKey) throws Exception {
		List dataList = new ArrayList();
		Map params = new HashMap();
		params.put("vbrno", "0001");
		params.put("vbrname", "测试");
		params.put("vquerydate", "20130123");
		for (int i = 1; i < 30; i++) {
			HashMap dataMap = new HashMap();
			dataMap.put("num", i);
			dataMap.put("brname", String.format("%06d", i) + "-测试");
			dataMap.put("A1", 1D);
			dataMap.put("A2", 2D);
			dataMap.put("A3", 3D);
			dataMap.put("A4", 4D);
			dataMap.put("A5", 5D);
			dataMap.put("A6", 6D);
			dataMap.put("A7", 7D);
			dataMap.put("A8", 8D);
			dataMap.put("A9", 9D);
			dataMap.put("A10", 10D);
			dataMap.put("A11", 11D);
			dataMap.put("A12", 12D);
			dataMap.put("A13", 13D);
			dataMap.put("A14", 14D);
			dataMap.put("A15", 15D);
			dataMap.put("A16", 16D);
			dataMap.put("A17", 17D);
			dataMap.put("A18", 18D);
			dataMap.put("A19", 19D);
			dataMap.put("A20", 20D);
			dataMap.put("A21", 21D);
			dataMap.put("A22", 22D);
			dataMap.put("A23", 23D);
			dataMap.put("A24", 24D);

			dataList.add(dataMap);
		}
		String filePath = SystemParamService.getInstance().getParamValue(
				"FILEPATH", "REPORT");
		String fileName = ReportService.createReport("T0001", filePath,
				"T0001", dataList, params, ReportService.EXCEL);
		downloader(fileName, response, false);
	}
	
	
	/**
	 * 自助终端报表
	 * @param bussType
	 * @param response
	 * @param primaryKey
	 * @throws Exception
	 */
	public static void downloadByBussType002(String bussType,
			HttpServletResponse response, String primaryKey) throws Exception {
		GlobalInfo gi = GlobalInfo.getCurrentInstance();
		
		List<Map<String,?>> dataList = new ArrayList<Map<String,?>>();
		Map<String,String> params = new HashMap<String,String>();
		params.put("vbrno", gi.getBranchNo());
		params.put("vbrname", gi.getBrhName());
		params.put("vquerydate", OmUtils.getLocalDateTime("yyyyMMdd"));
		CommonDAO dao = CommonDAO.getInstance();
		DataSource dataSource = dao.getDataSource();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int rowNum = 1;
		
		try {
			Map<String,String> primaryMap = getPrimaryMap(primaryKey);
			StringBuilder sql = new StringBuilder();
			sql.append("select po.TERMINAL_CODE from TBL_ATM_STAT po where 1=1");
			String brhNo = (String)primaryMap.get("brhNo");
			if(OmUtils.isEmpty(brhNo)){
				brhNo =  gi.getBranchNo();
			}
			if(!brhNo.equals(SystemConstant.MAIN_BRH_NO)){
				Integer brhId = BranchInfoService.getInstance().getBrhId(brhNo);
				sql.append(" and (exists(select sub.id from W_SUPER_BRANCH_LIST sub where");
				sql.append(" sub.BRH_NO=po.BRNO and (sub.UP_BRH_ID1=").append(brhId).append(" or");
				sql.append(" sub.UP_BRH_ID2=").append(brhId).append(" or");
				sql.append(" sub.UP_BRH_ID3=").append(brhId).append(" or");
				sql.append(" sub.UP_BRH_ID4=").append(brhId).append("))");
				sql.append(" or po.BRNO='").append(brhNo).append("')");
			}
			if(!OmUtils.isEmpty((String)primaryMap.get("termStWhere"))){
				sql.append(" and TERM_ST='").append((String)primaryMap.get("termStWhere")).append("'");
			}
			sql.append(" order by po.BRNO,po.CUP_ORGAN,po.TERMINAL_CODE");
			conn = DataSourceUtils.getConnection(dataSource);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql.toString());
			
			while(rs.next()) {
				String id = rs.getString("TERMINAL_CODE");
				TblAtmStat tas = (TblAtmStat) dao.get(TblAtmStat.class, id);
				
				@SuppressWarnings("rawtypes")
				HashMap<String, Comparable> dataMap = new HashMap<String, Comparable>();
				dataMap.put("num", rowNum);
				dataMap.put("brname", tas.getBrno() + "-"+BranchInfoService.getInstance().getBrhName(tas.getBrno()));
				dataMap.put("A1", id);
				dataMap.put("A2", tas.getTermType());
				dataMap.put("A3", tas.getAddress());
				dataMap.put("A4", tas.getCommAddr());
				dataMap.put("A5", tas.getTermVendorNm());
				dataMap.put("A6", tas.getStartDate());
				dataMap.put("A7", DataDicUtils.getDicName("100002", tas.getCupOrgan()));//银联机构号
				dataMap.put("A8", tas.getIpAddress());//ATM机具IP地址
				dataMap.put("A9", getStatu(tas.getTermSt(),"termSt"));//状态
				dataMap.put("A10", getStatu(tas.getChkStatus(),"chkSt"));//审核状态
				dataMap.put("A11", 1);
				
				dataList.add(dataMap);
				rowNum = rowNum + 1;
			}
		} finally {
			if(null != rs)      try { rs.close(); }      catch(Exception e) { }
			if(null != stmt)    try { stmt.close(); }    catch(Exception e) { }
			if(null != conn)    DataSourceUtils.releaseConnection(conn, dataSource);
		}
		
		
		String filePath = SystemParamService.getInstance().getParamValue(
				"FILEPATH", "REPORT");
		String fileName = ReportService.createReport("T0002", filePath,
				"T0002", dataList, params, ReportService.EXCEL);
		downloader(fileName, response, false);
	}
	
	private static String getStatu(String key,String type){
		if("termSt".equals(type)){
			Map<String,String> termStMap = new HashMap<String,String>();
			termStMap.put("1", "1-停用");
			termStMap.put("2", "2-启用");
			termStMap.put("3", "3-新增");
			termStMap.put("4", "4-修改");
			return termStMap.get(key);
		}
		if("chkSt".equals(type)){
			Map<String,String> chkStMap = new HashMap<String,String>();
			chkStMap.put("1", "1-停用待审核");
			chkStMap.put("2", "2-启用待审核");
			chkStMap.put("3", "3-新增待审核");
			chkStMap.put("4", "4-修改待审核");
			chkStMap.put("5", "5-停用审核通过");
			
			chkStMap.put("6", "6-启用审核通过");
			chkStMap.put("7", "7-新增审核通过");
			chkStMap.put("8", "8-修改审核通过");
			chkStMap.put("9", "9-停用审核拒绝");
			chkStMap.put("0", "10-启用审核拒绝");
			
			chkStMap.put("11", "11-新增审核拒绝");
			chkStMap.put("12", "12-修改审核拒绝");
			return chkStMap.get(key);
		}
		return "";
	}
	
	private static Map<String,String> getPrimaryMap(String primary){
		Map<String,String> map = new HashMap<String,String>();
		String[]  sa = primary.split(",");
		for(String d:sa){
			if(!OmUtils.isEmpty(d)){
				String[] sc = d.split("=");
				map.put(sc[0], sc[1]);
			}
		}
		return map;
	}
}
