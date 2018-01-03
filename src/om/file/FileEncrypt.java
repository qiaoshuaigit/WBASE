package om.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class FileEncrypt {
	private static String _KEY_VALUE_ = "~!@#$%)(*&^%1234";
	
	public static void main(String[] args) throws Exception {
		String srcFile = "G:/FileEncrypt.java";
		String destFile = "G:/FileEncrypt.java.1";
		String destFile1 = "G:/FileEncrypt.1.java";
		FileEncrypt.encrypt(srcFile, destFile);
		FileEncrypt.decrypt(destFile, destFile1);
	}
	
	/**
	 * 把文件srcFile加密后存储为destFile
	 * 
	 * @param srcFile
	 * @param destFile
	 * @throws Exception
	 */
	public static void encrypt(String srcFile, String destFile)
			throws Exception {
		Key privateKey = getKey();
		SecureRandom sr = new SecureRandom();
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec spec = new IvParameterSpec(privateKey.getEncoded());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey, spec, sr);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(destFile);
			int readLength = 0;
			byte[] b = new byte[2048];
			while ((readLength = fis.read(b)) != -1) {
				fos.write(cipher.doFinal(b, 0, readLength));
			}
		} finally {
			if(null != fos) try { fos.close(); } catch(Exception e) {}
			if(null != fis) try { fis.close(); } catch(Exception e) {}
		}
	}

	/**
	 * 把文件srcFile解密后存储为destFile
	 * 
	 * @param srcFile
	 * @param destFile
	 * @throws Exception
	 */
	public static void decrypt(String srcFile, String destFile)
			throws Exception {
		Key privateKey = getKey();
		SecureRandom sr = new SecureRandom();
		Cipher ciphers = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec spec = new IvParameterSpec(privateKey.getEncoded());
		ciphers.init(Cipher.DECRYPT_MODE, privateKey, spec, sr);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(destFile);
			int readLength = 0;
			byte[] b = new byte[2064];
			while ((readLength = fis.read(b)) != -1) {
				fos.write(ciphers.doFinal(b, 0, readLength));
			}
		} finally {
			if(null != fos) try { fos.close(); } catch(Exception e) {}
			if(null != fis) try { fis.close(); } catch(Exception e) {}
		}
	}
	
	/**
	 * 把文件输入流is 解密后 返回新生成的 输入流 
	 * 
	 * @param is
	 * @return newIs
	 * @throws Exception
	 */
	public static InputStream decrypt(InputStream is)
			throws Exception {
		Key privateKey = getKey();
		SecureRandom sr = new SecureRandom();
		Cipher ciphers = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec spec = new IvParameterSpec(privateKey.getEncoded());
		ciphers.init(Cipher.DECRYPT_MODE, privateKey, spec, sr);
		InputStream fis = null;
		ByteArrayOutputStream os = null;
		try {
			fis = is;
			os = new ByteArrayOutputStream();
			int readLength = 0;
			byte[] b = new byte[2064];
			while ((readLength = fis.read(b)) != -1) {
				os.write(ciphers.doFinal(b, 0, readLength));
			}
			return new ByteArrayInputStream(os.toByteArray());
		} finally {
			if(null != fis) try { fis.close(); } catch(Exception e) {}
			
		}
		
	}
	
	/**
	 * 生成密钥
	 * @return
	 */
	private static Key getKey() throws Exception {
		byte[] b = _KEY_VALUE_.getBytes();
		SecretKeySpec dks = new SecretKeySpec(b, "AES");
		return dks;
	}

}
