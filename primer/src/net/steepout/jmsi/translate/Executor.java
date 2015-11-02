package net.steepout.jmsi.translate;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import net.steepout.jmsi.BytecodeExecutor;
import net.steepout.jmsi.JMSI;

public class Executor {
	InputStream in;

	public Executor(FileInputStream stream) {
		in = stream;
	}

	public void execute() {
		try {
			Class.forName("net.steepout.jmsi.JMSI");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BytecodeExecutor executor = new BytecodeExecutor();
		BytecodeTranslation translate = new BytecodeTranslation();
		SimpleStringTranslation strt = new SimpleStringTranslation();
		BufferedReader scanner = new BufferedReader(new InputStreamReader(in));
		String in;
		try {
			while ((in = scanner.readLine()) != null) {
				if (JMSI.DEBUG)
					Main.out("read in : " + in);
				executor.execute(strt.parseString(translate.byte2String(in)));
			}
			// System.out.println("no more data!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			scanner.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}