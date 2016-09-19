package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;




public class XMLUtil {
	public int maxSum(int[] a, int n){
		int sum = 0;
		int b = 0;
		for(int i = 0; i < n; i ++){
			if(b < 0){
				b = a[i];
			}else{
				b += a[i];
			}
			
			if(sum < b)
				sum = b;
		}
		
		return sum;
	}
	
	
	public void testSum(){
		int[] a = {1,-2,3,-10,-4,7,2,-5};
		System.out.println(maxSum(a, a.length));
	}
	
	public void testddd() {
		int year = 2014;// 定义一个字段，接收输入的年份
		Calendar calendar = new GregorianCalendar();// 定义一个日历，变量作为年初
		Calendar calendarEnd = new GregorianCalendar();// 定义一个日历，变量作为年末
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置年初的日期为1月1日
		calendarEnd.set(Calendar.YEAR, year);
		calendarEnd.set(Calendar.MONTH, 11);
		calendarEnd.set(Calendar.DAY_OF_MONTH, 31);// 设置年末的日期为12月31日

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		while (calendar.getTime().getTime() <= calendarEnd.getTime().getTime()) {// 用一整年的日期循环
			System.out.print("\""+sf.format(calendar.getTime()) + "\",");
			calendar.add(Calendar.DAY_OF_MONTH, 1);// 日期+1
		}
	}
	
}
