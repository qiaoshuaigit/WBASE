package com.huateng.admin;

import java.util.ArrayList;
import java.util.List;

public class QueryResult {
	private int recordNo = 0;
	private List<String> fieldNameList = new ArrayList<String>();
	private List<String[]> valueList = new ArrayList<String[]>();

	public List<String> getFieldNameList() {
		return fieldNameList;
	}

	public void setFieldNameList(List<String> fieldNameList) {
		this.fieldNameList = fieldNameList;
	}

	public int getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(int recordNo) {
		this.recordNo = recordNo;
	}

	public List<String[]> getValueList() {
		return valueList;
	}

	public void setValueList(List<String[]> valueList) {
		this.valueList = valueList;
	}
}
