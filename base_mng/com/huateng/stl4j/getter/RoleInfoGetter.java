package com.huateng.stl4j.getter;

import java.util.List;

import org.apache.log4j.Logger;


import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.entity.RoleInfo;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.PageQueryCondition;
import com.huateng.stl4j.common.PageQueryResult;

public class RoleInfoGetter extends BaseGetter {
	private static Logger logger = Logger.getLogger(RoleInfoGetter.class);
	public Result call() throws AppException {
		try {
			Integer pageSize = new Integer(getResult().getPage().getEveryPage());
			Integer pageIndex = new Integer(getResult().getPage().getCurrentPage());
			PageQueryCondition condition = new PageQueryCondition();
			condition.setPageIndex(pageIndex);
			condition.setPageSize(pageSize);
			condition.setQueryString("select po from RoleInfo po where 1=1 order by po.id");
			PageQueryResult queryResult = CommonDAO.getInstance().pageQueryByHQL(condition);
			ResultMng.fillResultByList(getCommonQueryBean(), getCommQueryServletRequest(),
					queryResult.getQueryResult(), getResult());
			result.setContent(queryResult.getQueryResult());
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
