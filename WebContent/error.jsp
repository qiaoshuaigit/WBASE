<%@page language="java" contentType="text/html; charset=GBK" %> 
<%@page import="java.util.List" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
response.setHeader("Cache-Control", "no-cache"); 
response.setHeader("Cache-Control", "no-store");
response.setDateHeader("Expires", 0);
response.setHeader("Pragma", "no-cache");
%>
<html>
<head>
<title>�������</title>
<html:base/>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>

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
    		<td class="errtitle">���Ĳ�����������
    	</tr>
    	<logic:notEmpty name="ERRINFO_LIST">
    	<logic:iterate id="errInfoList" name="ERRINFO_LIST">
    		<tr><td class="errtable">
	    	<bean:write name="errInfoList"/>
	    	</td></tr>
		</logic:iterate>
		</logic:notEmpty>
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
