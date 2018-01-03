package com.huateng.ebank.topbpm.tools;

import org.hibernate.Session;

import com.huateng.ebank.common.CommonException;

/**
 * 清理TOPBPM中数据，针对已经结束的流程（指定过期天数的方式）
 * @author shen_antonio
 *
 */
public interface ITopBpmCleanJob extends java.io.Serializable{

	public abstract void setDueDays(int dueDays);
	
	public abstract int getDueDays();
	
	public abstract void setCommitCount(int commitCount);
	
	public abstract int getCommitCount();
	
	/**
	 * backup date
	 * @throws CommonException
	 */
	public abstract void backUp()throws CommonException;
	
	/**
	 * clean date
	 * @throws CommonException
	 */
	public abstract void clear()throws CommonException;
	
	/**
	 * 相关数据清理
	 * @throws CommonException
	 */
	public abstract void referenceDataClear(Long processInstanceId,Session session)throws CommonException;
	
	
}
