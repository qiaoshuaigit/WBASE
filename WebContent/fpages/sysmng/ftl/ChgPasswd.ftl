<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="�޸�����">
<@CommonQueryMacro.CommonQuery id="ChgPasswd" init="true" navigate="false" submitMode="all">
<table align="center">
    <tr><td>
		<table>
		<tr>
	      	<td valign="top">
	      		<table style="margin-top: 8px;">
	      			<tr>
	      				<td>
	      					<@CommonQueryMacro.Group id="group1" label="�޸�����" showGroupLine="false" fieldStr="oprNo,userName,oldPasswd,newPasswd1,newPasswd2" colNm="2" />
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
	/** ����ҳ���ʼ��Ľ��� */
	setFocus("group1", "oldPasswd");
	win.setText("�޸�����");
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

function btnCancel_onClick(button) {
	unloadPageWindows("ShowDialog");
}
</script>
</@CommonQueryMacro.page>
