package com.dantefung.dp.proxy;
import java.lang.reflect.Method;
public class $Proxy1 implements com.dantefung.dp.proxy.Moveable{
    public $Proxy1(InvocationHandler h) {
        this.h = h;
    }
    com.dantefung.dp.proxy.InvocationHandler h;
@Override
public void move(){
	  try {
    Method md = com.dantefung.dp.proxy.Moveable.class.getMethod("move");
    h.invoke(this, md);
    }catch(Exception e) {e.printStackTrace();}
}
}