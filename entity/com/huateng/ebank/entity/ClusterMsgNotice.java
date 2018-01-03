package com.huateng.ebank.entity;


import java.io.Serializable;

/**
 * 集群消息通知表
 * @author OM
 * @date 2013-03-25
 */
public class ClusterMsgNotice implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * 类名称
	 */
	public static final String REF="ClusterMsgNotice";

	/**
	 * 序号 的属性名
	 */
	public static final String PROP_ID="id";

	/**
	 * 系统日期 的属性名
	 */
	public static final String PROP_SYSDATE="sysDate";

	/**
	 * 发送方 的属性名
	 */
	public static final String PROP_SENDER="sender";

	/**
	 * 接收方 的属性名
	 */
	public static final String PROP_RECEIVER="receiver";

	/**
	 * 消息编号 的属性名
	 */
	public static final String PROP_MSGSEQ="msgSeq";

	/**
	 * 消息类型 的属性名
	 */
	public static final String PROP_MSGTYPE="msgType";

	/**
	 * 备注 的属性名
	 */
	public static final String PROP_MISC="misc";

	/**
	 * 序号
	 */
	private java.lang.Integer id;

	/**
	 * 系统日期
	 */
	private java.lang.String sysDate;

	/**
	 * 发送方
	 */
	private java.lang.String sender;

	/**
	 * 接收方
	 */
	private java.lang.String receiver;

	/**
	 * 消息编号
	 */
	private java.lang.Integer msgSeq;

	/**
	 * 消息类型
	 */
	private java.lang.String msgType;

	/**
	 * 备注
	 */
	private java.lang.String misc;

	/**
	 * 获取 序号 的属性值
	 * @return id : 序号
	 * @author OM
	 */
	public java.lang.Integer getId(){
		return this.id;
	}

	/**
	 * 设置 序号 的属性值
	 * @param id : 序号
	 * @author OM
	 */
	public void setId(java.lang.Integer id){
		this.id	= id;
	}

	/**
	 * 获取 系统日期 的属性值
	 * @return sysDate : 系统日期
	 * @author OM
	 */
	public java.lang.String getSysDate(){
		return this.sysDate;
	}

	/**
	 * 设置 系统日期 的属性值
	 * @param sysDate : 系统日期
	 * @author OM
	 */
	public void setSysDate(java.lang.String sysDate){
		this.sysDate	= sysDate;
	}

	/**
	 * 获取 发送方 的属性值
	 * @return sender : 发送方
	 * @author OM
	 */
	public java.lang.String getSender(){
		return this.sender;
	}

	/**
	 * 设置 发送方 的属性值
	 * @param sender : 发送方
	 * @author OM
	 */
	public void setSender(java.lang.String sender){
		this.sender	= sender;
	}

	/**
	 * 获取 接收方 的属性值
	 * @return receiver : 接收方
	 * @author OM
	 */
	public java.lang.String getReceiver(){
		return this.receiver;
	}

	/**
	 * 设置 接收方 的属性值
	 * @param receiver : 接收方
	 * @author OM
	 */
	public void setReceiver(java.lang.String receiver){
		this.receiver	= receiver;
	}

	/**
	 * 获取 消息编号 的属性值
	 * @return msgSeq : 消息编号
	 * @author OM
	 */
	public java.lang.Integer getMsgSeq(){
		return this.msgSeq;
	}

	/**
	 * 设置 消息编号 的属性值
	 * @param msgSeq : 消息编号
	 * @author OM
	 */
	public void setMsgSeq(java.lang.Integer msgSeq){
		this.msgSeq	= msgSeq;
	}

	/**
	 * 获取 消息类型 的属性值
	 * @return msgType : 消息类型
	 * @author OM
	 */
	public java.lang.String getMsgType(){
		return this.msgType;
	}

	/**
	 * 设置 消息类型 的属性值
	 * @param msgType : 消息类型
	 * @author OM
	 */
	public void setMsgType(java.lang.String msgType){
		this.msgType	= msgType;
	}

	/**
	 * 获取 备注 的属性值
	 * @return misc : 备注
	 * @author OM
	 */
	public java.lang.String getMisc(){
		return this.misc;
	}

	/**
	 * 设置 备注 的属性值
	 * @param misc : 备注
	 * @author OM
	 */
	public void setMisc(java.lang.String misc){
		this.misc	= misc;
	}

	/**
	 * 转换为字符串
	 * @author OM
	 */
	public String toString(){
		return "id=" + id;
	}
	/**
	 * 转换为 JSON 字符串
	 * @author OM
	 */
	public String toJson(){
		return "id=" + id;
	}
}