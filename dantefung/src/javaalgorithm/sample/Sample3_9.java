/*
 * 示例程序Sample3_9: Matrix类的对称正定矩阵的乔里斯基分解与行列式的求值
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Real;
import javaalgorithm.algorithm.Matrix;

public class Sample3_9
{
	public static void main(String[] args) 
	{
		// 矩阵数据
		double[] mtxData10 = {
				5.0,7.0,6.0,5.0,
				7.0,10.0,8.0,7.0,
				6.0,8.0,10.0,9.0,
				5.0,7.0,9.0,10.0};

		// 构造矩阵
		Matrix mtx10 = new Matrix(4, mtxData10);

		// 对称正定矩阵的乔里斯基分解与行列式的求值
		Real detRealValue = new Real();
		if (mtx10.computeDetCholesky(detRealValue))
		{
			System.out.println("行列式=" + detRealValue); 
			System.out.println("分解后的下三角矩阵="); 
			System.out.println(mtx10); 
		}
		else
		{
			System.out.println("失败");
		}
	}
}
