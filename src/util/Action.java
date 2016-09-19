package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Action implements ApplicationContextAware{
	
	public final static String TomcatAddress="I:\\apache-tomcat-7.0.57\\webapps";   //tamcat地址
	//public final static String TomcatAddress="G:\\apache-tomcat-7.0.57\\webapps";   //tamcat地址
	
	public final static String LcRiverDataTransferAddress=TomcatAddress+   //南瑞远程到本地数据转换配置文件地址
			"\\LcRiverDataTransfer\\WEB-INF\\classes\\com\\lcriver\\config\\connectConfig.xml";
	
	public final static String LcDT_kmyAddress=TomcatAddress+ 				//南瑞到河海以及昆明院配置文件地址
			"\\LcDT_kmy\\WEB-INF\\classes\\com\\lcriver\\config\\connectConfig.xml"; 
	public final static String LCJSAddress=TomcatAddress+ 							//增加数据库地址配置文件位置
			"\\LCJS\\WEB-INF\\classes\\DBConfig.xml"; 
	
	protected  HttpServletRequest request;
	protected  HttpServletResponse response;
	protected  HttpSession session;
	
//	public Action(ServletActionContext ctx){
//		request = ctx.getRequest();
//		response = ctx.getResponse();
//		response.setCharacterEncoding("utf8");
//		session = request.getSession();
//	}

	public Action()
	{
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf8");
		session = request.getSession();
	}
	
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub
		
	}
	
	public void testddd() {
		int year = 2013;// 定义一个字段，接收输入的年份
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
			System.out.println(sf.format(calendar.getTime()));
			calendar.add(Calendar.DAY_OF_MONTH, 1);// 日期+1
		}
	}
	
	//修改properties文件方法(tomcat路径不能有空格)
	public String ModifyFile_pro(String string){
		try {
			BufferedInputStream in=new BufferedInputStream(
					new FileInputStream(this.getClass().getResource("/").getPath()+
							"test.properties"));//载入文件
			Properties properties=new Properties();
			try {
				properties.load(in);//载入properties文件
				String temp;
				temp=properties.getProperty("datasource");//用键获取倄1�7
				properties.setProperty("datasource", string);//设置键的倄1�7
				in.close();
				BufferedOutputStream out=new BufferedOutputStream(
						new FileOutputStream(this.getClass().getResource("/").getPath()+
								"test.properties"));//写文仄1�7
				properties.store(out, null);
				out.flush();//刷新
				out.close();//关闭
				return temp;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return string;
	}
	
	//修改XML文件方法(dom4j)
	public int ModifyFile_XML(String string) throws IOException, FileNotFoundException, DocumentException{
		SAXReader sax = new SAXReader(); 
		String url=this.getClass().getResource("/").getPath()+
				"applicationContext-datasource.xml";     //设置文件地址
		Document xmlDocument =sax.read(url);
		Element element =(Element)(xmlDocument.getRootElement().elements().get(0)); 
		List<Element> value=element.elements();
		for (int i = 0; i < value.size(); i++) {
			if(value.get(i).attributeValue("name").equals("jdbcUrl")){
				string=value.get(i).attributeValue("value").split("//")[0]+"//"+string;
				value.get(i).attribute(1).setValue(string);
				break;
			}
		}
        
		XMLWriter output = new XMLWriter(new FileOutputStream(url));//写入文件
		output.write(xmlDocument);
		output.flush();
		output.close();
		String command=this.getClass().getResource("/").getPath()+"tomcat.bat";
		Runtime.getRuntime().exec("cmd /k start"+command);
		return 1;
	}
}
