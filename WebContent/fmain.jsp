<%@page language="java" contentType="text/html; charset=GBK" %> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
response.setHeader("Cache-Control", "no-cache"); 
response.setHeader("Cache-Control", "no-store");
response.setDateHeader("Expires", 0);
response.setHeader("Pragma", "no-cache");
%>
<html>
<head>
<title>主页</title>
<html:base/>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">
</head>
<script language="JavaScript" type="text/JavaScript">
if(window.Event) {
	document.captureEvents(Event.MOUSEUP);
}

function nocontextmenu() {
	event.cancelBubble = true
	event.returnValue = false;

	return false;
}

function norightclick(e) {
	if(window.Event) {
		if(e.which == 2 || e.which == 3)
		return false;
	}
	else {
		if(event.button == 2 || event.button == 3) {
			event.cancelBubble = true
			event.returnValue = false;
			return false;
		}
	}
}
document.oncontextmenu = nocontextmenu; // for IE5+
document.onmousedown = norightclick; // for all others

window.onbeforeunload = function() {
	this.location.replace("<%=request.getContextPath()%>/logout.do");
}
</script>
 
<frameset rows="88,*" framespacing="0" border="0" frameborder="0" >
  <frameset id="titlerow" rows="*,6" framespacing="0" border="0" frameborder="0" >
  	<frame name="head" src="<%=request.getContextPath()%>/pages/frame/fhead.jsp" frameborder="0" scrolling="no" noresize>
  	<frame name="splitbar" src="<%=request.getContextPath()%>/pages/frame/splitbar.jsp" frameborder="0" scrolling="no" noresize>
  </frameset>
  <frameset id="splitcol" cols="20%,80%" framespacing="1" border="1" frameborder="1" >
  	<frame name="leftwin" id="leftwin" src="<%=request.getContextPath()%>/pages/frame/leftwin.jsp" frameborder="0" scrolling="no"   >
  	<frame name="rightwin" id="rightwin" src="<%=request.getContextPath()%>/pages/frame/rightwin.jsp" frameborder="0" scrolling="no"   >
  </frameset>
</frameset>
</html>
