/*
 * ʾ������Sample3_3: Matrix���ʵ���������ȫѡ��Ԫ��˹��Լ����
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;

public class Sample3_3 
{
	public static void main(String[] args) 
	{
		// ��������
		double[] mtxData4 = {
				0.2368,0.2471,0.2568,1.2671,
				1.1161,0.1254,0.1397,0.1490,
				0.1582,1.1675,0.1768,0.1871,
				0.1968,0.2071,1.2168,0.2271};

		// �������
		Matrix mtx4 = new Matrix(4, mtxData4);

		// ʵ���������ȫѡ��Ԫ��˹��Լ����
		if (mtx4.invertGaussJordan())
		{
			System.out.println(mtx4);
		}
		else
		{
			System.out.println("ʧ��");
		}
	}
}