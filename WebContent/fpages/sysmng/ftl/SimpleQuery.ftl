<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="查询">
<@CommonQueryMacro.CommonQuery id="SimpleQuery" init="true" show="false" />
<@CommonQueryMacro.SimpleQuery id="SimpleQuery" title="信息查询" colNm="2" groupwidth="600px" fieldwidth="200px" dataset="SimpleQuery_dataset"/>
<script language="JavaScript">
function SimpleQuery_fieldStyle(fieldName, editor) {
	if("字段名29" == fieldName) {
		editor.style.width = "508px";
		return "right";
	}
	return "left";
}

function SimpleQuery_formatValue(fieldName, fieldValue) {
	if("字段名29" == fieldName) {
		return "格式化后的值";
	}
	return fieldValue;
}

function SimpleQuery_getColumnSpan(fieldName) {
	if("字段名29" == fieldName) {
		return 3;
	}
	return 1;
}
</script>
</@CommonQueryMacro.page>
