<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign globalinfo = statics["com.huateng.ebank.common.GlobalInfo"].getCurrentInstanceWithoutException()>
<@CommonQueryMacro.page title="�ն˹���">
<script language="javascript" src="${contextPath}/js/extCommon.js"></script>
<script language="javascript" src="${contextPath}/js/xmlUtil.js"></script>
<script language="javascript" src="${contextPath}/js/treeNode.js"></script>
<script language="javascript" src="${contextPath}/js/treeBuilder.js"></script>
<script language="javascript" src="${contextPath}/dwr/interface/PrivateAction.js"></script>
<@CommonQueryMacro.CommonQuery id="TerminalMng" init="true" navigate="true" submitMode="allchange">
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
	      		<@CommonQueryMacro.DataTable id="datatable1" fieldStr="terminalCode[80],brno[240]" readonly="true"/>
	      	</td>
	      	<td valign="top">
	      		<table style="margin-top: 8px;">
	      			<tr>
	      				<td>
	      					<@CommonQueryMacro.Group id="group1" label="�ն���Ϣ" fieldStr="terminalCode,termType,brno,address,commAddr,termVendorNm,startDate,cupOrgan,ipAddress,termSt,chkStatus,chkDsc" />
	      				</td>
	      			</tr>
	      			<#if globalinfo.getBranchNo() != "019801" >
	      			<tr>
	      				<td align="center">
	      					<@CommonQueryMacro.Button id="btnAdd"/>&nbsp;&nbsp;
	      					<@CommonQueryMacro.Button id="btnSave"/>&nbsp;&nbsp;
	      					<@CommonQueryMacro.Button id="btnUp"/>&nbsp;&nbsp;
	      					<@CommonQueryMacro.Button id="btnDwon"/>
	      				</td>
	      			</tr>
	      			</#if>
	      		</table>
	      	</td>
	    </tr>
		</table>
    </td></tr>
</table>
</@CommonQueryMacro.CommonQuery>
<@CommonQueryMacro.WindowElement/>
<script language="JavaScript" for="window" event="onload">
	setReadOnlyForEmptyDataset(TerminalMng_dataset);
	/** ����ҳ���ʼ��Ľ��� */
	
	clearHisInputData(TerminalMng_interface_dataset);
	clearHisInputData(TerminalMng_dataset);
</script>

<script language="JavaScript">
function TerminalMng_dataset_afterScroll(dataset) {
	setReadOnlyForEmptyDataset(TerminalMng_dataset);
	<#if globalinfo.getBranchNo() != "019801" >
	var btnSave = document.getElementById("btnSave");
	var btnUp = document.getElementById("btnUp");
	var btnDwon = document.getElementById("btnDwon");
	
	btnSave.disabled=true;
	btnUp.disabled=true;
	btnDwon.disabled=true;
	var termSt = TerminalMng_dataset.getValue("termSt");
	var chkStatus = TerminalMng_dataset.getValue("chkStatus");
	var terminalCode = TerminalMng_dataset.getValue("terminalCode");
	if(("1"==termSt && "5"==chkStatus) 
		|| ("3"==termSt && "3"==chkStatus && "" == terminalCode) 
		|| ("3"==termSt && "7"==chkStatus)
		|| ("3"==termSt && "11"==chkStatus)
		|| ("4"==termSt && "12"==chkStatus)){//��ͣ�á��������������������ܾ���״̬  ���޸����ݱ��水�����á�
		btnSave.disabled=false;
	}
	else if("2"==termSt && "6"==chkStatus){//������ ��ͣ��
		btnDwon.disabled=false;
	}
	else if(("1"==termSt && "5"==chkStatus) 
			|| ("3"==termSt && "7"==chkStatus)){//����������ͣ�� ������
		btnUp.disabled=false;
	}
	</#if>
}


function btnAdd_onClick(button) {
	TerminalMng_dataset.setValue2("termSt", 3);
	TerminalMng_dataset.setValue2("chkStatus", 3);
	<#if globalinfo.getBranchNo() != "019801" >
	document.getElementById("btnSave").disabled=false;
	</#if>
}


function btnSave_postSubmit(button) {
	TerminalMng_dataset.flushData(TerminalMng_dataset.pageIndex);
	var retParam = button.returnParam;
	if(retParam) {
		var resultMsg = retParam.resultMsg;
		if(resultMsg && "" != resultMsg) {
			alert(resultMsg);
		}
	}
}


function brno_DropDown_onSelect(dropDown,record,editor) {
	var v_cupOrgan= record.getValue("organ4Cup");
	
	TerminalMng_dataset.setValue2("cupOrgan",v_cupOrgan);
	return true;
}

function btnAdd_needCheck(button) {
	//TerminalMng_dataset.setFieldReadOnly("brhNo", false);
	//setFocus("group1", "brhNo");
	//if(!TerminalMng_dataset.record) {
	//	return false;
	//}
	//return true;
}

function btnDel_onClickCheck(button) {
	if(TerminalMng_dataset.record && confirm("�Ƿ�ɾ����ǰ��¼��")) {
		return true;
	}
	return false;
}


function btnUp_needCheck(button) {
	return false;
}

function btnUp_onClickCheck(button) {
	var vTerminalCode = TerminalMng_dataset.getValue("terminalCode");
	if(0 != vTerminalCode && "" != vTerminalCode) {
		TerminalMng_dataset.setParameter("terminalCode", vTerminalCode);
		TerminalMng_dataset.setParameter("type", "2");
		return true;
	}
	alert("���ȱ���������ã�", "��������", "error");
	return false;
}

function btnUp_postSubmit(button) {
	TerminalMng_dataset.flushData(TerminalMng_dataset.pageIndex);
	var retParam = button.returnParam;
	if(retParam) {
		var resultMsg = retParam.resultMsg;
		if(resultMsg && "" != resultMsg) {
			alert(resultMsg);
		}
	}
}

function btnDwon_needCheck(button) {
	return false;
}
function btnDwon_onClickCheck(button) {
	var vTerminalCode = TerminalMng_dataset.getValue("terminalCode");
	if(0 != vTerminalCode && "" != vTerminalCode) {
		TerminalMng_dataset.setParameter("terminalCode", vTerminalCode);
		TerminalMng_dataset.setParameter("type", "1");
		return true;
	}
	alert("���ȱ������ͣ�ã�", "��������", "error");
	return false;
}

function btnDwon_postSubmit(button) {
	TerminalMng_dataset.flushData(TerminalMng_dataset.pageIndex);
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
