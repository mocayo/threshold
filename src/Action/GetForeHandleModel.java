package Action;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import Dao.IMODEL_NAMEDao;
import Entity.MODEL_NAME;

import util.Action;

public class GetForeHandleModel extends Action {
	private IMODEL_NAMEDao model_nameDao;

	public IMODEL_NAMEDao getModel_nameDao() {
		return model_nameDao;
	}

	public void setModel_nameDao(IMODEL_NAMEDao model_nameDao) {
		this.model_nameDao = model_nameDao;
	}
	public void execute() throws IOException {
		try {
			int use_type=Integer.parseInt(request.getParameter("Use_Type"));
			List<MODEL_NAME> list=model_nameDao.getModel_NAMEs(use_type);
			JSONArray jsonArray=new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				JSONObject jsObj=JSONObject.fromObject(list.get(i));
				jsonArray.add(jsObj);
			}
			response.getWriter().write(jsonArray.toString());
		} catch (NumberFormatException e) {
			return;
		}
	}
}