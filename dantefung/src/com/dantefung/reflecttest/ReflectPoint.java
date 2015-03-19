package com.dantefung.reflecttest;
/**
 * @author dantefung 
 * @since 2014-11-1 
 * @version 1.0
 * study by itcast's course
 * take note 
 * */
public class ReflectPoint {
//    int x;//此处默认为私有的成员变量（字段，属性）,此时，用getDclaredField(pt1)可以访问
    private int x;//此时，要getDclaredField(pt1)和fieldX.setAccessible(true);设置为可以访问
	public int y;//此处显示声明了这个成员变量（字段，属性）是公有的，因此getField（pt1）可以获取到y的值
	public String str1 = "ball";
	public String str2 = "basketball";
	public String str3 = "itcast";
	public ReflectPoint(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	/*以下源代码是自动生成的*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/*以下源代码是自动生成的*/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReflectPoint other = (ReflectPoint) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	/*以下源代码是自动生成的*/
	@Override
	public String toString() {
		return "ReflectPoint [str1=" + str1 + ", str2=" + str2 + ", str3="
				+ str3 + "]";
	}

	
	

}
