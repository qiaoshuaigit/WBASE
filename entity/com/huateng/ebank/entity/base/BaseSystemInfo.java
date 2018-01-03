package com.huateng.ebank.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the system_info table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="system_info"
 */

public abstract class BaseSystemInfo  implements Serializable {

	public static String REF = "SystemInfo";
	public static String PROP_SYS_DATE = "sysDate";
	public static String PROP_STATUS = "status";
	public static String PROP_LAST_BHDATE = "lastBhdate";
	public static String PROP_BHDATE = "bhdate";
	public static String PROP_SYS_TYPE = "sysType";
	public static String PROP_ID = "id";
	public static String PROP_VERSION = "version";
	public static String PROP_SYS_NAME = "sysName";


	// constructors
	public BaseSystemInfo () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseSystemInfo (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String sysType;
	private java.lang.String sysName;
	private java.lang.String version;
	private java.lang.String sysDate;
	private java.lang.String bhdate;
	private java.lang.String lastBhdate;
	private java.lang.String status;



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
	 * Return the value associated with the column: SYS_TYPE
	 */
	public java.lang.String getSysType () {
		return sysType;
	}

	/**
	 * Set the value related to the column: SYS_TYPE
	 * @param sysType the SYS_TYPE value
	 */
	public void setSysType (java.lang.String sysType) {
		this.sysType = sysType;
	}



	/**
	 * Return the value associated with the column: SYS_NAME
	 */
	public java.lang.String getSysName () {
		return sysName;
	}

	/**
	 * Set the value related to the column: SYS_NAME
	 * @param sysName the SYS_NAME value
	 */
	public void setSysName (java.lang.String sysName) {
		this.sysName = sysName;
	}



	/**
	 * Return the value associated with the column: VERSION
	 */
	public java.lang.String getVersion () {
		return version;
	}

	/**
	 * Set the value related to the column: VERSION
	 * @param version the VERSION value
	 */
	public void setVersion (java.lang.String version) {
		this.version = version;
	}



	/**
	 * Return the value associated with the column: SYS_DATE
	 */
	public java.lang.String getSysDate () {
		return sysDate;
	}

	/**
	 * Set the value related to the column: SYS_DATE
	 * @param sysDate the SYS_DATE value
	 */
	public void setSysDate (java.lang.String sysDate) {
		this.sysDate = sysDate;
	}



	/**
	 * Return the value associated with the column: BHDATE
	 */
	public java.lang.String getBhdate () {
		return bhdate;
	}

	/**
	 * Set the value related to the column: BHDATE
	 * @param bhdate the BHDATE value
	 */
	public void setBhdate (java.lang.String bhdate) {
		this.bhdate = bhdate;
	}



	/**
	 * Return the value associated with the column: LAST_BHDATE
	 */
	public java.lang.String getLastBhdate () {
		return lastBhdate;
	}

	/**
	 * Set the value related to the column: LAST_BHDATE
	 * @param lastBhdate the LAST_BHDATE value
	 */
	public void setLastBhdate (java.lang.String lastBhdate) {
		this.lastBhdate = lastBhdate;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.ebank.entity.SystemInfo)) return false;
		else {
			com.huateng.ebank.entity.SystemInfo systemInfo = (com.huateng.ebank.entity.SystemInfo) obj;
			if (null == this.getId() || null == systemInfo.getId()) return false;
			else return (this.getId().equals(systemInfo.getId()));
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