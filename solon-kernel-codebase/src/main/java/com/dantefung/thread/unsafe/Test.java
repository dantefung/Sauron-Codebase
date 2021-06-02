package com.dantefung.thread.unsafe;

import java.lang.reflect.Field;
import java.security.PrivilegedExceptionAction;

import com.alibaba.fastjson.JSON;

import org.junit.jupiter.api.BeforeEach;
import sun.misc.Unsafe;
import sun.reflect.CallerSensitive;


/**
 * unsafe 操作数组
 *
 */
public class Test {

	private Unsafe unsafe = null;

	@CallerSensitive
	private sun.misc.Unsafe getUnsafe() {
		try {
			return sun.misc.Unsafe.getUnsafe();
		} catch (SecurityException tryReflectionInstead) {
		}
		try {
			return java.security.AccessController.doPrivileged(
					(PrivilegedExceptionAction<Unsafe>) () -> {
						Class<Unsafe> k = Unsafe.class;
						for (Field f : k.getDeclaredFields()) {
							f.setAccessible(true);
							Object x = f.get(null);
							if (k.isInstance(x)) return k.cast(x);
						}
						throw new NoSuchFieldError("the Unsafe");
					});
		} catch (java.security.PrivilegedActionException e) {
			throw new RuntimeException("Could not initialize intrinsics", e.getCause());
		}
	}

	@CallerSensitive
	public Unsafe reflectGetUnsafe() {
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			return (Unsafe) field.get(null);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@BeforeEach
	public void init() {
		unsafe = getUnsafe();
	}

    /**
	 * 线程挂起和恢复
     * park():方法返回条件
     * 1 当前现在调用过 unpark 方法 (多次调用 按照一次计算)
     * 2 当前线程被中断
     * 3 当park 为 false:时间块到了 单位纳秒
     * 4 当park 为 true:时间是绝对时间（1970）年 到期 单位毫秒
     */
    @org.junit.jupiter.api.Test
    public void function6() {
        System.out.println("Start");
        long time = System.currentTimeMillis()+3000l;
        unsafe.park(true,time);
        System.out.println("end");
    }
    
    /**
     * CAS操作
     * @throws Exception
     */
	@org.junit.jupiter.api.Test
    public void function5() throws Exception {
        
        Player player = (Player) unsafe.allocateInstance(Player.class);
        
        Field age = player.getClass().getDeclaredField("age");
        
        long addressAge = unsafe.objectFieldOffset(age);
        
        unsafe.compareAndSwapInt(player, addressAge, 0, 100);
        
        System.out.println(player.getAge());
        
    }
    
    
    /**
     * 直接分配内存地址：内存管理
     */
	@org.junit.jupiter.api.Test
    public void function4() {
        //分配100字节内存  返回初始地址
        long address = unsafe.allocateMemory(100);
        //往分配的内存地址写入值
        unsafe.putInt(address, 55);
        //获取值
        System.out.println(unsafe.getInt(address));
        
        //分配100字节内存  返回初始地址
        long address1 = unsafe.allocateMemory(100);
        
        //copy 内存值
        unsafe.copyMemory(address, address1, 4);
        
        System.out.println(unsafe.getInt(address1));

        // Unsafe申请的内存的使用将直接脱离jvm，gc将无法管理Unsafe申请的内存，所以使用之后一定要手动释放内存，避免内存溢出！！！
        //释放内存
        unsafe.freeMemory(address);
        unsafe.freeMemory(address1);
        
    }
    
    
    /**
     * 操作对象属性值
     * @throws Exception
     */
	@org.junit.jupiter.api.Test
    public void function3() throws Exception {
        
        Player player = (Player) unsafe.allocateInstance(Player.class);
        
        Field fieldName = player.getClass().getDeclaredField("name");
        
        Field fieldAge = player.getClass().getDeclaredField("age");
        
        long fileNameaddres = unsafe.objectFieldOffset(fieldName);
        
        long fileAgeaddres = unsafe.objectFieldOffset(fieldAge);

		Field fieldDefault = player.getClass().getDeclaredField("number");
		long staticFieldDefaultOffset = unsafe.staticFieldOffset(fieldDefault);

		System.out.println("静态变量相对于类内存地址的偏移量 = " + staticFieldDefaultOffset);
		System.out.println("非静态变量相对于实例化对象的偏移量 = " + fileNameaddres);
        
        unsafe.putObjectVolatile(player, fileNameaddres, "wangWu");
        
        unsafe.putInt(player,fileAgeaddres, 100);
        
        System.out.println(player.getAge()+"  "+player.getName());
        
    }
    
    
    /**
     * 实例化对象
     * @throws InstantiationException
     */
	@org.junit.jupiter.api.Test
    public void function2() throws InstantiationException {
        
        Player player = (Player) unsafe.allocateInstance(Player.class);
        
        player.setAge(100);
        
        player.setName("zhangShan");
        
        System.out.println(player.getAge()+"  "+player.getName());
    }
    
    /**
     * 对数组的操作
     */
	@org.junit.jupiter.api.Test
    public void function1() {
        int[] num = new int[7];
        
        //数组的起始地址
        long adress = unsafe.arrayBaseOffset(int[].class);
        //block 大小
        long index = unsafe.arrayIndexScale(int[].class);
        
        unsafe.putInt(num, adress,1);
        unsafe.putInt(num, adress+index, 2);
        unsafe.putInt(num, adress+index+index, 3);
        unsafe.putInt(num, adress+index+index+index, 4);
        
        System.out.println(JSON.toJSONString(num));
        
    }
}