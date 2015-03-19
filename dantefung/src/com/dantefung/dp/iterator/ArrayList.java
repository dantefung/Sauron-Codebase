package com.dantefung.dp.iterator;
import com.dantefung.dp.iterator.Collection;
//模拟JDK中的ArrayList
public class ArrayList implements Collection {
	Object[] objects = new Object[10];//用数组来模仿装任意多个对象的容器。
	private int index = 0;
	public int getIndex() {
		return index;
	}

	//利用动态数组实现。
	public void add(Object o)
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
	
	public Iterator iterator()
	{
		return new ArrayListIterator();
	}
	
	//内部的一个包装类。
	private class ArrayListIterator implements Iterator
	{
		private int currentIndex = 0;
		
		@Override
		public Object next() {
			Object o= objects[currentIndex];
			currentIndex ++;
			return o;
		}

		@Override
		public boolean hasNext() {	
			if(currentIndex >=index) return false;
			else return true;
		}
		
	}
	
	
}
