/*
 * 示例程序Sample3_15: Matrix类的实对称三对角阵的全部特征值与特征向量的计算
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;

public class Sample3_15 
{
	public static void main(String[] args) 
	{
		// 矩阵数据
		double[] mtxData16 = {
				10.0,1.0,2.0,3.0,4.0,
				1.0,9.0,-1.0,2.0,-3.0,
				2.0,-1.0,7.0,3.0,-5.0,
				3.0,2.0,3.0,12.0,-1.0,
				4.0,-3.0,-5.0,-1.0,15.0};

		// 构造矩阵
		Matrix mtx16 = new Matrix(5, 5, mtxData16);

		// 实对称三对角阵的全部特征值与特征向量的计算
		Matrix mtxQ2 = new Matrix();
		Matrix mtxT2 = new Matrix();
		double[] bArray2 = new double[mtx16.getNumColumns()];
		double[] cArray2 = new double[mtx16.getNumColumns()];
		// 1: 约化对称矩阵为对称三对角阵: 豪斯荷尔德变换法
		if (mtx16.makeSymTri(mtxQ2, mtxT2, bArray2, cArray2))
		{
			// 2: 计算全部特征值与特征向量
			if (mtx16.computeEvSymTri(bArray2, cArray2, mtxQ2, 60, 0.0001))
			{
				System.out.println("特征值=");
				String s = "";
				for (int i=0; i<mtxQ2.getNumColumns(); ++i)
				{
					s += new Float(bArray2[i]).toString() + ", ";
				}
				System.out.println(s);
				System.out.println("-------------------------------"); 
				System.out.println("对应的特征向量=");
				for (int i=0; i<mtxQ2.getNumColumns(); ++i)
				{
					System.out.println(mtxQ2.toStringCol(i, ", "));
				}
			}
			else
			{
				System.out.println("失败");
			}
		}
		else
		{
			System.out.println("失败");
		}
	}
}
