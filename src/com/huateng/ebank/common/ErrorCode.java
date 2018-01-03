package com.huateng.ebank.common;

public class ErrorCode {
	public static final String ERROR_CODE_TLRNO_NO_FUNCTION = "SYS001"; //操作员无此功能权限
	public static final String ERROR_CODE_TLRNO_SESSION_INVALID = "SYS002"; //操作员会话无效
	public static final String ERROR_CODE_TLRNO_SESSION_BINDED = "SYS003"; //此会话已经被其他操作员绑定
	public static final String ERROR_CODE_NO_GLOBALINFO_INSTANCE = "SYS004"; //系统错误，没有初始化全局信息
	public static final String ERROR_CODE_GLOBALINFO_BATCH = "SYS005"; //系统处于批量状态
	public static final String ERROR_CODE_DATE_FORMAT_ERR = "SYS006"; //日期格式错误
	public static final String ERROR_CODE_DAO = "SYS007"; //数据库访问错误
	public static final String ERROR_CODE_BRHCLASS_ERROR = "SYS008"; //机构级别错误
	public static final String ERROR_CODE_CANNOT_SUBMIT = "SYS888"; //不能提交
	public static final String ERROR_CODE_UNKNOW = "SYS999"; //未知错误
	public static final String ERROR_CODE_ACTSET = "SYS901"; //配置错误
	
	/**
	 * 系统错误代码 GD0001~GD0100
	 */
	public static final String ERROR_CODE_UNKNOWN = "GD0001"; //未知错误
	public static final String ERROR_CODE_DATA_FORMAT_ERR = "GD0003"; //数据格式错误
	public static final String ERROR_CODE_INTERNAL_ERROR = "GD0004"; //应用系统内部错误
	public static final String ERROR_CODE_DOWNLOAD = "GD0006"; //下载文件失败
	public static final String ERROR_CODE_INVALID_FORMAT = "GD0007"; //格式错误
	public static final String ERROR_CODE_WS_COMM_ERROR  ="SE0008"; // 通讯错误

	
	/**
	 * add by shen_antonio 2008-03-27
	 * 工作流相关任务分配错误码
	 */
	public static final String ERROR_CODE_TLRLVDAY_ERROR ="GDW001";//休假起始日不能在休假结束日之后
	public static final String ERROR_CODE_TLRVDAY_APPLY_ERROR = "GDW002";//休假申请失败
	public static final String ERROR_CODE_TLRVDAY_CANCEL_ERROR = "GDW003";//销假失败
	public static final String ERROR_CODE_TASK_GET_ASSIGN_TLRNO_ERROR = "GDW004";//工作流获得该该分配的操作员失败
	public static final String ERROR_CODE_TASK_ASSIGN_ERROR = "GDW005";//工作流分配任务失败
	public static final String ERROR_CODE_TASK_ASSIGN_DOFINISH_ERROR = "GDW006";//工作流完成任务操作失败
	public static final String ERROR_CODE_IU_TLR_WL_HS_ERROR = "GDW007";//操作员每日工作量统计表错误(记录重复，单个操作员单日，同种工作类型的记录有多条)
	public static final String ERROR_CODE_IU_TLR_WORKLOAD_ERROR = "GDW008";//操作员工作量统计表错误(记录重复，操作员，同种工作类型的记录有多条)
	public static final String ERROR_CODE_TASK_FORWARD_ERROR = "GDW009";//操作员工作移交失败
	public static final String ERROR_CODE_WORKFLOW_START_ERROR = "GDW010";//工作流服务启动工作流失败
	public static final String ERROR_CODE_WORKFLOW_DOFINISH_ERROR = "GDW011";//工作流服务执行任务失败
	public static final String ERROR_CODE_WORKFLOW_FORWARD_ERROR = "GDW012";//工作流服务任务移交失败
	public static final String ERROR_CODE_WORKFLOW_GETTASKLIST_ERROR = "GDW013";//工作流服务获取工作列表失败
	public static final String ERROR_CODE_WORKFLOW_GETTASKVALUE_ERROR = "GDW014";//工作流服务获取任务参数失败
	public static final String ERROR_CODE_WORKFLOW_SETTASKVALUE_ERROR = "GDW015";//工作流服务设置任务参数失败
	public static final String ERROR_CODE_WORKFLOW_LOCKTASK_ERROR = "GDW016";//工作流服务锁任务失败
	public static final String ERROR_CODE_WORKFLOW_RELEASETASK_ERROR = "GDW017";//工作流服务释放任务失败
	public static final String ERROR_CODE_WORKFLOW_KEEPTASK_ERROR = "GDW018";//工作流服务保留任务失败
	public static final String ERROR_CODE_WORKFLOW_GETFLOWINS_ERROR = "GDW019";//工作流服务获取流程实例失败
	public static final String ERROR_CODE_WORKFLOW_CLOSEFLOWINS_ERROR = "GDW020";//工作流服务结束流程实例失败
	public static final String ERROR_CODE_WORKFLOWRULE_DYN_FLOWNAME_EMPTY = "GDW021";//工作流规则启动错误，设置动态路由，没有对应流程名
	public static final String ERROR_CODE_WORKFLOWTASKRULE_CONTEXT= "GDW022";//工作流工作规则启动错误,输入参数错误(TaskId 不能为空 ProcId 不能为空 TlrList 不能为空 Status 不能为空)
	public static final String ERROR_CODE_WORKFLOWCONTEXT_COPY_TASKINFO= "GDW023";//工作流工作内容拷贝出错
	public static final String ERROR_CODE_TASK_ENDPROCTASK = "GDW024";//取消操作员任务分配记录
	
	public static final String ERROR_CODE_DB_SELECT = "DB1001"; //数据库查询出错
	public static final String ERROR_CODE_DB_UPDATE = "DB1002"; //数据库更新出错
	public static final String ERROR_CODE_DB_INSERT = "DB1003"; //数据库插入出错
	public static final String ERROR_CODE_DB_DELETE = "DB1004"; //数据库删除出错
	
	public static final String ERROR_CODE_USER_NOT_EXIST = "GD0108";// 用户不存在
}
