package Action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import predict.fit.CalPredVal;
import predict.model.ExpModel;
import predict.model.PolynomialModel;
import util.Action;
import util.DataPreProcess;
import util.IdGenerator;
import Dao.ICommonPredictDao;
import Dao.IINS_SORTDao;
import Dao.IMonitorItemTypeDao;
import Dao.ISINGLE_PREDICTIONDao;
import Entity.CommonPredict;
import Entity.DEFINSSORT;
import Entity.INS_SORT;
import Entity.MonitorItemType;
import Entity.PREDICT_DATA;
import Entity.T_ZB_PL;


/**
 * 预测
 * @author Administrator
 *
 */
public class CommonPredictAction extends Action{
	
	ICommonPredictDao commonPredictDao;
	ISINGLE_PREDICTIONDao single_predictionDao;
	private IINS_SORTDao ins_sortDao;
	private IMonitorItemTypeDao monitorItemTypeDao;

	@SuppressWarnings("null")
	public void execute() throws IOException {
		long st = System.currentTimeMillis();		

		String startTime = request.getParameter("sTime"); 
		String endTime = request.getParameter("eTime");
		String tableName=request.getParameter("title").trim();      //表名
		String decType_Sel = request.getParameter("decType_Sel").trim(); //类型
		String dam_Sel = request.getParameter("dam_Sel"); //坝段
		String instr_Sel = request.getParameter("instr_Sel").trim(); //测点编号
		String monitor_type = request.getParameter("component_Sel").trim(); //分量 monitor_type
		
		//批次号
		String genId = new IdGenerator().generateId();
		
		DataPreProcess dpp = new DataPreProcess();
		
		//预测X分量
//		startTime = "2009-01-01";
//		endTime = "2013-01-01";
//		tableName = "T_ZB_EA";
//		instr_Sel = "C2C-Lf12D-D3-C-01";
//		monitor_type = "R1";
//		decType_Sel = "6";
		
		Map<String, Object> staticsMap = new HashMap<String, Object>();
		Map<String, Object> date1Map = new HashMap<String, Object>();
		Map<String, Object> date2Map = new HashMap<String, Object>();
		Map<String, Object> date3Map = new HashMap<String, Object>();
		
//		String[] pointList;
//		if(pointIdString==null||pointIdString.equals("null")){//如果传递监测点为空，则根据坝段和监测项查询所有的点
//			List<INS_SORT> iswhereList = ins_sortDao.getIns_Sorts(Integer.parseInt(decType_Sel),dam_Sel);
//			pointList=new String[iswhereList.size()];
//			for (int i = 0; i < iswhereList.size(); i++) {
//				pointList[i]=iswhereList.get(i).getDesignCode().trim();
//			}
//		}
//		else {
//			pointList = pointIdString.split(",");
//		}
//		
//		String[] componentList;
//		if(pointIdString==null||pointIdString.equals("null")){//如果传递监测点为空，则根据坝段和监测项查询所有的点
//			MonitorItemType monitorItem = monitorItemTypeDao.getItemTypeByTableName(tableName);
//			componentList = new String[3];
//			componentList[0]=","+monitorItem.getR1();
//			componentList[1]=","+monitorItem.getR2();
//			componentList[2]=","+monitorItem.getR3();
//			
//		}
//		else {
//			componentList = pointIdString.split(";");
//		}
//		
		Map<String, Object> map = new HashMap<String, Object>();
//		
//		Map<String, Object> staticsMapcd = new HashMap<String, Object>();
//		Map<String, Object> date1Mapcd = new HashMap<String, Object>();
//		Map<String, Object> date2Mapcd = new HashMap<String, Object>();
//		Map<String, Object> date3Mapcd = new HashMap<String, Object>();
//		
//		
//		for (String instr_Sel : pointList) {//得到该点所有数据
//			if(instr_Sel.contains("'")){
//				instr_Sel=instr_Sel.replaceAll("'", "''");
//			}
//		List<String> monitorList = new ArrayList<String>();
//		for (int k=0;k<componentList.length;k++){
//			if (componentList[k].contains(instr_Sel)){
//				monitorList.add(componentList[k].split(",")[1]);
//			}
//		}
//		
//		Map<String, Object> staticsMapfl = new HashMap<String, Object>();
//		Map<String, Object> date1Mapfl = new HashMap<String, Object>();
//		Map<String, Object> date2Mapfl = new HashMap<String, Object>();
//		Map<String, Object> date3Mapfl = new HashMap<String, Object>();
//		
//		for (String monitor_type : monitorList){
//			map.clear();
		map.put("tableName", tableName);
		map.put("instr_no", instr_Sel);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		
		//得到时间列表
		List<String> dtList = commonPredictDao.getTimeListByIdTm(map);
		
		for (String dt : dtList) {

System.out.println("预测日期：" + dt);

			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("tableName", tableName);
			map2.put("instr_no", instr_Sel);
			map2.put("dt", dt.substring(0, 10));
			map2.put("monitor_type", monitor_type);

			// 取得100条预处理之后的记录
			List<PREDICT_DATA> dataList = commonPredictDao.getDataByIdTm(map2);
			
			int n = dataList.size();
System.out.println("数据量：" + n);
			if (n <= 40)
				continue;
			double[] x = new double[n];
			double[] y = new double[n];

			for (int i = 0; i < dataList.size(); i++) {
				x[i] = dataList.get(i).getWl().doubleValue();
				y[i] = dataList.get(i).getData().doubleValue();
			}

			// 增加偏移量
			double offSet = dpp.addOffSet(y);
			// 指数
			List<Double> args1 = ExpModel.fit_result(x, y);
			// 二次多项式
			List<Double> args2 = PolynomialModel.fit_result(x, y);

			// 得到各模型参数
			double rmse1_best ;
			double rmse2_best ;
			if(args1 == null)
			{
				continue;
//				rmse1_best = Double.MAX_VALUE;
			}
			else {
				rmse1_best = args1.get(2);
			}
			double flag1 = args1.get(3);
			if(args2 == null)
			{
				continue;
//				rmse1_best = Double.MAX_VALUE;
			}
			else {
				rmse2_best = args2.get(3);
			}
			double flag2 = args2.get(4);

			// 得到真实值
			List<PREDICT_DATA> realData = commonPredictDao.getRealDataByIdTm(map2);
			Double wl = null;
			Double real = null;
			if (realData.isEmpty()) {
				continue;
			}
			wl = realData.get(0).getWl().doubleValue();
			real = realData.get(0).getData().doubleValue();

			CalPredVal cpv = new CalPredVal();
			Double pred;

			String fitname;
			double best_rmse;
			double pred1 = cpv.model1(args1.get(0), args1.get(1), wl) + offSet;
			double pred2 = cpv.model2(args2.get(0), args2.get(1), args2.get(2), wl)
					+ offSet;
			double best_step;
			
			
			
			// 比较
			// 指数拟合 比较优
			if (Math.abs(pred1 - real) < Math.abs(pred2 - real)) {
				fitname = "指数拟合";
				best_rmse = rmse1_best;
				best_step = flag1;
				pred = cpv.model1(args1.get(0), args1.get(1), wl) + offSet;
			} else {
				fitname = "二次多项式";
				best_rmse = rmse2_best;
				best_step = flag2;

				pred = cpv.model2(args2.get(0), args2.get(1), args2.get(2), wl)
						+ offSet;
			}
System.out.println(pred);

			String x_mid = "";
			Double temp_pred;
			Map<String, Object> resultMap = new HashMap<String, Object>();

			// 指数拟合
			Map<String, Object> m1 = new HashMap<String, Object>();
			temp_pred = cpv.model1(args1.get(0), args1.get(1), wl) + offSet;
			m1.put("pred", temp_pred);
			m1.put("error", "" + Math.abs(real - temp_pred));
			m1.put("error_rate", "" + Math.abs((real - temp_pred) / real));
			m1.put("step", flag1);
			m1.put("rmse", rmse1_best);
			resultMap.put("m1", m1);

			// 二次多项式
			Map<String, Object> m0 = new HashMap<String, Object>();
			temp_pred = cpv
					.model2(args2.get(0), args2.get(1), args2.get(2), wl)
					+ offSet;
			m0.put("pred", temp_pred);
			m0.put("step", flag2);
			m0.put("rmse", rmse2_best);
			m0.put("error", "" + Math.abs(real - temp_pred));
			m0.put("error_rate", "" + Math.abs((real - temp_pred) / real));
			resultMap.put("m0", m0);

			JSONObject json = JSONObject.fromObject(resultMap);
			x_mid = json.toString();

			Object[] result = new Object[19];

			result[0] = instr_Sel;
			result[1] = dt;
			result[2] = real;
			result[4] = best_step;
			result[5] = fitname;
			result[6] = pred;
			result[7] = Math.abs(real - pred);
			result[8] = Math.abs(real - pred) / real;
			result[16] = x_mid;
			for (int i = 0; i < result.length; i++) {
				System.out.print(result[i] + "\t");
			}

			map = new HashMap<String, Object>();
			map.put("instr_no", instr_Sel);
			map.put("dt", dt);
			map.put("wl", wl);// 水位
			map.put("prediction_id", genId);
			map.put("monitor_type",monitor_type);
			map.put(monitor_type, real);
			map.put("tableName",tableName);
			map.put(monitor_type + "_step", best_step);
			map.put(monitor_type + "_model", fitname);
			map.put(monitor_type + "_pred", pred);
			map.put(monitor_type + "_error", Math.abs(real - pred));
			map.put(monitor_type + "_err_rate", Math.abs((real - pred) / real));
			map.put(monitor_type + "_err_level", null);
			map.put(monitor_type + "_mid_result", x_mid);
			List<CommonPredict> tmp =  commonPredictDao.getDataByKeys(map);
			if (tmp.size()==0){
				commonPredictDao.add_res(map);
			} else {
				commonPredictDao.update_res(map);
			}
			
		}
		
		//单个分量信息统计
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("monitor_type",monitor_type);
		paramap.put("prediction_id",genId);
		paramap.put("tableName",tableName);
		List<String> midList = commonPredictDao.getMidModel(paramap);
		Double  m1rmsePow = 0d;
		Double  m0rmsePow = 0d;
		List<Double> m1rmseList = new ArrayList<Double>();
		List<Double> m0rmseList = new ArrayList<Double>();
		for (int i = 0; i < midList.size(); i++) {
			String jsonStr = midList.get(i);
			// String jsonStr = temp.getDisplace_x_mid_result();
			JSONObject json = JSONObject.fromObject(jsonStr);
			if (!json.isEmpty()) {
				JSONObject m1Array = json.getJSONObject("m1");
				if (!m1Array.isEmpty()) {
					String m1rmse = m1Array.getString("rmse");
					if (m1rmse != null && !m1rmse.equals("")) {
						m1rmseList.add(Double.valueOf(m1rmse));
						m1rmsePow += Math.pow(Double.valueOf(m1rmse), 2);
					}
				}
				JSONObject m0Array = json.getJSONObject("m0");
				if (!m0Array.isEmpty()) {
					String m0rmse = m0Array.getString("rmse");
					if (m0rmse != null && !m0rmse.equals("")) {
						m0rmseList.add(Double.valueOf(m0rmse));
						m0rmsePow += Math.pow(Double.valueOf(m0rmse), 2);
					}
				}
			}
		}
		Collections.sort(m1rmseList);
		Collections.sort(m0rmseList);
		int m1oneIndex = (int) (m1rmseList.size()*0.99);
		int m1twoIndex = (int) (m1rmseList.size()*0.95);
		int m1threeIndex = (int) (m1rmseList.size()*0.90);
		Double m1oneData = 0d;
		Double m1twoData = 0d;
		Double m1threeData = 0d;
		if (m1oneIndex != 0){
			m1oneData = m1rmseList.get(m1oneIndex);
		}
		if (m1twoIndex != 0){
			m1twoData = m1rmseList.get(m1twoIndex);
		}
		if (m1threeIndex != 0){
			m1threeData = m1rmseList.get(m1threeIndex);
		}
		int m0oneIndex = (int) (m0rmseList.size()*0.99);
		int m0twoIndex = (int) (m0rmseList.size()*0.95);
		int m0threeIndex = (int) (m0rmseList.size()*0.90);
		Double m0oneData = 0d;
		Double m0twoData = 0d;
		Double m0threeData = 0d;
		if (m0oneIndex != 0){
			m0oneData = m0rmseList.get(m0oneIndex);
		}
		if (m0twoIndex != 0){
			m0twoData = m0rmseList.get(m0twoIndex);
		}
		if (m0threeIndex != 0){
			m0threeData = m0rmseList.get(m0threeIndex);
		}
		Map<String, Object> mpici = new HashMap<String, Object>();
		
		mpici.put("prediction_id",genId);
		mpici.put("tableName",tableName);
		
		List<CommonPredict> paraList = commonPredictDao.resultById(mpici);
		
		int m1countOneData = 0;
		int m1countTwoData = 0;
		int m1countThreeData = 0;
		int m1countNomalData = 0;
		int m0countOneData = 0;
		int m0countTwoData = 0;
		int m0countThreeData = 0;
		int m0countNomalData = 0;
		String m0date1 = "";
		String m0date2 = "";
		String m0date3 = "";
		String m1date1 = "";
		String m1date2 = "";
		String m1date3 = "";
		
		for (int k = 0; k < paraList.size(); k++) {
			CommonPredict tempObj = paraList.get(k);
			String jsonStr = "";
			if(monitor_type.equalsIgnoreCase("R1")){
				jsonStr = tempObj.getR1_mid_result();
			} else if(monitor_type.equalsIgnoreCase("R2")){
				jsonStr = tempObj.getR2_mid_result();
			} else if(monitor_type.equalsIgnoreCase("R3")){
				jsonStr = tempObj.getR3_mid_result();
			}
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			if (!json.isEmpty()) {
				JSONObject m1Array = json.getJSONObject("m1");
				if (!m1Array.isEmpty()) {
					String m1rmse = m1Array.getString("rmse");
					if (m1rmse != null && !m1rmse.equals("")) {
						if (Double.parseDouble(m1rmse) >= m1oneData) {
							m1countOneData++;
							m1date1 += "," + tempObj.getDt();
						} else if (Double.parseDouble(m1rmse) < m1oneData
								&& Double.parseDouble(m1rmse) >= m1twoData) {
							m1countTwoData++;
							m1date2 += "," + tempObj.getDt();
						} else if (Double.parseDouble(m1rmse) < m1twoData
								&& Double.parseDouble(m1rmse) >= m1threeData) {
							m1countThreeData++;
							m1date3 += "," + tempObj.getDt();
						} else {
							m1countNomalData++;
						}
					}
				}

				JSONObject m0Array = json.getJSONObject("m0");
				if (!m0Array.isEmpty()) {
					String m0rmse = m0Array.getString("rmse");
					if (m0rmse != null && !m0rmse.equals("")) {
						if (Double.parseDouble(m0rmse) >= m0oneData) {
							m0countOneData++;
							m0date1 += "," + tempObj.getDt();
						} else if (Double.parseDouble(m0rmse) < m0oneData
								&& Double.parseDouble(m0rmse) >= m0twoData) {
							m0countTwoData++;
							m0date2 += "," + tempObj.getDt();
						} else if (Double.parseDouble(m0rmse) < m0twoData
								&& Double.parseDouble(m0rmse) >= m0threeData) {
							m0countThreeData++;
							m0date3 += "," + tempObj.getDt();
						} else {
							m0countNomalData++;
						}
					}
				}
			}
		}
		
		
		
		//指数拟合
		Map<String, Object> m1 = new HashMap<String, Object>();
			m1.put("oneData", m1oneData);
			m1.put("twoData", m1twoData);
			m1.put("threeData", m1threeData);
			m1.put("rmsePow", Math.sqrt(m1rmsePow));
			m1.put("countOneData", m1countOneData);
			m1.put("countTwoData", m1countTwoData);
			m1.put("countThreeData", m1countThreeData);
			m1.put("countNomalData", m1countNomalData);
		staticsMap.put("m1", m1);
		
		Map<String, Object> m1d1 = new HashMap<String, Object>();
		if(!m1date1.equals("")){
			m1d1.put("date1", m1date1.substring(1));
		}
			
//			m1d1.put("date2", m1date2.substring(1));
//			m1d1.put("date2", m1date3.substring(1));
		date1Map.put("m1", m1d1);
		
		Map<String, Object> m1d2 = new HashMap<String, Object>();
		if(!m1date2.equals("")){
			m1d2.put("date2", m1date2.substring(1));
		}
			
		date2Map.put("m1", m1d2);
		
		Map<String, Object> m1d3 = new HashMap<String, Object>();
		if(!m1date3.equals("")){
			m1d3.put("date3", m1date3.substring(1));
		}
		date2Map.put("m1", m1d3);
		
		//二次多项式
		Map<String, Object> m0 = new HashMap<String, Object>();
			m0.put("oneData", m0oneData);
			m0.put("twoData", m0twoData);
			m0.put("threeData", m0threeData);
			m0.put("rmsePow", Math.sqrt(m0rmsePow));
			m0.put("countOneData", m0countOneData);
			m0.put("countTwoData", m0countTwoData);
			m0.put("countThreeData", m0countThreeData);
			m0.put("countNomalData", m0countNomalData);
		staticsMap.put("m0", m0);	
		
		Map<String, Object> m0d1 = new HashMap<String, Object>();
		if(!m0date1.equals("")){
			m0d1.put("date1", m0date1.substring(1));
		}
//			m0d1.put("date2", m0date2.substring(1));
//			m0d1.put("date3", m0date3.substring(1));
		date1Map.put("m0", m0d1);
		
		Map<String, Object> m0d2 = new HashMap<String, Object>();
		if(!m0date2.equals("")){
			m0d2.put("date2", m0date2.substring(1));
		}
		date2Map.put("m0", m0d2);
		
		Map<String, Object> m0d3 = new HashMap<String, Object>();
		if(!m0date3.equals("")){
			m0d3.put("date3", m0date3.substring(1));
		}
		date3Map.put("m0", m0d3);
		
		//所有日期处理完
		//统计批次的相关信息
		
				
				
				JSONObject json = JSONObject.fromObject(staticsMap);
				String staticsData = json.toString();
				
				JSONObject json1 = JSONObject.fromObject(date1Map);
				String date1Str = json1.toString();
				
				JSONObject json2 = JSONObject.fromObject(date2Map);
				String date2Str = json2.toString();
				
				JSONObject json3 = JSONObject.fromObject(date3Map);
				String date3Str = json3.toString();
				
				Date today = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String handleTime = sf.format(today);
				
				map.clear();
				map.put("id", genId);
				map.put("dt", handleTime);
				map.put("pre_component", monitor_type);
				map.put("det_type",decType_Sel);
				map.put("dam_no", dam_Sel);
				map.put("obser_no", instr_Sel);
				map.put("startTime",startTime);
				map.put("endTime", endTime);
				map.put("staticsData", staticsData);//统计数据
				map.put("date1Str",date1Str);
				map.put("date2Str",date2Str);
				map.put("date3Str",date3Str);
//				
				single_predictionDao.add_singlepre(map);

		System.out.println("需要时间"+(System.currentTimeMillis() - st) / 1000);
				
				map.clear();
				
				JSONObject jsObj = new JSONObject();
				jsObj.put("preId", genId);
				response.setCharacterEncoding("utf-8");
				response.getWriter().write(jsObj.toString());
	}

	public ICommonPredictDao getCommonPredictDao() {
		return commonPredictDao;
	}

	public void setCommonPredictDao(ICommonPredictDao commonPredictDao) {
		this.commonPredictDao = commonPredictDao;
	}

	public ISINGLE_PREDICTIONDao getSingle_predictionDao() {
		return single_predictionDao;
	}

	public void setSingle_predictionDao(ISINGLE_PREDICTIONDao single_predictionDao) {
		this.single_predictionDao = single_predictionDao;
	}

	public IINS_SORTDao getIns_sortDao() {
		return ins_sortDao;
	}

	public void setIns_sortDao(IINS_SORTDao ins_sortDao) {
		this.ins_sortDao = ins_sortDao;
	}
	
}
