package fortest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelDemo {
	
	public static void main(String[] args) {  
		readXml("D:/test.xls");  
	}  


	public static void readXml(String fileName){  
		try {  
			InputStream input = new FileInputStream(fileName);  //建立输入流  
			Workbook wb  = null;  
			wb = new HSSFWorkbook(input);  
			Sheet sheet = wb.getSheetAt(0);     //获得第一个表单  
			Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
			while (rows.hasNext()) {  
				Row row = rows.next();  //获得行数据  
				System.out.println("Row #" + row.getRowNum());  //获得行号从0开始  
				Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  
				while (cells.hasNext()) {  
					Cell cell = cells.next();  
					System.out.println("Cell #" + cell.getColumnIndex());  
					switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
					case HSSFCell.CELL_TYPE_NUMERIC:  
						System.out.println(cell.getNumericCellValue());  
						break;  
					case HSSFCell.CELL_TYPE_STRING:  
						System.out.println(cell.getStringCellValue());  
						break;  
					case HSSFCell.CELL_TYPE_BOOLEAN:  
						System.out.println(cell.getBooleanCellValue());  
						break;  
					case HSSFCell.CELL_TYPE_FORMULA:  
						System.out.println(cell.getCellFormula());  
						break;  
					default:  
						System.out.println("unsuported sell type");  
						break;  
					}  
				}  
			}  
		} catch (IOException ex) {  
			ex.printStackTrace();  
		}  
	}  
}