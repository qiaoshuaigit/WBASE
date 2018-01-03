package com.huateng.view.freemarker;

import java.util.List;

import om.util.OmUtils;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class GetNumberMethor implements TemplateMethodModel {
	public GetNumberMethor() {
		super();
	}
	
	public Object exec(List args) throws TemplateModelException {
		if (args.size() != 1) {
			throw new TemplateModelException("Wrong arguments");
		}
		String string = OmUtils.trim((String) args.get(0));
		try {
			if (OmUtils.isEmpty(string)) {
				return "0";
			} else {
				StringBuilder rs = new StringBuilder();
				for(int i = 0; i < string.length(); i ++) {
					char c = string.charAt(i);
					if(c >= '0' && c <= '9') {
						rs.append(c);
					}
				}
				return rs.toString();
			}
		} catch (Exception ex) {
			throw new TemplateModelException(ex.getMessage(),ex);
		}
	}
}
