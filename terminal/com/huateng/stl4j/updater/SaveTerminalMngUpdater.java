package com.huateng.stl4j.updater;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.MultiUpdateResultBean;
import com.huateng.commquery.result.UpdateResultBean;
import com.huateng.commquery.result.UpdateReturnBean;
import com.huateng.ebank.business.common.service.CommonService;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.framework.web.commQuery.BaseUpdate;
import com.huateng.exception.AppException;
import com.huateng.stl4j.common.OPCaller;
import com.huateng.stl4j.common.OperationContext;
import com.huateng.stl4j.entity.TblAtmStat;
import com.huateng.stl4j.operation.TerminalMngOperation;

public class SaveTerminalMngUpdater extends BaseUpdate {
	private static Logger logger = Logger.getLogger(SaveTerminalMngUpdater.class);
	public UpdateReturnBean saveOrUpdate(
			MultiUpdateResultBean multiUpdateResultBean,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		try {
			UpdateReturnBean updateReturnBean = new UpdateReturnBean();
			UpdateResultBean updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID("TerminalMng");
			List<TblAtmStat> delList = new ArrayList<TblAtmStat>();
			List<TblAtmStat> updList = new ArrayList<TblAtmStat>();
			List<TblAtmStat> insList = new ArrayList<TblAtmStat>();
			int lineCount = 0;
			while(updateResultBean.hasNext()) {
				TblAtmStat bean = new TblAtmStat();
				mapToObject(bean, updateResultBean.next());
				switch (updateResultBean.getRecodeState()) {
					case UpdateResultBean.DELETE:
						lineCount ++;
						delList.add(bean);
						break;
					case UpdateResultBean.MODIFY:
						lineCount ++;
						updList.add(bean);
						break;
					case UpdateResultBean.INSERT:
						lineCount ++;
						insList.add(bean);
						break;
					default:
						break;
				}
			}
			
			if(lineCount > 0) {
				OperationContext oc = new OperationContext();
				oc.set(TerminalMngOperation.DEL_LIST, delList);
				oc.set(TerminalMngOperation.INS_LIST, insList);
				oc.set(TerminalMngOperation.UPD_LIST, updList);
				oc.set(TerminalMngOperation.OP_TYPE, "0");
				OPCaller.call(TerminalMngOperation.ID, oc);
				
				updateReturnBean.setParameter("resultMsg", "保存成功：共更新 " + lineCount + " 条数据！");
			}
			return updateReturnBean;
		} catch (CommonException e) {
			logger.error(e.getClass().getSimpleName(), e);
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, e.getMessage());
		} catch (AppException appEx) {
			logger.error(appEx.getClass().getSimpleName(), appEx);
			throw appEx;
		}catch (Exception ex) {
			logger.error(ex.getClass().getSimpleName(), ex);
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}
	}
}
