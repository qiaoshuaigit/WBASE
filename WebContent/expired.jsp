<%@page language="java" contentType="text/html; charset=GBK" %> 
<%@page import="java.util.List" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
response.setHeader("Cache-Control", "no-cache"); 
response.setHeader("Cache-Control", "no-store");
response.setDateHeader("Expires", 0);
response.setHeader("Pragma", "no-cache");
%>
<html>
<head>
<title>会话无效</title>
<html:base/>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<script language="JavaScript" type="text/javascript">
if(window.self != window.top) {
	window.open("<%=request.getContextPath()%>/expired.jsp", "_top");
}
var secs =5; //倒计时的秒数
var URL ;
function reLoad(url) {
    URL = url;
    for(var i = secs;i >= 0;i --)
    {
        window.setTimeout('doUpdate(' + i + ')', (secs - i) * 1000);
    }
}
function doUpdate(num)
{
    document.getElementById('ShowDiv').innerHTML = num ;
    if(num == 0) {
        window.location = URL;
    }
}
</script>
<body>
<table align="center" valign="middle" width="700px" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="56px"><img src="./images/info_f1.gif" width="56px" height="44px"></td>
    <td width="618px" background="./images/info_f3.gif"><img src="./images/info_f2.gif" width="165px" height="44px"/></td>
    <td width="26px"><img src="./images/info_f4.gif" width="26px" height="44px"></td>
  </tr>
  <tr>
    <td width="56px" height="71px" valign="top" background="./images/info_f6.gif"><img src="./images/info_f5.gif" width="56px" height="30px"></td>
    <td style="background-color:#FCFDFD;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
    	<tr>
    		<td class="errtitle">您的会话已过期或无效，请重新登陆
    	</tr>
    	<tr>
    		<td class="errinfo">
				<ul>
					<li>您已经超过会话有效时间未访问应用服务器，本地会话已过期</li>
					<li>您访问的应用服务器已经重新启动，本地会话已无效</li>
				</ul>
    		</td>
    	</tr>
    </table></td>
    <td width="26px" background="./images/info_f11.gif">&nbsp;</td>
  </tr>
  <tr>
    <td><img src="./images/info_f7.gif" width="56px" height="36px"></td>
    <td background="./images/info_f9.gif"><img src="./images/info_f8.gif" width="77px" height="36px"></td>
    <td width="26px"><img src="./images/info_f10.gif" width="25px" height="35px"></td>
  </tr>
</table>
</body>
</html>
