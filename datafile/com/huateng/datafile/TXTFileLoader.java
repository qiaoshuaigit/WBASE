package com.huateng.datafile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.vidageek.mirror.Mirror;

import om.util.OmUtils;

import com.huateng.datafile.FileFormat.Field;


public class TXTFileLoader implements IFileLoader {
	private int count = 0;
	private BigDecimal sum = BigDecimal.ZERO;
	private File txtFile = null; 
	private FileFormat fileFormat = null;
	private BufferedReader io = null;
	
	public TXTFileLoader(File txtFile, FileFormat fileFormat) throws Exception {
		this.txtFile = txtFile;
		this.fileFormat = fileFormat;
		sum.setScale(2, BigDecimal.ROUND_HALF_UP);
		io = new BufferedReader(new InputStreamReader(new FileInputStream(txtFile), fileFormat.getEncoding()));
	}
	
	public TXTFileLoader(InputStream is, FileFormat fileFormat) throws Exception {
		this.txtFile = null;
		this.fileFormat = fileFormat;
		sum.setScale(2, BigDecimal.ROUND_HALF_UP);
		io = new BufferedReader(new InputStreamReader(is, fileFormat.getEncoding()));
	}
	
	public int count() {
		count ++;
		return count;
	}
	
	public int getCount() {
		return count;
	}
	
	/**
	 * 跳过无效行数据读
	 * @return
	 * @throws Exception
	 */
	public boolean next() throws Exception {
		String line = io.readLine();
		if(null == line) {
			return false;
		}
		return true;
	}

	public boolean next(Object bean) throws Exception {
//		String line = io.readLine();
//		if(null == line) {
//			return false;
//		}
//		List<String> fields = lineSplit(line);
//		for(int i = 0; i < fileFormat.getFields().size(); i ++) {
//			Field field = fileFormat.getFields().get(i);
//			String value = OmUtils.trim(fields.get(field.getIndex()));
//			try {
//				fileFormat.getParser().parse(bean, field.getFieldName(), value, field.getLength(), field.getScale(), field.isRequire());
//				if(field.getFieldName().equals(fileFormat.getSumField())) {
//					sum((BigDecimal) Mirror.on(bean).get().field(field.getFieldName()));
//				}
//			} catch(Throwable e) {
//				throw new Exception("第" + (count+1) + "行,字段:[" + field.getFieldName() + "-" + field.getDesc() + "]值[" +
//					value + "]错误:" + (e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage()), e);
//			}
//		}
//		count();
//		return true;
		return next(bean,true);
	}
	public boolean next(Object bean,boolean checkLength) throws Exception {
		String line = io.readLine();
		if(null == line) {
			return false;
		}
		List<String> fields = lineSplit(line,checkLength);
		for(int i = 0; i < fileFormat.getFields().size(); i ++) {
			Field field = fileFormat.getFields().get(i);
			String value = OmUtils.trim(fields.get(field.getIndex()));
			try {
				fileFormat.getParser().parse(bean, field.getFieldName(), value, field.getLength(), field.getScale(), field.isRequire());
				if(field.getFieldName().equals(fileFormat.getSumField())) {
					sum((BigDecimal) Mirror.on(bean).get().field(field.getFieldName()));
				}
			} catch(Throwable e) {
				throw new Exception("第" + (count+1) + "行,字段:[" + field.getFieldName() + "-" + field.getDesc() + "]值[" +
					value + "]错误:" + (e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage()), e);
			}
		}
		count();
		return true;
	}
	
	public List<String> lineSplit(String line) throws Exception {
//		List<String> result = new ArrayList<String>();
//		if(fileFormat.getFieldSplit().equals("FIXEDLENGTH")) {
//			int offset = 0;
//			int length = 0;
//			int fieldsCount = fileFormat.getFields().size();
//			byte[] bytes = line.getBytes("GBK");
//			if(fileFormat.getTotLength() != bytes.length) {
//				throw new Exception("第" + (count + 1) + "行数据总长度错误,应该为" + fileFormat.getTotLength() + "实际为" + bytes.length);
//			}
//			for(int i = 0; i < fieldsCount; i ++) {
//				length = fileFormat.getFields().get(i).getLength();
//				result.add(new String(bytes, offset, length, "GBK"));
//				offset += length;
//			}
//		}
//		else {
//			result = OmUtils.split(line, fileFormat.getFieldSplit());
//		}
//		return result;
		return lineSplit(line,true);
	}
	
	public List<String> lineSplit(String line,boolean checkLength) throws Exception {
		List<String> result = new ArrayList<String>();
		if(fileFormat.getFieldSplit().equals("FIXEDLENGTH")) {
			int offset = 0;
			int length = 0;
			int fieldsCount = fileFormat.getFields().size();
			byte[] bytes = line.getBytes("GBK");
			if(checkLength){
				if(fileFormat.getTotLength() != bytes.length) {
					throw new Exception("第" + (count + 1) + "行数据总长度错误,应该为" + fileFormat.getTotLength() + "实际为" + bytes.length);
				}
			}
			for(int i = 0; i < fieldsCount; i ++) {
				length = fileFormat.getFields().get(i).getLength();
				result.add(new String(bytes, offset, length, "GBK"));
				offset += length;
			}
		}
		else {
			result = OmUtils.split(line, fileFormat.getFieldSplit());
		}
		return result;
	}

	public BigDecimal sum(BigDecimal value) {
		sum = sum.add(value);
		return sum;
	}
	
	public BigDecimal getSum() {
		sum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);
		return sum;
	}
	
	public String getSum(String format) {
		return String.format(format, sum);
	}

	public void close() {
		if(null != io) {
			try { io.close(); } catch(Exception e) {}
		}
	}
}
