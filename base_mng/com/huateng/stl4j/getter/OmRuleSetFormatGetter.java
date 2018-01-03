package com.huateng.stl4j.getter;

import java.util.ArrayList;
import java.util.List;

import om.code.CodeFormat;
import om.rule.OmRuleSet;
import om.util.OmUtils;

import org.apache.log4j.Logger;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;

public class OmRuleSetFormatGetter extends BaseGetter {
	private static Logger logger = Logger.getLogger(OmRuleSetFormatGetter.class);
	
	public Result call() throws AppException {
		try {
			String javaCode = OmUtils.trim(getCommQueryServletRequest().getParameter("javaCode"));
			OmRuleSet omRuleSet = new OmRuleSet();
			omRuleSet.setJavaCode(CodeFormat.format(javaCode));
			List content = new ArrayList();
			ResultMng.fillResultByObject(getCommonQueryBean(), getCommQueryServletRequest(), omRuleSet, getResult());
			content.add(omRuleSet);
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
