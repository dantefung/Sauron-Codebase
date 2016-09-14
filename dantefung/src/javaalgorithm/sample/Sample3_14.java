/*
 * 示例程序Sample3_14: Matrix类的约化对称矩阵为对称三对角阵的豪斯荷尔德变换法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;

public class Sample3_14
{
	public static void main(String[] args) 
	{
		// 矩阵数据
		double[] mtxData15 = {
				10.0,1.0,2.0,3.0,4.0,
				1.0,9.0,-1.0,2.0,-3.0,
				2.0,-1.0,7.0,3.0,-5.0,
				3.0,2.0,3.0,12.0,-1.0,
				4.0,-3.0,-5.0,-1.0,15.0};

		// 构造矩阵
		Matrix mtx15 = new Matrix(5, 5, mtxData15);

		// 约化对称矩阵为对称三对角阵的豪斯荷尔德变换法
		Matrix mtxQ1 = new Matrix();
		Matrix mtxT = new Matrix();
		double[] bArray = new double[mtx15.getNumColumns()];
		double[] cArray = new double[mtx15.getNumColumns()];
		if (mtx15.makeSymTri(mtxQ1, mtxT, bArray, cArray))
		{
			System.out.println("乘积矩阵Q=");
			System.out.println(mtxQ1);
			System.out.println("-------------------------------"); 
			System.out.println("三对角阵=");
			System.out.println(mtxT);
		}
		else
		{
			System.out.println("失败");
		}
	}
}
