package com.huateng.ebank.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the user_role_relation table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="user_role_relation"
 */

public abstract class BaseUserRoleRelation  implements Serializable {

	public static String REF = "UserRoleRelation";
	public static String PROP_LAST_UPD_TIME = "lastUpdTime";
	public static String PROP_ROLE_CODE = "roleCode";
	public static String PROP_OPR_ID = "oprId";
	public static String PROP_ID = "id";
	public static String PROP_LAST_UPD_OPRNO = "lastUpdOprno";


	// constructors
	public BaseUserRoleRelation () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseUserRoleRelation (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer oprId;
	private java.lang.Integer roleCode;
	private java.lang.String lastUpdOprno;
	private java.lang.String lastUpdTime;



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
	 * Return the value associated with the column: OPR_ID
	 */
	public java.lang.Integer getOprId () {
		return oprId;
	}

	/**
	 * Set the value related to the column: OPR_ID
	 * @param oprId the OPR_ID value
	 */
	public void setOprId (java.lang.Integer oprId) {
		this.oprId = oprId;
	}



	/**
	 * Return the value associated with the column: ROLE_CODE
	 */
	public java.lang.Integer getRoleCode () {
		return roleCode;
	}

	/**
	 * Set the value related to the column: ROLE_CODE
	 * @param roleCode the ROLE_CODE value
	 */
	public void setRoleCode (java.lang.Integer roleCode) {
		this.roleCode = roleCode;
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
		if (!(obj instanceof com.huateng.ebank.entity.UserRoleRelation)) return false;
		else {
			com.huateng.ebank.entity.UserRoleRelation userRoleRelation = (com.huateng.ebank.entity.UserRoleRelation) obj;
			if (null == this.getId() || null == userRoleRelation.getId()) return false;
			else return (this.getId().equals(userRoleRelation.getId()));
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