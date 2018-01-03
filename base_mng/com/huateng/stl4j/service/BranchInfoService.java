package com.huateng.stl4j.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import om.util.OmUtils;
import org.apache.log4j.Logger;

import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.common.ErrorCode;
import com.huateng.ebank.common.ExceptionUtil;
import com.huateng.ebank.common.SystemConstant;
import com.huateng.ebank.entity.BranchInfo;
import com.huateng.stl4j.bean.BranchClass;
import com.huateng.stl4j.common.ApplicationContextUtils;
import com.huateng.stl4j.common.CommonDAO;

public class BranchInfoService {
	private static Logger logger = Logger.getLogger(BranchInfoService.class);
	private Map<String, String> brhClassMap = null;
	private String[] brhClassArray;
	private String brhClassDefine;
	private CommonDAO dao;
	
	private BranchInfoService() {
	}
	
	public static BranchInfoService getInstance() {
		return (BranchInfoService) ApplicationContextUtils.getBean(BranchInfoService.class.getName());
	}
	public String getBrhName(String brhNo, String status) {
		String brhName = "";
		if(OmUtils.isEmpty(brhNo)) {
			return brhName;
		}
		Object[] param = new Object[] {brhNo};
		StringBuilder hql = new StringBuilder("select po.brhName from BranchInfo po where po.brhNo=?");
		if(!OmUtils.isEmpty(status)) {
			hql.append(" and po.status=?");
			param = new Object[] {brhNo, status};
		}
		List list = CommonDAO.getInstance().findByHQL(hql.toString(), param);
		if(null != list && list.size() > 0) {
			brhName = (String) list.get(0);
		}
		return brhName;
	}
	
	public String getBrhName(String brhNo) {
		return getBrhName(brhNo, null);
	}
	
	public String getBrhName(Integer id, String status) {
		String brhName = "";
		if(null == id || 0 == id.intValue()) {
			return brhName;
		}
		Object[] param = new Object[] {id};
		StringBuilder hql = new StringBuilder("select po.brhName from BranchInfo po where po.id=?");
		if(!OmUtils.isEmpty(status)) {
			hql.append(" and po.status=?");
			param = new Object[] {id, status};
		}
		List list = CommonDAO.getInstance().findByHQL(hql.toString(), param);
		if(null != list && list.size() > 0) {
			brhName = (String) list.get(0);
		}
		return brhName;
	}
	
	public String getBrhName(Integer id) {
		return getBrhName(id, null);
	}
	
	public CommonDAO getDao() {
		return dao;
	}
	
	public void setDao(CommonDAO dao) {
		this.dao = dao;
	}

	public String getBrhClassDefine() {
		return brhClassDefine;
	}

	public void setBrhClassDefine(String brhClassDefine) {
		this.brhClassDefine = brhClassDefine;
		brhClassArray = OmUtils.split(brhClassDefine, ",").toArray(new String[1]);
	}
	
	public boolean hasUpBrhClass(String brhClass) {
		int upClassIndex = -1;
		for(int i = 0; i < brhClassArray.length; i ++) {
			if(brhClassArray[i].equals(brhClass)) {
				upClassIndex = i - 1;
				break;
			}
		}
		if(upClassIndex < 0) {
			return false;
		}
		return true;
	}
	
	public boolean hasSubBrhClass(String brhClass) {
		int subClassIndex = -1;
		for(int i = 0; i < brhClassArray.length; i ++) {
			if(brhClassArray[i].equals(brhClass)) {
				subClassIndex = i + 1;
				break;
			}
		}
		if(subClassIndex < 0 || subClassIndex >= brhClassArray.length) {
			return false;
		}
		return true;
	}
	
	public String getUpBrhClass(String brhClass) throws CommonException {
		int upClassIndex = -1;
		for(int i = 0; i < brhClassArray.length; i ++) {
			if(brhClassArray[i].equals(brhClass)) {
				upClassIndex = i - 1;
				break;
			}
		}
		if(upClassIndex < 0) {
			StringBuilder msg = new StringBuilder("机构级别[").append(brhClass).append("]");
			msg.append("无上级级别");
			ExceptionUtil.throwCommonException("", ErrorCode.ERROR_CODE_BRHCLASS_ERROR);
		}
		return brhClassArray[upClassIndex];
	}
	
	public String getSubBrhClass(String brhClass) throws CommonException {
		int subClassIndex = -1;
		for(int i = 0; i < brhClassArray.length; i ++) {
			if(brhClassArray[i].equals(brhClass)) {
				subClassIndex = i + 1;
				break;
			}
		}
		if(subClassIndex < 0 || subClassIndex >= brhClassArray.length) {
			StringBuilder msg = new StringBuilder("机构级别[").append(brhClass).append("]");
			msg.append("无下级级别");
			ExceptionUtil.throwCommonException("", ErrorCode.ERROR_CODE_BRHCLASS_ERROR);
		}
		return brhClassArray[subClassIndex];
	}
	
	public List<BranchClass> getBranchClassList() {
		List<BranchClass> result = new ArrayList<BranchClass>();
		for(int i = 0; i < brhClassArray.length; i ++) {
			BranchClass bean = new BranchClass();
			bean.setBrhClass(brhClassArray[i]);
			bean.setBrhClassName(brhClassMap.get(brhClassArray[i]));
			result.add(bean);
		}
		return result;
	}
	
	public List<BranchInfo> findByUpBrhId(Integer brhId, String status) {
		List<BranchInfo> result = new ArrayList<BranchInfo>();
		StringBuilder hql = new StringBuilder("select po, ");
		hql.append("(select count(sub.id) from BranchInfo sub ");
		if(!OmUtils.isEmpty(status)) {
			hql.append("where sub.upBrhId=po.id and sub.status=?) as subCount ");
		}
		else {
			hql.append("where sub.upBrhId=po.id) as subCount ");
		}
		hql.append("from BranchInfo po where po.upBrhId=? and po.id!=?");
		Object[] param = new Object[]{brhId, brhId};
		if(!OmUtils.isEmpty(status)) {
			hql.append(" and po.status=?");
			param = new Object[]{status, brhId, brhId, status};
		}
		List list = dao.findByHQL(hql.toString(), new Object[]{brhId, brhId});
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			if(null == obj[0]) continue;
			BranchInfo brhInfo = (BranchInfo)obj[0];
			Long subCount = (Long)obj[1];
			if(subCount.longValue() > 0) {
				brhInfo.setHasChild(true);
			}
			result.add(brhInfo);
		}
		return result;
	}
	
	public List<BranchInfo> findByUpBrhId(Integer brhId) {
		return findByUpBrhId(brhId, null);
	}
	
	public int countByBrhNo(String brhNo){
		StringBuilder hql = new StringBuilder("select po from BranchInfo po where po.brhNo = '");
		hql.append(brhNo).append("'");
		List list = dao.findByHQL(hql.toString());
		if(list!=null){
			return list.size();
		}
		return 0;
	}
	
	public void getSubBranchList(BranchInfo brhInfo, List<BranchInfo> list, String status) {
		List<BranchInfo> biList = findByUpBrhId(brhInfo.getId(), status);
		if(null != biList && biList.size() > 0) {
			int index = list.indexOf(brhInfo);
			brhInfo.setHasChild(true);
			if(index >= 0) {
				list.set(index, brhInfo);
			}
			for(int i = 0; i < biList.size(); i ++) {
				list.add(biList.get(i));
				getSubBranchList(biList.get(i), list, status);
			}
		}
	}
	
	public void getSubBranchList(BranchInfo brhInfo, List<BranchInfo> list) {
		getSubBranchList(brhInfo, list, null);
	}
	
	public List<BranchInfo> getSubBranchList(BranchInfo brhInfo, String status) {
		return findByUpBrhId(brhInfo.getId(), status);
	}
	
	public List<BranchInfo> getSubBranchList(BranchInfo brhInfo) {
		return findByUpBrhId(brhInfo.getId(), null);
	}
	
	public void getSubBranchIdList(Integer brhId, List<Integer> list, String status) {
		Object[] param = new Object[]{brhId, brhId};
		StringBuilder hql = new StringBuilder("select po.id from BranchInfo po where po.upBrhId=? and po.id!=?");
		if(!OmUtils.isEmpty(status)) {
			param = new Object[]{brhId, brhId, status};
			hql.append(" and po.status=?");
		}
		List idAndUpBrhIdList = dao.findByHQL(hql.toString(), param);
		if(null != idAndUpBrhIdList && idAndUpBrhIdList.size() > 0) {
			for(int i = 0; i < idAndUpBrhIdList.size(); i ++) {
				Integer thisId = (Integer) idAndUpBrhIdList.get(i);
				list.add(thisId);
				getSubBranchIdList(thisId, list, status);
			}
		}
	}
	
	public void getSubBranchIdList(Integer brhId, List<Integer> list) {
		getSubBranchIdList(brhId, list, null);
	}
	
	public List<String> getTreeList(Integer brhId, String status) {
		List<String> resList = new ArrayList<String>();
		BranchInfo topBrhInfo = null;
		StringBuilder hql = new StringBuilder("1=1");
		if(!OmUtils.isEmpty(status)) {
			hql.append(" and po.status='").append(status).append("'");
		}
		hql.append(" order by po.id desc");
		List<BranchInfo> branchList = dao.findByWhere(BranchInfo.class, hql.toString());
		for(int i = 0; i < branchList.size(); i ++) {
			/**
			if(branchList.get(i).getBrhClass().equals(getTopBrhClass())) {
				topBrhInfo = branchList.get(i);
				break;
			}*/
			if(branchList.get(i).getId().intValue() == brhId.intValue()) {
				topBrhInfo = branchList.get(i);
				break;
			}
		}
		List<BranchInfo> resultList = new ArrayList<BranchInfo>();
		resultList.add(topBrhInfo);
		getSubBranchList(topBrhInfo, resultList, status);
		StringBuilder line = new StringBuilder();
		for(int i = 0; i < resultList.size() ; i ++) {
			BranchInfo bi = (BranchInfo)(resultList.get(i));
			line.setLength(0);
			if(bi.getId().intValue() != topBrhInfo.getId().intValue()) {
				line.append(bi.getId()).append("|").append(bi.getUpBrhId());
				line.append("|").append(bi.getBrhName());
			}
			else {
				line.append(topBrhInfo.getId()).append("|0|").append(bi.getBrhName());
			}
			resList.add(line.toString());
		}
		
		return resList;
	}
	
	public List<String> getTreeList(Integer brhId) {
		return getTreeList(brhId, null);
	}
	
	public String getBrhClassName(String brhClass) {
		return brhClassMap.get(brhClass);
	}
	
	public String getTopBrhClass() {
		return brhClassArray[0];
	}
	
	public Integer getTopBrhId() {
		List list = dao.findByHQL("select po.id from BranchInfo po where po.brhClass=? and po.status=?",
			new Object[]{getTopBrhClass(), SystemConstant.FLAG_VALID});
		if(null != list && list.size() > 0) {
			return (Integer) list.get(0);
		}
		else return 0;
	}

	public Map<String, String> getBrhClassMap() {
		return brhClassMap;
	}

	public void setBrhClassMap(Map<String, String> brhClassMap) {
		this.brhClassMap = brhClassMap;
	}

	public String[] getBrhClassArray() {
		return brhClassArray;
	}
	
	public Integer getBrhId(String brhNo,String status){
		Integer brhId = 0;
		if(OmUtils.isEmpty(brhNo)) {
			return brhId;
		}
		Object[] param = new Object[] {brhNo};
		StringBuilder hql = new StringBuilder("select po.id from BranchInfo po where po.brhNo=?");
		if(!OmUtils.isEmpty(status)) {
			hql.append(" and po.status=?");
			param = new Object[] {brhNo, status};
		}
		List<?> list = CommonDAO.getInstance().findByHQL(hql.toString(), param);
		if(null != list && list.size() > 0) {
			brhId = (Integer) list.get(0);
		}
		return brhId;
	}
	
	public Integer getBrhId(String brhNo){
		return getBrhId(brhNo,null);
	}
}
