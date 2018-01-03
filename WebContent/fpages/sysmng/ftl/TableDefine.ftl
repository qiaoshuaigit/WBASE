<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="���ݿ����">
<@CommonQueryMacro.CommonQueryTab id="YW_NAVIGATE" navigate="false" currentTab="tableDefine">
<table align="left" width="100%">
	<tr>
        <td colspan='2'>��ҳ > ϵͳ���� > ���ݿ����</td>
    </tr>
    <tr>
        <td colspan='2' width="100%"><hr /></td>
    </tr>
    <tr>
    	<td align="center" valign="top">
    		<@CommonQueryMacro.CommonQuery id="TableDefine" init="false" navigate="false" submitMode="allchange">
    		<table align="right" valign="top">
    		<tr>
    		<td valign="top">
    		<@CommonQueryMacro.Interface id="intface" label="�������ѯ����" colNm="4" btnNm="��ѯ"/>
    		</td>
    		</tr>
    		<tr>
    		<td>
    		<@CommonQueryMacro.DataTable id="datatable1" readonly="true" fieldStr="tabName[100],tabDesc[160]"/>
    		</td>
    		</tr>
    		</table>
    		</@CommonQueryMacro.CommonQuery>
    	</td>
    	<td align="left" valign="top">
    		<table valign="top" style="margin-top: 70px;">
    		<tr>
    		<td valign="top">
    		<p>&nbsp;&nbsp;˫���������嵥��ѯ����</p>
    		<@CommonQueryMacro.CommonQuery id="TableDefineInfo" init="false" navigate="false" submitMode="allchange">
    		<@CommonQueryMacro.DataTable id="datatable2" readonly="true" fieldStr="seqNo[40],colName[100],dataType[80],colSize[40],colDesc[200],nullAble[100]"/>
    		</@CommonQueryMacro.CommonQuery>
    		</td>
    		</tr>
    		</table>
    	</td>
    </tr>
</table>

<script language="JavaScript">
function datatable1_ondblClick() {
	var tabName = TableDefine_dataset.getString("tabName");
	if(tabName && "" != tabName) {
		TableDefineInfo_dataset.setParameter("iTabName", tabName);
		TableDefineInfo_dataset.flushData(1);
	}
}
</script>
</@CommonQueryMacro.CommonQueryTab>
</@CommonQueryMacro.page>
