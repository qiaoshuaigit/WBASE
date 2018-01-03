package com.huateng.stl4j.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.huateng.services.FileDownloadService;

public class FileDownloadAction extends BaseAction {
	private static Logger logger = Logger.getLogger(FileDownloadAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		super.init(request);
		String id = request.getParameter("id");
		try {
			String bussType = request.getParameter("bussType");
			String primaryKey = request.getParameter("primaryKey");
			FileDownloadService.processDownloadFile(bussType, response, primaryKey);
		} catch(Exception e) {
			logger.error("下载出错", e);
			//response.reset();
			request.setAttribute("ERROR_MESSAGE", e.getMessage());
			String forward = "/pages/frame/download.jsp?id=" + id;
			request.getSession().getServletContext().getRequestDispatcher(forward).forward(request, response);
		}
		//return mapping.findForward("success");
		return null;
	}
}
