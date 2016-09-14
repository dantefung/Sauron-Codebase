/*
 * 示例程序Sample3_16: Matrix类的约化一般实矩阵为赫申伯格矩阵的初等相似变换法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;

public class Sample3_16 
{
	public static void main(String[] args) 
	{
		// 矩阵数据
		double[] mtxData17 = {
				1.0,6.0,-3.0,-1.0,7.0,
				8.0,-15.0,18.0,5.0,4.0,
				-2.0,11.0,9.0,15.0,20.0,
				-13.0,2.0,21.0,30.0,-6.0,
				17.0,22.0,-5.0,3.0,6.0};

		// 构造矩阵
		Matrix mtx17 = new Matrix(5, 5, mtxData17);

		// 约化一般实矩阵为赫申伯格矩阵的初等相似变换法
		mtx17.makeHberg();
		System.out.println(mtx17);
	}
}
