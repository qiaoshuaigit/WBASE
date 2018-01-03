<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="流水查询">
<@CommonQueryMacro.CommonQueryTab id="YW_NAVIGATE" navigate="false" currentTab="txnAccountLogQueryTab">
<@CommonQueryMacro.CommonQuery id="TxnAccountLogQuery" init="false" navigate="true" submitMode="allchange">
<table align="center">
    <tr>
        <td valign="top">
        <table align="center">
            <tr>
            <td>
            <@CommonQueryMacro.Interface id="intface" label="请输入查询条件" colNm="4" btnNm="查询"/>
            </td>
            </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <@CommonQueryMacro.PagePilot id="ddresult" maxpagelink="3"/>
            <@CommonQueryMacro.DataTable id="datatable" readonly="true" fieldStr="id,txnDesc,accsystemCode,taskId,missionId,batchId,stripId,serverSeqnb,status,excuteTime,misc"/>
        </td>
    </tr>
</table>
</@CommonQueryMacro.CommonQuery>
<script language="JavaScript" for="window" event="onload">
	clearHisInputData(TxnAccountLogQuery_interface_dataset);
</script>
<script language="javascript">
function datatable_id_onRefresh(cell, value, record) {
	var temp = "" + value;
	cell.innerHTML = temp.replace(/TIPS|APS|BCS|CBS/g, "");
}
</script>
</@CommonQueryMacro.CommonQueryTab>
</@CommonQueryMacro.page>
