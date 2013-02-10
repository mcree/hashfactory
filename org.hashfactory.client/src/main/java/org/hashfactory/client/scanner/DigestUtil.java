package org.hashfactory.client.scanner;

public class DigestUtil {

	public static final String digest2Hex(byte[] hash) {
		StringBuffer sb = new StringBuffer();
		for (byte b : hash) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

}
