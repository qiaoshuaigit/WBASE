<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="ESB-MQ联通性检查">
<@CommonQueryMacro.CommonQueryTab id="YW_NAVIGATE" navigate="false" currentTab="esbCheckTab">
<@CommonQueryMacro.CommonQuery id="EsbMqCheck" init="true" navigate="true" submitMode="current">
<table align="center">
    <tr>
    	 <td valign="top">
             <@CommonQueryMacro.DataTable id="datatable" fieldStr="ip,port,status" />
         </td>
    </tr>
    <tr>
        <td align="center">
             <@CommonQueryMacro.Button id="refreshBtn"/>
        </td>
    </tr>
</table>
</@CommonQueryMacro.CommonQuery>
<script language="JavaScript">
function refreshBtn_onClick(button) {
	EsbMqCheck_dataset.flushData(EsbMqCheck_dataset.pageIndex);
}
</script>
</@CommonQueryMacro.CommonQueryTab>
</@CommonQueryMacro.page>
