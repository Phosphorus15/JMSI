package net.steepout.jmsi.exp;

import net.steepout.jmsi.ControlCode;

public interface ExpressionParser {
	public Object parse(ControlCode control, Object[] obj) throws Throwable;
}