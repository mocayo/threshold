package Action;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import Dao.IDEFTABLETYPEDao;
import Entity.DEFTABLETYPE;
import Entity.MONITORITEMS;
import util.Action;

public class GetMonitorItems extends Action {
	private IDEFTABLETYPEDao deftabletypeDao;
	public IDEFTABLETYPEDao getDeftabletypeDao() {
		return deftabletypeDao;
	}

	public void setDeftabletypeDao(IDEFTABLETYPEDao deftabletypeDao) {
		this.deftabletypeDao = deftabletypeDao;
	}
	public void execute() throws IOException {
		List<DEFTABLETYPE> list=deftabletypeDao.getMonitorItems();
		JSONArray jsonArray=new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject jsObj=JSONObject.fromObject(list.get(i));
			jsonArray.add(jsObj);
		}
		response.getWriter().write(jsonArray.toString());
	}

	
}
