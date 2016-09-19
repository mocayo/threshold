package predict.netwave;

public class Test {
	public static void main(String[] args) {
//		testRandn();
		testreverse();
	}

	private static void testRandn() {
		NetWave nw = new NetWave();
		double[][] arr = new double[100][100];
		nw.randn(arr);
		nw.print(arr);
		nw.checkRandn(arr);
	}
	
	private static void testreverse() {
		NetWave nw = new NetWave();
		double[][] arr = new double[2][4];
		nw.randn(arr);
		nw.print(arr);
		nw.print(nw.reverse(arr));
	}
}
