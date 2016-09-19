package Action;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import util.Action;

import Dao.IT_ZB_PLDao;
import Dao.IT_ZB_PZDao;
import Entity.T_ZB_PL;
import Entity.T_ZB_PZ;

public class MultiPointScreening extends Action{
	private IT_ZB_PLDao t_zb_pzDao;
	public IT_ZB_PLDao getT_zb_pzDao() {
		return t_zb_pzDao;
	}

	public void setT_zb_pzDao(IT_ZB_PLDao t_zb_pzDao) {
		this.t_zb_pzDao = t_zb_pzDao;
	}

	public void execute() throws IOException {
		
		String instr_no=request.getParameter("instr_no");
		int days=Integer.parseInt(request.getParameter("time"));
		long time = System.currentTimeMillis();
		
		Timestamp endtime=Timestamp.valueOf("2014-03-01 00:00:00");
		Timestamp starttime = Timestamp.valueOf("2014-02-01 00:00:00");
		switch (days) {
		case 15:starttime=Timestamp.valueOf("2014-02-14 00:00:00");
			break;
		case 30:starttime=Timestamp.valueOf("2014-02-01 00:00:00");
			break;
		case 60:starttime=Timestamp.valueOf("2014-01-01 00:00:00");
			break;
		}
		
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("instr_no", instr_no);
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		
//		List<T_ZB_PZ> list=t_zb_pzDao.multiPointScreening(map);
		List<T_ZB_PL> list=t_zb_pzDao.getPointDataPL(map);
		BigDecimal[][] array =new BigDecimal[4][list.size()] ;
		String [] timeStrings=new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			/*String[] dt=list.get(i).getDt().split(" ")[0].split("-");*/
			array[0][i]=list.get(i).getDisplace_x();                 //实际值
			array[1][i]=list.get(i).getDisplace_x_pred();            //预测值
			array[2][i]=list.get(i).getDisplace_x_err_rate();        //相对误差
			array[3][i]=list.get(i).getDisplace_x_error();            //绝对误差
			/*timeStrings[i]=dt[1]+"."+dt[2];     //选取时间的年月日 */	
			timeStrings[i]=list.get(i).getDt();
		}
		JSONArray jsonArray=new JSONArray();
		jsonArray.add(array[0]);
		jsonArray.add(array[1]);
		jsonArray.add(array[2]);
		jsonArray.add(array[3]);
		jsonArray.add(timeStrings);
		response.getWriter().write(jsonArray.toString());
	}

	
}

/*String instr_no=request.getParameter("instr_no");

String [] instr_noStrings=instr_no.split(",");
JSONArray jsonArrayAll=new JSONArray();
for (int i = 0; i < instr_noStrings.length; i++) {
	List<T_ZB_PZ> list=t_zb_pzDao.multiPointScreening(instr_noStrings[i]);
	JSONArray jsonArray=new JSONArray();
	for (int j = 0; j < list.size(); j++) {
		JSONObject jsObj=JSONObject.fromObject(list.get(j));
		jsonArray.add(jsObj);
	}
	jsonArrayAll.add(jsonArray);
}
response.getWriter().write(jsonArrayAll.toString());
}
*/