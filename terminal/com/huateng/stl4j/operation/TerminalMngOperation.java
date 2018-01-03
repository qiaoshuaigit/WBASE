package com.huateng.stl4j.operation;

import java.util.Iterator;
import java.util.List;

import om.util.OmUtils;

import org.apache.log4j.Logger;

import com.huateng.ebank.business.common.service.DataDicService;
import com.huateng.ebank.common.GlobalInfo;
import com.huateng.exception.AppException;
import com.huateng.stl4j.common.BaseOperation;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.OperationContext;
import com.huateng.stl4j.entity.TblAtmStat;

public class TerminalMngOperation extends BaseOperation{
	private static Logger logger = Logger.getLogger(TerminalMngOperation.class);
	public static String ID = TerminalMngOperation.class.getName();
	public static String DEL_LIST = "DEL_LIST";
	public static String INS_LIST = "INS_LIST";
	public static String UPD_LIST = "UPD_LIST";
	public static String TERMINAL_CODE = "TERMINAL_CODE";
	public static String OP_TYPE = "OP_TYPE";
	public static String CHK_DSC = "CHK_DSC";
	private CommonDAO dao;

	public void beforeProc(OperationContext context) throws Exception {
	}
	
	@SuppressWarnings("unchecked")
	public void execute(OperationContext context) throws Exception {
		String opType = (String)context.get(OP_TYPE);
		GlobalInfo g = GlobalInfo.getCurrentInstance();
		
		// 新增、修改操作
		if(!OmUtils.isEmpty(opType) && "0".equals(opType)){
			List<TblAtmStat> delList = (List<TblAtmStat>) context.get(DEL_LIST);
			List<TblAtmStat> updList = (List<TblAtmStat>) context.get(UPD_LIST);
			List<TblAtmStat> insList = (List<TblAtmStat>) context.get(INS_LIST);
			//删除
			StringBuilder idList = new StringBuilder();
			Iterator<TblAtmStat> delit = delList.iterator();
			while (delit.hasNext()) {
				TblAtmStat bean = delit.next();
				idList.append(bean.getTerminalCode()).append(",");
			}
			if(idList.length() > 0 && idList.charAt(idList.length() - 1) == ',') {
				idList.setLength(idList.length() - 1);
			}
			if(idList.length() > 0) {
				StringBuilder delHql = new StringBuilder("update TblAtmStat po set termSt='1',chkStatus='1' where po.terminalCode in(");
				delHql.append(idList.toString()).append(")");
				dao.executeUpdate(delHql.toString());
				logger.info(delHql.toString());
			}
			//修改
			Iterator<TblAtmStat> updit = updList.iterator();
			while (updit.hasNext()) {
				TblAtmStat bean = updit.next();
				TblAtmStat dbBean = (TblAtmStat) dao.get(TblAtmStat.class, bean.getTerminalCode());
				dbBean.setBrno(bean.getBrno());
				dbBean.setAddress(bean.getAddress());
				dbBean.setCommAddr(bean.getCommAddr());
				dbBean.setTermVendorNm(bean.getTermVendorNm());
				dbBean.setStartDate(bean.getStartDate());
				dbBean.setCupOrgan(bean.getCupOrgan());
				dbBean.setIpAddress(bean.getIpAddress());
				dbBean.setTermSt("4");
				dbBean.setChkStatus("4");
				dbBean.setLstUpdData(OmUtils.getLocalDateTime(OmUtils.TIME_NUM_PATTERN));
				dbBean.setLstUpdOpr(g.getOprNo());
				dao.update(dbBean);
			}
			//新增
			Iterator<TblAtmStat> insit = insList.iterator();
			while (insit.hasNext()) {
				TblAtmStat bean = insit.next();
				bean.setLstUpdData(OmUtils.getLocalDateTime(OmUtils.TIME_NUM_PATTERN));
				bean.setLstUpdOpr(g.getOprNo());
				insitData(bean);
			}
		}
		//启用、停用操作
		else if(!OmUtils.isEmpty(opType) && ("1".equals(opType) || "2".equals(opType))){
			String terminalCode = (String)context.get(TERMINAL_CODE);
			TblAtmStat dbBean = (TblAtmStat) dao.get(TblAtmStat.class, terminalCode);
			if("2".equals(opType)){//启动
				dbBean.setChkStatus("2");
			}
			else{//停用
				dbBean.setChkStatus("1");
			}
			dbBean.setLstUpdData(OmUtils.getLocalDateTime(OmUtils.TIME_NUM_PATTERN));
			dbBean.setLstUpdOpr(g.getOprNo());
			dao.update(dbBean);
		}
		//审核通过、审核拒绝
		else if(!OmUtils.isEmpty(opType) && ("4".equals(opType) || "8".equals(opType))){
			String terminalCode = (String)context.get(TERMINAL_CODE);
			String chkDsc = (String)context.get(CHK_DSC);
			TblAtmStat dbBean = (TblAtmStat) dao.get(TblAtmStat.class, terminalCode);
			String dbChkStatus = dbBean.getChkStatus();
			if("4".equals(opType)){
				dbBean.setTermSt(dbBean.getChkStatus());
			}
			dbBean.setChkStatus(String.valueOf(Integer.parseInt(dbChkStatus)+Integer.parseInt(opType)));
			dbBean.setChkDsc(chkDsc);
			dbBean.setLstUpdData(OmUtils.getLocalDateTime(OmUtils.TIME_NUM_PATTERN));
			dbBean.setLstUpdOpr(g.getOprNo());
			dao.update(dbBean);
		}
		else{
			throw new AppException("未知的操作!");
		}
		
	}

	public void afterProc(OperationContext context) throws Exception {
	}

	public CommonDAO getDao() {
		return dao;
	}

	public void setDao(CommonDAO dao) {
		this.dao = dao;
	}
	
	synchronized private void insitData(TblAtmStat bean) throws AppException{
		String zoneCode = DataDicService.getInstance().mapOutToIn(100101, bean.getCupOrgan());
		if(OmUtils.isEmpty(zoneCode)){
			throw new AppException("非法的地区代码。");
		}
		String seq = "000000";
		String hql = "select max(po.terminalCode) from TblAtmStat po where po.terminalCode like '"+zoneCode+"%'";
		List<?> list = dao.findByHQL(hql);
		if(null != list && list.size()>0){
			String dbMaxTermCode = (String)list.get(0);
			if(!OmUtils.isEmpty(dbMaxTermCode) && dbMaxTermCode.length()>2){
				seq = dbMaxTermCode.substring(2);
			}
		}
		seq = zoneCode+String.format("%06d", new Integer(seq)+1);
		bean.setTerminalCode(seq);
		bean.setCommPid(seq);
		dao.insert(bean);
	}
	
}
