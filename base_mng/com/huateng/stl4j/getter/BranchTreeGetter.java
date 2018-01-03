package com.huateng.stl4j.getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import om.util.OmUtils;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.common.GlobalInfo;
import com.huateng.ebank.entity.BranchInfo;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.stl4j.bean.BranchTreeBean;
import com.huateng.stl4j.common.CommonDAO;
import com.huateng.stl4j.service.BranchInfoService;

public class BranchTreeGetter extends BaseGetter {
	private static Logger logger = Logger.getLogger(BranchTreeGetter.class);
	public Result call() throws AppException {
		try {
			CommonDAO dao = CommonDAO.getInstance();
			Map<String, String> param = this.getCommQueryServletRequest().getParameterMap();
			String upBrhId = OmUtils.trim(param.get("id"));
			Integer brhId = GlobalInfo.getCurrentInstance().getBrhId();
			List<BranchInfo> resultList = new ArrayList<BranchInfo>();
			if(!OmUtils.isEmpty(upBrhId)) {
				brhId = new Integer(upBrhId);
				BranchInfo brhInfo = (BranchInfo) dao.get(BranchInfo.class, brhId);
				resultList = BranchInfoService.getInstance().getSubBranchList(brhInfo);
			}
			else {
				StringBuilder hql = new StringBuilder("select po, ");
				hql.append("(select count(sub.id) from BranchInfo sub ");
				hql.append("where sub.upBrhId=po.id) as subCount ");
				hql.append("from BranchInfo po where po.id=?");
				List tmpList = dao.findByHQL(hql.toString(), new Object[]{brhId});
				if(tmpList.size() > 0) {
					Object[] obj = (Object[]) tmpList.get(0);
					if(null != obj[0]) {
						BranchInfo brhInfo = (BranchInfo) obj[0];
						long subCount = ((Long) obj[1]).longValue();
						if(subCount > 0) brhInfo.setHasChild(true);
						resultList.add(brhInfo);
					}
				}
			}
			
			Iterator<BranchInfo> it = resultList.iterator();
			List<BranchTreeBean> list = new ArrayList<BranchTreeBean>();
			while (it.hasNext()) {
				BranchInfo tmp = it.next();
				if(null == tmp.getUpBrhId() || tmp.getUpBrhId().intValue() == tmp.getId().intValue()) {
					continue;
				}
				BranchTreeBean bean = new BranchTreeBean();
				bean.setId(tmp.getId());
				bean.setBrhNo(tmp.getBrhNo());
				bean.setBrhName(tmp.getBrhName());
				bean.setBrhClass(tmp.getBrhClass());
				bean.setUpBrhId(tmp.getUpBrhId());
				bean.setLevel(0);
				bean.setCanSelected(true);
				bean.setHasChild(tmp.getHasChild());
				list.add(bean);
			}
			ResultMng.fillResultByList(getCommonQueryBean(), getCommQueryServletRequest(),
					list, getResult());
			result.setContent(list);
			result.getPage().setTotalPage(1);
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
