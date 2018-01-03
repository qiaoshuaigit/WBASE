<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="����ͳ��">
<@CommonQueryMacro.CommonQueryTab id="YW_NAVIGATE" navigate="false" currentTab="txnCountTab">
<@CommonQueryMacro.CommonQuery id="TxnCountQuery" init="false" navigate="true" submitMode="allchange">
<table align="center">
	<tr><td>
		<table align="center">
			<tr>
    			<td valign="top">
       				<@CommonQueryMacro.Interface id="intface" label="�������ѯ����" colNm=4 showButton="true" />
				</td>
    		</tr>
		</table>
	</td></tr>
    <tr><td>
		<table>
		<tr>
	      	<td valign="top">
	      		<@CommonQueryMacro.DataTable id="datatable1" fieldStr="tradChl[160],txnType[160],txnCount[160],txnCgl[160]" readonly="false"/>
	      	</td>
	    </tr>
		</table>
    </td></tr>
</table>
</@CommonQueryMacro.CommonQuery>
<#assign globalinfo = statics["com.huateng.ebank.common.GlobalInfo"].getCurrentInstanceWithoutException()>
<script language="JavaScript" for="window" event="onload">
	setReadOnlyForEmptyDataset(TxnCountQuery_dataset);
	/** ����ҳ���ʼ��Ľ��� */
	//setFocus("TxnCountQuery_interface", "startDate");
	<#if globalinfo?exists>
		var todayDate = new Date("${globalinfo.getTxdate()?string("yyyy/MM/dd")}");//��ǰ�������
	<#else>
		var todayDate = new Date();//��ǰ�������
	</#if>
	var startDate = new Date(todayDate.getFullYear(),todayDate.getMonth(),todayDate.getDate()-7);
	var endDate = new Date(todayDate.getFullYear(),todayDate.getMonth(),todayDate.getDate()-1);
	TxnCountQuery_interface_dataset.setValue2("startDate", new Date(startDate));
	TxnCountQuery_interface_dataset.setValue2("endDate", new Date(endDate));
	clearHisInputData(TxnCountQuery_interface_dataset);
</script>

<script language="JavaScript">
//��ѯǰ�ļ��
function TxnCountQuery_interface_dataset_btnSubmit_onClickCheck(button){
	var startDate = TxnCountQuery_interface_dataset.getValue("startDate");
	var endDate = TxnCountQuery_interface_dataset.getValue("endDate");
	if(startDate > endDate) {
		alert("��ʼ���ڲ����Դ��ڽ�������!");
		return false;
	}
	if((endDate.getTime()-startDate.getTime()) > 31*24*60*60*1000) {
		alert("��ѯ���Χ���ܳ���31��!");
		return false;
	}
	return true;
}
</script>
</@CommonQueryMacro.CommonQueryTab>
</@CommonQueryMacro.page>
