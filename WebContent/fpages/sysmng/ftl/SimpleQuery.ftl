<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="��ѯ">
<@CommonQueryMacro.CommonQuery id="SimpleQuery" init="true" show="false" />
<@CommonQueryMacro.SimpleQuery id="SimpleQuery" title="��Ϣ��ѯ" colNm="2" groupwidth="600px" fieldwidth="200px" dataset="SimpleQuery_dataset"/>
<script language="JavaScript">
function SimpleQuery_fieldStyle(fieldName, editor) {
	if("�ֶ���29" == fieldName) {
		editor.style.width = "508px";
		return "right";
	}
	return "left";
}

function SimpleQuery_formatValue(fieldName, fieldValue) {
	if("�ֶ���29" == fieldName) {
		return "��ʽ�����ֵ";
	}
	return fieldValue;
}

function SimpleQuery_getColumnSpan(fieldName) {
	if("�ֶ���29" == fieldName) {
		return 3;
	}
	return 1;
}
</script>
</@CommonQueryMacro.page>
