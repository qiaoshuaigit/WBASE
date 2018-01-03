package com.huateng.ebank.business.common.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.common.GlobalInfo;
import com.huateng.ebank.entity.Operator;
import com.huateng.ebank.entity.SystemInfo;
import com.huateng.stl4j.common.ApplicationContextUtils;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.service.SystemParamService;
import com.huateng.tools.DateTool;

public class CommonService {
	private static Logger logger = Logger.getLogger(CommonService.class);
	
	protected CommonService() {
	}
	
	public synchronized static CommonService getInstance() {
		return (CommonService)ApplicationContextUtils.getBean(CommonService.class.getName());
	}
	
	/**
	 * 审批编号
	 * @return YYYYMMDD + SEQ_APPNO(6)
	 * @throws CommonException
	 */
	public String getAppno() throws CommonException {
		GlobalInfo g = GlobalInfo.getCurrentInstance();
		return String.format("%s%06d", g.getSysDate(), CommonDAO.getInstance().getNextValueOfSequences("SEQ_APPNO"));
	}
	
	/**
	 * 流水号
	 * @return
	 * @throws CommonException
	 */
	public static String getTxnSeqNum() throws CommonException {
		CommonDAO dao = CommonDAO.getInstance();
		SystemInfo si = (SystemInfo) dao.get(SystemInfo.class, new Integer(1));
		return String.format("%s%08d", si.getSysDate(), CommonDAO.getInstance().getNextValueOfSequences("SEQ_TXNSEQNUM"));
	}
	
	/**
	 * @param oprNo 用户号 
	 * @param type  in-登录；out-登出
	 */
	public void oprInOutLog(String oprNo,String type){
		CommonDAO dao = CommonDAO.getInstance();
		List list = dao.findByWhere(Operator.class, "po.oprNo=?",
				new Object[] { oprNo });
		if (null != list && list.size() > 0) {
			Operator opr = null;
			opr = (Operator) list.get(0);
			if("in".equals(type)){
				opr.setStatusIn("1");
				opr.setInTime(DateTool.getDateTime());
				opr.setOutTime("");
			}else{
				opr.setStatusIn("0");
				opr.setOutTime(DateTool.getDateTime());
			}
			dao.update(opr);
		}
	}
	
	public void checkSysMngOprNos() throws CommonException {
		GlobalInfo g = GlobalInfo.getCurrentInstance();
		String oprNo = g.getOprNo();
		String mngOprNos = SystemParamService.getInstance().getParamValue("SYS_MNG", "OPRNOS");
		if(mngOprNos.indexOf(oprNo) < 0) {
			throw new CommonException("无此功能权限，不能提交！");
		}
	}
}
