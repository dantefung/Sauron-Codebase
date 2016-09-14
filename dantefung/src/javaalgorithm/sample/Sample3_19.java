/*
 * 示例程序Sample3_19: Matrix类的求实对称矩阵特征值与特征向量的雅可比过关法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;

public class Sample3_19 
{
	public static void main(String[] args) 
	{
		// 矩阵数据
		double[] mtxData20 = {
				10.0,1.0,2.0,3.0,4.0,
				1.0,9.0,-1.0,2.0,-3.0,
				2.0,-1.0,7.0,3.0,-5.0,
				3.0,2.0,3.0,12.0,-1.0,
				4.0,-3.0,-5.0,-1.0,15.0};

		// 构造矩阵
		Matrix mtx20 = new Matrix(5, 5, mtxData20);

		// 求实对称矩阵特征值与特征向量的雅可比过关法
		double[] valArray1 = new double[mtx20.getNumColumns()];
		Matrix mtxVV1 = new Matrix();
		if (mtx20.computeEvJacobi(valArray1, mtxVV1, 0.000001))
		{
			System.out.println("特征值=");
			String s = "";
			for (int i=0; i<mtx20.getNumColumns(); ++i)
			{
				s += valArray1[i] + ", ";
			}
			System.out.println(s);
			System.out.println("-------------------------------"); 
			System.out.println("对应的特征向量=");
			for (int i=0; i<mtx20.getNumColumns(); ++i)
			{
				System.out.println(mtxVV1.toStringCol(i, ", "));
			}
		}
		else
		{
			System.out.println("失败");
		}
	}
}
