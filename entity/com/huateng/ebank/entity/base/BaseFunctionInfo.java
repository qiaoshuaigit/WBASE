package com.huateng.ebank.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the function_info table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="function_info"
 */

public abstract class BaseFunctionInfo  implements Serializable {

	public static String REF = "FunctionInfo";
	public static String PROP_LAST_DIRECTORY = "lastDirectory";
	public static String PROP_FUNC_NAME = "funcName";
	public static String PROP_IS_DIRECTORY = "isDirectory";
	public static String PROP_SHOWSEQ = "showseq";
	public static String PROP_PAGE_PATH = "pagePath";
	public static String PROP_LOCATION = "location";
	public static String PROP_ISSHOW = "isShow";
	public static String PROP_ID = "id";


	// constructors
	public BaseFunctionInfo () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseFunctionInfo (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String funcName;
	private java.lang.String pagePath;
	private java.lang.Integer location;
	private java.lang.Integer isDirectory;
	private java.lang.Integer lastDirectory;
	private java.lang.Integer showseq;
	private java.lang.String isShow;



	public java.lang.String getIsShow() {
		return isShow;
	}

	public void setIsShow(java.lang.String isShow) {
		this.isShow = isShow;
	}

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="FUNC_CODE"
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
	 * Return the value associated with the column: FUNC_NAME
	 */
	public java.lang.String getFuncName () {
		return funcName;
	}

	/**
	 * Set the value related to the column: FUNC_NAME
	 * @param funcName the FUNC_NAME value
	 */
	public void setFuncName (java.lang.String funcName) {
		this.funcName = funcName;
	}



	/**
	 * Return the value associated with the column: PAGE_PATH
	 */
	public java.lang.String getPagePath () {
		return pagePath;
	}

	/**
	 * Set the value related to the column: PAGE_PATH
	 * @param pagePath the PAGE_PATH value
	 */
	public void setPagePath (java.lang.String pagePath) {
		this.pagePath = pagePath;
	}



	/**
	 * Return the value associated with the column: LOCATION
	 */
	public java.lang.Integer getLocation () {
		return location;
	}

	/**
	 * Set the value related to the column: LOCATION
	 * @param location the LOCATION value
	 */
	public void setLocation (java.lang.Integer location) {
		this.location = location;
	}



	/**
	 * Return the value associated with the column: IS_DIRECTORY
	 */
	public java.lang.Integer getIsDirectory () {
		return isDirectory;
	}

	/**
	 * Set the value related to the column: IS_DIRECTORY
	 * @param isDirectory the IS_DIRECTORY value
	 */
	public void setIsDirectory (java.lang.Integer isDirectory) {
		this.isDirectory = isDirectory;
	}



	/**
	 * Return the value associated with the column: LAST_DIRECTORY
	 */
	public java.lang.Integer getLastDirectory () {
		return lastDirectory;
	}

	/**
	 * Set the value related to the column: LAST_DIRECTORY
	 * @param lastDirectory the LAST_DIRECTORY value
	 */
	public void setLastDirectory (java.lang.Integer lastDirectory) {
		this.lastDirectory = lastDirectory;
	}



	/**
	 * Return the value associated with the column: SHOWSEQ
	 */
	public java.lang.Integer getShowseq () {
		return showseq;
	}

	/**
	 * Set the value related to the column: SHOWSEQ
	 * @param showseq the SHOWSEQ value
	 */
	public void setShowseq (java.lang.Integer showseq) {
		this.showseq = showseq;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.ebank.entity.FunctionInfo)) return false;
		else {
			com.huateng.ebank.entity.FunctionInfo functionInfo = (com.huateng.ebank.entity.FunctionInfo) obj;
			if (null == this.getId() || null == functionInfo.getId()) return false;
			else return (this.getId().equals(functionInfo.getId()));
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