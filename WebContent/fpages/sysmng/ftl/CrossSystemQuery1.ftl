<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="查询">
<@CommonQueryMacro.CommonQuery id="CrossSystemQuery1" init="true" show="false" />
<@CommonQueryMacro.SimpleQuery id="CrossSystemQuery1" title="信息查询" colNm="2" groupwidth="600px" fieldwidth="200px" dataset="CrossSystemQuery1_dataset"/>
<script language="JavaScript">
function CrossSystemQuery1_fieldStyle(fieldName, editor) {
	if("字段名29" == fieldName) {
		editor.style.width = "508px";
		return "right";
	}
	return "left";
}

function CrossSystemQuery1_formatValue(fieldName, fieldValue) {
	if("字段名29" == fieldName) {
		return "格式化后的值";
	}
	return fieldValue;
}

function CrossSystemQuery1_getColumnSpan(fieldName) {
	if("字段名29" == fieldName) {
		return 3;
	}
	return 1;
}
</script>
</@CommonQueryMacro.page>
