package com.huateng.datafile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FileFormat {
	private String name = null;
	/**
	 * TXT格式使用：字符集
	 */
	private String encoding = null;
	/**
	 * TXT格式使用：分隔符
	 */
	private String fieldSplit = null;
	private IParser parser = null;
	/**
	 * XLS格式使用：数据开始行数
	 */
	private int dataStartLine = 0;
	/**
	 * XLS格式使用：列名所在行数
	 */
	private int headLine = 0;
	private List<Field> fields = new ArrayList<Field>();
	/**
	 * 累加字段名
	 */
	private String sumField = null;
	
	/**
	 * 每行总长度
	 */
	private int totLength = 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getFieldSplit() {
		return fieldSplit;
	}

	public void setFieldSplit(String fieldSplit) {
		this.fieldSplit = fieldSplit;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public static class Field {
		private int index;
		private String fieldName;
		private int length;
		private int scale;
		private String desc;
		private boolean require = false;
		
		public Field(int index, String fieldName, int length, String desc, int scale) {
			this.index = index;
			this.fieldName = fieldName;
			this.length = length;
			this.desc = desc;
			this.scale = scale;
		}
		
		public String fprintFormat(Class clazz) throws Exception {
			if(clazz.equals(String.class)) {
				return "%-" + length + "s";
			}
			if(clazz.equals(Float.class)) {
				return "%0" + length + "." + scale + "f";
			}
			if(clazz.equals(Double.class)) {
				return "%0" + length + "." + scale + "f";
			}
			if(clazz.equals(BigDecimal.class)) {
				return "%0" + length + "." + scale + "f";
			}
			if(clazz.equals(Short.class)) {
				return "%0" + length + "d";
			}
			if(clazz.equals(Integer.class)) {
				return "%0" + length + "d";
			}
			if(clazz.equals(Long.class)) {
				return "%0" + length + "d";
			}
			throw new Exception("未实现的输出格式: " + clazz.getName());
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}

		public int getLength() {
			return length;
		}

		public void setLength(int length) {
			this.length = length;
		}

		public int getScale() {
			return scale;
		}

		public void setScale(int scale) {
			this.scale = scale;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public boolean isRequire() {
			return require;
		}

		public void setRequire(boolean require) {
			this.require = require;
		}
	}
	
	public IParser getParser() {
		return parser;
	}

	public FileFormat withParser(IParser parser) {
		this.parser = parser;
		return this;
	}
	
	public int getDataStartLine() {
		return dataStartLine;
	}

	public void setDataStartLine(int dataStartLine) {
		this.dataStartLine = dataStartLine;
	}

	public int getHeadLine() {
		return headLine;
	}

	public void setHeadLine(int headLine) {
		this.headLine = headLine;
	}

	public String getSumField() {
		return sumField;
	}

	public void setSumField(String sumField) {
		this.sumField = sumField;
	}

	public int getTotLength() {
		return totLength;
	}

	public void setTotLength(int totLength) {
		this.totLength = totLength;
	}
}
