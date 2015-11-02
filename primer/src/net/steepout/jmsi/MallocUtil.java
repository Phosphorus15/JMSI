package net.steepout.jmsi;

import net.steepout.jmsi.DataStorage.Malloc;

public class MallocUtil{
	public MallocUtil(Object obj){
		if(JMSI.DEBUG)
			JMSI.out("Malloc Util created by "+obj.hashCode()+" class ["+obj.getClass().getName()+"]"+" in thread ["+Thread.currentThread().getName()+"]");
	}
	public boolean createObject(String name){
		String namespace;
		String _name;
		if(name.contains(":")){
			namespace = name.split(":")[0];
			_name = name.split(":")[1];
		}else{
			namespace = "value";
			_name = name;
		}
		if(JMSI.DEBUG)
			JMSI.out("attemping to create "+name);
		Malloc malloc = DataStorage.get(namespace);
		if(malloc == null || malloc.hasObject(_name))
			return false;
		else{
			malloc.put(_name, new DynamicObject(_name));
			return true;
		}
	}
	public DynamicObject getObject(String name){
		String namespace;
		String _name;
		if(name.contains(":")){
			namespace = name.split(":")[0];
			_name = name.split(":")[1];
		}else{
			namespace = "value";
			_name = name;
		}
		if(JMSI.DEBUG)
			JMSI.out("attemping to get "+name);
		Malloc malloc = DataStorage.get(namespace);
		if(malloc == null || !malloc.hasObject(_name)){
			if(JMSI.DEBUG)
			System.err.println("null pointer! "+(malloc==null));
			return DynamicObject.NULL;
		}else{
			return malloc.get(_name);
		}
	}
	public boolean setObject(String name,DynamicObject obj){
		String namespace;
		String _name;
		if(name.contains(":")){
			namespace = name.split(":")[0];
			_name = name.split(":")[1];
		}else{
			namespace = "value";
			_name = name;
		}
		if(JMSI.DEBUG)
			JMSI.out("attemping to set "+name);
		Malloc malloc = DataStorage.get(namespace);
		if(malloc == null || malloc.hasObject(_name))
			return false;
		else{
			malloc.put(_name, obj);
			return true;
		}
	}
	public boolean hasObject(String name){
		String namespace;
		String _name;
		if(name.contains(":")){
			namespace = name.split(":")[0];
			_name = name.split(":")[1];
		}else{
			namespace = "value";
			_name = name;
		}
		if(JMSI.DEBUG)
			JMSI.out("attemping to check "+name);
		Malloc malloc = DataStorage.get(namespace);
		if(malloc == null || !malloc.hasObject(_name))
			return false;
		else{
			return true;
		}
	}
	public boolean freeObject(String name){
		String namespace;
		String _name;
		if(name.contains(":")){
			namespace = name.split(":")[0];
			_name = name.split(":")[1];
		}else{
			namespace = "value";
			_name = name;
		}
		if(JMSI.DEBUG)
			JMSI.out("attemping to delete "+name);
		Malloc malloc = DataStorage.get(namespace);
		if(malloc != null && malloc.hasObject(_name)){
			DynamicObject o = malloc.get(_name);
			malloc.remove(_name);
			o.destory();
			o = null;
			System.gc();
		}else{
			return false;
		}
		return true;
	}
	public boolean freeMalloc(String name){
		if(JMSI.DEBUG)
			JMSI.out("attemping to destory malloc ["+name+"]");
		Malloc malloc = DataStorage.get(name);
		if(malloc !=null){
			DataStorage.mallocs.remove(malloc);
			malloc.selfDestory();
			System.gc();
			return true;
		}
		return false;
	}
}