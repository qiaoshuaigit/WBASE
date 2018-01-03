package com.huateng.ebank.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the data_dic_map table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="data_dic_map"
 */

public abstract class BaseDataDicMap  implements Serializable {

	public static String REF = "DataDicMap";
	public static String PROP_INCODE = "incode";
	public static String PROP_OUTCODE = "outcode";
	public static String PROP_MAP_TYPE = "mapType";
	public static String PROP_INCODE_LEN = "incodeLen";
	public static String PROP_OUTCODE_LEN = "outcodeLen";
	public static String PROP_ID = "id";


	// constructors
	public BaseDataDicMap () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDataDicMap (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer mapType;
	private java.lang.String incode;
	private java.lang.String outcode;
	private java.lang.Integer incodeLen;
	private java.lang.Integer outcodeLen;



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
	 * Return the value associated with the column: MAP_TYPE
	 */
	public java.lang.Integer getMapType () {
		return mapType;
	}

	/**
	 * Set the value related to the column: MAP_TYPE
	 * @param mapType the MAP_TYPE value
	 */
	public void setMapType (java.lang.Integer mapType) {
		this.mapType = mapType;
	}



	/**
	 * Return the value associated with the column: INCODE
	 */
	public java.lang.String getIncode () {
		return incode;
	}

	/**
	 * Set the value related to the column: INCODE
	 * @param incode the INCODE value
	 */
	public void setIncode (java.lang.String incode) {
		this.incode = incode;
	}



	/**
	 * Return the value associated with the column: OUTCODE
	 */
	public java.lang.String getOutcode () {
		return outcode;
	}

	/**
	 * Set the value related to the column: OUTCODE
	 * @param outcode the OUTCODE value
	 */
	public void setOutcode (java.lang.String outcode) {
		this.outcode = outcode;
	}



	/**
	 * Return the value associated with the column: INCODE_LEN
	 */
	public java.lang.Integer getIncodeLen () {
		return incodeLen;
	}

	/**
	 * Set the value related to the column: INCODE_LEN
	 * @param incodeLen the INCODE_LEN value
	 */
	public void setIncodeLen (java.lang.Integer incodeLen) {
		this.incodeLen = incodeLen;
	}



	/**
	 * Return the value associated with the column: OUTCODE_LEN
	 */
	public java.lang.Integer getOutcodeLen () {
		return outcodeLen;
	}

	/**
	 * Set the value related to the column: OUTCODE_LEN
	 * @param outcodeLen the OUTCODE_LEN value
	 */
	public void setOutcodeLen (java.lang.Integer outcodeLen) {
		this.outcodeLen = outcodeLen;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.ebank.entity.DataDicMap)) return false;
		else {
			com.huateng.ebank.entity.DataDicMap dataDicMap = (com.huateng.ebank.entity.DataDicMap) obj;
			if (null == this.getId() || null == dataDicMap.getId()) return false;
			else return (this.getId().equals(dataDicMap.getId()));
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