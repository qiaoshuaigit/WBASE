package com.huateng.stl4j.common;

import org.hibernate.type.Type;

public class PageQueryConditionWithCount {
	private int pageSize;  /*每页的记录数*/
	private int pageIndex; /*需要查询第几页, 从0开始*/
	private String queryString;  /*查询条件*/
	private Object[] objArray;   /*查询变量*/
	private Type[] typeArray;    /*变量类型*/
	private String countHQL; /*统计HQL*/
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageIndex() {
		return pageIndex;
	}
	
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	public String getQueryString() {
		return queryString;
	}
	
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	
	public Object[] getObjArray() {
		return objArray;
	}
	
	public void setObjArray(Object[] objArray) {
		this.objArray = objArray;
	}
	
	public Type[] getTypeArray() {
		return typeArray;
	}
	
	public void setTypeArray(Type[] typeArray) {
		this.typeArray = typeArray;
	}

	public String getCountHQL() {
		return countHQL;
	}

	public void setCountHQL(String countHQL) {
		this.countHQL = countHQL;
	}
}
