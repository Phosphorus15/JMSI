package net.steepout.jmsi.translate;

import java.io.InputStream;
import java.util.Scanner;

import net.steepout.jmsi.BytecodeExecutor;
import net.steepout.jmsi.ControlCode;

public class Console{
	InputStream in;
	public Console(InputStream i){
		in = i;
	}
	public void start(){
		try {
			Class.forName("net.steepout.jmsi.JMSI");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner scan = new Scanner(in);
		SimpleStringTranslation translate = new SimpleStringTranslation();
		BytecodeExecutor executor = new BytecodeExecutor();
		while(true){
			System.out.print("--> ");
			String cmd = scan.nextLine();
			if(cmd==null)
				executor.execute(new Object[]{ControlCode.EXIT});
			executor.execute(translate.parseString(cmd));
		}
	}
}