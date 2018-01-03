<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="ParameterReload">
<@CommonQueryMacro.CommonQuery id="ReloadList" init="true" navigate="false" submitMode="selected">
<table align="center">
	<tr>
	   	<td valign="top">
	   		<@CommonQueryMacro.PagePilot id="ddresult" maxpagelink="3"/>
	    	<@CommonQueryMacro.DataTable id="datatable1" fieldStr="select[40],magicId[60],paramValue[180]" readonly="false"/>
	    	<table align="center">
	    		<tr>
	    			<td>
	    				<@CommonQueryMacro.Button id="btnSave"/>
	    			</td>
	    		</tr>
	    	</table>
	    </td>
	</tr>
</table>
</@CommonQueryMacro.CommonQuery>
<script language="JavaScript" for="window" event="onload">
	win.setText("ParameterReload");
	document.body.style.overflow = "hidden";
</script>

<script language="JavaScript">
function btnSave_postSubmit(button) {
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
