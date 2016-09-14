/*
 * 示例程序Sample7_4: Integral的龙贝格求积法
 */
package javaalgorithm.sample;

import java.util.Scanner;

import javaalgorithm.algorithm.Integral;

public class Sample7_4 
{
	public static void main(String[] args)
	{
		// 求解
		Scanner scanner = new Scanner(System.in);
		for(int i = 0; i < 5; i++)
		{
			System.out.println("请输入题号：");
			int no = scanner.nextInt();
			Integral integ;
			if(no == 1)
			{
				integ = new Integ1();
			}
			else if(no == 2)
			{
				integ = new Integ2();
			}
			else if(no == 3)
			{
				integ = new Integ3();
			}
			else if(no == 4)
			{
				integ = new Integ4();
			}
			else 
			{
				integ = new Integ5();
			}
			System.out.println("请输入下限：");
			Double subX = scanner.nextDouble(); 
			System.out.println("请输入上限：");
			Double supY = scanner.nextDouble();
			double value = integ.getValueRomberg(subX, supY, 0.00001);
			// 显示结果
			System.out.println(value);
		}

	}
}

// 建立Integral的子类，在其中重载函数Func
class Integ1 extends Integral
{
	public double func(double x)
	{
		return x*x*x+2*x-4;
	}
};
class Integ2 extends Integral
{
	public double func(double x)
	{
		return Math.sin(x)/x;
	}
};
class Integ3 extends Integral
{
	public double func(double x)
	{
		return 4/(1+x*x);
	}
};
class Integ4 extends Integral
{
	public double func(double x)
	{
		return 1/(1+x);
	}
};
class Integ5 extends Integral
{
	public double func(double x)
	{
		return Math.exp(-x*x);
	}
};
