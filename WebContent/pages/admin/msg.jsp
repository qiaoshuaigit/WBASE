<%@ page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page language="java" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
<%=request.getAttribute("MSG")%>
</body>
</html>
