/*
 * 示例程序Sample1_5: 基本数据类型不能实现引用传值
 */
package javaalgorithm.sample;

public class Sample1_5
{
	/**
	 * 试图交换a, b的值, 但是不能成功
	 */
	public static void swap(int a, int b)
	{
		int c = a;
		a = b;
		b = c;
		
		System.out.println("\n在函数swap内a, b的值");
		System.out.println("a = " + a);
		System.out.println("b = " + b);
	}

	public static void main(String[] args) 
	{
		int a = 2;
		int b = 100;
		System.out.println("交换前a, b的值");
		System.out.println("a = " + a);
		System.out.println("b = " + b);

		// 试图交换a, b的值
		swap(a, b);
		
		System.out.println("\n交换后a, b的值");
		System.out.println("a = " + a);
		System.out.println("b = " + b);
	}
}
