package com.huateng.exception;

import om.util.OmUtils;

/**
 * @Override DomainException.toString()
 * @author gang.xu
 * @date 2010-12-28
 */
public class DomainException extends HuatengException {
	private static final long serialVersionUID = 6490674043687262565L;
	protected String moduleName;
	protected String errCode;
	protected String errorPage;
	protected String errMessage;

	public DomainException() {
		
	}
	
	public DomainException(String message) {
		super(message);
	}
	
	public DomainException(String message, Throwable t) {
		super(message, t);
	}
	
	public DomainException(String moduleName, String errCode) {
		 this(moduleName, errCode, null, null);
	}
	
	public DomainException(String moduleName, String message, Exception exception) {
		this(moduleName, null, message, exception);
	}
	
	public DomainException(String moduleName, String errCode, String message) {
		this(moduleName, errCode, message, null);
	}
	
	public DomainException(String moduleName, String errCode, String message, String errorPage, Exception exception) {
		this(moduleName, errCode, message, exception);
		this.errorPage = errorPage;
	}
	
	public DomainException(String moduleName, String errCode, String message, Exception exception) {
		super(message, exception);
		errMessage = message;
		this.moduleName = moduleName;
		this.errCode = errCode;
	}
	
	public String getErrCode() {
		return errCode;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	
	public String toString() {
		StringBuilder value = new StringBuilder();
		if(!OmUtils.isEmpty(moduleName) && !OmUtils.isEmpty(errCode)) {
			value.append("[").append(moduleName).append("-").append(errCode).append("]: ");
		}
		value.append(super.toString().replaceAll("\n", ""));
		return value.toString();
	}
	
	public String getErrorPage() {
		return errorPage;
	}
	
	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}
}
