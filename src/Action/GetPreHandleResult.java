package Action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Action;
import Dao.ICommonPreHandleBatchDao;
import Entity.AutoPreHandleInfo;

public class GetPreHandleResult extends Action{
	private ICommonPreHandleBatchDao commonPreHandleBatchDao;

	public ICommonPreHandleBatchDao getCommonPreHandleBatchDao() {
		return commonPreHandleBatchDao;
	}

	public void setCommonPreHandleBatchDao(
			ICommonPreHandleBatchDao commonPreHandleBatchDao) {
		this.commonPreHandleBatchDao = commonPreHandleBatchDao;
	}
	
	public void getPreHandleResult2() throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		List<AutoPreHandleInfo> results = commonPreHandleBatchDao.getPreHandleResult2(map);
		JSONArray jsonArray = new JSONArray();
		for(AutoPreHandleInfo re:results){
			JSONObject obj = JSONObject.fromObject(re);
			jsonArray.add(obj);
		}
		response.getWriter().write(jsonArray.toString());
	}
}
