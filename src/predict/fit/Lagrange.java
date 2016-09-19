package predict.fit;

/**
 * 拉格朗日插值
 * @author Administrator
 *
 */
public class Lagrange {
	
	public static void main(String[] args) {
		double[] x = {4,5,6};
		double[] y = {10,5.25,1};
		System.out.println(lagrange(x, y, 18));
	}
	
	/**
	 * 拉格朗日插值
	 * @param x x数组
	 * @param y y数组
	 * @param x0 
	 * @return
	 */
	public static double lagrange(double[] x, double[] y, double x0) {
		double val = 0.0;
		for (int i = 0; i < x.length; i++) {
			double p = 1.0;
			for (int j = 0; j < y.length; j++) {
				if(i != j)
					p = p * (x0 - x[j])/(x[i] - x[j]);
			}
			val += p * y[i];
		}
		return val;
	}
}
