package com.huateng.stl4j.getter;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;

import om.util.OmUtils;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.stl4j.bean.RuleUseSelect;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.PageQueryResult;

public class OmRuleListSelectGetter extends BaseGetter {
	private static Logger logger = Logger.getLogger(OmRuleListSelectGetter.class);
	
	public Result call() throws AppException {
		try {
			String bussType = OmUtils.trim(getCommQueryServletRequest().getParameter("bussType"));
			String ruleSetName = OmUtils.trim(getCommQueryServletRequest().getParameter("ruleSetName"));
			
			List<RuleUseSelect> list = getRuleListSelectList(bussType, ruleSetName);
			for(int i = 0; i < list.size(); i ++) {
				RuleUseSelect useSelect = list.get(i);
				if(OmUtils.isEmpty(useSelect.getBussType())) {
					useSelect.setSelect(false);
				}
				else {
					useSelect.setSelect(true);
				}
			}
			PageQueryResult pageQueryResult = new PageQueryResult();
			pageQueryResult.setQueryResult(list);
			pageQueryResult.setTotalCount(list.size());
			ResultMng.fillResultByList(getCommonQueryBean(), getCommQueryServletRequest(),
					pageQueryResult.getQueryResult(), getResult());
			result.setContent(pageQueryResult.getQueryResult());
			result.getPage().setTotalPage(1);
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
	
	@SuppressWarnings("unchecked")
	private List<RuleUseSelect> getRuleListSelectList(String bussType, String ruleSetName) throws Exception {
		StringBuilder sql = new StringBuilder("select rule.RULE_NAME as ruleName, rule.RULE_DESC as ruleDesc, ");
		sql.append("rela.BUSS_TYPE as bussType, rela.RULE_ISOLATION as ruleIsolation, ");
		sql.append("rela.EXEC_ORDER as execOrder, rela.VAR_PARAM1 as varParam1, rela.VAR_PARAM2 as varParam2, ");
		sql.append("rela.VAR_PARAM3 as varParam3, rela.VAR_PARAM4 as varParam4, rela.VAR_PARAM5 as varParam5, ");
		sql.append("rela.VAR_PARAM6 as varParam6 ");
		sql.append("from OM_RULE_INFO rule left outer join OM_RULE_USE_RELATION rela ");
		sql.append("on rela.RULE_NAME=rule.RULE_NAME and rela.BUSS_TYPE='").append(bussType).append("' ");
		if(!OmUtils.isEmpty(ruleSetName) && !ruleSetName.equals("all")) {
			sql.append("where rule.RULE_NAME LIKE '").append(ruleSetName).append(".%'");
		}
		final String querySql = sql.toString();
		return (List<RuleUseSelect>) CommonDAO.getInstance().getHibernateTemplate()
			.executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				return session.createSQLQuery(querySql)
				.addScalar("ruleName")
				.addScalar("ruleDesc")
				.addScalar("bussType")
				.addScalar("ruleIsolation", Hibernate.STRING)
				.addScalar("execOrder", Hibernate.INTEGER)
				.addScalar("varParam1")
				.addScalar("varParam2")
				.addScalar("varParam3")
				.addScalar("varParam4")
				.addScalar("varParam5")
				.addScalar("varParam6")
				.setResultTransformer(Transformers.aliasToBean(RuleUseSelect.class)).list();
			}
		});
	}
}
