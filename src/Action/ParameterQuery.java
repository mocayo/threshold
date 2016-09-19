package Action;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import Dao.IMODEL_PARADao;
import Entity.MODEL_PARA;

import util.Action;

public class ParameterQuery extends Action {
	private IMODEL_PARADao model_paraDao;
	public IMODEL_PARADao getModel_paraDao() {
		return model_paraDao;
	}

	public void setModel_paraDao(IMODEL_PARADao model_paraDao) {
		this.model_paraDao = model_paraDao;
	}
	
	public void execute() throws IOException {
//		Timestamp firsttime=Timestamp.valueOf(request.getParameter("FirstTime"));
//		Timestamp lasttime=Timestamp.valueOf(request.getParameter("LastTime"));
		Timestamp firsttime=new Timestamp(System.currentTimeMillis()-15*24*60*60*1000);
		Timestamp lasttime=new Timestamp(System.currentTimeMillis());
		try {
			int mid = Integer.parseInt(request.getParameter("MID"));
			
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("firsttime", firsttime);
			map.put("lasttime", lasttime);
			map.put("mid", mid);
			List<MODEL_PARA> list =model_paraDao.parameterQuery(map);
			JSONArray jsonArray=new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				JSONObject jsonObject=JSONObject.fromObject(list.get(i));
				jsonArray.add(jsonObject);
			}
			response.getWriter().write(jsonArray.toString());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return;
		}
	}

	
}
