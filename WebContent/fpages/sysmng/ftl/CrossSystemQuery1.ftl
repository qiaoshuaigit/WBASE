<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="��ѯ">
<@CommonQueryMacro.CommonQuery id="CrossSystemQuery1" init="true" show="false" />
<@CommonQueryMacro.SimpleQuery id="CrossSystemQuery1" title="��Ϣ��ѯ" colNm="2" groupwidth="600px" fieldwidth="200px" dataset="CrossSystemQuery1_dataset"/>
<script language="JavaScript">
function CrossSystemQuery1_fieldStyle(fieldName, editor) {
	if("�ֶ���29" == fieldName) {
		editor.style.width = "508px";
		return "right";
	}
	return "left";
}

function CrossSystemQuery1_formatValue(fieldName, fieldValue) {
	if("�ֶ���29" == fieldName) {
		return "��ʽ�����ֵ";
	}
	return fieldValue;
}

function CrossSystemQuery1_getColumnSpan(fieldName) {
	if("�ֶ���29" == fieldName) {
		return 3;
	}
	return 1;
}
</script>
</@CommonQueryMacro.page>
