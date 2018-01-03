package com.huateng.ebank.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the super_branch_list table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="super_branch_list"
 */

public abstract class BaseSuperBranchList  implements Serializable {

	public static String REF = "SuperBranchList";
	public static String PROP_UP_BRH_ID7 = "upBrhId7";
	public static String PROP_UP_BRH_ID6 = "upBrhId6";
	public static String PROP_UP_BRH_ID5 = "upBrhId5";
	public static String PROP_UP_BRH_ID4 = "upBrhId4";
	public static String PROP_BRH_NO = "brhNo";
	public static String PROP_UP_BRH_ID3 = "upBrhId3";
	public static String PROP_UP_BRH_ID2 = "upBrhId2";
	public static String PROP_UP_BRH_ID1 = "upBrhId1";
	public static String PROP_BRH_CLASS = "brhClass";
	public static String PROP_ID = "id";
	public static String PROP_UP_BRH_ID8 = "upBrhId8";
	public static String PROP_UP_BRH_ID9 = "upBrhId9";


	// constructors
	public BaseSuperBranchList () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseSuperBranchList (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String brhNo;
	private java.lang.String brhClass;
	private java.lang.Integer upBrhId1 = 0;
	private java.lang.Integer upBrhId2 = 0;
	private java.lang.Integer upBrhId3 = 0;
	private java.lang.Integer upBrhId4 = 0;
	private java.lang.Integer upBrhId5 = 0;
	private java.lang.Integer upBrhId6 = 0;
	private java.lang.Integer upBrhId7 = 0;
	private java.lang.Integer upBrhId8 = 0;
	private java.lang.Integer upBrhId9 = 0;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
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
	 * Return the value associated with the column: UP_BRH_ID1
	 */
	public java.lang.Integer getUpBrhId1 () {
		return upBrhId1;
	}

	/**
	 * Set the value related to the column: UP_BRH_ID1
	 * @param upBrhId1 the UP_BRH_ID1 value
	 */
	public void setUpBrhId1 (java.lang.Integer upBrhId1) {
		this.upBrhId1 = upBrhId1;
	}



	/**
	 * Return the value associated with the column: UP_BRH_ID2
	 */
	public java.lang.Integer getUpBrhId2 () {
		return upBrhId2;
	}

	/**
	 * Set the value related to the column: UP_BRH_ID2
	 * @param upBrhId2 the UP_BRH_ID2 value
	 */
	public void setUpBrhId2 (java.lang.Integer upBrhId2) {
		this.upBrhId2 = upBrhId2;
	}



	/**
	 * Return the value associated with the column: UP_BRH_ID3
	 */
	public java.lang.Integer getUpBrhId3 () {
		return upBrhId3;
	}

	/**
	 * Set the value related to the column: UP_BRH_ID3
	 * @param upBrhId3 the UP_BRH_ID3 value
	 */
	public void setUpBrhId3 (java.lang.Integer upBrhId3) {
		this.upBrhId3 = upBrhId3;
	}



	/**
	 * Return the value associated with the column: UP_BRH_ID4
	 */
	public java.lang.Integer getUpBrhId4 () {
		return upBrhId4;
	}

	/**
	 * Set the value related to the column: UP_BRH_ID4
	 * @param upBrhId4 the UP_BRH_ID4 value
	 */
	public void setUpBrhId4 (java.lang.Integer upBrhId4) {
		this.upBrhId4 = upBrhId4;
	}



	/**
	 * Return the value associated with the column: UP_BRH_ID5
	 */
	public java.lang.Integer getUpBrhId5 () {
		return upBrhId5;
	}

	/**
	 * Set the value related to the column: UP_BRH_ID5
	 * @param upBrhId5 the UP_BRH_ID5 value
	 */
	public void setUpBrhId5 (java.lang.Integer upBrhId5) {
		this.upBrhId5 = upBrhId5;
	}



	/**
	 * Return the value associated with the column: UP_BRH_ID6
	 */
	public java.lang.Integer getUpBrhId6 () {
		return upBrhId6;
	}

	/**
	 * Set the value related to the column: UP_BRH_ID6
	 * @param upBrhId6 the UP_BRH_ID6 value
	 */
	public void setUpBrhId6 (java.lang.Integer upBrhId6) {
		this.upBrhId6 = upBrhId6;
	}



	/**
	 * Return the value associated with the column: UP_BRH_ID7
	 */
	public java.lang.Integer getUpBrhId7 () {
		return upBrhId7;
	}

	/**
	 * Set the value related to the column: UP_BRH_ID7
	 * @param upBrhId7 the UP_BRH_ID7 value
	 */
	public void setUpBrhId7 (java.lang.Integer upBrhId7) {
		this.upBrhId7 = upBrhId7;
	}



	/**
	 * Return the value associated with the column: UP_BRH_ID8
	 */
	public java.lang.Integer getUpBrhId8 () {
		return upBrhId8;
	}

	/**
	 * Set the value related to the column: UP_BRH_ID8
	 * @param upBrhId8 the UP_BRH_ID8 value
	 */
	public void setUpBrhId8 (java.lang.Integer upBrhId8) {
		this.upBrhId8 = upBrhId8;
	}



	/**
	 * Return the value associated with the column: UP_BRH_ID9
	 */
	public java.lang.Integer getUpBrhId9 () {
		return upBrhId9;
	}

	/**
	 * Set the value related to the column: UP_BRH_ID9
	 * @param upBrhId9 the UP_BRH_ID9 value
	 */
	public void setUpBrhId9 (java.lang.Integer upBrhId9) {
		this.upBrhId9 = upBrhId9;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.ebank.entity.SuperBranchList)) return false;
		else {
			com.huateng.ebank.entity.SuperBranchList superBranchList = (com.huateng.ebank.entity.SuperBranchList) obj;
			if (null == this.getId() || null == superBranchList.getId()) return false;
			else return (this.getId().equals(superBranchList.getId()));
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