package com.huateng.stl4j.getter;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.common.GlobalInfo;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.stl4j.bean.ChgPasswdBean;

public class ChgPasswdGetter extends BaseGetter {
	private static Logger logger = Logger.getLogger(ChgPasswdGetter.class);
	public Result call() throws AppException {
		try {
			GlobalInfo g = GlobalInfo.getCurrentInstance();
			ChgPasswdBean bean = new ChgPasswdBean();
			bean.setId(g.getUserId());
			bean.setOprNo(g.getOprNo());
			bean.setUserName(g.getUserName());
			ResultMng.fillResultByObject(getCommonQueryBean(), getCommQueryServletRequest(), bean, getResult());
			List<ChgPasswdBean> content = new ArrayList<ChgPasswdBean>();
			content.add(bean);
			result.setContent(content);
			result.init();
			return result;
		} catch (CommonException e) {
			logger.error(e.getClass().getSimpleName(), e);
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, e.getMessage());
		} catch (AppException appEx) {
			logger.error(appEx.getClass().getSimpleName(), appEx);
			throw appEx;
		} catch(Exception ex) {
			logger.error(ex.getClass().getSimpleName(), ex);
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}
	}
}
