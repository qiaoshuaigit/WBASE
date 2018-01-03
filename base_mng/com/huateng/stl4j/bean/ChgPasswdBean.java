package com.huateng.stl4j.bean;

public class ChgPasswdBean {
	private Integer id;
	private String oprNo;
	private String userName;
	private String oldPasswd;
	private String newPasswd1;
	private String newPasswd2;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getOprNo() {
		return oprNo;
	}
	
	public void setOprNo(String oprNo) {
		this.oprNo = oprNo;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getOldPasswd() {
		return oldPasswd;
	}
	
	public void setOldPasswd(String oldPasswd) {
		this.oldPasswd = oldPasswd;
	}
	
	public String getNewPasswd1() {
		return newPasswd1;
	}
	
	public void setNewPasswd1(String newPasswd1) {
		this.newPasswd1 = newPasswd1;
	}
	
	public String getNewPasswd2() {
		return newPasswd2;
	}
	
	public void setNewPasswd2(String newPasswd2) {
		this.newPasswd2 = newPasswd2;
	}
}
