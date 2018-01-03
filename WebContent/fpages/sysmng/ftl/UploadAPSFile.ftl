<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="上传支付日终差错文件">
<@CommonQueryMacro.CommonQuery id="CommEmpty" init="false" navigate="false">
<table align="center" style="margin-top: 8px;">
	<tr>
		<td>
		<@CommonQueryMacro.FileUpload id="upload1" name="上传支付日终差错文件" title="上传支付日终差错文件" fileSizeLimit="1 MB" />
		<@CommonQueryMacro.FileUpload id="upload2" name="上传横联日终差错文件" title="上传横联日终差错文件" fileSizeLimit="1 MB" />
		<@CommonQueryMacro.FileUpload id="upload3" name="上传网银日终差错文件" title="上传网银日终差错文件" fileSizeLimit="1 MB" />
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
