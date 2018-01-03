<#assign primaryKey = decodeStr(RequestParameters["primaryKey"]?default(""))>
<#assign bussType = decodeStr(RequestParameters["bussType"]?default(""))>
<#assign fileSizeLimit = decodeStr(RequestParameters["fileSizeLimit"]?default("3 MB"))>
<#assign fileTypes = decodeStr(RequestParameters["fileTypes"]?default("*.*"))>
<#assign fileTypeDesc = decodeStr(RequestParameters["fileTypeDesc"]?default("�����ļ�"))>
<#assign _SWF_ID_ = RequestParameters["_SWF_ID_"]?default("")>
<html>  
<head>
<meta http-equiv=Content-Type content="text/html; charset=GBK">
<link href="${contextPath}/js/swfupload/default.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/js/swfupload/button.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${contextPath}/js/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${contextPath}/js/swfupload/swfupload.queue.js"></script>
<script type="text/javascript" src="${contextPath}/js/swfupload/handlers.js"></script>
<script language="javascript" src="${contextPath}/js/extCommon.js"></script>
<script language="javascript" src="${contextPath}/js/richAlert.js"></script>
<script type="text/javascript">
    var _application_root="${contextPath}";
	var swf,swfInstallFlag;
	try {
		var swf = new ActiveXObject("ShockwaveFlash.ShockwaveFlash");
		(swf) ? swfInstallFlag = true : swfInstallFlag = false;
	} catch(ex) {
		swfInstallFlag = false;
	}
	
	var swfu;
	window.onload = function () {
		swfu = new SWFUpload({
			upload_url: "${contextPath}/fileUpload.do",
			post_params: {"primaryKey" : "${primaryKey}", "bussType" : "${bussType}"},
		
			// File Upload Settings
			file_size_limit : "${fileSizeLimit}",	// ?MB
			file_types : "${fileTypes}",
			file_types_description : "${fileTypeDesc}",
			file_upload_limit : "0",
		
			file_queue_error_handler : fileQueueError,
			file_dialog_complete_handler : fileDialogComplete,//ѡ����ļ����ύ
			file_queued_handler : fileQueued,
			upload_progress_handler : uploadProgress,
			upload_error_handler : uploadError,
			upload_success_handler : uploadSuccessed,
			upload_complete_handler : uploadComplete,
		
			// Button Settings
			button_image_url : "${contextPath}/images/SmallSpyGlassWithTransperancy_17x18.png",
			button_placeholder_id : "spanButtonPlaceholder",
			button_width: 436,
			button_height: 18,
			button_text : '<span class="button">���ѡ���ļ�<span class="buttonSmall">(�ļ���С����${fileSizeLimit})</span></span>',
			button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
			button_text_top_padding: 0,
			button_text_left_padding: 18,
			button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
			button_cursor: SWFUpload.CURSOR.HAND,

			// Flash Settings
			flash_url : "${contextPath}/js/swfupload/swfupload.swf",
		
			custom_settings : {
				upload_target : "divFileProgressContainer"
			},
			// Debug Settings
			debug: false  //�Ƿ���ʾ���Դ���
		});
	};
	function startUploadFile(){
		var infoTable = document.getElementById("infoTable");
		var rows = infoTable.rows;
		if(rows.length > 0) {
			swfu.startUpload();
		}
		else {
			alert("����ѡ���ϴ��ļ���");
		}
	}
	function uploadSuccessed(file, serverData) {
		try {
			var progress = new FileProgress(file,  this.customSettings.upload_target);
			addFileInfo(file.id,"�ϴ����");
			var msg = trimStr(serverData);
			if(msg && "" != msg) {
				addFailedInfo(file.id, "�ϴ�ʧ��");
				progress.setCancelled();
				progress.setStatus("<font color='red'>��̨�������" + trimStr(serverData) + "</font>");
				progress.toggleCancel(false);
			}
			else {
				if("" != "${_SWF_ID_}") {
					parent.${_SWF_ID_}_success();
				}
			}
		} catch (ex) {
			this.debug(ex);
		}
	}
</script>
<style type="text/css">
#titleHolder{width:100%;height:16px;filter:progid:DXImageTransform.Microsoft.gradient(enabled='true',startColorstr=#FFADD8E6, endColorstr=#00000000,gradientType=1);}
</style>
</head>
<body>
<div id="content" id="content" style="margin: 1;padding: 1;">
<form>
<div id="titleHolder" style="display: inline; border: 1px solid gray; background-color: #FFFFFF; padding: 2px;width:500px">
<span id="spanButtonPlaceholder"></span>
    <input id="btnUpload"
	type="button" value="��  ��" onclick="startUploadFile();"
	class="btn3_mouseout" onmouseup="this.className='btn3_mouseup'"
	onmousedown="this.className='btn3_mousedown'"
	onmouseover="this.className='btn3_mouseover'"
	onmouseout="this.className='btn3_mouseout'" />
	<#--
	<input id="btnCancel"
	type="button" value="ȡ���ϴ�" onclick="cancelUpload();"
	disabled="disabled" class="btn3_mouseout"
	onmouseup="this.className='btn3_mouseup'"
	onmousedown="this.className='btn3_mousedown'"
	onmouseover="this.className='btn3_mouseover'"
	onmouseout="this.className='btn3_mouseout'" />
	-->
	</div>
</form>
<div id="divFileProgressContainer"></div>
<table id="infoTable" border="1"
	style="display: inline; border: 1px solid gray; background-color: #ADD8E6; padding: 2px; top: 203px; width:500px; height:23px; position: absolute;">
</table>
</div>
</div>
<div id="_footDiv" style="border: 1px solid gray; background-color: #FFFFFF; padding: 2px; width:500px" />
<script type="text/javascript">
//swfInstallFlag = false;
if(!swfInstallFlag) {
	var swfDownloadDiv = document.createElement('div');
	var _marginTop = 230;
	
	var _divHtml = "<div>";
	_divHtml += "���ĵ���δ��װFlashPlayer,��";
	_divHtml += "<a href=\"javascript:downloadFlashPlayer()\"><u><font color=#8000FF>����FlashPlayer</font></u></a>";
	_divHtml += "����װ�����´�IE���������!";
	_divHtml += "</div>";
	
	swfDownloadDiv.setAttribute('id', 'swfDownloadDiv');
	swfDownloadDiv.innerHTML = _divHtml;
	var _footDiv = document.getElementById("_footDiv");
	_footDiv.style.marginTop = "" + _marginTop + "px";
	_footDiv.style.position = "absolute";
	_footDiv.style.width = "500px";
	_footDiv.style.height = "23px";
	_footDiv.style.left = "2px";
	_footDiv.style.top = "2px";
	
	_footDiv.appendChild(swfDownloadDiv);
}
else {
	var infoTable = document.getElementById("infoTable");
	infoTable.style.marginTop = "23px";
	var _footDiv = document.getElementById("_footDiv");
	_footDiv.style.display = "none";
}
function downloadFlashPlayer() {
	window.open("${contextPath}/res/flashplayer11-6_install_win_ax.exe", "_blank");
}
</script>
</body>
</html>
