<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="参数信息">
<script language="javascript" src="${contextPath}/js/extCommon.js"></script>
<script language="javascript" src="${contextPath}/js/xmlUtil.js"></script>
<script language="javascript" src="${contextPath}/js/treeNode.js"></script>
<script language="javascript" src="${contextPath}/js/treeBuilder.js"></script>
<script language="javascript" src="${contextPath}/dwr/interface/PrivateAction.js"></script>
<@CommonQueryMacro.CommonQuery id="ParamMng" init="false" navigate="true" submitMode="allchange">
<table align="center">
	<tr>
    	<td valign="top">
       		<@CommonQueryMacro.Interface id="intface" label="请输入查询条件" colNm="4" showButton="true" />
		</td>    
    </tr>
</table>
<table align="center">
    <tr><td valign="top">
      		<@CommonQueryMacro.PagePilot id="ddresult" maxpagelink="3"/>
      		<@CommonQueryMacro.DataTable id="datatable1" fieldStr="paramId[100],magicId[200]" readonly="true"/> 
      	</td>
      	<td valign="top">
      		<table style="margin-top: 8px;">
      			<tr>
      				<td>
      					<@CommonQueryMacro.Group id="group1" label="参数信息" fieldStr="paramId,magicId,paramDesc,paramValue" />
      				</td>
      			</tr>
      			<tr>
      				<td align="center">
      					<!--@CommonQueryMacro.Button id="btnAdd"/-->&nbsp;&nbsp;
      					<!--@CommonQueryMacro.Button id="btnDel"/-->&nbsp;&nbsp;
      					<@CommonQueryMacro.Button id="btnSave"/>
      				</td>
      			</tr>
      		</table>
      	</td>
    </tr>
</table>
</@CommonQueryMacro.CommonQuery>


<script language="JavaScript" for="window" event="onload">
	setReadOnlyForEmptyDataset(ParamMng_dataset);
	/** 设置页面初始后的焦点 */
	setFocus("ParamMng_interface", "paramId");
	clearHisInputData(ParamMng_interface_dataset);
</script>

<script language="JavaScript">
function ParamMng_dataset_afterScroll(dataset) {
	setReadOnlyForEmptyDataset(ParamMng_dataset);
	ParamMng_dataset.setFieldReadOnly("id", true);

}

function btnSave_needCheck(button) {
	//return false;
}

function btnSave_postSubmit(button) {
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
	if(!ParamMng_dataset.record) {
		return false;
	}
	return true;
}



function btnDel_onClickCheck(button) {
	if(ParamMng_dataset.record && confirm("是否删除当前记录？")) {
		return true;
	}
	return false;
}


</script>
</@CommonQueryMacro.page>
