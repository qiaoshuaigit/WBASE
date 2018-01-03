<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="数据库连接池监控">
<@CommonQueryMacro.CommonQueryTab id="YW_NAVIGATE" navigate="false" currentTab="dataSourceMonitorTab">
<table id="_mainWorkTable" align="left" width="100%">
    <tr>
        <td>主页 > 系统管理 > 数据库连接池监控</td>
    </tr>
    <tr>
        <td width="100%"><hr /></td>
    </tr>
    <tr>
        <td width="700px" height="600px">
<iframe id="dsm" src="${contextPath}/proxoolAdmin" width="700px" height="600px"
	style="border:0px #ffffff solid" frameborder='0' scrolling='no' marginheight='0' marginwidth='0' >
</iframe>
        </td>
    </tr>
</table>

</@CommonQueryMacro.CommonQueryTab>
</@CommonQueryMacro.page>
