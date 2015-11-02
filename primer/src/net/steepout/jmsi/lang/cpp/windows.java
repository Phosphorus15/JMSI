package net.steepout.jmsi.lang.cpp;

public class windows {
	public static native short system(String str);

	public static native HWND findWindow(String clazz, String name);

	public class HWND {
		int address;
		public HWND(int a){
			address = a;
		}
	}
}