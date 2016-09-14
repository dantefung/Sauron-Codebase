/*
 * 示例程序Sample1_7: 数组参数
 */
package javaalgorithm.sample;

public class Sample1_7
{
	/**
	 * 交换a, b的值, 能成功
	 */
	public static void swap(int[] a, int[] b)
	{
		int c = a[0];
		a[0] = b[0];
		b[0] = c;
		
		System.out.println("\n在函数swap内a, b的值");
		System.out.println("a = " + a[0]);
		System.out.println("b = " + b[0]);
	}

	/**
	 * 交换a, b的值, 不能成功
	 */
	public static void swap2(int[] a, int[] b)
	{
		int[] c = new int[1];
		c = a;
		a = b;
		b = c;
		
		System.out.println("\n在函数swap2内a, b的值");
		System.out.println("a = " + a[0]);
		System.out.println("b = " + b[0]);
	}

	public static void main(String[] args) 
	{
		int[] a = {2};
		int[] b = {100};
		System.out.println("交换前a, b的值");
		System.out.println("a = " + a[0]);
		System.out.println("b = " + b[0]);

		// 试图交换a, b的值
		swap(a, b);
		
		System.out.println("\n交换后a, b的值");
		System.out.println("a = " + a[0]);
		System.out.println("b = " + b[0]);

		// 试图交换a, b的值
		swap2(a, b);
		
		System.out.println("\n交换后a, b的值");
		System.out.println("a = " + a[0]);
		System.out.println("b = " + b[0]);
	}
}
