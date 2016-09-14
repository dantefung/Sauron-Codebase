/*
 * 示例程序Sample1_6: 基本数据类型的封装类也不能实现引用传值
 */
package javaalgorithm.sample;

public class Sample1_6
{
	/**
	 * 试图交换a, b的值, 不能成功
	 */
	public static void swap(Integer a, Integer b)
	{
		Integer c = new Integer(a.intValue());
		a = b;
		b = c;
		
		System.out.println("\n在函数swap内a, b的值");
		System.out.println("a = " + a);
		System.out.println("b = " + b);
	}

	public static void main(String[] args) 
	{
		Integer a = new Integer(2);
		Integer b = new Integer(100);
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
