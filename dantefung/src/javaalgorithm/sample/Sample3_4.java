/*
 * 示例程序Sample3_4: Matrix类的复矩阵求逆的全选主元高斯－约当法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;

public class Sample3_4 
{
	public static void main(String[] args) 
	{
		// 矩阵数据
		double[] mtxData51 = {
				0.2368,0.2471,0.2568,1.2671,
				1.1161,0.1254,0.1397,0.1490,
				0.1582,1.1675,0.1768,0.1871,
				0.1968,0.2071,1.2168,0.2271};
		double[] mtxData52 = {
				0.1345,0.1678,0.1875,1.1161,
				1.2671,0.2017,0.7024,0.2721,
				-0.2836,-1.1967,0.3558,-0.2078,
				0.3576,-1.2345,2.1185,0.4773};

		// 构造矩阵
		Matrix mtx5 = new Matrix(4, mtxData51);
		Matrix mtxImag = new Matrix(4, mtxData52);

		// 复矩阵求逆的全选主元高斯－约当法
		if (mtx5.invertGaussJordan(mtxImag))
		{
			System.out.println(mtx5);
			System.out.println("-------------------------");
			System.out.println(mtxImag);
		}
		else
		{
			System.out.println("失败");
		}
	}
}
