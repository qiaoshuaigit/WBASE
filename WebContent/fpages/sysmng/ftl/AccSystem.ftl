<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="机构管理">
<script language="javascript" src="${contextPath}/js/extCommon.js"></script>
<script language="javascript" src="${contextPath}/js/xmlUtil.js"></script>
<script language="javascript" src="${contextPath}/js/treeNode.js"></script>
<script language="javascript" src="${contextPath}/js/treeBuilder.js"></script>
<script language="javascript" src="${contextPath}/dwr/interface/PrivateAction.js"></script>
<@CommonQueryMacro.CommonQuery id="AccSystem" init="false" navigate="true" submitMode="allchange">
<table align="center">
	<tr>
    	<td valign="top">
       		<@CommonQueryMacro.Interface id="intface" label="请输入查询条件" colNm="4" showButton="true" />
		</td>
    </tr>
</table>
<table align="center">
    <tr><td>
		<table>
		<tr>
	      	<td valign="top">
	      		<@CommonQueryMacro.PagePilot id="ddresult" maxpagelink="3"/>
	      		<@CommonQueryMacro.DataTable id="datatable1" fieldStr="accsystemCode[120]" readonly="true"/>
	      	</td>
	      	<td valign="top">
	      		<table style="margin-top: 8px;">
	      			<tr>
	      				<td>
	      					<@CommonQueryMacro.Group id="group1" label="接入系统信息信息" fieldStr="accsystemName,accsystemCode,managerItName,managerItTel,managerBusinessAnme,managerBusinessTel" />
	      				</td>
	      			</tr>
	      			<tr>
	      				<td align="center">
	      					<@CommonQueryMacro.Button id="btnAdd"/>&nbsp;&nbsp;
	      					<@CommonQueryMacro.Button id="btnDel"/>&nbsp;&nbsp;
	      					
	      					<@CommonQueryMacro.Button id="btnSave"/>&nbsp;&nbsp;
	      					<@CommonQueryMacro.Button id="btnCfg"/>&nbsp;&nbsp;
	      				</td>
	      			</tr>
	      		</table>
	      	</td>
	    </tr>
		</table>
    </td></tr>
</table>
</@CommonQueryMacro.CommonQuery>
<@CommonQueryMacro.WindowElement/>

<script language="JavaScript" for="window" event="onload">
	setReadOnlyForEmptyDataset(AccSystem_dataset);
	/** 设置页面初始后的焦点 */
	setFocus("AccSystem_interface", "accsystemName");
	clearHisInputData(AccSystem_interface_dataset);
</script>

<script language="JavaScript">
function accsystemCode_DropDown_onSelect(dropDown,record,editor) {
	var v_accsystemName=record.getValue("accsystemName");
	AccSystem_dataset.setValue("accsystemName", accsystemName);
	return true;
}

function AccSystem_dataset_afterScroll(dataset) {
	setReadOnlyForEmptyDataset(AccSystem_dataset);
}

function btnSave_needCheck(button) {
	//return false;
}

function btnSave_postSubmit(button) {
	AccSystem_dataset.flushData(AccSystem_dataset.pageIndex);
	var retParam = button.returnParam;
	if(retParam) {
		var resultMsg = retParam.resultMsg;
		if(resultMsg && "" != resultMsg) {
			alert(resultMsg);
		}
	}
}

function btnAdd_needCheck(button) {
	setFocus("group1", "brhNo");
	if(!AccSystem_dataset.record) {
		return false;
	}
	return true;
}

function btnDel_onClickCheck(button) {
	if(AccSystem_dataset.record && confirm("是否删除当前记录？")) {
		return true;
	}
	return false;
}


function btnCfg_onClick(button) {
	//orgAlert("cfg");
	var idValue = AccSystem_dataset.getValue("id");
	if(!isNaN(idValue) && 0 != idValue) {
		var accsystemCode = AccSystem_dataset.getValue("accsystemCode");
		var _url = _application_root + "/fpages/sysmng/ftl/AccSystemCfg.ftl?accsystemCode="+accsystemCode;
		showXDialog({id : "userWin", url : _url, title : "接入系统接口设置", allowMove : true, allowResize : false, width : 600, height : 400});
	}
	else {
		alert("请保存后再给该记录配置！");
	}
}


</script>
</@CommonQueryMacro.page>
