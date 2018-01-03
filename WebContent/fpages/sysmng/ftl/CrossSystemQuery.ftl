<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="流水信息">
<script language="javascript" src="${contextPath}/js/extCommon.js"></script>
<script language="javascript" src="${contextPath}/js/xmlUtil.js"></script>
<script language="javascript" src="${contextPath}/js/treeNode.js"></script>
<script language="javascript" src="${contextPath}/js/treeBuilder.js"></script>
<script language="javascript" src="${contextPath}/dwr/interface/PrivateAction.js"></script>
<@CommonQueryMacro.CommonQuery id="CrossSystemQuery" init="true" navigate="true" submitMode="allchange">

<table align="center">
    <tr><td>
		<table>
		<tr>
	      
	      	<td valign="top">
	      		<table style="margin-top: 8px;">
	      			<tr>
	      				<td>
	      					<@CommonQueryMacro.Group id="group1" label="流水信息" fieldStr="zwrq,zhqzlsh,jyqd,workDate,entrustdDate,taxorgCode,trano,msgno,entrustDate2,trano2,zhqzlsh2,chkDate,chkAcctord,handleType,treCode,payeebankNo,payeeOrgCode,payeeAcct,payeeName,paybkCode,payopbkCode,protocolNo,payAcct,handOrgName,dfzh,jyje,taxvouNo,dqdh,jgdh,jygy,zddh,jyrq,jysj,zjrq,zjsj,zjlsh,zjxym,sxf,zhye,xym,xyxx,jyzt,dzbz,qsbz,kzbz,taxpayCode,byzd" />
	      				</td>
	      			</tr>
	      		</table>
	      	</td>
	    </tr>
		</table>
    </td></tr>
</table>
</@CommonQueryMacro.CommonQuery>
<@CommonQueryMacro.WindowElement/>

<script language="JavaScript" for="window" event="onload">
	setReadOnlyForEmptyDataset(CrossSystemQuery_dataset);
	/** 设置页面初始后的焦点 */
	setFocus("CrossSystemQuery_interface", "CrossSystemQueryName");
</script>

</@CommonQueryMacro.page>
