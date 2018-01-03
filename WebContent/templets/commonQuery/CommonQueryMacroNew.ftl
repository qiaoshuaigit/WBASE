<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<#--global value-->
<#global contextPath = contextPath>

<#--ͨ�ò�ѯͷģ��-->
<#macro CommonQuery>
<#assign CommonQueryConfig = statics["com.huateng.commquery.config.CommonQueryUtil"].getCommonQueryBean(CQId)>
<@CommonQueryMacro.page title=CommonQueryConfig.getAnyValue("title")>
<@CommonQueryMacro.CommonQueryForRequest>
	<#nested>
</@CommonQueryMacro.CommonQueryForRequest>
</@CommonQueryMacro.page>
</#macro>

<#--����PagePilot��-->
<#macro PagePilot id maxpagelink="9" showArrow="true">
<@CommonQueryMacro.PagePilot id=id maxpagelink=maxpagelink showArrow=showArrow/>
</#macro>

<#--����DataTable��-->
<#macro DataTable id fieldStr=CommonQueryConfig.toFieldString() width="" readonly="true">
<@CommonQueryMacro.DataTable id=id fieldStr=fieldStr width=width readonly=readonly/>
</#macro>

<#--����Group��-->
<#macro Group id label fieldStr colNm=4>
<@CommonQueryMacro.Group id=id label=label fieldStr=fieldStr colNm=colNm/>
</#macro>

<#--����Button��-->
<#macro Button id>
<@CommonQueryMacro.Button id=id/>
</#macro>

<#macro ButtonGroup>
<@CommonQueryMacro.ButtonGroup/>
</#macro>



