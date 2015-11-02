package net.steepout.jmsi.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.steepout.jmsi.translate.BytecodeTranslation;
import net.steepout.jmsi.translate.Compiler;
import net.steepout.jmsi.translate.SimpleStringTranslation;

public class UIMain{
	static TransferManager transfer;
	static Thread thread_main;
	static ScriptFileChooser chooser;
	static CommandFrame command;
	public static void main(String[] args){
		thread_main = Thread.currentThread();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ConfigDialog dialog = new ConfigDialog();
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setVisible(true);
		command = new CommandFrame();
		command.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		transfer = new TransferManager(command,dialog);
		transfer.start();
		chooser = new ScriptFileChooser();
		while(!transfer.isInterrupted()){
			
		}
		System.gc();
		return;
	}
	public static void callCommandExecute(String str){
		transfer.println(str);
	}
	public static void callScriptExecute(){
		chooser.showOpenDialog(command);
		File f = chooser.getSelectedFile();
		if(f!=null){
			chooser.setSelectedFile(null);
			boolean bytecode = f.getName().endsWith("sio");
			SimpleStringTranslation translate1 = new SimpleStringTranslation();
			BytecodeTranslation translate2 = new BytecodeTranslation();
			try {
				Scanner reader = new Scanner(f);
				String str;
				while(reader.hasNextLine()){
					str = reader.nextLine();
					if(!bytecode){
						str = translate2.String2byte(str);
					}
					str = translate2.byte2String(str);
					transfer.println(str);
				}
				reader.close();
				command.append("[INFO] Script executed ","black");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				command.append("[ERROR] "+e.toString(),"red");
			}
		}else{
			command.append("[INFO] No file selected,cancelled!","black");
		}
	}
}