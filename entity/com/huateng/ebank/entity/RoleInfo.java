package com.huateng.ebank.entity;

import com.huateng.ebank.entity.base.BaseRoleInfo;



public class RoleInfo extends BaseRoleInfo {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public RoleInfo () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public RoleInfo (java.lang.Integer id) {
		super(id);
	}

/*[CONSTRUCTOR MARKER END]*/

	private Integer oldId;
	private Integer roleCode;

	public Integer getOldId() {
		return getId();
	}

	public void setOldId(Integer oldId) {
	}

	public Integer getRoleCode() {
		return getId();
	}

	public void setRoleCode(Integer roleCode) {
	}
}