<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign globalinfo = statics["com.huateng.ebank.common.GlobalInfo"].getCurrentInstanceWithoutException()>
<@CommonQueryMacro.page title="�ն˱���">
<script language="javascript" src="${contextPath}/js/extCommon.js"></script>
<script language="javascript" src="${contextPath}/js/xmlUtil.js"></script>
<script language="javascript" src="${contextPath}/js/treeNode.js"></script>
<script language="javascript" src="${contextPath}/js/treeBuilder.js"></script>
<script language="javascript" src="${contextPath}/dwr/interface/PrivateAction.js"></script>
<@CommonQueryMacro.CommonQuery id="TerminalReport" init="true" navigate="true" submitMode="allchange">
<table align="center">
	<tr>
    	<td valign="top">
       		<@CommonQueryMacro.Interface id="intface" label="�������ѯ����" colNm="4" showButton="true" />
		</td>
    </tr>
</table>
<table align="center">
    <tr>
    	<td>
			<table>
				<tr>
			      	<td valign="top">
			      		<@CommonQueryMacro.PagePilot id="ddresult" maxpagelink="3"/>
			      		<@CommonQueryMacro.DataTable id="datatable1" width="1400" fieldStr="terminalCode[100],termType[50],brno[240],address,commAddr,termVendorNm,startDate,cupOrgan[130],ipAddress,termSt[100],chkStatus[140]" readonly="true"/>
			      	</td>
			    </tr>
			    <tr>
	                <td align="center">
	                	<@CommonQueryMacro.FileDownload id="teminalReportDown" name="�ն���Ϣ����" title="�ն���Ϣ����" />
	            	</td>
                </tr>
			</table>
    	</td>
    </tr>

</table>
</@CommonQueryMacro.CommonQuery>
<@CommonQueryMacro.WindowElement/>
<script language="JavaScript" for="window" event="onload">
	clearHisInputData(TerminalReport_interface_dataset);
</script>
<script language="javascript" src="${contextPath}/templets/lib/inquery.js"></script>
<script language="JavaScript">
function teminalReportDown_getPrimaryKey() {
	var brhNo=TerminalReport_interface_dataset.getString("brhNo");
	var termStWhere=TerminalReport_interface_dataset.getString("termStWhere");
	var param="";
	if(brhNo!=""){
		param=param+",brhNo="+brhNo+"";
	}
	if(termStWhere!=""){
		param=param+",termStWhere="+termStWhere+"";
	}
	
	return param;
}
function teminalReportDown_getBussType() {
	return "002";
}
</script>
</@CommonQueryMacro.page>
