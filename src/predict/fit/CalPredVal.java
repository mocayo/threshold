package predict.fit;

public class CalPredVal {
	/**
	 * 指数函数 a * Math.pow(b,x)
	 * @param a
	 * @param b
	 * @param x
	 * @return
	 */
	public Double model1(Double a, Double b, Double x) {
		return a * Math.pow(b,x);
	}
	
	/**
	 * 二次多项式 a + b*x + c*Math.pow(x, 2)
	 * @param a
	 * @param b
	 * @param c
	 * @param x
	 * @return
	 */
	public double model2(double a, double b, double c, double x) {
		return a + b*x + c*Math.pow(x, 2);
	}
	
	
}
