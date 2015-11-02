package net.steepout.jmsi.translate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1 {
	public String encode(String str) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
			return new String(digest.digest(str.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ERROR";
	}
}