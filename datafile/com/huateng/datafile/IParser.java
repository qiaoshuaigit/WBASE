package com.huateng.datafile;

public interface IParser {
	public void parse(Object bean, String fieldName, String value, int length, int scale, boolean require) throws Exception;
}
