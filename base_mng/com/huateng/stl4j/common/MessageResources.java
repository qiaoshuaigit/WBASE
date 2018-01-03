package com.huateng.stl4j.common;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Properties;

public class MessageResources {
	private static MessageResources single = null;
	private Properties properties = null;
	protected HashMap<String, MessageFormat> formats = new HashMap<String, MessageFormat>();
	
	private MessageResources() {
		
	}
	
	public synchronized static MessageResources getInstance() {
        if(null == single) {
            single = new MessageResources();
        }
        return single;
    }
	
	public void init(String resourcePath) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(resourcePath);
        if(null == is) {
            throw new RuntimeException("找不到资源文件:" + resourcePath);
        }
        try {
            properties = new Properties();
            properties.load(is);
        } catch(IOException ioe) {
            throw new RuntimeException("不能加载资源文件:" + resourcePath, ioe);
        } finally {
            try {
                is.close();
            } catch (Exception e) {
            }
        }
    }
	
	public String getMessage(String key) {
        String message = (String) properties.get(key);        
        return message;
    }
	
	public String getMessage(String key, Object[] args) {
        if(null == args || 0 == args.length) {
            return getMessage(key);
        }

        MessageFormat format = null;
        String formatKey = key;

        synchronized (formats) {
            format = (MessageFormat) formats.get(formatKey);
            if(format == null) {
                String formatString = getMessage(key);
                if(formatString == null) {
                    return null;
                }
                format = new MessageFormat(escape(formatString));
                formats.put(formatKey, format);
            }
        }
        synchronized (format) {
            return format.format(args);
        }
    }
	
	protected String escape(String string) {
        if((string == null) || (string.indexOf('\'') < 0)) {
            return string;
        }

        int n = string.length();
        StringBuffer sb = new StringBuffer(n);
        for(int i = 0; i < n; i ++) {
            char ch = string.charAt(i);
            if(ch == '\'') {
                sb.append('\'');
            }
            sb.append(ch);
        }
        return sb.toString();
    }
}
