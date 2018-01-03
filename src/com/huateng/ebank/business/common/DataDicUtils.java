package com.huateng.ebank.business.common;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import om.util.OmUtils;

import com.huateng.ebank.business.common.service.DataDicService;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.entity.DataDic;
import com.huateng.util.SystemDictionaryUnit;

public class DataDicUtils {
	public final static String TBL_NM = "DATA_DIC";

	public static void initDataDic() throws CommonException {
		try {
			DataDicService dataDicService = DataDicService.getInstance();
			List dataDicList = dataDicService.loadAllSort();
			Iterator it = dataDicList.iterator();
			DataDic dataDic;
			while (it.hasNext()) {
				dataDic = (DataDic) it.next();
				SystemDictionaryUnit.addRecord(TBL_NM, String.valueOf(dataDic
						.getDataTypeNo()), dataDic.getDataNo().trim(), dataDic
						.getDataName());

			}
		} catch (CommonException commEx) {
			throw commEx;
		} catch (Exception ex) {
			throw new CommonException(ex.getMessage(), ex);
		}
	}

	/**
	 * 获取字典值
	 * 
	 * @param dicType
	 * @param dicNo
	 * @return
	 */
	public static String getDicName(String dicType, String dicNo) {
		return SystemDictionaryUnit.getFieldDesc(TBL_NM, dicType, dicNo);
	}

	/**
	 *  获取字典值，如果没有找到，返回  dicNo。
	 *  @param dicType
	 *  @param dicNo
	 *  @return 显示名称(截去开头的datano和符号-)
	 * @throws CommonException 
	 */
	public static String getChnDicName(String dicType, String dicNo) throws CommonException{
		String value = SystemDictionaryUnit.getFieldDesc(TBL_NM, dicType, dicNo);
		if(value.length()<=(dicNo.length()+1)){
			return value;
		}else{
			return value.substring(dicNo.length()+1);
		}
	}
	
	/**
	 * 根据数据类型，获取全部字典值
	 * 
	 * @param dicType
	 * @return
	 */
	public static LinkedHashMap getAllDicType(String dicType) {
		return SystemDictionaryUnit.getAllFieldDesc(TBL_NM, dicType);
	}
}
