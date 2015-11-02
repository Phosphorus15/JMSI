package net.steepout.jmsi.translate;

import net.steepout.jmsi.ControlCode;

public class BytecodeTranslation {
	Base64 base64 = new Base64();

	public String byte2String(String line) {
		int len = line.length();
		if (len > 76) {
			len = len / 76;
			for (int x = 0; x != len; x++) {
				line = new StringBuilder(line).insert((x + 1) * 76 + x, '\n').toString();
			}
		}
		line = base64.decode(line);
		for (int x = 0; x != line.length(); x++) {
			if (ControlCode.find((int) line.charAt(x)) != ControlCode.NULL) {
				line = line.replaceFirst(new String(new char[] { line.charAt(x) }),
						" " + ControlCode.find(line.charAt(x)).name());
			} else {
			}
		}
		return line.substring(1, line.length());
	}

	public String String2byte(String line) {
		if (!(line.startsWith("//") || line.startsWith("\\*") || line.startsWith("*") || line.startsWith("/*")))
			return Byte2byte(new SimpleStringTranslation().parseString(line));
		else
			return Byte2byte(new SimpleStringTranslation().parseString("SKIP"));
	}

	public String Byte2byte(Object[] bytecode) {
		String data = "";
		for (Object x : bytecode) {
			if (x instanceof ControlCode) {
				data += ((char) ControlCode.encode((ControlCode) x));
			} else {
				data += " " + (String) x;
			}
		}
		return base64.encode(data).replaceAll("\n", "").replaceAll("\r", "");
	}
}