package com.huateng.ebank.business.common;

import java.net.UnknownHostException;

import om.util.OmUtils;

import com.huateng.stl4j.service.SystemParamService;

public class DoClusterNoticeProcess {
	public void doNoticeProcess() {
		if(!SystemParamService.getInstance().isClusterMode()) return; //非集群模式不需要处理消息
		ClusterNoticeProcesser.getInstance().processClusterNotices();
	}
	
	public void clearNoticeProcess() {
		if(!SystemParamService.getInstance().isClusterMode()) return; //非集群模式不需要处理消息
		String serverName1 = OmUtils.trim(System.getProperty("SERVERNAME"));
		String serverName2 = SystemParamService.getInstance().getParamValue("SERVERNAME", "QUERZ");
		if(!serverName1.equals(serverName2)) {
			return;
		}
		ClusterNoticeProcesser.getInstance().clearClusterNotices();
	}
	
	public void activeNoticeProcess() {
		if(!SystemParamService.getInstance().isClusterMode()) return; //非集群模式不需要处理消息
		try {
			ClusterNoticeProcesser.getInstance().activeNoticeProcess();
		} catch (UnknownHostException e) {
		}
	}
}

