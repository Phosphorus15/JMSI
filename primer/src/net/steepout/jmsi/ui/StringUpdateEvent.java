package net.steepout.jmsi.ui;

import java.awt.AWTEvent;

public class StringUpdateEvent extends AWTEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String string;
	
	String color;

	public StringUpdateEvent(Object obj,String str,String clr) {
		super(obj,(int) serialVersionUID);
		string = str;
		color = clr;
		// TODO Auto-generated constructor stub
	}
	
	public String getString(){
		return string;
	}
	
	public String getColor(){
		return color;
	}
	
}