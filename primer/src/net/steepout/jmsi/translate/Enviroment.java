package net.steepout.jmsi.translate;

import net.steepout.jmsi.JMSIParameter;
import net.steepout.jmsi.net.CommandInputSocket;
import net.steepout.jmsi.net.NetService;

public class Enviroment{
	public static String workpath;
	public static String realpath;
	public static String _version;
	public static String[] parse(String... obj){
		Class load;
		try {
			load=Class.forName("net.steepout.jmsi.net.CommandInputSocket");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		try {
			load=Class.forName("net.steepout.jmsi.net.NetService");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int cuttage = 3;
		realpath = obj[0];
		workpath = obj[1];
		_version = obj[2];
		if(obj.length>3&&obj[3].equals("-debug")){
			JMSIParameter.DEBUG = true;
			cuttage ++;
		}
		return cut(obj,cuttage);
	}
	private static String[] cut(String[] objs, int index) {
		String[] param = new String[objs.length - index];
		for (int x = index; x != objs.length; x++) {
			param[x - index] = objs[x];
		}
		return param;
	}
}