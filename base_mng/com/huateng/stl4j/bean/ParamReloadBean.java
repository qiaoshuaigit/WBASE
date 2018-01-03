package com.huateng.stl4j.bean;

import com.huateng.ebank.entity.SystemParam;

public class ParamReloadBean extends SystemParam{
	private static final long serialVersionUID = 3603523400800016618L;
	private Boolean select = false;

	public Boolean getSelect() {
		return select;
	}

	public void setSelect(Boolean select) {
		this.select = select;
	}
}
