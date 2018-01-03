package com.huateng.ebank.business.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import om.util.OmUtils;

import org.apache.log4j.Logger;

import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.entity.DataDic;
import com.huateng.ebank.entity.DataDicMap;
import com.huateng.stl4j.common.ApplicationContextUtils;
import com.huateng.stl4j.common.CommonDAO;

public class DataDicService {
	private static Logger logger = Logger.getLogger(DataDicService.class);
	private static DataDicService single = null;
	private CommonDAO dao;

	protected DataDicService() {
	}

	public synchronized static DataDicService getInstance() {
		return (DataDicService) ApplicationContextUtils
				.getBean(DataDicService.class.getName());
	}

	/**
	 * 将系统内数据字典编码转换为外部系统使用的编码
	 * 
	 * @param mapType
	 * @param inDataNo
	 * @return
	 * @throws CommonException
	 */
	public String mapInToOut(int mapType, String inDataNo)
			throws CommonException {
		List list = dao.findByWhere(DataDicMap.class,
			"po.mapType=? and po.incode=?", new Object[]{mapType, inDataNo});

		if(list.size() > 0) {
			DataDicMap po = (DataDicMap) list.get(0);
			return po.getOutcode().substring(0, po.getOutcodeLen().intValue());
		} else {
			return "";
		}
	}
	
	
	public String mapInToOut2(int mapType, String inDataNo)
			throws CommonException {
		List list = dao.findByWhere(DataDicMap.class,
			"po.mapType=? and po.incode=?", new Object[]{mapType, inDataNo});

		if(list.size() > 0) {
			DataDicMap po = (DataDicMap) list.get(0);
			return po.getOutcode();
		} else {
			return "";
		}
	}
	
	public DataDicMap mapInToOut3(int mapType, String inDataNo)
			throws CommonException {
		List list = dao.findByWhere(DataDicMap.class,
			"po.mapType=? and po.incode=?", new Object[]{mapType, inDataNo});

		if(list.size() > 0) {
			DataDicMap po = (DataDicMap) list.get(0);
			return po;
		} else {
			return null;
		}
	}	
	
	
	public Map<String,String> mapInToOut(int mapType)
			throws CommonException {
		Map<String,String> outMap = new HashMap<String,String>();
		List list = dao.findByWhere(DataDicMap.class,"po.mapType=? ", new Object[]{mapType});
		int size  = list.size();
		if(size>0){
			for(int i=0;i<size;i++){
				DataDicMap ddm = (DataDicMap)list.get(i);
				outMap.put(ddm.getIncode(), ddm.getOutcode());
			}
		}
		return outMap;
	}
	
	
	/**
	 * 将外部系统使用的编码转换为内部系统使用的编码
	 * 
	 * @param mapType
	 * @param inDataNo
	 * @return
	 * @throws CommonException
	 */
	public String mapOutToIn(int mapType, String outDataNo)
			throws CommonException {
		List list = dao.findByWhere(DataDicMap.class,
			"po.mapType=? and po.outcode=?", new Object[]{mapType, outDataNo});

		if(list.size() > 0) {
			DataDicMap po = (DataDicMap) list.get(0);
			return po.getIncode().substring(0, po.getIncodeLen().intValue());
		} else {
			return "";
		}
	}
	
	/**
	 * 根据数据编号取得对应的值
	 * 
	 * @param type
	 * @param dataNo
	 * @return value
	 * @throws CommonException
	 */
	public String getValueByDataNo(int type, String dataNo)
			throws CommonException {
		List list = dao.findByWhere(DataDic.class,
			"po.dataTypeNo=? and po.dataNo=?", new Object[]{type, dataNo});
		if (list.size() > 0) {
			DataDic po = (DataDic) list.get(0);
			return po.getHighLimit().trim();
		} else {
			return null;
		}
	}

	/**
	 * 根据数据范围值取得对应的数据编号
	 * 
	 * @param type
	 * @param value
	 * @return dataNo
	 * @throws CommonException
	 */
	public String getDataNoByRange(int type, double value)
			throws CommonException {
		StringBuffer whereStr = new StringBuffer(64);
		whereStr.append("po.dataTypeNo = ").append(type).append(
				" and cast(po.highLimit long) >= ").append(value).append(
				" and cast(po.lowLimit long) < ").append(value).append(
				" and po.limitFlag = '1' order by po.dataNo");
		List list = dao.findByWhere(DataDic.class, whereStr.toString());
		if(list.size() > 0) {
			DataDic po = (DataDic) list.get(0);
			return po.getDataNo().trim();
		} else {
			return null;
		}
	}

	/**
	 * 根据数据编号取得对应的显示名称，如果没有找到，返回""。
	 * 
	 * @param type 类型
	 * @param no 编号
	 * @return 显示名称
	 * @throws CommonException
	 */
	public String getNameByTypeNo(int type, String no) throws CommonException {
		if(OmUtils.isEmpty(no))
			return "";

		List list = dao.findByWhere(DataDic.class,
			"po.dataTypeNo=? and po.dataNo=?", new Object[]{type, no});
		if(list.size() == 0)
			return "";
		DataDic dataDic = (DataDic) list.get(0);
		return dataDic.getDataName().trim();
	}

	/**
	 * 根据数据编号取得对应的显示名称，如果没有找到，返回""。
	 * 
	 * @param type 类型
	 * @param no 编号
	 * @return 显示名称(截去开头的datano和符号-)
	 * @throws CommonException
	 */
	public String getChnNameByTypeNo(int type, String no)
			throws CommonException {
		if(OmUtils.isEmpty(no))
			return "";

		List list = dao.findByWhere(DataDic.class,
			"po.dataTypeNo=? and po.dataNo=?", new Object[]{type, no});
		if(list.size() == 0) {
			return "";
		}
		DataDic dataDic = (DataDic) list.get(0);
		return dataDic.getDataName().substring(
				dataDic.getDataNoLen().intValue() + 1).trim();
	}

	/**
	 * 导出DataDic数据表记录
	 * 
	 * @return List<DataDic>
	 * @throws CommonException
	 * @author shen_antonio
	 */
	public List loadAll() throws CommonException {
		return dao.findByWhere(DataDic.class, "1=1");
	}

	public List loadAllSort() throws CommonException {
		return dao.findByWhere(DataDic.class, "1=1 order by po.dataTypeNo, po.dataNo");
	}

	public CommonDAO getDao() {
		return dao;
	}

	public void setDao(CommonDAO dao) {
		this.dao = dao;
	}
}
