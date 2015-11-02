package net.steepout.jmsi.ui;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

import net.steepout.jmsi.translate.BytecodeTranslation;

public class TransferManager extends Thread {
	CommandFrame frame;
	ConfigDialog dialog;
	OutputStream out = null;
	InputStream in = null;
	BufferedReader reader;
	PrintWriter printer;
	boolean encode;
	boolean disc = false;

	public TransferManager(CommandFrame f, ConfigDialog d) {
		frame = f;
		dialog = d;
	}

	public void print(String str) {
		if (str.equalsIgnoreCase("exit")) {
			int choose = JOptionPane.showConfirmDialog(frame,
					"Are you sure you want to terminate the remote server?\nThis is irreversible!", "Warning",
					JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
			if(choose==JOptionPane.NO_OPTION){
				frame.append("[WARN] attemped to shutdown server but cancelled","DarkOrange");
				return;
			}else{
				frame.append("[WARN] server is shutted down!","DarkOrange");
				disc = true;
				frame.shutdown();
			}
		}else if(str.equalsIgnoreCase("disconnect")){
			frame.append("[INFO] disconnected from server ["+dialog.address()+":"+dialog.port()+"]!");
			disc = true;
		}
		if (encode)
			str = new BytecodeTranslation().String2byte(str);
		printer.print(str);
	}

	public void println(String str) {
		if (str.equalsIgnoreCase("exit")) {
			int choose = JOptionPane.showConfirmDialog(frame,
					"Are you sure you want to terminate the remote server?\nThis is irreversible!", "Warning",
					JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
			if(choose==JOptionPane.NO_OPTION){
				frame.append("[WARN] attemped to shutdown server but cancelled","DarkOrange");
				return;
			}else{
				frame.append("[WARN] server is shutted down!","DarkOrange");
				disc = true;
				frame.shutdown();
			}
		}else if(str.equalsIgnoreCase("disconnect")){
			frame.append("[INFO] disconnected from server ["+dialog.address()+":"+dialog.port()+"]!");
			disc = true;
		}
		if (encode)
			str = new BytecodeTranslation().String2byte(str);
		System.out.println("printing " + str);
		printer.println(str);
		printer.flush();
		if (printer.checkError()) {
			frame.append("[ERROR] Printer I/O Error!");
		}
	}

	public void run() {
		frame.setVisible(true);
		encode = dialog.base64();
		frame.append("[INFO] Connecting...");
		System.out.println("[INFO] Connecting...");
		System.out.println(frame.textpane.getText());
		try {
			Socket conn = new Socket(InetAddress.getByName(dialog.address()), dialog.port());
			out = conn.getOutputStream();
			in = conn.getInputStream();
			printer = new PrintWriter(out);
		} catch (IOException e) {
			if(!disc)
			frame.append("[ERROR] " + e.toString(), "red");
			return;
		}
		try {
			out.write(dialog.base64() ? 1 : 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if(!disc)
			frame.append("[ERROR] " + e.toString(), "red");
			return;
		}
		frame.append("[INFO] connected to server " + dialog.address() + ":" + dialog.port(), "green");
		reader = new BufferedReader(new InputStreamReader(in));
		String recv = "";
		while (!isInterrupted()) {
			try {
				System.out.println("waiting..");
				recv = reader.readLine();
				System.out.println("recieved " + recv);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				if(!disc)
				frame.append("[ERROR] " + e.toString(), "red");
				interrupt();
			}
			if (recv == null) {
				if(!disc)
				frame.append("[ERROR] recived null string!", "red");
				return;
			}
			if (recv.equalsIgnoreCase("DISCONNECT")) {
				interrupt();
			} else if (recv.startsWith("[ERROR]")) {
				frame.append(recv, "red");
			} else {
				frame.append(recv);
			}
		}
	}
}