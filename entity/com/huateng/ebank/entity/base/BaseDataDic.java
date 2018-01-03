package com.huateng.ebank.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the data_dic table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="data_dic"
 */

public abstract class BaseDataDic  implements Serializable {

	public static String REF = "DataDic";
	public static String PROP_HIGH_LIMIT = "highLimit";
	public static String PROP_LOW_LIMIT = "lowLimit";
	public static String PROP_ID = "id";
	public static String PROP_LIMIT_FLAG = "limitFlag";
	public static String PROP_DATA_NAME = "dataName";
	public static String PROP_DATA_TYPE_NAME = "dataTypeName";
	public static String PROP_DATA_TYPE_NO = "dataTypeNo";
	public static String PROP_DATA_NO_LEN = "dataNoLen";
	public static String PROP_DATA_NO = "dataNo";


	// constructors
	public BaseDataDic () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDataDic (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer dataTypeNo;
	private java.lang.String dataNo;
	private java.lang.String dataTypeName;
	private java.lang.Integer dataNoLen;
	private java.lang.String dataName;
	private java.lang.String limitFlag;
	private java.lang.String highLimit;
	private java.lang.String lowLimit;



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
	 * Return the value associated with the column: DATA_TYPE_NO
	 */
	public java.lang.Integer getDataTypeNo () {
		return dataTypeNo;
	}

	/**
	 * Set the value related to the column: DATA_TYPE_NO
	 * @param dataTypeNo the DATA_TYPE_NO value
	 */
	public void setDataTypeNo (java.lang.Integer dataTypeNo) {
		this.dataTypeNo = dataTypeNo;
	}



	/**
	 * Return the value associated with the column: DATA_NO
	 */
	public java.lang.String getDataNo () {
		return dataNo;
	}

	/**
	 * Set the value related to the column: DATA_NO
	 * @param dataNo the DATA_NO value
	 */
	public void setDataNo (java.lang.String dataNo) {
		this.dataNo = dataNo;
	}



	/**
	 * Return the value associated with the column: DATA_TYPE_NAME
	 */
	public java.lang.String getDataTypeName () {
		return dataTypeName;
	}

	/**
	 * Set the value related to the column: DATA_TYPE_NAME
	 * @param dataTypeName the DATA_TYPE_NAME value
	 */
	public void setDataTypeName (java.lang.String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}



	/**
	 * Return the value associated with the column: DATA_NO_LEN
	 */
	public java.lang.Integer getDataNoLen () {
		return dataNoLen;
	}

	/**
	 * Set the value related to the column: DATA_NO_LEN
	 * @param dataNoLen the DATA_NO_LEN value
	 */
	public void setDataNoLen (java.lang.Integer dataNoLen) {
		this.dataNoLen = dataNoLen;
	}



	/**
	 * Return the value associated with the column: DATA_NAME
	 */
	public java.lang.String getDataName () {
		return dataName;
	}

	/**
	 * Set the value related to the column: DATA_NAME
	 * @param dataName the DATA_NAME value
	 */
	public void setDataName (java.lang.String dataName) {
		this.dataName = dataName;
	}



	/**
	 * Return the value associated with the column: LIMIT_FLAG
	 */
	public java.lang.String getLimitFlag () {
		return limitFlag;
	}

	/**
	 * Set the value related to the column: LIMIT_FLAG
	 * @param limitFlag the LIMIT_FLAG value
	 */
	public void setLimitFlag (java.lang.String limitFlag) {
		this.limitFlag = limitFlag;
	}



	/**
	 * Return the value associated with the column: HIGH_LIMIT
	 */
	public java.lang.String getHighLimit () {
		return highLimit;
	}

	/**
	 * Set the value related to the column: HIGH_LIMIT
	 * @param highLimit the HIGH_LIMIT value
	 */
	public void setHighLimit (java.lang.String highLimit) {
		this.highLimit = highLimit;
	}



	/**
	 * Return the value associated with the column: LOW_LIMIT
	 */
	public java.lang.String getLowLimit () {
		return lowLimit;
	}

	/**
	 * Set the value related to the column: LOW_LIMIT
	 * @param lowLimit the LOW_LIMIT value
	 */
	public void setLowLimit (java.lang.String lowLimit) {
		this.lowLimit = lowLimit;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.ebank.entity.DataDic)) return false;
		else {
			com.huateng.ebank.entity.DataDic dataDic = (com.huateng.ebank.entity.DataDic) obj;
			if (null == this.getId() || null == dataDic.getId()) return false;
			else return (this.getId().equals(dataDic.getId()));
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