package com.huateng.stl4j.common;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.common.ExceptionUtil;

public class ApplicationContextUtils {
	private static ApplicationContext _context = null;
	private static Logger logger = Logger.getLogger(ApplicationContextUtils.class);

	public static synchronized void init(String location) throws CommonException {
		try {
			if (null == _context) {
				String[] locations = StringUtils.tokenizeToStringArray(location, ",");
				if(logger.isInfoEnabled()) {
					for(int i = 0; i < locations.length; i ++ ) {
						logger.info("Loading spring config from files:" + locations[i].trim());
					}
				}
				_context = new ClassPathXmlApplicationContext(locations);
			}
		} catch(Exception ex) {
			ExceptionUtil.throwCommonException("获得系统配置信息(spring相关)时出现错误.",
					"errors.system.spring", ex);
		}
	}
	
	public static void setContext(ApplicationContext context) {
		ApplicationContextUtils._context = context;
	}
	
	public static ApplicationContext getContext() {
		return ApplicationContextUtils._context;
	}

	public static Object getBean(String beanName) {
		if (null == _context)
			throw new IllegalStateException("ApplicationContext没有被初始化.");
		return _context.getBean(beanName);
	}

	public synchronized static void close() {
		_context = null;
	}
}
