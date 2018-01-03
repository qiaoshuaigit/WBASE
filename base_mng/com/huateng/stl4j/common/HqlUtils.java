package com.huateng.stl4j.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HqlUtils {

    private static String removeSelect(String hql) {
            int beginPos = hql.toLowerCase().indexOf("from ");
            return hql.substring(beginPos);
    }
   
    private static String removeOrders(String hql) {
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
                m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }
    
    public static String transferCountHQL(String hql){
    	return "SELECT COUNT(*) " + removeOrders(removeSelect(hql));
    }
}
