<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="�ϴ�֧�����ղ���ļ�">
<@CommonQueryMacro.CommonQuery id="CommEmpty" init="false" navigate="false">
<table align="center" style="margin-top: 8px;">
	<tr>
		<td>
		<@CommonQueryMacro.FileUpload id="upload1" name="�ϴ�֧�����ղ���ļ�" title="�ϴ�֧�����ղ���ļ�" fileSizeLimit="1 MB" />
		<@CommonQueryMacro.FileUpload id="upload2" name="�ϴ��������ղ���ļ�" title="�ϴ��������ղ���ļ�" fileSizeLimit="1 MB" />
		<@CommonQueryMacro.FileUpload id="upload3" name="�ϴ��������ղ���ļ�" title="�ϴ��������ղ���ļ�" fileSizeLimit="1 MB" />
		</td>
	</tr>
</table>
</@CommonQueryMacro.CommonQuery>
<@CommonQueryMacro.WindowElement/>
<script language="JavaScript">
function upload1_getPrimaryKey() {
	return "000";
}
function upload1_getBussType() {
	return "APS";
}
function upload1_upload_success() {
	
}
function upload2_getPrimaryKey() {
	return "000";
}
function upload2_getBussType() {
	return "TIPS";
}
function upload2_upload_success() {
	
}
function upload3_getPrimaryKey() {
	return "000";
}
function upload3_getBussType() {
	return "IBPS";
}
function upload3_upload_success() {
	
}
</script>
</@CommonQueryMacro.page>
