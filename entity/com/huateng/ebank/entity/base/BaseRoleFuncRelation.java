package com.huateng.ebank.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the role_func_relation table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="role_func_relation"
 */

public abstract class BaseRoleFuncRelation  implements Serializable {

	public static String REF = "RoleFuncRelation";
	public static String PROP_LAST_UPD_TIME = "lastUpdTime";
	public static String PROP_ROLE_CODE = "roleCode";
	public static String PROP_FUNC_CODE = "funcCode";
	public static String PROP_ID = "id";
	public static String PROP_LAST_UPD_OPRNO = "lastUpdOprno";


	// constructors
	public BaseRoleFuncRelation () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseRoleFuncRelation (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer roleCode;
	private java.lang.Integer funcCode;
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
	 * Return the value associated with the column: FUNC_CODE
	 */
	public java.lang.Integer getFuncCode () {
		return funcCode;
	}

	/**
	 * Set the value related to the column: FUNC_CODE
	 * @param funcCode the FUNC_CODE value
	 */
	public void setFuncCode (java.lang.Integer funcCode) {
		this.funcCode = funcCode;
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
		if (!(obj instanceof com.huateng.ebank.entity.RoleFuncRelation)) return false;
		else {
			com.huateng.ebank.entity.RoleFuncRelation roleFuncRelation = (com.huateng.ebank.entity.RoleFuncRelation) obj;
			if (null == this.getId() || null == roleFuncRelation.getId()) return false;
			else return (this.getId().equals(roleFuncRelation.getId()));
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