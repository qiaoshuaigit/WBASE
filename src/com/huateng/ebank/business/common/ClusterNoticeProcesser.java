package com.huateng.ebank.business.common;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import om.util.OmUtils;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdScheduler;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.SimpleTriggerBean;

import com.huateng.ebank.entity.ClusterMsgNotice;
import com.huateng.ebank.entity.SystemParam;
import com.huateng.stl4j.common.ApplicationContextUtils;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.service.ErrorCodeService;
import com.huateng.stl4j.service.SystemParamService;
import com.huateng.tools.DateTool;


public class ClusterNoticeProcesser {
	private CommonDAO dao;
	private static Logger logger = Logger.getLogger(ClusterNoticeProcesser.class);
	private static final Logger quartz = Logger.getLogger("Quartz.ClusterNoticeProcesser");
	private static String localServerName = OmUtils.trim(System.getProperty("SERVERNAME"));
	public static String MSGTYPE_DATADIC_RELOAD = "1001"; //DATA_DIC表数据RELOAD
	public static String MSGTYPE_SYSTEMPARAM_RELOAD = "1002"; //SYSTEMPARAM表数据RELOAD
	public static String MSGTYPE_ERRORCODE_RELOAD = "1003"; //ERRORCODE表数据RELOAD
	public static String MSGTYPE_CLUSTER_ACTIVE = "9999"; //集群活动状态
	
	protected ClusterNoticeProcesser() {
		if(OmUtils.isEmpty(localServerName)) {
			localServerName = "DEFAULT";
		}
	}

	public static ClusterNoticeProcesser getInstance() {
		return (ClusterNoticeProcesser) ApplicationContextUtils.getBean("ClusterNoticeProcesser");
	}

	/**
	 * 发布消息到集群
	 * @param msgType
	 * @param misc
	 */
	public void sendNoticeToCluster(String msgType, String misc) {
		if(!SystemParamService.getInstance().isClusterMode()) return; //非集群模式不需要发布消息
		ClusterMsgNotice clusterMsgNotice = new ClusterMsgNotice();
		clusterMsgNotice.setSysDate(DateTool.getDate());
		clusterMsgNotice.setSender(localServerName);
		clusterMsgNotice.setReceiver("-");
		clusterMsgNotice.setMsgSeq(dao.getNextValueOfSequences("SEQ_CLUSTER_MSG_SEQ"));
		clusterMsgNotice.setMsgType(msgType);
		clusterMsgNotice.setMisc(misc);
		dao.insert(clusterMsgNotice);
		logger.info(new StringBuilder("Sent Notice to Cluster MsgType=").append(msgType)
			.append(" MsgSeq=").append(clusterMsgNotice.getMsgSeq())
			.append(",Misc=").append(clusterMsgNotice.getMisc()).toString());
	}

	/**
	 * 查找非本机发布的且本机未处理的消息
	 * @return List
	 */
	public List findPendingClusterNotices() {
		StringBuilder sql = new StringBuilder("select ps from ClusterMsgNotice ps ");
		sql.append("where ps.sender!='").append(localServerName).append("' and ps.receiver='-' ");
		sql.append("and not exists(select 1 from ClusterMsgNotice pr where ");
		sql.append("pr.receiver='").append(localServerName).append("' and pr.msgSeq=ps.msgSeq)");
		return dao.findByHQL(sql.toString());
	}

	/**
	 * 保存一条本机收到消息的处理记录
	 * @param sendNotice
	 */
	public void saveReceivedClusterNotices(ClusterMsgNotice sendNotice) {
		ClusterMsgNotice clusterMsgNotice = new ClusterMsgNotice();
		clusterMsgNotice.setSysDate(sendNotice.getSysDate());
		clusterMsgNotice.setSender(sendNotice.getSender());
		clusterMsgNotice.setReceiver(localServerName);
		clusterMsgNotice.setMsgSeq(sendNotice.getMsgSeq());
		clusterMsgNotice.setMsgType(sendNotice.getMsgType());
		clusterMsgNotice.setMisc(sendNotice.getMisc());
		dao.insert(clusterMsgNotice);
		logger.info(new StringBuilder("Processed Notice from Cluster's ").append(sendNotice.getSender())
			.append(" MsgType=").append(sendNotice.getMsgType())
			.append(" MsgSeq=").append(sendNotice.getMsgSeq())
			.append(",Misc=").append(clusterMsgNotice.getMisc()).toString());
	}

	public void delClusterNotices(ClusterMsgNotice sendNotice) {
		dao.delete(sendNotice);
	}

	/**
	 * 处理集群消息
	 */
	public void processClusterNotices(){
		if(!SystemParamService.getInstance().isClusterMode()) return; //非集群模式不需要处理消息
		List list = findPendingClusterNotices();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			ClusterMsgNotice clusterMsgNotice = (ClusterMsgNotice) it.next();
			doProcessNotice(clusterMsgNotice);
		}
	}
	
	public void clearClusterNotices() {
		if(!SystemParamService.getInstance().isClusterMode()) return; //非集群模式不需要处理消息
		try {
			List list = dao.findByHQL("select po from ClusterMsgNotice po where po.sysDate<?",
					new Object[]{DateTool.getDate()});
			for(int i = 0; i < list.size(); i ++) {
				ClusterMsgNotice po = (ClusterMsgNotice) list.get(i);
				delClusterNotices(po);
			}
		} catch(Exception e) {
			logger.error("clearClusterNotices Error", e);
		}
	}

	private void doProcessNotice(ClusterMsgNotice clusterMsgNotice){
		try {
			logger.info(new StringBuilder("Processing Notice of MsgType=").append(clusterMsgNotice.getMsgType())
				.append(",MsgSeq=").append(clusterMsgNotice.getMsgSeq())
				.append(",Misc=").append(clusterMsgNotice.getMisc()).toString());
			if(MSGTYPE_DATADIC_RELOAD.equals(clusterMsgNotice.getMsgType())) {
				DataDicUtils.initDataDic();
			}
			else if(MSGTYPE_SYSTEMPARAM_RELOAD.equals(clusterMsgNotice.getMsgType())) {
				SystemParamService.getInstance().reload();
				refreshQuartz();
			}
			else if(MSGTYPE_ERRORCODE_RELOAD.equals(clusterMsgNotice.getMsgType())) {
				ErrorCodeService.getInstance().reload();
			}
			else {
				logger.error(new StringBuilder("Not Defined Cluster Notice(MSGTYPE=")
					.append(clusterMsgNotice.getMsgType()).append("), so delete it.").toString());
				delClusterNotices(clusterMsgNotice);
				return;
			}
			saveReceivedClusterNotices(clusterMsgNotice);
		} catch(Exception e) {
			logger.error(new StringBuilder("Process Cluster Notice(MSGTYPE=")
				.append(clusterMsgNotice.getMsgType()).append(") Error").toString(), e);
		}
	}
	
	private void refreshQuartz() throws Exception {
		StdScheduler scheduler = (StdScheduler) ApplicationContextUtils.getBean("startQuerz");
		List list = dao.findByHQL("select po from SystemParam po where po.paramId='QUARTZ' order by po.id");
		boolean rescheduleJob = false;
		for(int i = 0; i < list.size(); i ++) {
			SystemParam sysParam = (SystemParam) list.get(i);
			rescheduleJob = false;
			Object trigger = scheduler.getTrigger(sysParam.getMagicId(), Scheduler.DEFAULT_GROUP);
			if(trigger instanceof CronTriggerBean) {
				CronTriggerBean tmp = (CronTriggerBean) trigger;
				if(!tmp.getCronExpression().equals(sysParam.getParamValue())) {
					tmp.setCronExpression(sysParam.getParamValue());
					rescheduleJob = true;
				}
			}
			else if(trigger instanceof SimpleTriggerBean) {
				SimpleTriggerBean tmp = (SimpleTriggerBean) trigger;
				if(tmp.getRepeatInterval() != Long.parseLong(sysParam.getParamValue())) {
					tmp.setRepeatInterval(Long.parseLong(sysParam.getParamValue()));
					rescheduleJob = true;
				}
			}
			if(rescheduleJob) {
				scheduler.rescheduleJob(sysParam.getMagicId(), Scheduler.DEFAULT_GROUP, (Trigger) trigger);
			}
		}
	}
	
	public void activeNoticeProcess() throws UnknownHostException {
		sendNoticeToCluster();
		String serverName = SystemParamService.getInstance().getParamValue("SERVERNAME", "QUERZ");
		String lastActiveName = serverName;
		try {
			List list = dao.findByNamedQuery("ActiveNoticeList",
					new Serializable[]{MSGTYPE_CLUSTER_ACTIVE}, 0, 10);
			int count = 0;
			for(int i = 0; i < list.size(); i ++) {
				ClusterMsgNotice po = (ClusterMsgNotice) list.get(i);
				String activeName = po.getSender();
				if(!serverName.equals(activeName)) {
					count ++;
					if(count >= 3) {
						break;
					}
				}
				else {
					count = 0;
					break;
				}
				lastActiveName = activeName;
			}
			if(count >= 3) {
				SystemParamService.getInstance().noticeActiveServer(lastActiveName);
			}
			list = null;
		} catch(Exception e) {
		}
		quartz.info("Querz server is " + SystemParamService.getInstance().getParamValue("SERVERNAME", "QUERZ"));
		List list = dao.findByHQL("select count(po.id) from ClusterMsgNotice po where po.sender=? and po.msgType=?",
				new Object[]{localServerName, MSGTYPE_CLUSTER_ACTIVE});
		if(list.size() > 0) {
			long count = (Long) list.get(0);
			if(count > 30) {
				list = dao.findByNamedQuery("ExpiredActiveNoticeList",
						new Serializable[]{localServerName, MSGTYPE_CLUSTER_ACTIVE}, 0, 20);
				for(int i = 0; i < list.size(); i ++) {
					ClusterMsgNotice cmn = (ClusterMsgNotice) list.get(i);
					delClusterNotices(cmn);
				}
			}
		}
	}
	
	/**
	 * 发布活动状态到集群
	 * @param msgType
	 * @param misc
	 * @throws UnknownHostException 
	 */
	public void sendNoticeToCluster() throws UnknownHostException {
		if(!SystemParamService.getInstance().isClusterMode()) return; //非集群模式不需要发布消息
		ClusterMsgNotice clusterMsgNotice = new ClusterMsgNotice();
		clusterMsgNotice.setSysDate(DateTool.getDate());
		clusterMsgNotice.setSender(localServerName);
		clusterMsgNotice.setReceiver("all");
		clusterMsgNotice.setMsgSeq(dao.getNextValueOfSequences("SEQ_CLUSTER_MSG_SEQ"));
		clusterMsgNotice.setMsgType(MSGTYPE_CLUSTER_ACTIVE);
		clusterMsgNotice.setMisc(InetAddress.getLocalHost().getHostAddress() + " active at " + OmUtils.format("yyyy-MM-dd HH:mm:ss:SSS", new Date()));
		dao.insert(clusterMsgNotice);
//		logger.info(new StringBuilder("Sent Notice to Cluster MsgType=").append(MSGTYPE_CLUSTER_ACTIVE)
//			.append(" MsgSeq=").append(clusterMsgNotice.getMsgSeq())
//			.append(",Misc=").append(clusterMsgNotice.getMisc()).toString());
	}

	public CommonDAO getDao() {
		return dao;
	}

	public void setDao(CommonDAO dao) {
		this.dao = dao;
	}
}
