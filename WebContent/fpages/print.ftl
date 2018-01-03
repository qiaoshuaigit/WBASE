<html>
<head>
<#assign params=decodeStr(RequestParameters["params"]?default(""))>
<title>打印</title>
<meta http-equiv=Content-Type content="text/html; charset=GBK">
<style type="text/css">
* {
	margin:0
}
.editor
{
	font-family: verdana;
	font-size: 9pt;
	border: darkgray 1px solid;
	width: 30px;
	height: 18px;
	color: #FFFFFF;
	background-color: #758E9B;
}
.title
{
	font-size: 10pt;
	color: #FFFFFF;
	font-family: verdana;
	text-align: center;
	height: 18px;
}
.button
{
	font-family: verdana;
	border: darkgray 1px solid;
	font-size: 8pt;
	cursor: hand;
	height: 18px;
	width: 60px;
	color: #FFFFFF;
	background-color: #758E9B;
}
.button1
{
	font-family: verdana;
	border: darkgray 0px solid;
	font-size: 8pt;
	cursor: hand;
	height: 18px;
	width: 18px;
	color: #FFFFFF;
	background-color: #758E9B;
}
.label
{
	font-family: verdana;
	/*border: darkgray 1px solid;*/
	font-size: 8pt;
	height: 18px;
	/*width: 60px;*/
	color: #FFFFFF;
	background-color: #758E9B;
	vertical-align: middle;
}
</style>
<script type='text/javascript' src='${contextPath}/dwr/engine.js'> </script>
<script type='text/javascript' src='${contextPath}/dwr/util.js'> </script>
<script language="javascript" src="${contextPath}/templets/lib/dwr.js"></script>
<script type='text/javascript' src='${contextPath}/dwr/interface/CommonQueryResultProcess.js'> </script>
<script type='text/javascript' src='${contextPath}/dwr/interface/CommonQueryUpdateProcess.js'> </script>
<script language="javascript" src="${contextPath}/js/extCommon.js"></script>
<script language="javascript" src="${contextPath}/dwr/interface/PrivateAction.js"></script>
<script language="JavaScript">
var fpdScale = 100;
//--------------------------
//获取fpd
function getFpd() {
    vfpd = document.getElementById("fpd");
    return vfpd;
}
//打印
function print() {
	dwr.engine.setAsync(false);
	PrivateAction.updatePrintCount("${params}".substr(5));
	dwr.engine.setAsync(true);
    getFpd().print();
    //window.close();
    //location.reload(true);
}
//fpd初始化后自动调用
function onFpdOpen() {
	getFpd().setScale(100);
	fpdScale = 100;
}
function onFpdInit() {
	getFpd().setBarVisible(false);
}
function setScale(value) {
	if(value == fpdScale) {
		return;
	}
	fpdScale = value;
	getFpd().setScale(value);
}
function addScale() {
	if(fpdScale >= 100) {
		return;
	}
	if(fpdScale >= 90) {
		setScale(100);
		document.getElementById("_scale").value = fpdScale;
		return;
	}
	setScale(fpdScale + 10);
	document.getElementById("_scale").value = fpdScale;
}
function subScale() {
	if(fpdScale <= 0) {
		return;
	}
	if(fpdScale <= 10) {
		setScale(0);
		document.getElementById("_scale").value = fpdScale;
		return;
	}
	setScale(fpdScale - 10);
	document.getElementById("_scale").value = fpdScale;
}
</script>
</head>
<body style="overflow:hidden;background-color:#758E9B">
<table align="center" width="100%" style="border: solid 1 silver;">
<tr height="18px">
<td class="title">
<label class="label">显示比例</label>
<input type="button" class="button1" value="▼" title="缩小" onclick="subScale()">
<input type="text" name="scale" id="_scale" class="editor" value="100"/>
<label class="label">％</label>
<input type="button" class="button1" value="▲" title="放大" onclick="addScale()">
<input type="button" class="button" value="打  印" onclick="print()">
<script language="JavaScript">
_scale.onkeydown = function() {
	if(13 == window.event.keyCode) {
		var scaleText = document.getElementById("_scale").value;
		if(scaleText >= 0) {
			setScale(scaleText);
		}
	}
	if(8 != window.event.keyCode && 45 != window.event.keyCode && 46 != window.event.keyCode) {
		if((window.event.keyCode < 48 || window.event.keyCode > 57) && ((window.event.keyCode < 96 || window.event.keyCode > 105))) {
			event.returnValue = false;
			return;
		}
	}
}
</script>
</td>
</tr>
</table>
<object id="fpd" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
	codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab" width="100%" height="100%">
<param name="movie" value="../fpd.swf?url=${contextPath}/${params}">
<param name="quality" value="high">
<param name="bgcolor" value="#869ca7">
<param name="allowScriptAccess" value="sameDomain">
<param name="allowFullScreen" value="true">
</object>
</body>
</html>
