package com.huateng.datafile;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.vidageek.mirror.Mirror;
import om.util.OmUtils;
import om.util.XmlBuilder;

import org.dom4j.Element;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;

import com.huateng.datafile.FileFormat.Field;
import com.huateng.stl4j.common.ApplicationContextUtils;

public class FileFormatFactory implements IParser {
	private final String XML_CONFIG = "resources/fileFormat/FileFormat.xml";
	private static Map<String, FileFormat> cache = Collections.synchronizedMap(new HashMap<String, FileFormat>());
	public final static PropertyAccessor pa = PropertyAccessorFactory.getPropertyAccessor(null);
	
	private FileFormatFactory() throws Exception {
		XmlBuilder xml = new XmlBuilder(FileFormatFactory.class.getClassLoader().getResource(XML_CONFIG), "UTF-8");
		Iterator its = xml.getRootElement().elements().iterator();
		while(its.hasNext()) {
			Element element = (Element) its.next();
			FileFormat fileFormat = new FileFormat();
			fileFormat.setName(element.getName());
			fileFormat.setEncoding(element.attribute("encoding").getValue());
			fileFormat.setSumField(element.attribute("sumField").getValue());
			fileFormat.setDataStartLine(0);
			fileFormat.setHeadLine(0);
			fileFormat.setFieldSplit(element.attribute("split").getValue());
			List fieldList = element.elements();
			int totLength = 0;
			for(int i = 0; i < fieldList.size(); i ++) {
				int index = Integer.parseInt(((Element)fieldList.get(i)).attributeValue("index"));
				String fieldName = ((Element)fieldList.get(i)).attributeValue("fieldName");
				String desc = ((Element)fieldList.get(i)).attributeValue("desc");
				int length = Integer.parseInt(((Element)fieldList.get(i)).attributeValue("length"));
				totLength += length;
				int scale = Integer.parseInt("0" + OmUtils.trim(((Element)fieldList.get(i)).attributeValue("scale")));
				String require = OmUtils.trim(((Element)fieldList.get(i)).attributeValue("require"));
				Field field = new Field(index, fieldName, length, desc, scale);
				if(!OmUtils.isEmpty(require)) {
					field.setRequire(Boolean.parseBoolean(require));
				}
				fileFormat.getFields().add(field);
			}
			fileFormat.setTotLength(totLength);
			fileFormat.withParser(this);
			cache.put(fileFormat.getName(), fileFormat);
		}
	}
	
	public FileFormat getFileFormat(String formatName) {
		return cache.get(formatName);
	}
	
	public synchronized static FileFormatFactory getInstance() throws Exception {
		return (FileFormatFactory) ApplicationContextUtils.getBean("FileFormatFactory");
	}
	
	public void parse(Object bean, String fieldName, String value, int length, int scale, boolean require)
			throws Exception {
		value = OmUtils.trim(value);
		Class fieldClass = FileFormatFactory.pa.getGetter(bean.getClass(), fieldName).getReturnType();
		if(require && OmUtils.isEmpty(value)) {
			throw new Exception("不能为空");
		}
		if(fieldClass.equals(String.class)) {
			if(value.getBytes("GBK").length > length) {
				throw new Exception("长度超限-" + length);
			}
			Mirror.on(bean).set().field(fieldName).withValue(value);
		}
		else if(fieldClass.equals(Integer.class) || fieldClass.equals(int.class)) {
			if(!OmUtils.isEmpty(value)) {
				Mirror.on(bean).set().field(fieldName).withValue(new Integer(value));
			}
		}
		else if(fieldClass.equals(Short.class) || fieldClass.equals(short.class)) {
			if(!OmUtils.isEmpty(value)) {
				Mirror.on(bean).set().field(fieldName).withValue(new Short(value.replaceAll(",", "")));
			}
		}
		else if(fieldClass.equals(Long.class) || fieldClass.equals(long.class)) {
			if(!OmUtils.isEmpty(value)) {
				Mirror.on(bean).set().field(fieldName).withValue(new Long(value));
			}
		}
		else if(fieldClass.equals(Double.class) || fieldClass.equals(double.class)) {
			if(!OmUtils.isEmpty(value)) {
				Mirror.on(bean).set().field(fieldName).withValue(new Double(value.replaceAll(",", "")));
			}
		}
		else if(fieldClass.equals(Boolean.class) || fieldClass.equals(boolean.class)) {
			if(!OmUtils.isEmpty(value)) {
				Mirror.on(bean).set().field(fieldName).withValue(new Boolean(value));
			}
		}
		else if(fieldClass.equals(BigDecimal.class)) {
			if(!OmUtils.isEmpty(value)) {
				BigDecimal tmpValue = new BigDecimal(value.replaceAll(",", ""));
				tmpValue.setScale(scale, BigDecimal.ROUND_HALF_UP);
				Mirror.on(bean).set().field(fieldName).withValue(tmpValue);
			}
		}
		else if(fieldClass.equals(java.util.Date.class)) {
			if(!OmUtils.isEmpty(value)) {
				Mirror.on(bean).set().field(fieldName).withValue(OmUtils.stringToDate(value));
			}
		}
		else if(fieldClass.equals(java.sql.Date.class)) {
			if(!OmUtils.isEmpty(value)) {
				Mirror.on(bean).set().field(fieldName).withValue(
						new java.sql.Date(OmUtils.stringToDate(value).getTime()));
			}
		}
		else {
			throw new Exception("不支持的类型-" + fieldClass.getName());
		}
	}
}
