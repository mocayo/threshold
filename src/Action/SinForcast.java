package Action;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import Dao.ISINGLE_PREDICTIONDao;
import Entity.SINGLE_PREDICTION;
import util.Action;
import util.IdGenerator;
import util.PageControl;

public class SinForcast extends Action{
	
	private int len = 8;//每页长度
	private PageControl pc;//翻页对象
	private int cpage;//当前页
	private ISINGLE_PREDICTIONDao single_predictionDao;
	private List<SINGLE_PREDICTION> forcastList;
	private String decType_Sel;
	private String dam_Sel;
	private String instr_Sel;
	private String component_Sel;
	private String preId;
	private String sTime;
	private String eTime;
	
	public String getDecType_Sel() {
		return decType_Sel;
	}


	public void setDecType_Sel(String decType_Sel) {
		this.decType_Sel = decType_Sel;
	}


	public String getDam_Sel() {
		return dam_Sel;
	}


	public void setDam_Sel(String dam_Sel) {
		this.dam_Sel = dam_Sel;
	}


	public String getInstr_Sel() {
		return instr_Sel;
	}


	public void setInstr_Sel(String instr_Sel) {
		this.instr_Sel = instr_Sel;
	}


	public String getComponent_Sel() {
		return component_Sel;
	}


	public void setComponent_Sel(String component_Sel) {
		this.component_Sel = component_Sel;
	}


	public String getPreId() {
		return preId;
	}


	public void setPreId(String preId) {
		this.preId = preId;
	}


	public ISINGLE_PREDICTIONDao getSingle_predictionDao() {
		return single_predictionDao;
	}


	public void setSingle_predictionDao(ISINGLE_PREDICTIONDao single_predictionDao) {
		this.single_predictionDao = single_predictionDao;
	}

	public void prediction() throws IOException {
		decType_Sel = request.getParameter("decType_Sel");
		dam_Sel = request.getParameter("dam_Sel");
		instr_Sel = request.getParameter("instr_Sel");
		component_Sel = request.getParameter("component_Sel");
		sTime = request.getParameter("sTime");
		eTime = request.getParameter("eTime");
		Date today = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> map = new HashMap<String, Object>();
		IdGenerator idgenerator = new IdGenerator();
		preId = idgenerator.generateId();
		map.put("id", preId);
		map.put("dt", sf.format(today));
		map.put("det_type",decType_Sel);
		map.put("dam_no", dam_Sel);
		map.put("obser_no", instr_Sel);
		map.put("pre_component", component_Sel);
//		map.put("sTime", sTime);
//		map.put("eTime", eTime);
		single_predictionDao.add_singlepre(map);
//		return "detailedPre";
//		JSONArray jsonArray=new JSONArray();
		JSONObject jsObj = new JSONObject();
		jsObj.put("preId", preId);
//		jsonArray.add(jsObj);
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsObj.toString());
	}
	
	public void delPrediction () throws IOException{
		preId = request.getParameter("preId");
		JSONObject jsObj = new JSONObject();
		try {
			List<SINGLE_PREDICTION> tempList = single_predictionDao.getPreById(preId);
			Map<String, Object> delmap = new HashMap<String,Object>(); 
			delmap.put("tableName",tempList.get(0).getDet_instr());
			delmap.put("id",preId);
			single_predictionDao.del_singlepre(preId);
			single_predictionDao.del_result(delmap);
			jsObj.put("delresult", true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsObj.put("delresult", false);
		} 
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsObj.toString());
	}

	public void execute() throws IOException {
		decType_Sel = request.getParameter("decType_Sel");
		dam_Sel = request.getParameter("dam_Sel");
		instr_Sel = request.getParameter("instr_Sel");
		component_Sel = request.getParameter("component_Sel");
		String cpageStr = request.getParameter("cpage");
		sTime = request.getParameter("sTime");
		eTime = request.getParameter("eTime");
		if (cpageStr!=null&&cpageStr!=""){
			cpage = Integer.parseInt(cpageStr);
		} else {
			cpage = 1;
		}
		if (pc == null){
			pc = new PageControl();
		}
		int start = (cpage - 1) * len;
		pc.setPagesize(len);
		pc.setCpage(cpage);
		JSONArray jsonArray=new JSONArray();
//		modelCode = "8";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start",start);
		map.put("len", len);
		List<SINGLE_PREDICTION> paraList = single_predictionDao.getSingle_predictions(map);
		pc.setTotalitem(single_predictionDao.countPre());
		JSONObject jsObj1 = JSONObject.fromObject(pc);
		jsonArray.add(jsObj1);
		JSONObject jsObj2 = new JSONObject();
		jsObj2.put("decType_Sel", decType_Sel);
		jsonArray.add(jsObj2);
		JSONObject jsObj3 = new JSONObject();
		jsObj3.put("dam_Sel", dam_Sel);
		jsonArray.add(jsObj3);
		JSONObject jsObj4 = new JSONObject();
		jsObj4.put("instr_Sel", instr_Sel);
		jsonArray.add(jsObj4);
		JSONObject jsObj5 = new JSONObject();
		jsObj4.put("component_Sel", component_Sel);
		jsonArray.add(jsObj5);
		for (int i = 0; i < paraList.size(); i++) {
			JSONObject jsObj6 = JSONObject.fromObject(paraList.get(i));
//			System.out.println("==================================");
//			System.out.println(jsObj6);
//			System.out.println("==================================");
			jsonArray.add(jsObj6);
		}
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsonArray.toString());
	}
	
}
