package net.steepout.jmsi.exp;

import java.util.LinkedList;

import net.steepout.jmsi.ControlCode;
import net.steepout.jmsi.DynamicObject;
import net.steepout.jmsi.JMSI;
import net.steepout.jmsi.MallocUtil;

public class ObjectRetype implements ExpressionParser{

	@Override
	public Boolean parse(ControlCode control, Object[] obj) {
		// TODO Auto-generated method stub
		MallocUtil util = new MallocUtil(this);
		if(obj.length<6)
			return false;
		LinkedList<String> name = new LinkedList<String>();
		int x=1;
		for(x=1;x!=obj.length;x++){
			if(obj[x] instanceof ControlCode){
				break;
			}else{
				name.add((String) obj[x]);
			}
		}
		try {
			Class<? extends Object> clazz = Class.forName((String) obj[x+2]);
			for(String s:name){
				DynamicObject dyo = util.getObject(s);
				dyo.setType(clazz);
				util.setObject(s,dyo);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			JMSI.outerr("[ERROR] "+e.toString());
			return false;
		}
		
		return true;
	}
	
}