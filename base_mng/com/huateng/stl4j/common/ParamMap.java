package com.huateng.stl4j.common;

import java.util.HashMap;
import java.util.Map;

public class ParamMap {
	private Map<String, Object> map = new HashMap<String, Object>();
	
	public ParamMap(String key, Object value) {
		map.put(key, value);
	}
	
	public ParamMap addParam(String key, Object value) {
		map.put(key, value);
		return this;
	}
	
	public ParamMap removeParam(String key){
		map.remove(key);
		return this;
	}
	
	public Object getParamValue(String key) {
		return map.get(key);
	}
	
	public ParamMap clear() {
		map.clear();
		return this;
	}
	
	public final Map<String, Object> getMapTarget() {
		return map;
	}
}
