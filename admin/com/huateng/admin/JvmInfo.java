package com.huateng.admin;

import java.util.ArrayList;
import java.util.List;

import com.huateng.stl4j.service.SystemParamService;

public class JvmInfo {
	private List<JvmBean> jvmUsedInfoList = new ArrayList<JvmBean>();
	private long maxMemory = 0;

	public List<JvmBean> getJvmUsedInfoList() {
		return jvmUsedInfoList;
	}

	public void setJvmUsedInfoList(List<JvmBean> jvmUsedInfoList) {
		this.jvmUsedInfoList = jvmUsedInfoList;
	}

	public long getMaxMemory() {
		return maxMemory;
	}

	public void setMaxMemory(long maxMemory) {
		this.maxMemory = maxMemory;
	}
	
	public void addJvmBean(JvmBean bean) {
		if(jvmUsedInfoList.size() >= Integer.parseInt(SystemParamService.getInstance().getParamValue("SYSINFO", "JvmInfoListSize"))) {
			jvmUsedInfoList.remove(0);
		}
		jvmUsedInfoList.add(bean);
	}
}
