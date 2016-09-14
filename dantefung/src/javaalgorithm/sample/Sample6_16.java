/*
 * 示例程序Sample6_16: Interpolation类的二元三点插值
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Interpolation;

public class Sample6_16 
{
	public static void main(String[] args)
	{
		int i,j;
	    double u,v,w;
	    double[] x = new double[6];
	    double[] y = new double[5];
	    double[] z = new double[30];

	    for (i=0;i<=5;i++) 
			x[i]=0.2*i;
	    for (j=0;j<=4;j++) 
			y[j]=0.25*j;
	    for (i=0;i<=5;i++)
	    {
			for (j=0;j<=4;j++)
				z[i*5+j]=Math.exp(-(x[i]-y[j]));
		}

		// 插值运算
	    u = 0.9; 
		v = 0.8;
	    w = Interpolation.getValueTqip(6, x, 5, y, z, u, v);
	    System.out.println("z(" + u + "," + v + ") = " + w);

	    u = 0.3; 
		v = 0.9;
	    w = Interpolation.getValueTqip(6, x, 5, y, z, u, v);
	    System.out.println("z(" + u + "," + v + ") = " + w);
	}
}
