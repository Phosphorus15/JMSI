package net.steepout.jmsi.exp;

import net.steepout.jmsi.ControlCode;
import net.steepout.jmsi.DynamicObject;
import net.steepout.jmsi.JMSI;
import net.steepout.jmsi.MallocUtil;
import net.steepout.jmsi.reflect.ReflectionUtil;

public class ValueSet implements ExpressionParser {

	MallocUtil util = new MallocUtil(this);

	@Override
	public Object parse(ControlCode control, Object[] obj) {
		// TODO Auto-generated method stub
		if (ControlCode.OBJECT.equals(obj[0])) {
			return setObject(obj);
		} else {
			return setInClass(obj);
		}
	}

	private boolean setObject(Object[] obj) {
		String name = (String) obj[1];
		DynamicObject target = util.getObject(name);
		Object setter = new Object();
		if (ControlCode.DEFAULT.equals(obj[2])) {
			setter = JMSI.parseDefault((String) obj[3]);
		} else {
			setter = new ValueGet().parse(ControlCode.GET, cut(obj, 3));
		}
		target.setValue(setter);
		util.setObject(name, target);
		return true;
	}

	private boolean setInClass(Object[] obj) {
		String clazz;
		String field;
		Object parent = null;
		Object setter = null;
		clazz = (String) obj[1];
		field = (String) obj[3];

		ReflectionUtil reflect = new ReflectionUtil(clazz);
		if (ControlCode.WITH.equals(obj[4])) {
			parent = util.getObject((String) obj[5]).getValue();
			if (obj[6].equals(ControlCode.DEFAULT)) {
				setter = JMSI.parseDefault((String) obj[7]);
			} else
				setter = new ValueGet().parse(ControlCode.GET, cut(obj, 7));
		} else {
			if (obj[4].equals(ControlCode.DEFAULT)) {
				setter = JMSI.parseDefault((String) obj[5]);
			} else
				setter = new ValueGet().parse(ControlCode.GET, cut(obj, 5));
		}
		try {
			if (parent != null)
				reflect.set(parent, field, setter);
			else
				reflect.set(field, setter);
		} catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			JMSI.outerr("[ERROR] "+e.toString());
			return false;
		}
		return true;
	}

	private Object[] cut(Object[] objs, int index) {
		Object[] param = new Object[objs.length - index];
		for (int x = index; x != objs.length; x++) {
			param[x - index] = objs[x];
		}
		return param;
	}

}