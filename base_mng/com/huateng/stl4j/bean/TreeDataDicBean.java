package com.huateng.stl4j.bean;

public class TreeDataDicBean {
	private String id;
	private String codeValue;
	private String codeName;
	private Integer level;
	private Boolean canSelected;
	private Boolean hasChild;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
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
