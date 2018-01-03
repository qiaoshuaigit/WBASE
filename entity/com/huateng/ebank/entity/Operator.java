package com.huateng.ebank.entity;

import com.huateng.ebank.entity.base.BaseOperator;



public class Operator extends BaseOperator {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Operator () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Operator (java.lang.Integer id) {
		super(id);
	}

/*[CONSTRUCTOR MARKER END]*/

	private String brhName;
	private Integer oprId;
	private String oldOprNo;
	private java.lang.String teleno;
	public String getBrhName() {
		return brhName;
	}

	public void setBrhName(String brhName) {
		this.brhName = brhName;
	}

	public Integer getOprId() {
		return getId();
	}

	public void setOprId(Integer oprId) {
	}

	public String getOldOprNo() {
		return getOprNo();
	}

	public void setOldOprNo(String oldOprNo) {
	}
	public String getOprNoName() {
		return getOprNo() + "-" + getUserName();
	}

	public java.lang.String getTeleno() {
		return teleno;
	}

	public void setTeleno(java.lang.String teleno) {
		this.teleno = teleno;
	}
}