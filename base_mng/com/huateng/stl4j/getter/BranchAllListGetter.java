package com.huateng.stl4j.getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import om.util.OmUtils;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.common.GlobalInfo;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.stl4j.bean.BranchBean;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.PageQueryCondition;
import com.huateng.stl4j.common.PageQueryResult;

/**
 * 筛选所有机构
 * @author om
 * @date 2011-1-7
 */
public class BranchAllListGetter extends BaseGetter {
	private static Logger logger = Logger.getLogger(BranchAllListGetter.class);
	public Result call() throws AppException {
		try {
			GlobalInfo g = GlobalInfo.getCurrentInstance();
			String value = OmUtils.trim(getCommQueryServletRequest().getParameter("value"));
			Integer pageSize = new Integer(getResult().getPage().getEveryPage());
			Integer pageIndex = new Integer(getResult().getPage().getCurrentPage());
			PageQueryCondition condition = new PageQueryCondition();
			condition.setPageIndex(pageIndex);
			condition.setPageSize(pageSize);
			StringBuilder hql = new StringBuilder();
			hql.append("select po.id, po.brhNo, po.brhName ");
			hql.append("from BranchInfo po ");
			hql.append("where 1=1 ");
			if(!OmUtils.isEmpty(value) && !"%%".equals(value)) {
				hql.append("and (po.brhNo like '").append(value).append("' ");
				hql.append("or po.brhName like '").append(value).append("') ");
			}
			hql.append("order by po.brhClass,po.id");
			condition.setQueryString(hql.toString());
			PageQueryResult queryResult = CommonDAO.getInstance().pageQueryByHQL(condition);
			
			List<BranchBean> list = new ArrayList<BranchBean>();
			Iterator it = queryResult.getQueryResult().iterator();
			while (it.hasNext()) {
	        	Object[] obj = (Object[]) it.next();
	        	BranchBean bean = new BranchBean();
	        	bean.setId((Integer) obj[0]);
	        	bean.setBrhNo(OmUtils.trim((String) obj[1]));
	        	bean.setBrhName(OmUtils.trim((String) obj[2]));
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
