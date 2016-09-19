package Action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Action;
import Dao.ICommonPredictDao;
import Dao.IDEFTABLETYPEDao;
import Dao.ISINGLE_PREDICTIONDao;
import Dao.ISIN_PRE_AUTODao;
import Dao.IThresholdConfDao;
import Dao.PointDetailedResultDao;
import Entity.DEFTABLETYPE;
import Entity.PointDetailedResultInfo;
import Entity.PointError;
import Entity.SINGLE_PREDICTION;
import Entity.T_SIN_PRE_AUTO;

public class GetSinglePreInfo extends Action {
	
	private ISINGLE_PREDICTIONDao single_predictionDao;
	private ISIN_PRE_AUTODao sin_preAutoDao;
	private PointDetailedResultDao point_DetailedResultDao;
	private IDEFTABLETYPEDao deftabletypeDao;
	private IThresholdConfDao thresholdDao;
	private ICommonPredictDao commonPredictDao;
	
	public IThresholdConfDao getThresholdDao() {
		return thresholdDao;
	}

	public void setThresholdDao(IThresholdConfDao thresholdDao) {
		this.thresholdDao = thresholdDao;
	}

	public IDEFTABLETYPEDao getDeftabletypeDao() {
		return deftabletypeDao;
	}

	public void setDeftabletypeDao(IDEFTABLETYPEDao deftabletypeDao) {
		this.deftabletypeDao = deftabletypeDao;
	}

	public PointDetailedResultDao getPoint_DetailedResultDao() {
		return point_DetailedResultDao;
	}

	public void setPoint_DetailedResultDao(
			PointDetailedResultDao point_DetailedResultDao) {
		this.point_DetailedResultDao = point_DetailedResultDao;
	}

	public ISIN_PRE_AUTODao getSin_preAutoDao() {
		return sin_preAutoDao;
	}

	public void setSin_preAutoDao(ISIN_PRE_AUTODao sin_preAutoDao) {
		this.sin_preAutoDao = sin_preAutoDao;
	}

	public ISINGLE_PREDICTIONDao getSingle_predictionDao() {
		return single_predictionDao;
	}

	public void setSingle_predictionDao(ISINGLE_PREDICTIONDao single_predictionDao) {
		this.single_predictionDao = single_predictionDao;
	}
	
	public ICommonPredictDao getCommonPredictDao() {
		return commonPredictDao;
	}

	public void setCommonPredictDao(ICommonPredictDao commonPredictDao) {
		this.commonPredictDao = commonPredictDao;
	}

	public void getInfoByPreId() throws IOException{
		String preId = request.getParameter("preId");
		//事实上只查出来一个
		List<SINGLE_PREDICTION> singleprelist = single_predictionDao.getPreById(preId);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsObj = JSONObject.fromObject(singleprelist.get(0));
		jsonArray.add(jsObj);
		response.getWriter().write(jsonArray.toString());
	}
	
	public void getDrawChartData() throws IOException {
		Map<String,Object> map = new HashMap<String,Object>();
		String preId = request.getParameter("preId");
		String tableName = request.getParameter("tableName");
		String instr_no = request.getParameter("instr_no");
		String stime = request.getParameter("stime");
		String etime = request.getParameter("etime");
		String comp = request.getParameter("comp");
		
		System.out.println(tableName);
		System.out.println(instr_no);
		System.out.println(stime);
		System.out.println(etime);
		System.out.println(comp);
		
		map.put("preId", preId);
		map.put("tableName", tableName);
		map.put("instr_no", instr_no);
		map.put("stime", stime);
		map.put("etime", etime);
		map.put("comp", comp);
		
		JSONArray jsonArray = new JSONArray();
		List<T_SIN_PRE_AUTO> chartDatas = sin_preAutoDao.getDrawChartData(map);
		if(chartDatas==null){
			response.getWriter().write("0");
			return;
		}
		/*
		Map<String,String> result = new HashMap<String, String>();
		result.put("charttitle", stime + "~" + etime);
		result.put("subtitle", "测点：" + instr_no + " 分量：" + chartDatas.get(0).getComponent());
		
		//保留8位小数
//		java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.00000000");
		String bestFit = "";
		String models = "";
		for(T_SIN_PRE_AUTO data:chartDatas){
			if(data.getIsBest()==1){
				bestFit += data.getPreVal() + ",";
			}
			String key = "model" + data.getModelId();
			String value = data.getPreVal() + "";
//			String value = myformat.format(data.getPreVal());
//			String realValue = data.getRealVal() + "," + result.get("realValue");
//			result.put("realVal", realValue);
			if(result.keySet().contains(key)){
				value += "," + result.get(key);
			}else{
				models += key + ",";
			}
			result.put(key, value);
		}
		if(models.length()==0){
			response.getWriter().write("0");
			return;
		}
		models = models.substring(0,models.length()-1);
		result.put("models", models);
		result.put("start", stime);
		int realcount = (result.get(models.substring(0,models.indexOf(",")))).split(",").length;
		
		String realValue = "";
		for(int i=0;i<realcount;i++){
			realValue += chartDatas.get(i).getRealVal() + ",";
		}
		realValue = realValue.substring(0,realValue.lastIndexOf(","));
		bestFit = bestFit.substring(0,bestFit.lastIndexOf(","));
		result.put("realVal", realValue);
		result.put("bestFit", bestFit);*/
		for(T_SIN_PRE_AUTO data:chartDatas){
			JSONObject jsObj = JSONObject.fromObject(data);
			jsonArray.add(jsObj);
		}
		response.getWriter().write(jsonArray.toString());
	}
	
	public void getDetailInfoData() throws IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		String tableName = request.getParameter("tableName");
		String instr_no = request.getParameter("instr_no");
		int pageindex = Integer.parseInt(request.getParameter("pageindex"));
		instr_no = "'" + instr_no.replaceAll(",", "','") + "'";
		String[] time = request.getParameter("time").split(",");
		String stime = time[0];
		String etime = time[1];
		String preId = request.getParameter("preId");
		String comp = request.getParameter("comp");
		map.put("comp", comp);
		map.put("preId", preId);
		map.put("tableName", tableName);
		map.put("instr_no", instr_no);
		map.put("stime", stime);
		map.put("etime", etime);
		int modelcount = point_DetailedResultDao.getPointModelIdCount(map); 
		if(modelcount==0)
			modelcount = 3;
		map.put("pagesize",10*modelcount);
		map.put("pageindex", pageindex);
//		map.put("tableName", "T_ZB_JZ_AUTO");
//		map.put("instr_no", "C4-A13-LF2-J01-01");
		List<PointDetailedResultInfo> resultList = point_DetailedResultDao.getPointDetailedResultInfo(map);
		JSONArray jsonArray = new JSONArray();
		for(PointDetailedResultInfo data:resultList){
			JSONObject jsObj = JSONObject.fromObject(data);
			jsonArray.add(jsObj);
		}
		JSONObject jsObj = new JSONObject();
		jsObj.put("modelcount", modelcount);
		jsonArray.add(jsObj);
		response.getWriter().write(jsonArray.toString());
	}
	
	public void getDetailInfoDataCount() throws IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		String tableName = request.getParameter("tableName");
		String instr_no = request.getParameter("instr_no");
		map.put("tableName", tableName);
		map.put("instr_no", instr_no);
		instr_no = "'" + instr_no.replaceAll(",", "','") + "'";
		String[] time = request.getParameter("time").split(",");
		String stime = time[0];
		String etime = time[1];
		String preId = request.getParameter("preId");
		String comp = request.getParameter("comp");
		map.put("comp", comp);
		map.put("preId", preId);
		map.put("tableName", tableName);
		map.put("instr_no", instr_no);
		map.put("stime", stime);
		map.put("etime", etime);
		int modelcount = point_DetailedResultDao.getPointModelIdCount(map); 
		if(modelcount==0)
			modelcount = 3;
		int count = point_DetailedResultDao.getPointDetailedResultInfoCount(map);
		count /= modelcount;
		response.getWriter().write(count + "");
	}
	
	//最大日期
	public void getMaxDate() throws IOException{
		String callbackname = request.getParameter("jsoncallback");
		JSONArray jsonArray = new JSONArray();
		String maxdt = point_DetailedResultDao.getMaxDate();
		jsonArray.add(maxdt);
		if(callbackname == null){
			response.getWriter().write(jsonArray.toString());
		}else{
			response.getWriter().write(callbackname + "(" + jsonArray.toString() + ")");
		}
	}
	
	//误差分析
	public void getErrateByPoint() throws IOException{
		String instr_no = request.getParameter("instr_no");
		String table = request.getParameter("table");
		String comp = request.getParameter("comp");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("instr_no", instr_no);
		map.put("comp", comp);
		map.put("table", table + "_RES1");
		List<PointError> results = point_DetailedResultDao.getErrateByPoint(map);
		JSONArray jsonArray = new JSONArray();
		for(PointError re:results){
			re.setDT(re.getDT().substring(0, 10));
			JSONObject jsonObj = JSONObject.fromObject(re);
			jsonArray.add(jsonObj);
		}
		response.getWriter().write(jsonArray.toString());
	}
	
	//用来计算每天异常点json字符串
	public void getAllErrorPoints(){
		List<String> tables = getAllTableType();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		try {
			Date date1 = sdf.parse("2016-04-08");
			Date date2 = sdf.parse("2016-04-09");
			start.setTime(date1);
			end.setTime(date2);
			Map<String,Object> map = new HashMap<String, Object>();
			String _errRate = thresholdDao.getIndexErrorRate();
			double errRate = 0;
			if(_errRate!=null)
				errRate = Integer.parseInt(_errRate)/100.0;
			
			while(start.before(end)){
				String day = sdf.format(start.getTime());
				start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);
				//根据所有的表格获取结果
				System.out.println("第" + day + "天:");
				List<PointDetailedResultInfo> resultList = new ArrayList<PointDetailedResultInfo>();
				for(String tab:tables){
					map.clear();
					map.put("stime", day);
					map.put("etime", day);
					map.put("err_rate", errRate);
					map.put("tableName", tab.trim() + "_RES1");
					List<PointDetailedResultInfo> tmpList = point_DetailedResultDao.getErrorPoints(map);
					System.out.println("获取" + tab.trim() + "_RES1表数据" + tmpList.size() + "条.");
					resultList.addAll(tmpList);
				}
				int total = resultList.size();
				int pagesize = 10;
				int pagemax = (total - 1) /10 + 1;
				for(int i=0; i<pagemax; i++){
					JSONArray jsonArray = new JSONArray();
					int j = 0;
					int page = i + 1;
					for(; j<pagesize; j++){
						int index = i*pagesize + j;
						if(index<total){
							JSONObject jObj = JSONObject.fromObject(resultList.get(index));
							jsonArray.add(jObj);
						}
					}
					map.clear();
					map.put("day", day);
					map.put("page", page);
					map.put("total", total);
					map.put("result", jsonArray.toString());
					point_DetailedResultDao.addErrorResultByDay(map);
					System.out.println("结果json串:" + jsonArray.toString());
				}
//				for(int i=(pageindex-1)*pagesize;i<resultList.size() && i<pageindex*pagesize;i++){
//					JSONObject jsObj = JSONObject.fromObject(resultList.get(i));
//					jsonArray.add(jsObj);
//				}
			}
		} catch (ParseException e1) {}
	}
	
	private boolean checkDay(String day){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("day", day);
		int result = point_DetailedResultDao.checkErrorResultByDay(map);
		if(result == 0)return false;
		return true;
	}
	
	//修改后获取首页异常点字符串
	public void getErrorPoints2() throws IOException{
		JSONArray resultsArray = new JSONArray();
		List<String> results = new ArrayList<String>();
		//首先检验该天数据是否已经计算过
		String day = request.getParameter("day");
		if(day == null || "".equals(day))return;
		String callbackname = request.getParameter("jsoncallback");
		int totalSize = 0;
		if(checkDay(day)){
			//已经计算过字符串
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("day", day);
			results = point_DetailedResultDao.getErrorResultByDay(map);
			totalSize = point_DetailedResultDao.getErrorResultTotalByDay(map);
		}else{
			if(day.compareTo(commonPredictDao.getCurrentTDay())==1){
				System.out.println(day + "当天阈值尚未计算。。");
			}else{
				//清理ErrorResult表，防止出现当天脏数据
				point_DetailedResultDao.delErrorResultByDay(day);
				//计算字符串
				//根据所有的表格获取结果
				List<String> tables = getAllTableType();
				System.out.println("第" + day + "天:");
				List<PointDetailedResultInfo> resultList = new ArrayList<PointDetailedResultInfo>();
				Map<String, Object> map = new HashMap<String, Object>();
				String _errRate = thresholdDao.getIndexErrorRate();
				double errRate = 0;
				if(_errRate!=null)
					errRate = Integer.parseInt(_errRate)/100.0;
				for(String tab:tables){
					map.clear();
					map.put("stime", day);
					map.put("etime", day);
					map.put("err_rate", errRate);
					map.put("tableName", tab.trim() + "_RES1");
					List<PointDetailedResultInfo> tmpList = point_DetailedResultDao.getErrorPoints(map);
					System.out.println("获取" + tab.trim() + "_RES1表数据" + tmpList.size() + "条.");
					resultList.addAll(tmpList);
				}
				int total = resultList.size();
				totalSize = total;
				int pagesize = 10;
				int pagemax = (total - 1) /10 + 1;
				for(int i=0; i<pagemax; i++){
					JSONArray jsonArray = new JSONArray();
					int j = 0;
					int page = i + 1;
					for(; j<pagesize; j++){
						int index = i*pagesize + j;
						if(index<total){
							JSONObject jObj = JSONObject.fromObject(resultList.get(index));
							jsonArray.add(jObj);
						}
					}
					map.clear();
					map.put("day", day);
					map.put("page", page);
					map.put("total", total);
					map.put("result", jsonArray.toString());
					results.add(jsonArray.toString());
					point_DetailedResultDao.addErrorResultByDay(map);
					System.out.println("结果json串:" + jsonArray.toString());
				}
			}
		}
		resultsArray.add(results);
		resultsArray.add(totalSize);
		if(callbackname == null){
			response.getWriter().write(resultsArray.toString());
		}else{
			response.getWriter().write(callbackname + "(" + resultsArray.toString() + ")");
		}
	}
	
	//首页显示异常点
	public void getErrorPoints() throws IOException{
		List<String> tables = getAllTableType();
		List<PointDetailedResultInfo> resultList = new ArrayList<PointDetailedResultInfo>();
		String callbackname = request.getParameter("jsoncallback");
//		float err_rate = Float.parseFloat(request.getParameter("err_rate"));
		int pageindex = Integer.parseInt(request.getParameter("pageindex"));
		if(pageindex==0)pageindex = 1;
		int pagesize = 10;
		Map<String,Object> map = new HashMap<String, Object>();
		String _errRate = thresholdDao.getIndexErrorRate();
		double errRate = Integer.parseInt(_errRate)/100.0;
		map.put("err_rate", errRate);
		for(String tab:tables){
//			String tableName = request.getParameter("tableName");
			String stime = request.getParameter("starttime");
			String etime = request.getParameter("endtime");
			map.put("tableName", tab.trim() + "_RES1");
			map.put("stime", stime);
			map.put("etime", etime);
//			map.put("pageindex", pageindex);
//			map.put("pagesize", 10);
			
			List<PointDetailedResultInfo> tmpList = point_DetailedResultDao.getErrorPoints(map);
			resultList.addAll(tmpList);
		}
//		System.out.println("==================================");
//		System.out.println(resultList.size() + "aaa");
//		System.out.println("==================================");
//		for(PointDetailedResultInfo data:resultList){
//			JSONObject jsObj = JSONObject.fromObject(data);
//			jsonArray.add(jsObj);
//		}
		if(resultList.size()==0)return;
		JSONArray jsonArray = new JSONArray();
		//取出分页数据
		for(int i=(pageindex-1)*pagesize;i<resultList.size() && i<pageindex*pagesize;i++){
			JSONObject jsObj = JSONObject.fromObject(resultList.get(i));
			jsonArray.add(jsObj);
		}
		//误差水平统计
		int[] level = new int[11];
		if(pageindex == 1){
			for(PointDetailedResultInfo pdri:resultList){
				double err_rate = Double.parseDouble(pdri.getErr_rate());
				if(err_rate<=0.1)level[0]++;
				else if(err_rate<=0.2)level[1]++;
				else if(err_rate<=0.3)level[2]++;
				else if(err_rate<=0.4)level[3]++;
				else if(err_rate<=0.5)level[4]++;
				else if(err_rate<=0.6)level[5]++;
				else if(err_rate<=0.7)level[6]++;
				else if(err_rate<=0.8)level[7]++;
				else if(err_rate<=0.9)level[8]++;
				else if(err_rate<=1.0)level[9]++;
				else level[10]++;
			}
		}
		jsonArray.add(level);
		jsonArray.add(resultList.size());
		if(callbackname == null){
			response.getWriter().write(jsonArray.toString());
		}else{
			response.getWriter().write(callbackname + "(" + jsonArray.toString() + ")");
		}
	}
	
	public void getErrorPointsCount() throws IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		String tableName = request.getParameter("tableName");
		String stime = request.getParameter("starttime");
		String etime = request.getParameter("endtime");
		String callbackname = request.getParameter("jsoncallback");
		map.put("tableName", tableName);
		map.put("stime", stime);
		map.put("etime", etime);
		map.put("tableName", tableName);
		int count = point_DetailedResultDao.getErrorPointsCount(map);
		if(callbackname == null){
			response.getWriter().write(count+"");
		}else{
			response.getWriter().write(callbackname + "(" + count + ")");
		}
	}
	
	public void getThresholdDay() throws IOException{
		String callbackname = request.getParameter("jsoncallback");
		String currentDay = commonPredictDao.getCurrentTDay();
		if(callbackname == null){
			response.getWriter().write(currentDay+"");
		}else{
			JSONArray array = new JSONArray();
			array.add(currentDay);
			response.getWriter().write(callbackname + "(" + array.toString() + ")");
		}
	}
	
	public void getKeyPoints() throws IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		String instr_no = request.getParameter("instr_no");
		String comp = request.getParameter("comp");
		String callbackname = request.getParameter("jsoncallback");
		
//		int period = Integer.parseInt(request.getParameter("period"));
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		map.put("instr_no", instr_no);
		map.put("comp", comp);
//		map.put("period", period);
		map.put("start", start);
		map.put("end", end);
		List<PointDetailedResultInfo> resultList = point_DetailedResultDao.getKeyPoints(map);
		JSONArray jsonArray = new JSONArray();
		for(PointDetailedResultInfo data:resultList){
			JSONObject jsObj = JSONObject.fromObject(data);
			jsonArray.add(jsObj);
		}
		if(callbackname == null){
			response.getWriter().write(jsonArray.toString());
		}else{
			response.getWriter().write(callbackname + "(" + jsonArray.toString() + ")");
		}
	}
	
	private List<String> getAllTableType(){
		List<String> tabletypes = new ArrayList<String>();
		List<DEFTABLETYPE> tables = deftabletypeDao.getMonitorItems();
		for(DEFTABLETYPE table:tables)
			tabletypes.add(table.getTable());
		return tabletypes;
	}
	
}