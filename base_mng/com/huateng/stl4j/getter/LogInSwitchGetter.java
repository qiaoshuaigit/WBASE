package com.huateng.stl4j.getter;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.entity.SystemParam;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.stl4j.common.CommonDAO;

public class LogInSwitchGetter extends BaseGetter {
	private static Logger logger = Logger.getLogger(LogInSwitchGetter.class);

	public Result call() throws AppException {
		try {

			SystemParam bean = new SystemParam();
			String hql = "select po from SystemParam po where po.paramId='SYSINFO' and magicId='LOGINSWITCH' order by po.id";
			List<?> list = CommonDAO.getInstance().findByHQL(hql);

			if (list != null && list.size() > 0) {
				bean = (SystemParam) list.get(0);
			}

			ResultMng.fillResultByObject(getCommonQueryBean(),
					getCommQueryServletRequest(), bean, getResult());
			List<SystemParam> content = new ArrayList<SystemParam>();
			content.add(bean);
			result.setContent(content);
			result.init();
			return result;
		} catch (Exception ex) {
			logger.error(ex.getClass().getSimpleName(), ex);
			throw new AppException(Module.SYSTEM_MODULE,
					Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}
	}
}
