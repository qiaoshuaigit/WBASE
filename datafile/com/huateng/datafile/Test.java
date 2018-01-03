package com.huateng.datafile;

import java.io.File;
import java.math.BigDecimal;

import com.huateng.stl4j.common.ApplicationContextUtils;
import com.huateng.stl4j.common.MessageResources;
import com.thoughtworks.xstream.XStream;

public class Test {
	public static void main(String[] args) throws Exception {
		ApplicationContextUtils.init("classpath:resources/applicationContext.xml,classpath:resources/business/**/*.xml");
		MessageResources.getInstance().init("resources/errorcode.properties");
		File file = new File("D:/2.txt");
		FileFormat fileFormat = FileFormatFactory.getInstance().getFileFormat("HL_IMP_DATA");
		//导入
		TXTFileLoader fileLoader = new TXTFileLoader(file, fileFormat);
		try {
			TestBean bean = new TestBean();
			XStream xs = new XStream();
			while(fileLoader.next(bean)) {
				System.out.println(xs.toXML(bean));
			}
			
			System.out.println(fileLoader.getCount());
			System.out.println(fileLoader.getSum("%015.2f"));
		} finally {
			if(null != fileLoader) {
				fileLoader.close();
			}
		}
		
		//导出
//		TXTFileExporter fileExporter = new TXTFileExporter(file, fileFormat);
//		try {
//			TestBean bean = new TestBean();
//			BigDecimal value = new BigDecimal(12.23);
//			value.setScale(2, BigDecimal.ROUND_HALF_UP);
//			bean.setApplySeq("1");
//			bean.setApplyAmount(value);
//			fileExporter.writeLine(bean);
//			bean.setApplySeq("2");
//			bean.setApplyAmount(value);
//			fileExporter.writeLine(bean);
//			
//			System.out.println(fileExporter.getCount());
//			System.out.println(fileExporter.getSum().toPlainString());
//		} finally {
//			if(null != fileExporter) {
//				fileExporter.close();
//			}
//		}
	}
}
