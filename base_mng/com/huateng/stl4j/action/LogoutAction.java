package com.huateng.stl4j.action;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import om.util.OmUtils;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.huateng.ebank.business.common.service.CommonService;
import com.huateng.ebank.common.GlobalInfo;
import com.huateng.ebank.entity.Operator;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.tools.DateTool;

public class LogoutAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		String forward = "success";
		String MESSAGE_KEY = "ERRINFO_LIST";
		List<String> errorList = new ArrayList<String>();
		try {
			HttpSession httpSession = request.getSession();
			String oprNo = (String)httpSession.getAttribute("tlrNo");
			httpSession.removeAttribute(GlobalInfo.KEY_GLOBAL_INFO);
			Enumeration<String> e = httpSession.getAttributeNames();
			while(e.hasMoreElements()) {
				httpSession.removeAttribute(e.nextElement());
			}
			httpSession.invalidate();
			String loginType = "";
			CommonDAO dao = CommonDAO.getInstance();
			List paramList = dao.findByHQL("select po.paramValue from SystemParam po where po.paramId=? and magicId=?",
					new Object[]{"SYSINFO", "LOGINTYPE"});
			if(paramList.size() > 0) {
				loginType = OmUtils.trim((String) paramList.get(0));
			}
			request.setAttribute("loginType", loginType);
			request.setAttribute("logout", "logout");
			
			CommonService.getInstance().oprInOutLog(oprNo, "out");			
		} catch(Exception e) {
			forward = "error";
			errorList.add(e.getMessage());
			if(null != e.getCause()) {
				errorList.add(e.getCause().getMessage());
			}
			request.setAttribute(MESSAGE_KEY, errorList);
		}
		return mapping.findForward(forward);
	}
}
