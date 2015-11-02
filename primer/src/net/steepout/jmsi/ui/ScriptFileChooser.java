package net.steepout.jmsi.ui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class ScriptFileChooser extends JFileChooser{
	public ScriptFileChooser(){
		this.setFileSelectionMode(JFileChooser.FILES_ONLY);
		this.setMultiSelectionEnabled(false);
		this.addChoosableFileFilter(new FileFilter(){

			@Override
			public boolean accept(File f) {
				// TODO Auto-generated method stub
				if(f.isDirectory())return true;
				else if(f.getName().endsWith(".jmsi"))return true;
				return false;
			}

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return ".jmsi - JMSI source file";
			}
			
		});
		this.addChoosableFileFilter(new FileFilter(){

			@Override
			public boolean accept(File f) {
				// TODO Auto-generated method stub
				if(f.isDirectory())return true;
				else if(f.getName().endsWith(".sio"))return true;
				return false;
			}

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return ".sio - JMSI byte code file";
			}
			
		});
		this.removeChoosableFileFilter(this.getAcceptAllFileFilter());
		this.setFileFilter(this.getChoosableFileFilters()[0]);
	}
}