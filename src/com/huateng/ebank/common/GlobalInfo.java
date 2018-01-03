package com.huateng.ebank.common;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class GlobalInfo {
	private static Logger logger = Logger.getLogger(GlobalInfo.class);
	private static ThreadLocal<GlobalInfo> session = new ThreadLocal<GlobalInfo>();
	public static String KEY_GLOBAL_INFO = "GLOBAL_INFO";
	private String contextPath = "";
	private String sessionId = "";
	private Integer userId = 0;
	private Integer brhId = 0;
	private Integer topBrhId = 0;
	private String oprNo;
	private String userName;
	private String branchNo;
	private String brhName;

	 
	public String getBrhName() {
		return brhName;
	}

	public void setBrhName(String brhName) {
		this.brhName = brhName;
	}

	private Date txdate;
	private String sysDate;
	private String language = "zh";
	private long taskId = 0;
	private String assignedOprid = null;

	public static void setGlobalInfo2HttpSession(HttpSession httpSession,
			GlobalInfo globalInfo) {
		httpSession.setAttribute(GlobalInfo.KEY_GLOBAL_INFO, globalInfo);
		globalInfo.setSessionId(httpSession.getId());
	}

	public static GlobalInfo getCurrentInstance() throws CommonException {
		GlobalInfo gi = (GlobalInfo) session.get();

		if (null == gi) {
			ExceptionUtil.throwCommonException("没有找到GlobalInfo",
					ErrorCode.ERROR_CODE_NO_GLOBALINFO_INSTANCE);
		}

		return gi;
	}

	public static GlobalInfo getFromRequest(HttpServletRequest request)
			throws CommonException {
		HttpSession httpSession = request.getSession(false);
		GlobalInfo globalInfo = (GlobalInfo) httpSession
				.getAttribute(GlobalInfo.KEY_GLOBAL_INFO);
		if (null != globalInfo) {
			setCurrentInstance(globalInfo);
			String oldSessionId = globalInfo.getSessionId();
			String sessionId = httpSession.getId();
			if (!sessionId.equals(oldSessionId)) {
				ExceptionUtil.throwCommonException("全局信息不符合, 请重新登录.",
						ErrorCode.ERROR_CODE_TLRNO_SESSION_BINDED);
			}
			globalInfo.setSessionId(sessionId);
		}
		return globalInfo;
	}

	public static GlobalInfo getCurrentInstanceWithoutException() {
		GlobalInfo gi = (GlobalInfo) session.get();
		return gi;
	}

	public static void setCurrentInstance(GlobalInfo gi) {
		session.set(gi);
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getOprNo() {
		return oprNo;
	}

	public void setOprNo(String oprNo) {
		this.oprNo = oprNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public Integer getTopBrhId() {
		return topBrhId;
	}

	public void setTopBrhId(Integer topBrhId) {
		this.topBrhId = topBrhId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getTxdate() {
		return txdate;
	}

	public void setTxdate(Date txdate) {
		this.txdate = txdate;
	}

	public Integer getBrhId() {
		return brhId;
	}

	public void setBrhId(Integer brhId) {
		this.brhId = brhId;
	}

	public String getSysDate() {
		return sysDate;
	}

	public void setSysDate(String sysDate) {
		this.sysDate = sysDate;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getAssignedOprid() {
		return assignedOprid;
	}

	public void setAssignedOprid(String assignedOprid) {
		this.assignedOprid = assignedOprid;
	}
}
