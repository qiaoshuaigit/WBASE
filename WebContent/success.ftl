<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="">
<link rel="stylesheet" type="text/css" href="css/style.css">
<#assign TIPINFO = decodeStr(RequestParameters["TIPINFO"]?default(""))>
<table align="center" valign="middle" width="700px" border="0" cellspacing="0" cellpadding="0" style="margin-top: 60px;">
  <tr>
    <td width="56px"><img src="./images/info_f1.gif" width="56px" height="44px"></td>
    <td width="618px" background="./images/info_f3.gif"><img src="./images/info_f2.gif" width="165px" height="44px"/></td>
    <td width="26px"><img src="./images/info_f4.gif" width="26px" height="44px"></td>
  </tr>
  <tr>
    <td width="56px" height="71px" valign="top" background="./images/info_f6.gif"><img src="./images/info_f5.gif" width="56px" height="30px"></td>
    <td style="background-color:#FCFDFD;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
    	<tr id="msgTitle" style="display:none">
    		<td class="tiptitle">您的操作已成功！
    	</tr>
    	<tr>
    		<td class="tiptitle">
    		<div id="msgDiv" style="margin-left: 20px;">
    		</div>
    		</td>
    	</tr>
    </table></td>
    <td width="26px" background="./images/info_f11.gif">&nbsp;</td>
  </tr>
  <tr>
    <td><img src="./images/info_f7.gif" width="56px" height="36px"></td>
    <td background="./images/info_f9.gif"><img src="./images/info_f8.gif" width="77px" height="36px"></td>
    <td width="26px"><img src="./images/info_f10.gif" width="25px" height="35px"></td>
  </tr>
</table>
<@CommonQueryMacro.CommonQuery id="success" init="true" navigate="false" show="false" />
<script language="javascript">
function success_dataset_flushDataPost(dataset) {
	var message = success_dataset.getValue("message");
	var _msgDiv = document.getElementById("msgDiv");
	if(message && "" != message) {
		document.getElementById("msgTitle").style.display="none";
    	msgDiv.innerHTML = message.replace(/\n/g, "<br />").replace(/\^/g, "");
	}
	else {
		document.getElementById("msgTitle").style.display="";
	}
}
</script>
</@CommonQueryMacro.page>
