package com.huateng.stl4j.updater;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.MultiUpdateResultBean;
import com.huateng.commquery.result.UpdateResultBean;
import com.huateng.commquery.result.UpdateReturnBean;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.framework.web.commQuery.BaseUpdate;
import com.huateng.exception.AppException;

public class DynamicColumnUpdater extends BaseUpdate {
	private static Logger logger = Logger.getLogger(DynamicColumnUpdater.class);
	public UpdateReturnBean saveOrUpdate(
			MultiUpdateResultBean multiUpdateResultBean,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		try {
			UpdateResultBean query = multiUpdateResultBean.getUpdateResultBeanByID("DynamicColumnQuery");
			UpdateReturnBean updateReturnBean = new UpdateReturnBean();
			if(query.hasNext()) {
				Map<String, String> map = query.next();
				String custType = map.get("custType");
				String custNo   = map.get("custNo");
				this.setValue2DataBus("custType", custType);
				this.setValue2DataBus("custNo", custNo);
				//其它条件...
				//下一展示页面
				if(custType.equals("1")) {
					updateReturnBean.setParameter("nextUrl", "/fpages/demo/ftl/DynamicColumnResult1.ftl");
				}
				else if(custType.equals("2")) {
					updateReturnBean.setParameter("nextUrl", "/fpages/demo/ftl/DynamicColumnResult2.ftl");
				}
			}
			
			return updateReturnBean;
		} catch (CommonException e) {
			logger.error(e.getClass().getSimpleName(), e);
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, e.getMessage());
		} catch (AppException appEx) {
			logger.error(appEx.getClass().getSimpleName(), appEx);
			throw appEx;
		} catch (Exception ex) {
			logger.error(ex.getClass().getSimpleName(), ex);
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}
	}
}
