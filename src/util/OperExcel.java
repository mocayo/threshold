package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts2.ServletActionContext;


public class OperExcel {
	
	private String exportFilePath = ServletActionContext.getServletContext().getRealPath("/media/download/tmp.xls");
	private String exportFilePath1 = ServletActionContext.getServletContext().getRealPath("/media/download/tmp1.xls");
	
	public void arrayToExcel(List<String[]> objList,String header,int size)
	{
		try{
			WritableWorkbook wwb = Workbook.createWorkbook(new File(exportFilePath));
			WritableSheet ws = wwb.createSheet("Sheet 1", 0);
			
			ws.mergeCells(0, 0, size, 0);
			WritableFont bold = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);//设置字体种类和黑体显示,字体为Arial,字号大小为10,采用黑体显示
	        WritableCellFormat titleFormate = new WritableCellFormat(bold);//生成一个单元格样式控制对象
	        titleFormate.setAlignment(jxl.format.Alignment.CENTRE);//单元格中的内容水平方向居中
	        titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直方向居中
	        Label title = new Label(0,0,header,titleFormate);
	        ws.setRowView(0, 600, false);//设置第一行的高度
	        ws.addCell(title);
			
			for(int j=0;j<objList.size();j++)
			{
				String[] obj = objList.get(j);
				for(int i=0;i<obj.length;i++)
				{
					Label labelC = new jxl.write.Label(i, j+1, obj[i]);
					ws.addCell(labelC);
				}
			}
			
			wwb.write();
			wwb.close(); 
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	public void arrayToExcel2(List<Object[]> objList,String header,String table,String point,int size)
	{
		try{
//			Workbook wb = Workbook.getWorkbook(exportFilePath1); 
			File f = new File("D:/media/download/"+table+"/"+point+".xls");
	        File parent = f.getParentFile();
	        if(parent!=null && !parent.exists()){
	        	parent.mkdirs();
	        }
	        if(!f.exists()){
	        	f.createNewFile();
	        }
		    FileOutputStream fos = new FileOutputStream(f); 
		    WritableWorkbook wwb = Workbook.createWorkbook(fos); 
			
			WritableSheet ws = wwb.createSheet("Sheet 1", 0);
			
			ws.mergeCells(0, 0, size, 0);
			WritableFont bold = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);//设置字体种类和黑体显示,字体为Arial,字号大小为10,采用黑体显示
	        WritableCellFormat titleFormate = new WritableCellFormat(bold);//生成一个单元格样式控制对象
	        titleFormate.setAlignment(jxl.format.Alignment.CENTRE);//单元格中的内容水平方向居中
	        titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直方向居中
	        Label title = new Label(0,0,header,titleFormate);
	        ws.setRowView(0, 600, false);//设置第一行的高度
	        ws.addCell(title);
			
			for(int j=0;j<objList.size();j++)
			{
				Object[] obj = objList.get(j);
				for(int i=0;i<obj.length;i++)
				{
//					System.out.println(obj);
					if ((i>=2&&i<=13)&&(obj[i]!=null)&&j!=0){
						jxl.write.Number number = new jxl.write.Number(i, j+1, Double.parseDouble(""+obj[i]));
						ws.addCell(number);
					} else {
						Label labelC = new jxl.write.Label(i, j+1, ""+obj[i]);
						ws.addCell(labelC);
					}
				}
			}
			
			wwb.write();
			wwb.close(); 
			
		/*	FileOutputStream fos = null;  
	        ObjectOutputStream oos = null;  
	        File f = new File("D:/media/download/"+table+"/"+point+".xls");
	        File parent = f.getParentFile();
	        if(parent!=null && !parent.exists()){
	        	parent.mkdirs();
	        }
	        if(!f.exists()){
	        	f.createNewFile();
	        }
	            fos = new FileOutputStream(f);  
	            oos = new ObjectOutputStream(fos);  
	            oos.writeObject(wwb);    //括号内参数为要保存java对象  
	                oos.close();  
	                fos.close();  */
			fos.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	//写入Excel内容
	public void writeExcel(String filename){
		try{			
			//1.添加Label对象
			WritableWorkbook wwb = Workbook.createWorkbook(new File(exportFilePath));
			WritableSheet ws = wwb.createSheet("Test Sheet 1", 0);
			Label labelC = new jxl.write.Label(0, 0, "This is a Label cell");
			ws.addCell(labelC);
			
			//2.添加带有字型Formatting的对象
			WritableFont wf = new WritableFont(WritableFont.TIMES, 18, WritableFont.BOLD, true);
			WritableCellFormat wcfF = new WritableCellFormat(wf);
			Label labelCF = new jxl.write.Label(1, 0, "This is a Label Cell", wcfF);
			ws.addCell(labelCF);
			
			//2.添加Number对象
			jxl.write.Number labelN = new jxl.write.Number(0, 1, 3.1415926);
			ws.addCell(labelN);
			
			//添加带有formatting的Number对象
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##");
			jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf);
			jxl.write.Number labelNF = new jxl.write.Number(1, 1, 3.1415926, wcfN);
			ws.addCell(labelNF);
			
			//3.添加Boolean对象
			jxl.write.Boolean labelB = new jxl.write.Boolean(0, 2, false);
			ws.addCell(labelB);
			
			//4.添加DateTime对象
			jxl.write.DateTime labelDT = new jxl.write.DateTime(0, 3, new java.util.Date());
			ws.addCell(labelDT);

			//添加带有formatting的DateFormat对象
			jxl.write.DateFormat df = new jxl.write.DateFormat("dd MM yyyy hh:mm:ss");
			jxl.write.WritableCellFormat wcfDF = new jxl.write.WritableCellFormat(df);
			jxl.write.DateTime labelDTF = new jxl.write.DateTime(1, 3, new java.util.Date(), wcfDF);
			ws.addCell(labelDTF); 

			wwb.write();
			wwb.close(); 			

		}catch(Exception ex){
			ex.printStackTrace();
		}		 
	}
	
	
	public List excel2List() throws BiffException, IOException{
		   //创建一个list 用来存储读取的内容
		    List list = new ArrayList();
		    Workbook rwb = null;
		    Cell cell = null;
		    
		    //创建输入流
		    InputStream stream = new FileInputStream("d:\\points.xls");
		    
		    //获取Excel文件对象
		    rwb = Workbook.getWorkbook(stream);
		    
		    //获取文件的指定工作表 默认的第一个
		    Sheet sheet = rwb.getSheet(0);  
		   
		    //行数(表头的目录不需要，从1开始)
		    for(int i=0; i<sheet.getRows(); i++){
		     
		     //创建一个数组 用来存储每一列的值
		     String[] str = new String[sheet.getColumns()];
		     
		     //列数
		     for(int j=0; j<sheet.getColumns(); j++){
		     
		      //获取第i行，第j列的值
		      cell = sheet.getCell(j,i);    
		      str[j] = cell.getContents();
		      
		     }
		     //把刚获取的列存入list
		     list.add(str);
		    }
		    List rList = new ArrayList();
		    for(int i=0;i<list.size();i++){
		     String[] str = (String[])list.get(i);
		     
		     if(str!=null){
		    	 rList.add(str[0]);
		     }
		    }
		    return rList;
		  }
	
	
	//读取Excel文件中的内容
	public void readExcel(String sourcefile){
		try{
			InputStream is = new FileInputStream(sourcefile);						
			Workbook rwb = Workbook.getWorkbook(is);
			
			//获取第一张Sheet表既可能通过Sheet的名称来访问它，也可以通过下标来访问它。
			//如果通过下标来访问的话，要注意的一点是下标从0开始，就像数组一样。
			Sheet rs = rwb.getSheet(0); 
			
			//获取第一行，第一列的值
			Cell c00 = rs.getCell(0, 0);
			String strc00 = c00.getContents();
			//获取第一行，第二列的值

			Cell c10 = rs.getCell(1, 0);
			String strc10 = c10.getContents();

			//获取第二行，第二列的值
			Cell c11 = rs.getCell(1, 1);
			String strc11 = c11.getContents();

			System.out.println("Cell(0, 0)" + " value : " + strc00 + "; type : " + c00.getType());
			System.out.println("Cell(1, 0)" + " value : " + strc10 + "; type : " + c10.getType());
			System.out.println("Cell(1, 1)" + " value : " + strc11 + "; type : " + c11.getType()); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	//获得工作薄(Workbook)中工作表(Sheet)的个数，示例：
	public int getNumberOfSheets(String sourcefile){
		int result = 0;
		try{
			
			jxl.Workbook rwb = jxl.Workbook.getWorkbook(new File(sourcefile));
			result = rwb.getNumberOfSheets(); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	//返回工作薄(Workbook)中工作表(Sheet)对象数组，示例：
	public Sheet[] getSheets(String sourcefile){
		Sheet[] sheets = null;
		try{
			
			jxl.Workbook rwb = jxl.Workbook.getWorkbook(new File(sourcefile));
			sheets = rwb.getSheets();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return sheets;
	}
	
	//获取Sheet的名称，示例：
	public String getName(String sourcefile){
		String name = "";
		try{
			
			jxl.Workbook rwb = jxl.Workbook.getWorkbook(new File(sourcefile));
			jxl.Sheet rs = rwb.getSheet(0);
			name = rs.getName(); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return name;
	}
	
	//获取Sheet表中所包含的总列数，示例：
	public int getColumns(String sourcefile){
		int i = 0;
		try{
			
			jxl.Workbook rwb = jxl.Workbook.getWorkbook(new File(sourcefile));
			jxl.Sheet rs = rwb.getSheet(0);
			i = rs.getColumns(); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return i;
	}
	
	/*获取Sheet表中所包含的总行数*/
	public int getRows(String sourcefile){
		int rsRows = 0;
		try{
			//获取Sheet表中所包含的总行数
			jxl.Workbook rwb = jxl.Workbook.getWorkbook(new File(sourcefile));
			jxl.Sheet rs = rwb.getSheet(0);
			rsRows = rs.getRows(); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return rsRows;
	}
		
}

