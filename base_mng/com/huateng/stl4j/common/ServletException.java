package com.huateng.stl4j.common;

public class ServletException extends javax.servlet.ServletException {
	private static final long serialVersionUID = 6141091758183707058L;

	public ServletException(Exception e) {
		super(e);
	}
	
	public ServletException(String msg, Exception e) {
		super(msg, e);
	}
	
	public String toString() {
		return this.getMessage();
	}
}
