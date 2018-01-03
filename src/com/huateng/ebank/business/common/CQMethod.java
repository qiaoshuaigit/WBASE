package com.huateng.ebank.business.common;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringUtils;

import com.huateng.commquery.config.bean.base.ICommonQueryBaseBean;
import com.huateng.exception.HuatengException;

public class CQMethod {
	/**
	 * get data_dic_name by translated
	 *
	 * @param element
	 * @param value
	 * @param request
	 * @return
	 * @throws HuatengException
	 */
	public static String getDataDicName(ICommonQueryBaseBean element,
			String value, ServletRequest request) throws HuatengException {
		String translated = element.getAnyValue("translated");
		if (StringUtils.isEmpty(value) || StringUtils.isEmpty(translated)
				|| !translated.startsWith("DATA_DIC.")) {
			return value;
		}
		String dicType = translated.substring(translated.indexOf(".") + 1);
		String dicName = DataDicUtils.getDicName(dicType, value);
		return dicName;
	}
}
