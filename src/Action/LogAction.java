package Action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import Dao.IDefLogDao;
import Entity.DEFLOG;
import Entity.T_OUTLIER;
import util.Action;
import util.PageControl;

public class LogAction extends Action {
	
	private IDefLogDao logDao;

	public IDefLogDao getLogDao() {
		return logDao;
	}

	public void setLogDao(IDefLogDao logDao) {
		this.logDao = logDao;
	}
	
	// TODO 根据日期，获取当前日期的日志信息
	public void execute() throws IOException {
//		sjTypes = request.getParameter("sjTypes");
		JSONArray jsonArray=new JSONArray();
		
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsonArray.toString());
	}
	
	public void getLogByTime() throws IOException{
		String start=request.getParameter("start");
		String end=request.getParameter("end");
		Map<String, String> map=new HashMap<String,String>();
		map.put("start", start+ " 00:00:00");
		map.put("end", end+ " 23:59:59");
		List<DEFLOG> list=logDao.getLogByTime(map);
		JSONArray jsonArray=new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			DEFLOG deflog=list.get(i);
			String string=deflog.getLogcontent();
			String result="";
			if(string.split("INFO").length==2){
				String temp[];
				try {
					temp = string.split("#{4}")[1].split("--|;|：|；|:");
					for (int j = 1; j < temp.length; j=j+2) {
						if(j == 5){
							result= result + "同步数据" + temp[j].trim()+",";
						}else{
							result=result+temp[j].trim()+",";
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(string);
					System.out.println(deflog.getTime());
				}
			}
			else if(string.split("ERROR").length==2){
				result="无,无,目标数据库连接失败！,0秒";
			}
			deflog.setLogcontent(result);
			jsonArray.add(deflog);
		}
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsonArray.toString());
	}
}
