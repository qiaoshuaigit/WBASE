<%@page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page language="java" %>
<%@page import="om.util.OmUtils" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<style type="text/css">
html {
	scrollbar-3dlight-color:gray;
	scrollbar-arrow-color:black;
	scrollbar-base-color:#cccccc;
	scrollbar-darkshadow-color:white;
	overflow-x:hidden;
	overflow-y:hidden;
}

.editor
{
	font-family: verdana;
	font-size: 9pt;
	border: darkgray 1px solid;
	width: 150px;
}

.loginDiv
{
	height: 422px;
	width: 647px;
}
.button
{
	font-family: verdana;
	border: darkgray 1px solid;
	font-size: 9pt;
	cursor: hand;
	height: 22px;
	width: 80px;
	background-color: white;
}
.loginTitle
{
	font-size: 10pt;
	color: #FFFFFF;
	font-family: "宋体","Tahoma","Arial";
	padding-top: 4px;
}
</style>
<script language="JavaScript">
	top.document.title = "用户登录";
	if(window.self != window.top) {
      window.open("#", "_top");
    }
    function resetErrMsg() {
    	var _msgDiv = document.getElementById("msgDiv");
    	msgDiv.innerHTML = "";
    }
</script> 
</head>
<body bgcolor="#CAE1F3">

<table id="_loginTable" class="loginDiv" align="center" background="<%=request.getContextPath()%>/images/login_bg.jpg">
	<tr>
		<td>
		<form name="loginForm" action="<%=request.getContextPath()%>/login.do" target="_top" method="post">
		<table align="center">
			<tr>
				<td class="loginTitle"><b>操作员：</b><input type="text" name="userName" id="userName" class="editor"/></td>
			</tr>
			<tr>
				<td class="loginTitle"><b>密　码：</b><input type="password" name="password" class="editor"/></td>
			</tr>
			<tr>
				<td><div id="msgDiv" style="color: #400080; text-align: center;font-size: 9pt;"></div></td>
			</tr>
			<tr>
				<td align="center">
				<input type="submit" name="button1" class="button" value="提交" style="BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/templets/lib/themes/default/button.gif); COLOR: black"/>
				&nbsp;&nbsp;
				<input type="reset"  name="button2" class="button" value="重置" style="BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/templets/lib/themes/default/button.gif); COLOR: black"
					onclick="resetErrMsg();">
				</td>
			</tr>
		</table>
		</form>
		</td>
	</tr>
<table>

</body>
<script language="JavaScript" for="window" event="onload">
	document.getElementById("userName").focus();
	<%
	String logout = OmUtils.trim((String) request.getAttribute("logout"));
	String loginType = OmUtils.trim((String) request.getAttribute("loginType"));
	%>
	var logout = "<%=logout%>";
	var loginType = "<%=loginType%>";
	if(logout == "logout" && "AB" == loginType) {
		window.close();
	}
	if(_loginTable && _loginTable.style) {
    	_loginTable.style.marginTop = ((document.documentElement.clientHeight || document.body.clientHeight) - 422)/2 + "px";
    }
    <%
    	String errMsg = OmUtils.trim((String) request.getAttribute("ERROR_MESSAGE"));
    	request.removeAttribute("ERROR_MESSAGE");
    	if(!OmUtils.isEmpty(errMsg)) {
    		%>
    		var _msgDiv = document.getElementById("msgDiv");
    		msgDiv.innerHTML = "<%=errMsg%>";
    		<%
    	}
    %>
</script>
</html>
