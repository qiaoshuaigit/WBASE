package com.huateng.ebank.entity.base;

import java.io.Serializable;


public abstract class BaseRuleUseRelationPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String ruleName;
	private java.lang.String bussType;


	public BaseRuleUseRelationPK () {}
	
	public BaseRuleUseRelationPK (
		java.lang.String ruleName,
		java.lang.String bussType) {

		this.setRuleName(ruleName);
		this.setBussType(bussType);
	}


	/**
	 * Return the value associated with the column: RULE_NAME
	 */
	public java.lang.String getRuleName () {
		return ruleName;
	}

	/**
	 * Set the value related to the column: RULE_NAME
	 * @param ruleName the RULE_NAME value
	 */
	public void setRuleName (java.lang.String ruleName) {
		this.ruleName = ruleName;
	}



	/**
	 * Return the value associated with the column: BUSS_TYPE
	 */
	public java.lang.String getBussType () {
		return bussType;
	}

	/**
	 * Set the value related to the column: BUSS_TYPE
	 * @param bussType the BUSS_TYPE value
	 */
	public void setBussType (java.lang.String bussType) {
		this.bussType = bussType;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.ebank.entity.RuleUseRelationPK)) return false;
		else {
			com.huateng.ebank.entity.RuleUseRelationPK mObj = (com.huateng.ebank.entity.RuleUseRelationPK) obj;
			if (null != this.getRuleName() && null != mObj.getRuleName()) {
				if (!this.getRuleName().equals(mObj.getRuleName())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getBussType() && null != mObj.getBussType()) {
				if (!this.getBussType().equals(mObj.getBussType())) {
					return false;
				}
			}
			else {
				return false;
			}
			return true;
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			StringBuilder sb = new StringBuilder();
			if (null != this.getRuleName()) {
				sb.append(this.getRuleName().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getBussType()) {
				sb.append(this.getBussType().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			this.hashCode = sb.toString().hashCode();
		}
		return this.hashCode;
	}


}