package net.steepout.jmsi;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.steepout.jmsi.DataStorage.Malloc;

public class JMSI {
	public static boolean DEBUG;
	public static boolean NET_STAT;
	public static PrintWriter out;
	static {
		out = null;
		NET_STAT = false;
		DEBUG = JMSIParameter.DEBUG;
		format = new SimpleDateFormat("H:m:s z");
		if (JMSI.DEBUG)
			_out00("loading [Java Method String Invoker(JMSI) interface]...");
		try {
			Class.forName("net.steepout.jmsi.DataStorage");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.hashCode();
		}
		if (JMSI.DEBUG)
			_out00("Storage Created!");
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (JMSI.DEBUG)
					_out01("saving profiles");
				if(JMSI.DEBUG)
					_out01("running garbage collector...");
				DataStorage._func_646573746f7279_p();
				if(JMSI.DEBUG)
					_out01("Phosphorus15 All rights reserved!");
			}
			
		}));
	}
	static SimpleDateFormat format;

	protected static synchronized Malloc costants(Malloc malloc) {
		malloc.put("True", new DynamicObject(true, "True"));
		malloc.put("False", new DynamicObject(false, "False"));
		malloc.put("NORMAL_EXIT", new DynamicObject(new Integer(0),
				"NORMAL_EXIT"));
		malloc.put("hello_world", new DynamicObject("Hello World!",
				"hello_world"));
		malloc.forbidEdit();
		return malloc;
	}

	public static synchronized Object parseDefault(String str) {
		if (str.startsWith("\"") && str.endsWith("\"")) {
			return str.substring(1, str.length() - 1);
		} else if (str.equalsIgnoreCase("true")) {
			return true;
		} else if (str.equalsIgnoreCase("false")) {
			return false;
		} else if (endsWith(str, "i")) {
			return Integer.parseInt(str.substring(0, str.length() - 1));
		} else if (endsWith(str, "l")) {
			return Long.parseLong(str.substring(0, str.length() - 1));
		} else if (endsWith(str, "s")) {
			return Short.parseShort(str.substring(0, str.length() - 1));
		} else if (endsWith(str, "b")) {
			return Byte.parseByte(str.substring(0, str.length() - 1));
		} else if (endsWith(str, "f")) {
			return Float.parseFloat(str.substring(0, str.length() - 1));
		} else if (endsWith(str, "d")) {
			return Double.parseDouble(str.substring(0, str.length() - 1));
		} else {
			return new Object();
		}
	}

	private static boolean endsWith(String s, String end) {
		return s.endsWith(end.toLowerCase()) || s.endsWith(end.toUpperCase());
	}

	public static void out(String str) {
		//System.out.println("[SIVM] [" + time() + "] " + str);
		if(out!=null&&NET_STAT){
		out.println("[SIVM] [" + time() + "] " + str);
		out.flush();
		}
		else if(DEBUG&&!NET_STAT)
		System.out.println("[SIVM] [" + time() + "] " + str);
	}
	
	public static void outerr(String str) {
		str = str.replaceAll("[ERROR] ", "");
		//System.err.println("[ERROR] [" + time() + "] " + str);
		if(out!=null&&NET_STAT){
		out.println("[ERROR] [" + time() + "] " + str);
		out.flush();
		}
		else if(DEBUG&&!NET_STAT)
		System.out.println("[ERROR] [" + time() + "] " + str);
	}

	private static void _out00(String str) {
		if(out!=null&&NET_STAT)
		out.println("[SIVM] [INIT] " + str);
		else if(DEBUG&&!NET_STAT)
		System.out.println("[SIVM] [INIT] " + str);
	}
	
	private static void _out01(String str){
		if(out!=null&&NET_STAT)
			out.println("[SIVM] [SHUTDOWN] " + str);
			else if(DEBUG&&!NET_STAT)
		System.out.println("[SIVM] [SHUTDOWN] " + str);
	}

	private static String time() {
		return format.format(new Date());
	}
}