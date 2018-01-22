package br.com.fr.cupuama.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Criptografia {

	private static Log logger = LogFactory.getLog(Criptografia.class);

	private static final String ALGORITIMO = "md5";

	private static final String HEX_DIGITS = "0123456789abcdef";

	public static String criptografar(String senha) {
		byte[] b = null;
		String str = senha;

		try {
			b = digest(str.getBytes(), ALGORITIMO);
		} catch (NoSuchAlgorithmException e) {
			logger.error("", e);
			return null;
		}
		return byteArrayToHexString(b);
	}

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < b.length; i++) {
			int j = b[i] & 0xFF;
			buf.append(HEX_DIGITS.charAt(j / 16));
			buf.append(HEX_DIGITS.charAt(j % 16));
		}
		return buf.toString();
	}

	private static byte[] digest(byte[] input, String palgoritmo1) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(palgoritmo1);
		md.reset();
		return md.digest(input);
	}

	public String generateSecurityCode() {

		/*
		 * Random r = new Random(System.currentTimeMillis()); StringBuffer stb =
		 * new StringBuffer(); for (int i = 0; i < 25; i++) { boolean isNumber =
		 * (r.nextInt(2) == 0 ? false : true); if (isNumber)
		 * stb.append(r.nextInt(10)); else { char c = (char) (65 +
		 * r.nextInt(25)); stb.append(c); } } return stb.toString();
		 */
		return UUID.randomUUID().toString();
	}
}