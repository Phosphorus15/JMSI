package net.steepout.jmsi.net;

import java.net.Socket;

import net.steepout.jmsi.BytecodeExecutor;
import net.steepout.jmsi.ControlCode;
import net.steepout.jmsi.JMSI;
import net.steepout.jmsi.translate.BytecodeTranslation;
import net.steepout.jmsi.translate.SimpleStringTranslation;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;

public class CommandInputSocket implements Runnable {
	Socket socket;
	boolean encode;

	public CommandInputSocket(Socket s, boolean e) {
		socket = s;
		encode = e;
	}

	public void run() {
		try {
			JMSI.NET_STAT = true;
			JMSI.DEBUG = true;
			//System.out.println("into!");
			BytecodeExecutor executor = new BytecodeExecutor();
			SimpleStringTranslation translate1 = new SimpleStringTranslation();
			BytecodeTranslation translate2 = new BytecodeTranslation();
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			JMSI.out = new PrintWriter(socket.getOutputStream());
			//System.out.println("started!");
			while (true) {
				if (Thread.currentThread().isInterrupted())
					return;
				String str = reader.readLine();
				//System.out.println("recieved " + str);
				if (Thread.currentThread().isInterrupted())
					return;
				if (encode)
					str = translate2.byte2String(str);
				Object[] objs = translate1.parseString(str);
				if (objs[0].equals(ControlCode.DISCONNECT)) {
					reader.close();
					JMSI.out = null;
					JMSI.NET_STAT = true;
					JMSI.NET_STAT = false;
					return;
				} else {
					try {
						executor.execute(objs);
					} catch (Exception e) {
						JMSI.outerr("[ERROR] "+e.toString());
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// JMSI.outerr("[ERROR] "+e.toString());
			JMSI.outerr("[ERROR] "+e.toString());
		}
	}
}