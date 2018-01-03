package com.huateng.stl4j.getter;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.stl4j.bean.RuleSetCompileResult;

public class OmRuleSetMessageGetter extends BaseGetter {
	private static Logger logger = Logger.getLogger(OmRuleSetMessageGetter.class);
	
	public Result call() throws AppException {
		try {
			List content = new ArrayList();
			RuleSetCompileResult bean = new RuleSetCompileResult();
			bean.setResult(((String) getSessionObject("OmRuleSetMessage")).replaceAll("\t", "        "));
			ResultMng.fillResultByObject(getCommonQueryBean(), getCommQueryServletRequest(), bean, getResult());
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
