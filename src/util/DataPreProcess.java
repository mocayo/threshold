package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataPreProcess {

	/**
	 * 异常值处理
	 * @param arr
	 * @return 异常数据
	 */
	public List<Double> outlier_clear(double[] y) {
		double[] arr = Arrays.copyOf(y, y.length);
		Arrays.sort(arr);
		int q1,q2;
		double min,max;
		q1 = arr.length/4;
		q2 = (arr.length * 3) / 4;
		min = arr[q1] - 1.5 * (arr[q2] - arr[q1]);
		max = arr[q2] + 1.5 * (arr[q2] - arr[q1]);
		List<Double> li = new ArrayList<Double>();
		for (int i = 0; i < arr.length; i++) {
			if(arr[i] < min || arr[i] > max)
			{
				li.add(arr[i]);
			}
		}
		
		return li;
	}
	
	/**
	 * 增加偏移量
	 * @param y
	 */
	public double addOffSet(double[] y) {
		double min = Double.MIN_VALUE;
		for (int i = 0; i < y.length; i++) {
			if(y[i] < min)
			{
				min = y[i];
			}
		}
//		System.out.println(min);
		if(min < 0){
			for (int i = 0; i < y.length; i++) {
				y[i] = y[i] - min + 1;
			}	
			return min - 1;
		}
		else
		{
			return 0;
		}
	}
	
	/**
	 * 截取部分数据
	 * @param arr
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	public double[] sub(double[] arr, int beginIndex, int endIndex) {
		double[] a = new double[endIndex - beginIndex];
		for (int i = beginIndex,n = 0; i < endIndex; i++,n++) {
			a[n] = arr[i];
		}
		return a;
	}
}
