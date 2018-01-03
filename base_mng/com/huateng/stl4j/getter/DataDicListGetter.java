package com.huateng.stl4j.getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import om.util.OmUtils;

import org.apache.log4j.Logger;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.entity.DataDic;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.PageQueryCondition;
import com.huateng.stl4j.common.PageQueryResult;

public class DataDicListGetter extends BaseGetter {
	private static Logger logger = Logger.getLogger(DataDicListGetter.class);
	
	public Result call() throws AppException {
		try {
			String value = OmUtils.trim(getCommQueryServletRequest().getParameter("value"));
			String dataTypeNo = OmUtils.trim(getCommQueryServletRequest().getParameter("dataTypeNo"));
			String dataNo = OmUtils.trim(getCommQueryServletRequest().getParameter("dataNo"));
			
			Integer pageSize = new Integer(getResult().getPage().getEveryPage());
			Integer pageIndex = new Integer(getResult().getPage().getCurrentPage());
			PageQueryCondition condition = new PageQueryCondition();
			condition.setPageIndex(pageIndex);
			condition.setPageSize(pageSize);
			StringBuilder hql = new StringBuilder();
			hql.append("select po.id, po.dataNo, po.dataName ");
			hql.append("from DataDic po ");
			hql.append("where po.dataTypeNo='").append(dataTypeNo).append("' ");
			
			if(!OmUtils.isEmpty(dataNo)){
				hql.append(" and po.dataNo like '").append(dataNo).append("%' ");
			}
			
			if(!OmUtils.isEmpty(value) && !"%%".equals(value)) {
				hql.append("and (po.dataNo like '").append(value).append("' ");
				hql.append("or po.dataName like '").append(value).append("') ");
			}
			hql.append("order by po.dataNo");
			condition.setQueryString(hql.toString());
			PageQueryResult queryResult = CommonDAO.getInstance().pageQueryByHQL(condition);
			
			List<DataDic> list = new ArrayList<DataDic>();
			Iterator it = queryResult.getQueryResult().iterator();
			while (it.hasNext()) {
	        	Object[] obj = (Object[]) it.next();
	        	DataDic bean = new DataDic();
	        	bean.setId((Integer) obj[0]);
	        	bean.setDataNo(OmUtils.trim((String) obj[1]));
	        	bean.setDataName(OmUtils.trim((String) obj[2]));
	        	list.add(bean);
			}
			
			PageQueryResult pageQueryResult = new PageQueryResult();
			pageQueryResult.setQueryResult(list);
			pageQueryResult.setTotalCount(queryResult.getTotalCount());
			ResultMng.fillResultByList(getCommonQueryBean(), getCommQueryServletRequest(),
					pageQueryResult.getQueryResult(), getResult());
			result.setContent(pageQueryResult.getQueryResult());
			result.getPage().setTotalPage(pageQueryResult.getPageCount(getResult().getPage().getEveryPage()));
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
