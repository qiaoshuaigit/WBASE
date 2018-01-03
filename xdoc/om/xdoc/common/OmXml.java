package om.xdoc.common;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

public class OmXml {
	private String encoding;
    private Document document;
    private Element currentElement;
    
    public OmXml() {
    }
    
    public OmXml(String rootId, String encoding) {
    	this.encoding = encoding;
        document = DocumentFactory.getInstance().createDocument();
        document.setXMLEncoding(encoding);
        currentElement = createElement(rootId);
        document.add(currentElement);
    }
    
    public OmXml(String encoding, Element element) {
    	this.encoding = encoding;
        document = DocumentFactory.getInstance().createDocument();
        document.setXMLEncoding(encoding);
        document.add(element);
        currentElement = document.getRootElement();
    }
    
    public Element getCurrentElement() {
    	return currentElement;
    }
    
    public Element getRootElement() {
    	return document.getRootElement();
    }
    
    public Document getDocument() {
    	return document;
    }
    
    public Element createElement(String nodeName) {
        return DocumentFactory.getInstance().createElement(nodeName);
    }
    
    public String asXML() {
    	return document.asXML();
    }
    
    public String asLine() {
    	return document.asXML().replaceAll("\\n|\r|\t", "").replaceAll(">[ ]*<", "><");
    }
    
    public void setEncoding(String encoding) {
    	this.encoding = encoding;
    	if(null != document) {
    		 document.setXMLEncoding(encoding);
    	}
    }
    
    public String getEncoding() {
    	return encoding;
    }
    
    public void parse(String xml, String encoding) throws DocumentException {
    	StringReader strReader = null;
    	InputSource source = null;
    	try {
    		this.encoding = encoding;
    		strReader = new StringReader(xml);
    		source = new InputSource(strReader);
    		document = new SAXReader().read(source);
            document.setXMLEncoding(encoding);
            currentElement = document.getRootElement();
    	} finally {
    		strReader.close();
    	}
    }
    
    public OmXml add(String name) throws Exception {
    	Element parentElement = currentElement;
    	currentElement = DocumentFactory.getInstance().createElement(name);
    	parentElement.add(currentElement);
    	
    	return this;
    }
    
    public OmXml add(String xpath, String name) throws Exception {
    	of(xpath);
    	Element parentElement = currentElement;
    	currentElement = DocumentFactory.getInstance().createElement(name);
    	parentElement.add(currentElement);
    	
    	return this;
    }
    
    public OmXml root() {
    	currentElement = document.getRootElement();
    	
    	return this;
    }
    
    public OmXml up() {
    	currentElement = currentElement.getParent();
    	
    	return this;
    }
    
    public OmXml of(String xpath) throws Exception {
    	Element element = (Element) currentElement.selectSingleNode(xpath);
    	if(null != element) {
    		currentElement = element;
    		return this;
    	}
    	else {
    		throw new Exception(currentElement.getPath() + "/" + xpath + " not exists");
    	}
    }
    
    public OmXml setText(String text) {
    	currentElement.setText(text);
    	
    	return this;
    }
    
    public OmXml setCDATA(String text) {
    	((Element) currentElement).addCDATA(text);
    	
    	return this;
    }

    public OmXml setAttribute(String name, String value) {
    	((Element) currentElement).addAttribute(name, value);
    	return this;
    }
    
    public static void main(String[] args) throws Exception {
//    	String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
//    	             "<xdoc version=\"7.5.5\">" +
//    	             "</xdoc>";
//    	XDocContent xdoc = new XDocContent();
    	OmXml xdoc = new OmXml("xdoc", "UTF-8");
    	xdoc.setAttribute("version", "7.5.5");
//    	xdoc.parse(xml, "UTF-8");
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
													.setText("${TestField}");
    	xdoc.root().of("body/table").add("cell").setAttribute("col", "2")
												.setAttribute("row", "2")
												.setAttribute("name", "e67")
												.add("para")
													.setAttribute("align", "right")
												.add("text")
													.setAttribute("fontName", "宋体")
													.setAttribute("fontStyle", "bold")
													.setAttribute("fontSize", "16")
													.setText("${TestField}");
    	xdoc.root().of("body/table").add("cell").setAttribute("col", "3")
												.setAttribute("row", "2")
												.setAttribute("name", "e70");
    	System.out.println(xdoc.asLine());
    }
}
