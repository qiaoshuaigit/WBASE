package com.huateng.ebank.topbpm.tools;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import om.util.OmUtils;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.huateng.ebank.common.CommonException;
import com.huateng.stl4j.common.ApplicationContextUtils;
import com.huateng.stl4j.service.SystemParamService;
import com.huateng.tools.DateTool;
import com.huateng.topbpm.TopBPMConfiguration;
import com.huateng.topbpm.TopBPMContext;
import com.huateng.topbpm.db.GraphSession;

/**
 * 清理TOPBPM中数据，针对已经结束的流程（指定过期天数的方式）
 * @author shen_antonio
 *
 */
public class TopBpmCleanJob implements ITopBpmCleanJob {
	private static final Logger logger = Logger.getLogger(TopBpmCleanJob.class);
	private static final Logger quartz = Logger.getLogger("Quartz.TopBpmCleanJob");
	private Boolean doing = false;

	/* 过期天数  .*/
	private int dueDays = 180;

	/* 断点提交笔数.*/
	private int commitCount = 500;

	private int fetchSize = 500;

	public TopBpmCleanJob() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TopBpmCleanJob(int dueDays, int commitCount) {
		super();
		this.dueDays = dueDays;
		this.commitCount = commitCount;
		this.fetchSize = commitCount;
	}

	/* (non-Javadoc)
	 * @see com.huateng.ebank.topbpm.tools.ITopBpmCleanJob#backUp()
	 */
	public void backUp() throws CommonException {
		// TODO Auto-generated method stub
	}

	public void clear() throws CommonException {
		if(SystemParamService.getInstance().isClusterMode()) {
			String serverName1 = OmUtils.trim(System.getProperty("SERVERNAME"));
			String serverName2 = SystemParamService.getInstance().getParamValue("SERVERNAME", "QUERZ");
			if(!serverName1.equals(serverName2)) {
				return;
			}
		}
		synchronized(doing) {
			if(doing) {
				return;
			}
			else {
				doing = true;
			}
		}
		quartz.info("begin TopBpmCleanJob");
		Session session = null;
		Transaction transaction = null;
		int endRow,totleRow;
		try{
			if(logger.isInfoEnabled()){
				logger.info("*********************Clear TOPBPM History Data Clear Begin****************************");
			}
			SessionFactory sessionFactory = (SessionFactory)ApplicationContextUtils.getBean("mySessionFactory");
			session = sessionFactory.openSession();
			TopBPMContext context = TopBPMConfiguration.getInstance().createTopBPMContext();
			context.setSession(session);
			GraphSession graphSession = context.getGraphSession();
			/** 获取系统自然日.*/
	    	Date currDate = new Date();
			if(logger.isInfoEnabled()){
				logger.info("**************Clear Information:System Date[" + DateTool.date2Str(currDate, "yyyy-MM-dd") + "],Clear Interval Day[" + dueDays + "]********************");
			}
	    	/** 获取清理到期日 = 当前系统日期 - 清理间隔天数.*/
			Date dueDate = DateTool.getStartDateByDays(currDate, dueDays);
			if(logger.isInfoEnabled()){
				logger.info("**************Clear Information：System Due Date["+ DateTool.date2Str(dueDate, "yyyy-MM-dd")+ "]***********************");
			}
			Calendar dueDateCalendar =  Calendar.getInstance();
			dueDateCalendar.setTime(dueDate);
			dueDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
			dueDateCalendar.set(Calendar.MINUTE, 0);
			dueDateCalendar.set(Calendar.SECOND, 0);
			int count = 0; // 清理流程个数
			int pCommitCount = 0;
			String processId = null;
			Date processStart = null;
			Date processEnd = null;
			while (true) {
				Query query = session.createQuery("select pi.id, pi.start , pi.end from com.huateng.topbpm.graph.exe.ProcessInstance as pi where pi.end <> null and pi.end <= :end");
				query.setDate("end", dueDateCalendar.getTime());
				query.setFirstResult(0);
				query.setMaxResults(fetchSize);
				List proInsList = query.list();
				if(proInsList.isEmpty()){
					break;
				}
				Iterator proInsIt = proInsList.iterator();
				transaction = session.beginTransaction();
				while (proInsIt.hasNext()) {
					count++;
					pCommitCount++;
					Object[] processInstArg = (Object[]) proInsIt.next();
					processId = processInstArg[0].toString();
					processStart = (Date) processInstArg[1];
					processEnd = (Date) processInstArg[2];
					if (logger.isDebugEnabled()) {
						logger.debug("Clear Number[" + count
								+ "]: Process Instance[" + processId + "],"
								+ "Start Time[" + processStart + "],end Time["
								+ processEnd + "]");
					}
					/* 清理流程数据：流程实例、流程节点、流程变量 . */
					graphSession.deleteProcessInstance(Long
							.parseLong(processId));
					/* 调用相关信息程序. */
					referenceDataClear(Long.parseLong(processId), session);
					/** flush session . */
					session.flush();
					/* 断点提交：默认为500条提交. */
					if (count / commitCount >= 1 && count % commitCount == 0) {
						if(logger.isInfoEnabled()){
							logger.info("Transaction Commit , Clear ProcessInstance: " + pCommitCount);
						}
						transaction.commit();
						pCommitCount = 0;
					}
				}
			}
			if (pCommitCount > 0) {
				transaction.commit();
				if(logger.isInfoEnabled()){
					logger.info("Transaction Commit , Clear ProcessInstance: " + pCommitCount);
				}
			}
			if(logger.isInfoEnabled()){
				logger.info("Totle Clear ProcessInstance: " + count);
				logger.info("*********************Clear TOPBPM History Data Clear End****************************");
			}
			quartz.info("finished TopBpmCleanJob, clear count is " + count);
		}catch(Exception ex){
			logger.error("******************Clear TOPBPM History Data Clear Fail, The End****************************",ex);
			if(transaction!=null){
				transaction.rollback();
			}
			throw new CommonException("clear TopBpm Process Instance Error",ex);
		}finally{
			try {
				if (session != null) {
					if (session.isOpen()) {
						session.close();
					}
				}
			} catch (Exception e) {
				logger.error("close session error", e);
			}
			doing = false;
		}
	}

	public void setCommitCount(int commitCount){
		this.commitCount = commitCount;
		this.fetchSize = commitCount;
	}

	public int getCommitCount(){
		return commitCount;
	}


	public int getDueDays() {
		// TODO Auto-generated method stub
		return this.dueDays;
	}

	public void setDueDays(int dueDays) {
		// TODO Auto-generated method stub
		this.dueDays = dueDays;
	}

	public void referenceDataClear(Long processInstanceId,Session session)throws CommonException{

	}
}
