package com.huateng.view.freemarker;

import java.util.List;


import com.huateng.common.Code;
import om.util.OmUtils;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class DecodeStrMethod implements TemplateMethodModel {
	public DecodeStrMethod() {
		super();
	}
	
	public Object exec(List args) throws TemplateModelException {
		if (args.size() != 1) {
			throw new TemplateModelException("Wrong arguments");
		}
		String string = (String) args.get(0);
		try {
			if (OmUtils.isEmpty(string)) {
				return "";
			} else {
				return Code.decode(string);
			}
		} catch (Exception ex) {
			throw new TemplateModelException(ex.getMessage(),ex);
		}
	}
}
