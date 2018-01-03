package com.huateng.services;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

/**
 * 文件上传处理类, 需增加bussType定义列表和uploadByBussType???方法 <li>001: 测试1 <li>002: 测试2
 * 
 * @author "xugang"
 */
public class FileUploadService {
	private static Logger logger = Logger.getLogger(FileUploadService.class);

	public static void processUploadedFile(String fileName, InputStream is,
			String primaryKey, String bussType) throws Exception {
		logger.info("已接收到上传文件: " + fileName + ",primaryKey=" + primaryKey
				+ ",bussType=" + bussType);
		Method method = null;
		try {
			method = FileUploadService.class.getMethod("uploadByBussType"
					+ bussType, String.class, InputStream.class, String.class);
		} catch (Exception e) {
			throw new Exception("未定义bussType=" + bussType + "的上传文件处理方法", e);
		}
		try {
			method.invoke(null, fileName, is, primaryKey);
		} catch (InvocationTargetException ite) {
			throw (Exception) ite.getTargetException();
		}
	}

	/**
	 * 不同bussType的处理方法： uploadByBussType + bussType的值
	 * 
	 * @param fileName
	 * @param is
	 * @param primaryKey
	 * @throws Exception
	 */
	public static void uploadByBussType(String fileName, InputStream is,
			String primaryKey) throws Exception {
		if (true) {
			throw new Exception("处理测试文件出错");
		}
	}
}
