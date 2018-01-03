package com.huateng.stl4j.bean;

public class AccSystemDic {
	private String accsystemCode;
	private String accsystemName;
	private String accsystemInfo;
	
	public String getAccsystemCode() {
		return accsystemCode;
	}
	public void setAccsystemCode(String accsystemCode) {
		this.accsystemCode = accsystemCode;
	}
	public String getAccsystemName() {
		return accsystemName;
	}
	public void setAccsystemName(String accsystemName) {
		this.accsystemName = accsystemName;
	}
	public String getAccsystemInfo() {
		return accsystemCode + "-" + accsystemName;
	}
	public void setAccsystemInfo(String accsystemInfo) {
		//this.accsystemInfo = accsystemInfo;
	}
}
