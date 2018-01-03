<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="÷ÿ÷√√‹¬Î">
<#assign oprId = RequestParameters["oprId"]?default("0")>
<#assign oprNo = RequestParameters["oprNo"]?default("")>
<#assign userName = decodeStr(RequestParameters["userName"]?default(""))>
<#assign brhNo = RequestParameters["brhNo"]?default("")>
<#assign brhName = decodeStr(RequestParameters["brhName"]?default(""))>
<@CommonQueryMacro.CommonQuery id="ResetPasswd" init="false" insertOnEmpty="true" navigate="false" submitMode="all">
<table align="center">
    <tr><td>
		<table>
		<tr>
	      	<td valign="top">
	      		<table style="margin-top: 8px;">
	      			<tr>
	      				<td>
	      					<@CommonQueryMacro.Group id="group1" label="÷ÿ÷√√‹¬Î" showGroupLine="false" fieldStr="oprNo,userName,brhName,newPasswd1,newPasswd2" colNm="2" />
	      				</td>
	      			</tr>
	      			<tr>
	      				<td align="center">
	      					<@CommonQueryMacro.Button id="btnSave"/>&nbsp;&nbsp;
	      					<@CommonQueryMacro.Button id="btnCancel"/>
	      				</td>
	      			</tr>
	      		</table>
	      	</td>
	    </tr>
		</table>
    </td></tr>
</table>
</@CommonQueryMacro.CommonQuery>
<script language="JavaScript" for="window" event="onload">
	win.setText("÷ÿ÷√√‹¬Î");
	ResetPasswd_dataset.setValue("id", "${oprId}");
	ResetPasswd_dataset.setValue("oprNo", "${oprNo}");
	ResetPasswd_dataset.setValue("userName", "${userName}");
	ResetPasswd_dataset.setValue("brhNo", "${brhNo}");
	ResetPasswd_dataset.setValue("brhName", "${brhName}");
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

function btnCancel_onClick(button) {
	unloadPageWindows("userWin");
}
</script>
</@CommonQueryMacro.page>
