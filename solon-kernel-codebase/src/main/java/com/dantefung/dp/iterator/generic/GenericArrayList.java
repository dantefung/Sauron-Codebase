package com.dantefung.dp.iterator.generic;

public class GenericArrayList <E>{
	Object[] objects = new Object[10];//用数组来模仿装任意多个对象的容器。
	private int index = 0;
	public int getIndex() {
		return index;
	}

	//利用动态数组实现。
	public void add(E o)
	{
		//先判断数组是否满了。若满了，就将原来的数组做扩展。
		if(index == objects.length)
		{
			/**创建一个新的数组**/
			Object[] newObjects = new Object[objects.length * 2];//JDK中内部算法不是这样的，内部是有个加权值，根据加权值来确定要加多少。
			/**将原来的数组内的内容copy到新的数组里。**/
			System.arraycopy(objects, 0, newObjects, 0, objects.length);
			/**原来的数组的引用指向了新的数组。**/
			objects = newObjects;
		}
		objects[index] = o;
		index ++;
	}
	
	public int size()
	{
		return index;
	}
	
	public static void main(String[] args)
	{
		GenericArrayList<String> a = new GenericArrayList<String>();
		a.add("hello");
	}
}
