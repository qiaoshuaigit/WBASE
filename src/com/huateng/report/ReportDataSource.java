package com.huateng.report;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ReportDataSource implements JRDataSource {
	private List data;
	private int index;
	private int count;
	
	public ReportDataSource(List data) {
		this.data = data;
		this.index = -1;
		this.count = data.size();
	}
	
	public Object getFieldValue(JRField field) throws JRException {
		Map row = (Map) data.get(index);

		Object o = row.get(field.getName());
		if (o != null && o instanceof BigDecimal) {
			BigDecimal d = (BigDecimal)o;
			o = new Double(d.doubleValue());
		}

		return o;
	}
	
	public boolean next() throws JRException {
		if (this.index >= this.count - 1)
			return false;
		else {
			this.index++;
			return true;
		}
	}
}
