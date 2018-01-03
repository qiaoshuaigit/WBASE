<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="用户在线监控">
<@CommonQueryMacro.CommonQueryTab id="YW_NAVIGATE" navigate="false" currentTab="oprOnlineMonitor">
<table  width="100%">
	<tr>
		<td>主页 > 系统管理 > 用户在线监控</td>
	</tr>
	<tr>
		<td width="100%"><hr /></td>
	</tr>
</table>
<table align="center">
<tr>
<td>
<@CommonQueryMacro.CommonQuery id="LogInSwitch" init="true" navigate="false" submitMode="allchange">
<table align="center">
    <tr>
    	 <td valign="top">
             <@CommonQueryMacro.Group id="group" showGroupLine="true" label="登录状态" colNm="2" fieldStr="paramValue"/>
         </td>
    </tr>
    <tr>
        <td align="center">
        	<@CommonQueryMacro.Button id="btnLogInSwitchSave"/>&nbsp;&nbsp;
             <@CommonQueryMacro.Button id="btnLogInSwitchRefresh"/>
        </td>
    </tr>
</table>
</@CommonQueryMacro.CommonQuery>
</td>
</tr>
<tr>
<td>
<@CommonQueryMacro.CommonQuery id="OprOnline" init="false" navigate="false" submitMode="allchange">
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
            <@CommonQueryMacro.DataTable id="datatable" readonly="true" fieldStr="brhNoName[400],oprOnlineCount[100]"/>
        </td>
    </tr>
</table>
</@CommonQueryMacro.CommonQuery>
</td>
</tr>
</table>
<script language="JavaScript">
function btnLogInSwitchRefresh_onClick(button) {
	LogInSwitch_dataset.flushData(LogInSwitch_dataset.pageIndex);
}
function btnLogInSwitchSave_postSubmit(button) {
	LogInSwitch_dataset.flushData(LogInSwitch_dataset.pageIndex);
	var retParam = button.returnParam;
	if(retParam) {
		var resultMsg = retParam.resultMsg;
		if(resultMsg && "" != resultMsg) {
			alert(resultMsg);
		}
	}
}

</script>
</@CommonQueryMacro.CommonQueryTab>

</@CommonQueryMacro.page>
