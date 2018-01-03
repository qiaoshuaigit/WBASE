package com.huateng.stl4j.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public class LoginForm extends ValidatorForm {
	private static final long serialVersionUID = 1L;
	private String userName = null;
	private String password = null;
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		userName = null;
		password = null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			 HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		return (errors);
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
