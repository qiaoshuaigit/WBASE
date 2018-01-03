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
import com.huateng.ebank.entity.BranchInfo;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.stl4j.bean.BranchInfoBean;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.PageQueryCondition;
import com.huateng.stl4j.common.PageQueryResult;
import com.huateng.stl4j.service.BranchInfoService;

public class BranchMngGetter extends BaseGetter {
	private static Logger logger = Logger.getLogger(BranchMngGetter.class);
	public Result call() throws AppException {
		try {
			GlobalInfo g = GlobalInfo.getCurrentInstance();
			Integer pageSize = new Integer(getResult().getPage().getEveryPage());
			Integer pageIndex = new Integer(getResult().getPage().getCurrentPage());
			String brhNo = OmUtils.trim(this.getCommQueryServletRequest().getParameter("brhNoWhere"));
			String brhName = OmUtils.trim(this.getCommQueryServletRequest().getParameter("brhName"));
			String brhClass = OmUtils.trim(this.getCommQueryServletRequest().getParameter("brhClassWhere"));
			String sUpBrhId = OmUtils.trim(this.getCommQueryServletRequest().getParameter("sUpBrhId"));
			StringBuilder hql = new StringBuilder("select po from BranchInfo po where 1=1 ");
			if(!OmUtils.isEmpty(brhNo)) {
				hql.append("and po.brhNo like '%").append(brhNo).append("%' ");
			}
			if(!OmUtils.isEmpty(brhName)) {
				hql.append("and po.brhName like '%").append(brhName).append("%' ");
			}
			if(!OmUtils.isEmpty(brhClass)) {
				hql.append("and po.brhClass='").append(brhClass).append("' ");
			}
			if(!OmUtils.isEmpty(sUpBrhId)) {
				/**
				String subBrhIdList = OmUtils.trim(getValueFromDataBus("SubBrhIdList"));
				if(!OmUtils.isEmpty(subBrhIdList)) {
					hql.append(" and po.id in(").append(subBrhIdList).append(")");
				}
				else {
					GlobalInfo gi = GlobalInfo.getCurrentInstanceWithoutException();
					if(gi.getTopBrhId().intValue() != Integer.parseInt(sUpBrhId)) {
						List<Integer> idList = new ArrayList<Integer>();
						BranchInfoService.getInstance().getSubBranchIdList(new Integer(sUpBrhId), idList);
						StringBuilder ids = new StringBuilder(sUpBrhId);
						for(int i = 0; i < idList.size(); i ++) {
							ids.append(",").append(idList.get(i).toString());
						}
						subBrhIdList = ids.toString();
						this.setValue2DataBus("SubBrhIdList", subBrhIdList);
						hql.append(" and po.id in(").append(subBrhIdList).append(")");
					}
				}*/
				Integer upBrhId = Integer.parseInt(sUpBrhId);
				if(g.getTopBrhId().intValue() != upBrhId.intValue()) {
					hql.append(" and (exists(select sub.id from SuperBranchList sub where");
					hql.append(" sub.id=po.id and (sub.upBrhId1=").append(upBrhId).append(" or");
					hql.append(" sub.upBrhId2=").append(upBrhId).append(" or");
					hql.append(" sub.upBrhId3=").append(upBrhId).append(" or");
					hql.append(" sub.upBrhId4=").append(upBrhId).append("))");
					hql.append(" or po.id=").append(upBrhId).append(")");
				}
				else {
					hql.append(" and po.upBrhId=").append(upBrhId);
				}
			}
			Integer upBrhId = g.getBrhId();
			//若没选择所属上级机构
			if(OmUtils.isEmpty(sUpBrhId) && g.getTopBrhId().intValue() != upBrhId.intValue()) {
				hql.append(" and (exists(select sub.id from SuperBranchList sub where");
				hql.append(" sub.id=po.id and (sub.upBrhId1=").append(upBrhId).append(" or");
				hql.append(" sub.upBrhId2=").append(upBrhId).append(" or");
				hql.append(" sub.upBrhId3=").append(upBrhId).append(" or");
				hql.append(" sub.upBrhId4=").append(upBrhId).append("))");
				hql.append(" or po.id=").append(upBrhId).append(")");
			}
			hql.append(" order by po.brhClass,po.id");
			
			PageQueryCondition condition = new PageQueryCondition();
			condition.setPageIndex(pageIndex);
			condition.setPageSize(pageSize);
			condition.setQueryString(hql.toString());
			PageQueryResult queryResult = CommonDAO.getInstance().pageQueryByHQL(condition);
			List<BranchInfoBean> list = new ArrayList<BranchInfoBean>();
			Iterator it = queryResult.getQueryResult().iterator();
			while (it.hasNext()) {
				Object[] obj = (Object[]) it.next();
				BranchInfo brhInfo = (BranchInfo) obj[0];
				BranchInfoBean bean = new BranchInfoBean();
				BeanUtils.copyProperties(brhInfo, bean);
				bean.setBrhClassName(BranchInfoService.getInstance().getBrhClassName(bean.getBrhClass()));
				bean.setUpBrhIdName(BranchInfoService.getInstance().getBrhName(bean.getUpBrhId()));
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

