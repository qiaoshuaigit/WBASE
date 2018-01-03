package com.huateng.ebank.framework.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.huateng.common.err.Module;
import com.huateng.commquery.config.bean.base.ICommonQueryBaseBean;
import com.huateng.ebank.common.ErrorCode;
import com.huateng.ebank.common.GlobalInfo;
import com.huateng.exception.AppException;
import com.huateng.mvc.servlet.HTFreemarkerServlet;

import freemarker.template.Template;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class BankHTFreemarkServlet extends HTFreemarkerServlet {
	private static Logger logger = Logger.getLogger(BankHTFreemarkServlet.class);
	private static final long serialVersionUID = 57989966371044664L;

	@Override
	public void init() throws ServletException {
		super.init();
	}

	/**
	 * Called before the execution is passed to template.process(). This is a
	 * generic hook you might use in subclasses to perform a specific action
	 * before the template is processed. By default does nothing. A typical
	 * action to perform here is to inject application-specific objects into the
	 * model root
	 * 
	 * @param request
	 *            the actual HTTP request
	 * @param response
	 *            the actual HTTP response
	 * @param template
	 *            the template that will get executed
	 * @param data
	 *            the data that will be passed to the template
	 * @return true to process the template, false to suppress template
	 *         processing.
	 */
	@Override
	protected boolean preTemplateProcess(HttpServletRequest request,
			HttpServletResponse response, Template template, TemplateModel data)
			throws ServletException, IOException {
		try {
			HttpSession httpSession = request.getSession();
			if (httpSession == null) {
				throw new AppException(Module.SYSTEM_MODULE,
						ErrorCode.ERROR_CODE_TLRNO_SESSION_INVALID, null, null);
			}
			GlobalInfo globalInfo = (GlobalInfo) httpSession
					.getAttribute(GlobalInfo.KEY_GLOBAL_INFO);
			if (null != globalInfo) {
				GlobalInfo.setCurrentInstance(globalInfo);
				String sessionId = httpSession.getId();
				globalInfo.setSessionId(sessionId);
			}
			
			if (request.getServletPath().contains("/exportExcel.ftl")) {
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment;filename=exportExcel.xls");
				getConfiguration().setSharedVariable("cqMethod", new TemplateMethodModel(){
					public Object exec(List args) throws TemplateModelException {
						String method = (String)args.get(0);
						String value = (String)args.get(1);
						int index = method.lastIndexOf(".");
						String className = method.substring(0,index);
						String methodName= method.substring(index+1);
						try {
							Class clazz = Class.forName(className);
							Method m = clazz.getMethod(methodName, ICommonQueryBaseBean.class, String.class, ServletRequest.class);
							return m.invoke(clazz.newInstance(), null,value,null);
						} catch (Exception e) {
							logger.error("TemplateMethodModel cqMethod error!", e);
							return "";
						}
					}
				});
			}

			return super.preTemplateProcess(request, response, template, data);
		} catch (AppException asEx) {
			logger.error("preTemplateProcess error!", asEx);
			throw new ServletException(asEx);
		}
	}
}
