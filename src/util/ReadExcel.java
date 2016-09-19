package util;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ReadExcel {
	public static void main(String[] args) throws Exception {
		testReadExcel2();
		
		
 	}


	private static void testReadExcel2() throws Exception {
		File file = new File("xls\\C4-A29-C-01.xls");
		List<Double> a = readExcel2(file);
		
				
		Double[] y = new Double[a.size()];
		a.toArray(y);
		//Arrays.sort(y);
		for (int i = 0; i < y.length; i++) {
			System.out.println("aaa:"+y[i]);
		}
		System.out.println(a.size());
	}
	
	private static void testReadExcel() throws Exception {
		File file = new File("C4-A29-C-01.xls");
		List<Double>[] a = readExcel(file);
		
		System.out.println(a[0].size() );	
		System.out.println(a[0].size() == a[1].size());	     
		
	
		Double[] y = new Double[a[1].size()];
		a[1].toArray(y);
		Arrays.sort(y);
		for (int i = 0; i < y.length; i++) {
			System.out.println("aaa:"+y[i]);
		}
	}
	
	
	/**
	 * 读取excel文件
	 * @param file 文件名
	 * @return list数组 第一个元素为x[] 第二个为y[]
	 * @throws Exception
	 */
	public static List<Double>[] readExcel(File file) throws Exception {
		BufferedInputStream bufferedIS = new BufferedInputStream(new FileInputStream(file));
		Workbook workbook = new HSSFWorkbook(bufferedIS);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rit = sheet.rowIterator();
//		rit.next();//跳过第一行
		List<Double> list_x = new ArrayList<Double>(); //实例化List集合
		List<Double> list_y = new ArrayList<Double>(); 
		double x,y;
		while (rit.hasNext()) {
			Row myRow = rit.next();
			try{
				x = myRow.getCell(3).getNumericCellValue();
//				System.out.print(x+"    ");
			} catch (Exception e) {
//				System.out.println("空值");
				continue;
			} 
			
			try{
				y = myRow.getCell(2).getNumericCellValue();
//				System.out.println(y);
			} catch (Exception e) {
//			System.out.println("空值");
//				System.out.println();
				continue;
			} 
			list_x.add(x);
			list_y.add(y);
		}
		List[] xy = {list_x,list_y};
		return xy;
	}
	/**
	 * 读取excel文件
	 * @param file 文件名
	 * @return list数组 第一个元素为x[] 第二个为y[]
	 * @throws Exception
	 */
	public static List<Double> readExcel2(File file) throws Exception {
		BufferedInputStream bufferedIS = new BufferedInputStream(new FileInputStream(file));
		Workbook workbook = new HSSFWorkbook(bufferedIS);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rit = sheet.rowIterator();
//		rit.next();//跳过第一行
		List<Double> list_x = new ArrayList<Double>(); //实例化List集合
		double x;
		while (rit.hasNext()) {
			Row myRow = rit.next();
						
			try{
				x = myRow.getCell(2).getNumericCellValue();
//				System.out.println(y);
			} catch (Exception e) {
//				System.out.println();
				continue;
			} 
			list_x.add(x);
		}
		return list_x;
	}
}
