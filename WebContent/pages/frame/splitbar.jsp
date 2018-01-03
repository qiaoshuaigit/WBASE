<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/public.css">
<title>BLANK</title>
</head>

<body style="margin-top:0px; padding-left:0px; margin-left:0px; margin-bottom:0px;" bgcolor="#CAE1F3">
<div id="_splitBar" class="splitBar">
</div>
</body>

<script language="JavaScript" for="window" event="onload">
	if(_splitBar && _splitBar.style) {
    	//_splitBar.style.width = (document.documentElement.clientWidth || document.body.clientWidth) + "px";
    	_splitBar.style.width = "" + window.screen.availWidth + "px";
    }
</script>

</html>
