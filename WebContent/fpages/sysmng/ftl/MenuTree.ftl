<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<@CommonQueryMacro.page title="权限管理">
<#assign roleCode = RequestParameters["roleCode"]?default("0")>
<#assign roleName = decodeStr(RequestParameters["roleName"]?default(""))>
<@CommonQueryMacro.CommonQuery id="RoleFuncRelation" init="false" navigate="false" submitMode="current">
<table align="left" style="margin-top: 0px; margin-bottom: 0px;">
	<tr>
		<td nowrap>给[&nbsp;${roleName}&nbsp;]分配权限：
			<#--
			<LABEL id="roleNameForTitle"></LABEL>
			<script language="JavaScript">
				var roleName = getDecodeStr("${roleName}");
				document.getElementById("roleNameForTitle").innerHTML = "给[&nbsp;" + roleName + "&nbsp;]分配权限：";
			</script>
			-->
		</td>
	</tr>
	<tr>
		<td><@CommonQueryMacro.Button id="btnSelectAll"/>&nbsp;&nbsp;
		    <@CommonQueryMacro.Button id="btnSelectNone"/>&nbsp;&nbsp;
		    <@CommonQueryMacro.Button id="btnSaveRoleFuncRelation"/>
		</td>
	</tr>
	<tr>
		<td><div id="menuTreeDiv">
			<script language="JavaScript">
				var menuTree = null;
				var vRoleCode = "${roleCode}";
				if(!isNaN(vRoleCode) && "" != vRoleCode && 0 != vRoleCode) {
					dwr.engine.setAsync(false);
					PrivateAction.getRoleFuncRelation(vRoleCode, function(data) {menuTree = data;});
					dwr.engine.setAsync(true);
					var treeHtml = createTree(menuTree, 0, 1);
					document.getElementById("menuTreeDiv").innerHTML = treeHtml;
					document.all.menuHome.title = "展开";
					closeAll(0);
				}
			</script>
			</div>
		</td>
	</tr>
</table>
</@CommonQueryMacro.CommonQuery>
<script language="JavaScript">
var vRoleCode = "${roleCode}";
function treeItemClicked(id,upId,name) {
	//alert("id=" + id + ",upId=" + upId + ",name=" + name);
}

function btnSelectAll_onClick(button) {
	var len = document.getElementsByName("id").length;
	for(i = 0;i < len; i ++) {
		if (document.getElementsByName("id")[i].disabled == false) {
			document.getElementsByName("id")[i].checked = true;
		}
	}
}

function btnSelectNone_onClick(button) {
	var len = document.getElementsByName("id").length;
	for(i = 0;i < len; i ++) {
		if (document.getElementsByName("id")[i].disabled == false) {
			document.getElementsByName("id")[i].checked = false;
		}
	}
}

function getSelectedFuncCodes() {
	var count = 0;
	var result = "";
	var len = document.getElementsByName("id").length;
	for(i = 0; i < len; i ++) {
		if(document.getElementsByName("id")[i].checked == true) {
			if(count > 0) result += ",";
			result += document.getElementsByName("id")[i].value;
			count ++;
		}
	}
	return result;
}

function checkResult(data) {
	if(data == "0") {
		alert("保存成功！");
	}
	else {
		alert(data, "保存失败", "error");
	}
}

function btnSaveRoleFuncRelation_onClick(button) {
	dwr.engine.setAsync(false);
	PrivateAction.saveRoleFuncRelation(vRoleCode, getSelectedFuncCodes(), checkResult);
	dwr.engine.setAsync(true);
}
</script>
</@CommonQueryMacro.page>
