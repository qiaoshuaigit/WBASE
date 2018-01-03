package com.huateng.stl4j.bean;

import com.huateng.ebank.entity.UserRoleRelation;

public class UserRoleRelationBean extends UserRoleRelation {
	private static final long serialVersionUID = -2387645822968750391L;
	private Boolean select;
	private String roleName;
	
	public Boolean getSelect() {
		return select;
	}
	public void setSelect(Boolean select) {
		this.select = select;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
