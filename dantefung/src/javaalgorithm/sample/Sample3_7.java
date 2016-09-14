/*
 * 示例程序Sample3_7: Matrix类的求行列式值的全选主元高斯消去法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;

public class Sample3_7
{
	public static void main(String[] args) 
	{
		// 矩阵数据
		double[] mtxData1 = {
				1.0,2.0,3.0,4.0,
				5.0,6.0,7.0,8.0,
				9.0,10.0,11.0,12.0,
				13.0,14.0,15.0,16.0};
		double[] mtxData2 = {
				3.0,-3.0,-2.0,4.0,
				5.0,-5.0,1.0,8.0,
				11.0,8.0,5.0,-7.0,
				5.0,-1.0,-3.0,-1.0};

		// 构造矩阵
		Matrix mtx1 = new Matrix(4, mtxData1);
		Matrix mtx2 = new Matrix(4, mtxData2);

		// 求行列式值的全选主元高斯消去法
		double detValue1 = mtx1.computeDetGauss();
		double detValue2 = mtx1.computeDetGauss();
		System.out.println("detValue1 = " + new Float(detValue1).toString());
		System.out.println("detValue2 = " + new Float(detValue2).toString());
	}
}
