package net.steepout.jmsi;

import java.util.Arrays;

import net.steepout.jmsi.exp.ConstructObject;
import net.steepout.jmsi.exp.DynamicMethodInvoke;
import net.steepout.jmsi.exp.ObjectDefination;
import net.steepout.jmsi.exp.ObjectFree;
import net.steepout.jmsi.exp.ObjectRetype;
import net.steepout.jmsi.exp.StaticMethodInvoke;
import net.steepout.jmsi.exp.ValueGet;
import net.steepout.jmsi.exp.ValueSet;

public class BytecodeExecutor {
	public Object execute(Object[] bytecode) {
		if (JMSI.DEBUG) {
			JMSI.out("executing command : " + Arrays.toString(bytecode));
		}
		ControlCode control = (ControlCode) bytecode[0];
		if (control.equals(ControlCode.OBJECT)) {
			new ObjectDefination().parse(control, cut(bytecode, 1));
			return 0;
		} else if (control.equals(ControlCode.TYPE)) {
			new ObjectRetype().parse(control, cut(bytecode, 1));
			return 0;
		} else if (control.equals(ControlCode.INVOKE)) {
			if (bytecode[1] instanceof ControlCode && ControlCode.STATIC.equals(bytecode[1])) {
				return new StaticMethodInvoke().parse(control, cut(bytecode, 3));
			} else if (bytecode[1] instanceof ControlCode && ControlCode.OBJECT.equals(bytecode[1])) {
				return new DynamicMethodInvoke().parse(control, cut(bytecode, 2));
			}
		} else if (control.equals(ControlCode.GET)) {
			if (JMSI.DEBUG)
				JMSI.out("invoked 'get' command");
			return new ValueGet().parse(control, cut(bytecode, 1));
		} else if (control.equals(ControlCode.SET)) {
			return new ValueSet().parse(control, cut(bytecode, 1));
		} else if (control.equals(ControlCode.CONSTRUCT)) {
			return new ConstructObject().parse(ControlCode.CONSTRUCT, cut(bytecode, 2));
		} else if (control.equals(ControlCode.FREE)) {
			return new ObjectFree().parse(control, cut(bytecode, 1));
		} else if (control.equals(ControlCode.EXIT)) {
			System.exit(0);
		} else if (control.equals(ControlCode.SKIP)) {
			return new Object();
		} else if (control.equals(ControlCode.LOG)) {
			JMSI.out(new MallocUtil(this).getObject((String) bytecode[2]).toString());
		}
		if (JMSI.DEBUG)
			JMSI.outerr("[ERROR] " + "no such command!");
		return false;
	}

	private Object[] cut(Object[] objs, int index) {
		Object[] param = new Object[objs.length - index];
		for (int x = index; x != objs.length; x++) {
			param[x - index] = objs[x];
		}
		return param;
	}
}