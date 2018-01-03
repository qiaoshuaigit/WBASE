package com.huateng.stl4j.getter;

import java.util.ArrayList;
import java.util.List;

import om.util.OmUtils;

import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.stl4j.bean.SimpleQueryBean;

public class SimpleQueryGetter extends BaseGetter {
	public Result call() throws AppException {
		//String accsystemCode = OmUtils.trim(this.getCommQueryServletRequest().getParameter("AccsystemCode"));
		List list = new ArrayList();
		for(int i = 1; i < 51; i ++) {
			SimpleQueryBean bean = new SimpleQueryBean();
			bean.setFieldName("字段名" + i);
			bean.setFieldValue("测试" + i);
			list.add(bean);
		}
		ResultMng.fillResultByList(getCommonQueryBean(), getCommQueryServletRequest(),
				list, getResult());
		result.setContent(list);
		result.getPage().setTotalPage(1);
		result.init();
		return result;
	}
}
