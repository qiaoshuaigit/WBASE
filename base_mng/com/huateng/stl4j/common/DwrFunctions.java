package com.huateng.stl4j.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import om.util.OmUtils;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdScheduler;

import com.huateng.admin.SystemMonitor;
import com.huateng.ebank.business.common.service.CommonService;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.common.GlobalInfo;
import com.huateng.ebank.entity.FunctionInfo;
import com.huateng.ebank.entity.RoleFuncRelation;
import com.huateng.stl4j.operation.RoleFuncRelationOperation;
import com.huateng.stl4j.service.BranchInfoService;
import com.huateng.stl4j.service.UserMangerService;

public class DwrFunctions {
	private static Logger logger = Logger.getLogger(DwrFunctions.class);

	private void setGlobalInfoByRequest(HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		GlobalInfo globalInfo = (GlobalInfo) httpSession.getAttribute(GlobalInfo.KEY_GLOBAL_INFO);
		if (null != globalInfo) {
			String sessionId = httpSession.getId();
			globalInfo.setSessionId(sessionId);
			GlobalInfo.setCurrentInstance(globalInfo);
		}
	}
	
	public void doJvmGc(HttpServletRequest request) throws Exception {
		setGlobalInfoByRequest(request);
		System.gc();
		logger.info("doJvmGc");
		SystemMonitor.getJVMInfo();
	}
	
	public void pausedQuartzTask(HttpServletRequest request, String beanId) throws Exception {
		setGlobalInfoByRequest(request);
		StdScheduler scheduler = (StdScheduler) ApplicationContextUtils.getBean("startQuerz");
		int status = scheduler.getTriggerState(beanId, Scheduler.DEFAULT_GROUP);
		if(Trigger.STATE_PAUSED != status) {
			scheduler.pauseTrigger(beanId, Scheduler.DEFAULT_GROUP);
		}
	}
	
	public void resumeQuartzTask(HttpServletRequest request, String beanId) throws Exception {
		setGlobalInfoByRequest(request);
		StdScheduler scheduler = (StdScheduler) ApplicationContextUtils.getBean("startQuerz");
		int status = scheduler.getTriggerState(beanId, Scheduler.DEFAULT_GROUP);
		if(Trigger.STATE_PAUSED == status) {
			scheduler.resumeTrigger(beanId, Scheduler.DEFAULT_GROUP);
		}
	}

	public List<String> getBranchTree(HttpServletRequest request, Integer brhId) {
		setGlobalInfoByRequest(request);
		return BranchInfoService.getInstance().getTreeList(brhId);
	}
	
	public int isHaveBranch(HttpServletRequest request, String no,String methodType) {
		setGlobalInfoByRequest(request);
		return BranchInfoService.getInstance().countByBrhNo(no);
	}
	
	public int isHaveOpr(HttpServletRequest request, String oprNo){
		CommonDAO dao = CommonDAO.getInstance();
		StringBuilder hql = new StringBuilder("select po from Operator po where po.oprNo = '");
		hql.append(oprNo).append("'");
		List list = dao.findByHQL(hql.toString());
		if(list!=null){
			return list.size();
		}
		return 0;
	}
	
	public List<String> getRoleFuncRelation(HttpServletRequest request, String roleCode) throws CommonException {
		Integer roleId = 0;
		setGlobalInfoByRequest(request);
		if(!OmUtils.isEmpty(roleCode)) {
			roleId = new Integer(roleCode.trim());
		}
		return UserMangerService.getInstance().getRoleFuncRelation(roleId);
	}
	
	private void getUpFunctionInfo(List<FunctionInfo> funcList, FunctionInfo funcInfo, Integer roleCode, Map<String, RoleFuncRelation> resultMap) {
		for(int i = 0; i < funcList.size(); i ++) {
			FunctionInfo fi = funcList.get(i);
			if(fi.getId().intValue() == funcInfo.getLastDirectory().intValue()) {
				RoleFuncRelation bean = new RoleFuncRelation();
				bean.setRoleCode(roleCode);
				bean.setFuncCode(fi.getId());
				resultMap.put(fi.getId().toString(), bean);
				if(0 != fi.getLastDirectory().intValue()) {
					getUpFunctionInfo(funcList, fi, roleCode, resultMap);
				}
			}
		}
	}
	
	private FunctionInfo getFunctionInfo(List<FunctionInfo> funcList, Integer funcCode) {
		for(int i = 0; i < funcList.size(); i ++) {
			FunctionInfo fi = funcList.get(i);
			if(fi.getId().intValue() == funcCode.intValue()) {
				return fi;
			}
		}
		return null;
	}
	
	private RoleFuncRelation getRoleFuncRelation(List<RoleFuncRelation> relatList, Integer roleCode, Integer funcCode) {
		for(int i = 0; i < relatList.size(); i ++) {
			RoleFuncRelation rf = relatList.get(i);
			if(rf.getRoleCode().intValue() == roleCode.intValue() &&
				rf.getFuncCode().intValue() == funcCode.intValue()) {
				return rf;
			}
		}
		return null;
	}
	
	public String saveRoleFuncRelation(HttpServletRequest request, String roleCode, String funcCodes) {
		try {
			setGlobalInfoByRequest(request);
			CommonService.getInstance().checkSysMngOprNos();
			Integer iRoleCode = new Integer(roleCode);
			CommonDAO dao = CommonDAO.getInstance();
			List<FunctionInfo> funcList = dao.findByWhere(FunctionInfo.class, "1=1");
			List<RoleFuncRelation> relatList = dao.findByWhere(RoleFuncRelation.class,
				"po.roleCode=?", new Object[]{iRoleCode});
			Map<String, RoleFuncRelation> delMap = new HashMap<String, RoleFuncRelation>();
			Map<String, RoleFuncRelation> addMap = new HashMap<String, RoleFuncRelation>();
			List<String> selectedFuncList = OmUtils.split(funcCodes, ",");
			Map<String, RoleFuncRelation> resultMap = new HashMap<String, RoleFuncRelation>();
			for(int i = 0; i < selectedFuncList.size(); i ++) {
				String sFuncCode = selectedFuncList.get(i);
				Integer funcCode = new Integer(sFuncCode);
				FunctionInfo funcInfo = getFunctionInfo(funcList, funcCode);
				if(null == funcInfo) continue;
				RoleFuncRelation bean = new RoleFuncRelation();
				bean.setRoleCode(iRoleCode);
				bean.setFuncCode(funcCode);
				resultMap.put(funcInfo.getId().toString(), bean);
				getUpFunctionInfo(funcList, funcInfo, iRoleCode, resultMap);
			}
			//Init delMap
			for(int i = 0; i < relatList.size(); i ++) {
				RoleFuncRelation rf = relatList.get(i);
				RoleFuncRelation tmp = resultMap.get(rf.getFuncCode().toString());
				if(null == tmp) {
					delMap.put(rf.getFuncCode().toString(), rf);
				}
			}
			//Init addMap
			Iterator<RoleFuncRelation> it = resultMap.values().iterator();
			while(it.hasNext()) {
				RoleFuncRelation bean = it.next();
				if(null == getRoleFuncRelation(relatList, bean.getRoleCode(), bean.getFuncCode())) {
					addMap.put(bean.getFuncCode().toString(), bean);
				}
			}
			OperationContext oc = new OperationContext();
			oc.set(RoleFuncRelationOperation.ADD_ITERATOR, addMap.values().iterator());
			oc.set(RoleFuncRelationOperation.DEL_ITERATOR, delMap.values().iterator());
			OPCaller.call(RoleFuncRelationOperation.ID, oc);
		} catch(Exception e) {
			logger.error("保存岗位[" + roleCode + "]权限失败", e);
			return e.getMessage();
		}
		return "0";
	}
	
	public long hasWorkList(HttpServletRequest request) {
		long result = 0L;
		setGlobalInfoByRequest(request);
		GlobalInfo gi = GlobalInfo.getCurrentInstanceWithoutException();
		if(null != gi) {
			CommonDAO dao = CommonDAO.getInstance();
			List list = dao.findByHQL("select count(a.id) from UserRoleRelation a,RoleFuncRelation b where " +
					"a.oprId=? and a.roleCode=b.roleCode and b.funcCode=100000", new Object[]{gi.getUserId()});
			result = ((Long) list.get(0)).longValue();
		}
		return result;
	}
}
