package com.huateng.ebank.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the role_info table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="role_info"
 */

public abstract class BaseRoleInfo  implements Serializable {

	public static String REF = "RoleInfo";
	public static String PROP_ROLE_NAME = "roleName";
	public static String PROP_LAST_UPD_TIME = "lastUpdTime";
	public static String PROP_STATUS = "status";
	public static String PROP_ID = "id";
	public static String PROP_LAST_UPD_OPRNO = "lastUpdOprno";


	// constructors
	public BaseRoleInfo () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseRoleInfo (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String roleName;
	private java.lang.String status;
	private java.lang.String lastUpdOprno;
	private java.lang.String lastUpdTime;

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="ROLE_CODE"
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
	 * Return the value associated with the column: ROLE_NAME
	 */
	public java.lang.String getRoleName () {
		return roleName;
	}

	/**
	 * Set the value related to the column: ROLE_NAME
	 * @param roleName the ROLE_NAME value
	 */
	public void setRoleName (java.lang.String roleName) {
		this.roleName = roleName;
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
		if (!(obj instanceof com.huateng.ebank.entity.RoleInfo)) return false;
		else {
			com.huateng.ebank.entity.RoleInfo roleInfo = (com.huateng.ebank.entity.RoleInfo) obj;
			if (null == this.getId() || null == roleInfo.getId()) return false;
			else return (this.getId().equals(roleInfo.getId()));
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