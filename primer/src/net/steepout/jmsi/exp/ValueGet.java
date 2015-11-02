package net.steepout.jmsi.exp;

import net.steepout.jmsi.ControlCode;
import net.steepout.jmsi.JMSI;
import net.steepout.jmsi.MallocUtil;
import net.steepout.jmsi.reflect.ReflectionUtil;

public class ValueGet implements ExpressionParser {

	MallocUtil util = new MallocUtil(this);

	@Override
	public Object parse(ControlCode control, Object[] obj) {
		if (ControlCode.BY.equals(obj[0])) {
			try {
				return getInClass(obj);
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
			}
		} else {
			if (JMSI.DEBUG)
				JMSI.out("attemping to request malloc");
			return getInMalloc((String) obj[1]);
		}
		return null;
	}

	private Object getInClass(Object[] obj) throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		Object parent = null;
		ReflectionUtil reflect = new ReflectionUtil((String) obj[1]);
		String target = (String) obj[3];
		if (obj.length > 4) {
			parent = util.getObject((String) obj[5]);
		}
		if (parent != null)
			return reflect.get(parent, target);
		else
			return reflect.get(target);
	}

	private Object getInMalloc(String name) {
		return util.getObject(name).getValue();
	}

}
