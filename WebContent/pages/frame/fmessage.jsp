<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/footbar.css">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>MESSAGE</title>
</head>

<body>
<div class="foot-sp-nav">
	<div class="foot-sp-con">
		<div class="foot-sp-l"></div>
		<div class="foot-sp-m">
欢迎：<%=request.getSession().getAttribute("tlrName")+" "+request.getSession().getAttribute("tlrNo")%> 机构：<%=request.getSession().getAttribute("brhName") %> 系统日期：<%=request.getSession().getAttribute("sysDate")%>
		</div>
		<div class="foot-sp-r"></div>
	</div>
</div>
</body>

</html>
