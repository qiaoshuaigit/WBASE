package om.xdoc.common;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Element;

import com.thoughtworks.xstream.XStream;

import om.util.XmlBuilder;
import om.xdoc.data.IXDocDataMap;

public class XDocService {
	private static Map<String, String> XDOC_DATA_GETTER = new HashMap<String, String>();
	static {
		//XDOC_DATA_GETTER.put("xx.xdoc", "om.xdoc.data.TestXDocDataMap");
		XDOC_DATA_GETTER.put("AdjustApplyForm.xdoc", "om.xdoc.data.AdjustApplyFormData");
		XDOC_DATA_GETTER.put("AdjustApplyPrint.xdoc", "om.xdoc.data.AdjustApplyPrintData");
		XDOC_DATA_GETTER.put("IbpsCompletionTotal.xdoc", "om.xdoc.data.IbpsCompletionTotalData");
		XDOC_DATA_GETTER.put("IbpsCompletionPrint.xdoc", "om.xdoc.data.IbpsCompletionPrintData");
	}
	
	public static Map<String, String> getXDocDataMap(String name, Map<String, String> param) throws Exception {
		String className = XDOC_DATA_GETTER.get(name);
		if(null != className) {
			Class<?> clazz = Class.forName(className);
			IXDocDataMap getter = (IXDocDataMap) clazz.newInstance();
			return getter.getXDocDataMap(param);
		}
		return new HashMap<String, String>();
	}
	
	public static String getXDocContent(Map<String, String> param) throws Exception {
		String name = (String) param.get("xdoc");
		String className = XDOC_DATA_GETTER.get(name);
		if(null != className) {
			Class<?> clazz = Class.forName(className);
			IXDocDataMap getter = (IXDocDataMap) clazz.newInstance();
			return getter.getXDocContent(param);
		}
		return "";
	}
	
	public String test(Map<String, String> param) throws Exception {
		System.out.println(param);
		OmXml xdoc = new OmXml("xdoc", "UTF-8");
    	xdoc.setAttribute("version", "7.5.5");
    	xdoc.root().add("meta").setAttribute("author", "Administrator")
    	                       .setAttribute("modifyDate", "2013-09-25 16:42:15")
    	                       .setAttribute("createDate", "2010-12-23 12:21:15");
    	xdoc.root().add("paper").setAttribute("topMargin", "96")
    							.setAttribute("leftMargin", "96")
    							.setAttribute("rightMargin", "96")
    							.setAttribute("bottomMargin", "96");
    	xdoc.root().add("body").add("para").setAttribute("lineSpacing", "4")
    	                                   .add("text").setText("测试123")
    	                                   		       .setAttribute("fontName", "宋体")
    	                                               .setAttribute("fontStyle", "bold")
    	                                               .setAttribute("fontSize", "16");
    	xdoc.root().of("body").add("table").setAttribute("cols", "88,344,164")
    	                                   .setAttribute("line", "00000000")
    	                                   .setAttribute("height", "60")
    	                                   .setAttribute("name", "e52")
    	                                   .setAttribute("left", "4")
    	                                   .setAttribute("sizeType", "autoheight")
    	                                   .setAttribute("top", "100")
    	                                   .setAttribute("width", "596")
    	                                   .setAttribute("rows", "28,32")
    	                                   .setAttribute("color", "#ffffff")
    	                                   .setAttribute("align", "center")
    	                                   .add("cell").setAttribute("name", "e55")
    	                                   		.add("para").setAttribute("align", "center")
    												.add("text").setAttribute("fontName", "宋体")
    															.setAttribute("fontStyle", "bold")
    															.setAttribute("fontSize", "16")
    															.setText("TEXT");
    	xdoc.root().of("body/table").add("cell").setAttribute("col", "2")
    											.setAttribute("name", "e58")
    											.add("para")
    											.add("text")
    												.setAttribute("fontName", "宋体")
    												.setAttribute("fontStyle", "bold")
    												.setAttribute("fontSize", "16")
    												.setText("测试1");
    	xdoc.root().of("body/table").add("cell").setAttribute("col", "3")
												.setAttribute("name", "e61")
												.add("para")
												.add("text")
													.setAttribute("fontName", "宋体")
													.setAttribute("fontStyle", "bold")
													.setAttribute("fontSize", "16")
													.setText("测试2");
    	xdoc.root().of("body/table").add("cell").setAttribute("sizeType", "autoheight")
    											.setAttribute("row", "2")
												.setAttribute("name", "e64")
												.add("para")
												.add("text")
													.setAttribute("fontName", "宋体")
													.setAttribute("fontStyle", "bold")
													.setAttribute("fontSize", "16")
													.setText("吉林省123324354");
    	xdoc.root().of("body/table").add("cell").setAttribute("col", "2")
												.setAttribute("row", "2")
												.setAttribute("name", "e67")
												.setAttribute("sizeType", "autoheight")
												.add("para")
													.setAttribute("align", "right")
												.add("text")
													.setAttribute("fontName", "宋体")
													.setAttribute("fontStyle", "bold")
													.setAttribute("fontSize", "16")
													.setText("测试测试1234567890");
    	xdoc.root().of("body/table").add("cell").setAttribute("col", "3")
												.setAttribute("row", "2")
												.setAttribute("sizeType", "autoheight")
												.setAttribute("name", "e70");
		return xdoc.asLine();
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(getXDocContent(null));
	}
}
