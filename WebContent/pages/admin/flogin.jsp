<%@ page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page language="java"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort() + path + "/";
String tlrno = request.getParameter("tlrno");
if(null == tlrno) {
	tlrno = "";
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="<%=path%>/templets/lib/themes/default/extra.css">
<title>ADMIN</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<script language="javascript">
</script>
</head>
<body>

<form action="<%=basePath%>pages/admin" method="post" name="form1">
<input type="hidden" name="action" value="login" />
<input type="hidden" name="tlrno"  value="<%=tlrno%>"/>
<table class=datatable borderColor=silver cellSpacing=0 cellPadding=2 rules=all style="margin-top: 50px;">
	<tr style="BACKGROUND-IMAGE: url(<%=path%>/templets/lib/themes/default/table_header.gif);" height=20>
		<td align=center colspan=2>SQL ADMIN LOGIN</td>
	</tr>

	<tr>
		<td>请输入密码：<input type="password" class=editor name="accesskey" /></td>
	</tr>

	<tr>
		<td align=center colspan=2><input type="submit" name="button1" value="提交" class=button style="BACKGROUND-IMAGE: url(<%=path%>/templets/lib/themes/default/button.gif); COLOR: black"/>
		<input type="button" name="button2" value="关闭" onclick="window.open('about:blank', '_self')" class=button style="BACKGROUND-IMAGE: url(<%=path%>/templets/lib/themes/default/button.gif); COLOR: black"/></td>
	</tr>
</table>
</form>

</body>
</html>
