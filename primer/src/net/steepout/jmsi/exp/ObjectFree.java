package net.steepout.jmsi.exp;

import java.util.LinkedList;

import net.steepout.jmsi.ControlCode;
import net.steepout.jmsi.MallocUtil;

public class ObjectFree implements ExpressionParser{
	
	MallocUtil util = new MallocUtil(this);

	@Override
	public Object parse(ControlCode control, Object[] obj) {
		// TODO Auto-generated method stub
		LinkedList<String> objects = new LinkedList<String>();
		
		for(int x=1;x!=obj.length;x++){
			if(ControlCode.END.equals(obj[x])){
				break;
			}else{
				objects.add((String) obj[x]);
			}
		}
		for(String x:objects){
			util.freeObject(x);
		}
		return true;
	}

}
