package com.dantefung.test;

public class TestForCdetails {
	public static void main(String[] args){
		int j=0;
		int k=0;
		for(int i = 0; i < 100; i++)
		{
			j=++j;//这里的++j是先加后用，即是先自增然后将值赋给j;
		}
		System.out.println(j);
		
		for(int i = 0; i < 100; i++)
		{
			k=k++;//这里的++j是先用后加，即是先将值赋给j，然后才开始自增;
		}
		System.out.println(k);
	}
}
