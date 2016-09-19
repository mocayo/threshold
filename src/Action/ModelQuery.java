package Action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import util.Action;
import Dao.IMODEL_NAMEDao;
import Entity.MODEL_NAME;

public class ModelQuery extends Action {
	private IMODEL_NAMEDao model_nameDao;

	public IMODEL_NAMEDao getModel_nameDao() {
		return model_nameDao;
	}

	public void setModel_nameDao(IMODEL_NAMEDao model_nameDao) {
		this.model_nameDao = model_nameDao;
	}

	public void execute() throws IOException{
		String monitoritem=request.getParameter("MonitorItem");
		
		List<MODEL_NAME> list = model_nameDao.modelQuery(monitoritem);
		
		JSONArray jsonArray=new JSONArray();
		
		for (int i = 0; i < list.size(); i++) {
			JSONObject jsonObject=JSONObject.fromObject(list.get(i));
			jsonArray.add(jsonObject);
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonArray.toString());
	}

}
