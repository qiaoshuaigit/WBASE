package com.huateng.ebank.entity;


import java.io.Serializable;

/**
 * 参数列表
 * @author OM
 * @date 2013-06-19
 */
public class ParamList implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * 类名称
	 */
	public static final String REF="ParamList";

	/**
	 * 参数KEY 的属性名
	 */
	public static final String PROP_PARAMKEY="paramKey";

	/**
	 * 参数值 的属性名
	 */
	public static final String PROP_PARAMVALUE="paramValue";

	/**
	 * 参数KEY
	 */
	private java.lang.String paramKey;

	/**
	 * 参数值
	 */
	private java.lang.String paramValue;

	/**
	 * 获取 参数KEY 的属性值
	 * @return paramKey : 参数KEY
	 * @author OM
	 */
	public java.lang.String getParamKey(){
		return this.paramKey;
	}

	/**
	 * 设置 参数KEY 的属性值
	 * @param paramKey : 参数KEY
	 * @author OM
	 */
	public void setParamKey(java.lang.String paramKey){
		this.paramKey	= paramKey;
	}

	/**
	 * 获取 参数值 的属性值
	 * @return paramValue : 参数值
	 * @author OM
	 */
	public java.lang.String getParamValue(){
		return this.paramValue;
	}

	/**
	 * 设置 参数值 的属性值
	 * @param paramValue : 参数值
	 * @author OM
	 */
	public void setParamValue(java.lang.String paramValue){
		this.paramValue	= paramValue;
	}

	/**
	 * 转换为字符串
	 * @author OM
	 */
	public String toString(){
		return "{" + "paramKey(参数KEY)=" + paramKey + "," +"paramValue(参数值)=" + paramValue + "}";
	}
	/**
	 * 转换为 JSON 字符串
	 * @author OM
	 */
	public String toJson(){
		return "{" + "paramKey:'" + paramKey + "'," +"paramValue:'" + paramValue + "'}";
	}
}