package com.huateng.tools;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.helpers.CountingQuietWriter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * RollingFileAppender 按日期目录输出日志文件
 * 配置文件需在文件路径中预留日期位置yyyyMMdd
 * <param name="File" value="/home/eps/log/yyyyMMdd/esb.log" />
 * @author "xugang"
 */
public class OmRollingFileAppender extends RollingFileAppender {
	private String lastDateString = null;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	protected void subAppend(LoggingEvent event) {
		if(fileName != null) {
			String currDateString = sdf.format(new java.util.Date());
			if(null != lastDateString && !currDateString.equals(lastDateString)) {
				fileName = fileName.replaceAll(lastDateString, currDateString);
				lastDateString = currDateString;
				this.rollOver();
			}
		}
		if((fileName != null) && ((CountingQuietWriter) qw).getCount() >= maxFileSize) {
			this.rollOver();
		}
		super.subAppend(event);
	} 

	public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize) throws IOException {
		String currDateString = sdf.format(new java.util.Date());
		if(fileName.indexOf("yyyyMMdd") >= 0) {
			fileName = fileName.replaceAll("yyyyMMdd", currDateString);
			lastDateString = currDateString;
		}
		else if(null != lastDateString && !currDateString.equals(lastDateString)) {
			fileName = fileName.replaceAll(lastDateString, currDateString);
			lastDateString = currDateString;
		}
		super.setFile(fileName, append, this.bufferedIO, this.bufferSize);
		if(append) {
			File f = new File(fileName);
			((CountingQuietWriter) qw).setCount(f.length());
		}
	}
}
