package com.huateng.ebank.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the branch_info table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="branch_info"
 */

public abstract class BaseBranchInfo  implements Serializable {

	public static String REF = "BranchInfo";
	public static String PROP_BRH_NO = "brhNo";
	public static String PROP_UP_BRH_ID = "upBrhId";
	public static String PROP_PROPERTY = "property";
	public static String PROP_CITY_CD = "cityCd";
	public static String PROP_STATUS = "status";
	public static String PROP_LAST_UPD_TIME = "lastUpdTime";
	public static String PROP_CONTACTS = "contacts";
	public static String PROP_TELE_NO = "teleNo";
	public static String PROP_BRH_CLASS = "brhClass";
	public static String PROP_ADDRESS = "address";
	public static String PROP_ID = "id";
	public static String PROP_BRH_NAME = "brhName";
	public static String PROP_LAST_UPD_OPRNO = "lastUpdOprno";
	public static String PROP_POSTNO = "postno";


	// constructors
	public BaseBranchInfo () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseBranchInfo (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String brhNo;
	private java.lang.String brhName;
	private java.lang.String brhClass;
	private java.lang.Integer upBrhId;
	private java.lang.String contacts;
	private java.lang.String teleNo;
	private java.lang.String address;
	private java.lang.String postno;
	private java.lang.String status;
	private java.lang.String lastUpdOprno;
	private java.lang.String lastUpdTime;
	private java.lang.String property;
	private java.lang.String cityCd;


	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="native"
     *  column="ID"
     */
	public java.lang.Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
	 * Return the value associated with the column: BRH_NO
	 */
	public java.lang.String getBrhNo () {
		return brhNo;
	}

	/**
	 * Set the value related to the column: BRH_NO
	 * @param brhNo the BRH_NO value
	 */
	public void setBrhNo (java.lang.String brhNo) {
		this.brhNo = brhNo;
	}

	/**
	 * Return the value associated with the column: BRH_NAME
	 */
	public java.lang.String getBrhName () {
		return brhName;
	}

	/**
	 * Set the value related to the column: BRH_NAME
	 * @param brhName the BRH_NAME value
	 */
	public void setBrhName (java.lang.String brhName) {
		this.brhName = brhName;
	}

	/**
	 * Return the value associated with the column: PROPERTY
	 */
	public java.lang.String getProperty () {
		return property;
	}

	/**
	 * Set the value related to the column: PROPERTY
	 * @param property the PROPERTY value
	 */
	public void setProperty (java.lang.String property) {
		this.property = property;
	}	
	
	/**
	 * Return the value associated with the column: CITY_CD
	 */
	public java.lang.String getCityCd () {
		return cityCd;
	}

	/**
	 * Set the value related to the column: CITY_CD
	 * @param cityCd the CITY_CD value
	 */
	public void setCityCd (java.lang.String cityCd) {
		this.cityCd = cityCd;
	}	

	/**
	 * Return the value associated with the column: BRH_CLASS
	 */
	public java.lang.String getBrhClass () {
		return brhClass;
	}

	/**
	 * Set the value related to the column: BRH_CLASS
	 * @param brhClass the BRH_CLASS value
	 */
	public void setBrhClass (java.lang.String brhClass) {
		this.brhClass = brhClass;
	}

	/**
	 * Return the value associated with the column: UP_BRH_ID
	 */
	public java.lang.Integer getUpBrhId () {
		return upBrhId;
	}

	/**
	 * Set the value related to the column: UP_BRH_ID
	 * @param upBrhId the UP_BRH_ID value
	 */
	public void setUpBrhId (java.lang.Integer upBrhId) {
		this.upBrhId = upBrhId;
	}
	
	/**
	 * Return the value associated with the column: CONTACTS
	 */
	public java.lang.String getContacts () {
		return contacts;
	}

	/**
	 * Set the value related to the column: CONTACTS
	 * @param contacts the CONTACTS value
	 */
	public void setContacts (java.lang.String contacts) {
		this.contacts = contacts;
	}
	
	/**
	 * Return the value associated with the column: TELE_NO
	 */
	public java.lang.String getTeleNo () {
		return teleNo;
	}

	/**
	 * Set the value related to the column: TELE_NO
	 * @param teleNo the TELE_NO value
	 */
	public void setTeleNo (java.lang.String teleNo) {
		this.teleNo = teleNo;
	}

	/**
	 * Return the value associated with the column: ADDRESS
	 */
	public java.lang.String getAddress () {
		return address;
	}

	/**
	 * Set the value related to the column: ADDRESS
	 * @param address the ADDRESS value
	 */
	public void setAddress (java.lang.String address) {
		this.address = address;
	}

	/**
	 * Return the value associated with the column: POSTNO
	 */
	public java.lang.String getPostno () {
		return postno;
	}

	/**
	 * Set the value related to the column: POSTNO
	 * @param postno the POSTNO value
	 */
	public void setPostno (java.lang.String postno) {
		this.postno = postno;
	}

	/**
	 * Return the value associated with the column: STATUS
	 */
	public java.lang.String getStatus () {
		return status;
	}

	/**
	 * Set the value related to the column: STATUS
	 * @param status the STATUS value
	 */
	public void setStatus (java.lang.String status) {
		this.status = status;
	}

	/**
	 * Return the value associated with the column: LAST_UPD_OPRNO
	 */
	public java.lang.String getLastUpdOprno () {
		return lastUpdOprno;
	}

	/**
	 * Set the value related to the column: LAST_UPD_OPRNO
	 * @param lastUpdOprno the LAST_UPD_OPRNO value
	 */
	public void setLastUpdOprno (java.lang.String lastUpdOprno) {
		this.lastUpdOprno = lastUpdOprno;
	}

	/**
	 * Return the value associated with the column: LAST_UPD_TIME
	 */
	public java.lang.String getLastUpdTime () {
		return lastUpdTime;
	}

	/**
	 * Set the value related to the column: LAST_UPD_TIME
	 * @param lastUpdTime the LAST_UPD_TIME value
	 */
	public void setLastUpdTime (java.lang.String lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.ebank.entity.BranchInfo)) return false;
		else {
			com.huateng.ebank.entity.BranchInfo branchInfo = (com.huateng.ebank.entity.BranchInfo) obj;
			if (null == this.getId() || null == branchInfo.getId()) return false;
			else return (this.getId().equals(branchInfo.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}