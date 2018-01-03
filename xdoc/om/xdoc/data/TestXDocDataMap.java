package om.xdoc.data;

import java.util.HashMap;
import java.util.Map;

public class TestXDocDataMap implements IXDocDataMap {
	public Map<String, String> getXDocDataMap(Map<String, String> inParam) {
		System.out.println(inParam);
		Map<String, String> map = new HashMap<String, String>();  
        map.put("secondDate", "系统日期");
        map.put("TestField", "吉林省农村信用社");
        return map;
	}

	public String getXDocContent(Map<String, String> inParam) {
		// TODO Auto-generated method stub
		return null;
	}
}
