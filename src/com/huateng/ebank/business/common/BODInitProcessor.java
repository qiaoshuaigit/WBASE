package com.huateng.ebank.business.common;

import java.util.List;

import om.util.OmUtils;

import org.apache.log4j.Logger;

import com.huateng.ebank.business.common.service.CommonService;
import com.huateng.ebank.entity.Operator;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.service.SystemParamService;
import com.huateng.tools.DateTool;

/**
 * 日初初始化 
 * @author "xugang"
 */
public class BODInitProcessor {
	private static final Logger logger = Logger.getLogger(BODInitProcessor.class);
	private static final Logger quartz = Logger.getLogger("Quartz.BODInitProcessor");
	private Boolean doing = false;
	private CommonDAO dao;
	
	public void bodInitProcess() {
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
		try {
			quartz.info("Begin BOD Process!");
			logoutProcess();
			//清理集群通知消息
			ClusterNoticeProcesser.getInstance().clearClusterNotices();
			quartz.info("End BOD Process!");
		} finally {
			doing = false;
		}
	}

	/**
	 * 操作员登出处理
	 */
	private void logoutProcess() {
		try {
			String today = DateTool.getDate();
			List list = dao.findByHQL("select po from Operator po where po.statusIn='1'");
			for(int i = 0; i < list.size(); i ++) {
				Operator op = (Operator) list.get(i);
				if(!OmUtils.trim(op.getInTime()).startsWith(today)) { //不是当天登录的记录
					CommonService.getInstance().oprInOutLog(op.getOprNo(), "out");
					logger.info("logout oprNo: " + op.getOprNo());
				}
			}
		} catch(Exception e) {
			logger.error("logoutProcess出错", e);
		}
	}
	
	public CommonDAO getDao() {
		return dao;
	}

	public void setDao(CommonDAO dao) {
		this.dao = dao;
	}
}
