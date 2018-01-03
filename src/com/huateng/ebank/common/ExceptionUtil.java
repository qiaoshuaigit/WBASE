package com.huateng.ebank.common;

public class ExceptionUtil {
	public static void throwCommonException(String msg, String key,
			Throwable t, Object[] objs) throws CommonException {
		CommonException ce = new CommonException(msg, t);
		ce.setKey(key);
		ce.setObjs(objs);
		throw ce;
	}

	public static void throwCommonException(String msg, String key, Throwable t)
			throws CommonException {
		CommonException ce = new CommonException(msg, t);
		ce.setKey(key);
		throw ce;
	}
	
	public static void throwCommonException(String msg, Throwable t)
			throws CommonException {
		CommonException ce = new CommonException(msg, t);
		throw ce;
	}

	public static void throwCommonException(String msg, String key)
			throws CommonException {
		CommonException ce = new CommonException(msg);
		ce.setKey(key);
		throw ce;
	}

	public static void throwCommonException(String key) throws CommonException {
		CommonException ce = new CommonException("异常中的key值为:" + key);
		ce.setKey(key);
		throw ce;
	}

	public static void throwCommonException(String msg, String key,
			Object[] objs) throws CommonException {
		CommonException ce = new CommonException(msg);
		ce.setKey(key);
		ce.setObjs(objs);
		throw ce;
	}
}
