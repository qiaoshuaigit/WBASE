package com.huateng.stl4j.getter;

import java.util.ArrayList;
import java.util.List;

import om.util.OmUtils;

import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.stl4j.bean.SuccessMessage;

public class SuccessMessageGetter extends BaseGetter {
	public Result call() throws AppException {
		try {
			String msg = OmUtils.trim((String) getSessionObject("SuccessMessage"));
			SuccessMessage bean = new SuccessMessage();
			bean.setMessage(msg);
			ResultMng.fillResultByObject(getCommonQueryBean(), getCommQueryServletRequest(), bean, getResult());
			List<SuccessMessage> content = new ArrayList<SuccessMessage>();
			content.add(bean);
			result.setContent(content);
			result.init();
		} catch(Exception e) {
		}
		return result;
	}
}
