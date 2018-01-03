package com.huateng.stl4j.getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import om.util.OmUtils;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.impl.StdScheduler;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.SimpleTriggerBean;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.entity.SystemParam;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.stl4j.bean.QuartzTaskBean;
import com.huateng.stl4j.common.ApplicationContextUtils;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.PageQueryCondition;
import com.huateng.stl4j.common.PageQueryResult;

public class QuartzTaskManagerGetter extends BaseGetter {
	private static Logger logger = Logger.getLogger(QuartzTaskManagerGetter.class);
	
	public Result call() throws AppException {
		try {
			Integer pageSize = new Integer(getResult().getPage().getEveryPage());
			Integer pageIndex = new Integer(getResult().getPage().getCurrentPage());
			PageQueryCondition condition = new PageQueryCondition();
			condition.setPageIndex(pageIndex);
			condition.setPageSize(pageSize);
			condition.setQueryString("select po from SystemParam po where po.paramId='QUARTZ' order by po.id");
			PageQueryResult queryResult = CommonDAO.getInstance().pageQueryByHQL(condition);
			List<QuartzTaskBean> list = new ArrayList<QuartzTaskBean>();
			Iterator it = queryResult.getQueryResult().iterator();
			StdScheduler scheduler = (StdScheduler) ApplicationContextUtils.getBean("startQuerz");
			while (it.hasNext()) {
	        	Object[] obj = (Object[]) it.next();
	        	SystemParam sysParam = (SystemParam) obj[0];
	        	QuartzTaskBean bean = new QuartzTaskBean();
	        	bean.setBeanId(sysParam.getMagicId());
	        	bean.setTaskName(sysParam.getParamDesc());
	        	bean.setTaskDefine(sysParam.getParamValue());
	        	Object trigger = scheduler.getTrigger(bean.getBeanId(), Scheduler.DEFAULT_GROUP);
	        	if(trigger instanceof CronTriggerBean) {
	        		CronTriggerBean tmp = (CronTriggerBean) trigger;
	        		bean.setNextExecuteTime(OmUtils.format("yyyy-MM-dd HH:mm:ss:SSS", tmp.getNextFireTime()));
	        		bean.setTriggerType("CronTrigger");
	        	}
	        	else if(trigger instanceof SimpleTriggerBean) {
	        		SimpleTriggerBean tmp = (SimpleTriggerBean) trigger;
	        		bean.setNextExecuteTime(OmUtils.format("yyyy-MM-dd HH:mm:ss:SSS", tmp.getNextFireTime()));
	        		bean.setTriggerType("SimpleTrigger");
	        	}
	        	bean.setStatus(String.valueOf(scheduler.getTriggerState(bean.getBeanId(), Scheduler.DEFAULT_GROUP)));
	        	list.add(bean);
			}
			PageQueryResult pageQueryResult = new PageQueryResult();
			pageQueryResult.setQueryResult(list);
			pageQueryResult.setTotalCount(queryResult.getTotalCount());
			ResultMng.fillResultByList(getCommonQueryBean(), getCommQueryServletRequest(),
					pageQueryResult.getQueryResult(), getResult());
			result.setContent(pageQueryResult.getQueryResult());
			result.getPage().setTotalPage(pageQueryResult.getPageCount(getResult().getPage().getEveryPage()));
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
