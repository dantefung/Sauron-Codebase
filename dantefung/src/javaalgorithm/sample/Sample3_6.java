/*
 * 示例程序Sample3_6: Matrix类的托伯利兹矩阵求逆的埃兰特方法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;

public class Sample3_6
{
	public static void main(String[] args) 
	{
		// 矩阵数据
		double[] mtxData7 = {
				10, 5, 4, 3, 2, 1,
				-1,10, 5, 4, 3, 2,
				-2,-1,10, 5, 4, 3,
				-3,-2,-1,10, 5, 4,
				-4,-3,-2,-1,10, 5,
				-5,-4,-3,-2,-1,10};

		// 构造矩阵
		Matrix mtx7 = new Matrix(6, mtxData7);

		// 托伯利兹矩阵求逆的埃兰特方法
		if (mtx7.invertTrench())
		{
			System.out.println(mtx7);
		}
		else
		{
			System.out.println("失败");
		}
	}
}
