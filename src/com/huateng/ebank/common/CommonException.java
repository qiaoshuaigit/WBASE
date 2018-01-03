package com.huateng.ebank.common;


import org.apache.commons.lang.builder.ToStringBuilder;
import om.util.OmUtils;

import com.huateng.exception.AppException;
import com.huateng.stl4j.common.ActionExceptionHandler;
import com.huateng.stl4j.common.MessageResources;

public class CommonException extends AppException {
	private static final long serialVersionUID = -1772609246029549670L;
	private String key;
	private Object[] objs = null;

	public CommonException() {
		super();
	}

	public CommonException(String errorMsg) {
		super(errorMsg);
	}

	public CommonException(String errorMsg, Throwable t) {
		super(errorMsg, t);
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	public Object[] getObjs() {
		return objs;
	}

	public void setObjs(Object[] objs) {
		this.objs = objs;
	}

	public String message() {
		return new ToStringBuilder(this).append("message", this.getMessage())
				.append("key", key).toString();
	}
	
	public String getMessage() {
		String errorMsg = "";
		String detailMsg = super.getMessage();

		Object[] objs = this.getObjs();
		String key = this.getKey();

		if(OmUtils.trim(key) != "") {
			MessageResources mr = MessageResources.getInstance();
			errorMsg = mr.getMessage(key, objs);
			if(null == errorMsg) {
				//errorMsg = "未知的错误代码定义: " + key;
				errorMsg = key;
			}
			else {
				errorMsg = "错误代码: " + key + "-" + errorMsg + "！";
			}
		}

		StringBuffer sb = new StringBuffer(errorMsg);
		if(null != detailMsg && !"".equals(detailMsg)) {
			if(detailMsg.indexOf("Exception") < 0) {
				if(detailMsg.indexOf("错误信息: ") < 0) {
					if(sb.length() > 0) {
						sb.append("\n");
					}
					sb.append("错误信息: ");
				}
				sb.append(detailMsg);
			}
		}
		
		String msg = sb.toString();
		if (msg.length() > 256) {
			msg = msg.substring(0, 256) + "......";
		}
		return msg;
	}
}
