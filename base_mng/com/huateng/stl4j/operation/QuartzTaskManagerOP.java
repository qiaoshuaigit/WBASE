package com.huateng.stl4j.operation;

import java.util.List;

import om.util.OmUtils;

import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdScheduler;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.SimpleTriggerBean;

import com.huateng.ebank.entity.SystemParam;
import com.huateng.stl4j.bean.QuartzTaskBean;
import com.huateng.stl4j.common.ApplicationContextUtils;
import com.huateng.stl4j.common.BaseOperation;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.OperationContext;

public class QuartzTaskManagerOP extends BaseOperation {
	public static final String CMD = "CMD";
	public static final String ID = QuartzTaskManagerOP.class.getName();
	public void beforeProc(OperationContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void execute(OperationContext context) throws Exception {
		List<QuartzTaskBean> updList = (List<QuartzTaskBean>) context.get("UPD_LIST");
		CommonDAO dao = CommonDAO.getInstance();
		String command = (String) context.get(CMD);
		StdScheduler scheduler = (StdScheduler) ApplicationContextUtils.getBean("startQuerz");
		for(int i = 0; i < updList.size(); i ++) {
			QuartzTaskBean bean = updList.get(i);
			if("UPDATE".equals(command)) {
				List tmp = dao.findByHQL("select po from SystemParam po where po.paramId='QUARTZ' and po.magicId=?",
						new Object[]{bean.getBeanId()});
				if(tmp.size() > 0) {
					SystemParam sysParam = (SystemParam) tmp.get(0);
					String preValue = OmUtils.trim(sysParam.getParamValue());
					String newValue = OmUtils.trim(bean.getTaskDefine());
					if(!preValue.equals(newValue)) {
						sysParam.setParamValue(newValue);
						dao.saveOrUpdate(sysParam);
						Object trigger = scheduler.getTrigger(bean.getBeanId(), Scheduler.DEFAULT_GROUP);
			        	if(trigger instanceof CronTriggerBean) {
			        		CronTriggerBean cronTrigger = (CronTriggerBean) trigger;
			        		cronTrigger.setCronExpression(newValue);
			        	}
			        	else if(trigger instanceof SimpleTriggerBean) {
			        		SimpleTriggerBean simpleTrigger = (SimpleTriggerBean) trigger;
			        		simpleTrigger.setRepeatInterval(Long.parseLong(newValue));
			        	}
			        	scheduler.rescheduleJob(bean.getBeanId(), Scheduler.DEFAULT_GROUP, (Trigger) trigger);
					}
				}
			}
		}
	}
	
	public void afterProc(OperationContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
