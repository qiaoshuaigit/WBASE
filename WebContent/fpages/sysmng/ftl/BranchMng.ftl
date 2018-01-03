<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign globalinfo = statics["com.huateng.ebank.common.GlobalInfo"].getCurrentInstanceWithoutException()>
<@CommonQueryMacro.page title="机构管理">
<script language="javascript" src="${contextPath}/js/extCommon.js"></script>
<script language="javascript" src="${contextPath}/js/xmlUtil.js"></script>
<script language="javascript" src="${contextPath}/js/treeNode.js"></script>
<script language="javascript" src="${contextPath}/js/treeBuilder.js"></script>
<script language="javascript" src="${contextPath}/dwr/interface/PrivateAction.js"></script>
<@CommonQueryMacro.CommonQuery id="BranchMng" init="false" navigate="true" submitMode="allchange">
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
	      		<@CommonQueryMacro.DataTable id="datatable1" fieldStr="brhNo[80],brhName[240]" readonly="true"/>
	      	</td>
	      	<td valign="top">
	      		<table style="margin-top: 8px;">
	      			<tr>
	      				<td>
	      					<@CommonQueryMacro.Group id="group1" label="机构信息" fieldStr="brhNo,brhName,brhClass,upBrhId,contacts,teleNo,cityCd,address,postno,status" />
	      				</td>
	      			</tr>
	      			<tr>
	      				<td align="center">
	      					<@CommonQueryMacro.Button id="btnAdd"/>&nbsp;&nbsp;
	      					<#--
	      					<@CommonQueryMacro.Button id="btnDel"/>&nbsp;&nbsp;
	      					-->
	      					<!--@CommonQueryMacro.Button id="btnTree"/>&nbsp;&nbsp;-->
	      					<@CommonQueryMacro.Button id="btnSave"/>
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
	setReadOnlyForEmptyDataset(BranchMng_dataset);
	/** 设置页面初始后的焦点 */
	setFocus("BranchMng_interface", "brhNo");
	
	clearHisInputData(BranchMng_interface_dataset);
	clearHisInputData(BranchMng_dataset);
</script>

<script language="JavaScript">
function BranchMng_dataset_afterScroll(dataset) {
	setReadOnlyForEmptyDataset(BranchMng_dataset);
	var vBrhNo = BranchMng_dataset.getValue("brhNo");
	//当登录用户的机构和选中机构相同时，无权限修改机构号、网点号、机构级别、归属上级机构、状态 ；当登录用户的机构为选中机构的上级时，可修改机构级别、归属上级机构、状态 
	
	BranchMng_dataset.setFieldReadOnly("brhNo", true);
	
	if("${globalinfo.getBranchNo()}" == vBrhNo){

		BranchMng_dataset.setFieldReadOnly("upBrhId", true);
		BranchMng_dataset.setFieldReadOnly("brhClass", true);
		BranchMng_dataset.setFieldReadOnly("status", true);
	}else {
		BranchMng_dataset.setFieldReadOnly("upBrhId", false);
		BranchMng_dataset.setFieldReadOnly("brhClass", false);
		BranchMng_dataset.setFieldReadOnly("status", false);
	}
}

function btnSyncBrh_needCheck(button) {
	return false;
}

function btnSyncBrh_postSubmit(button) {
	BranchMng_dataset.flushData(BranchMng_dataset.pageIndex);
	var retParam = button.returnParam;
	if(retParam) {
		var resultMsg = retParam.resultMsg;
		if(resultMsg && "" != resultMsg) {
			alert(resultMsg);
		}
	}
}


function btnAdd_onClick(button) {
	BranchMng_dataset.setFieldReadOnly("brhNo", false);
}


function btnSave_postSubmit(button) {
	BranchMng_dataset.flushData(BranchMng_dataset.pageIndex);
	var retParam = button.returnParam;
	if(retParam) {
		var resultMsg = retParam.resultMsg;
		if(resultMsg && "" != resultMsg) {
			alert(resultMsg);
		}
	}
}

function btnAdd_needCheck(button) {
	BranchMng_dataset.setFieldReadOnly("brhNo", false);
	setFocus("group1", "brhNo");
	if(!BranchMng_dataset.record) {
		return false;
	}
	return true;
}

function btnDel_onClickCheck(button) {
	if(BranchMng_dataset.record && confirm("是否删除当前记录？")) {
		return true;
	}
	return false;
}


function brhClass_DropDown_onSelect(dropDown,record,editor) {
	var v_brhClass= record.getValue("brhClass");
	if(v_brhClass==1){
		alert("机构级别不应该为省联社！");
		return false;
	}
	BranchMng_dataset.setValue2("upBrhId","");
	return true;
}

/**
 * 联动归属上级机构 
 * 县联社的归属上级机构只能是省联社
 * 网点的归属上级机构只能是县联社
 */
function upBrhId_DropDown_beforeOpen(dropDown){
	var brhClass=BranchMng_dataset.getValue("brhClass");
	BranchList_DropDownDataset.setParameter("brhClass",brhClass-1);
	upBrhId_DropDown.cached=false;
}

/**
 *机构数据过大，生成树耗系统资源
function btnTree_onClick(button) {
	var vId = BranchMng_dataset.getValue("id");
	if(!isNaN(vId) && "" != vId) {
		var paramMap = new Map();
		paramMap.put("brhId", vId);
		loadPageWindows("userWin", "机构树", "/fpages/sysmng/ftl/BranchTree.ftl", paramMap, "winZone", 500, 400);
	}else {
		alert("当前记录未保存或数据为空，不能查看机构树！");
	}
}
*/

//检查机构号是否唯一
function editor_brhNo_onUpdate(editor){
	var brhNo = editor.value;
	var record = BranchMng_dataset.find(["brhNo"],[brhNo]);
	if(record){
		alert("机构号已存在！");
		return true;
	}else{
		return checkDataIsOnly(editor,"brhNo");
	}
}

function checkDataIsOnly(editor,editorTpye) {
	var rtn = false;
	dwr.engine.setAsync(false);
	PrivateAction.isHaveBranch(editor.value,editorTpye,function checkResult(data) {
		if(data != 0) {
			if(editorTpye=="brhNo"){
				alert("机构号已存在！");
			}
			rtn = true;
		}
	});
	dwr.engine.setAsync(true);
	return rtn;
}
function datatable1_ondblClick() {
	//btnTree.click();
}

</script>
</@CommonQueryMacro.page>
