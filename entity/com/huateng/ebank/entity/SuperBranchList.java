package com.huateng.ebank.entity;

import com.huateng.ebank.entity.base.BaseSuperBranchList;



public class SuperBranchList extends BaseSuperBranchList {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public SuperBranchList () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public SuperBranchList (java.lang.Integer id) {
		super(id);
	}

/*[CONSTRUCTOR MARKER END]*/

	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(this.getId()).append(":").append(this.getBrhNo()).append(",")
			.append(this.getBrhClass()).append(",").append(this.getUpBrhId1());
		if(null != this.getUpBrhId2()) {
			str.append(",").append(this.getUpBrhId2());
			if(null != this.getUpBrhId3()) {
				str.append(",").append(this.getUpBrhId3());
				if(null != this.getUpBrhId4()) {
					str.append(",").append(this.getUpBrhId4());
					if(null != this.getUpBrhId5()) {
						str.append(",").append(this.getUpBrhId5());
						if(null != this.getUpBrhId6()) {
							str.append(",").append(this.getUpBrhId6());
							if(null != this.getUpBrhId7()) {
								str.append(",").append(this.getUpBrhId7());
								if(null != this.getUpBrhId8()) {
									str.append(",").append(this.getUpBrhId8());
									if(null != this.getUpBrhId9()) {
										str.append(",").append(this.getUpBrhId9());
									}
								}
							}
						}
					}
				}
			}
		}
		
		return str.toString();
	}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.ebank.entity.SuperBranchList)) return false;
		else {
			com.huateng.ebank.entity.SuperBranchList superBranchList = (com.huateng.ebank.entity.SuperBranchList) obj;
			if (null == this.getId() || null == superBranchList.getId()) {
				return false;
			}
			else if(this.getId().intValue() != superBranchList.getId().intValue()) {
				return false;
			}
			else {
				return this.toString().equals(superBranchList.toString());
			}
		}
	}
}