<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign globalinfo = statics["com.huateng.ebank.common.GlobalInfo"].getCurrentInstanceWithoutException()>
<@CommonQueryMacro.page title="��������">
<script language="javascript" src="${contextPath}/js/extCommon.js"></script>
<script language="javascript" src="${contextPath}/js/xmlUtil.js"></script>
<script language="javascript" src="${contextPath}/js/treeNode.js"></script>
<script language="javascript" src="${contextPath}/js/treeBuilder.js"></script>
<script language="javascript" src="${contextPath}/dwr/interface/PrivateAction.js"></script>
<@CommonQueryMacro.CommonQuery id="BranchMng" init="false" navigate="true" submitMode="allchange">
<table align="center">
	<tr>
    	<td valign="top">
       		<@CommonQueryMacro.Interface id="intface" label="�������ѯ����" colNm="4" showButton="true" />
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
	      					<@CommonQueryMacro.Group id="group1" label="������Ϣ" fieldStr="brhNo,brhName,brhClass,upBrhId,contacts,teleNo,cityCd,address,postno,status" />
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
	/** ����ҳ���ʼ��Ľ��� */
	setFocus("BranchMng_interface", "brhNo");
	
	clearHisInputData(BranchMng_interface_dataset);
	clearHisInputData(BranchMng_dataset);
</script>

<script language="JavaScript">
function BranchMng_dataset_afterScroll(dataset) {
	setReadOnlyForEmptyDataset(BranchMng_dataset);
	var vBrhNo = BranchMng_dataset.getValue("brhNo");
	//����¼�û��Ļ�����ѡ�л�����ͬʱ����Ȩ���޸Ļ����š�����š��������𡢹����ϼ�������״̬ ������¼�û��Ļ���Ϊѡ�л������ϼ�ʱ�����޸Ļ������𡢹����ϼ�������״̬ 
	
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
	if(BranchMng_dataset.record && confirm("�Ƿ�ɾ����ǰ��¼��")) {
		return true;
	}
	return false;
}


function brhClass_DropDown_onSelect(dropDown,record,editor) {
	var v_brhClass= record.getValue("brhClass");
	if(v_brhClass==1){
		alert("��������Ӧ��Ϊʡ���磡");
		return false;
	}
	BranchMng_dataset.setValue2("upBrhId","");
	return true;
}

/**
 * ���������ϼ����� 
 * ������Ĺ����ϼ�����ֻ����ʡ����
 * ����Ĺ����ϼ�����ֻ����������
 */
function upBrhId_DropDown_beforeOpen(dropDown){
	var brhClass=BranchMng_dataset.getValue("brhClass");
	BranchList_DropDownDataset.setParameter("brhClass",brhClass-1);
	upBrhId_DropDown.cached=false;
}

/**
 *�������ݹ�����������ϵͳ��Դ
function btnTree_onClick(button) {
	var vId = BranchMng_dataset.getValue("id");
	if(!isNaN(vId) && "" != vId) {
		var paramMap = new Map();
		paramMap.put("brhId", vId);
		loadPageWindows("userWin", "������", "/fpages/sysmng/ftl/BranchTree.ftl", paramMap, "winZone", 500, 400);
	}else {
		alert("��ǰ��¼δ���������Ϊ�գ����ܲ鿴��������");
	}
}
*/

//���������Ƿ�Ψһ
function editor_brhNo_onUpdate(editor){
	var brhNo = editor.value;
	var record = BranchMng_dataset.find(["brhNo"],[brhNo]);
	if(record){
		alert("�������Ѵ��ڣ�");
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
				alert("�������Ѵ��ڣ�");
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
