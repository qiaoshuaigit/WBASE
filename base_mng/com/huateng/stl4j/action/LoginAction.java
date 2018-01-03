package com.huateng.stl4j.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import om.util.OmUtils;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.huateng.ebank.business.common.service.CommonService;
import com.huateng.ebank.common.GlobalInfo;
import com.huateng.ebank.entity.BranchInfo;
import com.huateng.ebank.entity.Operator;
import com.huateng.ebank.entity.SystemInfo;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.CryptoUtils;
import com.huateng.stl4j.form.LoginForm;
import com.huateng.stl4j.service.BranchInfoService;


public class LoginAction extends Action {
	private static Logger logger = Logger.getLogger(LoginAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		String forward = "success";
		String MESSAGE_KEY = "ERROR_MESSAGE";
		CommonDAO dao = CommonDAO.getInstance();
		
		String loginSwitch ="";
		List loginSwithcList = dao.findByHQL("select po.paramValue from SystemParam po where po.paramId=? and magicId=?",
				new Object[]{"SYSINFO", "LOGINSWITCH"});
		if(loginSwithcList.size() > 0) {
			loginSwitch = OmUtils.trim((String) loginSwithcList.get(0));
		}
		
		if("false".equals(loginSwitch)){
			List<String> errList = new ArrayList<String>();
			forward = "error";
			errList.add("当前系统正在维护中，禁止登录！");
			request.setAttribute("ERRINFO_LIST", errList);
			return mapping.findForward(forward);
		}
		
		try {
			forward = "success";
			request.removeAttribute(MESSAGE_KEY);
			LoginForm loginForm = (LoginForm)form;
			loginProcess(request, loginForm);
		} catch(Exception e) {
			forward = "failed";
			request.setAttribute(MESSAGE_KEY, e.getMessage());
			logger.error("登录失败", e);
		}
		return mapping.findForward(forward);
	}
	
	private void loginProcess(HttpServletRequest request, LoginForm loginForm) throws Exception {
		GlobalInfo g = new GlobalInfo();
		
		HttpSession httpSession = request.getSession();
		if(null == httpSession) {
			throw new Exception("用户没有登录！");
		}
		
		String userName = OmUtils.trim(loginForm.getUserName());
		String password = OmUtils.trim(loginForm.getPassword());
		if(OmUtils.isEmpty(userName) || OmUtils.isEmpty(password)) {
			throw new Exception("用户名和密码不能为空！");
		}
		
		CommonDAO dao = CommonDAO.getInstance();
		Operator opr = null;
		List list = dao.findByWhere(Operator.class, "po.oprNo=?", new Object[] {userName});
		if(null != list && list.size() > 0) {
			opr = (Operator) list.get(0);
		}
		if(null == opr) {
			throw new Exception("操作员[" + userName + "]不存在！");
		}
		if("0".equals(opr.getStatus())){
			throw new Exception("操作员[" + userName + "]无效！");
		}
		String pwmd5 = CryptoUtils.MD5(password);
		if(opr.getPassword() != null && opr.getPassword().equals(pwmd5)) {
			SystemInfo sysInfo = null;
			list = dao.findByWhere(SystemInfo.class, "po.id=?", new Object[] {new Integer(1)});
			if(null != list && list.size() > 0) {
				sysInfo = (SystemInfo) list.get(0);
			}
			if(null != sysInfo) {
				request.getSession().setAttribute("sysDate", OmUtils.formatDateTime(sysInfo.getSysDate()));
				g.setTxdate(OmUtils.stringToDate(sysInfo.getSysDate()));
				g.setSysDate(sysInfo.getSysDate());
			}
			else {
				request.getSession().setAttribute("sysDate", "");
			}
			
			list = dao.findByWhere(BranchInfo.class, "status='1' and po.brhNo=?", new Object[]{opr.getBrhNo()});
			if(list==null || list.size()==0){
				throw new Exception("操作员[" + userName + "]所属机构无效！");
			}
			BranchInfo brhInfo = null;
			list = dao.findByWhere(BranchInfo.class, "po.brhNo=?", new Object[]{opr.getBrhNo()});
			if(null != list && list.size() > 0) {
				brhInfo = (BranchInfo) list.get(0);
			}
			else {
				throw new Exception("操作员[" + userName + "]所属机构不存在！");
			}
			CommonService.getInstance().oprInOutLog(opr.getOprNo(), "in");
			
			g.setContextPath(request.getContextPath());
			g.setSessionId(httpSession.getId());
			g.setUserId(opr.getId());
			g.setUserName(opr.getUserName());
			g.setOprNo(opr.getOprNo());
			g.setBrhId(brhInfo.getId());
			g.setBranchNo(opr.getBrhNo());
			g.setBrhName(brhInfo.getBrhName());
			g.setTopBrhId(BranchInfoService.getInstance().getTopBrhId());
			GlobalInfo.setCurrentInstance(g);
			GlobalInfo.setGlobalInfo2HttpSession(httpSession, g);
			request.getSession().setAttribute("tlrId", opr.getId());
			request.getSession().setAttribute("tlrName", opr.getUserName());
			request.getSession().setAttribute("tlrNo",   opr.getOprNo());
			request.getSession().setAttribute("brhName", brhInfo.getBrhName());
		}
		else {
			throw new Exception("操作员[" + userName + "]密码不正确！");
		}
	}
}
