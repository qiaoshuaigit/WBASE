<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="Á¬½Ó³Ø¼à¿Ø">
<@CommonQueryMacro.CommonQuery id="ProxoolMonitor" init="true" navigate="true" submitMode="allchange">
<table align="center">
    <tr>
		<td valign="top">
			<@CommonQueryMacro.DataTable id="datatable1" fieldStr="key[360],value[400]" width="800" height="460" hasFrame="true" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td align="center">
			<@CommonQueryMacro.Button id="btnMonitor"/>&nbsp;&nbsp;
			<@CommonQueryMacro.Button id="btnRefresh"/>
		</td>
	</tr>
</table>
</@CommonQueryMacro.CommonQuery>

<script language="JavaScript">
function btnRefresh_onClick() {
	ProxoolMonitor_dataset.flushData(1);
}
function btnMonitor_onClick() {
	path = _application_root + "/proxoolAdmin";
	window.open(path, "_self");
}
</script>
</@CommonQueryMacro.page>
