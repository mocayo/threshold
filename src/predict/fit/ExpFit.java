package predict.fit;

public class ExpFit {
	
	public static void main(String[] args) {
		double[] y = {0,0,0,0};
		double[] x = {1,2,3,4};
		double[]aaa = fit(y, x);
		for (double d : aaa) {
			System.out.println(d);
		}
	}
	
	/**
	 * 指数函数拟合函数模型，公式为 y=c*m^x;输出为 c,m
	 * 
	 * @param y
	 * @param x
	 * @return
	 */
	public static double[] fit(double[] y, double[] x) {
		double[] lnY = new double[y.length];
		double[] ratio;
		for (int i = 0; i < y.length; i++) {
			lnY[i] = Math.log(y[i]);
		}

		ratio = linear(lnY, x);

		for (int i = 0; i < ratio.length; i++) {
			ratio[i] = Math.exp(ratio[i]);
		}
		return ratio;
	}
	
	/**
	 * 
	 * @param y
	 * @param x
	 * @param a
	 * @param b
	 * @return
	 */
	public static double rmse(double[] y, double[] x, double a, double b) {
		double rmse = 0;
		double sum = 0;
		for (int i = 0; i < x.length; i++) {
			double y_pre = a * Math.pow(b,x[i]);
			sum += Math.pow((y_pre - y[i]), 2);		
		}
		rmse = Math.sqrt(sum/x.length);		
		return rmse;
	}
	
	
	/**
	 * 一次拟合函数，y=a0+a1*x,输出次序是a0,a1
	 * 
	 * @param y
	 * @param x
	 * @return
	 */
	public static double[] linear(double[] y, double[] x) {
		double[] ratio = polyfit(y, x, 1);
		return ratio;
	}
	
	/**
	 * 多项式拟合函数,输出系数是y=a0+a1*x+a2*x*x+.........，按a0,a1,a2输出
	 * 
	 * @param y
	 * @param x
	 * @param order
	 * @return
	 */
	public static double[] polyfit(double[] y, double[] x, int order) {
		double[][] guass = get_Array(y, x, order);

		double[] ratio = cal_Guass(guass, order + 1);

		return ratio;
	}
	
	/**
	 * 得到数据的法矩阵,输出为发矩阵的增广矩阵
	 * 
	 * @param y
	 * @param x
	 * @param n
	 * @return
	 */
	private static double[][] get_Array(double[] y, double[] x, int n) {
		double[][] result = new double[n + 1][n + 2];

		if (y.length != x.length) {
			System.out.println("两个输入数组长度不一！");
		}

		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= n; j++) {
				result[i][j] = cal_sum(x, i + j);
			}
			result[i][n + 1] = cal_multi(y, x, i);
		}

		return result;
	}
	
	/**
	 * 最小二乘法部分， 计算增广矩阵
	 * 
	 * @param guass
	 * @param count
	 * @return
	 */
	private static double[] cal_Guass(double[][] guass, int count) {
		double temp;
		double[] x_value;

		for (int j = 0; j < count; j++) {
			int k = j;
			double min = guass[j][j];

			for (int i = j; i < count; i++) {
				if (Math.abs(guass[i][j]) < min) {
					min = guass[i][j];
					k = i;
				}
			}

			if (k != j) {
				for (int x = j; x <= count; x++) {
					temp = guass[k][x];
					guass[k][x] = guass[j][x];
					guass[j][x] = temp;
				}
			}

			for (int m = j + 1; m < count; m++) {
				double div = guass[m][j] / guass[j][j];
				for (int n = j; n <= count; n++) {
					guass[m][n] = guass[m][n] - guass[j][n] * div;
				}
			}
		}
		x_value = get_Value(guass, count);

		return x_value;
	}
	
	/**
	 * 累加的计算
	 * 
	 * @param input
	 * @param order
	 * @return
	 */
	private static double cal_sum(double[] input, int order) {
		double result = 0;
		int length = input.length;

		for (int i = 0; i < length; i++) {
			result += Math.pow(input[i], order);
		}

		return result;
	}

	/**
	 * 计算∑(x^j)*y
	 * 
	 * @param y
	 * @param x
	 * @param order
	 * @return
	 */
	private static double cal_multi(double[] y, double[] x, int order) {
		double result = 0;

		int length = x.length;

		for (int i = 0; i < length; i++) {
			result += Math.pow(x[i], order) * y[i];
		}

		return result;
	}
	
	/**
	 * 回带计算X值
	 * 
	 * @param guass
	 * @param count
	 * @return
	 */
	private static double[] get_Value(double[][] guass, int count) {
		double[] x = new double[count];
		double[][] X_Array = new double[count][count];

		for (int i = 0; i < count; i++)
			for (int j = 0; j < count; j++)
				X_Array[i][j] = guass[i][j];

		if (2 < count - 1)// 表示有多解
		{
			return null;
		}
		// 回带计算x值
		x[count - 1] = guass[count - 1][count] / guass[count - 1][count - 1];
		for (int i = count - 2; i >= 0; i--) {
			double temp = 0;
			for (int j = i; j < count; j++) {
				temp += x[j] * guass[i][j];
			}
			x[i] = (guass[i][count] - temp) / guass[i][i];
		}

		return x;
	}
}
