package net.steepout.jmsi.translate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

public class Compiler {
	public void compile(InputStream in, OutputStream out) {
		BytecodeTranslation translation = new BytecodeTranslation();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		PrintStream stream = new PrintStream(out);
		String str;
		try {
			while ((str = reader.readLine()) != null) {
				str = translation.String2byte(str);
				stream.println(str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	protected static FileOutputStream creatOut(String name) {
		try {
			if (!name.contains(":")) {
				name = Enviroment.workpath + name;
			}
			int index = name.lastIndexOf('.');
			if (index != -1) {
				name = name.substring(0, index);
			}
			name += ".sio";
			File f = new File(name);
			if (!f.exists())
				f.createNewFile();
			return new FileOutputStream(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}