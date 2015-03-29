package com.dantefung.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		for(int i=0; i<100; i++)
		{
			list.add(i);
		}
		
		Collections.sort(list, new IntComparator());
		
		for(int i : list)
		{
			System.out.println(i);
		}
	}

}

class IntComparator implements Comparator<Integer>
{

	//Returns a negative integer, zero, or a positive integer 
	//as the first argument is less than, equal to, or greater than the second.

	@Override
	public int compare(Integer o1, Integer o2) {
		// TODO Auto-generated method stub
		return (int)(o2-o1);
	}
	

}