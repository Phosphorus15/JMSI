package net.steepout.jmsi.reflect;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import net.steepout.jmsi.DynamicObject;
import net.steepout.jmsi.JMSI;
import net.steepout.jmsi.MallocUtil;

@SuppressWarnings("rawtypes")
public class ReflectionUtil {
	Class parent = Void.class;
	MallocUtil util = new MallocUtil(this);
	public ReflectionUtil(String clazz){
		try {
			parent = Class.forName(clazz);
			if(JMSI.DEBUG)
				JMSI.out("reflection util of ["+clazz+"] created!");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.hashCode();
		}
	}
	public ReflectionUtil(Class<?> forName) {
		parent = forName;
		// TODO Auto-generated constructor stub
	}
	public Object invoke(String method,Class[] param,Object[] obj,String _for) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		if(JMSI.DEBUG){
			JMSI.out("invoking method ["+method+"] in "+parent.getName());
		}
		Object ret= parent.getMethod(method, param).invoke(null, obj);
		if(_for!=null){
			DynamicObject o = util.getObject(_for);
			o.setValue(ret);
			util.setObject(_for, o);
		}
		return ret;
	}
	public Object invoke(Object invoker,String method,Class[] param,Object[] obj,String _for) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		if(JMSI.DEBUG){
			JMSI.out("invoking method ["+method+"] in "+parent.getName()+" - hashcode:"+invoker.hashCode());
		}
		Object ret= parent.getMethod(method, param).invoke(invoker, obj);
		if(_for!=null){
			DynamicObject o = util.getObject(_for);
			o.setValue(ret);
			util.setObject(_for, o);
		}
		return ret;
	}
	public Object construct(Class[] param,Object[] obj,String _for) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		if(JMSI.DEBUG){
			JMSI.out("invoking <init> ["+parent.getName()+"] ( "+Arrays.toString(param)+" ) ");
		}
		Object ret= parent.getConstructor(param).newInstance(obj);
		if(_for!=null){
			DynamicObject o = util.getObject(_for);
			o.setValue(ret);
			util.setObject(_for, o);
		}
		return ret;
	}
	public Object get(String str) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		if(JMSI.DEBUG){
			JMSI.out("getting static field ["+str+"] for "+parent.getName());
		}
		Object ret= parent.getField(str).get(null);
		return ret;
	}
	public Object get(Object obj,String str) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		if(JMSI.DEBUG){
			JMSI.out("getting field ["+str+"] for "+parent.getName()+" - hashcode:"+obj.hashCode());
		}
		return parent.getField(str).get(obj);
	}
	public void set(String str,Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		if(JMSI.DEBUG){
			JMSI.out("setting static field ["+str+"] for "+parent.getName());
		}
		parent.getField(str).set(null, value);
	}
	public void set(Object obj,String str,Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		if(JMSI.DEBUG){
			JMSI.out("setting field ["+str+"] for "+parent.getName()+" - hashcode:"+obj.hashCode());
		}
		parent.getField(str).set(obj, value);
	}
}
