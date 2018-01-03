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
import com.huateng.ebank.entity.BranchInfo;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.stl4j.bean.BranchBean;
import com.huateng.stl4j.bean.BranchInfoBean;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.PageQueryCondition;
import com.huateng.stl4j.common.PageQueryResult;
import com.huateng.stl4j.service.BranchInfoService;

public class OprOnlineGetter extends BaseGetter {
	private static Logger logger = Logger.getLogger(OprOnlineGetter.class);
	public Result call() throws AppException {
		try {
			Integer pageSize = new Integer(getResult().getPage().getEveryPage());
			Integer pageIndex = new Integer(getResult().getPage().getCurrentPage());
			String brhNo = OmUtils.trim(this.getCommQueryServletRequest().getParameter("brhNoWhere"));
			String brhName = OmUtils.trim(this.getCommQueryServletRequest().getParameter("brhName"));
			String rsType = OmUtils.trim(this.getCommQueryServletRequest().getParameter("rsType"));
			StringBuilder hql = new StringBuilder("select po.id,po.brhNo,po.brhName,(select count(opr.id) " +
					"from Operator opr where po.brhNo=opr.brhNo and opr.statusIn='1') " +
					"from BranchInfo po where 1=1 ");
			if(!OmUtils.isEmpty(brhNo)) {
				hql.append("and po.brhNo like '%").append(brhNo).append("%' ");
			}
			if(!OmUtils.isEmpty(brhName)) {
				hql.append("and po.brhName like '%").append(brhName).append("%' ");
			}
			//ALL,所有机构;SI1,有登录操作员;SI0,无登录操作员
			if("SI1".equals(rsType)) {
				hql.append("and exists(select 1 from Operator op where po.brhNo=op.brhNo and op.statusIn='1') ");
			}
			else if("SI0".equals(rsType)) {
				hql.append("and not exists(select 1 from Operator op where po.brhNo=op.brhNo and op.statusIn='1') ");
			}
			hql.append(" order by po.id");
			
			PageQueryCondition condition = new PageQueryCondition();
			condition.setPageIndex(pageIndex);
			condition.setPageSize(pageSize);
			condition.setQueryString(hql.toString());
			PageQueryResult queryResult = CommonDAO.getInstance().pageQueryByHQL(condition);
			List<BranchBean> list = new ArrayList<BranchBean>();
			Iterator it = queryResult.getQueryResult().iterator();
			while (it.hasNext()) {
				Object[] obj = (Object[]) it.next();
				BranchBean bean = new BranchBean();
				Integer id = (Integer) obj[0];
				String brhNo1 = OmUtils.trim((String)obj[1]);
				String brhName1 = OmUtils.trim((String)obj[2]);
				Long oprOnlineCount = (Long)obj[3];
				bean.setId(id);
				bean.setBrhNo(brhNo1);
				bean.setBrhName(brhName1);
				bean.setOprOnlineCount(oprOnlineCount);
				
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

