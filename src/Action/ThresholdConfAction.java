package Action;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Action;
import Dao.IThresholdConfDao;
import Entity.ThresholdConf;

public class ThresholdConfAction extends Action{

	private IThresholdConfDao thresholdDao;
	
	public IThresholdConfDao getThresholdDao() {
		return thresholdDao;
	}

	public void setThresholdDao(IThresholdConfDao thresholdDao) {
		this.thresholdDao = thresholdDao;
	}

	public void getThresoldConf() throws IOException{
		String item = request.getParameter("item");
//		String name = request.getParameter("name");
		Map<String,String> map = new HashMap<String,String>();
		map.put("item", item);
		List<ThresholdConf> confs = thresholdDao.getConfByItem(map);
		JSONArray jsonArray = new JSONArray();	
		Map<String,String> result = new HashMap<String,String>();
		for(ThresholdConf conf : confs){
			result.put(conf.getName(),conf.getValue());
		}
		JSONObject obj = JSONObject.fromObject(result);
		jsonArray.add(obj);
		response.getWriter().write(jsonArray.toString());
	}
	
	public void updateThresholdConf() throws IOException{
		String item = request.getParameter("item");
		String[] names = request.getParameter("names").split(",");
		String[] values = request.getParameter("values").split(",");
		String  result = "";
		for(int i=0;i<names.length;i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("item",item);
			map.put("name",names[i]);
			map.put("value",values[i]);
			result += thresholdDao.updateConf(map);
		}
		response.getWriter().write(result);
	}
	
	public void updateKalMan() throws IOException{
		List<String> tables = thresholdDao.getAllTableName();
		String msg = "";
		for(String table:tables){
			System.out.println(table.trim() + "_RES1");
			Map<String,String> map = new HashMap<String,String>();
			map.put("tableName", table.trim() + "_RES1");
			thresholdDao.addTableField(map);
			int del = thresholdDao.delData(map);
			msg += "为" + table.trim() + "_RES1添加字段acheck ";
			msg += "\n<br>为" + table.trim() + "_RES1删除" + del + "条记录";
		}
		response.getWriter().write(msg);
	}
	
}