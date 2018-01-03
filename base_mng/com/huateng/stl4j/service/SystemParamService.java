package com.huateng.stl4j.service;

import java.util.List;

import om.param.IReloadable;
import om.param.ReloadService;
import om.util.OmUtils;

import org.apache.commons.collections.map.LRUMap;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.huateng.ebank.entity.SystemParam;
import com.huateng.stl4j.common.ApplicationContextUtils;
import com.huateng.stl4j.common.CommonDAO;

public class SystemParamService implements IReloadable {
	private static Logger logger = Logger.getLogger(SystemParamService.class);
	private static LRUMap loadedMap = new LRUMap(200);
	private CommonDAO dao;
	
	private SystemParamService() {
		ReloadService.getInstance().registReloader("SYSTEMPARAM", this);
	}
	
	public static SystemParamService getInstance() {
		return (SystemParamService) ApplicationContextUtils.getBean(SystemParamService.class.getName());
	}
	
	public void reload() throws Exception {
		loadedMap.clear();
		logger.info("loadedMap.clear()");
	}
	
	public synchronized String getParamValue(String paramId, String magicId) {
		Object obj = loadedMap.get(paramId + "+" + magicId);
		if(null != obj) {
			return ((SystemParam) obj).getParamValue();
		}
		List<SystemParam> list = dao.findByWhere(SystemParam.class,
			"po.paramId=? and po.magicId=?", new Object[]{paramId, magicId});
		if(null != list && list.size() > 0) {
			SystemParam sp = list.get(0);
			loadedMap.put(paramId + "+" + magicId, sp);
			return sp.getParamValue();
		}
		return "";
	}
	
	public boolean isClusterMode() {
		boolean flag = false;
		String value = getParamValue("SYSINFO", "CLUSTER_FLAG");
		if(!OmUtils.isEmpty(value)) {
			flag = Boolean.parseBoolean(OmUtils.trim(value));
		}
		return flag;
	}
	
	public boolean isLogMsgToFile() {
		boolean result = false;
		String value = getParamValue("SYSINFO", "MSG2FILE");
		if(!OmUtils.isEmpty(value)) {
			result = Boolean.parseBoolean(value);
		}
		return result;
	}
	
	public void noticeActiveServer(String serverName) {
		SystemParam po = (SystemParam) loadedMap.get("SERVERNAME+QUERZ");
		if(null != po) {
			SystemParam sp = new SystemParam();
			BeanUtils.copyProperties(po, sp, new String[]{"id"});
			sp.setParamValue(serverName);
			loadedMap.put("SERVERNAME+QUERZ", sp);
		}
	}

	public CommonDAO getDao() {
		return dao;
	}

	public void setDao(CommonDAO dao) {
		this.dao = dao;
	}
}
