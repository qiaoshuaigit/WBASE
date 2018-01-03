package com.huateng.report;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huateng.stl4j.common.ApplicationContextUtils;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.MessageResources;
import com.huateng.tools.DateTool;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class ReportService {
	private static final String JASPER_RESOURCES_PATH = "resources/jasper/";
	public static final String EXCEL = "EXCEL";
	public static final String HTML = "HTML";
	public static final String RTF = "RTF";
	public static final String CSV = "CSV";
	public static final String PDF = "PDF";
	
	/**
	 * 根据List dataList生成报表
	 * @param fillTemplet
	 * @param reportFilePath
	 * @param reportBaseFileName
	 * @param dataList
	 * @param params
	 * @param reportType
	 * @return
	 * @throws Exception
	 */
	public static String createReport(String fillTemplet, String reportFilePath, String reportBaseFileName,
			List dataList, Map params, String reportType) throws Exception {
		JasperReport fillTemp = loadReportTemplate(fillTemplet);
		JasperPrint jasperPrint = JasperFillManager.fillReport(fillTemp, params, new ReportDataSource(dataList));
		
		return createReport(reportFilePath, reportBaseFileName, jasperPrint, reportType);
	}
	
	/**
	 * 根据ResultSet rs生成报表
	 * @param fillTemplet
	 * @param reportFilePath
	 * @param reportBaseFileName
	 * @param rs
	 * @param params
	 * @param reportType
	 * @return
	 * @throws Exception
	 */
	public static String createReport(String fillTemplet, String reportFilePath, String reportBaseFileName,
			ResultSet rs, Map params, String reportType) throws Exception {
		JasperReport fillTemp = loadReportTemplate(fillTemplet);
		JasperPrint jasperPrint = JasperFillManager.fillReport(fillTemp, params, new JRResultSetDataSource(rs));
		
		return createReport(reportFilePath, reportBaseFileName, jasperPrint, reportType);
	}
	
	private static String createReport(String reportFilePath, String reportBaseFileName,
			JasperPrint jasperPrint, String reportType) throws Exception {
		String reportFileName = null;
		if(EXCEL.equalsIgnoreCase(reportType)) {
			reportFileName = getReportFileName(reportFilePath, reportBaseFileName, ".xls");
			buildExcelReport(jasperPrint, reportFileName);
		}
		else if(HTML.equalsIgnoreCase(reportType)) {
			reportFileName = getReportFileName(reportFilePath, reportBaseFileName, ".html");
			buildHtmlReport(jasperPrint, reportFileName);
		}
		else if(RTF.equalsIgnoreCase(reportType)) {
			reportFileName = getReportFileName(reportFilePath, reportBaseFileName, ".rtf");
			buildRTFReport(jasperPrint, reportFileName);
		}
		else if(PDF.equalsIgnoreCase(reportType)) {
			reportFileName = getReportFileName(reportFilePath, reportBaseFileName, ".pdf");
			buildPDFReport(jasperPrint, reportFileName);
		}
		else if(CSV.equalsIgnoreCase(reportType)) {
			reportFileName = getReportFileName(reportFilePath, reportBaseFileName, ".csv");
			buildCSVReport(jasperPrint, reportFileName);
		}
		return reportFileName;
	}
	
	private static String getReportFileName(String reportFilePath, String reportBaseFileName, String extName) {
		StringBuilder result = new StringBuilder(reportFilePath);
		if(!reportFilePath.endsWith("/") && !reportFilePath.endsWith("\\")) {
			result.append("/");
		}
		result.append(DateTool.getDate());
		result.append("/");
		makeReportFilePath(result.toString());
		result.append(reportBaseFileName);
		result.append("_");
		result.append(String.format("%06d", CommonDAO.getInstance().getNextValueOfSequences("SEQ_FILEID")));
		result.append(extName);
		return result.toString();
	}
	
	private static void buildExcelReport(JasperPrint jasperPrint, String targetName) throws Exception {
		try {
			JRXlsExporter jrxe = new JRXlsExporter();
			jrxe.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			jrxe.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, targetName);
			jrxe.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
			jrxe.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			jrxe.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			jrxe.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			jrxe.exportReport();
		} catch(Exception e) {
			throw new Exception("生成Excel报表出错", e);
		}
	}
	
	private static void buildHtmlReport(JasperPrint jasperPrint, String targetName) throws Exception {
		try {
			JRHtmlExporter exporter = new JRHtmlExporter();
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, targetName);
	        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);
	        exporter.exportReport();
		} catch(Exception e) {
			throw new Exception("生成HTML报表出错", e);
		}
	}
	
	private static void buildRTFReport(JasperPrint jasperPrint, String targetName) throws Exception {
		try {
			JRRtfExporter exporter = new JRRtfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, targetName);
			exporter.exportReport();
		} catch(Exception e) {
			throw new Exception("生RTF报表出错", e);
		}
	}
	
	private static void buildCSVReport(JasperPrint jasperPrint, String targetName) throws Exception {
		try {
			JRCsvExporter exporter = new JRCsvExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, targetName);
			exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "GBK");
			exporter.exportReport();
		} catch(Exception e) {
			throw new Exception("生CSV报表出错", e);
		}
	}
	
	private static void buildPDFReport(JasperPrint jasperPrint, String targetName) throws Exception {
		try {
			JasperExportManager.exportReportToPdfFile(jasperPrint, targetName);
		} catch(Exception e) {
			throw new Exception("生成PDF报表出错", e);
		}
	}
	
	private static synchronized void makeReportFilePath(String reportFilePath) {
		File file = new File(reportFilePath);
		if(!file.exists()) {
			file.mkdirs();
		}
	}
	
	private static JasperReport loadReportTemplate(String fillTemplet) throws Exception {
		String filePath = JASPER_RESOURCES_PATH + fillTemplet + ".jasper";
		InputStream is = ReportService.class.getClassLoader().getResourceAsStream(filePath);
		return (JasperReport) JRLoader.loadObject(is);
	}
	
	public static void main(String[] args) throws Exception {
		List dataList = new ArrayList();
		Map params = new HashMap();
		params.put("vbrno", "0001");
		params.put("vbrname", "测试");
		params.put("vquerydate", "20130123");
//		for(int i = 1; i < 30; i ++) {
//			HashMap dataMap = new HashMap();
//			dataMap.put("num", i);
//			dataMap.put("brname", String.format("%06d", i) + "-测试");
//			dataMap.put("A1", 1D);
//			dataMap.put("A2", 2D);
//			dataMap.put("A3", 3D);
//			dataMap.put("A4", 4D);
//			dataMap.put("A5", 5D);
//			dataMap.put("A6", 6D);
//			dataMap.put("A7", 7D);
//			dataMap.put("A8", 8D);
//			dataMap.put("A9", 9D);
//			dataMap.put("A10", 10D);
//			dataMap.put("A11", 11D);
//			dataMap.put("A12", 12D);
//			dataMap.put("A13", 13D);
//			dataMap.put("A14", 14D);
//			dataMap.put("A15", 15D);
//			dataMap.put("A16", 16D);
//			dataMap.put("A17", 17D);
//			dataMap.put("A18", 18D);
//			dataMap.put("A19", 19D);
//			dataMap.put("A20", 20D);
//			dataMap.put("A21", 21D);
//			dataMap.put("A22", 22D);
//			dataMap.put("A23", 23D);
//			dataMap.put("A24", 24D);
//
//			dataList.add(dataMap);
//		}
//		ReportService.createReport("T0001", "C:/temp", "T0001", dataList, params, ReportService.EXCEL);
		ApplicationContextUtils.init("classpath:resources/applicationContext.xml,classpath:resources/business/**/*.xml");
		MessageResources.getInstance().init("resources/errorcode.properties");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = CommonDAO.getInstance().getDataSource().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select 1 as num, '名称' as brname, 1 as A1, 2 as A2, 3 as A3, 4 as A4, 5 as A5, 6 as A6" +
					", 7 as A7, 8 as A8, 9 as A9, 10 as A10, 11 as A11, 12 as A12, 13 as A13, 14 as A14, 15 as A15, 16 as A16" +
					", 17 as A17, 18 as A18, 19 as A19, 20 as A20, 21 as A21, 22 as A22, 23 as A23, 24 as A24 from SYSTEM_INFO");
			ReportService.createReport("T0001", "C:/temp", "T0001", rs, params, ReportService.EXCEL);
		} finally {
			if(null != rs) rs.close();
			if(null != stmt) stmt.close();
			if(null != conn) conn.close();
		}
	}
}
