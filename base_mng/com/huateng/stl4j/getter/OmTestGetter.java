package com.huateng.stl4j.getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;

public class OmTestGetter extends BaseGetter {
	private static Logger logger = Logger.getLogger(OmTestGetter.class);
	public Result call() throws AppException {
		try {
			//Map map = new HashMap();
			//map.put("result", "OmTest测试");
			List list = new ArrayList();
			TestCustBean bean = new TestCustBean();
			bean.setCustNo("custNo");
			bean.setCustName("custName");
			bean.setCustAddress("custAddress");
			bean.setTelephone("telephone");
			bean.setCustType("02113");
			bean.setYm("201210");
			bean.setTime("083001");
			list.add(bean);
			ResultMng.fillResultByList(getCommonQueryBean(), getCommQueryServletRequest(), list, getResult());
			result.setContent(list);
			result.getPage().setTotalPage(1);
			result.init();
			return result;
		} catch (AppException appEx) {
			logger.error(appEx.getClass().getSimpleName(), appEx);
			throw appEx;
		} catch(Exception ex) {
			logger.error(ex.getClass().getSimpleName(), ex);
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}
	}
	
	public static class TestCustBean {
		private String custNo;
		private String custName;
		private String custAddress;
		private String telephone;
		private String custType;
		private String ym;
		private String time;
		
		public String getCustNo() {
			return custNo;
		}
		public void setCustNo(String custNo) {
			this.custNo = custNo;
		}
		public String getCustName() {
			return custName;
		}
		public void setCustName(String custName) {
			this.custName = custName;
		}
		public String getCustAddress() {
			return custAddress;
		}
		public void setCustAddress(String custAddress) {
			this.custAddress = custAddress;
		}
		public String getTelephone() {
			return telephone;
		}
		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}
		public String getCustType() {
			return custType;
		}
		public void setCustType(String custType) {
			this.custType = custType;
		}
		public String getYm() {
			return ym;
		}
		public void setYm(String ym) {
			this.ym = ym;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
	}
}
