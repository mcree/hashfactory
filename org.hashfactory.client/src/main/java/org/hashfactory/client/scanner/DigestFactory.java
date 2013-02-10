package org.hashfactory.client.scanner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestFactory {
	
	public DigestFactory() throws NoSuchAlgorithmException {
		MessageDigest.getInstance("SHA-256");
		MessageDigest.getInstance("MD5");
		
	}
	
	public MessageDigest createSha256Digest() {
		try {
			return MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public MessageDigest createMd5Digest() {
		try {
			return MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}
