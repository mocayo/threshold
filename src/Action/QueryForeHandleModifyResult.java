package Action;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import Dao.IT_ZB_PL_ForeHandleDao;
import Entity.T_ZB_PL_ForeHandle;
import util.Action;

public class QueryForeHandleModifyResult extends Action {
	private IT_ZB_PL_ForeHandleDao t_zb_pl_forehandleDao;
	
	public void execute() throws IOException{
		try {
			String starttimelong=request.getParameter("StartTime")+" 00:00:00";
			String endtimelong=request.getParameter("EndTime")+" 00:00:00";
			Timestamp starttime =Timestamp.valueOf(starttimelong);
			Timestamp endtime=Timestamp.valueOf(endtimelong);
			JSONArray jsonArray=new JSONArray();
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("starttime", starttime);
			map.put("endtime", endtime);
			List<String> timelist=t_zb_pl_forehandleDao.getForeHandleModifyResult(map);
			for (int i = 0; i < timelist.size(); i++) {
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("HandleTime", timelist.get(i));
				List<T_ZB_PL_ForeHandle> list=
						t_zb_pl_forehandleDao.queryForeHandleModifyResult(timelist.get(i));
				List<String[]>liststringList=new ArrayList<String[]>();
				for(int j=0;j<list.size();j++){
					String [] resultStrings=new String[7];
					T_ZB_PL_ForeHandle tempForeHandle=(T_ZB_PL_ForeHandle)list.get(j);
					resultStrings[0]=tempForeHandle.getInstr_no();
					resultStrings[1]=tempForeHandle.getDt().toString().split("\\.")[0];
					resultStrings[2]=tempForeHandle.getDisplace_x().toString();
					resultStrings[3]=tempForeHandle.getDisplace_y().toString();
					resultStrings[4]=tempForeHandle.getHandle_time().toString().split("\\.")[0];
					resultStrings[5]=tempForeHandle.isHandle_result()==true?"正常":"异常";
					resultStrings[6]=tempForeHandle.getPeople_handle()==1?"正常":"异常";
					liststringList.add(resultStrings);
				}
				
				jsonObject.put("HandleList", liststringList);
				jsonArray.add(jsonObject);
			}
			response.getWriter().write(jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public IT_ZB_PL_ForeHandleDao getT_zb_pl_forehandleDao() {
		return t_zb_pl_forehandleDao;
	}

	public void setT_zb_pl_forehandleDao(
			IT_ZB_PL_ForeHandleDao t_zb_pl_forehandleDao) {
		this.t_zb_pl_forehandleDao = t_zb_pl_forehandleDao;
	}
}
