package com.huateng.stl4j.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import om.util.OmUtils;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

import com.huateng.ebank.common.CommonException;

public class ActionExceptionHandler extends ExceptionHandler {
	private static Logger logger = Logger.getLogger(ActionExceptionHandler.class);
	
	public ActionForward execute(Exception ex, ExceptionConfig ae,
			ActionMapping mapping, ActionForm formInstance,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		try {
			super.execute(ex, ae, mapping, formInstance, request, response);
		} catch(Exception e) {
			throw new ServletException(e);
		}
		String msg = convertErrorMessage(ex);
		throw new ServletException(msg, ex);
	}
	
	protected void storeException(HttpServletRequest request, String property,
			ActionMessage error, ActionForward forward) {
		ActionMessages errors = (ActionMessages) request.getAttribute(Globals.ERROR_KEY);
		if(errors == null) {
			errors = new ActionMessages();
		}
		errors.add(property, error);
		request.setAttribute(Globals.ERROR_KEY, errors);
	}
	
	public static String convertErrorMessage(Exception ex) {
		//logger.error("异常", ex);
		String errorMsg = "";
		Throwable t = ex;
		while(t.getCause() != null) {
			t = t.getCause();
		}
		String detailMsg = t.getMessage();

		if(ex instanceof CommonException) {
			CommonException ce = (CommonException) ex;
			Object[] objs = ce.getObjs();
			String key = ce.getKey();

			if(OmUtils.trim(key) != "") {
				MessageResources mr = MessageResources.getInstance();
				errorMsg = mr.getMessage(key, objs);
				if(null == errorMsg) {
					errorMsg = "未知的错误代码定义: " + key;
				}
				else {
					errorMsg = "错误代码: " + key + "-" + errorMsg;
				}
			}
		}

		StringBuffer sb = new StringBuffer(errorMsg);
		if(null != detailMsg && !"".equals(detailMsg)) {
			if(detailMsg.indexOf("Exception") < 0) {
				if(detailMsg.indexOf("错误信息: ") < 0) {
					if(sb.length() > 0) {
						sb.append("\n");
					}
					sb.append("错误信息: ");
				}
				sb.append(detailMsg);
			}
		}
		
		String msg = sb.toString();
		if (msg.length() > 256) {
			msg = msg.substring(0, 256) + "......";
		}
		return msg;
	}
}
