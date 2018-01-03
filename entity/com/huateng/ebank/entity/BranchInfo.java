package com.huateng.ebank.entity;

import com.huateng.ebank.entity.base.BaseBranchInfo;



public class BranchInfo extends BaseBranchInfo {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public BranchInfo () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public BranchInfo (java.lang.Integer id) {
		super(id);
	}

/*[CONSTRUCTOR MARKER END]*/

	/** 生成机构树时使用 */
	private Boolean hasChild = false;

	public Boolean getHasChild() {
		return hasChild;
	}

	public void setHasChild(Boolean hasChild) {
		this.hasChild = hasChild;
	}
}