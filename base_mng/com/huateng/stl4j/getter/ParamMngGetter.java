package com.huateng.stl4j.getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import om.util.OmUtils;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.common.GlobalInfo;
import com.huateng.ebank.entity.SystemParam;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.PageQueryCondition;
import com.huateng.stl4j.common.PageQueryResult;
import com.huateng.stl4j.service.BranchInfoService;

public class ParamMngGetter extends BaseGetter {
	private static Logger logger = Logger.getLogger(ParamMngGetter.class);
	public Result call() throws AppException {
		try {
			GlobalInfo g = GlobalInfo.getCurrentInstance();
			Integer pageSize = new Integer(getResult().getPage().getEveryPage());
			Integer pageIndex = new Integer(getResult().getPage().getCurrentPage());
			String id = OmUtils.trim(this.getCommQueryServletRequest().getParameter("id"));
			String paramId = OmUtils.trim(this.getCommQueryServletRequest().getParameter("paramId"));
			String magicId = OmUtils.trim(this.getCommQueryServletRequest().getParameter("magicId"));
			String paramValue = OmUtils.trim(this.getCommQueryServletRequest().getParameter("paramValue"));
			StringBuilder hql = new StringBuilder("select po from SystemParam po where 1=1 ");
			if(!OmUtils.isEmpty(paramId)) {
				hql.append("and po.paramId = '").append(paramId).append("' ");
			}
			if(!OmUtils.isEmpty(paramValue)) {
				hql.append("and po.paramValue like '%").append(paramValue).append("%' ");
			}
			if(!OmUtils.isEmpty(magicId)) {
				hql.append("and po.magicId='").append(magicId).append("' ");
			}
			
			hql.append(" order by po.id");
			PageQueryCondition condition = new PageQueryCondition();
			condition.setPageIndex(pageIndex);
			condition.setPageSize(pageSize);
			condition.setQueryString(hql.toString());
			PageQueryResult queryResult = CommonDAO.getInstance().pageQueryByHQL(condition);
			List<SystemParam> list = new ArrayList<SystemParam>();
			Iterator it = queryResult.getQueryResult().iterator();
			while (it.hasNext()) {
				Object[] obj = (Object[]) it.next();
				SystemParam param = (SystemParam) obj[0];
				list.add(param);
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

