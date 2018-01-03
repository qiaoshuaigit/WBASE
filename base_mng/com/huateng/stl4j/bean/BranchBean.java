package com.huateng.stl4j.bean;

import java.io.Serializable;

public class BranchBean implements Serializable {
	private static final long serialVersionUID = 6112472469843017963L;
	private Integer id;
	private String brhNo;
	private String brhName;
	private String brhNoName;
	private Long oprOnlineCount;

	public String getBrhNoName() {
		return brhNo + "-" + brhName;
	}

	public void setBrhNoName(String brhNoName) {
		this.brhNoName = brhNoName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBrhNo() {
		return brhNo;
	}

	public void setBrhNo(String brhNo) {
		this.brhNo = brhNo;
	}

	public String getBrhName() {
		return brhName;
	}

	public void setBrhName(String brhName) {
		this.brhName = brhName;
	}

	public Long getOprOnlineCount() {
		return oprOnlineCount;
	}

	public void setOprOnlineCount(Long oprOnlineCount) {
		this.oprOnlineCount = oprOnlineCount;
	}

}
