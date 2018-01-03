package com.huateng.tools;

import java.io.Serializable;
import java.text.ParseException;

import org.springframework.scheduling.quartz.CronTriggerBean;

public class OmCronTriggerBean extends CronTriggerBean implements Serializable {
	private static final long serialVersionUID = -7408575949170790360L;
	private ScheduleInfoManager scheduleMgr = null;
	private String beanId = "";

	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String beanId) throws ParseException {
		this.beanId = beanId;
		setCronExpression(scheduleMgr.getCronExpression(beanId));
	}

	public ScheduleInfoManager getScheduleMgr() {
		return scheduleMgr;
	}

	public void setScheduleMgr(ScheduleInfoManager scheduleMgr) {
		this.scheduleMgr = scheduleMgr;
	}
}
