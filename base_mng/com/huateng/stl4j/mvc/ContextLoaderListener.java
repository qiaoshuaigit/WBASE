package com.huateng.stl4j.mvc;

import java.math.BigDecimal;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import om.exception.OmException;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huateng.common.Constants;
import com.huateng.ebank.business.common.DataDicUtils;
import com.huateng.stl4j.common.ApplicationContextUtils;
import com.huateng.stl4j.common.MessageResources;
import com.huateng.stl4j.service.ErrorCodeService;

public class ContextLoaderListener implements ServletContextListener {
	private static Logger logger = Logger.getLogger(ContextLoaderListener.class);
	private static String ERROR_MESSAGE_LOCATION_PARAM = "errorMessage";
	private static String SESSION_BETWEEN_SESSION_TIME = "sessionBetweenSessionTime";
	private final static BigDecimal BIGDECIMAL_ZERO = new BigDecimal(0);
	
	public ContextLoaderListener() {
		super();
	}
	
	public void contextDestroyed(ServletContextEvent arg0) {
		ApplicationContextUtils.close();
	}

	public void contextInitialized(ServletContextEvent event) {
		logger.info("sessionBetweenSessionTime = " + event.getServletContext().getInitParameter(SESSION_BETWEEN_SESSION_TIME));
		System.setProperty(SESSION_BETWEEN_SESSION_TIME, event.getServletContext().getInitParameter(SESSION_BETWEEN_SESSION_TIME));
		String errorMessageLocation = event.getServletContext().getInitParameter(ERROR_MESSAGE_LOCATION_PARAM);
		if(null == errorMessageLocation) {
			logger.error("没有配置错误消息资源: errorMessage");
			System.exit(-1);
		}
		try {
			MessageResources.getInstance().init(errorMessageLocation);
			ServletContext context = event.getServletContext();
			Constants.rootPath = context.getRealPath(Constants.SLASH);
			Constants.contextPath = context.getContextPath();
			ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
			ApplicationContextUtils.setContext(ctx);
			om.util.ContextUtils.setContext(ctx);
			com.huateng.util.ContextUtil.setContext(ctx);
			converBeanUtils();
			DataDicUtils.initDataDic();
			OmException.setMessageGenerator(ErrorCodeService.getInstance());
		} catch (Exception ce) {
			logger.error("初始化不成功,系统退出!", ce);
			System.exit(-1);
		}
	}
	
	protected void converBeanUtils() {
		BigDecimalConverter bd = new BigDecimalConverter(BIGDECIMAL_ZERO);
		ConvertUtils.register(bd, java.math.BigDecimal.class);
	}
}
