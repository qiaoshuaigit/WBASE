<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="��������">
<#assign brhId = RequestParameters["brhId"]?default("0")>
<@CommonQueryMacro.CommonQuery id="BranchTree" init="false" navigate="false" submitMode="allchange">
<table align="left" style="margin-left: 10px;">
	<tr>
		<td>��ҳ&nbsp;&gt;&nbsp;ϵͳ����&nbsp;&gt;&nbsp;��������&nbsp;&gt;&nbsp;������</td>
	</tr>
	<tr>
		<td><div id="branchTreeDiv">
			<script language="JavaScript">
				var vId = "${brhId}";
				if(!isNaN(vId)) {
					var brhTree = null;
					dwr.engine.setAsync(false);
					PrivateAction.getBranchTree(vId, function(data){brhTree = data;});
					dwr.engine.setAsync(true);
					var treeHtml = createTree(brhTree, 0, 0);
					document.getElementById("branchTreeDiv").innerHTML = treeHtml;
					document.all.menuHome.title = "չ��";
					closeAll(0);
				}
			</script>
			</div>
		</td>
	</tr>
</table>
</@CommonQueryMacro.CommonQuery>
<script language="JavaScript">
function treeItemClicked(id,upId,name) {
	//alert("id=" + id + ",upId=" + upId + ",name=" + name);
}
</script>
</@CommonQueryMacro.page>
