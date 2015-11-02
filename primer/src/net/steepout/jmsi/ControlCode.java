package net.steepout.jmsi;

public enum ControlCode{
	INVOKE(0x1),
	STATIC(0x2),
	OBJECT(0x3),
	SET(0x4),
	GET(0x5),
	PARAM(0x6),
	WITH(0x7),
	BY(0x8),
	IN(0x9),
	CONSTRUCT(0xA),
	NULL(0xB),
	DEFAULT(0xC),
	START(0XD),
	END(0XE),
	TYPE(0xF),
	FREE(0x10),
	FOR(0x11),
	EXIT(0x12),
	SKIP(0x13),
	DISCONNECT(0x14),
	CONNECT(0x15),
	LOG(0x16);
	final int CODE;
	ControlCode(int code){
		CODE = code;
	}
	public static ControlCode find(int code){
		ControlCode codes[] = ControlCode.values();
		for(ControlCode x:codes){
			if(x.CODE==code)return x;
		}
		return NULL;
	}
	public static boolean isCode(String str){
		ControlCode codes[] = ControlCode.values();
		for(ControlCode x:codes){
			if(x.name().equals(str))return true;
		}
		return false;
	}
	public static int encode(ControlCode code){
		ControlCode codes[] = ControlCode.values();
		for(ControlCode x:codes){
			if(x.name().equals(code.name()))return x.CODE;
		}
		return NULL.CODE;
	}
}