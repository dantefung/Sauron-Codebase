/*
 * 示例程序Sample3_5: Matrix类的对称正定矩阵的求逆
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;

public class Sample3_5 
{
	public static void main(String[] args) 
	{
		// 矩阵数据
		double[] mtxData6 = {
			5.0,7.0,6.0,5.0,
			7.0,10.0,8.0,7.0,
			6.0,8.0,10.0,9.0,
			5.0,7.0,9.0,10.0};

		// 构造矩阵
		Matrix mtx6 = new Matrix(4, mtxData6);

		// 对称正定矩阵的求逆
		if (mtx6.invertSsgj())
		{
			System.out.println(mtx6);
		}
		else
		{
			System.out.println("失败");
		}
	}
}
