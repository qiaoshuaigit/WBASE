package com.huateng.stl4j.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.vidageek.mirror.Mirror;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.huateng.ebank.entity.BranchInfo;
import com.huateng.ebank.entity.SuperBranchList;
import com.huateng.stl4j.common.ApplicationContextUtils;
import com.huateng.stl4j.common.CommonDAO;

public class SuperBranchListService {
	private static Logger logger = Logger.getLogger(SuperBranchListService.class);
	private static String lastBrhClass = null;
	private CommonDAO dao;
	
	protected SuperBranchListService() {
	}
	
	public static SuperBranchListService getInstance() {
		return (SuperBranchListService) ApplicationContextUtils.getBean(SuperBranchListService.class.getName());
	}
	
	public void initSuperBranchList() {
		logger.info("Start initSuperBranchList");
		if(null == lastBrhClass) {
			String[] brhClassArray = BranchInfoService.getInstance().getBrhClassArray();
			lastBrhClass = brhClassArray[brhClassArray.length - 1];
		}
		Map<Integer,SuperBranchList> supBrhMap = new LinkedHashMap<Integer,SuperBranchList>();
		Integer topBrhId = BranchInfoService.getInstance().getTopBrhId();
		buildSuperBranchList(topBrhId, supBrhMap);
		saveSuperBranchList(supBrhMap);
		supBrhMap.clear();
		supBrhMap = null;
		logger.info("_End_ initSuperBranchList");
	}
	
	private void saveSuperBranchList(Map<Integer,SuperBranchList> supBrhMap) {
//		dao.executeUpdate("delete from SuperBranchList po where 1=1");
//		Iterator<Integer> ids = supBrhMap.keySet().iterator();
//		while(ids.hasNext()) {
//			SuperBranchList bean = supBrhMap.get(ids.next());
//			dao.insert(bean);
//		}
		Map<Integer, SuperBranchList> map = new HashMap<Integer, SuperBranchList>();
		dao.executeUpdate("delete from SuperBranchList po where not exists(select bi.id from BranchInfo bi where bi.id=po.id)");
		List<SuperBranchList> all = dao.findByWhere(SuperBranchList.class, "1=1");
		Iterator<SuperBranchList> it = all.iterator();
		while(it.hasNext()) {
			SuperBranchList bean = it.next();
			map.put(bean.getId(), bean);
		}
		Iterator<Integer> ids = supBrhMap.keySet().iterator();
		while(ids.hasNext()) {
			SuperBranchList bean = supBrhMap.get(ids.next());
			SuperBranchList po = map.get(bean.getId());
			if(null == po) {
				dao.insert(bean);
			}
			else if(!bean.equals(po)) {
				BeanUtils.copyProperties(bean, po, new String[]{"id"});
				dao.update(po);
			}
		}
		map.clear();
		map = null;
	}
	
	private void buildSuperBranchList(Integer upBrhId, Map<Integer,SuperBranchList> supBrhMap) {
		StringBuilder hql = new StringBuilder("po.upBrhId=? and po.upBrhId!=0 ");
		hql.append("and po.upBrhId!=po.id order by po.brhClass,po.id");
		List<BranchInfo> list = dao.findByWhere(BranchInfo.class,
			hql.toString(), new Object[]{upBrhId});
		for(int i = 0; i < list.size(); i ++) {
			BranchInfo brhInfo = list.get(i);
			SuperBranchList uSupBrhList = supBrhMap.get(brhInfo.getUpBrhId());
			SuperBranchList supBrhList = new SuperBranchList();
			supBrhList.setId(brhInfo.getId());
			supBrhList.setBrhNo(brhInfo.getBrhNo());
			supBrhList.setBrhClass(brhInfo.getBrhClass());
			int upIndex = 1, maxUpIndex = 9;
			if(null != uSupBrhList) {
				for(;upIndex <= maxUpIndex; upIndex ++) {
					String upBrhIdFieldName = "upBrhId" + upIndex;
					Integer fieldValue = (Integer) Mirror.on(uSupBrhList).get().field(upBrhIdFieldName);
					if(null == fieldValue || 0 == fieldValue.intValue()) {
						break;
					}
					Mirror.on(supBrhList).set().field(upBrhIdFieldName).withValue(fieldValue);
				}
			}
			Mirror.on(supBrhList).set().field("upBrhId" + upIndex).withValue(brhInfo.getUpBrhId());
			supBrhMap.put(supBrhList.getId(), supBrhList);
			if(!brhInfo.getBrhClass().equals(lastBrhClass)) {
				buildSuperBranchList(brhInfo.getId(), supBrhMap);				
			}
		}
	}
	
	public CommonDAO getDao() {
		return dao;
	}
	
	public void setDao(CommonDAO dao) {
		this.dao = dao;
	}
}
