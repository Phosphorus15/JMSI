package net.steepout.jmsi.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import net.steepout.jmsi.JMSI;

public class NetService implements Runnable {

	ServerSocket socket;

	public NetService() {
		try {
			socket = new ServerSocket(7046);
			socket.setSoTimeout(1500);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			if (Thread.currentThread().isInterrupted())
				return;
			try {
				Socket s = socket.accept();
				//System.out.println("connected!");
				int state = s.getInputStream().read();
				boolean isEncode = (state == 0) ? false : true;
				new Thread(new CommandInputSocket(s, isEncode)).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
	}

}