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
import com.huateng.ebank.entity.BranchInfo;
import com.huateng.ebank.entity.Operator;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.PageQueryCondition;
import com.huateng.stl4j.common.PageQueryResult;

public class UserListGetter extends BaseGetter {
	private static Logger logger = Logger.getLogger(UserListGetter.class);
	public Result call() throws AppException {
		try {
			GlobalInfo g = GlobalInfo.getCurrentInstance();
			Integer pageSize = new Integer(getResult().getPage().getEveryPage());
			Integer pageIndex = new Integer(getResult().getPage().getCurrentPage());
			String value = OmUtils.trim(getCommQueryServletRequest().getParameter("value"));
			StringBuilder hql = new StringBuilder();
			hql.append("select po from Operator po where status=1");
			if(!OmUtils.isEmpty(value) && !value.equals("%%")) {
				hql.append(" and (po.oprNo like '").append(value).append("'");
				hql.append(" or   po.userName like '").append(value).append("')");
			}
			Integer upBrhId = g.getBrhId();
			hql.append(" and (exists(select sub.id from SuperBranchList sub where");
			hql.append(" sub.brhNo=po.brhNo and (sub.upBrhId1=").append(upBrhId).append(" or");
			hql.append(" sub.upBrhId2=").append(upBrhId).append(" or");
			hql.append(" sub.upBrhId3=").append(upBrhId).append(" or");
			hql.append(" sub.upBrhId4=").append(upBrhId).append("))");
			hql.append(" or po.brhNo='").append(g.getBranchNo()).append("')");
			hql.append(" order by po.brhNo,po.oprNo");
			PageQueryCondition condition = new PageQueryCondition();
			condition.setPageIndex(pageIndex);
			condition.setPageSize(pageSize);
			condition.setQueryString(hql.toString());
			PageQueryResult queryResult = CommonDAO.getInstance().pageQueryByHQL(condition);
			List<Operator> list = new ArrayList<Operator>();
	        Iterator it = queryResult.getQueryResult().iterator();
	        while (it.hasNext()) {
	        	Object[] obj = (Object[]) it.next();
	        	Operator bean = (Operator) obj[0];
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
