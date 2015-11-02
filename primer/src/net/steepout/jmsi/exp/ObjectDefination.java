package net.steepout.jmsi.exp;

import net.steepout.jmsi.ControlCode;
import net.steepout.jmsi.MallocUtil;

public class ObjectDefination implements ExpressionParser{

	@Override
	public Boolean parse(ControlCode control, Object[] obj) {
		// TODO Auto-generated method stub
		boolean ret = true;
		for(int x=1;x!=obj.length;x++){
			if(obj[x] instanceof ControlCode){
				if(ControlCode.END.equals(obj[x]))
				break;
			}else{
				ret = new MallocUtil(this).createObject((String)obj[x]);
			}
		}
		return ret;
	}
	
}