package com.huateng.ebank.entity.base;

import java.io.Serializable;

/**
 * This is an object that contains data related to the operator table. Do not
 * modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 * 
 * @hibernate.class table="operator"
 */

public abstract class BaseOperator implements Serializable {

	public static String REF = "Operator";
	public static String PROP_STATUS = "status";
	public static String PROP_BRH_NO = "brhNo";
	public static String PROP_PASSWORD = "password";
	public static String PROP_ID = "id";
	public static String PROP_USER_NAME = "userName";
	public static String PROP_OPR_NO = "oprNo";
	public static String PROP_STATUS_IN = "statusIn";
	public static String PROP_IN_TIME = "inTime";
	public static String PROP_OUT_TIME = "outTime";

	// constructors
	public BaseOperator() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseOperator(java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize() {
	}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String oprNo;
	private java.lang.String userName;
	private java.lang.String brhNo;
	private java.lang.String password;
	private java.lang.String status;
	private java.lang.String statusIn = "0";
	private java.lang.String inTime;
	private java.lang.String outTime;
	/**
	 * 电话
	 */
	private java.lang.String teleno;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="native" column="ID"
	 */
	public java.lang.Integer getId() {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * 
	 * @param id
	 *            the new ID
	 */
	public void setId(java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
	 * Return the value associated with the column: OPR_NO
	 */
	public java.lang.String getOprNo() {
		return oprNo;
	}

	/**
	 * Set the value related to the column: OPR_NO
	 * 
	 * @param oprNo
	 *            the OPR_NO value
	 */
	public void setOprNo(java.lang.String oprNo) {
		this.oprNo = oprNo;
	}

	/**
	 * Return the value associated with the column: USER_NAME
	 */
	public java.lang.String getUserName() {
		return userName;
	}

	/**
	 * Set the value related to the column: USER_NAME
	 * 
	 * @param userName
	 *            the USER_NAME value
	 */
	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	/**
	 * Return the value associated with the column: BRH_NO
	 */
	public java.lang.String getBrhNo() {
		return brhNo;
	}

	/**
	 * Set the value related to the column: BRH_NO
	 * 
	 * @param brhNo
	 *            the BRH_NO value
	 */
	public void setBrhNo(java.lang.String brhNo) {
		this.brhNo = brhNo;
	}

	/**
	 * Return the value associated with the column: PASSWORD
	 */
	public java.lang.String getPassword() {
		return password;
	}

	/**
	 * Set the value related to the column: PASSWORD
	 * 
	 * @param password
	 *            the PASSWORD value
	 */
	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	/**
	 * Return the value associated with the column: STATUS
	 */
	public java.lang.String getStatus() {
		return status;
	}

	/**
	 * Set the value related to the column: STATUS
	 * 
	 * @param status
	 *            the STATUS value
	 */
	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	public java.lang.String getStatusIn() {
		return statusIn;
	}

	public void setStatusIn(java.lang.String statusIn) {
		this.statusIn = statusIn;
	}

	public java.lang.String getInTime() {
		return inTime;
	}

	public void setInTime(java.lang.String inTime) {
		this.inTime = inTime;
	}

	public java.lang.String getOutTime() {
		return outTime;
	}

	public void setOutTime(java.lang.String outTime) {
		this.outTime = outTime;
	}
	

	public java.lang.String getTeleno() {
		return teleno;
	}

	public void setTeleno(java.lang.String teleno) {
		this.teleno = teleno;
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.huateng.ebank.entity.Operator))
			return false;
		else {
			com.huateng.ebank.entity.Operator operator = (com.huateng.ebank.entity.Operator) obj;
			if (null == this.getId() || null == operator.getId())
				return false;
			else
				return (this.getId().equals(operator.getId()));
		}
	}

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":"
						+ this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString() {
		return super.toString();
	}

}