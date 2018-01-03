<%@ page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page language="java"%>
<%@ page import="com.huateng.admin.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>JVM Monitor</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<style type="text/css">
.table {
	text-align: center;
	border: 1px;
	vertical-align: middle;
	border: gray 1px solid;
	border-collapse: collapse;
}
.titleTd {
	background-color: #DCDCDC;
	font-family: verdana;
	font-size: 9pt;
	text-align: center;
	align: center;
}
.normTd {
	background-color: #ffffff;
	font-family: verdana;
	font-size: 9pt;
	text-align: center;
	align: center;
}
.usedTd {
	background-color: #B3D9CB;
	font-family: verdana;
	font-size: 9pt;
	text-align: right;
	align: center;
}
</style>
<script language="javascript" src='<%=request.getContextPath()%>/dwr/engine.js'> </script>
<script language="javascript" src='<%=request.getContextPath()%>/dwr/util.js'> </script>
<script language="javascript" src="<%=request.getContextPath()%>/templets/lib/dwr.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/dwr/interface/PrivateAction.js"></script>
<script language="javascript">
function refresh() {
	location.reload();
}
function doJvmGc() {
	dwr.engine.setAsync(false);
	PrivateAction.doJvmGc();
	dwr.engine.setAsync(true);
	refresh();
}
setInterval("refresh();", 30000);
</script>
</head>
<body>
<%
JvmInfo jvmInfo = SystemMonitor.getJvmInfo();
%>
<table class="table" align="center" cellspacing="0" cellpadding="0" rules=all width="90%">
<tr>
<td class="titleTd" width="80%">JVM内存使用情况<br>MaxMemory=<%=jvmInfo.getMaxMemory()%>M, TotalMemory=<%=jvmInfo.getJvmUsedInfoList().get(jvmInfo.getJvmUsedInfoList().size() - 1).getTotalMemory()%>M</td>
<td class="titleTd" width="10%">时间</td>
<td class="titleTd" width="10%">当前使用</td>
</tr>
<%
for(int i = 0; i < jvmInfo.getJvmUsedInfoList().size(); i ++) {
	JvmBean bean = jvmInfo.getJvmUsedInfoList().get(i);
%>
	<tr>
	<td class="normTd" width="80%">
	
	<table width="<%=bean.getPUsed()%>%" align="left" cellspacing="0" cellpadding="0">
	<tr>
	<td class="usedTd" width="100%"><%=bean.getPUsed()%>%</td>
	</tr>
	</table>
	
	</td>
	<td class="normTd"  width="10%"><%=bean.getTime()%></td>
	<td class="normTd"  width="10%"><%=bean.getUsedMemory()%>M</td>
	</tr>
<%
}
%>
</table>
<table border="0" align="center" cellspacing="0" cellpadding="0" rules=none width="70%">
<tr>
<td align="center" width="100%">
<input type="button" value="JVM垃圾回收" onclick="doJvmGc()"/>
</td>
</tr>
</table>
</body>
</html>
