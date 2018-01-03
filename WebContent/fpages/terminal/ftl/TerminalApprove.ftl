<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign globalinfo = statics["com.huateng.ebank.common.GlobalInfo"].getCurrentInstanceWithoutException()>
<@CommonQueryMacro.page title="÷’∂À…Û∫À">
<script language="javascript" src="${contextPath}/js/extCommon.js"></script>
<script language="javascript" src="${contextPath}/js/xmlUtil.js"></script>
<script language="javascript" src="${contextPath}/js/treeNode.js"></script>
<script language="javascript" src="${contextPath}/js/treeBuilder.js"></script>
<script language="javascript" src="${contextPath}/dwr/interface/PrivateAction.js"></script>
<@CommonQueryMacro.CommonQuery id="TerminalApprove" init="true" navigate="true" submitMode="allchange">
<table align="center">
	<tr>
    	<td valign="top">
       		<@CommonQueryMacro.Interface id="intface" label="«Î ‰»Î≤È—ØÃıº˛" colNm="4" showButton="true" />
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
	      					<@CommonQueryMacro.Group id="group1" label="÷’∂À–≈œ¢" fieldStr="terminalCode,termType,brno,address,commAddr,termVendorNm,startDate,cupOrgan,ipAddress,termSt,chkStatus,chkDsc" />
	      				</td>
	      			</tr>
	      			<tr>
	      				<td align="center">
	      					<@CommonQueryMacro.Button id="btnPass"/>&nbsp;&nbsp;
	      					<@CommonQueryMacro.Button id="btnFail"/>
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
	setReadOnlyForEmptyDataset(TerminalApprove_dataset);
	/** …Ë÷√“≥√Ê≥ı º∫ÛµƒΩπµ„ */
	
	clearHisInputData(TerminalApprove_interface_dataset);
	clearHisInputData(TerminalApprove_dataset);
</script>

<script language="JavaScript">
function TerminalApprove_dataset_afterScroll(dataset) {
	setReadOnlyForEmptyDataset(TerminalApprove_dataset);
	TerminalApprove_dataset.setFieldReadOnly("chkDsc", false);
	TerminalApprove_dataset.setFieldReadOnly("brno", true);
	TerminalApprove_dataset.setFieldReadOnly("address", true);
	TerminalApprove_dataset.setFieldReadOnly("commAddr", true);
	TerminalApprove_dataset.setFieldReadOnly("termVendorNm", true);
	TerminalApprove_dataset.setFieldReadOnly("startDate", true);
	TerminalApprove_dataset.setFieldReadOnly("cupOrgan", true);
	TerminalApprove_dataset.setFieldReadOnly("ipAddress", true);
}

function btnPass_needCheck(button) {
	return false;
}

function btnPass_onClickCheck(button) {
	var vTerminalCode = TerminalApprove_dataset.getValue("terminalCode");
	var vChkDsc = TerminalApprove_dataset.getValue("chkDsc");
	TerminalApprove_dataset.setParameter("terminalCode", vTerminalCode);
	TerminalApprove_dataset.setParameter("type", "4");
	TerminalApprove_dataset.setParameter("chkDsc", vChkDsc);
	return true;
}

function btnPass_postSubmit(button) {
	TerminalApprove_dataset.flushData(TerminalApprove_dataset.pageIndex);
	var retParam = button.returnParam;
	if(retParam) {
		var resultMsg = retParam.resultMsg;
		if(resultMsg && "" != resultMsg) {
			alert(resultMsg);
		}
	}
}

function btnFail_needCheck(button) {
	return false;
}

function btnFail_onClickCheck(button) {
	var vChkDsc = TerminalApprove_dataset.getValue("chkDsc");
	if(""==vChkDsc.trim()){
		alert("…Û∫Àæ‹æ¯£¨«Î ‰»Îæ‹æ¯√Ë ˆ£°", "≤Ÿ◊˜¥ÌŒÛ", "error");
		return false;
	}
	var vTerminalCode = TerminalApprove_dataset.getValue("terminalCode");
	var vChkDsc = TerminalApprove_dataset.getValue("chkDsc");
	TerminalApprove_dataset.setParameter("terminalCode", vTerminalCode);
	TerminalApprove_dataset.setParameter("type", "8");
	TerminalApprove_dataset.setParameter("chkDsc", vChkDsc);
	return true;
}

function btnFail_postSubmit(button) {
	TerminalApprove_dataset.flushData(TerminalApprove_dataset.pageIndex);
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
