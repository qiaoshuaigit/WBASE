package com.huateng.stl4j.getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.entity.SystemParam;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.stl4j.bean.ParamReloadBean;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.PageQueryCondition;
import com.huateng.stl4j.common.PageQueryResult;

public class ParamReloadGetter extends BaseGetter {
	private static Logger logger = Logger.getLogger(ParamReloadGetter.class);
	public Result call() throws AppException {
		try {
			Integer pageSize = new Integer(getResult().getPage().getEveryPage());
			Integer pageIndex = new Integer(getResult().getPage().getCurrentPage());
			PageQueryCondition condition = new PageQueryCondition();
			condition.setPageIndex(pageIndex);
			condition.setPageSize(pageSize);
			condition.setQueryString("select po from SystemParam po where po.paramId='RELOAD' order by po.id");
			PageQueryResult queryResult = CommonDAO.getInstance().pageQueryByHQL(condition);
			List<ParamReloadBean> list = new ArrayList<ParamReloadBean>();
			Iterator it = queryResult.getQueryResult().iterator();
			while (it.hasNext()) {
				Object[] obj = (Object[]) it.next();
				SystemParam sp = (SystemParam) obj[0];
				ParamReloadBean bean = new ParamReloadBean();
				BeanUtils.copyProperties(sp, bean);
				list.add(bean);
			}
			ResultMng.fillResultByList(getCommonQueryBean(), getCommQueryServletRequest(),
					list, getResult());
			result.setContent(list);
			result.getPage().setTotalPage(queryResult.getPageCount(getResult().getPage().getEveryPage()));
			result.init();
			return result;
		} catch (CommonException e) {
			logger.error(e.getClass().getSimpleName(), e);
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, e.getMessage());
		} catch (AppException appEx) {
			logger.error(appEx.getClass().getSimpleName(), appEx);
			throw appEx;
		} catch(Exception ex) {
			logger.error(ex.getClass().getSimpleName(), ex);
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}
	}
}
