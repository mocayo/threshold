package Action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Action;
import Dao.IPointInfoDao;

public class QueryPoint extends Action{
	
	private IPointInfoDao pointInfoDao;

	public IPointInfoDao getPointInfoDao() {
		return pointInfoDao;
	}


	public void setPointInfoDao(IPointInfoDao pointInfoDao) {
		this.pointInfoDao = pointInfoDao;
	}
	
	public void execute() throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", "qh");
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj = JSONObject.fromObject(map);
		jsonArray.add(jsonObj);
		response.getWriter().write(jsonArray.toString());
	}
	
	//基本部位
	public void getBasicItem() throws IOException{
		List<String> basics = pointInfoDao.getBasicItem();
		JSONArray jsonArray = new JSONArray();
		for(String basic:basics)
			jsonArray.add(basic);
		response.getWriter().write(jsonArray.toString());
	}
	
	//监测项目
	public void getMonitor() throws IOException{
		String basicId = request.getParameter("basicId");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("basicId", basicId);
		List<String> basics = pointInfoDao.getMonitor(map);
		JSONArray jsonArray = new JSONArray();
		for(String basic:basics)
			jsonArray.add(basic);
		response.getWriter().write(jsonArray.toString());
	}
	
	//仪器类型
	public void getInstrType() throws IOException{
		String basicId = request.getParameter("basicId");
		String monitorItem = request.getParameter("monitorItem");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("basicId", basicId);
		map.put("monitorItem", monitorItem);
		List<String> basics = pointInfoDao.getInstrType(map);
		JSONArray jsonArray = new JSONArray();
		for(String basic:basics)
			jsonArray.add(basic);
		response.getWriter().write(jsonArray.toString());
	}
	
	//测点编号
	public void getObservePoints() throws IOException{
//		String basicId = "4";
//		String monitorItem = "101";
//		String instr_type = "1103";
		String basicId = request.getParameter("basicId");
		String monitorItem = request.getParameter("monitorItem");
		String instr_type = request.getParameter("instr_type");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("basicId", basicId);
		map.put("monitorItem", monitorItem);
		map.put("instr_type", instr_type);
		List<String> basics = pointInfoDao.getObservePoints(map);
		JSONArray jsonArray = new JSONArray();
		for(String basic:basics)
			jsonArray.add(basic);
		response.getWriter().write(jsonArray.toString());
	}
	
	//测点分量
	public void getComponent() throws IOException{
		String[] points = request.getParameter("point").split(",");
		Map<String,Object> map = new HashMap<String,Object>();
		JSONArray jsonArray = new JSONArray();
		for(String point:points){
			map.put("point", point);
			String comps =  pointInfoDao.getComponent(map);
			Map<String,String> tmp = new HashMap<String,String>();
			tmp.put("point", point);
			tmp.put("comps", comps);
			jsonArray.add(tmp);
		}
		response.getWriter().write(jsonArray.toString());
	}

}
