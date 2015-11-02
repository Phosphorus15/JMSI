package net.steepout.jmsi.translate;

import java.io.IOException;

public class Base64{
	static sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	static sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
	public String encode(String str){
		return encoder.encode(str.getBytes());
	}
	public String decode(String str){
		try {
			return new String(decoder.decodeBuffer(str));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.hashCode();
		}
		return "ERROR";
	}
}