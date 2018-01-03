package om.xdoc.common;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import om.util.OmUtils;

import com.hg.xdoc.XDoc;
import com.hg.xdoc.XDocIO;

import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class XDocServlet extends FreemarkerServlet {
	public static String XDOC_HOME = "";
	/** 静态XDoc类型 */
	private static String STATIC_XDOC  = "1";
	/** 动态XDoc类型 */
	private static String DYNAMIC_XDOC = "2";
	private static Configuration cf = new Configuration();
	public void init() throws ServletException {
		super.init();
		String str = XDocServlet.class.getResource(XDocServlet.class.getSimpleName() + ".class").getPath();
		XDOC_HOME = str.substring(0, str.lastIndexOf("WEB-INF")) + "xdoc";
        //模板存放路径
		try {
			cf.setDirectoryForTemplateLoading(new File(XDOC_HOME));  
			cf.setEncoding(Locale.getDefault(), "UTF-8");
		} catch(IOException e) {
			throw new ServletException("初始化XDOC模板存放路径失败", e);
		}
	}
	
	public void doGet(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws IOException {
		doPost(paramHttpServletRequest, paramHttpServletResponse);
	}

	public void doPost(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws IOException {
		try {
			String xdocTemplate = paramHttpServletRequest.getQueryString();
			Map<String, String> param = new HashMap<String, String>();
			String[] params = xdocTemplate.split("!!", -1);
			for(int i = 0; i < params.length; i ++) {
				String[] tmp = params[i].split("=");
				param.put(tmp[0], tmp[1]);
			}
			XDoc xdoc = null;
			String xdocType = OmUtils.trim((String) param.get("xdocType"));
			if(STATIC_XDOC.equals(xdocType)) {
				xdocTemplate = (String) param.get("xdoc");
				if(OmUtils.isEmpty(xdocTemplate)) {
					throw new Exception("XDoc模板不能为空");
				}
				Map<String, String> map = XDocService.getXDocDataMap(xdocTemplate, param);
				//模板名称
				Template template = cf.getTemplate(xdocTemplate);
		        StringWriter sw = new StringWriter();  
		        //处理并把结果输出到字符串中  
		        template.process(map, sw);  
		        //用字符串构建XDoc  
		        xdoc = new XDoc(sw.toString());
		        XDocIO.write(xdoc, paramHttpServletResponse.getOutputStream(), "swf.zip");
			}
			else if(DYNAMIC_XDOC.equals(xdocType)) {
				String xdocString = XDocService.getXDocContent(param);
				xdoc = new XDoc(xdocString);
				XDocIO.write(xdoc, paramHttpServletResponse.getOutputStream(), "swf.zip");
			}
		} catch(Exception e) {
			throw new IOException("XDocServlet Error", e);
		}
		
	}
}
