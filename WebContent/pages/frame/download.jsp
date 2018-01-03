<%@ page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page language="java"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/templets/lib/themes/default/extra.css">
<title>下载文件</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script language="javascript" src="<%=request.getContextPath()%>/js/richAlert.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/extCommon.js"></script>
</head>
<body>
<form action="<%=request.getContextPath()%>/fileDownload.do" method="post" enctype="application/x-www-form-urlencoded"
	name="download_form_<%=request.getParameter("id")%>" id="download_form_<%=request.getParameter("id")%>">
	<input type="hidden" name="id" value="<%=request.getParameter("id")%>"/>
</form>
<%
	String errMsg = (String) request.getAttribute("ERROR_MESSAGE");
	if(null != errMsg && errMsg.length() > 0) {
%>
    <script language="javascript">
        var _application_root = "<%=request.getContextPath()%>";
        alert("<%=errMsg%>", "操作失败", "error");
    </script>
<%
	}
%>
</body>
</html>
