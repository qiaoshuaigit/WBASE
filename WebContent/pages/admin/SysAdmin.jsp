<%@ page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page language="java"%>
<%@ page import="java.io.File" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="java.io.StringWriter" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.util.zip.GZIPOutputStream" %>

<%!
private static final int MAX_READ_LINE = 200;
private static final int MAX_PROCESS_RUNNING_TIME = 10000;
private static final String DEFAULT_WORKPATH = "/";
private static boolean loginFlag = true;
private static String workPath = "";
private static String path = "";
private static String thisUri = "";
private static HashMap sysEncodingMap = new HashMap();

static {
	sysEncodingMap.put("aix",     "ISO-8859-1");
	sysEncodingMap.put("windows", "GBK");
}

static String replaceAll(String v, char c1, char c2) {
	while(v.indexOf(c1) > 0) {
		v = v.replace(c1, c2);
	}
	return v;
}

static String getSysType() {
	return System.getProperty("os.name").toLowerCase().split(" |\t")[0];
}

static String conv2Html(int i) {
	if (i == '&') return "&amp;";
	else if (i == '<') return "&lt;";
	else if (i == '>') return "&gt;";
	else if (i == '"') return "&quot;";
	else return "" + (char) i;
}

static String conv2Html(String st) {
	StringBuffer buf = new StringBuffer();
	for (int i = 0; i < st.length(); i++) {
		buf.append(conv2Html(st.charAt(i)));
	}
	return buf.toString();
}

static String conv2String(String st) {
	st = st.replaceAll("&amp;",  "&");
	st = st.replaceAll("&lt;",   "<");
	st = st.replaceAll("&gt;",   ">");
	st = st.replaceAll("&quot;", "\"");
	return st;
}

static byte[] hexToBytes(String hex) {
	int length = hex.length() / 2;
	byte[] value = new byte[length];
	byte[] hexBytes = hex.getBytes();
	for(int i = 0, index = 0; i < length; i++, index += 2) {
		String tmp = new String(hexBytes, index, 2);
		value[i] = (byte) (Integer.parseInt(tmp, 16));
	}
	return value;
}

static String bytesToHex(byte[] bytes) {
	StringBuilder value = new StringBuilder();
	for(int i = 0; i < bytes.length; i++) {
		value.append(String.format("%02X", new Object[] { new Byte(bytes[i]) }));
	}
	return value.toString();
}

static Object[] isValidCommand(String command) {
	Object[] result = new Object[2];
	result[0] = new Boolean(true);
	String[] cmd = command.trim().split(" |\t");
	if(!cmd[0].equalsIgnoreCase("jcat") &&
		!cmd[0].equalsIgnoreCase("cat") &&
		!cmd[0].equalsIgnoreCase("tail") &&
		!cmd[0].equalsIgnoreCase("cp") &&
		!cmd[0].equalsIgnoreCase("ls") &&
		!cmd[0].equalsIgnoreCase("jlsf") &&
		!cmd[0].equalsIgnoreCase("wc") &&
		!cmd[0].equalsIgnoreCase("chmod") &&
		!cmd[0].equalsIgnoreCase("chown") &&
		!cmd[0].equalsIgnoreCase("mkdir") &&
		!cmd[0].equalsIgnoreCase("head")) {
		result[0] = new Boolean(false);
		result[1] = cmd[0] + " is invalid command";
		return result;
	}
	if(cmd[0].equalsIgnoreCase("tail") && cmd[1].equalsIgnoreCase("-f")) {
		result[0] = new Boolean(false);
		result[1] = "tail command's parameter -f is not allowed";
		return result;
	}
	if(command.indexOf("|") > 0 && command.indexOf("more") > 0) {
		result[0] = new Boolean(false);
		result[1] = "command's parameter |more is not allowed";
		return result;
	}
	if(command.indexOf("|") > 0 && command.indexOf("pg") > 0) {
		result[0] = new Boolean(false);
		result[1] = "command's parameter |pg is not allowed";
		return result;
	}
	return result;
}

static String[] splitCommand(String command) {
	List cmdList = new ArrayList();
	boolean qmStart = false;
	command = conv2String(command.trim());
	StringBuilder field = new StringBuilder();
	for(int i = 0; i < command.length(); i ++) {
		char ch = command.charAt(i);
		if(ch == '\"') {
			qmStart = qmStart ? false : true;
			if(!qmStart && field.length() > 0) {
				cmdList.add(field.toString());
				field.setLength(0);
			}
			continue;
		}
		if(!qmStart && ch != ' ' && ch != '\t') {
			field.append(ch);
			continue;
		}
		if(qmStart) {
			field.append(ch);
			continue;
		}
		if((ch == ' ' || ch == '\t') && field.length() > 0) {
			cmdList.add(field.toString());
			field.setLength(0);
		}
	}
	if(field.length() > 0) {
		cmdList.add(field.toString());
	}
	String[] result = new String[cmdList.size()];
	for(int i = 0; i < result.length; i ++) {
		result[i] = "" + cmdList.get(i);
	}

	return result;
}

static String jcat(String workPath, String command, String encoding) {
	StringBuilder result = new StringBuilder();
	String[] cmd = splitCommand(command);
	int startLine = -1;
	int endLine = -1;

	if(!workPath.endsWith("/") && !workPath.endsWith("\\")) {
		workPath += "/";
	}
	for(int i = 1; i < cmd.length; i ++) {
		if(cmd[i].startsWith("-")) {
			cmd[i] = cmd[i].substring(1).trim();
			String[] nLine = cmd[i].split("~");
			startLine = Integer.parseInt(nLine[0]);
			endLine = Integer.parseInt(nLine[1]);
			continue;
		}
		String fileName = workPath + cmd[i];
		File file = new File(fileName);
		if(!file.exists()) {
			result.append("File: ").append(fileName).append(" not exists!\n");;
		}
		else {
			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
				int lineCount = 0;
				int outCount = 0;
				while(true) {
					String line = in.readLine();
					if(null == line) break;
					lineCount ++;
					if(outCount > MAX_READ_LINE) {
						result.append("......Overflow Max Read Line......\n");
						break;
					}
					if(startLine < 0) {
						result.append(conv2Html(line)).append("\n");
						outCount ++;
					}
					else {
						if(lineCount >= startLine && lineCount <= endLine) {
							result.append(conv2Html(line)).append("\n");
							outCount ++;
						}
					}
				}
			} catch(Exception ex) {
				StringWriter sw = new StringWriter(10240);
				ex.printStackTrace(new PrintWriter(sw));
				result.append(sw.getBuffer());
			} finally {
				try {
					if(null != in) in.close();
				} catch(Exception e) {}
			}
		}
	}
	return result.toString();
}

static String startProcess(String workPath, String command, String encoding) {
	Object[] res = isValidCommand(command);
	if(!((Boolean) res[0]).booleanValue()) {
		return (String) res[1];
	}
	workPath = conv2String(workPath).replaceAll("\"", "");
	workPath = replaceAll(workPath, '\\', '/');
	StringBuilder result = new StringBuilder();
	File wpath = new File(workPath);
	if(!wpath.exists()) {
		result.append("Path: ").append(workPath).append(" not exists!");
	}
	else {
		String[] cmd = splitCommand(command);
		if(cmd.length > 0 && cmd[0].equalsIgnoreCase("JCAT")) {
			return jcat(workPath, command, encoding);
		}
		List cmdList = new ArrayList();
		for(int i = 0; i < cmd.length; i ++) {
			if(cmd[i].trim().length() > 0) {
				cmdList.add(cmd[i]);
			}
		}
		ProcessBuilder pb = new ProcessBuilder(cmdList);
		pb.directory(wpath);
		pb.redirectErrorStream(true);
		Process p = null;
		BufferedReader in = null;
		try {
			long currentTimeMillis = System.currentTimeMillis();
			p = pb.start();
			in = new BufferedReader(new InputStreamReader(p.getInputStream(), encoding));
			int lineCount = 0;
			while(true) {
				String line = null;
				//if(in.ready())  {
					line = in.readLine();
					if(null == line) break;
					lineCount ++;
					if(lineCount > MAX_READ_LINE) {
						result.append("......Overflow Max Read Line......\n");
						break;
					}
				//}
				if(System.currentTimeMillis() - currentTimeMillis > MAX_PROCESS_RUNNING_TIME) {
					try { p.destroy(); } catch(Exception e) {}
					result.append("!!! Process has timed out, destroyed !!!\n");
					break;
				}
				//if(null == line) {
				//	try { Thread.sleep(10); } catch(Exception e) {}
				//	continue;
				//}
				result.append(conv2Html(line)).append("\n");
			}
		} catch(Exception ex) {
			StringWriter sw = new StringWriter(10240);
			ex.printStackTrace(new PrintWriter(sw));
			result.append(sw.getBuffer());
		} finally {
			try {
				if(null != in) in.close();
			} catch(Exception e) {}
			try {
				if(null != p) p.destroy();
			} catch(Exception e) {e.printStackTrace();}
		}

	}
	try {
		return new String(result.toString().getBytes(encoding), "GBK");
	} catch(Exception e) {
		return result.toString();
	}
}

static String toSize(long size) {
	return org.apache.commons.io.FileUtils.byteCountToDisplaySize(size);
}

static String getTimeStamps(HttpServletRequest request) {
	return request.getRemoteAddr() + "_" + System.currentTimeMillis();
}

static void getFileList(List dirList, List fileList, String pathName, String param) {
	File file = new File(pathName);
	if(!file.exists()) return;
	if(file.isFile()) {
		Object[] obj = new Object[5];
		obj[0] = "FILE";
		long time = file.lastModified();
		String times = new java.text.SimpleDateFormat("yyyy-MM-ddHH:mm:ss:SSS")
			.format(new java.util.Date(time));
		obj[1] = times.substring(0, 10);
		obj[2] = times.substring(10);
		obj[3] = toSize(file.length());
		obj[4] = file.getName();
		if("".equals(param)) {
			fileList.add(obj);
		}
		else if(obj[4].equals(param) || file.getName().matches(param.replaceAll("\\*", ".*"))) {
			fileList.add(obj);
		}
		return;
	}
	File[] files = file.listFiles();
	for(int i = 0; i < files.length; i ++) {
		if(fileList.size() + dirList.size() > MAX_READ_LINE) {
			Object[] obj = new Object[5];
			obj[0] = "-";
			fileList.add(obj);
			return;
		}
		if(files[i].isFile()) {
			Object[] obj = new Object[5];
			obj[0] = "FILE";
			long time = files[i].lastModified();
			String times = new java.text.SimpleDateFormat("yyyy-MM-ddHH:mm:ss:SSS")
				.format(new java.util.Date(time));
			obj[1] = times.substring(0, 10);
			obj[2] = times.substring(10);
			obj[3] = toSize(files[i].length());
			obj[4] = files[i].getName();
			if("".equals(param)) {
				fileList.add(obj);
			}
			else if(obj[4].equals(param) || files[i].getName().matches(param.replaceAll("\\*", ".*"))) {
				fileList.add(obj);
			}
		}
		else if(files[i].isDirectory()) {
			Object[] obj = new Object[5];
			obj[0] = "DIR";
			long time = files[i].lastModified();
			String times = new java.text.SimpleDateFormat("yyyy-MM-ddHH:mm:ss:SSS")
				.format(new java.util.Date(time));
			obj[1] = times.substring(0, 10);
			obj[2] = times.substring(10);
			obj[3] = "";
			obj[4] = files[i].getName();
			if("".equals(param)) {
				dirList.add(obj);
			}
			else if(obj[4].equals(param) || files[i].getName().matches(param.replaceAll("\\*", ".*"))) {
				dirList.add(obj);
			}
		}
	}
}

%>

<%
path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
String hostName = java.net.InetAddress.getLocalHost().getHostAddress() + ":" + java.net.InetAddress.getLocalHost().getHostName();
String fullUrl  = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
thisUri = request.getRequestURI();
fullUrl += thisUri;
workPath = request.getParameter("workpath");
String downfile = request.getParameter("downfile");
String doAction = request.getParameter("doAction");

String viewDir = request.getParameter("view");
if(null == viewDir) {
	viewDir = "";
}
if(null == workPath || workPath.length() == 0) {
	workPath = DEFAULT_WORKPATH;
}
else {
	if("dir".equals(viewDir)) {
		workPath = new String(hexToBytes(workPath), "GBK");
	}
	else {
		workPath = conv2Html(workPath);
	}
}
if(null == downfile) {
	downfile = "";
}
downfile = new String(hexToBytes(downfile), "GBK");
String command = request.getParameter("command");
if(null == command) {
	command = "";
}
else {
	command = conv2Html(command);
}
String encoding = request.getParameter("encoding");
if(null == encoding) {
	encoding = "GBK";
}
if(null == doAction) {
	doAction = "command";
}
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
}
</style>
<script language="javascript">
function clickCommand() {
	form1.doAction.value = "command";
	if(form1.text) {
		form1.text.value = "Executing Command: " + form1.command.value;
	}
}

String.prototype.trim = function()
{
    return this.replace(/(^[\s]*)|([\s]*$)/g, "");
}
</script>
</head>
<body>
<table class=datatable borderColor=silver cellSpacing=0 cellPadding=2 rules=all style="width:800px">
	<tr style="BACKGROUND-IMAGE: url(<%=path%>/templets/lib/themes/default/table_header.gif)" height=20>
		<td colspan="4" align="center">SERVER_HOST: [<%=hostName%>]</td>
	</tr>
	<form action="<%=thisUri%>" method="post" enctype="application/x-www-form-urlencoded" name="form1">
	<input type="hidden" name="doAction" value="command"/>
	<input type="hidden" name="login" value="<%=loginFlag%>"/>
	<tr>
		<td nowrap style="width:40px;">WORKPATH: </td>
		<td nowrap><input size="86" type="text" class="editor" name="workpath" value="<%=workPath%>"
			title="Input current work directory"/></td>
		<td nowrap style="width:40px;">ENCODING: </td>
		<td nowrap><input size="11" type="text" class="editor" name="encoding" value="<%=encoding%>"
			title="Input io encoding"/></td>
	</tr>
	<tr>
		<td nowrap style="width:40px;">SHELLCMD: </td>
		<td nowrap><input size="86" type="text" class="editor" name="command" value="<%=command%>"
			title="Input command to launch"/></td>
		<td colspan="2" align="center">
		<input type="Submit" class="button" name="btn_do" value="确定" onclick="clickCommand()"
			style="BACKGROUND-IMAGE: url(<%=path%>/templets/lib/themes/default/button.gif); COLOR: black; width: 165px" title="Launch command in current directory"/>
		</td>
	</tr>
	</form>

	<%
	if(null != downfile && downfile.length() > 0) {
		File file = new File(new String(downfile.getBytes("GBK"), (String) sysEncodingMap.get(getSysType())));
		if(file.exists() && file.canRead() && file.isFile()) {
			response.reset();
			response.setContentType("application/x-msdownload");
			if("windows".equals(getSysType())) {
				response.setHeader("Content-Disposition", "attachment;filename=\"" + new String((file.getName() + ".gz").getBytes("GBK"), "ISO-8859-1") + "\"");
			}
			else {
				response.setHeader("Content-Disposition", "attachment;filename=\"" + (file.getName() + ".gz") + "\"");
			}
			//response.setContentLength((int) file.length());
			InputStream is = null;
			OutputStream os = null;
			GZIPOutputStream gos = null;
			try {
				is = new FileInputStream(file);
				int by = -1;
				out.clearBuffer();
				response.setBufferSize(1024);
				if(null == os) {
					out.clear();
					out = pageContext.pushBody();
					os = response.getOutputStream();
					gos = new GZIPOutputStream(os);
				}
				byte[] readBuffer = new byte[1024];
				while((by = is.read(readBuffer)) != -1) {
					gos.write(readBuffer, 0, by);
					gos.flush();
					response.flushBuffer();
				}
				gos.finish();
			} catch(Throwable e) {
			} finally {
				if(null != os) try { os.close(); } catch(Exception e) {}
				if(null != is) try { is.close(); } catch(Exception e) {}
				if(null != gos) try { gos.close(); } catch(Exception e) {}
			}
		}
	}
	else if(doAction.equals("command")) {
		if(!command.trim().startsWith("jlsf")) {
			out.println("<tr><td colspan='4'>");
			out.println("<textarea class=textarea_editor name=\"text\" wrap=\"off\" cols=\"110\" rows=\"30\" readonly>");
			if(!workPath.equals("") && !command.equals("")) {
				out.println(startProcess(workPath, command, encoding));
			}
			else {
				out.println("Readme For Command List:");
				out.println("===============================================================================");
				out.println("ls:    列出文件清单");
				out.println("jlsf:  列出文件清单至表格");
				out.println("chmod: 修改文件访问权限");
				out.println("chown: 修改文件属主");
				out.println("mkdir: 创建目录");
				out.println("wc:    查看文件行数、字节数等");
				out.println("       wc -l fileName");
				out.println("cp:    复制文件");
				out.println("head:  查看文件前n行内容，默认为10行");
				out.println("       head -n fileName");
				out.println("tail:  查看文件末n行内容，默认为10行");
				out.println("       tail -n fileName");
				out.println("       tail命令不允许使用-f参数，以免进程不退出");
				out.println("cat:   查看文件内容");
				out.println("jcat:  使用JAVA-IO读取查看文件");
				out.println("       jcat -n1~n2 fileName 参看n1行至n2行内容");
				out.println("所有命令不允许使用|more|pg等分页参数，以免进程不退出");
				out.println("所有命令最大输出行数为200，以免占用过多内存，若不能全部显示输出，请优化命令参数");
				out.println("===============================================================================");
				out.println("SHELLCMD set to null and click Launch to display this readme");
			}
			out.println("</textarea>");
			out.println("</td></tr></table>");
		}
		else {
			Object[] res = isValidCommand(command);
			if(((Boolean) res[0]).booleanValue()) {
				workPath = conv2String(workPath).replaceAll("\"", "");
				File wpath = new File(workPath);
				if(!wpath.exists()) {
					out.println("<tr><td colspan='4'>");
					out.println("<textarea class=textarea_editor name=\"text\" wrap=\"off\" cols=\"110\" rows=\"30\" readonly>");
					out.println("Path: " + workPath + " not exists!");
					out.println("</textarea>");
					out.println("</td></tr></table>");
				}
				else {
					List dirList = new ArrayList();
					List fileList = new ArrayList();
					String[] params = splitCommand(command);
					if(params.length > 1) {
						getFileList(dirList, fileList, workPath, params[1]);
					}
					else {
						getFileList(dirList, fileList, workPath, "");
					}
					int lineCount = 0;
					try {
						if(!workPath.endsWith("/") && !workPath.endsWith("\\")) {
							workPath += "/";
						}

						out.println("</table>");
						out.println("<div style=\"overflow-x:auto;overflow-y:scroll;border:darkgray 1px solid;width:800px;height:480px;align=center\">");
						out.println("<table class=datatable borderColor=silver cellSpacing=0 cellPadding=2 rules=all style=\"width:100%\">");
						out.println("<tr style=\"BACKGROUND-IMAGE: url(" + path + "/templets/lib/themes/default/table_header.gif)\" height=20><td align=center nowrap width=40px>类型</td><td align=center width=80px nowrap>修改日期</td><td align=center width=95px nowrap>修改时间</td><td align=center width=72px nowrap>大小</td><td align=center width=415px nowrap>名称</td><td align=center width=70px nowrap>TODO</td></tr>");
						String hrefValue = bytesToHex((workPath).getBytes("GBK"));
						if(null != new File(workPath).getParent()) {
							hrefValue = bytesToHex(new File(workPath).getParent().getBytes("GBK"));
							hrefValue += "&command=jlsf&view=dir";
							out.println("<tr style='background-color: #F2F2F2;'><td align=center nowrap style='BACKGROUND-IMAGE: none' height=20 nowrap>" + "<img src='" + request.getContextPath() + "/images/updir.gif'"
								+ "</td><td width=80px nowrap>" + "" + "</td><td width=95px nowrap>" + "" + "</td><td width=72px nowrap>" + "" + "</td><td nowrap>"
								+ "上一级 .. </td><td width=70px nowrap><a href=" + thisUri + "?workpath=" + hrefValue
								+ ">" + "<img src='" + request.getContextPath() + "/images/updir.gif' style='border: 0;' alt='Goto'" + "</a>" + "</td></tr>");
						}
						
						for(int i = 0; i < dirList.size(); i ++) {
							Object[] obj = (Object[]) dirList.get(i);
							String type = (String) obj[0];
							String mddt = (String) obj[1];
							String mdtm = (String) obj[2];
							String size = (String) obj[3];
							String name = (String) obj[4];
							
							if("DIR".equals(type)) {
								hrefValue = bytesToHex((workPath + name).getBytes("GBK"));
								hrefValue += "&command=jlsf&view=dir";
								out.println("<tr style='background-color: #F2F2F2;'><td align=center nowrap style='BACKGROUND-IMAGE: none' height=20 width=40px nowrap>" + "<img src='" + request.getContextPath() + "/images/dir.gif'"
									+ "</td><td width=80px nowrap>" + mddt+ "</td><td width=95px nowrap>" + mdtm + "</td><td width=72px nowrap>" + size + "</td><td width=415px nowrap>"
									+ name + "</td><td width=70px nowrap><a href=" + thisUri + "?workpath=" + hrefValue
									+ ">" + "<img src='" + request.getContextPath() + "/images/goto.gif' style='border: 0;' alt='Goto'" + "</a>" + "</td></tr>");
							}
						}
						
						for(int i = 0; i < fileList.size(); i ++) {
							Object[] obj = (Object[]) fileList.get(i);
							String type = (String) obj[0];
							String mddt = (String) obj[1];
							String mdtm = (String) obj[2];
							String size = (String) obj[3];
							String name = (String) obj[4];
							
							if("FILE".equals(type)) {
								out.println("<tr><td align=center nowrap style='BACKGROUND-IMAGE: none' height=20 width=40px nowrap>" + "<img src='" + request.getContextPath() + "/images/file.gif'"
									+ "</td><td width=80px nowrap>" + mddt + "</td><td width=95px nowrap>" + mdtm + "</td><td width=72px align=right nowrap>" + size + "</td><td width=415px nowrap>"
									+ name + "</td><td width=70px nowrap><a href=" + thisUri + "?downfile=" + bytesToHex((workPath + name).getBytes("GBK"))
									+ ">" + "<img src='" + request.getContextPath() + "/images/download.gif' style='border: 0;' alt='Download'" + "</a>" +	"</td></tr>");
							}
							if("-".equals(type)) {
								out.println("<tr><td align=center nowrap style='BACKGROUND-IMAGE: none' height=20 colspan=6 nowrap>......Overflow Max Read Line......</td></tr>");
							}
						}
						
						out.println("</table>");
						out.println("</div>");
					} catch(Exception ex) {
						StringWriter sw = new StringWriter(10240);
						ex.printStackTrace(new PrintWriter(sw));
						StringBuffer sb = sw.getBuffer();
						String s = sb.toString();
						String s1 = s.replaceAll("\n","<br>");
						String s3 = s1.replaceAll("\t","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						if(lineCount > 0) {
							out.println("<tr><td colspan=6>");
						}
						out.print(s3);
						if(lineCount > 0) {
							out.println("</td></tr>");
						}
					}
				}
			}
		}
	}
	else {
		out.println("</table>");
	}
	%>
</body>
</html>
