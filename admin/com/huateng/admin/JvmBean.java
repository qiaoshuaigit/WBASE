package com.huateng.admin;

public class JvmBean {
	private long totalMemory = 0;
	private long usedMemory = 0;
	private String time = "";
	private int pUsed = 0;

	public long getUsedMemory() {
		return usedMemory;
	}

	public void setUsedMemory(long usedMemory) {
		this.usedMemory = usedMemory;
	}

	public long getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}

	public int getPUsed() {
		return pUsed;
	}

	public void setPUsed(int used) {
		pUsed = used;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
