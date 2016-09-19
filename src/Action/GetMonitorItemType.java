package Action;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import Dao.IMonitorItemTypeDao;
import Entity.MonitorItemType;

import util.Action;

public class GetMonitorItemType extends Action {
	private IMonitorItemTypeDao monitorItemTypeDao;

	public IMonitorItemTypeDao getMonitorItemTypeDao() {
		return monitorItemTypeDao;
	}

	public void setMonitorItemTypeDao(IMonitorItemTypeDao monitorItemTypeDao) {
		this.monitorItemTypeDao = monitorItemTypeDao;
	}

	public void execute() throws IOException {
		try {
			String table_type = request.getParameter("table_type");
			MonitorItemType monitorItem = monitorItemTypeDao.getItemTypeByTableName(table_type);
//			JSONArray jsonArray=new JSONArray();
			JSONObject jsObj=JSONObject.fromObject(monitorItem);
//			jsonArray.add(jsObj);
			response.getWriter().write(jsObj.toString());
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return;
		}
	}
}