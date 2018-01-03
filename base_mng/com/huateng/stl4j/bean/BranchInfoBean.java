package com.huateng.stl4j.bean;

import com.huateng.ebank.entity.BranchInfo;

public class BranchInfoBean extends BranchInfo {
	private static final long serialVersionUID = 9052514120530655433L;
	private String brhClassName;
	private String upBrhIdName;
	
	public String getBrhClassName() {
		return brhClassName;
	}
	
	public void setBrhClassName(String brhClassName) {
		this.brhClassName = brhClassName;
	}

	public String getUpBrhIdName() {
		return upBrhIdName;
	}

	public void setUpBrhIdName(String upBrhIdName) {
		this.upBrhIdName = upBrhIdName;
	}
}
