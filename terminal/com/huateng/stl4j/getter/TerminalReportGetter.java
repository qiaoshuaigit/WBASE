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
import com.huateng.ebank.common.SystemConstant;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.stl4j.bean.TblAtmStatBean;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.common.PageQueryCondition;
import com.huateng.stl4j.common.PageQueryResult;
import com.huateng.stl4j.entity.TblAtmStat;
import com.huateng.stl4j.service.BranchInfoService;

public class TerminalReportGetter extends BaseGetter {
	private static Logger logger = Logger.getLogger(TerminalReportGetter.class);
	public Result call() throws AppException {
		try {
			GlobalInfo g = GlobalInfo.getCurrentInstance();
			Integer pageSize = new Integer(getResult().getPage().getEveryPage());
			Integer pageIndex = new Integer(getResult().getPage().getCurrentPage());
			String brhNo = OmUtils.trim(this.getCommQueryServletRequest().getParameter("brhNo"));
			String termStWhere = OmUtils.trim(this.getCommQueryServletRequest().getParameter("termStWhere"));
			StringBuilder hql = new StringBuilder("select po from TblAtmStat po where 1=1 ");
			if(!OmUtils.isEmpty(termStWhere)) {
				hql.append("and po.termSt like '").append(termStWhere).append("' ");
			}
			if(OmUtils.isEmpty(brhNo)){
				brhNo =  g.getBranchNo();
			}
			if(!brhNo.equals(SystemConstant.MAIN_BRH_NO)){
				Integer brhId = BranchInfoService.getInstance().getBrhId(brhNo);
				hql.append(" and (exists(select sub.id from SuperBranchList sub where");
				hql.append(" sub.brhNo=po.brno and (sub.upBrhId1=").append(brhId).append(" or");
				hql.append(" sub.upBrhId2=").append(brhId).append(" or");
				hql.append(" sub.upBrhId3=").append(brhId).append(" or");
				hql.append(" sub.upBrhId4=").append(brhId).append("))");
				hql.append(" or po.brno='").append(brhNo).append("')");
			}
			
			hql.append(" order by po.terminalCode");
			
			PageQueryCondition condition = new PageQueryCondition();
			condition.setPageIndex(pageIndex);
			condition.setPageSize(pageSize);
			condition.setQueryString(hql.toString());
			PageQueryResult queryResult = CommonDAO.getInstance().pageQueryByHQL(condition);
			List<TblAtmStatBean> list = new ArrayList<TblAtmStatBean>();
			Iterator<?> it = queryResult.getQueryResult().iterator();
			while (it.hasNext()) {
				Object[] obj = (Object[]) it.next();
				TblAtmStat dbBean = (TblAtmStat) obj[0];
				TblAtmStatBean bean = new TblAtmStatBean();
				BeanUtils.copyProperties(dbBean, bean);
				bean.setBrnoName(BranchInfoService.getInstance().getBrhName(bean.getBrno()));
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

