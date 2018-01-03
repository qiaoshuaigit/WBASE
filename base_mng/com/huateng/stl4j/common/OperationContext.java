package com.huateng.stl4j.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import spc.webos.data.IMessage;

public class OperationContext {
	private Map<String, Object> data = Collections.synchronizedMap(new HashMap<String, Object>());

	public void set(String attribute, Object value) {
		synchronized(data) {
			data.put(attribute, value);
		}
	}

	public Object get(String attribute) {
		synchronized(data) {
			return data.get(attribute);
		}
	}
	
	public void addAll(Map<String, Object> map) {
		synchronized(data) {
			data.putAll(map);
		}
	}
	
	public String toString() {
		StringBuilder rs = new StringBuilder("{");
		try {
			int count = 0;
			Iterator<String> keyIt = data.keySet().iterator();
			while(keyIt.hasNext()) {
				String key = keyIt.next();
				Object value = data.get(key);
				if(count > 0) {
					rs.append(",");
				}
				count ++;
				if(null == value) {
					rs.append(key).append("=null");
					continue;
				}
				if(value instanceof java.util.List) {
					rs.append(key).append("=List.size(").append(((List)value).size()).append(")");
				}
				else if(value instanceof java.util.Map) {
					rs.append(key).append("=Map.size(").append(((Map)value).size()).append(")");
				}
				else if(value instanceof Object[]) {
					rs.append(key).append("=[].length(").append(((Object[])value).length).append(")");
				}
				else if(value instanceof IMessage) {
					rs.append(key).append("=IMessage");
				}
				else {
					rs.append(key).append("=").append(value);
				}
			}
		} catch(Exception e) {
		}
		rs.append("}");
		return rs.toString();
	}
}
