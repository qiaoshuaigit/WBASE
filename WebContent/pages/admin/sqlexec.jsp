<%@ page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page language="java" %>
<%@ page import="com.huateng.admin.QueryResult" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
String opAuth = "" + request.getSession().getAttribute("OP_AUTH");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="<%=path%>/templets/lib/themes/default/extra.css">
<title>ADMIN</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<style type="text/css">
.textarea_editor
{
	scrollbar-3dlight-color:gray;
	scrollbar-arrow-color:black;
	scrollbar-base-color:#cccccc;
	scrollbar-darkshadow-color:white;
	border: darkgray 1px solid;
}
</style>
<!--
    <link rel="stylesheet" type="text/css" href="styles.css">
-->
<script language="javascript">
v_confirm = false;

function trimStr(str)
{
	str = getValidStr(str);
	if(!str) return "";
	for(i = str.length - 1; i >= 0; i --) {
		if(str.charCodeAt(i, 10) != 32) break;
	}
	return str.substr(0, i + 1);
}

function getValidStr(str)
{
	str += "";
	if(str == "undefined" || str == "null")
		return "";
	else
		return str;
}

function clickIt() {
	var v_op_auth = "" + form1.OP_AUTH.value;
    v_sql = form1.sql.value.toLowerCase();
    v_sql_array = v_sql.split("\n");
    v_sql_v = "";
    for(i = 0; i < v_sql_array.length; i ++) {
    	var chkValue = v_sql_array[i];
    	if(chkValue.length < 6) continue;
    	if(chkValue.indexOf("--") == 0) continue;
    	if(chkValue.indexOf("//") == 0) continue;
    	else {
    		v_sql_v = trimStr(chkValue);
    		break;
    	}
    }
	if(v_sql_v.substr(0, 6) == "select" ||
		v_sql_v.substr(0, 4) == "list"  ||
		v_sql_v.substr(0, 4) == "desc") {
		form1.button1.value = "SQL执行中";
		form1.button1.disabled = true;
		form1.submit();
		return;
	}
	if(v_op_auth != "UPDATE") {
		alert("无权访问");
		return;
	}
	if(v_confirm) {
		form1.button1.value = "SQL执行中";
		form1.button1.disabled = true;
		form1.button2.disabled = true;
		form1.submit();
	} else {
		form1.button1.value = "请再确认";
		form1.button2.disabled = false;
		v_confirm = true;
	}
}

function cancelIt() {
	v_confirm = false;
	form1.button1.value = "执行SQL";
	form1.button2.disabled = true;
}

function exitMe() {
	form1.DO_EXIT.value = "true";
	form1.submit();
}

function helpMe() {
	var helpText = "SQL Type List: select,list tables,describe tabname\n";
	helpText += "※ list tables [*value*]\n     ";
	helpText += "e.g. list tables to list all table name\n     ";
	helpText += "e.g. list tables *tmp* to list table name like '%tmp%'";
	alert(helpText);
}
</script>
</head>
<body>
<form action="<%=basePath%>pages/admin" method="post" name="form1">
	<input type="hidden" name="action" value="execute"/>
	<input type="hidden" name="DO_EXIT" value=""/>
	<input type="hidden" name="OP_AUTH" value="<%=opAuth%>"/>
	<table>
		<tr>
			<td align="center">
				<textarea type="text" name="sql" rows="6" cols="128" class=textarea_editor><%=request.getAttribute("SQL")%></textarea>
			</td>
		</tr>

		<tr>
			<td align=center>
				<input type="button" name="button1" value="执行SQL" onclick="clickIt()" class=button style="BACKGROUND-IMAGE: url(<%=path%>/templets/lib/themes/default/button.gif); COLOR: black"/>&nbsp;&nbsp;
				<input type="button" name="button2" value="取消执行" disabled="true" onclick="cancelIt()" class=button style="BACKGROUND-IMAGE: url(<%=path%>/templets/lib/themes/default/button.gif); COLOR: black"/>&nbsp;&nbsp;
				<input type="button" name="button3" value="注销" onclick="exitMe()" class=button style="BACKGROUND-IMAGE: url(<%=path%>/templets/lib/themes/default/button.gif); COLOR: black"/>&nbsp;&nbsp;
				<input type="button" name="button3" value="帮助" onclick="helpMe()" class=button style="BACKGROUND-IMAGE: url(<%=path%>/templets/lib/themes/default/button.gif); COLOR: black"/>
			</td>
		</tr>
	</table>
</form>

<%
	String action = (String)request.getAttribute("ACTION");
	if(!"EMPTY".equals(action)) {
		String code = (String)request.getAttribute("CODE");
		if("SUCCESS".equals(code)) {
			if(action.equals("UPDATE")) {
%>
				执行结果：耗时<%=request.getAttribute("DURING")%>毫秒,<%=request.getAttribute("RESULT")%>
			<%
			} else {
				QueryResult queryResult = (QueryResult)request.getAttribute("RESULT");
			%>
				执行结果：耗时<%=request.getAttribute("DURING")%>毫秒,(数据查询只显示前50条记录)
				<div style="overflow-x:auto;overflow-y:scroll;border:darkgray 1px solid;width:924px;height:72%;align=center">
				<table class=datatable borderColor=silver cellSpacing=0 cellPadding=2 rules=all style="width:100%;align=center">
				<thead>
				<tr style="BACKGROUND-IMAGE: url(<%=path%>/templets/lib/themes/default/table_header.gif)" height=20>
				<%
					int colIndex = 0;
					for(Iterator it = queryResult.getFieldNameList().iterator(); it.hasNext();) {
						colIndex ++;
						String fieldName = (String)it.next();
						if(colIndex == 1) {
							out.print("<td align=center style=\"width:20pt\">" + fieldName + "</td>");
						}
						else {
							out.print("<td align=center>" + fieldName + "</td>");
						}
					}
				%>
				</tr>
				</thead>

				<tbody>
				<%
					int rowIndex = 0;
					for(Iterator it = queryResult.getValueList().iterator(); it.hasNext();) {
						rowIndex ++;
						if(rowIndex % 2 == 1) {
							out.print("<tr>");
						}
						else {
							out.print("<tr style='background-color: #F2F2F2;'>");
						}
						String[] stringArray = (String[])it.next();
						for(int i = 0; i < stringArray.length; i ++) {
							if(i == 0) {
								out.print("<td align=center nowrap style='BACKGROUND-IMAGE: url(" + path + "/templets/lib/themes/default/table_header.gif)' height=20>");
							}
							else {
								out.print("<td align='left' nowrap>");
							}
							out.print(stringArray[i]);
							out.print("</td>");
						}
						out.print("</tr>");
					}
				}
				%>
				</tbody>
				</table>
				</div>
	<%
		} else if("FAIL".equals(code)) {
			out.println("<div style=\"overflow-x:auto;overflow-y:scroll;border:darkgray 1px solid;width:924px;height:75%;align=center;\">");
			out.print("<table style=\"width:100%;align=center\"><tr><td>");
			Exception ex = (Exception)request.getAttribute("RESULT");
			out.print("<br> 发生异常:");
			StringWriter sw = new StringWriter(10240);
			ex.printStackTrace(new PrintWriter(sw));

			StringBuffer sb = sw.getBuffer();
			String s = sb.toString();
			String s1 = s.replaceAll("\n","<br>");
			String s3 = s1.replaceAll("\t","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.print(s3);
			out.print("</td></tr></table>");
			out.println("</div>");
		}
	}
	%>

</body>
</html>
