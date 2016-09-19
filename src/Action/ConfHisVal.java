/**
 * @author qihai
 * @time 2016年5月6日01:48:19
 */
package Action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Action;
import Dao.IConfHisValDao;
import Dao.LSTZZInfoDao;
import Entity.LSSET;
import Entity.LSTZZInfo;
import Entity.T_LSTZZ_RES;
import Entity.T_LSTZZ_RES_5;

public class ConfHisVal extends Action {

	private IConfHisValDao confhisvalDao;
	private LSTZZInfoDao lstzz_InfoDao;
	private LSSET confinfo;

	public LSSET getConfinfo() {
		return confinfo;
	}

	public void setConfinfo(LSSET confinfo) {
		this.confinfo = confinfo;
	}

	public IConfHisValDao getConfhisvalDao() {
		return confhisvalDao;
	}

	public void setConfhisvalDao(IConfHisValDao confhisvalDao) {
		this.confhisvalDao = confhisvalDao;
	}
	
	public LSTZZInfoDao getLstzz_InfoDao() {
		return lstzz_InfoDao;
	}

	public void setLstzz_InfoDao(LSTZZInfoDao lstzz_InfoDao) {
		this.lstzz_InfoDao = lstzz_InfoDao;
	}
	
	public String getConf(){
		confinfo = confhisvalDao.getConf();
//		System.out.println("/////////////////////////////");
//		System.out.println("step = " + confinfo.getStep());
//		System.out.println("nextDate = " + confinfo.getNextDate());
//		System.out.println("/////////////////////////////");
		return "success";
	}
	
	//获取历史特征值参数
	public void getConfInfo() throws IOException{
		confinfo = confhisvalDao.getConf();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsObj = JSONObject.fromObject(confinfo);
		jsonArray.add(jsObj);
		response.getWriter().write(jsonArray.toString());
	}
	
	public void updateConf() throws IOException{
		String step = request.getParameter("step");
		String nextDate = request.getParameter("nextDate");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("step", step);
		map.put("nextDate", nextDate);
		int conf_state = 0;
		if(confhisvalDao.updateConf(map)>=1)
			conf_state = 1;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsObj = new JSONObject();
		jsObj.put("ok", conf_state);
		jsonArray.add(jsObj);
		response.getWriter().write(jsonArray.toString());
	}
	
	public void getLSTZZInfo() throws IOException{
		Map<String, Object> map = new HashMap<String,Object>();
		int pageindex = Integer.parseInt(request.getParameter("pageindex"));
		map.put("pageindex", pageindex);
		map.put("pagesize", 10);
		List<LSTZZInfo> results = lstzz_InfoDao.getLSTZZInfo(map);
		if(results==null){
			response.getWriter().write("0");
			return;
		}else{
			JSONArray jsonArray = new JSONArray();
			for(LSTZZInfo result:results){
				JSONObject jsObj = JSONObject.fromObject(result);
				jsonArray.add(jsObj);
			}
			response.getWriter().write(jsonArray.toString());
		}
	}
	
	public void getLSTZZInfoCount() throws IOException{
		Map<String, Object> map = new HashMap<String,Object>();
		int count = lstzz_InfoDao.getLSTZZInfoCount(map);
		response.getWriter().write(count + "");
	}
	
	public void getLSTZZ_RES() throws IOException{
		Map<String, Object> map = new HashMap<String,Object>();
		String instr_no = request.getParameter("instr_no");
		String table = request.getParameter("table");
		int wl_class = Integer.parseInt(request.getParameter("wl_class"));
		
		map.put("table_name", table);
		map.put("instr_no", instr_no);
		map.put("wl_class", wl_class);
		
		List<T_LSTZZ_RES> results = lstzz_InfoDao.getLSTZZ_RES(map);
		JSONArray jsonArray = new JSONArray();
		for(T_LSTZZ_RES re : results){
			JSONObject jsObj = JSONObject.fromObject(re);
			jsonArray.add(jsObj);
		}
		response.getWriter().write(jsonArray.toString());
	}
	
	public void getWL() throws IOException{
		String dt = request.getParameter("dt");
		double wl = lstzz_InfoDao.getWaterLevel(dt);
		double tmpWL = Math.round(wl*10)/10.0;
		System.out.println(wl);
		response.getWriter().write(tmpWL + "");
	}
	
	private double getWL(String dt){
		double wl = lstzz_InfoDao.getWaterLevel(dt);
		return Math.round(wl*10)/10.0;
	}
	
	public void getLSTZZ_RES_5() throws IOException{
		Map<String, Object> map = new HashMap<String,Object>();
		String instr_no = request.getParameter("instr_no");
		String wl_date = request.getParameter("wl_date");
		String table = request.getParameter("table");
		
		double wl = getWL(wl_date);
		
		map.put("table_name", table);
		map.put("instr_no", instr_no);
//		map.put("wl_date", wl_date);
		map.put("wl", wl);
		
		List<T_LSTZZ_RES_5> results = lstzz_InfoDao.getLSTZZ_RES_5(map);
		JSONArray jsonArray = new JSONArray();
		for(T_LSTZZ_RES_5 re : results){
			JSONObject jsObj = JSONObject.fromObject(re);
			jsonArray.add(jsObj);
		}
		response.getWriter().write(jsonArray.toString());
	}
	

}
