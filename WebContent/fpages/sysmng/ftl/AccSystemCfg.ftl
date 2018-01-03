<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="反馈系统配置">
<#assign accsystemCode = RequestParameters["accsystemCode"]?default("")>
<table border=0 align="center">
<tr>
<td>
<@CommonQueryMacro.CommonQuery id="AccSystemCfg" init="true" navigate="false" submitMode="allchange">
<table align="center">
	<tr>
	   	<td valign="top">
	   		<@CommonQueryMacro.PagePilot id="ddresult" maxpagelink="3"/>
	    	<@CommonQueryMacro.DataTable id="datatablelst" fieldStr="accsystemCode[100],errType[200],jobNum[50],taskNum[50],serverSystem[100],interfaceType[100]"/>
	    	<table align="center">
	    		<tr>
	    			<td>
                        <@CommonQueryMacro.Button id="btnAddList"/>&nbsp;&nbsp;
                        <@CommonQueryMacro.Button id="btnDelList"/>&nbsp;&nbsp;
                        <@CommonQueryMacro.Button id="btnSaveList"/>&nbsp;&nbsp;
                     
	    			</td>
	    		</tr>
	    	</table>
	    </td>
	</tr>
</table>
</@CommonQueryMacro.CommonQuery>
</td>
</tr>
<tr id="NeedCdtTr" style="display:none">
<td>
<FIELDSET name='intface' style="padding: 6px;" expand="true">
<LEGEND extra="groupboxtitle">&nbsp;请输入必经该审批岗位的条件&nbsp;</LEGEND>
<div style="width:100%;">
<div style="">
<@CommonQueryMacro.CommonQuery id="ApproveRouteCondition" init="false" navigate="false" submitMode="allchange">
<table align="center">
	<tr>
	   	<td valign="top">
	    	<@CommonQueryMacro.DataTable id="datatable2" fieldStr="needCondition[200],minCondition[200]"/>
	    	<table align="center">
	    		<tr>
	    			<td>
                        <@CommonQueryMacro.ButtonGroup />
	    			</td>
	    		</tr>
	    	</table>
	    </td>
	</tr>
</table>
</@CommonQueryMacro.CommonQuery>
</div>
</div>
</FIELDSET>
</td>
</tr>
</table>
<script language="JavaScript">
setReadOnlyForEmptyDataset(AccSystemCfg_dataset);
setReadOnlyForEmptyDataset(ApproveRouteCondition_dataset);
function ApproveRouteCondition_dataset_flushDataPost(dataset) {
	setReadOnlyForEmptyDataset(ApproveRouteCondition_dataset);
}

function btnDelList_onClickCheck(button) {
	if(AccSystemCfg_dataset.record && confirm("是否删除当前记录？")) {
		return true;
	}
	return false;
}

function btnAddList_onClick(button) {
	AccSystemCfg_dataset.setValue2("accsystemCode", "${accsystemCode}");
}
function btnAddCdt_onClick(button) {
	lstId = AccSystemCfg_dataset.getValue("id");
	if(lstId && lstId != 0) {
		if(!ApproveRouteCondition_dataset.record) {
			return false;
		}
		ApproveRouteCondition_dataset.setReadOnly(false);
    	ApproveRouteCondition_dataset.setValue2("lstId", lstId);
    	return true;
    }
    else {
    	alert("请先保存后再录入条件信息！");
    	return false;
    }
}
function AccSystemCfg_dataset_afterScroll(dataset) {
	setReadOnlyForEmptyDataset(AccSystemCfg_dataset);
}
function btnSaveCdt_postSubmit(button) {
	lstId = AccSystemCfg_dataset.getValue("id");
	if(lstId && lstId != 0) {
		ApproveRouteCondition_dataset.setParameter("lstId", lstId);
		ApproveRouteCondition_dataset.flushData();
	}
	var retParam = button.returnParam;
	if(retParam) {
		var resultMsg = retParam.resultMsg;
		if(resultMsg && "" != resultMsg) {
			alert(resultMsg);
		}
	}
}
function btnSaveList_postSubmit(button) {
	AccSystemCfg_dataset.flushData();
	var retParam = button.returnParam;
	if(retParam) {
		var resultMsg = retParam.resultMsg;
		if(resultMsg && "" != resultMsg) {
			alert(resultMsg);
		}
	}
}
</script>
</@CommonQueryMacro.page>
