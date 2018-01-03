<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="数据库设计">
<@CommonQueryMacro.CommonQueryTab id="YW_NAVIGATE" navigate="false" currentTab="tableDefine">
<table align="left" width="100%">
	<tr>
        <td colspan='2'>主页 > 系统管理 > 数据库设计</td>
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
    		<@CommonQueryMacro.Interface id="intface" label="请输入查询条件" colNm="4" btnNm="查询"/>
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
    		<p>&nbsp;&nbsp;双击左侧表名清单查询详情</p>
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
