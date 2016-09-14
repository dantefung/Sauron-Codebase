/*
 * 示例程序Sample6_17: Interpolation类的二元全区间插值
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Interpolation;

public class Sample6_17 
{
	public static void main(String[] args)
	{
		int i,j;
	    double u,v,w;
	    double[] x = new double[11];
	    double[] y = new double[11];
	    double[] z = new double[121];

	    for (i=0;i<=10;i++)
	    { 
			x[i]=0.1*i; 
			y[i]=x[i];
		}
	    
		for (i=0;i<=10;i++)
	    {
			for (j=0;j<=10;j++)
				z[i*11+j]=Math.exp(-(x[i]-y[j]));
		}

		// 插值运算
	    u=0.35; 
		v=0.65;
	    w = Interpolation.getValueTqip(11, x, 11, y, z, u, v);
	    System.out.println("z(" + u + "," + v + ") = " + w);

	    u=0.45; 
		v=0.55;
	    w = Interpolation.getValueTqip(11, x, 11, y, z, u, v);
	    System.out.println("z(" + u + "," + v + ") = " + w);
	}
}
