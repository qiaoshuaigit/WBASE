<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="JVM内存监控">
<@CommonQueryMacro.CommonQueryTab id="YW_NAVIGATE" navigate="false" currentTab="jvmMonitorTab">
<table id="_mainWorkTable" align="center" width="100%">
    <tr>
        <td>主页 > 系统管理 > JVM内存监控</td>
    </tr>
    <tr>
        <td width="100%"><hr /></td>
    </tr>
    <tr>
        <td id="dsmtd" width="900px" height="600px">
<iframe id="dsmframe" src="${contextPath}/pages/admin/JvmMonitor.jsp" width="900px" height="600px"
	style="border:0px #ffffff solid" frameborder='0' scrolling='auto' marginheight='0' marginwidth='0' >
</iframe>
        </td>
    </tr>
</table>

</@CommonQueryMacro.CommonQueryTab>
<script language="JavaScript">
var _dsmtd = document.getElementById("dsmtd");
var _dsmframe = document.getElementById("dsmframe");
_dsmtd.style.width = "" + (document.body.clientWidth - 100) + "px";
_dsmframe.style.width = "" + (document.body.clientWidth - 100) + "px";
</script>
</@CommonQueryMacro.page>
