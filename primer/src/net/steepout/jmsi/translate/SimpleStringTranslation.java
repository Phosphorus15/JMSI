package net.steepout.jmsi.translate;

import java.util.LinkedList;

import net.steepout.jmsi.ControlCode;
import net.steepout.jmsi.JMSI;

public class SimpleStringTranslation {
	public SimpleStringTranslation() {

	}

	public Object[] parseString(String str) {
		LinkedList<Object> objs = new LinkedList<Object>();
		boolean linking = false;
		String data = "";
		for (String x : str.split(" ")) {
			if (ControlCode.isCode(x)) {
				objs.add(ControlCode.valueOf(x));
			} else {
				if (linking) {
					if (x.endsWith("\"")) {
						linking = false;
						data += " " + x;
						objs.add(data);
						data = "";
					} else
						data += " " + x;
				} else {
					if (startOfString(x)) {
						linking = true;
						data += x;
					} else {
						objs.add(x);
					}
				}
			}
		}
		if (str.equals("")) {
			return new Object[] { ControlCode.SKIP };
		}
		return objs.toArray();
	}

	private static boolean startOfString(String str) {
		return str.charAt(0) == '"' && (str.charAt(str.length() - 1) != '"');
	}
}
