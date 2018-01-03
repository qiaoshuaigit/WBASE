<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
response.setHeader("Cache-Control", "no-cache"); 
response.setHeader("Cache-Control", "no-store");
response.setDateHeader("Expires", 0);
response.setHeader("Pragma", "no-cache");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/public.css">
<title>BLANK</title>
</head>

<body style="margin-top:0px; padding-left:0px; margin-left:0px; margin-bottom:0px;" bgcolor="#CAE1F3">
<div class="titleHeader">
	<div class="loginstatus">
		<b>登录信息: <%=request.getSession().getAttribute("tlrName")+" "+request.getSession().getAttribute("tlrNo")%> 机构：<%=request.getSession().getAttribute("brhName") %> 系统日期：<%=request.getSession().getAttribute("sysDate")%>
		</b>
	</div>
</div>
</body>

</html>
