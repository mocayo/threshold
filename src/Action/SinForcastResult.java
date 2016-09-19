/**
 * @author qihai
 * @time 2016年5月19日11:12:56
 */
package Action;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Action;
import util.OperExcel;
//import util.OperExcel;
import util.PageControl;
import Dao.ICommonPredictDao;
import Dao.IMonitorItemTypeDao;
import Dao.ISINGLE_PREDICTIONDao;
import Dao.ISIN_PRE_AUTODao;
import Dao.ITABLE_TYPEDao;
import Entity.AutoPreInfo;
import Entity.AutoPreInfo2;
import Entity.CommonPredict;
import Entity.MonitorItemType;
import Entity.SINGLE_PREDICTION;
import Entity.TABLE_TYPE;

public class SinForcastResult extends Action{
	
	private int len = 20;//每页长度
	private PageControl pc;//页面控制
	private int cpage;//当前页
	
	private int the_total = 0;
//	private IT_ZB_PLDao it_zb_plDao;
	private ICommonPredictDao commonPredictDao;
	private ISINGLE_PREDICTIONDao single_predictionDao;
	private ISIN_PRE_AUTODao sin_preAutoDao;
	private ITABLE_TYPEDao table_typeDao;
	private String decType_Sel;//监测仪器选择
	private String dam_Sel;//坝段选择
	private String instr_Sel;//测点编号选择
	private String component_Sel;//预测分量选择
	private String preId;//预测批次id
	private String sTime;//预测开始时间
	private String eTime;//预测结束时间
	private IMonitorItemTypeDao monitorItemTypeDao;

	public IMonitorItemTypeDao getMonitorItemTypeDao() {
		return monitorItemTypeDao;
	}

	public void setMonitorItemTypeDao(IMonitorItemTypeDao monitorItemTypeDao) {
		this.monitorItemTypeDao = monitorItemTypeDao;
	}
	
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


	public ISINGLE_PREDICTIONDao getSingle_predictionDao() {
		return single_predictionDao;
	}


	public void setSingle_predictionDao(ISINGLE_PREDICTIONDao single_predictionDao) {
		this.single_predictionDao = single_predictionDao;
	}


	public ITABLE_TYPEDao getTable_typeDao() {
		return table_typeDao;
	}


	public void setTable_typeDao(ITABLE_TYPEDao table_typeDao) {
		this.table_typeDao = table_typeDao;
	}


	public String getPreId() {
		return preId;
	}


	public void setPreId(String preId) {
		this.preId = preId;
	}

	public ICommonPredictDao getCommonPredictDao() {
		return commonPredictDao;
	}


	public void setCommonPredictDao(ICommonPredictDao commonPredictDao) {
		this.commonPredictDao = commonPredictDao;
	}


	public ISIN_PRE_AUTODao getSin_preAutoDao() {
		return sin_preAutoDao;
	}


	public void setSin_preAutoDao(ISIN_PRE_AUTODao sin_preAutoDao) {
		this.sin_preAutoDao = sin_preAutoDao;
	}

	private List<AutoPreInfo> getAutoList() throws IOException,ParseException {
		String tableName = request.getParameter("title").trim();      //表名
		String decType_Sel = request.getParameter("decType_Sel").trim(); //类型
		int pageindex = Integer.parseInt(request.getParameter("pageindex"));
		instr_Sel = request.getParameter("instr_Sel").trim();
		if(instr_Sel.contains(",")){
			String[] points = instr_Sel.split(",");
			instr_Sel = "";
			for(String point:points){
				instr_Sel += "'" + point + "',";
			}
			instr_Sel += "'#'";
		}else{
			instr_Sel = "'" + instr_Sel + "'";
		}
		
		String dataType_Sel = request.getParameter("dataType_Sel");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		sTime = request.getParameter("sTime");
		Calendar cd = Calendar.getInstance();
		cd.setTime(df.parse(sTime));
		cd.add(Calendar.DATE, 1);
		String nextTime = df.format(cd.getTime());
		eTime = request.getParameter("eTime");
		List<TABLE_TYPE> tablelist = new ArrayList<TABLE_TYPE>();
		List<AutoPreInfo> paraList = new ArrayList<AutoPreInfo>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageindex", pageindex);
		map.put("pagesize", 10);
		if (tableName==null||tableName.equals("")){
			tablelist = table_typeDao.getTable_Types();
			for(int k=0;k<tablelist.size();k++){
				map.clear();
				map.put("tableName",tablelist.get(k).getTable().trim());
				map.put("decType_Sel",decType_Sel);
				map.put("instr_no", instr_Sel);
				map.put("status", dataType_Sel);
				map.put("sTime", sTime);
				map.put("nextTime", nextTime);
				map.put("eTime", eTime);
				List<AutoPreInfo> tempList = sin_preAutoDao.getAllDataByPage(map);
				paraList.addAll(tempList);
				the_total = sin_preAutoDao.getAllDataCount(map);
			}
		} else {
			map.put("tableName",tableName.trim());
			map.put("decType_Sel",decType_Sel);
			map.put("instr_no", instr_Sel);
			map.put("status", dataType_Sel);
			map.put("sTime", sTime);
			map.put("nextTime", nextTime);
			map.put("eTime", eTime);
			paraList = sin_preAutoDao.getAllDataByPage(map);
			the_total = sin_preAutoDao.getAllDataCount(map);
		}
		return paraList;
	}
	
	public void sinPreAuto() throws IOException,ParseException {
		List<AutoPreInfo> paraList = getAutoList();
		JSONArray jsonArray=new JSONArray();
		//String staticsStr = "";
		for (int i = 0; i < paraList.size(); i++) {
			AutoPreInfo tempAuto = paraList.get(i);
			//staticsStr = tempAuto.getStatics();
//			String status1 = tempAuto.getStatus_r1();
//			if (status1.equals("1")){
//				tempAuto.setStatus_r1("一级预警");
//			} else if (status1.equals("2")){
//				tempAuto.setStatus_r1("二级预警");
//			} else if (status1.equals("3")){
//				tempAuto.setStatus_r1("三级预警");
//			} else if (status1.equals("4")){
//				tempAuto.setStatus_r1("原数据异常");
//			} else if (status1.equals("5")){
//				tempAuto.setStatus_r1("正常");
//			}
//			String status2 = tempAuto.getStatus_r2();
//			if (status2.equals("1")){
//				tempAuto.setStatus_r2("一级预警");
//			} else if (status2.equals("2")){
//				tempAuto.setStatus_r2("二级预警");
//			} else if (status2.equals("3")){
//				tempAuto.setStatus_r2("三级预警");
//			} else if (status2.equals("4")){
//				tempAuto.setStatus_r2("原数据异常");
//			} else if (status2.equals("5")){
//				tempAuto.setStatus_r2("正常");
//			}
//			String status3 = tempAuto.getStatus_r3();
//			if (status3.equals("1")){
//				tempAuto.setStatus_r3("一级预警");
//			} else if (status3.equals("2")){
//				tempAuto.setStatus_r3("二级预警");
//			} else if (status3.equals("3")){
//				tempAuto.setStatus_r3("三级预警");
//			} else if (status3.equals("4")){
//				tempAuto.setStatus_r3("原数据异常");
//			} else if (status3.equals("5")){
//				tempAuto.setStatus_r3("正常");
//			}
			JSONObject jsObj = JSONObject.fromObject(tempAuto);
			jsonArray.add(jsObj);
		}
		jsonArray.add(the_total);
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsonArray.toString());
	}
	
	public void getAllSinPreAuto() throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		List<AutoPreInfo> results = sin_preAutoDao.getAllData(map);
		JSONArray jsonArray=new JSONArray();
		for (int i = 0; i < results.size(); i++) {
			AutoPreInfo tempAuto = results.get(i);
			JSONObject jsObj = JSONObject.fromObject(tempAuto);
			jsonArray.add(jsObj);
		}
		jsonArray.add(the_total);
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsonArray.toString());
	}
	
	public void sinPreAutoByPage() throws IOException,ParseException {
//		List<AutoPreInfo> paraList = getAutoList();
		Map<String,Object> map = new HashMap<String,Object>();
		String point = request.getParameter("point");
		String table = request.getParameter("table");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		map.put("point", point);
		map.put("table", table);
		map.put("table_res", table+"_RES1");
		map.put("start", start);
		map.put("end", end);
//		System.out.println("=================================================>>>>");
//		System.out.println(map.get("point"));
//		System.out.println(map.get("table"));
//		System.out.println(map.get("table_res"));
		List<AutoPreInfo2> result = sin_preAutoDao.getAllDataByPage2(map);
//		System.out.println("=================================================>>>>");
//		System.out.println(result.size());
//		System.out.println("===================");
		JSONArray jsonArray=new JSONArray();
		for (int i = 0; i < result.size(); i++) {
			AutoPreInfo2 tempAuto = result.get(i);
			//System.out.println(tempAuto.getDT());
			JSONObject jsObj = JSONObject.fromObject(tempAuto);
			jsonArray.add(jsObj);
		}
		//jsonArray.add(the_total);
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsonArray.toString());
	}
	
	public void getTestInfo() throws IOException{
		List<AutoPreInfo2> result = sin_preAutoDao.getTestAutoInfo();
		JSONArray jsonArray=new JSONArray();
		for (int i = 0; i < result.size(); i++) {
			AutoPreInfo2 tempAuto = result.get(i);
			//System.out.println(tempAuto.getDT());
			JSONObject jsObj = JSONObject.fromObject(tempAuto);
			jsonArray.add(jsObj);
		}
		//jsonArray.add(the_total);
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsonArray.toString());
	}
	
	public void autoPrint() throws IOException,ParseException {
		List<AutoPreInfo> paraList = getAutoList();
		
		List<String[]> objList = new ArrayList<String[]>();
		String[] objTitle = new String[]{"序号","采集时间","测点编号","水位","实测值","预测值","预测分量","数据类型"};
		objList.add(objTitle);
		
		for (int i = 0; i < paraList.size(); i++) {
			AutoPreInfo tempAuto = paraList.get(i);
//			String status1 = tempAuto.getStatus_r1();
//			if (status1.equals("1")){
//				tempAuto.setStatus_r1("一级预警");
//			} else if (status1.equals("2")){
//				tempAuto.setStatus_r1("二级预警");
//			} else if (status1.equals("3")){
//				tempAuto.setStatus_r1("三级预警");
//			} else if (status1.equals("4")){
//				tempAuto.setStatus_r1("原数据异常");
//			} else if (status1.equals("5")){
//				tempAuto.setStatus_r1("正常");
//			}
//			String status2 = tempAuto.getStatus_r2();
//			if (status2.equals("1")){
//				tempAuto.setStatus_r2("一级预警");
//			} else if (status2.equals("2")){
//				tempAuto.setStatus_r2("二级预警");
//			} else if (status2.equals("3")){
//				tempAuto.setStatus_r2("三级预警");
//			} else if (status2.equals("4")){
//				tempAuto.setStatus_r2("原数据异常");
//			} else if (status2.equals("5")){
//				tempAuto.setStatus_r2("正常");
//			}
//			String status3 = tempAuto.getStatus_r3();
//			if (status3.equals("1")){
//				tempAuto.setStatus_r3("一级预警");
//			} else if (status3.equals("2")){
//				tempAuto.setStatus_r3("二级预警");
//			} else if (status3.equals("3")){
//				tempAuto.setStatus_r3("三级预警");
//			} else if (status3.equals("4")){
//				tempAuto.setStatus_r3("原数据异常");
//			} else if (status3.equals("5")){
//				tempAuto.setStatus_r3("正常");
//			}
//			String[] obj1 = new String[] {String.valueOf(i+1),tempAuto.getDt(),tempAuto.getInstr_no(),tempAuto.getWl().toString(),tempAuto.getRealVal().toString(),tempAuto.getPreVal_r1().toString(),"r1",tempAuto.getStatus_r1()};
//			String[] obj2 = new String[] {String.valueOf(i+1),tempAuto.getDt(),tempAuto.getInstr_no(),tempAuto.getWl().toString(),tempAuto.getRealVal().toString(),tempAuto.getPreVal_r2().toString(),"r2",tempAuto.getStatus_r2()};
//			String[] obj3 = new String[] {String.valueOf(i+1),tempAuto.getDt(),tempAuto.getInstr_no(),tempAuto.getWl().toString(),tempAuto.getRealVal().toString(),tempAuto.getPreVal_r3().toString(),"r3",tempAuto.getStatus_r3()};
			
			String[] obj1 = new String[] {String.valueOf(i+1),tempAuto.getDt(),tempAuto.getInstr_no(),tempAuto.getWl().toString(),tempAuto.getRealVal().toString(),tempAuto.getPreVal().toString(),"r1",tempAuto.getStatus()};
			String[] obj2 = new String[] {String.valueOf(i+1),tempAuto.getDt(),tempAuto.getInstr_no(),tempAuto.getWl().toString(),tempAuto.getRealVal().toString(),tempAuto.getPreVal().toString(),"r2",tempAuto.getStatus()};
			String[] obj3 = new String[] {String.valueOf(i+1),tempAuto.getDt(),tempAuto.getInstr_no(),tempAuto.getWl().toString(),tempAuto.getRealVal().toString(),tempAuto.getPreVal().toString(),"r3",tempAuto.getStatus()};
			
			objList.add(obj1);
			objList.add(obj2);
			objList.add(obj3);
		}
		OperExcel oExcep = new OperExcel();
		oExcep.arrayToExcel(objList,"单点预测(自动)处理结果",objTitle.length);
		response.getWriter().write("true");
	}
	
	public void compSelect() throws IOException {
		preId = request.getParameter("preId");
		String instr_no = request.getParameter("instr_no");
		List<SINGLE_PREDICTION> paraList2 = single_predictionDao.getPreById(preId);
		SINGLE_PREDICTION temp = paraList2.get(0);
		String compStr = temp.getPre_component();
		String tableName = temp.getDet_instr();
		MonitorItemType moniter = monitorItemTypeDao.getItemTypeByTableName(tableName);
		String[] compStrs = compStr.split(";");
		String[] checkStr = compStr.split(",");
		List<MonitorItemType> paraList = new ArrayList<MonitorItemType>();
		if(checkStr.length == 1){
			if (compStr.equalsIgnoreCase("r1")){
				MonitorItemType monitorTemp = new MonitorItemType();
				monitorTemp.setTable_name(tableName);
				monitorTemp.setR1(moniter.getR1());
				monitorTemp.setR2("0");
				monitorTemp.setR3("0");
				paraList.add(monitorTemp);
			}else if (compStr.equalsIgnoreCase("r2")){
				MonitorItemType monitorTemp = new MonitorItemType();
				monitorTemp.setTable_name(tableName);
				monitorTemp.setR1("0");
				monitorTemp.setR2(moniter.getR2());
				monitorTemp.setR3("0");
				paraList.add(monitorTemp);
			}else if (compStr.equalsIgnoreCase("r3")){
				MonitorItemType monitorTemp = new MonitorItemType();
				monitorTemp.setTable_name(tableName);
				monitorTemp.setR1("0");
				monitorTemp.setR2("0");
				monitorTemp.setR3(moniter.getR3());
				paraList.add(monitorTemp);
			}
		} else {
			for(int i=0;i<compStrs.length-1;i++){
				if(compStrs[i].indexOf(instr_no)!=-1){
					String ri = compStrs[i].split(",")[1];
					if (ri.equalsIgnoreCase("r1")){
						MonitorItemType monitorTemp = new MonitorItemType();
						monitorTemp.setTable_name(tableName);
						monitorTemp.setR1(moniter.getR1());
						monitorTemp.setR2("0");
						monitorTemp.setR3("0");
						paraList.add(monitorTemp);
					}else if (ri.equalsIgnoreCase("r2")){
						MonitorItemType monitorTemp = new MonitorItemType();
						monitorTemp.setTable_name(tableName);
						monitorTemp.setR1("0");
						monitorTemp.setR2(moniter.getR2());
						monitorTemp.setR3("0");
						paraList.add(monitorTemp);
					}else if (ri.equalsIgnoreCase("r3")){
						MonitorItemType monitorTemp = new MonitorItemType();
						monitorTemp.setTable_name(tableName);
						monitorTemp.setR1("0");
						monitorTemp.setR2("0");
						monitorTemp.setR3(moniter.getR3());
						paraList.add(monitorTemp);
					}
				}
			}
		}
		
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < paraList.size(); i++) {
			JSONObject jsObj = JSONObject.fromObject(paraList.get(i));
			jsonArray.add(jsObj);
		}
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsonArray.toString());
	}
	
	public void detailedData() throws IOException,ParseException {
		preId = request.getParameter("preId");
		String modelId = request.getParameter("modelId");
		String dataModel = request.getParameter("dataModel");
		String instr_no = request.getParameter("instr_no").trim();
		String component_val = request.getParameter("component_val");
		List<SINGLE_PREDICTION> mylist = new ArrayList<SINGLE_PREDICTION>();
		mylist = single_predictionDao.getPreById(preId);
		String tableName = mylist.get(0).getDet_instr();
		String component_Sel = mylist.get(0).getPre_component();
		JSONObject json = null;
		if (dataModel.equals("one")){
			dataModel = "date1";
			json = JSONObject.fromObject(mylist.get(0).getDate1Str());
		} else if (dataModel.equals("two")){
			dataModel = "date2";
			json = JSONObject.fromObject(mylist.get(0).getDate2Str());
		} else if (dataModel.equals("three")){
			dataModel = "date3";
			json = JSONObject.fromObject(mylist.get(0).getDate3Str());
		}
		
		if (!json.isEmpty()) {
			JSONObject dateObjTmp1 = json.getJSONObject(instr_no);
			JSONObject dateObjTmp2 = dateObjTmp1.getJSONObject(component_val);
			JSONObject dateObj = dateObjTmp2.getJSONObject(modelId);
			if(!dateObj.isEmpty()) {
				String dateStr = dateObj.getString(dataModel);
				Timestamp ts = new Timestamp(System.currentTimeMillis());
//				dateStr = "'2014-09-17 12:00:00.0','2014-09-16 12:00:00.0','2014-09-15 12:00:00.0','2014-02-20 08:00:00.0','2014-02-19 08:00:00.0','2014-02-18 08:00:00.0','2014-02-17 08:00:00.0'";
				String[] dateS = dateStr.split(",");
				JSONArray jsonArray = new JSONArray();
				JSONObject comObj = new JSONObject();
				comObj.put("component_Sel", component_Sel);
				jsonArray.add(comObj);
				for (int i = 0;i < dateS.length; i++){
					List<CommonPredict> paraList = new ArrayList<CommonPredict>();
					Map<String, Object> map = new HashMap<String, Object>();
					String str = dateS[i].substring(0, 19);
					ts = Timestamp.valueOf(str);
					map.put("preId", preId);
					map.put("dateS", ts);
					map.put("tableName",tableName.trim());
					paraList = commonPredictDao.getExceptionDetial(map);
//				for (int i = 0; i < paraList.size(); i++) {
					JSONObject jsObj = JSONObject.fromObject(paraList.get(0));
					jsonArray.add(jsObj);
//				}
				}
				response.setCharacterEncoding("utf8");
				response.getWriter().write(jsonArray.toString());
			}
		}
	}
	
	
	/**
	 * EXCEL导出
	 */
	public void exportPiciInDetail () throws IOException, ParseException
	{
		String m1oneData = "";
		String m1twoData = "";
		String m1threeData = "";
		String m1rmsePow = "";
		String m0oneData = "";
		String m0twoData = "";
		String m0threeData = "";
		String m0rmsePow = "";
		String m1countOneData = "";
		String m1countTwoData = "";
		String m1countThreeData = "";
		String m1countNomalData = "";
		String m0countOneData = "";
		String m0countTwoData = "";
		String m0countThreeData = "";
		String m0countNomalData = "";
		
		List<SINGLE_PREDICTION> myList=single_predictionDao.getAllforPrint();
		
		List<String[]> objList = new ArrayList<String[]>();
		String[] objTitle = new String[]{"序号","预测时间","监测仪器","坝段","测点编号","预测分量","开始时间","结束时间","一级预警","二级预警","三级预警","正常","均方差平方和开方"};
		objList.add(objTitle);
		for(int i=0;i<myList.size();i++)
		{
			SINGLE_PREDICTION pici = myList.get(i);
			JSONObject m0Array = null;
			JSONObject m1Array = null;
			JSONObject json = JSONObject.fromObject(pici.getStaticsData());
			if (!json.isEmpty()) {
				m1Array = json.getJSONObject("m1");
				m1oneData = m1Array.getString("oneData");
				m1twoData = m1Array.getString("twoData");
				m1threeData = m1Array.getString("threeData");
				m1rmsePow = m1Array.getString("rmsePow");
				m1countOneData = m1Array.getString("countOneData");
				m1countTwoData = m1Array.getString("countTwoData");
				m1countThreeData = m1Array.getString("countThreeData");
				m1countNomalData = m1Array.getString("countNomalData");

				m0Array = json.getJSONObject("m0");
				m0oneData = m0Array.getString("oneData");
				m0twoData = m0Array.getString("oneData");
				m0threeData = m0Array.getString("oneData");
				m0rmsePow = m0Array.getString("oneData");
				m0countOneData = m0Array.getString("countOneData");
				m0countTwoData = m0Array.getString("countTwoData");
				m0countThreeData = m0Array.getString("countThreeData");
				m0countNomalData = m0Array.getString("countNomalData");
			}
			String oneData = "";
			String twoData = "";
			String threeData = "";
			String normalData = "";
			String rmsePow = "";
			
			if (!(m1Array==null||m1Array.isEmpty())){
				oneData += "指数拟合("+m1oneData+")："+m1countOneData+"条；";
				twoData += "指数拟合("+m1twoData+")："+m1countTwoData+"条；";
				threeData += "指数拟合("+m1threeData+")："+m1countThreeData+"条；";
				normalData += "指数拟合："+m1countNomalData+"条；";
				rmsePow += "指数拟合："+m1rmsePow+"；";
			}
			
			if (!(m0Array==null||m0Array.isEmpty())){
				oneData += "二次多项式("+m0oneData+")："+m0countOneData+"条；";
				twoData += "二次多项式("+m0twoData+")："+m0countTwoData+"条；";
				threeData += "二次多项式("+m0threeData+")："+m0countThreeData+"条；";
				normalData += "二次多项式："+m0countNomalData+"条；";
				rmsePow += "二次多项式："+m0rmsePow+"；";
			}
			
			String[] obj = new String[] {String.valueOf(i+1),pici.getDt(),pici.getDet_instr(),pici.getDam_no(),pici.getObser_no(),pici.getPre_component(),pici.getStartTime(),pici.getEndTime(),oneData,twoData,threeData,normalData,rmsePow};
			objList.add(obj);
		}
		
		OperExcel oExcep = new OperExcel();
		oExcep.arrayToExcel(objList,"单点预测（人工）处理结果",objTitle.length);
		response.getWriter().write("true");
	}
	
	public void execute() throws IOException, ParseException {
		preId = request.getParameter("preId");
		String instr_no = request.getParameter("instr_no"); 
		if(instr_no == null){
			instr_no = "";
		}
//		decType_Sel = request.getParameter("decType_Sel");
//		dam_Sel = request.getParameter("dam_Sel");
//		instr_Sel = request.getParameter("instr_Sel");
//		component_Sel = request.getParameter("component_Sel");
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		sTime = request.getParameter("sTime");
//		if (sTime != null && sTime != "") {
//			sTime = sTime.replaceAll("-", "/");
//		}
//		Timestamp sDate = null;
//		if (sTime != null && sTime != "") {
//			java.util.Date date1 = df.parse(sTime);
//			String time1 = df.format(date1);
//			sDate = Timestamp.valueOf(time1);
//		}
//		eTime = request.getParameter("eTime");
//		if (eTime != null && eTime != "") {
//			eTime = eTime.replaceAll("-", "/");
//		}
//		Timestamp eDate = null;
//		if (eTime != null && eTime != "") {
//			java.util.Date date2 = df.parse(eTime);
//			String time2 = df.format(date2);
//			eDate = Timestamp.valueOf(time2);
//		}
		String cpageStr = request.getParameter("cpage");
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
		map.put("preId", preId);
//		map.put("det_type",decType_Sel);
//		map.put("dam_no", dam_Sel);
//		map.put("obser_no", instr_Sel);
//		map.put("pre_component", component_Sel);
//		map.put("sTime", sTime);
//		map.put("eTime", eTime);
		String component_name="";
		List<CommonPredict> paraList = new ArrayList<CommonPredict>();
		String staticsStr = "";
		MonitorItemType moniter = new MonitorItemType();
		if (preId!=null&&!preId.equals("")){
			List<SINGLE_PREDICTION> paraList2 = single_predictionDao.getPreById(preId);
			SINGLE_PREDICTION temp = paraList2.get(0);
			Map<String,Object> resParas = new HashMap<String,Object>();
			resParas.put("tableName",temp.getDet_instr());
			resParas.put("prediction_id",preId);
			resParas.put("instr_no",instr_no);
			resParas.put("start",start);
			resParas.put("len", len);
			paraList = commonPredictDao.resultFYById(resParas);
			pc.setTotalitem(commonPredictDao.countResultById(resParas));
			decType_Sel = table_typeDao.getTypesByInstr(temp.getDet_instr())+","+temp.getDet_instr();
			dam_Sel = temp.getDam_no();
			instr_Sel = temp.getObser_no();
			component_Sel = temp.getPre_component();
			sTime = temp.getStartTime();
			eTime = temp.getEndTime();
			
			moniter = monitorItemTypeDao.getItemTypeByTableName(temp.getDet_instr());
			if(component_Sel.toLowerCase().contains("r1")){
				component_name += "," + moniter.getR1();
			}
			if(component_Sel.toLowerCase().contains("r2")){
				component_name += "," + moniter.getR2();
			}
			if(component_Sel.toLowerCase().contains("r3")){
				component_name += "," + moniter.getR3();
			}
			staticsStr = temp.getStaticsData();
			

		} else {
//			paraList = commonPredictDao.resultByParas(map);
		}
//		pc.setTotalitem(commonPredictDao.countResultById(preId));
		JSONObject jsObj1 = JSONObject.fromObject(pc);
		jsonArray.add(jsObj1);
		JSONObject jsObj2 = new JSONObject();
		jsObj2.put("decType_Sel", decType_Sel);
		jsonArray.add(jsObj2);
		JSONObject jsObj3 = new JSONObject();
		jsObj3.put("dam_Sel", dam_Sel.trim());
		jsonArray.add(jsObj3);
		JSONObject jsObj4 = new JSONObject();
		jsObj4.put("instr_Sel", instr_Sel);
		jsonArray.add(jsObj4);
		JSONObject jsObj5 = new JSONObject();
		jsObj5.put("component_Sel", component_Sel);
		jsonArray.add(jsObj5);
		JSONObject jsObj6 = new JSONObject();
		jsObj6.put("sTime", sTime);
		jsonArray.add(jsObj6);
		JSONObject jsObj7 = new JSONObject();
		jsObj7.put("eTime", eTime);
		jsonArray.add(jsObj7);
		JSONObject jsObj8 = JSONObject.fromObject(staticsStr);
		jsonArray.add(jsObj8);
		JSONObject jsObj10 = new JSONObject();
		jsObj10.put("component_name", component_name.substring(1));
		jsObj10.put("r1",moniter.getR1());
		jsObj10.put("r2",moniter.getR2());
		jsObj10.put("r3",moniter.getR3());
		jsonArray.add(jsObj10);
		for (int i = 0; i < paraList.size(); i++) {
			
			JSONObject jsObj9 = JSONObject.fromObject(paraList.get(i));
			jsonArray.add(jsObj9);
		}
		
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsonArray.toString());
	}
	
}
