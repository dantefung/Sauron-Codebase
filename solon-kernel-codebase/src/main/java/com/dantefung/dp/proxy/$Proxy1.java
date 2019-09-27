package com.dantefung.dp.proxy;
import java.lang.reflect.Method;
public class $Proxy1 implements Moveable{
    public $Proxy1(InvocationHandler h) {
        this.h = h;
    }
    InvocationHandler h;
@Override
public void move(){
	  try {
    Method md = Moveable.class.getMethod("move");
    h.invoke(this, md);
    }catch(Exception e) {e.printStackTrace();}
}
}