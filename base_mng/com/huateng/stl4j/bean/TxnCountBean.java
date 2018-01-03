package com.huateng.stl4j.bean;

public class TxnCountBean {
	private String txnType;
	private int txnCount;
	private String txnCgl;
	private String tradChl;

	public int getTxnCount() {
		return txnCount;
	}

	public void setTxnCount(int txnCount) {
		this.txnCount = txnCount;
	}

	public String getTxnCgl() {
		return txnCgl;
	}

	public void setTxnCgl(String txnCgl) {
		this.txnCgl = txnCgl;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getTradChl() {
		return tradChl;
	}

	public void setTradChl(String tradChl) {
		this.tradChl = tradChl;
	}
}
