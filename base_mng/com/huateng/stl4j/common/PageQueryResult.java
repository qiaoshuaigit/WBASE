package com.huateng.stl4j.common;

import java.util.ArrayList;
import java.util.List;

public class PageQueryResult {
	private int totalCount; /*总记录数*/
	private List queryResult; /*查询结果*/
	
	public PageQueryResult() {
		totalCount = 0;
		queryResult = new ArrayList();
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public List getQueryResult() {
		return queryResult;
	}
	
	public void setQueryResult(List queryResult) {
		this.queryResult = queryResult;
	}
	
	public int getPageCount(int pageSize){	    
		int pageCount = 0;
		if (totalCount % pageSize == 0) {
			pageCount = totalCount / pageSize;
		} else {
			pageCount = totalCount / pageSize + 1;
		}	
		return pageCount;
	}
}
