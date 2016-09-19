package Action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Action;
import Dao.IPointInfoDao;

public class GetPointByDamAndTableName extends Action{
	private IPointInfoDao pointInfoDao;
	public void execute() throws IOException {
		String dam=request.getParameter("dam");
		String title=request.getParameter("title");
		Map<String, String> map=new HashMap<String, String>();
		if(dam!=""&&dam!=null)
			map.put("dam", dam);
		map.put("title", title);
		List<String> pointList=pointInfoDao.getPoint(map);
		JSONArray jsonArray=new JSONArray();
		for (int i = 0; i < pointList.size(); i++) {
			JSONObject jsObj=new JSONObject();
			jsObj.put("code",pointList.get(i).trim());
			jsonArray.add(jsObj);
		}
		
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsonArray.toString());
	}
	public IPointInfoDao getPointInfoDao() {
		return pointInfoDao;
	}
	public void setPointInfoDao(IPointInfoDao pointInfoDao) {
		this.pointInfoDao = pointInfoDao;
	}
	
}
