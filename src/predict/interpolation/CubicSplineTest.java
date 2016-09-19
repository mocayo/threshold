package predict.interpolation;

public class CubicSplineTest {
	public static void main(String[] args) {
		double[] x = new double[100];
		double[] y = new double[100];
		for (int i = 0; i < x.length; i++) {
			x[i] = (i + 1);
			y[i] = Math.random();
		}

		int n = x.length;

		double yy[] = { 3.5};
		
		double x1[] = new double[n + 2];
		double y1[] = new double[n + 2];
		
		x1[0] = y1[0] = 0;
		x1[n + 1] = y1[n + 1] = 0;
		
		for (int i = 1; i < n + 1; i++) {
			x1[i] = x[i - 1];
			y1[i] = y[i - 1];
		}
		
		CubicSpline ca = new CubicSpline(x1, y1, n + 2);
		double s[] = ca.calculate(yy);
		
		for (int i = 0; i < s.length; i++) {
			System.out.println(s[i] + "  ");
		}

	}
}
