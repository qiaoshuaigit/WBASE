package com.huateng.stl4j.common;

public abstract class BaseOperation {
	public abstract void beforeProc(OperationContext context) throws Exception ;

	public abstract void execute(OperationContext context) throws Exception ;

	public abstract void afterProc(OperationContext context) throws Exception;
}
