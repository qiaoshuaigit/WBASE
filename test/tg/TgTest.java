package tg;

import com.thoughtworks.xstream.XStream;

import freemarker.template.TemplateModelException;

public class TgTest {

	/**
	 * @param args
	 * @throws TemplateModelException 
	 */
	public static void main(String[] args) {
		XStream xs = new XStream();
		//Gridbag g = new Gridbag("qqq","www","eee");
		
		//System.out.println(xs.toXML(g));
		
		String ss = "<tg.Gridbag><s1>qqq</s1><s2>www</s2><s3>eee</s3></tg.Gridbag>";
		Gridbag gb = (Gridbag) xs.fromXML(ss);
		System.out.println(gb);
		System.out.println("s1="+gb.getS1());
		System.out.println("s2="+gb.getS2());
		System.out.println("s3="+gb.getS3());
	}
	
}
