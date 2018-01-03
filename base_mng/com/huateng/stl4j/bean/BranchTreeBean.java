package com.huateng.stl4j.bean;

import java.io.Serializable;

public class BranchTreeBean implements Serializable {
	private static final long serialVersionUID = -291353603111741072L;
	private Integer id;
	private String brhNo;
	private String brhName;
	private String brhClass;
	private Integer upBrhId;
	private Integer level;
	private Boolean canSelected;
	private Boolean hasChild;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getBrhNo() {
		return brhNo;
	}
	
	public void setBrhNo(String brhNo) {
		this.brhNo = brhNo;
	}
	
	public String getBrhName() {
		return brhName;
	}
	
	public void setBrhName(String brhName) {
		this.brhName = brhName;
	}
	
	public String getBrhClass() {
		return brhClass;
	}
	
	public void setBrhClass(String brhClass) {
		this.brhClass = brhClass;
	}
	
	public Integer getUpBrhId() {
		return upBrhId;
	}
	
	public void setUpBrhId(Integer upBrhId) {
		this.upBrhId = upBrhId;
	}
	
	public Integer getLevel() {
		return level;
	}
	
	public void setLevel(Integer level) {
		this.level = level;
	}

	public Boolean getCanSelected() {
		return canSelected;
	}

	public void setCanSelected(Boolean canSelected) {
		this.canSelected = canSelected;
	}

	public Boolean getHasChild() {
		return hasChild;
	}

	public void setHasChild(Boolean hasChild) {
		this.hasChild = hasChild;
	}
}
