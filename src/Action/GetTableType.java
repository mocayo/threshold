package Action;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import Dao.ITABLE_TYPEDao;
import Entity.TABLE_TYPE;

import util.Action;

public class GetTableType extends Action {
	private ITABLE_TYPEDao table_typeDao;

	public ITABLE_TYPEDao getTable_typeDao() {
		return table_typeDao;
	}

	public void setTable_typeDao(ITABLE_TYPEDao table_typeDao) {
		this.table_typeDao = table_typeDao;
	}
	public void execute() throws IOException {
		try {
			List<TABLE_TYPE> list = table_typeDao.getTable_Types();
			JSONArray jsonArray=new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				JSONObject jsObj=JSONObject.fromObject(list.get(i));
				jsonArray.add(jsObj);
			}
			response.getWriter().write(jsonArray.toString());
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return;
		}
	}
}