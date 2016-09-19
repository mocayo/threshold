package Action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import util.Action;

public class DataBaseSource extends Action{
	public void testConfig() throws IOException, ClassNotFoundException,    //测试数据库连接
		SQLException {
		String ip=request.getParameter("ip");
		String port=request.getParameter("port");
		String DBname=request.getParameter("dbname");
		String username=request.getParameter("user");
		String password=request.getParameter("password");
		String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url="jdbc:sqlserver://"+ip+":"+port+";databaseName="+DBname;
		try {
			Class.forName(driver);  
			Connection conn=DriverManager.getConnection(url,username,password);
			if(!conn.isClosed()){
				System.out.println("ok");
				response.getWriter().write("1");       //连接成功
				return;
			}
			else{
				System.out.println("no");
				response.getWriter().write("0");        //连接失败
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("no");
			response.getWriter().write("0");           //连接失败
			return;
		}
	}
	
	//获取数据源
	public void getDBConfig() throws IOException, ClassNotFoundException,
	SQLException {
		String url=LCJSAddress;
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			JSONObject jsonObject=new JSONObject();
			document = saxReader.read(new File(url));
			saxReader.setEncoding("utf-8");
			List<Element> son1 = document.getRootElement().elements();
			for (Element element : son1) {
				String ip="",number,name,sourceid;
				sourceid=element.attributeValue("id");
				name=element.attributeValue("name");
				List<Element> son2=element.elements();
				String [] resArray=new String[son2.size()];
				int i=0;
				for (Element element2 : son2) {
					number=element2.attributeValue("id");
					List<Element> temp=element2.elements();
					String [] strings=temp.get(2).getText().split("//|;|:|=");
					ip=temp.get(0).getText()+","+number+","+sourceid+","+strings[3]+","+strings[4]+","+strings[6]+","+temp.get(3).getText()+","+temp.get(4).getText();
					resArray[i++]=ip;
				}
				jsonObject.put(sourceid+","+name, resArray);
			}
			
			response.setCharacterEncoding("utf8");
			response.getWriter().write(jsonObject.toString());
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	//增加数据源地址
	public void addDBSourceIP() throws IOException, ClassNotFoundException,
		SQLException {
		String url=LCJSAddress,result="0";
		SAXReader saxReader = new SAXReader();
		saxReader.setEncoding("utf-8");
		Document document;
		String sourceid=request.getParameter("sourceid");
		String ip=request.getParameter("ip");
		String port=request.getParameter("port");
		String dbname=request.getParameter("dbname");
		String user=request.getParameter("user");
		String password=request.getParameter("password");
		try {
			document = saxReader.read(new File(url));
			List <Element> root=document.getRootElement().elements("dbserver");
			for (int i=0;i<root.size();i++) {
				if(root.get(i).attributeValue("id").equals(sourceid)){
					/*String size=String.valueOf(root.get(i).elements().size());*/
					Element list=root.get(i).addElement("list");
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					String time=df.format(new Date());// new Date()为获取当前系统时间
					list.addAttribute("id", time);   //增加数据源的序号
					Element used=list.addElement("used");
					used.setText("0");    //1表示使用，0表示未使用
					Element driver=list.addElement("driver");
					driver.setText("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					String urlString="jdbc:sqlserver://"+ip+":"+port+";databaseName="+dbname;
					Element urleElement=list.addElement("url");
					urleElement.setText(urlString);
					Element username=list.addElement("username");
					username.setText(user);
					Element passwordElement=list.addElement("password");
					passwordElement.setText(password);
						
					XMLWriter writer = new XMLWriter(new FileOutputStream(url));
					writer.write(document);
		            writer.close();
		            result="0,"+time+","+sourceid+","+ip+","+port+","+dbname+","+user+","+password;
					break;
				}
			}
			response.setCharacterEncoding("utf8");
			response.getWriter().write(result);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	//增加数据源
	public void addDBSource() throws IOException, ClassNotFoundException,
	SQLException {
		String url=LCJSAddress,result="0";
		SAXReader saxReader = new SAXReader();
		saxReader.setEncoding("utf-8");
		Document document;
		String source=new String(request.getParameter("sourcename").getBytes("iso8859-1"),"utf-8");
		String ip=request.getParameter("ip");
		String port=request.getParameter("port");
		String dbname=request.getParameter("dbname");
		String user=request.getParameter("user");
		String password=request.getParameter("password");
		try {
			document = saxReader.read(new File(url));
			Element root=document.getRootElement();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String time=df.format(new Date());// new Date()为获取当前系统时间
			Element element=root.addElement("dbserver");
			result=String.valueOf(root.elements("dbserver").size());
			element.addAttribute("id",result);
			element.addAttribute("name", source);
			Element sonlist=element.addElement("list");
			sonlist.addAttribute("id", time);   //增加数据源的序号
			Element used=sonlist.addElement("used");
			used.setText("1");    //1表示使用，0表示未使用
			Element driver=sonlist.addElement("driver");
			driver.setText("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String urlString="jdbc:sqlserver://"+ip+":"+port+";databaseName="+dbname;
			Element urleElement=sonlist.addElement("url");
			urleElement.setText(urlString);
			Element username=sonlist.addElement("username");
			username.setText(user);
			Element passwordElement=sonlist.addElement("password");
			passwordElement.setText(password);
				
			XMLWriter writer = new XMLWriter(new FileOutputStream(url));
			writer.write(document);
            writer.close();
            result+=","+time;
            
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf8");
		response.getWriter().write(result);
	}
	
	//删除数据源
	public void deleteDBSource() throws IOException, ClassNotFoundException,SQLException {
		String url=LCJSAddress,result="0";
		SAXReader saxReader = new SAXReader();
		saxReader.setEncoding("utf-8");
		Document document;
		String source=request.getParameter("DBSourceId");
		try {
			document = saxReader.read(new File(url));
			List <Element> root=document.getRootElement().elements("dbserver");
			for (int i=0;i<root.size();i++) {
				if(root.get(i).attributeValue("id").equals(source)){
					root.remove(i);
					XMLWriter writer = new XMLWriter(new FileOutputStream(url));
					writer.write(document);
		            writer.close();
					result="1";
					break;
				}
			}
			response.setCharacterEncoding("utf8");
			response.getWriter().write(result);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
	}
	
	//删除数据源IP
	public void deleteDBSourceIP() throws IOException, ClassNotFoundException,SQLException {
		String url=LCJSAddress,result="0";
		SAXReader saxReader = new SAXReader();
		saxReader.setEncoding("utf-8");
		Document document;
		String source=request.getParameter("sourcename");
		String list=request.getParameter("list");
		try {
			document = saxReader.read(new File(url));
			List <Element> root=document.getRootElement().elements("dbserver");
			for (int i=0;i<root.size();i++) {
				if(root.get(i).attributeValue("id").equals(source)){
					List<Element> elements=root.get(i).elements();
					int j=0;
					for(;j<elements.size();j++){
						if(elements.get(j).attributeValue("id").equals(list)){
							elements.remove(j);
							break;
						}
					}
					XMLWriter writer = new XMLWriter(new FileOutputStream(url));
					writer.write(document);
		            writer.close();
					result="1";
					break;
				}
			}
			response.setCharacterEncoding("utf8");
			response.getWriter().write(result);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
	}
	
	//修改数据源
	public void modifyDBConfig() throws IOException, ClassNotFoundException,
		SQLException {
		String result="0";//修改是否成功标志0未成功，1已成功
		String list=request.getParameter("list");
		String source=request.getParameter("source");   //数据库名
		String ip=request.getParameter("ip");
		String port=request.getParameter("port");
		String dbname=request.getParameter("dbname");
		String username=request.getParameter("user");
		String password=request.getParameter("password");
		String url=LCJSAddress;
		SAXReader saxReader = new SAXReader();
		saxReader.setEncoding("utf-8");
		Document document;String time = null;
		String used="0";    //保存数据源使用情况
		try {
			document = saxReader.read(new File(url));
			List <Element> root=document.getRootElement().elements("dbserver");
			for (int i=0;i<root.size();i++) {
				if(root.get(i).attributeValue("id").equals(source)){
					List<Element> elements=root.get(i).elements();
					for(int j=0;j<elements.size();j++){
						if(elements.get(j).attributeValue("id").equals(list)){
							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
							time=df.format(new Date());// new Date()为获取当前系统时间
							elements.get(j).setAttributeValue("id", time);
							List<Element> list2=elements.get(j).elements();
							used=list2.get(0).getText();
							String urlsString="jdbc:sqlserver://"+ip+":"+port+";databaseName="+dbname;
							list2.get(2).setText(urlsString);
							list2.get(3).setText(username);
							list2.get(4).setText(password);
							break;
						}
					}
					XMLWriter writer = new XMLWriter(new FileOutputStream(url));
					writer.write(document);
		            writer.close();
					result=used+","+time;
					break;
				}
			}
			response.setCharacterEncoding("utf8");
			response.getWriter().write(result);
		} catch (DocumentException e1) {
					// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	//使用数据源
	public void useDBSource() throws IOException, ClassNotFoundException,
	SQLException {
		String result="0";//修改是否成功标志0未成功，1已成功
		String list=request.getParameter("list");
		String source=request.getParameter("source");   //数据库名
		String ip=request.getParameter("ip");
		String port=request.getParameter("port");
		String dbname=request.getParameter("dbname");
		String username=request.getParameter("user");
		String password=request.getParameter("password");
		String url_string="jdbc:sqlserver://"+ip+":"+port+";databaseName="+dbname;
		String url_LCJS=LCJSAddress;
		String url_modify;
		
		SAXReader saxReader = new SAXReader();
		saxReader.setEncoding("utf-8");
		Document document;
		try {                           
			if(source.equals("hhu")){//修改河海数据源
				url_modify=LcDT_kmyAddress;
				document = saxReader.read(new File(url_modify));
				Element hhuElement=(Element)document.getRootElement()
						.elements("connectConfig").get(1);
				List<Element> elements=hhuElement.elements();
				elements.get(2).setText(url_string);
				elements.get(3).setText(username);
				elements.get(4).setText(password);
				XMLWriter writer = new XMLWriter(new FileOutputStream(url_modify));
				writer.write(document);
	            writer.close();
	            
			}
			else if (source.equals("kmy")) {//修改昆明院数据源
				
			}
			else if (source.equals("nrRemote")) { //修改南瑞远程数据源
				url_modify=LcRiverDataTransferAddress;
				document = saxReader.read(new File(url_modify));
				Element hhuElement=(Element)document.getRootElement()
						.elements("connectConfig").get(0);
				List<Element> elements=hhuElement.elements();
				elements.get(2).setText(url_string);
				elements.get(3).setText(username);
				elements.get(4).setText(password);
				XMLWriter writer = new XMLWriter(new FileOutputStream(url_modify));
				writer.write(document);
	            writer.close();
			}
			else if (source.equals("nrLocal")) { //修改南瑞本地数据源
				url_modify=LcRiverDataTransferAddress;           
				document = saxReader.read(new File(url_modify));
				Element hhuElement=(Element)document.getRootElement()
						.elements("connectConfig").get(1);
				List<Element> elements=hhuElement.elements();
				elements.get(2).setText(url_string);
				elements.get(3).setText(username);
				elements.get(4).setText(password);
				XMLWriter writer = new XMLWriter(new FileOutputStream(url_modify));
				writer.write(document);
	            writer.close();
	            
	            url_modify=LcDT_kmyAddress;           
				document = saxReader.read(new File(url_modify));
				hhuElement=(Element)document.getRootElement()
						.elements("connectConfig").get(0);
				elements=hhuElement.elements();
				elements.get(2).setText(url_string);
				elements.get(3).setText(username);
				elements.get(4).setText(password);
				writer = new XMLWriter(new FileOutputStream(url_modify));
				writer.write(document);
	            writer.close();
			}
			
			document = saxReader.read(new File(url_LCJS));
			List <Element> root=document.getRootElement().elements("dbserver");
			for (int i=0;i<root.size();i++) {
				if(root.get(i).attributeValue("id").equals(source)){
					List<Element> elements=root.get(i).elements();
					for(int j=0;j<elements.size();j++){
						Element tElement=elements.get(j);
						if (tElement.attributeValue("id").equals(list)) {
							((Element)tElement.elements().get(0)).setText("1");
						} else {
							((Element)tElement.elements().get(0)).setText("0");
						}
					}
					XMLWriter writer = new XMLWriter(new FileOutputStream(url_LCJS));
					writer.write(document);
	                writer.close();
	                result="1";
					break;
				}
			}
			response.setCharacterEncoding("utf8");
			response.getWriter().write(result);
		} catch (DocumentException e1) {
					// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void Reload_project() throws IOException {
	response.sendRedirect("http://localhost:8080/manager/my/reload?path=/LCJS");
	}
}
