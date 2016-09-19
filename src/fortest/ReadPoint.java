package fortest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ReadPoint {

	/**
	 * 读取.xls文件并将每行数据以字符串形式存储到List中
	 * @param excel
	 * @return
	 */
	public static List<String> readExcel(String excel){
		List<String> datas = new ArrayList<String>();
		try {
			Workbook wb = null;
			if(excel.endsWith(".xls"))
				wb = new HSSFWorkbook(new FileInputStream(excel));
//			else if(excel.endsWith(".xlsx"))
//				wb = new XSSFWorkbook(new FileInputStream(excel));
			else
				return null;
			Sheet sheet = wb.getSheetAt(0);
			Iterator<Row> rows = sheet.rowIterator();
			while(rows.hasNext()){
				Row row = rows.next();
				//System.out.println("Row " + row.getRowNum() + "#");
				Iterator<Cell> cells = row.cellIterator(); 
				String data = "";
				while(cells.hasNext()){
					Cell cell = cells.next();
					//System.out.println("Cell #" + cell.getColumnIndex());  
					String val = "";
					switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
					case HSSFCell.CELL_TYPE_NUMERIC:  
						//System.out.println(cell.getNumericCellValue());  
						val += cell.getNumericCellValue();
						break;  
					case HSSFCell.CELL_TYPE_STRING:  
						//System.out.println(cell.getStringCellValue());  
						val += cell.getStringCellValue();
						break;  
					case HSSFCell.CELL_TYPE_BOOLEAN:  
						//System.out.println(cell.getBooleanCellValue());
						val += cell.getBooleanCellValue();
						break;  
					case HSSFCell.CELL_TYPE_FORMULA:  
						//System.out.println(cell.getCellFormula());  
						val += cell.getCellFormula();
						break;  
					default:  
						//System.out.println("unsuported sell type");  
						val += "###";
						break;  
					}  
					data += val + " ";
				}
				datas.add(data.trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}

	/**
	 * 从文件输入流中读取excel
	 * @param excel
	 * @return
	 */
	public static void readExcelByFIS(FileInputStream excel){
		try {
			Workbook wb = null;
			if(excel!=null)
				wb = new HSSFWorkbook(excel);
			else
				return;
			Sheet sheet = wb.getSheetAt(0);
			Iterator<Row> rows = sheet.rowIterator();
			//获取第一行的标题信息
			Row title = rows.next();
			String rowinfo = "";
			for(int i=0;i<title.getLastCellNum();i++){
				rowinfo += title.getCell(i).getStringCellValue()+",";
			}
			rowinfo = rowinfo.replaceAll("测点编号", "Instr_no");
			rowinfo = rowinfo.replaceAll("测点类型", "PointType");
			rowinfo = rowinfo.replaceAll("监测仪器", "MonitorItem");
			rowinfo = rowinfo.replaceAll("分量", "");
			String[] keys = rowinfo.trim().split(",");
//			for(String key:keys){
//				System.out.println(key);
//			}
			while(rows.hasNext()){
				Row row = rows.next();
				Map<String, Object> map = new HashMap<String, Object>();
				for(int index=0;index<keys.length;index++){
					String val = "";
					if(row.getCell(index)!=null)
						val += row.getCell(index).getStringCellValue();
					map.put(keys[index], val);
//					if(row.getCell(index)==null){
//						System.out.println("");
//					}else{
//						System.out.println(row.getCell(index).getStringCellValue());
//					}
				}
				System.out.println(map.toString());
			}
		} catch (Exception e) {
//			e.printStackTrace();
			return;
		}
		return;
	}
	
	public static void main(String[] args) {
		try {
			readExcelByFIS(new FileInputStream("D://point.xls"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}	

}
