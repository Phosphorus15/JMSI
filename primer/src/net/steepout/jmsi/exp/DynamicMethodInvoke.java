package net.steepout.jmsi.exp;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

import net.steepout.jmsi.ControlCode;
import net.steepout.jmsi.DynamicObject;
import net.steepout.jmsi.JMSI;
import net.steepout.jmsi.MallocUtil;
import net.steepout.jmsi.reflect.ReflectionUtil;

public class DynamicMethodInvoke implements ExpressionParser{

	@Override
	public Object parse(ControlCode control, Object[] obj) {
		// TODO Auto-generated method stub
		MallocUtil util = new MallocUtil(this);
		LinkedList<DynamicObject> objs = new LinkedList<DynamicObject>();
		String target = "";
		String method = null;
		Object parent = util.getObject((String) obj[0]).getValue();
		method = (String) obj[2];
		target = (String) obj[4];
		int x;
		for (x = 7; x != obj.length; x++) {
			if (obj[x] instanceof ControlCode) {
				if (ControlCode.END.equals(obj[x]))
					break;
			} else {
				objs.add(util.getObject((String) obj[x]));
			}
		}
		boolean custom = false;
		if (obj.length - 1 > x) {
			custom = true;
		}
		Class<? extends Object> clazz[] = new Class[objs.size()];
		Object param[] = new Object[objs.size()];
		if (JMSI.DEBUG)
			JMSI.out("param size " + objs.size());
		for (int i = 0; i != objs.size(); i++) {
			if (!custom)
				clazz[i] = objs.get(i).getType();
			param[i] = objs.get(i).getValue();
		}
		if(custom)
		for (int i = x + 3; i - x - 3 != clazz.length; i++) {
			Class cl = null;
			String arg = (String) obj[i];
			if (arg.startsWith("raw:")) {
				try {
					cl = (Class)new ReflectionUtil( Class.forName(arg.replace("raw:", ""))).get("TYPE");
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					JMSI.outerr("[ERROR] "+e.toString());
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					JMSI.outerr("[ERROR] "+e.toString());
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					JMSI.outerr("[ERROR] "+e.toString());
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					JMSI.outerr("[ERROR] "+e.toString());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					JMSI.outerr("[ERROR] "+e.toString());
				}
			} else {
				try {
					cl = Class.forName(arg);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					JMSI.outerr("[ERROR] "+e.toString());
				}
			}
			clazz[i - 3 - x] = cl;
			if (JMSI.DEBUG)
				JMSI.out("invoke with : "
						+ clazz[i - 3 - x].getName() + " - "
						+ param[i - 3 - x].toString());
		}
		try {
			if (ControlCode.FOR.equals(obj[obj.length - 2])) {
				return new ReflectionUtil(target).invoke(parent, method, clazz,
						param, (String) obj[obj.length - 1]);
			} else
				return new ReflectionUtil(target).invoke(parent, method, clazz,
						param, null);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			JMSI.outerr("[ERROR] "+e.toString());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			JMSI.outerr("[ERROR] "+e.toString());
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			JMSI.outerr("[ERROR] "+e.toString());
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			JMSI.outerr("[ERROR] "+e.toString());
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			JMSI.outerr("[ERROR] "+e.toString());
		}
		return null;
	}

}
