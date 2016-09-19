package predict.fit;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

/**
 * 二次多项式拟合
 * @author Administrator
 *
 */
public class PolynomialFit {
	public static void main(String[] args) {
		double[] x = new double[4];
		double[] y = new double[4];
		for (int i = 0; i < x.length; i++) {
			x[i] = i+1;
		}
		for (int i = 0; i < y.length; i++) {
			y[i] = 5 + 4 * x[i] + 3 * x[i]*x[i] + 2*x[i]*x[i]*x[i];
//			y[i] = 5 + 4 * x[i] + 3 * x[i]*x[i];
		}
		double[] aaa = fit3(y, x);
		for (int i = 0; i < aaa.length; i++) {
			System.out.println(aaa[i]);
		}
		System.out.println(rmse3(y, x, aaa[0], aaa[1], aaa[2], aaa[3]));
		;
	}
	
	/**
	 * 二次多项式拟合
	 * @param y
	 * @param x
	 * @return 二次多项式系数 a + b * x + c *x2 
	 */
	public static double[] fit(double[] y, double[] x) {		
		WeightedObservedPoints obs = new WeightedObservedPoints();
		for (int i = 0; i < x.length; i++) {
			obs.add(x[i], y[i]);
		}
		PolynomialCurveFitter fitter = PolynomialCurveFitter.create(0)
				.withStartPoint(new double[] { -1e-20, 3e15, -5e25 });
		double[] best = fitter.fit(obs.toList());
		
		return best;
	}
	
	/**
	 * 三次多项式拟合
	 * @param y
	 * @param x
	 * @return a + b * x + c *x2 + d * x3
	 */
	public static double[] fit3(double[] y, double[] x) {		
		WeightedObservedPoints obs = new WeightedObservedPoints();
		for (int i = 0; i < x.length; i++) {
			obs.add(x[i], y[i]);
		}
		PolynomialCurveFitter fitter = PolynomialCurveFitter.create(0)
				.withStartPoint(new double[] {-1e-20, 3e15,-1e-20, 3e15});
		double[] best = fitter.fit(obs.toList());
		
		return best;
	}
	
	/**
	 * 计算均方误差
	 * @param y
	 * @param x
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public static double rmse(double[] y, double[] x, double a, double b, double c) {
		double rmse = 0;
		double sum = 0;
		for (int i = 0; i < x.length; i++) {
			double y_pre = a + b*x[i] + c*Math.pow(x[i], 2);
			sum += Math.pow((y_pre - y[i]), 2);
		}
		rmse = Math.sqrt(sum/x.length);		
		return rmse;
	}
	
	/**
	 * 计算均方误差
	 * @param y
	 * @param x
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @return
	 */
	public static double rmse3(double[] y, double[] x, double a, double b, double c, double d) {
		double rmse = 0;
		double sum = 0;
		for (int i = 0; i < x.length; i++) {
			double y_pre = a + b*x[i] + c*Math.pow(x[i], 2) + d*Math.pow(x[i], 3);
			sum += Math.pow((y_pre - y[i]), 2);
		}
		rmse = Math.sqrt(sum/x.length);		
		return rmse;
	}
}
