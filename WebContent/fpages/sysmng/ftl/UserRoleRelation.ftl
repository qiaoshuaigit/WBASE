<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="�����λ">
<#assign oprId = RequestParameters["oprId"]?default("0")>
<#assign userName = decodeStr(RequestParameters["userName"]?default(""))>
<@CommonQueryMacro.CommonQuery id="UserRoleRelation" init="true" navigate="false" submitMode="allchange">
<table align="center">
	<tr>
	   	<td nowrap>��[&nbsp;${userName}&nbsp;]�����λ��
	   		<#--
			<LABEL id="userNameForTitle"></LABEL>
			<script language="JavaScript">
				var userName = getDecodeStr("${userName}");
				document.getElementById("userNameForTitle").innerHTML = "��[&nbsp;" + userName + "&nbsp;]�����λ��";
			</script>
			-->
	   	</td>
	</tr>
	<tr>
	   	<td valign="top">
	    	<@CommonQueryMacro.DataTable id="datatable1" fieldStr="select[40],roleCode[60],roleName[180]" readonly="false"/>
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
<script language="JavaScript">
function btnSave_postSubmit(button) {
	UserRoleRelation_dataset.flushData(UserRoleRelation_dataset.pageIndex);
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
