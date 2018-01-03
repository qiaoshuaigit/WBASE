package com.huateng.tools;

import java.io.Serializable;

import org.springframework.scheduling.quartz.SimpleTriggerBean;

public class OmSimpleTriggerBean extends SimpleTriggerBean implements Serializable {
	private static final long serialVersionUID = -3948082872928850864L;
	private ScheduleInfoManager scheduleMgr = null;
	private String beanId = "";

	public ScheduleInfoManager getScheduleMgr() {
		return scheduleMgr;
	}

	public void setScheduleMgr(ScheduleInfoManager scheduleMgr) {
		this.scheduleMgr = scheduleMgr;
	}

	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String beanId) {
		this.beanId = beanId;
		setRepeatInterval(scheduleMgr.getRepeatInterval(beanId));
	}
}
