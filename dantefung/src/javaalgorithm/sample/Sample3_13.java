/*
 * 示例程序Sample3_13: Matrix类的求广义逆的奇异值分解法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;

public class Sample3_13 
{
	public static void main(String[] args) 
	{
		// 矩阵数据
		double[] mtxData14 = {
				1.0,2.0,3.0,4.0,
				6.0,7.0,8.0,9.0,
				1.0,2.0,13.0,0.0,
				16.0,17.0,8.0,9.0,
				2.0,4.0,3.0,4.0};

		// 构造矩阵
		Matrix mtx14 = new Matrix(5, 4, mtxData14);

		// 求广义逆的奇异值分解法
		Matrix mtxAP = new Matrix();
		Matrix mtxU2 = new Matrix();
		Matrix mtxV2 = new Matrix();
		if (mtx14.invertUV(mtxAP, mtxU2, mtxV2, 0.0001))
		{
			System.out.println("分解后的U矩阵=");
			System.out.println(mtxU2);
			System.out.println("-------------------------------"); 
			System.out.println("分解后的V矩阵=");
			System.out.println(mtxV2);
			System.out.println("-------------------------------"); 
			System.out.println("分解后的广义逆矩阵=");
			System.out.println(mtxAP);
		}
		else
		{
			System.out.println("失败");
		}
	}
}
