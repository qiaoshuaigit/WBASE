function openTaskDetail(appno,piid){
	path = _application_root + "/fpages/approve/ftl/workflowQuery.ftl";
			path += "?appno="+appno;
		    path +="&piid="+piid;
	//showXDialog({id : appno, url : path, title : "审批详细信息", allowMove : true, allowResize : false, width : 800, height : 600});
	window.open(path,"_blank","height=728,width=1000,fullscreen=no,toolbar=no,scrollbars=yes,resizable=yes,location=no,status=no,menubar=no,top=0,left=0;");
}

function openWaitToHandleTask() {
	path = _application_root + "/fpages/approve/ftl/WaitToHandleTaskEntry.ftl";
	window.open(path, "_self");
}

function openErrExceptionQuery(taskType, accsystemCode) {
	var path = _application_root;
	path += "/fpages/network/ftl/ErrExceptionQuery.ftl?";
	path += ("accsystemCodeClass=" + accsystemCode);
	window.open(path, "_self");
}

function openErrExceptionDetailQuery(id) {
	var paramMap = new Map();
	paramMap.put("id", id);
	
	loadPageWindows("userWin", "差错明细信息", "/fpages/network/ftl/ErrDetail.ftl", paramMap, "winZone", 550, 500);
}

function openCrossSystemQuery(accsystemCode, sshPre,settlmtDate,batchId,isSelf){
	//path = _application_root + "/fpages/sysmng/ftl/CrossSystemQuery.ftl";
	if(null == isSelf || undefined == isSelf){
		isSelf = "";
	}
	if(!accsystemCode || "" == accsystemCode) {
		return;
	}
	path = _application_root + "/fpages/sysmng/ftl/CrossSystemQuery1.ftl";
	path += "?accsystemCode=" + accsystemCode;
	path += "&sshPre="+sshPre;
	path +="&settlmtDate="+settlmtDate;
	path +="&batchId="+batchId;
	path +="&isSelf="+isSelf;
	//showXDialog({id : appno, url : path, title : "审批详细信息", allowMove : true, allowResize : false, width : 800, height : 600});
	window.open(path,"_blank","height=728,width=1000,fullscreen=no,toolbar=no,scrollbars=yes,resizable=yes,location=no,status=no,menubar=no,top=0,left=0;");
}

function openErrModifyInfoView(modifyId,procName) {
	path = _application_root + "/fpages/approve/ftl/ErrModifyInfoView.ftl";
			path += "?modifyId="+modifyId;
			path += "&procName="+procName;
	//showXDialog({id : "ErrModifyInfoView", url : path, title : "差错调整修改信息", allowMove : true, allowResize : false, width : 620, height : 400});
	window.open(path,"_blank","height=728,width=1000,fullscreen=no,toolbar=no,scrollbars=yes,resizable=yes,location=no,status=no,menubar=no,top=0,left=0;");
}

function openErrAdjustInfoView(batchId,procName,errType) {
	if("IbpsCG" == procName){
		path = _application_root + "/fpages/approve/ftl/IbpsErrAdjustInfoView.ftl";
	}else{
		path = _application_root + "/fpages/approve/ftl/ErrAdjustInfoView.ftl";
	}
		path += "?batchId="+batchId;
		path += "&procName="+procName;
		path += "&errType="+errType;
	//showXDialog({id : "ErrAdjustInfoView", url : path, title : "差错调整信息", allowMove : true, allowResize : false, width : 850, height : 520});
	window.open(path,"_blank","height=728,width=1000,fullscreen=no,toolbar=no,scrollbars=yes,resizable=yes,location=no,status=no,menubar=no,top=0,left=0;");
}

function openAccountAdjustInfoView(adjustId,procName) {
	path = _application_root + "/fpages/approve/ftl/AccountAdjustInfoView.ftl";
			path += "?adjustId="+adjustId;
			path += "&procName="+procName;
	//showXDialog({id : "ErrAdjustInfoView", url : path, title : "差错调整信息", allowMove : true, allowResize : false, width : 850, height : 520});
	window.open(path,"_blank","height=500,width=700,fullscreen=no,toolbar=no,scrollbars=yes,resizable=yes,location=no,status=no,menubar=no,top=0,left=0;");
}

function openAccountAdjustFormView(batchId,procName) {
	path = _application_root + "/fpages/approve/ftl/AccountAdjustFormView.ftl";
			path += "?batchId="+batchId;
			path += "&procName="+procName;
	//showXDialog({id : "ErrAdjustInfoView", url : path, title : "差错调整信息", allowMove : true, allowResize : false, width : 850, height : 520});
	window.open(path,"_blank","height=500,width=700,fullscreen=no,toolbar=no,scrollbars=yes,resizable=yes,location=no,status=no,menubar=no,top=0,left=0;");
}

function openErrDetailByBatchId(batchId, accsystemCode, bussType) {
	if("APS" == accsystemCode) {
		path = _application_root + "/fpages/approve/ftl/ZFApproveDetailInfo.ftl?batchId=" + batchId;
		if(bussType) {
			path += ("&bussType=" + bussType);
		}
	}
	else if("TIPS" == accsystemCode) {
		path = _application_root + "/fpages/approve/ftl/HLApproveDetailInfo.ftl?batchId=" + batchId;
	}
	else if("IBPS" == accsystemCode){
		path = _application_root + "/fpages/approve/ftl/IbpsApproveDetailInfo.ftl?batchId=" + batchId;
	}
	window.open(path,"_blank","height=728,width=1000,fullscreen=no,toolbar=no,scrollbars=yes,resizable=yes,location=no,status=no,menubar=no,top=0,left=0;");
}

