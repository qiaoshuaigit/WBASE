package com.huateng.stl4j.common;

import java.security.MessageDigest;

import org.apache.log4j.Logger;

public class CryptoUtils {
	private static Logger logger = Logger.getLogger(CryptoUtils.class);
	
	public static String MD5(String value) {
		try {
			return CryptoUtils.hash(value.getBytes("UTF-8"), "MD5");
		} catch(Exception e) {
			logger.error((new StringBuilder("Exception: ")).append(e).toString());
            return null;
		}
	}
	
	public static String hash(byte unencodedPassword[], String algorithm) {
		MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch(Exception e) {
        	logger.error((new StringBuilder("Exception: ")).append(e).toString());
            return null;
        }
        md.reset();
        md.update(unencodedPassword);
        byte encodedPassword[] = md.digest();
        StringBuffer buf = new StringBuffer();
        for(int i = 0; i < encodedPassword.length; i++) {
            if((encodedPassword[i] & 0xff) < 16)
                buf.append("0");
            buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }

        return buf.toString().toUpperCase();
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(CryptoUtils.MD5("123456"));
	}
}
