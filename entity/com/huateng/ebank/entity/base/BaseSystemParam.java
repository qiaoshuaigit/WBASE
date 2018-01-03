package com.huateng.ebank.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the SYSTEM_PARAM table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="SYSTEM_PARAM"
 */

public abstract class BaseSystemParam  implements Serializable {

	public static String REF = "SystemParam";
	public static String PROP_PARAM_DESC = "paramDesc";
	public static String PROP_MAGIC_ID = "magicId";
	public static String PROP_PARAM_VALUE = "paramValue";
	public static String PROP_ID = "id";
	public static String PROP_PARAM_ID = "paramId";


	// constructors
	public BaseSystemParam () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseSystemParam (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String paramId;
	private java.lang.String magicId;
	private java.lang.String paramValue;
	private java.lang.String paramDesc;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="sequence"
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
	 * Return the value associated with the column: PARAM_ID
	 */
	public java.lang.String getParamId () {
		return paramId;
	}

	/**
	 * Set the value related to the column: PARAM_ID
	 * @param paramId the PARAM_ID value
	 */
	public void setParamId (java.lang.String paramId) {
		this.paramId = paramId;
	}



	/**
	 * Return the value associated with the column: MAGIC_ID
	 */
	public java.lang.String getMagicId () {
		return magicId;
	}

	/**
	 * Set the value related to the column: MAGIC_ID
	 * @param magicId the MAGIC_ID value
	 */
	public void setMagicId (java.lang.String magicId) {
		this.magicId = magicId;
	}



	/**
	 * Return the value associated with the column: PARAM_VALUE
	 */
	public java.lang.String getParamValue () {
		return paramValue;
	}

	/**
	 * Set the value related to the column: PARAM_VALUE
	 * @param paramValue the PARAM_VALUE value
	 */
	public void setParamValue (java.lang.String paramValue) {
		this.paramValue = paramValue;
	}



	/**
	 * Return the value associated with the column: PARAM_DESC
	 */
	public java.lang.String getParamDesc () {
		return paramDesc;
	}

	/**
	 * Set the value related to the column: PARAM_DESC
	 * @param paramDesc the PARAM_DESC value
	 */
	public void setParamDesc (java.lang.String paramDesc) {
		this.paramDesc = paramDesc;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.ebank.entity.SystemParam)) return false;
		else {
			com.huateng.ebank.entity.SystemParam systemParam = (com.huateng.ebank.entity.SystemParam) obj;
			if (null == this.getId() || null == systemParam.getId()) return false;
			else return (this.getId().equals(systemParam.getId()));
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