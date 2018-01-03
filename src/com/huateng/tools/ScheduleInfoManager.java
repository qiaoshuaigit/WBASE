package com.huateng.tools;

import com.huateng.stl4j.service.SystemParamService;

public class ScheduleInfoManager {
	private SystemParamService sysParam;
	
	public String getCronExpression(String beanId) {
		return sysParam.getParamValue("QUARTZ", beanId);
	}
	
	public long getRepeatInterval(String beanId) {
		return Long.parseLong(sysParam.getParamValue("QUARTZ", beanId));
	}

	public SystemParamService getSysParam() {
		return sysParam;
	}

	public void setSysParam(SystemParamService sysParam) {
		this.sysParam = sysParam;
	}
}
