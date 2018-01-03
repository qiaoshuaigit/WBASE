<%@page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page language="java" %>
<%@page import="com.huateng.stl4j.service.UserMangerService" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/menubar.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/menu.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/public.css">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>MENU</title>
<script language="javascript" src="<%=request.getContextPath()%>/js/menu.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/scrollbutton.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/poslib.js"></script>
</head>
<%
UserMangerService ums = UserMangerService.getInstance();
StringBuffer menu = null;
try {
	menu = ums.getDefinedMenu((Integer) request.getSession().getAttribute("tlrId"));
} catch(Exception e) {
	e.printStackTrace();
}
%>
<body>
<div class="sp-nav">
	<div class="sp-con">
		<div class="sp-l"></div>
		<div class="sp-m">
			<img src="<%=request.getContextPath()%>/images/sys.gif"/>
			<strong>WELCOME! DEMO SYSTEM!</strong>
			<span class="span">
			<script language="javascript">
				Menu.prototype.mouseHoverDisabled = false;
				Menu.prototype.cssFile = "<%=request.getContextPath()%>/css/menu.css";
				var menuMain = new MenuBar();
				Menu_buildMenu(new Array(<%=menu.toString()%>), menuMain);
				menuMain.write();
			</script>
			</span>
		</div>
		<div class="sp-r"></div>
	</div>
</div>
</body>

</html>
<script language="JavaScript" for="window" event="onload">
	var vm = 926/window.screen.width * 100;
	var lr = (100 - vm)/2;
	var sp = lr + "%,*," + lr + "%";
	parent.frames["split_main"].cols = sp;
</script>
