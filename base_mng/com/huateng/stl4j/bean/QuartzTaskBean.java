package com.huateng.stl4j.bean;

public class QuartzTaskBean {
	private String beanId;
	private String taskName;
	private String taskDefine;
	private String triggerType;
	private String status;
	private String nextExecuteTime;

	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDefine() {
		return taskDefine;
	}

	public void setTaskDefine(String taskDefine) {
		this.taskDefine = taskDefine;
	}

	public String getNextExecuteTime() {
		return nextExecuteTime;
	}

	public void setNextExecuteTime(String nextExecuteTime) {
		this.nextExecuteTime = nextExecuteTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}
}
