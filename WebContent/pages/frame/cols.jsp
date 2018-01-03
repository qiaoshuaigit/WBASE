<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cols</title>
</head>
<body style="margin: 0; overflow: hidden" bgcolor=#FFFFFF>
<script language=javascript>
var cols="20%,80%";
var isShow=true;
function hidden()
{
	if(isShow == true)
	{
		cols=parent.parent.frames["splitcol"].cols;
		parent.parent.frames["splitcol"].cols="0,*";
		document.getElementById("direct").src="<%=request.getContextPath()%>/images/rightbar_btn_r.jpg";
		isShow=false;
	}
	else
	{
		parent.parent.frames["splitcol"].cols=cols;
		document.getElementById("direct").src="<%=request.getContextPath()%>/images/rightbar_btn_l.jpg";
		isShow=true;
	}
}
</script>

<div id='_mnuMain' style='left:0;height:100%' align="center">
<img id="direct" src="<%=request.getContextPath()%>/images/rightbar_btn_l.jpg" onclick="hidden()"/>
</div>
</body>
<script language="JavaScript" for="window" event="onload">
	if(_mnuMain && _mnuMain.style) {
    	_mnuMain.style.marginTop = ((document.documentElement.clientHeight || document.body.clientHeight) - 88 - 10)/2 + "px";
    }
</script>
</html>