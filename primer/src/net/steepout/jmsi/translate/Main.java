package net.steepout.jmsi.translate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import net.steepout.jmsi.BytecodeExecutor;
import net.steepout.jmsi.JMSI;
import net.steepout.jmsi.JMSIParameter;

public class Main {
	@SuppressWarnings("resource")
	public static void main(String... args) throws ClassNotFoundException {
		if (args.length < 3) {
			throw new SecurityException("Access denied!");
		}
		args = Enviroment.parse(args);
		if (args.length == 0) {
			Main.out("No such commad , try 'SVM -console'");
		}
		if (args.length == 1) {
			if (args[0].equals("-console")) {
				new Console(System.in).start();
			}
		}
		if (args.length == 2) {
			if (args[0].equals("-compile")) {
				new Compiler().compile(Compiler.createIn(args[1]), Compiler.creatOut(args[1]));
				System.out.println("compile finished [" + args[1] + "]");
				return;
			} else if (args[0].equals("-execute")) {
				new Executor(createIn(args[1])).execute();
			}
		}
	}

	public static void _main(String[] args) {
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		BytecodeTranslation bytec = new BytecodeTranslation();
		// System.out.println(bytec.String2byte("INVOKE OBJECT out BY println IN
		// java.io.PrintStream WITH START str END PARAM START java.lang.String
		// END\nEXIT abc"));
		// System.out.println(bytec.byte2String(bytec.String2byte("OBJECT START
		// str out END WITH java.lang.String")));
		// System.out.println(new Base64().encode("INVOKE OBJECT out BY println
		// IN java.io.PrintStream WITH START str END PARAM START
		// java.lang.String END"));
		// System.out.println("AQMgb3V0CCBwcmludGxuCSBqYXZhLmlvLlByaW50U3RyZWFtBw0gc3RyDgYNIGphdmEubGFuZy5T".length());
		String line = "hello world";
		System.out.println(new StringBuilder(line).insert(5, '\n').toString());
	}

	public static void out(String str) {
		System.out.println("[SVM] " + str);
	}

	protected static FileInputStream createIn(String name) {
		try {
			if (!name.contains(":"))
				return new FileInputStream(Enviroment.workpath + name);
			else
				return new FileInputStream(name);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
