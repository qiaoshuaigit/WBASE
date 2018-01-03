package com.huateng.ebank.framework.web.commQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huateng.commquery.servlet.CommQueryServlet;
import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.common.ErrorCode;
import com.huateng.ebank.common.ExceptionUtil;
import com.huateng.ebank.common.GlobalInfo;
import com.huateng.exception.DomainException;

public class BankCommQueryServlet extends CommQueryServlet {
	private static final long serialVersionUID = -7223047626902349647L;
	/**
	 * pre process
	 * @param request
	 * @param response
	 * @throws DomainException
	 */
	@Override
	protected Object preProcess(HttpServletRequest request,HttpServletResponse response)throws DomainException{
		init(request);
		super.preProcess(request, response);
		return null;
	}
	/**
	 * post process
	 * @param request
	 * @param response
	 * @throws DomainException
	 */
	@Override
	protected void postProcess(HttpServletRequest request,HttpServletResponse response,Exception ex,Object obj)throws DomainException{
	}

	/**
	 * Initialize function for action
	 *
	 * @param request
	 *            HttpServletRequest
	 * @throws CommonException
	 *             If error happened.
	 */
	protected void init(HttpServletRequest request) throws CommonException {
		this.checkGlobalInfo(request);

	}

	protected GlobalInfo checkGlobalInfo(HttpServletRequest request)
			throws CommonException {
		GlobalInfo globalInfo = GlobalInfo.getFromRequest(request);
		if (null == globalInfo) {
			ExceptionUtil.throwCommonException("用户没有登录.",
					ErrorCode.ERROR_CODE_TLRNO_SESSION_INVALID);
		}
		GlobalInfo.setCurrentInstance(globalInfo);
		return globalInfo;
	}

	protected GlobalInfo initGlobalInfo(HttpServletRequest request)
			throws CommonException {
		return checkGlobalInfo(request);
	}
}
