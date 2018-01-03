package com.huateng.datafile;

import java.math.BigDecimal;
import java.util.List;

public interface IFileLoader {
	public boolean next(Object bean) throws Exception;
	public List<String> lineSplit(String line) throws Exception;
	public int count();
	public int getCount();
	public BigDecimal sum(BigDecimal value);
	public BigDecimal getSum();
	public void close();
}
