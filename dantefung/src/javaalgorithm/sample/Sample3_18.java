/*
 * 示例程序Sample3_18: Matrix类的求实对称矩阵特征值与特征向量的雅可比法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;

public class Sample3_18 
{
	public static void main(String[] args) 
	{
		// 矩阵数据
		double[] mtxData19 = {
				2.0,-1.0,0.0,
				-1.0,2.0,-1.0,
				0.0,-1.0,2.0};

		// 构造矩阵
		Matrix mtx19 = new Matrix(3, 3, mtxData19);

		// 求实对称矩阵特征值与特征向量的雅可比法
		double[] valArray = new double[mtx19.getNumColumns()];
		Matrix mtxVV = new Matrix();
		if (mtx19.computeEvJacobi(valArray, mtxVV, 100, 0.0001))
		{
			System.out.println("特征值=");
			String s = "";
			for (int i=0; i<mtx19.getNumColumns(); ++i)
			{
				s += valArray[i] + ", ";
			}
			System.out.println(s);
			System.out.println("-------------------------------"); 
			System.out.println("对应的特征向量=");
			for (int i=0; i<mtx19.getNumColumns(); ++i)
			{
				System.out.println(mtxVV.toStringCol(i, ", "));
			}
		}
		else
		{
			System.out.println("失败");
		}
	}
}
