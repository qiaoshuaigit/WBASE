<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign datasetid="${RequestParameters['datasetid']?default('')}">
<#assign fieldStr="${RequestParameters['fieldStr']?default('')}">
<#assign title="${RequestParameters['title']?default('')}">
<#assign CommonQueryConfig = statics["com.huateng.commquery.config.CommonQueryUtil"].getCommonQueryBean(datasetid)>
<#assign tablehead="">
<#assign readonly=true>
<#assign fields = fieldStr?split(',')>
<#assign fCount = fields?size>
<#assign translatedStr = "">
<#assign datatypeStr = "">
<#assign methodStr = "">
<#assign xpathStr = "">
<#list fields as fId>
       <#assign field = CommonQueryConfig.getField(fId)>
       <#assign fDesc = getFieldDesc(CommonQueryConfig.getId(),fId)>
       <#assign fwidth = field.getAnyValue("width")?default('')>
       <#assign translated = field.getAnyValue("translated")?default("")>
       <#assign method = field.getAnyValue("method")?default("None")>
       <#assign xpath = field.getAnyValue("xpath")?default("/")>
       <#assign datatype = field.getAnyValue("datatype")?default('string')>
       <#assign tablehead = tablehead + "\lth\g" + fDesc + "\l/th\g" >
       <#assign translatedStr = translatedStr+"$"+translated>
       <#assign datatypeStr = datatypeStr+"$"+datatype>
       <#assign methodStr = methodStr+"$"+method>
       <#assign xpathStr = xpathStr+"$"+xpath>
</#list>
<#assign translatedArr = translatedStr?split("$")>
<#assign datatypeArr = datatypeStr?split("$")>
<#assign methodArr = methodStr?split("$")>
<#assign xpathArr = xpathStr?split("$")>
<html>  
<head>
<meta http-equiv=Content-Type content="text/html; charset=GBK">
<style type="text/css">
	td,th {
		word-break: keep-all;
		white-space:nowrap;
	}
	.s {
		mso-number-format:'\@';
	}
</style>
</head>
<body>
<table id="table1" border=1 cellspacing="0" cellpadding="0" >
<thead>
	<#if title!=""><#assign decodeTitle=decodeStr("${title}")><tr><th colspan="${fCount}">${decodeTitle}</th></tr></#if>
	<tr>${tablehead}</tr>
</thead>
<tbody>
	<#if Session["print_dataset"]?exists>
		<#assign dataset = Session["print_dataset"] />
	<#list dataset as r>
	<tr>
		<#assign r1 = r><#if r?size==1><#assign r1 = r[0]></#if><#list fields as c><#assign translated = translatedArr[c_index+1]><#assign datatype = datatypeArr[c_index+1]><#assign xpath = xpathArr[c_index+1]><#assign method = methodArr[c_index+1]><#if xpath?starts_with("/")><#assign xpath = xpath?substring(1)></#if><#if xpath==""><#assign value = ""><#elseif xpath?contains("[")><#assign idx0 = xpath?index_of("[")><#assign key = xpath?substring(0,idx0)><#assign xpath = xpath?substring(idx0+1)><#assign idx1 = xpath?index_of("]")><#assign val = xpath?substring(0,idx1)><#assign value = statics["org.apache.commons.beanutils.BeanUtils"].getProperty(r1,key)[val]><#else><#assign xpath = xpath?replace("/",".")><#assign value = statics["org.apache.commons.beanutils.BeanUtils"].getNestedProperty(r1,xpath)?default("")></#if><td <#if datatype=="currency"||datatype=="double"||datatype=="float">align="right"<#elseif datatype="string">class=s</#if>><#if value!=""><#if method==""||method=="None"><#if translated==""><#if datatype=="string">${value}<#elseif datatype=="currency"||datatype=="double"||datatype=="float"><#if value?is_string><#assign value = statics["java.lang.Double"].parseDouble(value)></#if>${value?string(",##0.00")}<#elseif datatype=="date"><#assign value = value?date("yyyyMMdd")>${value?string("yyyy-MM-dd")}<#else>${value}</#if><#elseif translated?starts_with("LIST:")><#assign strInd = translated?index_of(value+",")><#if strInd==-1><#else><#assign len = (value+",")?length><#assign translated = translated?substring(strInd+len)><#assign endInd = translated?index_of(";")><#if endInd==-1>${translated}<#else><#assign v = translated?substring(0,endInd)>${v}</#if></#if><#elseif translated?starts_with("CQ:")>${value}<#elseif translated?starts_with("DATA_DIC")><#assign dicType = translated?substring(9)><#assign v = statics["com.huateng.ebank.business.common.DataDicUtils"].getDicName(dicType, value)>${v}</#if><#else><#if method==""||method=="None">${value}<#else><#assign value = cqMethod(method,value)>${value}</#if></#if></#if></td></#list>
	</tr>
	</#list>
	</#if>
</tbody>
</table>
</body>
</html>
