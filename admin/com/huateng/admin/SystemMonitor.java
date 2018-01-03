package com.huateng.admin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import om.util.OmUtils;

import org.apache.log4j.Logger;

public class SystemMonitor extends Thread {
	private static Logger logger = Logger.getLogger(SystemMonitor.class);
	private static SystemMonitor sm = null;
	private boolean runningFlag = false;
	private static Runtime rt = Runtime.getRuntime();
	private static long maxMemory = rt.maxMemory()/1024/1024;
	private static JvmInfo jvmInfo = new JvmInfo();

	private SystemMonitor() {
		setName("SystemMonitor");
		jvmInfo.setMaxMemory(maxMemory);
	}

	public synchronized static SystemMonitor getInstance() {
		if (null == sm) {
			sm = new SystemMonitor();
		}
		return sm;
	}

	public void run() {
		try {
			while (runningFlag) {
				logger.info(getJVMInfo());
				sleep(30000);
			}
		} catch (InterruptedException e) {
			
		}
	}
	
	public static String getJVMInfo() {
		long totalMemory = rt.totalMemory()/1024/1024;
		long freeMemory = rt.freeMemory()/1024/1024;
		
		StringBuilder sb = new StringBuilder();
		sb.append("MaxMemory=").append(maxMemory).append("M,");
		sb.append("TotalMemory=").append(totalMemory).append("M,");
		sb.append("FreeMemory=").append(freeMemory).append("M,");
		sb.append("UsedMemory=").append(totalMemory - freeMemory).append("M");
		JvmBean jvmBean = new JvmBean();
		jvmBean.setTotalMemory(totalMemory);
		jvmBean.setUsedMemory(totalMemory - freeMemory);
		BigDecimal pused = new BigDecimal(jvmBean.getUsedMemory() * 100);
		pused = pused.divide(new BigDecimal(maxMemory), 0, RoundingMode.HALF_UP);
		pused.setScale(0, RoundingMode.HALF_UP);
		jvmBean.setPUsed(pused.intValue());
		jvmBean.setTime(OmUtils.format("HH:mm:ss", new Date()));
		jvmInfo.addJvmBean(jvmBean);
		
		return sb.toString();
	}

	public boolean isRunningFlag() {
		return runningFlag;
	}

	public void setRunningFlag(boolean runningFlag) {
		this.runningFlag = runningFlag;
	}

	public static JvmInfo getJvmInfo() {
		return jvmInfo;
	}
}
