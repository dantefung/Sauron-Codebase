/*
 * 示例程序Sample3_17: Matrix类的求赫申伯格矩阵全部特征值的QR方法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Complex;
import javaalgorithm.algorithm.Matrix;

public class Sample3_17 
{
	public static void main(String[] args) 
	{
		// 矩阵数据
		double[] mtxData18 = {
				1.0,6.0,-3.0,-1.0,7.0,
				8.0,-15.0,18.0,5.0,4.0,
				-2.0,11.0,9.0,15.0,20.0,
				-13.0,2.0,21.0,30.0,-6.0,
				17.0,22.0,-5.0,3.0,6.0};
		Matrix mtx18 = new Matrix(5, 5, mtxData18);

		// 求赫申伯格矩阵全部特征值的QR方法
		// 1: 生成赫申伯格矩阵
		mtx18.makeHberg();
		System.out.println("赫申伯格矩阵=");
		System.out.println(mtx18);
		
		// 2: 计算全部特征值的QR方法
		double[] uArray = new double[mtx18.getNumColumns()];
		double[] vArray = new double[mtx18.getNumColumns()];
		if (mtx18.computeEvHBerg(uArray, vArray, 60, 0.0001))
		{
			System.out.println("特征值=");
			for (int i=0; i<mtx18.getNumColumns(); ++i)
			{
				Complex cp = new Complex(uArray[i], vArray[i]);
				System.out.println(cp);
			}
		}
		else
		{
			System.out.println("失败");
		}
	}
}
