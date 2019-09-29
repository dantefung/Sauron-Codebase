package com.dantefung.dp.iterator;
//用LinkedList来写一个容器。
public class LinkedList implements Collection {
	Node head = null;
	Node tail = null;
	int size = 0;//冗余数据。
	public void add(Object o)
	{
		Node n = new Node(o,null);
		if(head == null) //添加进来的这个节点是第一个节点。
		{
			head = n;//既是頭
			tail = n;//也是尾
			tail.setNext(null);
		}
		else
		{
			tail.setNext(n);//tail记录了下一个节点的引用。
			tail = n;//tail本身要等于新加进来的节点。tail的引用指向下一个节点。
			size ++;
		}
	}
	
	public int size()
	{
		return size;
	}

	@Override
	public Iterator iterator() {
		return new LinkedListIterator();
	}
	
	private class LinkedListIterator implements Iterator
	{

		@Override
		public Object next() {
			Object e;
			if(head != null)
			{
				Object data = head.getData();
				head = head.getNext();
				return data;
			}
			else
			{
				return null;
			}
					
		}

		@Override
		public boolean hasNext() {
			if(head == null)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		
	}
}
