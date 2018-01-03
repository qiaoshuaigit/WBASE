package com.huateng.datafile;

import java.math.BigDecimal;

public interface IFileExporter {
	public void writeLine(Object bean) throws Exception;
	public int count();
	public int getCount();
	public BigDecimal sum(BigDecimal value);
	public BigDecimal getSum();
	public void close();
}
