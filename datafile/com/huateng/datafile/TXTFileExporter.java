package com.huateng.datafile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;

import om.util.OmUtils;

import net.vidageek.mirror.Mirror;

import com.huateng.datafile.FileFormat.Field;

public class TXTFileExporter implements IFileExporter {
	private int count = 0;
	private BigDecimal sum = BigDecimal.ZERO;
	private File txtFile = null; 
	private FileFormat fileFormat = null;
	private PrintWriter out = null;
	
	public TXTFileExporter(File txtFile, FileFormat fileFormat) throws Exception {
		this.txtFile = txtFile;
		this.fileFormat = fileFormat;
		sum.setScale(2, BigDecimal.ROUND_HALF_UP);
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(txtFile), fileFormat.getEncoding())));
	}
	
	public void writeLine(Object bean) throws Exception {
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < fileFormat.getFields().size(); i ++) {
			Field field = fileFormat.getFields().get(i);
			Class fieldClass = FileFormatFactory.pa.getGetter(bean.getClass(), field.getFieldName()).getReturnType();
			String value = "";
			if(fieldClass.equals(String.class)) {
				String fieldValue = OmUtils.trim((String) Mirror.on(bean).get().field(field.getFieldName()));
				byte[] fieldBytes = fieldValue.getBytes("GBK");
				if(fieldBytes.length > field.getLength()) {
					fieldValue = new String(fieldBytes, 0, field.getLength(), "GBK");
					value = String.format(field.fprintFormat(fieldClass), fieldValue);
				}
				else {
					value = fieldValue + getBlankSpace(field.getLength() - fieldBytes.length);
				}
				
			}
			else {
				value = String.format(field.fprintFormat(fieldClass), Mirror.on(bean).get().field(field.getFieldName()));
			}
			result.append(value);
			if(!fileFormat.getFieldSplit().equals("FIXEDLENGTH")) {
				result.append(fileFormat.getFieldSplit());
			}
			if(field.getFieldName().equals(fileFormat.getSumField())) {
				sum((BigDecimal) Mirror.on(bean).get().field(field.getFieldName()));
			}
		}
		out.println(result.toString());
		count();
	}
	
	private String getBlankSpace(int length) {
		if(length > 0) {
			return String.format("%" + length + "s", " ");
		}
		else {
			return "";
		}
	}

	public void close() {
		if(null != out) {
			try { out.close(); } catch(Exception e) {}
		}
	}

	public int count() {
		count ++;
		return count;
	}

	public BigDecimal sum(BigDecimal value) {
		sum = sum.add(value);
		return sum;
	}

	public int getCount() {
		return count;
	}

	public BigDecimal getSum() {
		sum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);
		return sum;
	}

	public String getSum(String format) {
		return String.format(format, sum);
	}
}
