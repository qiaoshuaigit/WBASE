package com.huateng.stl4j.entity;


import java.io.Serializable;

/**
 * 数据类型定义表
 * @author OM
 * @date 2015-04-28
 */
public class TblAtmStat implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * 类名称
	 */
	public static final String REF="TblAtmStat";

	/**
	 * ATM所在地址(中文) 的属性名
	 */
	public static final String PROP_ADDRESS="address";

	/**
	 * ATM所在地址(英文) 的属性名
	 */
	public static final String PROP_ADDRESSEN="addressEn";

	/**
	 * 广告版本号 的属性名
	 */
	public static final String PROP_ADVERNO="adverNo";

	/**
	 * 广告信息 的属性名
	 */
	public static final String PROP_ADINFOR="adInfor";

	/**
	 * 主密钥 的属性名
	 */
	public static final String PROP_ATMBMK="atmBmk";

	/**
	 * ATM MACkey 的属性名
	 */
	public static final String PROP_ATMMAK="atmMak";

	/**
	 * ATM PINkey 的属性名
	 */
	public static final String PROP_ATMPIK="atmPik";

	/**
	 * ATM流水号 的属性名
	 */
	public static final String PROP_ATMSSN="atmSsn";

	/**
	 * ATM批次 的属性名
	 */
	public static final String PROP_BATCHNUMBER="batchNumber";

	/**
	 * 第1种面额装钞数 的属性名
	 */
	public static final String PROP_BOX1COUNT="box1Count";

	/**
	 * 钞匣一出钞数 的属性名
	 */
	public static final String PROP_BOX1OUTQT="box1OutQt";

	/**
	 * 第一钞箱状态 的属性名
	 */
	public static final String PROP_BOX1STATUS="box1Status";

	/**
	 * 钞匣一至废钞匣数 的属性名
	 */
	public static final String PROP_BOX1TOWBOXQT="box1ToWboxQt";

	/**
	 * 第2种面额装钞数 的属性名
	 */
	public static final String PROP_BOX2COUNT="box2Count";

	/**
	 * 钞匣2出钞数 的属性名
	 */
	public static final String PROP_BOX2OUTQT="box2OutQt";

	/**
	 * 第2钞箱状态 的属性名
	 */
	public static final String PROP_BOX2STATUS="box2Status";

	/**
	 * 钞匣2至废钞匣数 的属性名
	 */
	public static final String PROP_BOX2TOWBOXQT="box2ToWboxQt";

	/**
	 * 第3种面额装钞数 的属性名
	 */
	public static final String PROP_BOX3COUNT="box3Count";

	/**
	 * 钞匣3出钞数 的属性名
	 */
	public static final String PROP_BOX3OUTQT="box3OutQt";

	/**
	 * 第3钞箱状态 的属性名
	 */
	public static final String PROP_BOX3STATUS="box3Status";

	/**
	 * 钞匣3至废钞匣数 的属性名
	 */
	public static final String PROP_BOX3TOWBOXQT="box3ToWboxQt";

	/**
	 * 第4种面额装钞数 的属性名
	 */
	public static final String PROP_BOX4COUNT="box4Count";

	/**
	 * 钞匣4出钞数 的属性名
	 */
	public static final String PROP_BOX4OUTQT="box4OutQt";

	/**
	 * 第4钞箱状态 的属性名
	 */
	public static final String PROP_BOX4STATUS="box4Status";

	/**
	 * 钞匣4至废钞匣数 的属性名
	 */
	public static final String PROP_BOX4TOWBOXQT="box4ToWboxQt";

	/**
	 *  ATM行号（机构代码）  的属性名
	 */
	public static final String PROP_BRNO="brno";

	/**
	 * callDownDate 的属性名
	 */
	public static final String PROP_CALLDOWNDATE="callDownDate";

	/**
	 * callUpDate 的属性名
	 */
	public static final String PROP_CALLUPDATE="callUpDate";

	/**
	 * 没收卡片总数 的属性名
	 */
	public static final String PROP_CAPCARDCOUNT="capcardCount";

	/**
	 * 磁条读写器状态 的属性名
	 */
	public static final String PROP_CARDRWSTAT="cardrwStat";

	/**
	 * 磁条卡日终日期时间  new add 的属性名
	 */
	public static final String PROP_CARDBATCHTIME="cardBatchTime";

	/**
	 * 卡表版本号 的属性名
	 */
	public static final String PROP_CARDBINNO="cardBinNo";

	/**
	 * 磁条卡上次对账日期时间  new add 的属性名
	 */
	public static final String PROP_CARDLSTCHKTIME="cardLstChkTime";

	/**
	 * 出纳号 的属性名
	 */
	public static final String PROP_CASHIER="cashier";

	/**
	 * 现金帐号 的属性名
	 */
	public static final String PROP_CASHACCOUNT="cashAccount";

	/**
	 * 100钞匣存款钞数 的属性名
	 */
	public static final String PROP_CDMBOXQT100="cdmBoxQt100";

	/**
	 * 50钞匣存款钞数 的属性名
	 */
	public static final String PROP_CDMBOXQT50="cdmBoxQt50";

	/**
	 * 存款箱状态 的属性名
	 */
	public static final String PROP_CDMBOXSTAT="cdmBoxStat";

	/**
	 * 存款模块状态 的属性名
	 */
	public static final String PROP_CDMDEVICESTAT="cdmDeviceStat";

	/**
	 * 验证失败描述 的属性名
	 */
	public static final String PROP_CHECKFAILDESC="checkFailDesc";

	/**
	 * 审核描述  new add  的属性名
	 */
	public static final String PROP_CHKDSC="chkDsc";

	/**
	 * chkId 的属性名
	 */
	public static final String PROP_CHKID="chkId";

	/**
	 * chkStatus 的属性名
	 */
	public static final String PROP_CHKSTATUS="chkStatus";

	/**
	 * IC卡一次最大圈取额 的属性名
	 */
	public static final String PROP_CIRCLEGETMAX="circlegetMax";

	/**
	 * 通讯地址  new add 的属性名
	 */
	public static final String PROP_COMMADDR="commAddr";

	/**
	 * ATM通讯进程号 的属性名
	 */
	public static final String PROP_COMMPID="commPid";

	/**
	 * ATM通讯规则   new add  的属性名
	 */
	public static final String PROP_COMMRULE="commRule";

	/**
	 * ATM连接  new add  的属性名
	 */
	public static final String PROP_CONNTCM="conntCm";

	/**
	 * ATM连接主机IP  new add  的属性名
	 */
	public static final String PROP_CONNTIPADDR="conntIpAddr";

	/**
	 * 联系人 的属性名
	 */
	public static final String PROP_CONTACTPERSON="contactPerson";

	/**
	 * 控制板状态  new add  的属性名
	 */
	public static final String PROP_CTLSTAT="ctlStat";

	/**
	 * 银联机构号 的属性名
	 */
	public static final String PROP_CUPORGAN="cupOrgan";

	/**
	 * 第1种面额 的属性名
	 */
	public static final String PROP_DEN1AMOUNT="den1Amount";

	/**
	 * 第1种币种 的属性名
	 */
	public static final String PROP_DEN1CURRENCY="den1Currency";

	/**
	 * 第2种面额 的属性名
	 */
	public static final String PROP_DEN2AMOUNT="den2Amount";

	/**
	 * 第2种币种 的属性名
	 */
	public static final String PROP_DEN2CURRENCY="den2Currency";

	/**
	 * 第3种面额 的属性名
	 */
	public static final String PROP_DEN3AMOUNT="den3Amount";

	/**
	 * 第3种币种 的属性名
	 */
	public static final String PROP_DEN3CURRENCY="den3Currency";

	/**
	 * 第4种面额 的属性名
	 */
	public static final String PROP_DEN4AMOUNT="den4Amount";

	/**
	 * 第4种币种 的属性名
	 */
	public static final String PROP_DEN4CURRENCY="den4Currency";

	/**
	 * 部门代码   装机行  的属性名
	 */
	public static final String PROP_DEPARTCODE="departCode";

	/**
	 * 安全门状态 的属性名
	 */
	public static final String PROP_DOORSTAT="doorStat";

	/**
	 * 文件上传下载地址 的属性名
	 */
	public static final String PROP_DOWNLOADPATH="downloadPath";

	/**
	 * 加密模块状态 的属性名
	 */
	public static final String PROP_ENCSTAT="encStat";

	/**
	 * 一次最大取款金额 的属性名
	 */
	public static final String PROP_FETCHCASHMAX="fetchCashMax";

	/**
	 * 一次最小取款金额 的属性名
	 */
	public static final String PROP_FETCHCASHMIN="fetchCashMin";

	/**
	 * 文件密码 的属性名
	 */
	public static final String PROP_FTPPWD="ftpPwd";

	/**
	 * 文件用户名 的属性名
	 */
	public static final String PROP_FTPUSER="ftpUser";

	/**
	 * 硬件加密器状态 的属性名
	 */
	public static final String PROP_HARDENCSTAT="hardEncStat";

	/**
	 * IC卡日终日期时间  new add 的属性名
	 */
	public static final String PROP_ICBATCHTIME="icBatchTime";

	/**
	 * IC卡一次最大圈存额 的属性名
	 */
	public static final String PROP_ICCIRCLEMAX="icCircleMax";

	/**
	 * IC卡一次最小圈存额 的属性名
	 */
	public static final String PROP_ICCIRCLEMIN="icCircleMin";

	/**
	 * IC卡上次对账日期时间  new add 的属性名
	 */
	public static final String PROP_ICLSTCHKTIME="icLstChkTime";

	/**
	 * 事件代码 的属性名
	 */
	public static final String PROP_INCIDENTCODE="incidentCode";

	/**
	 * ATM端口号 的属性名
	 */
	public static final String PROP_INPORT="inport";

	/**
	 * ATM机具IP地址 的属性名
	 */
	public static final String PROP_IPADDRESS="ipAddress";

	/**
	 * 上次开机时间 的属性名
	 */
	public static final String PROP_LASTOPENTIME="lastOpenTime";

	/**
	 * 线路状态 的属性名
	 */
	public static final String PROP_LINESTAT="lineStat";

	/**
	 * 记录打印机状态 的属性名
	 */
	public static final String PROP_LOGPRINTER="logPrinter";

	/**
	 * 最后更新日期 new add 的属性名
	 */
	public static final String PROP_LSTUPDDATA="lstUpdData";

	/**
	 * 最后更新柜员 new add 的属性名
	 */
	public static final String PROP_LSTUPDOPR="lstUpdOpr";

	/**
	 * 机箱门状态 的属性名
	 */
	public static final String PROP_MACHINEDOORSTAT="machineDoorStat";

	/**
	 * 万事顺参数文件版本号  new add 的属性名
	 */
	public static final String PROP_MCCPARAMNO="mccParamNo";

	/**
	 * 支行号（营业网点） 的属性名
	 */
	public static final String PROP_NETCODE="netCode";

	/**
	 * 新PINBlock 的属性名
	 */
	public static final String PROP_NEWPINBLOCK="newPinBlock";

	/**
	 * 旧PINBlock 的属性名
	 */
	public static final String PROP_OLDPINBLOCK="oldPinBlock";

	/**
	 * oldTermCode 的属性名
	 */
	public static final String PROP_OLDTERMCODE="oldTermCode";

	/**
	 * 当前开机时间 的属性名
	 */
	public static final String PROP_OPENTIME="openTime";

	/**
	 * 出卡口状态 的属性名
	 */
	public static final String PROP_OUTCARDSTAT="outcardStat";

	/**
	 * 出钞口状态 的属性名
	 */
	public static final String PROP_OUTCURSTAT="outcurStat";

	/**
	 * 卡号长度 的属性名
	 */
	public static final String PROP_PANL="panL";

	/**
	 * 卡号 的属性名
	 */
	public static final String PROP_PANV="panV";

	/**
	 * 参数版本号 的属性名
	 */
	public static final String PROP_PARAMETERVERNO="parameterverNo";

	/**
	 * 参数数据 的属性名
	 */
	public static final String PROP_PARAMETERDATA="parameterData";

	/**
	 * 收据打印机状态 的属性名
	 */
	public static final String PROP_RECEIPTPRINTER="receiptPrinter";

	/**
	 * 应答码 的属性名
	 */
	public static final String PROP_RESPONSECODE="responseCode";

	/**
	 * 保留域1 的属性名
	 */
	public static final String PROP_RESDATA1="resData1";

	/**
	 * 保留域2 的属性名
	 */
	public static final String PROP_RESDATA2="resData2";

	/**
	 * 黑名单文件版本号  new add 的属性名
	 */
	public static final String PROP_RISKFILENO="riskFileNo";

	/**
	 * 装钞金额 的属性名
	 */
	public static final String PROP_RWTAMT="rwtAmt";

	/**
	 * 装钞日期 的属性名
	 */
	public static final String PROP_RWTDATE="rwtDate";

	/**
	 * 装钞时间 的属性名
	 */
	public static final String PROP_RWTTIME="rwtTime";

	/**
	 * 一次最大存款金额 的属性名
	 */
	public static final String PROP_SAVINGMAX="savingMax";

	/**
	 * 转入卡号 的属性名
	 */
	public static final String PROP_SECONDPANV="secondPanV";

	/**
	 * 已对帐标志 的属性名
	 */
	public static final String PROP_SETTLEMENTFLAG="settlementFlag";

	/**
	 * 软件版本号 的属性名
	 */
	public static final String PROP_SOFTVERNO="softverNo";

	/**
	 * 间隔发送状态包时长 的属性名
	 */
	public static final String PROP_SPACETIME="spaceTime";

	/**
	 * 初始摆放日期  new add 的属性名
	 */
	public static final String PROP_STARTDATE="startDate";

	/**
	 * ATM状态 的属性名
	 */
	public static final String PROP_STATUS="status";

	/**
	 * 今日累计装钞金额 的属性名
	 */
	public static final String PROP_TDACMRWTAMT="tdAcmRwtAmt";

	/**
	 * 今日累计装钞次数 的属性名
	 */
	public static final String PROP_TDACMRWTCNT="tdAcmRwtCnt";

	/**
	 * 联系电话 的属性名
	 */
	public static final String PROP_TELEPHONE="telephone";

	/**
	 *  终端号码  的属性名
	 */
	public static final String PROP_TERMINALCODE="terminalCode";

	/**
	 * ATM厂商 的属性名
	 */
	public static final String PROP_TERMINALVENDOR="terminalVendor";

	/**
	 *  终端状态  的属性名
	 */
	public static final String PROP_TERMST="termSt";

	/**
	 *  终端类型  new add 的属性名
	 */
	public static final String PROP_TERMTYPE="termType";

	/**
	 * ATM厂商名称  new add 的属性名
	 */
	public static final String PROP_TERMVENDORNM="termVendorNm";

	/**
	 * 柜员号 的属性名
	 */
	public static final String PROP_TLRNO="tlrno";

	/**
	 * 存钞总笔数 的属性名
	 */
	public static final String PROP_TOTALCDMCNT="totalCdmCnt";

	/**
	 * 吐钞总额 的属性名
	 */
	public static final String PROP_TOTALOUT="totalOut";

	/**
	 * 废钞总额 的属性名
	 */
	public static final String PROP_TOTALWASTE="totalWaste";

	/**
	 * 交易金额 的属性名
	 */
	public static final String PROP_TRANSACTIONAMOUNT="transactionAmount";

	/**
	 * 交易代码 的属性名
	 */
	public static final String PROP_TRANSACTIONCODE="transactionCode";

	/**
	 * 交易发生时间 的属性名
	 */
	public static final String PROP_TRANSMISSIONTIME="transmissionTime";

	/**
	 * 交易信息 的属性名
	 */
	public static final String PROP_TXNINF="txnInf";

	/**
	 * 定时上传对账文件时间  new add  的属性名
	 */
	public static final String PROP_ULDFILETIME="uldFileTime";

	/**
	 * 营业单位代码  管机行  的属性名
	 */
	public static final String PROP_UNITCODE="unitCode";

	/**
	 * 一次最大转帐金额 的属性名
	 */
	public static final String PROP_VIREMENTMAX="virementMax";

	/**
	 * 废钞箱状态 的属性名
	 */
	public static final String PROP_WASTEBOXSTAT="wasteBoxStat";

	/**
	 * 废钞箱张数 的属性名
	 */
	public static final String PROP_WASTECOUNT="wasteCount";

	/**
	 * ATM所在地址(中文)
	 */
	private java.lang.String address;

	/**
	 * ATM所在地址(英文)
	 */
	private java.lang.String addressEn;

	/**
	 * 广告版本号
	 */
	private java.lang.String adverNo;

	/**
	 * 广告信息
	 */
	private java.lang.String adInfor;

	/**
	 * 主密钥
	 */
	private java.lang.String atmBmk;

	/**
	 * ATM MACkey
	 */
	private java.lang.String atmMak;

	/**
	 * ATM PINkey
	 */
	private java.lang.String atmPik;

	/**
	 * ATM流水号
	 */
	private java.lang.String atmSsn;

	/**
	 * ATM批次
	 */
	private java.lang.String batchNumber;

	/**
	 * 第1种面额装钞数
	 */
	private java.lang.String box1Count;

	/**
	 * 钞匣一出钞数
	 */
	private java.lang.String box1OutQt;

	/**
	 * 第一钞箱状态
	 */
	private java.lang.String box1Status;

	/**
	 * 钞匣一至废钞匣数
	 */
	private java.lang.String box1ToWboxQt;

	/**
	 * 第2种面额装钞数
	 */
	private java.lang.String box2Count;

	/**
	 * 钞匣2出钞数
	 */
	private java.lang.String box2OutQt;

	/**
	 * 第2钞箱状态
	 */
	private java.lang.String box2Status;

	/**
	 * 钞匣2至废钞匣数
	 */
	private java.lang.String box2ToWboxQt;

	/**
	 * 第3种面额装钞数
	 */
	private java.lang.String box3Count;

	/**
	 * 钞匣3出钞数
	 */
	private java.lang.String box3OutQt;

	/**
	 * 第3钞箱状态
	 */
	private java.lang.String box3Status;

	/**
	 * 钞匣3至废钞匣数
	 */
	private java.lang.String box3ToWboxQt;

	/**
	 * 第4种面额装钞数
	 */
	private java.lang.String box4Count;

	/**
	 * 钞匣4出钞数
	 */
	private java.lang.String box4OutQt;

	/**
	 * 第4钞箱状态
	 */
	private java.lang.String box4Status;

	/**
	 * 钞匣4至废钞匣数
	 */
	private java.lang.String box4ToWboxQt;

	/**
	 *  ATM行号（机构代码） 
	 */
	private java.lang.String brno;

	/**
	 * callDownDate
	 */
	private java.lang.String callDownDate;

	/**
	 * callUpDate
	 */
	private java.lang.String callUpDate;

	/**
	 * 没收卡片总数
	 */
	private java.lang.String capcardCount;

	/**
	 * 磁条读写器状态
	 */
	private java.lang.String cardrwStat;

	/**
	 * 磁条卡日终日期时间  new add
	 */
	private java.lang.String cardBatchTime;

	/**
	 * 卡表版本号
	 */
	private java.lang.String cardBinNo;

	/**
	 * 磁条卡上次对账日期时间  new add
	 */
	private java.lang.String cardLstChkTime;

	/**
	 * 出纳号
	 */
	private java.lang.String cashier;

	/**
	 * 现金帐号
	 */
	private java.lang.String cashAccount;

	/**
	 * 100钞匣存款钞数
	 */
	private java.lang.String cdmBoxQt100;

	/**
	 * 50钞匣存款钞数
	 */
	private java.lang.String cdmBoxQt50;

	/**
	 * 存款箱状态
	 */
	private java.lang.String cdmBoxStat;

	/**
	 * 存款模块状态
	 */
	private java.lang.String cdmDeviceStat;

	/**
	 * 验证失败描述
	 */
	private java.lang.String checkFailDesc;

	/**
	 * 审核描述  new add 
	 */
	private java.lang.String chkDsc;

	/**
	 * chkId
	 */
	private java.lang.String chkId;

	/**
	 * chkStatus
	 */
	private java.lang.String chkStatus;

	/**
	 * IC卡一次最大圈取额
	 */
	private java.lang.String circlegetMax;

	/**
	 * 通讯地址  new add
	 */
	private java.lang.String commAddr;

	/**
	 * ATM通讯进程号
	 */
	private java.lang.String commPid;

	/**
	 * ATM通讯规则   new add 
	 */
	private java.lang.String commRule;

	/**
	 * ATM连接????  new add 
	 */
	private java.lang.String conntCm;

	/**
	 * ATM连接主机IP  new add 
	 */
	private java.lang.String conntIpAddr;

	/**
	 * 联系人
	 */
	private java.lang.String contactPerson;

	/**
	 * 控制板状态  new add 
	 */
	private java.lang.String ctlStat;

	/**
	 * 银联机构号
	 */
	private java.lang.String cupOrgan;

	/**
	 * 第1种面额
	 */
	private java.lang.String den1Amount;

	/**
	 * 第1种币种
	 */
	private java.lang.String den1Currency;

	/**
	 * 第2种面额
	 */
	private java.lang.String den2Amount;

	/**
	 * 第2种币种
	 */
	private java.lang.String den2Currency;

	/**
	 * 第3种面额
	 */
	private java.lang.String den3Amount;

	/**
	 * 第3种币种
	 */
	private java.lang.String den3Currency;

	/**
	 * 第4种面额
	 */
	private java.lang.String den4Amount;

	/**
	 * 第4种币种
	 */
	private java.lang.String den4Currency;

	/**
	 * 部门代码   装机行 
	 */
	private java.lang.String departCode;

	/**
	 * 安全门状态
	 */
	private java.lang.String doorStat;

	/**
	 * 文件上传下载地址
	 */
	private java.lang.String downloadPath;

	/**
	 * 加密模块状态
	 */
	private java.lang.String encStat;

	/**
	 * 一次最大取款金额
	 */
	private java.lang.String fetchCashMax;

	/**
	 * 一次最小取款金额
	 */
	private java.lang.String fetchCashMin;

	/**
	 * 文件密码
	 */
	private java.lang.String ftpPwd;

	/**
	 * 文件用户名
	 */
	private java.lang.String ftpUser;

	/**
	 * 硬件加密器状态
	 */
	private java.lang.String hardEncStat;

	/**
	 * IC卡日终日期时间  new add
	 */
	private java.lang.String icBatchTime;

	/**
	 * IC卡一次最大圈存额
	 */
	private java.lang.String icCircleMax;

	/**
	 * IC卡一次最小圈存额
	 */
	private java.lang.String icCircleMin;

	/**
	 * IC卡上次对账日期时间  new add
	 */
	private java.lang.String icLstChkTime;

	/**
	 * 事件代码
	 */
	private java.lang.String incidentCode;

	/**
	 * ATM端口号
	 */
	private java.lang.String inport;

	/**
	 * ATM机具IP地址
	 */
	private java.lang.String ipAddress;

	/**
	 * 上次开机时间
	 */
	private java.lang.String lastOpenTime;

	/**
	 * 线路状态
	 */
	private java.lang.String lineStat;

	/**
	 * 记录打印机状态
	 */
	private java.lang.String logPrinter;

	/**
	 * 最后更新日期 new add
	 */
	private java.lang.String lstUpdData;

	/**
	 * 最后更新柜员 new add
	 */
	private java.lang.String lstUpdOpr;

	/**
	 * 机箱门状态
	 */
	private java.lang.String machineDoorStat;

	/**
	 * 万事顺参数文件版本号  new add
	 */
	private java.lang.String mccParamNo;

	/**
	 * 支行号（营业网点）
	 */
	private java.lang.String netCode;

	/**
	 * 新PINBlock
	 */
	private java.lang.String newPinBlock;

	/**
	 * 旧PINBlock
	 */
	private java.lang.String oldPinBlock;

	/**
	 * oldTermCode
	 */
	private java.lang.String oldTermCode;

	/**
	 * 当前开机时间
	 */
	private java.lang.String openTime;

	/**
	 * 出卡口状态
	 */
	private java.lang.String outcardStat;

	/**
	 * 出钞口状态
	 */
	private java.lang.String outcurStat;

	/**
	 * 卡号长度
	 */
	private java.lang.String panL;

	/**
	 * 卡号
	 */
	private java.lang.String panV;

	/**
	 * 参数版本号
	 */
	private java.lang.String parameterverNo;

	/**
	 * 参数数据
	 */
	private java.lang.String parameterData;

	/**
	 * 收据打印机状态
	 */
	private java.lang.String receiptPrinter;

	/**
	 * 应答码
	 */
	private java.lang.String responseCode;

	/**
	 * 保留域1
	 */
	private java.lang.String resData1;

	/**
	 * 保留域2
	 */
	private java.lang.String resData2;

	/**
	 * 黑名单文件版本号  new add
	 */
	private java.lang.String riskFileNo;

	/**
	 * 装钞金额
	 */
	private java.lang.String rwtAmt;

	/**
	 * 装钞日期
	 */
	private java.lang.String rwtDate;

	/**
	 * 装钞时间
	 */
	private java.lang.String rwtTime;

	/**
	 * 一次最大存款金额
	 */
	private java.lang.String savingMax;

	/**
	 * 转入卡号
	 */
	private java.lang.String secondPanV;

	/**
	 * 已对帐标志
	 */
	private java.lang.String settlementFlag;

	/**
	 * 软件版本号
	 */
	private java.lang.String softverNo;

	/**
	 * 间隔发送状态包时长
	 */
	private java.lang.String spaceTime;

	/**
	 * 初始摆放日期  new add
	 */
	private java.lang.String startDate;

	/**
	 * ATM状态
	 */
	private java.lang.String status;

	/**
	 * 今日累计装钞金额
	 */
	private java.lang.String tdAcmRwtAmt;

	/**
	 * 今日累计装钞次数
	 */
	private java.lang.String tdAcmRwtCnt;

	/**
	 * 联系电话
	 */
	private java.lang.String telephone;

	/**
	 *  终端号码 
	 */
	private java.lang.String terminalCode;

	/**
	 * ATM厂商
	 */
	private java.lang.String terminalVendor;

	/**
	 *  终端状态 
	 */
	private java.lang.String termSt;

	/**
	 *  终端类型  new add
	 */
	private java.lang.String termType;

	/**
	 * ATM厂商名称  new add
	 */
	private java.lang.String termVendorNm;

	/**
	 * 柜员号
	 */
	private java.lang.String tlrno;

	/**
	 * 存钞总笔数
	 */
	private java.lang.String totalCdmCnt;

	/**
	 * 吐钞总额
	 */
	private java.lang.String totalOut;

	/**
	 * 废钞总额
	 */
	private java.lang.String totalWaste;

	/**
	 * 交易金额
	 */
	private java.lang.String transactionAmount;

	/**
	 * 交易代码
	 */
	private java.lang.String transactionCode;

	/**
	 * 交易发生时间
	 */
	private java.lang.String transmissionTime;

	/**
	 * 交易信息
	 */
	private java.lang.String txnInf;

	/**
	 * 定时上传对账文件时间  new add 
	 */
	private java.lang.String uldFileTime;

	/**
	 * 营业单位代码  管机行 
	 */
	private java.lang.String unitCode;

	/**
	 * 一次最大转帐金额
	 */
	private java.lang.String virementMax;

	/**
	 * 废钞箱状态
	 */
	private java.lang.String wasteBoxStat;

	/**
	 * 废钞箱张数
	 */
	private java.lang.String wasteCount;

	/**
	 * 获取 ATM所在地址(中文) 的属性值
	 * @return address : ATM所在地址(中文)
	 * @author OM
	 */
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 * 设置 ATM所在地址(中文) 的属性值
	 * @param address : ATM所在地址(中文)
	 * @author OM
	 */
	public void setAddress(java.lang.String address){
		this.address	= address;
	}

	/**
	 * 获取 ATM所在地址(英文) 的属性值
	 * @return addressEn : ATM所在地址(英文)
	 * @author OM
	 */
	public java.lang.String getAddressEn(){
		return this.addressEn;
	}

	/**
	 * 设置 ATM所在地址(英文) 的属性值
	 * @param addressEn : ATM所在地址(英文)
	 * @author OM
	 */
	public void setAddressEn(java.lang.String addressEn){
		this.addressEn	= addressEn;
	}

	/**
	 * 获取 广告版本号 的属性值
	 * @return adverNo : 广告版本号
	 * @author OM
	 */
	public java.lang.String getAdverNo(){
		return this.adverNo;
	}

	/**
	 * 设置 广告版本号 的属性值
	 * @param adverNo : 广告版本号
	 * @author OM
	 */
	public void setAdverNo(java.lang.String adverNo){
		this.adverNo	= adverNo;
	}

	/**
	 * 获取 广告信息 的属性值
	 * @return adInfor : 广告信息
	 * @author OM
	 */
	public java.lang.String getAdInfor(){
		return this.adInfor;
	}

	/**
	 * 设置 广告信息 的属性值
	 * @param adInfor : 广告信息
	 * @author OM
	 */
	public void setAdInfor(java.lang.String adInfor){
		this.adInfor	= adInfor;
	}

	/**
	 * 获取 主密钥 的属性值
	 * @return atmBmk : 主密钥
	 * @author OM
	 */
	public java.lang.String getAtmBmk(){
		return this.atmBmk;
	}

	/**
	 * 设置 主密钥 的属性值
	 * @param atmBmk : 主密钥
	 * @author OM
	 */
	public void setAtmBmk(java.lang.String atmBmk){
		this.atmBmk	= atmBmk;
	}

	/**
	 * 获取 ATM MACkey 的属性值
	 * @return atmMak : ATM MACkey
	 * @author OM
	 */
	public java.lang.String getAtmMak(){
		return this.atmMak;
	}

	/**
	 * 设置 ATM MACkey 的属性值
	 * @param atmMak : ATM MACkey
	 * @author OM
	 */
	public void setAtmMak(java.lang.String atmMak){
		this.atmMak	= atmMak;
	}

	/**
	 * 获取 ATM PINkey 的属性值
	 * @return atmPik : ATM PINkey
	 * @author OM
	 */
	public java.lang.String getAtmPik(){
		return this.atmPik;
	}

	/**
	 * 设置 ATM PINkey 的属性值
	 * @param atmPik : ATM PINkey
	 * @author OM
	 */
	public void setAtmPik(java.lang.String atmPik){
		this.atmPik	= atmPik;
	}

	/**
	 * 获取 ATM流水号 的属性值
	 * @return atmSsn : ATM流水号
	 * @author OM
	 */
	public java.lang.String getAtmSsn(){
		return this.atmSsn;
	}

	/**
	 * 设置 ATM流水号 的属性值
	 * @param atmSsn : ATM流水号
	 * @author OM
	 */
	public void setAtmSsn(java.lang.String atmSsn){
		this.atmSsn	= atmSsn;
	}

	/**
	 * 获取 ATM批次 的属性值
	 * @return batchNumber : ATM批次
	 * @author OM
	 */
	public java.lang.String getBatchNumber(){
		return this.batchNumber;
	}

	/**
	 * 设置 ATM批次 的属性值
	 * @param batchNumber : ATM批次
	 * @author OM
	 */
	public void setBatchNumber(java.lang.String batchNumber){
		this.batchNumber	= batchNumber;
	}

	/**
	 * 获取 第1种面额装钞数 的属性值
	 * @return box1Count : 第1种面额装钞数
	 * @author OM
	 */
	public java.lang.String getBox1Count(){
		return this.box1Count;
	}

	/**
	 * 设置 第1种面额装钞数 的属性值
	 * @param box1Count : 第1种面额装钞数
	 * @author OM
	 */
	public void setBox1Count(java.lang.String box1Count){
		this.box1Count	= box1Count;
	}

	/**
	 * 获取 钞匣一出钞数 的属性值
	 * @return box1OutQt : 钞匣一出钞数
	 * @author OM
	 */
	public java.lang.String getBox1OutQt(){
		return this.box1OutQt;
	}

	/**
	 * 设置 钞匣一出钞数 的属性值
	 * @param box1OutQt : 钞匣一出钞数
	 * @author OM
	 */
	public void setBox1OutQt(java.lang.String box1OutQt){
		this.box1OutQt	= box1OutQt;
	}

	/**
	 * 获取 第一钞箱状态 的属性值
	 * @return box1Status : 第一钞箱状态
	 * @author OM
	 */
	public java.lang.String getBox1Status(){
		return this.box1Status;
	}

	/**
	 * 设置 第一钞箱状态 的属性值
	 * @param box1Status : 第一钞箱状态
	 * @author OM
	 */
	public void setBox1Status(java.lang.String box1Status){
		this.box1Status	= box1Status;
	}

	/**
	 * 获取 钞匣一至废钞匣数 的属性值
	 * @return box1ToWboxQt : 钞匣一至废钞匣数
	 * @author OM
	 */
	public java.lang.String getBox1ToWboxQt(){
		return this.box1ToWboxQt;
	}

	/**
	 * 设置 钞匣一至废钞匣数 的属性值
	 * @param box1ToWboxQt : 钞匣一至废钞匣数
	 * @author OM
	 */
	public void setBox1ToWboxQt(java.lang.String box1ToWboxQt){
		this.box1ToWboxQt	= box1ToWboxQt;
	}

	/**
	 * 获取 第2种面额装钞数 的属性值
	 * @return box2Count : 第2种面额装钞数
	 * @author OM
	 */
	public java.lang.String getBox2Count(){
		return this.box2Count;
	}

	/**
	 * 设置 第2种面额装钞数 的属性值
	 * @param box2Count : 第2种面额装钞数
	 * @author OM
	 */
	public void setBox2Count(java.lang.String box2Count){
		this.box2Count	= box2Count;
	}

	/**
	 * 获取 钞匣2出钞数 的属性值
	 * @return box2OutQt : 钞匣2出钞数
	 * @author OM
	 */
	public java.lang.String getBox2OutQt(){
		return this.box2OutQt;
	}

	/**
	 * 设置 钞匣2出钞数 的属性值
	 * @param box2OutQt : 钞匣2出钞数
	 * @author OM
	 */
	public void setBox2OutQt(java.lang.String box2OutQt){
		this.box2OutQt	= box2OutQt;
	}

	/**
	 * 获取 第2钞箱状态 的属性值
	 * @return box2Status : 第2钞箱状态
	 * @author OM
	 */
	public java.lang.String getBox2Status(){
		return this.box2Status;
	}

	/**
	 * 设置 第2钞箱状态 的属性值
	 * @param box2Status : 第2钞箱状态
	 * @author OM
	 */
	public void setBox2Status(java.lang.String box2Status){
		this.box2Status	= box2Status;
	}

	/**
	 * 获取 钞匣2至废钞匣数 的属性值
	 * @return box2ToWboxQt : 钞匣2至废钞匣数
	 * @author OM
	 */
	public java.lang.String getBox2ToWboxQt(){
		return this.box2ToWboxQt;
	}

	/**
	 * 设置 钞匣2至废钞匣数 的属性值
	 * @param box2ToWboxQt : 钞匣2至废钞匣数
	 * @author OM
	 */
	public void setBox2ToWboxQt(java.lang.String box2ToWboxQt){
		this.box2ToWboxQt	= box2ToWboxQt;
	}

	/**
	 * 获取 第3种面额装钞数 的属性值
	 * @return box3Count : 第3种面额装钞数
	 * @author OM
	 */
	public java.lang.String getBox3Count(){
		return this.box3Count;
	}

	/**
	 * 设置 第3种面额装钞数 的属性值
	 * @param box3Count : 第3种面额装钞数
	 * @author OM
	 */
	public void setBox3Count(java.lang.String box3Count){
		this.box3Count	= box3Count;
	}

	/**
	 * 获取 钞匣3出钞数 的属性值
	 * @return box3OutQt : 钞匣3出钞数
	 * @author OM
	 */
	public java.lang.String getBox3OutQt(){
		return this.box3OutQt;
	}

	/**
	 * 设置 钞匣3出钞数 的属性值
	 * @param box3OutQt : 钞匣3出钞数
	 * @author OM
	 */
	public void setBox3OutQt(java.lang.String box3OutQt){
		this.box3OutQt	= box3OutQt;
	}

	/**
	 * 获取 第3钞箱状态 的属性值
	 * @return box3Status : 第3钞箱状态
	 * @author OM
	 */
	public java.lang.String getBox3Status(){
		return this.box3Status;
	}

	/**
	 * 设置 第3钞箱状态 的属性值
	 * @param box3Status : 第3钞箱状态
	 * @author OM
	 */
	public void setBox3Status(java.lang.String box3Status){
		this.box3Status	= box3Status;
	}

	/**
	 * 获取 钞匣3至废钞匣数 的属性值
	 * @return box3ToWboxQt : 钞匣3至废钞匣数
	 * @author OM
	 */
	public java.lang.String getBox3ToWboxQt(){
		return this.box3ToWboxQt;
	}

	/**
	 * 设置 钞匣3至废钞匣数 的属性值
	 * @param box3ToWboxQt : 钞匣3至废钞匣数
	 * @author OM
	 */
	public void setBox3ToWboxQt(java.lang.String box3ToWboxQt){
		this.box3ToWboxQt	= box3ToWboxQt;
	}

	/**
	 * 获取 第4种面额装钞数 的属性值
	 * @return box4Count : 第4种面额装钞数
	 * @author OM
	 */
	public java.lang.String getBox4Count(){
		return this.box4Count;
	}

	/**
	 * 设置 第4种面额装钞数 的属性值
	 * @param box4Count : 第4种面额装钞数
	 * @author OM
	 */
	public void setBox4Count(java.lang.String box4Count){
		this.box4Count	= box4Count;
	}

	/**
	 * 获取 钞匣4出钞数 的属性值
	 * @return box4OutQt : 钞匣4出钞数
	 * @author OM
	 */
	public java.lang.String getBox4OutQt(){
		return this.box4OutQt;
	}

	/**
	 * 设置 钞匣4出钞数 的属性值
	 * @param box4OutQt : 钞匣4出钞数
	 * @author OM
	 */
	public void setBox4OutQt(java.lang.String box4OutQt){
		this.box4OutQt	= box4OutQt;
	}

	/**
	 * 获取 第4钞箱状态 的属性值
	 * @return box4Status : 第4钞箱状态
	 * @author OM
	 */
	public java.lang.String getBox4Status(){
		return this.box4Status;
	}

	/**
	 * 设置 第4钞箱状态 的属性值
	 * @param box4Status : 第4钞箱状态
	 * @author OM
	 */
	public void setBox4Status(java.lang.String box4Status){
		this.box4Status	= box4Status;
	}

	/**
	 * 获取 钞匣4至废钞匣数 的属性值
	 * @return box4ToWboxQt : 钞匣4至废钞匣数
	 * @author OM
	 */
	public java.lang.String getBox4ToWboxQt(){
		return this.box4ToWboxQt;
	}

	/**
	 * 设置 钞匣4至废钞匣数 的属性值
	 * @param box4ToWboxQt : 钞匣4至废钞匣数
	 * @author OM
	 */
	public void setBox4ToWboxQt(java.lang.String box4ToWboxQt){
		this.box4ToWboxQt	= box4ToWboxQt;
	}

	/**
	 * 获取  ATM行号（机构代码）  的属性值
	 * @return brno :  ATM行号（机构代码） 
	 * @author OM
	 */
	public java.lang.String getBrno(){
		return this.brno;
	}

	/**
	 * 设置  ATM行号（机构代码）  的属性值
	 * @param brno :  ATM行号（机构代码） 
	 * @author OM
	 */
	public void setBrno(java.lang.String brno){
		this.brno	= brno;
	}

	/**
	 * 获取 callDownDate 的属性值
	 * @return callDownDate : callDownDate
	 * @author OM
	 */
	public java.lang.String getCallDownDate(){
		return this.callDownDate;
	}

	/**
	 * 设置 callDownDate 的属性值
	 * @param callDownDate : callDownDate
	 * @author OM
	 */
	public void setCallDownDate(java.lang.String callDownDate){
		this.callDownDate	= callDownDate;
	}

	/**
	 * 获取 callUpDate 的属性值
	 * @return callUpDate : callUpDate
	 * @author OM
	 */
	public java.lang.String getCallUpDate(){
		return this.callUpDate;
	}

	/**
	 * 设置 callUpDate 的属性值
	 * @param callUpDate : callUpDate
	 * @author OM
	 */
	public void setCallUpDate(java.lang.String callUpDate){
		this.callUpDate	= callUpDate;
	}

	/**
	 * 获取 没收卡片总数 的属性值
	 * @return capcardCount : 没收卡片总数
	 * @author OM
	 */
	public java.lang.String getCapcardCount(){
		return this.capcardCount;
	}

	/**
	 * 设置 没收卡片总数 的属性值
	 * @param capcardCount : 没收卡片总数
	 * @author OM
	 */
	public void setCapcardCount(java.lang.String capcardCount){
		this.capcardCount	= capcardCount;
	}

	/**
	 * 获取 磁条读写器状态 的属性值
	 * @return cardrwStat : 磁条读写器状态
	 * @author OM
	 */
	public java.lang.String getCardrwStat(){
		return this.cardrwStat;
	}

	/**
	 * 设置 磁条读写器状态 的属性值
	 * @param cardrwStat : 磁条读写器状态
	 * @author OM
	 */
	public void setCardrwStat(java.lang.String cardrwStat){
		this.cardrwStat	= cardrwStat;
	}

	/**
	 * 获取 磁条卡日终日期时间  new add 的属性值
	 * @return cardBatchTime : 磁条卡日终日期时间  new add
	 * @author OM
	 */
	public java.lang.String getCardBatchTime(){
		return this.cardBatchTime;
	}

	/**
	 * 设置 磁条卡日终日期时间  new add 的属性值
	 * @param cardBatchTime : 磁条卡日终日期时间  new add
	 * @author OM
	 */
	public void setCardBatchTime(java.lang.String cardBatchTime){
		this.cardBatchTime	= cardBatchTime;
	}

	/**
	 * 获取 卡表版本号 的属性值
	 * @return cardBinNo : 卡表版本号
	 * @author OM
	 */
	public java.lang.String getCardBinNo(){
		return this.cardBinNo;
	}

	/**
	 * 设置 卡表版本号 的属性值
	 * @param cardBinNo : 卡表版本号
	 * @author OM
	 */
	public void setCardBinNo(java.lang.String cardBinNo){
		this.cardBinNo	= cardBinNo;
	}

	/**
	 * 获取 磁条卡上次对账日期时间  new add 的属性值
	 * @return cardLstChkTime : 磁条卡上次对账日期时间  new add
	 * @author OM
	 */
	public java.lang.String getCardLstChkTime(){
		return this.cardLstChkTime;
	}

	/**
	 * 设置 磁条卡上次对账日期时间  new add 的属性值
	 * @param cardLstChkTime : 磁条卡上次对账日期时间  new add
	 * @author OM
	 */
	public void setCardLstChkTime(java.lang.String cardLstChkTime){
		this.cardLstChkTime	= cardLstChkTime;
	}

	/**
	 * 获取 出纳号 的属性值
	 * @return cashier : 出纳号
	 * @author OM
	 */
	public java.lang.String getCashier(){
		return this.cashier;
	}

	/**
	 * 设置 出纳号 的属性值
	 * @param cashier : 出纳号
	 * @author OM
	 */
	public void setCashier(java.lang.String cashier){
		this.cashier	= cashier;
	}

	/**
	 * 获取 现金帐号 的属性值
	 * @return cashAccount : 现金帐号
	 * @author OM
	 */
	public java.lang.String getCashAccount(){
		return this.cashAccount;
	}

	/**
	 * 设置 现金帐号 的属性值
	 * @param cashAccount : 现金帐号
	 * @author OM
	 */
	public void setCashAccount(java.lang.String cashAccount){
		this.cashAccount	= cashAccount;
	}

	/**
	 * 获取 100钞匣存款钞数 的属性值
	 * @return cdmBoxQt100 : 100钞匣存款钞数
	 * @author OM
	 */
	public java.lang.String getCdmBoxQt100(){
		return this.cdmBoxQt100;
	}

	/**
	 * 设置 100钞匣存款钞数 的属性值
	 * @param cdmBoxQt100 : 100钞匣存款钞数
	 * @author OM
	 */
	public void setCdmBoxQt100(java.lang.String cdmBoxQt100){
		this.cdmBoxQt100	= cdmBoxQt100;
	}

	/**
	 * 获取 50钞匣存款钞数 的属性值
	 * @return cdmBoxQt50 : 50钞匣存款钞数
	 * @author OM
	 */
	public java.lang.String getCdmBoxQt50(){
		return this.cdmBoxQt50;
	}

	/**
	 * 设置 50钞匣存款钞数 的属性值
	 * @param cdmBoxQt50 : 50钞匣存款钞数
	 * @author OM
	 */
	public void setCdmBoxQt50(java.lang.String cdmBoxQt50){
		this.cdmBoxQt50	= cdmBoxQt50;
	}

	/**
	 * 获取 存款箱状态 的属性值
	 * @return cdmBoxStat : 存款箱状态
	 * @author OM
	 */
	public java.lang.String getCdmBoxStat(){
		return this.cdmBoxStat;
	}

	/**
	 * 设置 存款箱状态 的属性值
	 * @param cdmBoxStat : 存款箱状态
	 * @author OM
	 */
	public void setCdmBoxStat(java.lang.String cdmBoxStat){
		this.cdmBoxStat	= cdmBoxStat;
	}

	/**
	 * 获取 存款模块状态 的属性值
	 * @return cdmDeviceStat : 存款模块状态
	 * @author OM
	 */
	public java.lang.String getCdmDeviceStat(){
		return this.cdmDeviceStat;
	}

	/**
	 * 设置 存款模块状态 的属性值
	 * @param cdmDeviceStat : 存款模块状态
	 * @author OM
	 */
	public void setCdmDeviceStat(java.lang.String cdmDeviceStat){
		this.cdmDeviceStat	= cdmDeviceStat;
	}

	/**
	 * 获取 验证失败描述 的属性值
	 * @return checkFailDesc : 验证失败描述
	 * @author OM
	 */
	public java.lang.String getCheckFailDesc(){
		return this.checkFailDesc;
	}

	/**
	 * 设置 验证失败描述 的属性值
	 * @param checkFailDesc : 验证失败描述
	 * @author OM
	 */
	public void setCheckFailDesc(java.lang.String checkFailDesc){
		this.checkFailDesc	= checkFailDesc;
	}

	/**
	 * 获取 审核描述  new add  的属性值
	 * @return chkDsc : 审核描述  new add 
	 * @author OM
	 */
	public java.lang.String getChkDsc(){
		return this.chkDsc;
	}

	/**
	 * 设置 审核描述  new add  的属性值
	 * @param chkDsc : 审核描述  new add 
	 * @author OM
	 */
	public void setChkDsc(java.lang.String chkDsc){
		this.chkDsc	= chkDsc;
	}

	/**
	 * 获取 chkId 的属性值
	 * @return chkId : chkId
	 * @author OM
	 */
	public java.lang.String getChkId(){
		return this.chkId;
	}

	/**
	 * 设置 chkId 的属性值
	 * @param chkId : chkId
	 * @author OM
	 */
	public void setChkId(java.lang.String chkId){
		this.chkId	= chkId;
	}

	/**
	 * 获取 chkStatus 的属性值
	 * @return chkStatus : chkStatus
	 * @author OM
	 */
	public java.lang.String getChkStatus(){
		return this.chkStatus;
	}

	/**
	 * 设置 chkStatus 的属性值
	 * @param chkStatus : chkStatus
	 * @author OM
	 */
	public void setChkStatus(java.lang.String chkStatus){
		this.chkStatus	= chkStatus;
	}

	/**
	 * 获取 IC卡一次最大圈取额 的属性值
	 * @return circlegetMax : IC卡一次最大圈取额
	 * @author OM
	 */
	public java.lang.String getCirclegetMax(){
		return this.circlegetMax;
	}

	/**
	 * 设置 IC卡一次最大圈取额 的属性值
	 * @param circlegetMax : IC卡一次最大圈取额
	 * @author OM
	 */
	public void setCirclegetMax(java.lang.String circlegetMax){
		this.circlegetMax	= circlegetMax;
	}

	/**
	 * 获取 通讯地址  new add 的属性值
	 * @return commAddr : 通讯地址  new add
	 * @author OM
	 */
	public java.lang.String getCommAddr(){
		return this.commAddr;
	}

	/**
	 * 设置 通讯地址  new add 的属性值
	 * @param commAddr : 通讯地址  new add
	 * @author OM
	 */
	public void setCommAddr(java.lang.String commAddr){
		this.commAddr	= commAddr;
	}

	/**
	 * 获取 ATM通讯进程号 的属性值
	 * @return commPid : ATM通讯进程号
	 * @author OM
	 */
	public java.lang.String getCommPid(){
		return this.commPid;
	}

	/**
	 * 设置 ATM通讯进程号 的属性值
	 * @param commPid : ATM通讯进程号
	 * @author OM
	 */
	public void setCommPid(java.lang.String commPid){
		this.commPid	= commPid;
	}

	/**
	 * 获取 ATM通讯规则   new add  的属性值
	 * @return commRule : ATM通讯规则   new add 
	 * @author OM
	 */
	public java.lang.String getCommRule(){
		return this.commRule;
	}

	/**
	 * 设置 ATM通讯规则   new add  的属性值
	 * @param commRule : ATM通讯规则   new add 
	 * @author OM
	 */
	public void setCommRule(java.lang.String commRule){
		this.commRule	= commRule;
	}

	/**
	 * 获取 ATM连接????  new add  的属性值
	 * @return conntCm : ATM连接????  new add 
	 * @author OM
	 */
	public java.lang.String getConntCm(){
		return this.conntCm;
	}

	/**
	 * 设置 ATM连接  new add  的属性值
	 * @param conntCm : ATM连接 new add 	 * @author OM
	 */
	public void setConntCm(java.lang.String conntCm){
		this.conntCm	= conntCm;
	}

	/**
	 * 获取 ATM连接主机IP  new add  的属性值
	 * @return conntIpAddr : ATM连接主机IP  new add 
	 * @author OM
	 */
	public java.lang.String getConntIpAddr(){
		return this.conntIpAddr;
	}

	/**
	 * 设置 ATM连接主机IP  new add  的属性值
	 * @param conntIpAddr : ATM连接主机IP  new add 
	 * @author OM
	 */
	public void setConntIpAddr(java.lang.String conntIpAddr){
		this.conntIpAddr	= conntIpAddr;
	}

	/**
	 * 获取 联系人 的属性值
	 * @return contactPerson : 联系人
	 * @author OM
	 */
	public java.lang.String getContactPerson(){
		return this.contactPerson;
	}

	/**
	 * 设置 联系人 的属性值
	 * @param contactPerson : 联系人
	 * @author OM
	 */
	public void setContactPerson(java.lang.String contactPerson){
		this.contactPerson	= contactPerson;
	}

	/**
	 * 获取 控制板状态  new add  的属性值
	 * @return ctlStat : 控制板状态  new add 
	 * @author OM
	 */
	public java.lang.String getCtlStat(){
		return this.ctlStat;
	}

	/**
	 * 设置 控制板状态  new add  的属性值
	 * @param ctlStat : 控制板状态  new add 
	 * @author OM
	 */
	public void setCtlStat(java.lang.String ctlStat){
		this.ctlStat	= ctlStat;
	}

	/**
	 * 获取 银联机构号 的属性值
	 * @return cupOrgan : 银联机构号
	 * @author OM
	 */
	public java.lang.String getCupOrgan(){
		return this.cupOrgan;
	}

	/**
	 * 设置 银联机构号 的属性值
	 * @param cupOrgan : 银联机构号
	 * @author OM
	 */
	public void setCupOrgan(java.lang.String cupOrgan){
		this.cupOrgan	= cupOrgan;
	}

	/**
	 * 获取 第1种面额 的属性值
	 * @return den1Amount : 第1种面额
	 * @author OM
	 */
	public java.lang.String getDen1Amount(){
		return this.den1Amount;
	}

	/**
	 * 设置 第1种面额 的属性值
	 * @param den1Amount : 第1种面额
	 * @author OM
	 */
	public void setDen1Amount(java.lang.String den1Amount){
		this.den1Amount	= den1Amount;
	}

	/**
	 * 获取 第1种币种 的属性值
	 * @return den1Currency : 第1种币种
	 * @author OM
	 */
	public java.lang.String getDen1Currency(){
		return this.den1Currency;
	}

	/**
	 * 设置 第1种币种 的属性值
	 * @param den1Currency : 第1种币种
	 * @author OM
	 */
	public void setDen1Currency(java.lang.String den1Currency){
		this.den1Currency	= den1Currency;
	}

	/**
	 * 获取 第2种面额 的属性值
	 * @return den2Amount : 第2种面额
	 * @author OM
	 */
	public java.lang.String getDen2Amount(){
		return this.den2Amount;
	}

	/**
	 * 设置 第2种面额 的属性值
	 * @param den2Amount : 第2种面额
	 * @author OM
	 */
	public void setDen2Amount(java.lang.String den2Amount){
		this.den2Amount	= den2Amount;
	}

	/**
	 * 获取 第2种币种 的属性值
	 * @return den2Currency : 第2种币种
	 * @author OM
	 */
	public java.lang.String getDen2Currency(){
		return this.den2Currency;
	}

	/**
	 * 设置 第2种币种 的属性值
	 * @param den2Currency : 第2种币种
	 * @author OM
	 */
	public void setDen2Currency(java.lang.String den2Currency){
		this.den2Currency	= den2Currency;
	}

	/**
	 * 获取 第3种面额 的属性值
	 * @return den3Amount : 第3种面额
	 * @author OM
	 */
	public java.lang.String getDen3Amount(){
		return this.den3Amount;
	}

	/**
	 * 设置 第3种面额 的属性值
	 * @param den3Amount : 第3种面额
	 * @author OM
	 */
	public void setDen3Amount(java.lang.String den3Amount){
		this.den3Amount	= den3Amount;
	}

	/**
	 * 获取 第3种币种 的属性值
	 * @return den3Currency : 第3种币种
	 * @author OM
	 */
	public java.lang.String getDen3Currency(){
		return this.den3Currency;
	}

	/**
	 * 设置 第3种币种 的属性值
	 * @param den3Currency : 第3种币种
	 * @author OM
	 */
	public void setDen3Currency(java.lang.String den3Currency){
		this.den3Currency	= den3Currency;
	}

	/**
	 * 获取 第4种面额 的属性值
	 * @return den4Amount : 第4种面额
	 * @author OM
	 */
	public java.lang.String getDen4Amount(){
		return this.den4Amount;
	}

	/**
	 * 设置 第4种面额 的属性值
	 * @param den4Amount : 第4种面额
	 * @author OM
	 */
	public void setDen4Amount(java.lang.String den4Amount){
		this.den4Amount	= den4Amount;
	}

	/**
	 * 获取 第4种币种 的属性值
	 * @return den4Currency : 第4种币种
	 * @author OM
	 */
	public java.lang.String getDen4Currency(){
		return this.den4Currency;
	}

	/**
	 * 设置 第4种币种 的属性值
	 * @param den4Currency : 第4种币种
	 * @author OM
	 */
	public void setDen4Currency(java.lang.String den4Currency){
		this.den4Currency	= den4Currency;
	}

	/**
	 * 获取 部门代码   装机行  的属性值
	 * @return departCode : 部门代码   装机行 
	 * @author OM
	 */
	public java.lang.String getDepartCode(){
		return this.departCode;
	}

	/**
	 * 设置 部门代码   装机行  的属性值
	 * @param departCode : 部门代码   装机行 
	 * @author OM
	 */
	public void setDepartCode(java.lang.String departCode){
		this.departCode	= departCode;
	}

	/**
	 * 获取 安全门状态 的属性值
	 * @return doorStat : 安全门状态
	 * @author OM
	 */
	public java.lang.String getDoorStat(){
		return this.doorStat;
	}

	/**
	 * 设置 安全门状态 的属性值
	 * @param doorStat : 安全门状态
	 * @author OM
	 */
	public void setDoorStat(java.lang.String doorStat){
		this.doorStat	= doorStat;
	}

	/**
	 * 获取 文件上传下载地址 的属性值
	 * @return downloadPath : 文件上传下载地址
	 * @author OM
	 */
	public java.lang.String getDownloadPath(){
		return this.downloadPath;
	}

	/**
	 * 设置 文件上传下载地址 的属性值
	 * @param downloadPath : 文件上传下载地址
	 * @author OM
	 */
	public void setDownloadPath(java.lang.String downloadPath){
		this.downloadPath	= downloadPath;
	}

	/**
	 * 获取 加密模块状态 的属性值
	 * @return encStat : 加密模块状态
	 * @author OM
	 */
	public java.lang.String getEncStat(){
		return this.encStat;
	}

	/**
	 * 设置 加密模块状态 的属性值
	 * @param encStat : 加密模块状态
	 * @author OM
	 */
	public void setEncStat(java.lang.String encStat){
		this.encStat	= encStat;
	}

	/**
	 * 获取 一次最大取款金额 的属性值
	 * @return fetchCashMax : 一次最大取款金额
	 * @author OM
	 */
	public java.lang.String getFetchCashMax(){
		return this.fetchCashMax;
	}

	/**
	 * 设置 一次最大取款金额 的属性值
	 * @param fetchCashMax : 一次最大取款金额
	 * @author OM
	 */
	public void setFetchCashMax(java.lang.String fetchCashMax){
		this.fetchCashMax	= fetchCashMax;
	}

	/**
	 * 获取 一次最小取款金额 的属性值
	 * @return fetchCashMin : 一次最小取款金额
	 * @author OM
	 */
	public java.lang.String getFetchCashMin(){
		return this.fetchCashMin;
	}

	/**
	 * 设置 一次最小取款金额 的属性值
	 * @param fetchCashMin : 一次最小取款金额
	 * @author OM
	 */
	public void setFetchCashMin(java.lang.String fetchCashMin){
		this.fetchCashMin	= fetchCashMin;
	}

	/**
	 * 获取 文件密码 的属性值
	 * @return ftpPwd : 文件密码
	 * @author OM
	 */
	public java.lang.String getFtpPwd(){
		return this.ftpPwd;
	}

	/**
	 * 设置 文件密码 的属性值
	 * @param ftpPwd : 文件密码
	 * @author OM
	 */
	public void setFtpPwd(java.lang.String ftpPwd){
		this.ftpPwd	= ftpPwd;
	}

	/**
	 * 获取 文件用户名 的属性值
	 * @return ftpUser : 文件用户名
	 * @author OM
	 */
	public java.lang.String getFtpUser(){
		return this.ftpUser;
	}

	/**
	 * 设置 文件用户名 的属性值
	 * @param ftpUser : 文件用户名
	 * @author OM
	 */
	public void setFtpUser(java.lang.String ftpUser){
		this.ftpUser	= ftpUser;
	}

	/**
	 * 获取 硬件加密器状态 的属性值
	 * @return hardEncStat : 硬件加密器状态
	 * @author OM
	 */
	public java.lang.String getHardEncStat(){
		return this.hardEncStat;
	}

	/**
	 * 设置 硬件加密器状态 的属性值
	 * @param hardEncStat : 硬件加密器状态
	 * @author OM
	 */
	public void setHardEncStat(java.lang.String hardEncStat){
		this.hardEncStat	= hardEncStat;
	}

	/**
	 * 获取 IC卡日终日期时间  new add 的属性值
	 * @return icBatchTime : IC卡日终日期时间  new add
	 * @author OM
	 */
	public java.lang.String getIcBatchTime(){
		return this.icBatchTime;
	}

	/**
	 * 设置 IC卡日终日期时间  new add 的属性值
	 * @param icBatchTime : IC卡日终日期时间  new add
	 * @author OM
	 */
	public void setIcBatchTime(java.lang.String icBatchTime){
		this.icBatchTime	= icBatchTime;
	}

	/**
	 * 获取 IC卡一次最大圈存额 的属性值
	 * @return icCircleMax : IC卡一次最大圈存额
	 * @author OM
	 */
	public java.lang.String getIcCircleMax(){
		return this.icCircleMax;
	}

	/**
	 * 设置 IC卡一次最大圈存额 的属性值
	 * @param icCircleMax : IC卡一次最大圈存额
	 * @author OM
	 */
	public void setIcCircleMax(java.lang.String icCircleMax){
		this.icCircleMax	= icCircleMax;
	}

	/**
	 * 获取 IC卡一次最小圈存额 的属性值
	 * @return icCircleMin : IC卡一次最小圈存额
	 * @author OM
	 */
	public java.lang.String getIcCircleMin(){
		return this.icCircleMin;
	}

	/**
	 * 设置 IC卡一次最小圈存额 的属性值
	 * @param icCircleMin : IC卡一次最小圈存额
	 * @author OM
	 */
	public void setIcCircleMin(java.lang.String icCircleMin){
		this.icCircleMin	= icCircleMin;
	}

	/**
	 * 获取 IC卡上次对账日期时间  new add 的属性值
	 * @return icLstChkTime : IC卡上次对账日期时间  new add
	 * @author OM
	 */
	public java.lang.String getIcLstChkTime(){
		return this.icLstChkTime;
	}

	/**
	 * 设置 IC卡上次对账日期时间  new add 的属性值
	 * @param icLstChkTime : IC卡上次对账日期时间  new add
	 * @author OM
	 */
	public void setIcLstChkTime(java.lang.String icLstChkTime){
		this.icLstChkTime	= icLstChkTime;
	}

	/**
	 * 获取 事件代码 的属性值
	 * @return incidentCode : 事件代码
	 * @author OM
	 */
	public java.lang.String getIncidentCode(){
		return this.incidentCode;
	}

	/**
	 * 设置 事件代码 的属性值
	 * @param incidentCode : 事件代码
	 * @author OM
	 */
	public void setIncidentCode(java.lang.String incidentCode){
		this.incidentCode	= incidentCode;
	}

	/**
	 * 获取 ATM端口号 的属性值
	 * @return inport : ATM端口号
	 * @author OM
	 */
	public java.lang.String getInport(){
		return this.inport;
	}

	/**
	 * 设置 ATM端口号 的属性值
	 * @param inport : ATM端口号
	 * @author OM
	 */
	public void setInport(java.lang.String inport){
		this.inport	= inport;
	}

	/**
	 * 获取 ATM机具IP地址 的属性值
	 * @return ipAddress : ATM机具IP地址
	 * @author OM
	 */
	public java.lang.String getIpAddress(){
		return this.ipAddress;
	}

	/**
	 * 设置 ATM机具IP地址 的属性值
	 * @param ipAddress : ATM机具IP地址
	 * @author OM
	 */
	public void setIpAddress(java.lang.String ipAddress){
		this.ipAddress	= ipAddress;
	}

	/**
	 * 获取 上次开机时间 的属性值
	 * @return lastOpenTime : 上次开机时间
	 * @author OM
	 */
	public java.lang.String getLastOpenTime(){
		return this.lastOpenTime;
	}

	/**
	 * 设置 上次开机时间 的属性值
	 * @param lastOpenTime : 上次开机时间
	 * @author OM
	 */
	public void setLastOpenTime(java.lang.String lastOpenTime){
		this.lastOpenTime	= lastOpenTime;
	}

	/**
	 * 获取 线路状态 的属性值
	 * @return lineStat : 线路状态
	 * @author OM
	 */
	public java.lang.String getLineStat(){
		return this.lineStat;
	}

	/**
	 * 设置 线路状态 的属性值
	 * @param lineStat : 线路状态
	 * @author OM
	 */
	public void setLineStat(java.lang.String lineStat){
		this.lineStat	= lineStat;
	}

	/**
	 * 获取 记录打印机状态 的属性值
	 * @return logPrinter : 记录打印机状态
	 * @author OM
	 */
	public java.lang.String getLogPrinter(){
		return this.logPrinter;
	}

	/**
	 * 设置 记录打印机状态 的属性值
	 * @param logPrinter : 记录打印机状态
	 * @author OM
	 */
	public void setLogPrinter(java.lang.String logPrinter){
		this.logPrinter	= logPrinter;
	}

	/**
	 * 获取 最后更新日期 new add 的属性值
	 * @return lstUpdData : 最后更新日期 new add
	 * @author OM
	 */
	public java.lang.String getLstUpdData(){
		return this.lstUpdData;
	}

	/**
	 * 设置 最后更新日期 new add 的属性值
	 * @param lstUpdData : 最后更新日期 new add
	 * @author OM
	 */
	public void setLstUpdData(java.lang.String lstUpdData){
		this.lstUpdData	= lstUpdData;
	}

	/**
	 * 获取 最后更新柜员 new add 的属性值
	 * @return lstUpdOpr : 最后更新柜员 new add
	 * @author OM
	 */
	public java.lang.String getLstUpdOpr(){
		return this.lstUpdOpr;
	}

	/**
	 * 设置 最后更新柜员 new add 的属性值
	 * @param lstUpdOpr : 最后更新柜员 new add
	 * @author OM
	 */
	public void setLstUpdOpr(java.lang.String lstUpdOpr){
		this.lstUpdOpr	= lstUpdOpr;
	}

	/**
	 * 获取 机箱门状态 的属性值
	 * @return machineDoorStat : 机箱门状态
	 * @author OM
	 */
	public java.lang.String getMachineDoorStat(){
		return this.machineDoorStat;
	}

	/**
	 * 设置 机箱门状态 的属性值
	 * @param machineDoorStat : 机箱门状态
	 * @author OM
	 */
	public void setMachineDoorStat(java.lang.String machineDoorStat){
		this.machineDoorStat	= machineDoorStat;
	}

	/**
	 * 获取 万事顺参数文件版本号  new add 的属性值
	 * @return mccParamNo : 万事顺参数文件版本号  new add
	 * @author OM
	 */
	public java.lang.String getMccParamNo(){
		return this.mccParamNo;
	}

	/**
	 * 设置 万事顺参数文件版本号  new add 的属性值
	 * @param mccParamNo : 万事顺参数文件版本号  new add
	 * @author OM
	 */
	public void setMccParamNo(java.lang.String mccParamNo){
		this.mccParamNo	= mccParamNo;
	}

	/**
	 * 获取 支行号（营业网点） 的属性值
	 * @return netCode : 支行号（营业网点）
	 * @author OM
	 */
	public java.lang.String getNetCode(){
		return this.netCode;
	}

	/**
	 * 设置 支行号（营业网点） 的属性值
	 * @param netCode : 支行号（营业网点）
	 * @author OM
	 */
	public void setNetCode(java.lang.String netCode){
		this.netCode	= netCode;
	}

	/**
	 * 获取 新PINBlock 的属性值
	 * @return newPinBlock : 新PINBlock
	 * @author OM
	 */
	public java.lang.String getNewPinBlock(){
		return this.newPinBlock;
	}

	/**
	 * 设置 新PINBlock 的属性值
	 * @param newPinBlock : 新PINBlock
	 * @author OM
	 */
	public void setNewPinBlock(java.lang.String newPinBlock){
		this.newPinBlock	= newPinBlock;
	}

	/**
	 * 获取 旧PINBlock 的属性值
	 * @return oldPinBlock : 旧PINBlock
	 * @author OM
	 */
	public java.lang.String getOldPinBlock(){
		return this.oldPinBlock;
	}

	/**
	 * 设置 旧PINBlock 的属性值
	 * @param oldPinBlock : 旧PINBlock
	 * @author OM
	 */
	public void setOldPinBlock(java.lang.String oldPinBlock){
		this.oldPinBlock	= oldPinBlock;
	}

	/**
	 * 获取 oldTermCode 的属性值
	 * @return oldTermCode : oldTermCode
	 * @author OM
	 */
	public java.lang.String getOldTermCode(){
		return this.oldTermCode;
	}

	/**
	 * 设置 oldTermCode 的属性值
	 * @param oldTermCode : oldTermCode
	 * @author OM
	 */
	public void setOldTermCode(java.lang.String oldTermCode){
		this.oldTermCode	= oldTermCode;
	}

	/**
	 * 获取 当前开机时间 的属性值
	 * @return openTime : 当前开机时间
	 * @author OM
	 */
	public java.lang.String getOpenTime(){
		return this.openTime;
	}

	/**
	 * 设置 当前开机时间 的属性值
	 * @param openTime : 当前开机时间
	 * @author OM
	 */
	public void setOpenTime(java.lang.String openTime){
		this.openTime	= openTime;
	}

	/**
	 * 获取 出卡口状态 的属性值
	 * @return outcardStat : 出卡口状态
	 * @author OM
	 */
	public java.lang.String getOutcardStat(){
		return this.outcardStat;
	}

	/**
	 * 设置 出卡口状态 的属性值
	 * @param outcardStat : 出卡口状态
	 * @author OM
	 */
	public void setOutcardStat(java.lang.String outcardStat){
		this.outcardStat	= outcardStat;
	}

	/**
	 * 获取 出钞口状态 的属性值
	 * @return outcurStat : 出钞口状态
	 * @author OM
	 */
	public java.lang.String getOutcurStat(){
		return this.outcurStat;
	}

	/**
	 * 设置 出钞口状态 的属性值
	 * @param outcurStat : 出钞口状态
	 * @author OM
	 */
	public void setOutcurStat(java.lang.String outcurStat){
		this.outcurStat	= outcurStat;
	}

	/**
	 * 获取 卡号长度 的属性值
	 * @return panL : 卡号长度
	 * @author OM
	 */
	public java.lang.String getPanL(){
		return this.panL;
	}

	/**
	 * 设置 卡号长度 的属性值
	 * @param panL : 卡号长度
	 * @author OM
	 */
	public void setPanL(java.lang.String panL){
		this.panL	= panL;
	}

	/**
	 * 获取 卡号 的属性值
	 * @return panV : 卡号
	 * @author OM
	 */
	public java.lang.String getPanV(){
		return this.panV;
	}

	/**
	 * 设置 卡号 的属性值
	 * @param panV : 卡号
	 * @author OM
	 */
	public void setPanV(java.lang.String panV){
		this.panV	= panV;
	}

	/**
	 * 获取 参数版本号 的属性值
	 * @return parameterverNo : 参数版本号
	 * @author OM
	 */
	public java.lang.String getParameterverNo(){
		return this.parameterverNo;
	}

	/**
	 * 设置 参数版本号 的属性值
	 * @param parameterverNo : 参数版本号
	 * @author OM
	 */
	public void setParameterverNo(java.lang.String parameterverNo){
		this.parameterverNo	= parameterverNo;
	}

	/**
	 * 获取 参数数据 的属性值
	 * @return parameterData : 参数数据
	 * @author OM
	 */
	public java.lang.String getParameterData(){
		return this.parameterData;
	}

	/**
	 * 设置 参数数据 的属性值
	 * @param parameterData : 参数数据
	 * @author OM
	 */
	public void setParameterData(java.lang.String parameterData){
		this.parameterData	= parameterData;
	}

	/**
	 * 获取 收据打印机状态 的属性值
	 * @return receiptPrinter : 收据打印机状态
	 * @author OM
	 */
	public java.lang.String getReceiptPrinter(){
		return this.receiptPrinter;
	}

	/**
	 * 设置 收据打印机状态 的属性值
	 * @param receiptPrinter : 收据打印机状态
	 * @author OM
	 */
	public void setReceiptPrinter(java.lang.String receiptPrinter){
		this.receiptPrinter	= receiptPrinter;
	}

	/**
	 * 获取 应答码 的属性值
	 * @return responseCode : 应答码
	 * @author OM
	 */
	public java.lang.String getResponseCode(){
		return this.responseCode;
	}

	/**
	 * 设置 应答码 的属性值
	 * @param responseCode : 应答码
	 * @author OM
	 */
	public void setResponseCode(java.lang.String responseCode){
		this.responseCode	= responseCode;
	}

	/**
	 * 获取 保留域1 的属性值
	 * @return resData1 : 保留域1
	 * @author OM
	 */
	public java.lang.String getResData1(){
		return this.resData1;
	}

	/**
	 * 设置 保留域1 的属性值
	 * @param resData1 : 保留域1
	 * @author OM
	 */
	public void setResData1(java.lang.String resData1){
		this.resData1	= resData1;
	}

	/**
	 * 获取 保留域2 的属性值
	 * @return resData2 : 保留域2
	 * @author OM
	 */
	public java.lang.String getResData2(){
		return this.resData2;
	}

	/**
	 * 设置 保留域2 的属性值
	 * @param resData2 : 保留域2
	 * @author OM
	 */
	public void setResData2(java.lang.String resData2){
		this.resData2	= resData2;
	}

	/**
	 * 获取 黑名单文件版本号  new add 的属性值
	 * @return riskFileNo : 黑名单文件版本号  new add
	 * @author OM
	 */
	public java.lang.String getRiskFileNo(){
		return this.riskFileNo;
	}

	/**
	 * 设置 黑名单文件版本号  new add 的属性值
	 * @param riskFileNo : 黑名单文件版本号  new add
	 * @author OM
	 */
	public void setRiskFileNo(java.lang.String riskFileNo){
		this.riskFileNo	= riskFileNo;
	}

	/**
	 * 获取 装钞金额 的属性值
	 * @return rwtAmt : 装钞金额
	 * @author OM
	 */
	public java.lang.String getRwtAmt(){
		return this.rwtAmt;
	}

	/**
	 * 设置 装钞金额 的属性值
	 * @param rwtAmt : 装钞金额
	 * @author OM
	 */
	public void setRwtAmt(java.lang.String rwtAmt){
		this.rwtAmt	= rwtAmt;
	}

	/**
	 * 获取 装钞日期 的属性值
	 * @return rwtDate : 装钞日期
	 * @author OM
	 */
	public java.lang.String getRwtDate(){
		return this.rwtDate;
	}

	/**
	 * 设置 装钞日期 的属性值
	 * @param rwtDate : 装钞日期
	 * @author OM
	 */
	public void setRwtDate(java.lang.String rwtDate){
		this.rwtDate	= rwtDate;
	}

	/**
	 * 获取 装钞时间 的属性值
	 * @return rwtTime : 装钞时间
	 * @author OM
	 */
	public java.lang.String getRwtTime(){
		return this.rwtTime;
	}

	/**
	 * 设置 装钞时间 的属性值
	 * @param rwtTime : 装钞时间
	 * @author OM
	 */
	public void setRwtTime(java.lang.String rwtTime){
		this.rwtTime	= rwtTime;
	}

	/**
	 * 获取 一次最大存款金额 的属性值
	 * @return savingMax : 一次最大存款金额
	 * @author OM
	 */
	public java.lang.String getSavingMax(){
		return this.savingMax;
	}

	/**
	 * 设置 一次最大存款金额 的属性值
	 * @param savingMax : 一次最大存款金额
	 * @author OM
	 */
	public void setSavingMax(java.lang.String savingMax){
		this.savingMax	= savingMax;
	}

	/**
	 * 获取 转入卡号 的属性值
	 * @return secondPanV : 转入卡号
	 * @author OM
	 */
	public java.lang.String getSecondPanV(){
		return this.secondPanV;
	}

	/**
	 * 设置 转入卡号 的属性值
	 * @param secondPanV : 转入卡号
	 * @author OM
	 */
	public void setSecondPanV(java.lang.String secondPanV){
		this.secondPanV	= secondPanV;
	}

	/**
	 * 获取 已对帐标志 的属性值
	 * @return settlementFlag : 已对帐标志
	 * @author OM
	 */
	public java.lang.String getSettlementFlag(){
		return this.settlementFlag;
	}

	/**
	 * 设置 已对帐标志 的属性值
	 * @param settlementFlag : 已对帐标志
	 * @author OM
	 */
	public void setSettlementFlag(java.lang.String settlementFlag){
		this.settlementFlag	= settlementFlag;
	}

	/**
	 * 获取 软件版本号 的属性值
	 * @return softverNo : 软件版本号
	 * @author OM
	 */
	public java.lang.String getSoftverNo(){
		return this.softverNo;
	}

	/**
	 * 设置 软件版本号 的属性值
	 * @param softverNo : 软件版本号
	 * @author OM
	 */
	public void setSoftverNo(java.lang.String softverNo){
		this.softverNo	= softverNo;
	}

	/**
	 * 获取 间隔发送状态包时长 的属性值
	 * @return spaceTime : 间隔发送状态包时长
	 * @author OM
	 */
	public java.lang.String getSpaceTime(){
		return this.spaceTime;
	}

	/**
	 * 设置 间隔发送状态包时长 的属性值
	 * @param spaceTime : 间隔发送状态包时长
	 * @author OM
	 */
	public void setSpaceTime(java.lang.String spaceTime){
		this.spaceTime	= spaceTime;
	}

	/**
	 * 获取 初始摆放日期  new add 的属性值
	 * @return startDate : 初始摆放日期  new add
	 * @author OM
	 */
	public java.lang.String getStartDate(){
		return this.startDate;
	}

	/**
	 * 设置 初始摆放日期  new add 的属性值
	 * @param startDate : 初始摆放日期  new add
	 * @author OM
	 */
	public void setStartDate(java.lang.String startDate){
		this.startDate	= startDate;
	}

	/**
	 * 获取 ATM状态 的属性值
	 * @return status : ATM状态
	 * @author OM
	 */
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 * 设置 ATM状态 的属性值
	 * @param status : ATM状态
	 * @author OM
	 */
	public void setStatus(java.lang.String status){
		this.status	= status;
	}

	/**
	 * 获取 今日累计装钞金额 的属性值
	 * @return tdAcmRwtAmt : 今日累计装钞金额
	 * @author OM
	 */
	public java.lang.String getTdAcmRwtAmt(){
		return this.tdAcmRwtAmt;
	}

	/**
	 * 设置 今日累计装钞金额 的属性值
	 * @param tdAcmRwtAmt : 今日累计装钞金额
	 * @author OM
	 */
	public void setTdAcmRwtAmt(java.lang.String tdAcmRwtAmt){
		this.tdAcmRwtAmt	= tdAcmRwtAmt;
	}

	/**
	 * 获取 今日累计装钞次数 的属性值
	 * @return tdAcmRwtCnt : 今日累计装钞次数
	 * @author OM
	 */
	public java.lang.String getTdAcmRwtCnt(){
		return this.tdAcmRwtCnt;
	}

	/**
	 * 设置 今日累计装钞次数 的属性值
	 * @param tdAcmRwtCnt : 今日累计装钞次数
	 * @author OM
	 */
	public void setTdAcmRwtCnt(java.lang.String tdAcmRwtCnt){
		this.tdAcmRwtCnt	= tdAcmRwtCnt;
	}

	/**
	 * 获取 联系电话 的属性值
	 * @return telephone : 联系电话
	 * @author OM
	 */
	public java.lang.String getTelephone(){
		return this.telephone;
	}

	/**
	 * 设置 联系电话 的属性值
	 * @param telephone : 联系电话
	 * @author OM
	 */
	public void setTelephone(java.lang.String telephone){
		this.telephone	= telephone;
	}

	/**
	 * 获取  终端号码  的属性值
	 * @return terminalCode :  终端号码 
	 * @author OM
	 */
	public java.lang.String getTerminalCode(){
		return this.terminalCode;
	}

	/**
	 * 设置  终端号码  的属性值
	 * @param terminalCode :  终端号码 
	 * @author OM
	 */
	public void setTerminalCode(java.lang.String terminalCode){
		this.terminalCode	= terminalCode;
	}

	/**
	 * 获取 ATM厂商 的属性值
	 * @return terminalVendor : ATM厂商
	 * @author OM
	 */
	public java.lang.String getTerminalVendor(){
		return this.terminalVendor;
	}

	/**
	 * 设置 ATM厂商 的属性值
	 * @param terminalVendor : ATM厂商
	 * @author OM
	 */
	public void setTerminalVendor(java.lang.String terminalVendor){
		this.terminalVendor	= terminalVendor;
	}

	/**
	 * 获取  终端状态  的属性值
	 * @return termSt :  终端状态 
	 * @author OM
	 */
	public java.lang.String getTermSt(){
		return this.termSt;
	}

	/**
	 * 设置  终端状态  的属性值
	 * @param termSt :  终端状态 
	 * @author OM
	 */
	public void setTermSt(java.lang.String termSt){
		this.termSt	= termSt;
	}

	/**
	 * 获取  终端类型  new add 的属性值
	 * @return termType :  终端类型  new add
	 * @author OM
	 */
	public java.lang.String getTermType(){
		return this.termType;
	}

	/**
	 * 设置  终端类型  new add 的属性值
	 * @param termType :  终端类型  new add
	 * @author OM
	 */
	public void setTermType(java.lang.String termType){
		this.termType	= termType;
	}

	/**
	 * 获取 ATM厂商名称  new add 的属性值
	 * @return termVendorNm : ATM厂商名称  new add
	 * @author OM
	 */
	public java.lang.String getTermVendorNm(){
		return this.termVendorNm;
	}

	/**
	 * 设置 ATM厂商名称  new add 的属性值
	 * @param termVendorNm : ATM厂商名称  new add
	 * @author OM
	 */
	public void setTermVendorNm(java.lang.String termVendorNm){
		this.termVendorNm	= termVendorNm;
	}

	/**
	 * 获取 柜员号 的属性值
	 * @return tlrno : 柜员号
	 * @author OM
	 */
	public java.lang.String getTlrno(){
		return this.tlrno;
	}

	/**
	 * 设置 柜员号 的属性值
	 * @param tlrno : 柜员号
	 * @author OM
	 */
	public void setTlrno(java.lang.String tlrno){
		this.tlrno	= tlrno;
	}

	/**
	 * 获取 存钞总笔数 的属性值
	 * @return totalCdmCnt : 存钞总笔数
	 * @author OM
	 */
	public java.lang.String getTotalCdmCnt(){
		return this.totalCdmCnt;
	}

	/**
	 * 设置 存钞总笔数 的属性值
	 * @param totalCdmCnt : 存钞总笔数
	 * @author OM
	 */
	public void setTotalCdmCnt(java.lang.String totalCdmCnt){
		this.totalCdmCnt	= totalCdmCnt;
	}

	/**
	 * 获取 吐钞总额 的属性值
	 * @return totalOut : 吐钞总额
	 * @author OM
	 */
	public java.lang.String getTotalOut(){
		return this.totalOut;
	}

	/**
	 * 设置 吐钞总额 的属性值
	 * @param totalOut : 吐钞总额
	 * @author OM
	 */
	public void setTotalOut(java.lang.String totalOut){
		this.totalOut	= totalOut;
	}

	/**
	 * 获取 废钞总额 的属性值
	 * @return totalWaste : 废钞总额
	 * @author OM
	 */
	public java.lang.String getTotalWaste(){
		return this.totalWaste;
	}

	/**
	 * 设置 废钞总额 的属性值
	 * @param totalWaste : 废钞总额
	 * @author OM
	 */
	public void setTotalWaste(java.lang.String totalWaste){
		this.totalWaste	= totalWaste;
	}

	/**
	 * 获取 交易金额 的属性值
	 * @return transactionAmount : 交易金额
	 * @author OM
	 */
	public java.lang.String getTransactionAmount(){
		return this.transactionAmount;
	}

	/**
	 * 设置 交易金额 的属性值
	 * @param transactionAmount : 交易金额
	 * @author OM
	 */
	public void setTransactionAmount(java.lang.String transactionAmount){
		this.transactionAmount	= transactionAmount;
	}

	/**
	 * 获取 交易代码 的属性值
	 * @return transactionCode : 交易代码
	 * @author OM
	 */
	public java.lang.String getTransactionCode(){
		return this.transactionCode;
	}

	/**
	 * 设置 交易代码 的属性值
	 * @param transactionCode : 交易代码
	 * @author OM
	 */
	public void setTransactionCode(java.lang.String transactionCode){
		this.transactionCode	= transactionCode;
	}

	/**
	 * 获取 交易发生时间 的属性值
	 * @return transmissionTime : 交易发生时间
	 * @author OM
	 */
	public java.lang.String getTransmissionTime(){
		return this.transmissionTime;
	}

	/**
	 * 设置 交易发生时间 的属性值
	 * @param transmissionTime : 交易发生时间
	 * @author OM
	 */
	public void setTransmissionTime(java.lang.String transmissionTime){
		this.transmissionTime	= transmissionTime;
	}

	/**
	 * 获取 交易信息 的属性值
	 * @return txnInf : 交易信息
	 * @author OM
	 */
	public java.lang.String getTxnInf(){
		return this.txnInf;
	}

	/**
	 * 设置 交易信息 的属性值
	 * @param txnInf : 交易信息
	 * @author OM
	 */
	public void setTxnInf(java.lang.String txnInf){
		this.txnInf	= txnInf;
	}

	/**
	 * 获取 定时上传对账文件时间  new add  的属性值
	 * @return uldFileTime : 定时上传对账文件时间  new add 
	 * @author OM
	 */
	public java.lang.String getUldFileTime(){
		return this.uldFileTime;
	}

	/**
	 * 设置 定时上传对账文件时间  new add  的属性值
	 * @param uldFileTime : 定时上传对账文件时间  new add 
	 * @author OM
	 */
	public void setUldFileTime(java.lang.String uldFileTime){
		this.uldFileTime	= uldFileTime;
	}

	/**
	 * 获取 营业单位代码  管机行  的属性值
	 * @return unitCode : 营业单位代码  管机行 
	 * @author OM
	 */
	public java.lang.String getUnitCode(){
		return this.unitCode;
	}

	/**
	 * 设置 营业单位代码  管机行  的属性值
	 * @param unitCode : 营业单位代码  管机行 
	 * @author OM
	 */
	public void setUnitCode(java.lang.String unitCode){
		this.unitCode	= unitCode;
	}

	/**
	 * 获取 一次最大转帐金额 的属性值
	 * @return virementMax : 一次最大转帐金额
	 * @author OM
	 */
	public java.lang.String getVirementMax(){
		return this.virementMax;
	}

	/**
	 * 设置 一次最大转帐金额 的属性值
	 * @param virementMax : 一次最大转帐金额
	 * @author OM
	 */
	public void setVirementMax(java.lang.String virementMax){
		this.virementMax	= virementMax;
	}

	/**
	 * 获取 废钞箱状态 的属性值
	 * @return wasteBoxStat : 废钞箱状态
	 * @author OM
	 */
	public java.lang.String getWasteBoxStat(){
		return this.wasteBoxStat;
	}

	/**
	 * 设置 废钞箱状态 的属性值
	 * @param wasteBoxStat : 废钞箱状态
	 * @author OM
	 */
	public void setWasteBoxStat(java.lang.String wasteBoxStat){
		this.wasteBoxStat	= wasteBoxStat;
	}

	/**
	 * 获取 废钞箱张数 的属性值
	 * @return wasteCount : 废钞箱张数
	 * @author OM
	 */
	public java.lang.String getWasteCount(){
		return this.wasteCount;
	}

	/**
	 * 设置 废钞箱张数 的属性值
	 * @param wasteCount : 废钞箱张数
	 * @author OM
	 */
	public void setWasteCount(java.lang.String wasteCount){
		this.wasteCount	= wasteCount;
	}

	/**
	 * 转换为字符串
	 * @author OM
	 */
	public String toString(){
		return super.toString();
	}
}