package com.huateng.stl4j.common;

import org.apache.log4j.Logger;
import org.hibernate.cache.Timestamper;

public class OPCaller {
	private static final Logger logger = Logger.getLogger(OPCaller.class);
	public void callOperation(BaseOperation operation, OperationContext context) throws Exception {
		operation.beforeProc(context);
		operation.execute(context);
		operation.afterProc(context);
	}

	public static void call(String beanName, OperationContext context) throws Exception {
		OPCaller caller = (OPCaller) ApplicationContextUtils.getBean("operationCaller");
		BaseOperation operation = (BaseOperation) ApplicationContextUtils.getBean(beanName);
		String ts = String.valueOf(Timestamper.next());
		logger.info("Begin call operation(" + beanName + ")-" + ts);
		logger.info("OperationContext-" + ts + "=" + context);
		try {
			caller.callOperation(operation, context);
		} finally {
			logger.info("End call operation(" + beanName + ")-" + ts);
		}
	}
}
