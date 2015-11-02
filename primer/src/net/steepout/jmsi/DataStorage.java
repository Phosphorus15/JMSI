package net.steepout.jmsi;

import java.util.HashMap;

public class DataStorage{
	static HashMap<String,Malloc> mallocs;
	static{
		mallocs = new HashMap<String,Malloc>();
		mallocs.put("value", new Malloc("value"));
		mallocs.put("constant", JMSI.costants(new Malloc("constant")));
	}
	public static Malloc get(String name){
		return mallocs.get(name);
	}
	protected static void _func_646573746f7279_p(){
		for(Malloc x:mallocs.values()){
			x.selfDestory();
			x=null;
		}
		System.gc();
	}
	public static void put(String name,Malloc obj){
		mallocs.put(name, obj);
	}
	public static boolean hasObject(String name){
		return mallocs.containsKey(name);
	}
	public DynamicObject directTarget(String name){
		if(mallocs.get("constant").hasObject(name)){
			return mallocs.get("constant").get(name);
		}else if(mallocs.get("value").hasObject(name)){
			return mallocs.get("value").get(name);
		}
		return DynamicObject.NULL;
	}
	public void setValue(DynamicObject obj){
		Malloc malloc = mallocs.get("value");
		malloc.put(obj.name, obj);
		mallocs.put("value", malloc);
	}
	public static class Malloc{
		HashMap<String,DynamicObject> malloc = new HashMap<String,DynamicObject>();
		String name;
		boolean editable = true;
		public Malloc(String n){
			name = n;
		}
		public void forbidEdit(){
			editable = false;
		}
		protected void initMemoryAllocation(){
			malloc = new HashMap<String,DynamicObject>();
		}
		public DynamicObject get(String name){
			return malloc.get(name);
		}
		public void put(String name,DynamicObject obj){
			if(editable){
				malloc.put(name, obj);
				if(JMSI.DEBUG)
				JMSI.out("Putted malloc object "+this.name+":"+name);
			}
			else
				throw new RuntimeException();
		}
		public boolean hasObject(String name){
			return malloc.containsKey(name);
		}
		protected void remove(String name){
			if(malloc.containsKey(name))
				malloc.remove(name);
		}
		protected void selfDestory(){
			for(DynamicObject v :malloc.values()){
				v.destory();
				v=null;
			}
			malloc = null;
		}
	}
}