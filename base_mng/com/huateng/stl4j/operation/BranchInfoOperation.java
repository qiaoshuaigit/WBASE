package com.huateng.stl4j.operation;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.ebank.entity.BranchInfo;
import com.huateng.stl4j.common.BaseOperation;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.OperationContext;


public class BranchInfoOperation extends BaseOperation {
	private static Logger logger = Logger.getLogger(BranchInfoOperation.class);
	public static String ID = BranchInfoOperation.class.getName();
	public static String DEL_LIST = "DEL_LIST";
	public static String INS_LIST = "INS_LIST";
	public static String UPD_LIST = "UPD_LIST";
	private CommonDAO dao;

	public void beforeProc(OperationContext context) throws Exception {
	}
	
	public void execute(OperationContext context) throws Exception {
		List<BranchInfo> delList = (List<BranchInfo>) context.get(DEL_LIST);
		List<BranchInfo> updList = (List<BranchInfo>) context.get(UPD_LIST);
		List<BranchInfo> insList = (List<BranchInfo>) context.get(INS_LIST);
		
		
		BranchInfo t = new BranchInfo();
		List<BranchInfo> list = dao.findByWhere(BranchInfo.class," brhClass='1'");
		if(list!=null && list.size()>0){
			if(list.size()>1){
				throw new Exception("存在多个省联社，请联系运维人员检查机构数据！");
			}else{
				t = (BranchInfo)list.get(0);
			}
		}else{
			throw new Exception("缺失省联社，请联系运维人员检查机构数据！");
		}
		
		//删除
		StringBuilder idList = new StringBuilder();
		Iterator<BranchInfo> delit = delList.iterator();
		while (delit.hasNext()) {
			BranchInfo bean = delit.next();
			if(t.getId().toString().equals(bean.getId().toString())){
				throw new Exception("省联社不允许删除！");
			}
			idList.append(bean.getId().toString()).append(",");
		}
		if(idList.length() > 0 && idList.charAt(idList.length() - 1) == ',') {
			idList.setLength(idList.length() - 1);
		}
		if(idList.length() > 0) {
			StringBuilder delHql = new StringBuilder("update BranchInfo po set status='0' where po.id in(");
			delHql.append(idList.toString()).append(")");
			dao.executeUpdate(delHql.toString());
			logger.info(delHql.toString());
		}
		//修改
		Iterator<BranchInfo> updit = updList.iterator();
		while (updit.hasNext()) {
			BranchInfo bean = updit.next();
			BranchInfo brhInfo = (BranchInfo) dao.get(BranchInfo.class, bean.getId());
			if(bean.getBrhClass().equals("1") && !bean.getId().toString().equals(t.getId().toString())){
				throw new Exception("已存在省联社！");
			}
			brhInfo.setBrhNo(bean.getBrhNo());
			brhInfo.setBrhName(bean.getBrhName());
			brhInfo.setBrhClass(bean.getBrhClass());
			brhInfo.setUpBrhId(bean.getUpBrhId());
			brhInfo.setProperty(bean.getProperty());
			brhInfo.setContacts(bean.getContacts());
			brhInfo.setTeleNo(bean.getTeleNo());
			brhInfo.setCityCd(bean.getCityCd());
			brhInfo.setAddress(bean.getAddress());
			brhInfo.setPostno(bean.getPostno());
			brhInfo.setStatus(bean.getStatus());
			dao.update(brhInfo);
		}
		//新增
		Iterator<BranchInfo> insit = insList.iterator();
		while (insit.hasNext()) {
			BranchInfo bean = insit.next();
			if(bean.getBrhClass().equals("1")){
				throw new Exception("不允许新增省联社！");
			}
			dao.insert(bean);
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
}
