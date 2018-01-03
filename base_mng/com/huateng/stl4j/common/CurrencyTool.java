package com.huateng.stl4j.common;

import java.math.BigDecimal;

import om.util.OmUtils;

public class CurrencyTool {
	public static String toString(double value) {
		return CurrencyTool.toString(String.format("%.2f", value));
	}
	
	public static String toString(Double value) {
		if(null == value) {
			return "";
		}
		return CurrencyTool.toString(String.format("%.2f", value));
	}
	
	public static String toString(BigDecimal value) {
		if(null == value) {
			return "";
		}
		return CurrencyTool.toString(String.format("%.2f", value));
	}
	
	private static String toString(String value) {
		if(OmUtils.isEmpty(value)) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		int digit = value.indexOf("."); // 取得小数点的位置
		if(digit < 0) { //无小数
			for(int i = value.length(); i > 0; i -= 3) {
				result.insert(0, value.substring(i, i - 3));
				i -= 3;
				result.insert(0, ",");
			}
			result.append(".00");
		}
		else {
			String tmpValue = value.substring(0, digit);
			for(int i = tmpValue.length(); i > 0; i -= 3) {
				if(i >= 3) {
					result.insert(0, tmpValue.substring(i - 3, i));
				}
				else {
					result.insert(0, tmpValue.substring(0, i));
				}
				result.insert(0, ",");
			}
			result.append(value.subSequence(digit, digit + 3));
		}
		if(result.charAt(0) == ',') {
			return result.substring(1);
		}
		else {
			return result.toString();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(CurrencyTool.toString(new BigDecimal(123456789.12)));
	}
}
