package com.huateng.stl4j.bean;

import java.io.Serializable;

public class BranchClass implements Serializable {
	private static final long serialVersionUID = -5201413221200937263L;
	private String brhClass;
	private String brhClassName;
	
	public String getBrhClass() {
		return brhClass;
	}
	
	public void setBrhClass(String brhClass) {
		this.brhClass = brhClass;
	}

	public String getBrhClassName() {
		return brhClassName;
	}

	public void setBrhClassName(String brhClassName) {
		this.brhClassName = brhClassName;
	}
}
