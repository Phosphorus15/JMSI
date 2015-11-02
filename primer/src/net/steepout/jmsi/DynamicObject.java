package net.steepout.jmsi;

public class DynamicObject extends Object {
	static int IDL = 0x0;
	Object parent;
	Class<? extends Object> type;
	int id;
	String name;
	public static DynamicObject NULL = new DynamicObject("NULL");

	DynamicObject(Object obj, int id, String name) {
		if (JMSI.DEBUG)
			JMSI.out("Dynamic Object Memory created with [" + obj
					+ "," + id + "," + name + "]");
		parent = obj;
		if (obj != null)
			type = obj.getClass();
		this.id = id;
		this.name = name;
	}

	DynamicObject(int id, String name) {
		this(null, id, name);
	}

	DynamicObject(Object obj, String name) {
		this(obj, IDL++, name);
	}

	DynamicObject(String name) {
		this(null, name);
	}

	boolean isNil() {
		return parent == null;
	}

	boolean hasType() {
		return type != null;
	}

	public Object getValue() {
		return parent;
	}

	public void setValue(Object obj) {
		if (type == null) {
			parent = obj;
			type = obj.getClass();
			if(JMSI.DEBUG)
				JMSI.out("DynamicObject ["+name+"] set to "+obj+" (class:"+type.getName()+")");
		} else {
			if (type.isInstance(obj)) {
				if(JMSI.DEBUG)
					JMSI.out("DynamicObject ["+name+"] set to "+obj);
				parent = obj;
			} else {
				System.out.println(obj.getClass().getName());
				throw new RuntimeException();
			}
		}
	}

	public Class getType() {
		return type;
	}

	public void setType(Class<? extends Object> clazz) {
		if (parent != null) {
			throw new RuntimeException();
		} else {
			type = clazz;
		}
	}

	int getId() {
		return id;
	}
	protected void destory(){
		id = 0;
		type = null;
		parent = null;
	}
}