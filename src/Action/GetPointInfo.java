package Action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Action;
import Dao.ICommonPreHandleBatchDao;
import Dao.IModelPointDao;
import Dao.ISINGLE_PREDICTIONDao;
import Entity.ModelPoint;
import Entity.SINGLE_PREDICTION;

public class GetPointInfo extends Action {
	private IModelPointDao modelPointDao;
	private ISINGLE_PREDICTIONDao single_predictionDao;
	private ICommonPreHandleBatchDao commonPreHandleBatchDao;
	
	public ICommonPreHandleBatchDao getCommonPreHandleBatchDao() {
		return commonPreHandleBatchDao;
	}

	public void setCommonPreHandleBatchDao(
			ICommonPreHandleBatchDao commonPreHandleBatchDao) {
		this.commonPreHandleBatchDao = commonPreHandleBatchDao;
	}

	public ISINGLE_PREDICTIONDao getSingle_predictionDao() {
		return single_predictionDao;
	}

	public void setSingle_predictionDao(ISINGLE_PREDICTIONDao single_predictionDao) {
		this.single_predictionDao = single_predictionDao;
	}

	public IModelPointDao getModelPointDao() {
		return modelPointDao;
	}

	public void setModelpointDao(IModelPointDao modelPointDao) {
		this.modelPointDao = modelPointDao;
	}
	
	public void getSinglePre() throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		String index = request.getParameter("pageindex");
		String size = request.getParameter("pagesize");
		int pageindex = 1;
		int pagesize = 5;
		
		if(index != null && !("".equals(index)))
			pageindex = Integer.parseInt(index);
		if(size != null && !("".equals(size)))
			pagesize = Integer.parseInt(size);
		List<SINGLE_PREDICTION> list = single_predictionDao.getSingle_predictions(map);
		JSONArray jsonArray = new JSONArray();
		for(int i=(pageindex-1)*pagesize;i<list.size() && i<pageindex * pagesize;i++){
			JSONObject jsObj = JSONObject.fromObject(list.get(i));
			jsonArray.add(jsObj);
		}
		jsonArray.add(list.size());
//		for(SINGLE_PREDICTION sp : list){
//			JSONObject jsObj = JSONObject.fromObject(sp);
//			jsonArray.add(jsObj);
//		}
		response.getWriter().write(jsonArray.toString());
	}
	
	//获取预处理批次记录总数
	public void getPreHandleCount() throws IOException{
		JSONArray jsonArray = new JSONArray();
		JSONObject jsObj = new JSONObject();
		jsObj.put("totalrecord", commonPreHandleBatchDao.getPreHandleCount());
		jsonArray.add(jsObj);
		response.getWriter().write(jsonArray.toString());
	}
	
	//获取预处理批次记录总数by time
	public void getPreHandleTimeCount() throws IOException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		String time = df.format(new Date());// new Date()为获取当前系统时间
		String starttime = request.getParameter("StartTime"), endtime = request
				.getParameter("EndTime");
		if (starttime == null || starttime == "")
			starttime = time;
		if (endtime == null || endtime == "")
			endtime = time;
		JSONArray jsonArray = new JSONArray();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("starttime", starttime + " 00:00:00");
		map.put("endtime", endtime + " 23:59:59");
		JSONObject jsObj = new JSONObject();
		jsObj.put("totalrecord_time", commonPreHandleBatchDao.getPreHandleTimeCount(map));
		jsonArray.add(jsObj);
		response.getWriter().write(jsonArray.toString());
	}
	
	//获取预处理批次记录总数by point
	public void getPreHandlePointCount() throws IOException{
		JSONArray jsonArray = new JSONArray();
		JSONObject jsObj = new JSONObject();
//		jsObj.put("totalrecord", commonPreHandleBatchDao.getPreHandlePointCount());
		jsonArray.add(jsObj);
		response.getWriter().write(jsonArray.toString());
	}

	public void getComponent() throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		String Instr_no = request.getParameter("in_no");
		String PointType = "premodel";
		if(Instr_no.contains(",")){
			String[] nos = Instr_no.split(",");
			Instr_no = "";
			for(String no:nos)
				Instr_no += "'" + no + "',";
			Instr_no += "'#'";
		}else{
			Instr_no = "'" + Instr_no + "'";
		}
		map.put("Instr_no", Instr_no);
		map.put("PointType", PointType);
		List<ModelPoint> point = modelPointDao.getPointComponent(map);
		JSONArray jsonArray = new JSONArray();
		for(ModelPoint thepoint : point){
			JSONObject jsObj = JSONObject.fromObject(thepoint);
			System.out.println(jsObj);
			jsonArray.add(jsObj);
		}
		response.getWriter().write(jsonArray.toString());
	}
	
	public void getComponentByPoint() throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		String points = request.getParameter("points");
		map.put("point", points);
		String rs = modelPointDao.getComponentByPoint(map);
		response.getWriter().write(rs);
	}
	
}