<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="岗位管理">
<script language="javascript" src="${contextPath}/js/extCommon.js"></script>
<script language="javascript" src="${contextPath}/js/xmlUtil.js"></script>
<script language="javascript" src="${contextPath}/js/treeNode.js"></script>
<script language="javascript" src="${contextPath}/js/treeBuilder.js"></script>
<script language="javascript" src="${contextPath}/dwr/interface/PrivateAction.js"></script>
<@CommonQueryMacro.CommonQuery id="RoleInfo" init="true" navigate="true" submitMode="allchange">
<table align="center">
	<tr>
      	<td valign="top">
      		<@CommonQueryMacro.PagePilot id="ddresult" maxpagelink="3"/>
      		<@CommonQueryMacro.DataTable id="datatable1" fieldStr="id[60],roleName[160],status[60]" readonly="false"/>
      		<table align="center">
      			<tr>
      				<td>
      					<@CommonQueryMacro.Button id="btnAdd"/>&nbsp;&nbsp;
      					<@CommonQueryMacro.Button id="btnDel"/>&nbsp;&nbsp;
      					<@CommonQueryMacro.Button id="btnSave"/>&nbsp;&nbsp;
      					<@CommonQueryMacro.Button id="btnFunc"/>
      				</td>
      			</tr>
      		</table>
      	</td>
    </tr>
</table>
</@CommonQueryMacro.CommonQuery>
<@CommonQueryMacro.WindowElement/>
<script language="JavaScript">

function btnDel_onClickCheck(button) {
	if(RoleInfo_dataset.record && confirm("是否删除当前记录？")) {
		return true;
	}
	return false;
}


function btnAdd_onClick(button) {
	setFocusToFirstCellOfNewRowForDataTabel(datatable1);
}

function btnAdd_needCheck(button) {
	if(!RoleInfo_dataset.record) {
		return false;
	}
	return true;
}

function RoleInfo_dataset_onSetValue(dataset, field, value) {
	if("id" == field.name) {
		var vOldId = RoleInfo_dataset.getValue("oldId");
		if(!isNaN(vOldId) && 0 != vOldId && vOldId != value) {
			alert("岗位编号不允许修改！");
			return vOldId;
		}
	}
	return value;
}

function datatable1_changeTableRowEvent(table) {
	var vOldId = RoleInfo_dataset.getValue("oldId");
	if(isNaN(vOldId) || 0 == vOldId) {
		RoleInfo_dataset.setFieldReadOnly("id", false);
	}
	else {
		RoleInfo_dataset.setFieldReadOnly("id", true);
	}
}

function RoleInfo_dataset_afterChange(dataset, field) {
	if("id" == field.name) {
		var vId = RoleInfo_dataset.getValue("id");
		RoleInfo_dataset.setValue("roleCode", vId);
	}
}

function btnFunc_onClick(button) {
	var vId = RoleInfo_dataset.getValue("oldId");
	if(!isNaN(vId) && 0 != vId) {
		var paramMap = new Map();
		paramMap.put("roleCode", RoleInfo_dataset.getValue("roleCode"));
		paramMap.put("roleName", getEncodeStr(RoleInfo_dataset.getValue("roleName")));
		loadPageWindows("userWin", "分配权限", "/fpages/sysmng/ftl/MenuTree.ftl", paramMap, "winZone", 500, 400);
	}
	else {
		alert("请保存岗位信息后再给该岗位分配权限！");
	}
}

function winZone_onCloseCheck() {
	return true;
}

function btnSave_postSubmit(button) {
	RoleInfo_dataset.flushData(RoleInfo_dataset.pageIndex);
	var retParam = button.returnParam;
	if(retParam) {
		var resultMsg = retParam.resultMsg;
		if(resultMsg && "" != resultMsg) {
			alert(resultMsg);
		}
	}
	RoleInfo_dataset.moveFirst();
	while (!RoleInfo_dataset.eof) {
		var vId = 0 + RoleInfo_dataset.getValue("id");
		var vOldId = 0 + RoleInfo_dataset.getValue("oldId");
		if(vId != vOldId) {
			RoleInfo_dataset.setValue("oldId", vId);
		}
		RoleInfo_dataset.moveNext();
	}
}
</script>
</@CommonQueryMacro.page>
