package util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import predict.fit.CalPredVal;
import predict.model.ExpModel;
import predict.model.PolynomialModel;
import BestInterp.BestInterp;
//import singlepre.Interp;
import Dao.ICommonPredictDao;
import Dao.ISIN_PRE_AUTODao;
import Entity.CommonPredict;
import Entity.PREDICT_DATA;
import Entity.PREDICT_DATA2;
import Entity.T_SIN_PRE;
import Entity.T_SIN_PRE_AUTO;
import autoModelSelect.autoModelSelect;
import expFitModel.expFitModel;//指数拟合
import poly2FitModel.poly2FitModel;//二次多项式

import com.mathworks.toolbox.javabuilder.MWArray;
import com.mathworks.toolbox.javabuilder.MWCellArray;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

import org.apache.commons.math3.filter.DefaultMeasurementModel;
import org.apache.commons.math3.filter.DefaultProcessModel;
import org.apache.commons.math3.filter.KalmanFilter;
import org.apache.commons.math3.filter.MeasurementModel;
import org.apache.commons.math3.filter.ProcessModel;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.log4j.Logger;

public class DataPredictUtil {
	
	/**
	 * 利用拟合方法进行预测
	 * @param commonPredictDao
	 * @param map
	 * @return
	 */
	/*public static Map<String, Object> predictMethod1(ICommonPredictDao commonPredictDao, Map<String, Object> map) {
		DataPreProcess dpp = new DataPreProcess();
		
		// 取得100条预处理之后的记录
		List<PREDICT_DATA> dataList = commonPredictDao.getDataByIdTm(map);
		int n = dataList.size();
		if (n <= 20)
			return null;					
//System.out.println("数据量：" + n);
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
		
		double rmse1_best ;
		if(args1 == null)
		{
			rmse1_best = Double.MAX_VALUE;
		}
		else {
			rmse1_best = args1.get(2);
		}
		// 得到各模型参数
		double rmse2_best = args2.get(3);

		// 得到真实值
		List<PREDICT_DATA> realData = commonPredictDao.getRealDataByIdTm(map);
		if (realData.isEmpty()) {
//			System.out.println("无真实值");
			return null;
		}
		Double wl = null;
		Double real = null;
		wl = realData.get(0).getWl().doubleValue();
		real = realData.get(0).getData().doubleValue();

		CalPredVal cpv = new CalPredVal();
		Double pred = null;
		Double rmse;
		// 比较
		// 指数拟合 比较优
		if (rmse1_best < rmse2_best) {
			pred = cpv.model1(args1.get(0), args1.get(1), wl) + offSet;
			rmse = rmse1_best;
		} else {
			pred = cpv.model2(args2.get(0), args2.get(1), args2.get(2), wl) + offSet;
			rmse = rmse2_best;
		}
//		System.out.println(pred);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("instr_no", map.get("instr_no"));
		resultMap.put("dt", map.get("dt"));
		resultMap.put("wl", wl);// 水位
		resultMap.put("realVal_" + map.get("monitor_type"), real);
		resultMap.put("preVal_" + map.get("monitor_type"), pred);
		resultMap.put("rmse_" + map.get("monitor_type"),rmse);

		//本身为异常值
		Integer status = commonPredictDao.getStatusByIdTm(map);
		if(status == null || status == 0) {
			resultMap.put("status_" + map.get("monitor_type"), 4);			
		} else {
			List<String> midList = commonPredictDao.getRsme(map);
			List<Double> rmseList = new ArrayList<Double>();
			List<Double> m0rmseList = new ArrayList<Double>();
			for (int i = 0; i < midList.size(); i++) {
				if (midList.get(i) != null && !midList.get(i).equals("")) {
				rmseList.add(Double.valueOf(midList.get(i)));
				}
			}
				// String jsonStr = temp.getDisplace_x_mid_result();
			Collections.sort(rmseList);
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
			
			
			Map<String, Object> staticsMap = new HashMap<String, Object>();
			
			Map<String, Object> m0 = new HashMap<String, Object>();
				m0.put("oneData", m0oneData);
				m0.put("twoData", m0twoData);
				m0.put("threeData", m0threeData);
			staticsMap.put("m0", m0);	
			
			
			JSONObject json = JSONObject.fromObject(staticsMap);
			String staticsData = json.toString();
			
//			Date today = new Date();
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String handleTime = sf.format(today);
			
			if (rmse >= m0oneData) {
				resultMap.put("status_" + map.get("monitor_type"), 1);
			} else if (rmse < m0oneData && rmse >= m0twoData) {
				resultMap.put("status_" + map.get("monitor_type"), 2);
			} else if (rmse < m0twoData && rmse >= m0threeData) {
				resultMap.put("status_" + map.get("monitor_type"), 3);
			} else {
				resultMap.put("status_" + map.get("monitor_type"), 5);
			}
			resultMap.put("staticsData", staticsData);//统计数据
		}
		
		return resultMap;
	}*/
	
	/**
	 * 调用matlab
	 * 利用拟合方法进行预测
	 * @param commonPredictDao
	 * @param map
	 * @return
	 * @throws SigarException 
	 */
	public static Map<String, Object> predictMethod1_1(ICommonPredictDao commonPredictDao, Map<String, Object> map,autoModelSelect anlys2) {
		
		// 取得100条预处理之后的记录
		List<PREDICT_DATA> dataList = commonPredictDao.getDataByIdTm(map);
		int n = dataList.size();
		if (n <= 20)
			return null;					
//System.out.println("数据量：" + n);
		double[] x = new double[n];
		double[] y = new double[n];
		
		for (int i = 0; i < dataList.size(); i++) {
			x[i] = dataList.get(i).getWl().doubleValue();
			y[i] = dataList.get(i).getData().doubleValue();
		}
	
		
		MWNumericArray ax = null; // 用于保存矩阵
		MWNumericArray ay = null; // 用于保存矩阵
		ax = new MWNumericArray(x,MWClassID.DOUBLE);
		ay = new MWNumericArray(y,MWClassID.DOUBLE);
		
		Object[] result = null; // 用于保存计算结果
		
//		autoModelSelect anlys2 = null;
		// 得到真实值
		List<PREDICT_DATA> realData = commonPredictDao.getRealDataByIdTm(map);
		if (realData.isEmpty()) {
//			System.out.println("无真实值");
			return null;
		}
		Double wl = null;
		Double real = null;
		wl = realData.get(0).getWl().doubleValue();
		real = realData.get(0).getData().doubleValue();
		try {
//			anlys2 = new autoModelSelect();
			//1 最优模型 2 rmse 3 步长 4 预测值 5 中间结果(json格式)
			result = anlys2.autoModelSelect(5,ax,ay,wl);
		} catch (MWException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			MWArray.disposeArray(ax);
			MWArray.disposeArray(ay);
//			anlys2.dispose();
		}
		
		Double rmse = Math.abs((real - Double.parseDouble(result[3].toString())) / real);
		for (int i = 0; i < result.length; i++) {
//			System.out.println(result[i].toString());
			
//			System.out.println("-------------");
		}
		
//		System.out.println(Double.parseDouble(result[1].toString()));
//		System.out.println(Integer.parseInt(result[2].toString()));
//		System.out.println(Double.parseDouble(result[3].toString()));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("instr_no", map.get("instr_no"));
//		resultMap.put("dt", map.get("dt"));
		resultMap.put("wl", wl);// 水位
		resultMap.put("realVal_" + map.get("monitor_type"), real);
		resultMap.put("preVal_" + map.get("monitor_type"), Double.parseDouble(result[3].toString()));
		resultMap.put("rmse_" + map.get("monitor_type"),rmse);

		//本身为异常值
		Integer status = commonPredictDao.getStatusByIdTm(map);
		if(status == null || status == 0) {
			resultMap.put("STATUS", 4);			
		} else {
			List<String> midList = commonPredictDao.getRsme(map);
			List<Double> rmseList = new ArrayList<Double>();
			List<Double> m0rmseList = new ArrayList<Double>();
			for (int i = 0; i < midList.size(); i++) {
				if (midList.get(i) != null && !midList.get(i).equals("")) {
				rmseList.add(Double.valueOf(midList.get(i)));
				}
			}
				// String jsonStr = temp.getDisplace_x_mid_result();
			Collections.sort(rmseList);
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
			
			
			Map<String, Object> staticsMap = new HashMap<String, Object>();
			
			Map<String, Object> m0 = new HashMap<String, Object>();
				m0.put("oneData", m0oneData);
				m0.put("twoData", m0twoData);
				m0.put("threeData", m0threeData);
			staticsMap.put("m0", m0);	
			
			
			JSONObject json = JSONObject.fromObject(staticsMap);
			String staticsData = json.toString();
			
//			Date today = new Date();
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String handleTime = sf.format(today);
			
			if (rmse >= m0oneData) {
				resultMap.put("status_" + map.get("monitor_type"), 1);
			} else if (rmse < m0oneData && rmse >= m0twoData) {
				resultMap.put("status_" + map.get("monitor_type"), 2);
			} else if (rmse < m0twoData && rmse >= m0threeData) {
				resultMap.put("status_" + map.get("monitor_type"), 3);
			} else {
				resultMap.put("status_" + map.get("monitor_type"), 5);
			}
			resultMap.put("staticsData", staticsData);//统计数据
		}
		

		return resultMap;
	}
	/*	
public static Map<String, Object> predictMethod1_2(ICommonPredictDao commonPredictDao, Map<String, Object> map,expFit anlys2) {
		
		// 取得100条预处理之后的记录
		List<PREDICT_DATA> dataList = commonPredictDao.getDataByIdTm(map);
		int n = dataList.size();
		if (n <= 20)
			return null;					
//System.out.println("数据量：" + n);
		double[] x = new double[n];
		double[] y = new double[n];
		
		for (int i = 0; i < dataList.size(); i++) {
			x[i] = dataList.get(i).getWl().doubleValue();
			y[i] = dataList.get(i).getData().doubleValue();
		}
	
		
		MWNumericArray ax = null; // 用于保存矩阵
		MWNumericArray ay = null; // 用于保存矩阵
		ax = new MWNumericArray(x,MWClassID.DOUBLE);
		ay = new MWNumericArray(y,MWClassID.DOUBLE);
		
		Object[] result = null; // 用于保存计算结果
		
//		autoModelSelect anlys2 = null;
		// 得到真实值
		List<PREDICT_DATA> realData = commonPredictDao.getRealDataByIdTm(map);
		if (realData.isEmpty()) {
//			System.out.println("无真实值");
			return null;
		}
		Double wl = null;
		Double real = null;
		wl = realData.get(0).getWl().doubleValue();
		real = realData.get(0).getData().doubleValue();
		
		try {
//			anlys2 = new autoModelSelect();
			//1 最优模型 2 rmse 3 步长 4 预测值 5 中间结果(json格式)
			result = anlys2.expFit(5,ax,ay,wl);//autoModelSelect(5,ax,ay,wl);
		} catch (MWException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			MWArray.disposeArray(ax);
			MWArray.disposeArray(ay);
//			anlys2.dispose();
		}
		
		Double rmse = Math.abs((real - Double.parseDouble(result[3].toString())) / real);
		for (int i = 0; i < result.length; i++) {
			System.out.println(result[i].toString());
			
			System.out.println("-------------");
		}
		
		System.out.println(Double.parseDouble(result[1].toString()));
		System.out.println(Integer.parseInt(result[2].toString()));
		System.out.println(Double.parseDouble(result[3].toString()));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("instr_no", map.get("instr_no"));
		resultMap.put("dt", map.get("dt"));
		resultMap.put("wl", wl);// 水位
		resultMap.put("realVal_" + map.get("monitor_type"), real);
		resultMap.put("preVal_" + map.get("monitor_type"), Double.parseDouble(result[3].toString()));
		resultMap.put("rmse_" + map.get("monitor_type"),rmse);

		//本身为异常值
		Integer status = commonPredictDao.getStatusByIdTm(map);
		if(status == null || status == 0) {
			resultMap.put("STATUS", 4);			
		} else {
			List<String> midList = commonPredictDao.getRsme(map);
			List<Double> rmseList = new ArrayList<Double>();
			List<Double> m0rmseList = new ArrayList<Double>();
			for (int i = 0; i < midList.size(); i++) {
				if (midList.get(i) != null && !midList.get(i).equals("")) {
				rmseList.add(Double.valueOf(midList.get(i)));
				}
			}
				// String jsonStr = temp.getDisplace_x_mid_result();
			Collections.sort(rmseList);
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
			
			
			Map<String, Object> staticsMap = new HashMap<String, Object>();
			
			Map<String, Object> m0 = new HashMap<String, Object>();
				m0.put("oneData", m0oneData);
				m0.put("twoData", m0twoData);
				m0.put("threeData", m0threeData);
			staticsMap.put("m0", m0);	
			
			
			JSONObject json = JSONObject.fromObject(staticsMap);
			String staticsData = json.toString();
			
//			Date today = new Date();
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String handleTime = sf.format(today);
			
			if (rmse >= m0oneData) {
				resultMap.put("status_" + map.get("monitor_type"), 1);
			} else if (rmse < m0oneData && rmse >= m0twoData) {
				resultMap.put("status_" + map.get("monitor_type"), 2);
			} else if (rmse < m0twoData && rmse >= m0threeData) {
				resultMap.put("status_" + map.get("monitor_type"), 3);
			} else {
				resultMap.put("status_" + map.get("monitor_type"), 5);
			}
			resultMap.put("staticsData", staticsData);//统计数据
		}
		return resultMap;
	}
	*/



	/**
	 * matlab利用差值方法进行预测
	 * @param commonPredictDao
	 * @param map
	 * @return
	 */
	@SuppressWarnings("null")
	public static Map<String, Object> predictMethod2(ICommonPredictDao commonPredictDao, Map<String, Object> map, BestInterp anlys2 ) throws Exception  {
		System.out.println("调用了predictMethod2");
		// 取得100条预处理之后的记录
		List<PREDICT_DATA2> dataList = commonPredictDao.getDataByIdTm4(map);
		int n = dataList.size();
		/*System.out.println("=================================");
		System.out.println("n = " + n);
		System.out.println("=================================");*/
		if (n <= 40)
			return null;					
//		System.out.println("数据量：" + n);
		String[] x = new String[n];
		double[] y = new double[n];
		
		for (int i = 0; i < dataList.size(); i++) {
			x[i] = TimeFormat.format1(dataList.get(i).getDt());
			y[i] = dataList.get(i).getData().doubleValue();
		}
		
//		System.out.println("--------line 483 -----------");
		
		MWNumericArray ay = null; // 用于保存矩阵
		ay = new MWNumericArray(y,MWClassID.DOUBLE);
			
		Object[] result = null; // 用于保存计算结果
		
//		System.out.println("======= line 490 ==========");
		
		try {
			result = anlys2.BestInterp(1,ay);
		} catch (MWException e) {
//			System.out.println("第446行（MATLAB数据处理错误）："+result[0]);
			e.printStackTrace();
		} finally {
			MWArray.disposeArray(ay);
		}
		
//		System.out.println("========= line 497 ============");
		
		MWCellArray msa = null;
		List<MWArray> ma = null;
		//保存模型参数
		List<Object> args = new ArrayList<Object>();
		
		for (int i = 0; i < result.length; i++) {
			msa = (MWCellArray) result[i];
			ma = msa.asList();
			//System.out.println(ma.size());
			for (MWArray mwArray : ma) {
				int[] index = mwArray.columnIndex();
				
				if(index.length == 1)
				{
					args.add(mwArray.get(1));
				}
				else
				{
					String str = "";
					for (int j = 0; j < index.length; j++) {
						str = str + mwArray.get(j+1);				
					}
					args.add(str);
				}
			}
//			System.out.println("-------------");
		}

		MWArray.disposeArray(msa);
		
//		System.out.println("=========line 527 =================");
		
		Double pred = null;
		int step = 0;
//		System.out.println("===== line 537 ===========");
		if(args.size() != 0)
		{
//			System.out.println("======== line 540 ==============");
			try {
//				System.out.println("====== line 542 ===========");
				pred = (Double) args.get(0);	
//				System.out.println("====== line 544 ===========");
//				Double reseve3=(Double) args.get(1);
//				step = reseve3.intValue();
//				System.out.println("======== 547 ============");
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				System.out.println("第511行（args数据读取错误）：pred:"+args.get(0)+";step:"+args.get(1));
				e.printStackTrace();
			}
		}
		
//		System.out.println("================== line 555  ============");
		
		// 得到真实值
		List<PREDICT_DATA> realData = commonPredictDao.getRealDataByIdTm(map);
		/*System.out.println(map.get("instr_no"));
		System.out.println(map.get("tableName"));
		System.out.println(map.get("dt"));
		System.out.println(map.get("monitor_type"));
		System.out.println("========= line 559 ==========" + realData == null);*/
		if (realData.isEmpty()) {
//			System.out.println("无真实值");
			return null;
		}
//		System.out.println("========= line 564 ==========");
		Double wl = null;
		Double real = null;
		wl = realData.get(0).getWl().doubleValue();
		real = realData.get(0).getData().doubleValue();
		
		Double rmse = null;
		try {
			rmse = Math.abs((real - pred)/ real);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			System.out.println("第534行（相对误差计算错误）：real："+real+";pred"+pred);
			e.printStackTrace();
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		/*resultMap.put("instr_no", map.get("instr_no"));
		resultMap.put("dt", map.get("dt"));
		resultMap.put("wl", wl);// 水位
		resultMap.put("realVal_" + map.get("monitor_type"), real);
		resultMap.put("preVal_" + map.get("monitor_type"), pred);*/
		
		resultMap.put("instr_no", map.get("instr_no"));
//		resultMap.put("dt", map.get("dt"));
		resultMap.put("wl", wl);// 水位
		resultMap.put("modelId", 2);
//		resultMap.put("prediction_id", 0);
		resultMap.put("realVal", real);
		resultMap.put("preVal", pred);
		resultMap.put("step", step);
		resultMap.put("err_rate",rmse);
		
		//本身为异常值
		Integer status = commonPredictDao.getStatusByIdTm(map);
		double prehandle = 0;
		try {
			prehandle = commonPredictDao.getPreDataByIdTm(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}

		resultMap.put("mark", status);
		resultMap.put("prehandle", prehandle);
		
		/*if(status == null || status == 0) {
			resultMap.put("status", 4);			
		}  else {
			List<String> midList = commonPredictDao.getRsme(map);
			List<Double> rmseList = new ArrayList<Double>();
			List<Double> m0rmseList = new ArrayList<Double>();
			for (int i = 0; i < midList.size(); i++) {
				if (midList.get(i) != null && !midList.get(i).equals("")) {
				rmseList.add(Double.valueOf(midList.get(i)));
				}
			}
				// String jsonStr = temp.getDisplace_x_mid_result();
			Collections.sort(rmseList);
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
			
			
			Map<String, Object> staticsMap = new HashMap<String, Object>();
			
			Map<String, Object> m0 = new HashMap<String, Object>();
				m0.put("oneData", m0oneData);
				m0.put("twoData", m0twoData);
				m0.put("threeData", m0threeData);
			staticsMap.put("m3", m0);	
			
			
			JSONObject json = JSONObject.fromObject(staticsMap);
			String staticsData = json.toString();
			
//			Date today = new Date();
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String handleTime = sf.format(today);
			
			if (rmse >= m0oneData) {
				resultMap.put("status", 1);
			} else if (rmse < m0oneData && rmse >= m0twoData) {
				resultMap.put("status", 2);
			} else if (rmse < m0twoData && rmse >= m0threeData) {
				resultMap.put("status", 3);
			} else {
				resultMap.put("status", 5);
			}
			resultMap.put("staticsData", staticsData);//统计数据
		}*/
		
		/*System.out.println("====================line 667=============================");
		System.out.println(resultMap == null);
		System.out.println(resultMap.keySet());
		System.out.println(resultMap.keySet().size());
		System.out.println("=======================line 671==========================");*/
		
		return resultMap;
	}
	
	
	/**
	 * 调用matlab
	 * 利用拟合方法进行预测
	 * @param commonPredictDao
	 * @param map
	 * @return
	 * @throws SigarException 
	 */
	public static Map<String, Object> predictMethod3(ICommonPredictDao commonPredictDao, Map<String, Object> map,expFitModel anlys2) throws Exception  {
		
		// 取得100条预处理之后的记录
		List<PREDICT_DATA> dataList = commonPredictDao.getDataByIdTm3(map);
		int n = dataList.size();
		if (n <= 20)
			return null;					
//System.out.println("数据量：" + n);
		double[] x = new double[n];
		double[] y = new double[n];
		
		for (int i = 0; i < dataList.size(); i++) {
			x[i] = dataList.get(i).getWl().doubleValue();
			y[i] = dataList.get(i).getData().doubleValue();
		}
	
		
		MWNumericArray ax = null; // 用于保存矩阵
		MWNumericArray ay = null; // 用于保存矩阵
		ax = new MWNumericArray(x,MWClassID.DOUBLE);
		ay = new MWNumericArray(y,MWClassID.DOUBLE);
		
		Object[] result = null; // 用于保存计算结果
		
//		autoModelSelect anlys2 = null;
		// 得到真实值
		List<PREDICT_DATA> realData = commonPredictDao.getRealDataByIdTm(map);
		if (realData.isEmpty()) {
//			System.out.println("无真实值");
			return null;
		}
		Double wl = null;
		Double real = null;
		wl = realData.get(0).getWl().doubleValue();
		real = realData.get(0).getData().doubleValue();
		try {
//			anlys2 = new autoModelSelect();
			//1步长  2   预测值
			result = anlys2.expFitModel(2,ax,ay,wl);//autoModelSelect(5,ax,ay,wl);
		} catch (MWException e) {
			// TODO Auto-generated catch block
//			System.out.println("第670行（MATLAB数据处理错误）：步长："+result[0]+";预测值："+result[1]);
			e.printStackTrace();
		} finally {
			MWArray.disposeArray(ax);
			MWArray.disposeArray(ay);
//			anlys2.dispose();
		}
		
		if(result[1].toString().equalsIgnoreCase("Inf"))
			return null;
		Double rmse = null;
		double pre = 0;
		try {
			pre = Double.parseDouble(result[1].toString());
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
//			System.out.println("第686行（预测值String转Double）：预测值："+result[1]);
			e1.printStackTrace();
		}
		try {
			rmse = Math.abs((real - pre)/ real);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
//			System.out.println("第693行（相对误差计算错误）：real："+real+";预测值："+result[1]);
			e.printStackTrace();
		}
		/*for (int i = 0; i < result.length; i++) {
			System.out.println(result[i].toString());
			
			System.out.println("-------------");
		}
		
		System.out.println(Double.parseDouble(result[1].toString()));
		System.out.println(Integer.parseInt(result[2].toString()));
		System.out.println(Double.parseDouble(result[3].toString()));*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("instr_no", map.get("instr_no"));
//		resultMap.put("dt", map.get("dt"));
		resultMap.put("wl", wl);// 水位
		resultMap.put("modelId", 3);
//		resultMap.put("prediction_id", 0);
		resultMap.put("realVal", real);
		resultMap.put("preVal", Double.parseDouble(result[1].toString()));
		resultMap.put("step", Integer.parseInt(result[0].toString()));
		resultMap.put("err_rate",rmse);

		//本身为异常值
		Integer status = commonPredictDao.getStatusByIdTm(map);
		double prehandle = 0;
		try {
			prehandle = commonPredictDao.getPreDataByIdTm(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
		resultMap.put("mark", status);
		resultMap.put("prehandle", prehandle);
		
		/*if(status == null || status == 0) {
			resultMap.put("status", 4);			
		} else {
			List<String> midList = commonPredictDao.getRsme(map);
			List<Double> rmseList = new ArrayList<Double>();
			List<Double> m0rmseList = new ArrayList<Double>();
			for (int i = 0; i < midList.size(); i++) {
				if (midList.get(i) != null && !midList.get(i).equals("")) {
				rmseList.add(Double.valueOf(midList.get(i)));
				}
			}
				// String jsonStr = temp.getDisplace_x_mid_result();
			Collections.sort(rmseList);
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
			
			
			Map<String, Object> staticsMap = new HashMap<String, Object>();
			
			Map<String, Object> m0 = new HashMap<String, Object>();
				m0.put("oneData", m0oneData);
				m0.put("twoData", m0twoData);
				m0.put("threeData", m0threeData);
			staticsMap.put("m3", m0);	
			
			
			JSONObject json = JSONObject.fromObject(staticsMap);
			String staticsData = json.toString();
			
//			Date today = new Date();
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String handleTime = sf.format(today);
			
			if (rmse >= m0oneData) {
				resultMap.put("status", 1);
			} else if (rmse < m0oneData && rmse >= m0twoData) {
				resultMap.put("status", 2);
			} else if (rmse < m0twoData && rmse >= m0threeData) {
				resultMap.put("status", 3);
			} else {
				resultMap.put("status", 5);
			}
			resultMap.put("staticsData", staticsData);//统计数据
		}*/
		
		return resultMap;
	}
	
	/**
	 * 调用matlab
	 * 利用二次多项式方法进行预测
	 * @param commonPredictDao
	 * @param map
	 * @return
	 * @throws SigarException 
	 */
	public static Map<String, Object> predictMethod4(ICommonPredictDao commonPredictDao, Map<String, Object> map,poly2FitModel anlys2) throws Exception  {
		
		// 取得100条预处理之后的记录
		List<PREDICT_DATA> dataList = commonPredictDao.getDataByIdTm3(map);
		int n = dataList.size();
		if (n <= 20)
			return null;					
//System.out.println("数据量：" + n);
		double[] x = new double[n];
		double[] y = new double[n];
		
		for (int i = 0; i < dataList.size(); i++) {
			x[i] = dataList.get(i).getWl().doubleValue();
			y[i] = dataList.get(i).getData().doubleValue();
		}
	
		
		MWNumericArray ax = null; // 用于保存矩阵
		MWNumericArray ay = null; // 用于保存矩阵
		ax = new MWNumericArray(x,MWClassID.DOUBLE);
		ay = new MWNumericArray(y,MWClassID.DOUBLE);
		
		Object[] result = null; // 用于保存计算结果
		
//		autoModelSelect anlys2 = null;
		// 得到真实值
		List<PREDICT_DATA> realData = commonPredictDao.getRealDataByIdTm(map);
		if (realData.isEmpty()) {
//			System.out.println("无真实值");
			return null;
		}
		Double wl = null;
		Double real = null;
		wl = realData.get(0).getWl().doubleValue();
		real = realData.get(0).getData().doubleValue();
		/*long cpus = 0L;
		long memus = 0L;*/
		try {
//			anlys2 = new autoModelSelect();
			//1步长   2  预测值
			result = anlys2.poly2FitModel(2,ax,ay,wl);
		} catch (MWException e) {
			// TODO Auto-generated catch block
//			System.out.println("第830行（MATLAB数据处理错误）：步长："+result[0]+";预测值："+result[1]);
			e.printStackTrace();
		} finally {
			MWArray.disposeArray(ax);
			MWArray.disposeArray(ay);
		}
		
		if(result[1].toString().equalsIgnoreCase("Inf"))
			return null;
		Double rmse = null;
		double pre = 0;
		try {
			pre = Double.parseDouble(result[1].toString());
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
//			System.out.println("第845行（预测值String转Double）：预测值："+result[1]);
			e1.printStackTrace();
		}
		try {
			rmse = Math.abs((real - pre)/ real);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
//			System.out.println("第852行（相对误差计算错误）：real："+real+";预测值："+result[1]);
			e.printStackTrace();
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("instr_no", map.get("instr_no"));
//		resultMap.put("dt", map.get("dt"));
		resultMap.put("wl", wl);// 水位
		resultMap.put("modelId", 4);
//		resultMap.put("prediction_id", 0);
		resultMap.put("realVal", real);
		resultMap.put("preVal", Double.parseDouble(result[1].toString()));
		resultMap.put("step", Integer.parseInt(result[0].toString()));
		resultMap.put("err_rate",rmse);

		//本身为异常值
		Integer status = commonPredictDao.getStatusByIdTm(map);
		double prehandle = 0;
		try {
			prehandle = commonPredictDao.getPreDataByIdTm(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
		resultMap.put("mark", status);
		resultMap.put("prehandle", prehandle);
		
		/*if(status == null || status == 0) {
			resultMap.put("status", 4);			
		} else {
			List<String> midList = commonPredictDao.getRsme(map);
			List<Double> rmseList = new ArrayList<Double>();
			List<Double> m0rmseList = new ArrayList<Double>();
			for (int i = 0; i < midList.size(); i++) {
				if (midList.get(i) != null && !midList.get(i).equals("")) {
				rmseList.add(Double.valueOf(midList.get(i)));
				}
			}
				// String jsonStr = temp.getDisplace_x_mid_result();
			Collections.sort(rmseList);
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
			
			
			Map<String, Object> staticsMap = new HashMap<String, Object>();
			
			Map<String, Object> m0 = new HashMap<String, Object>();
				m0.put("oneData", m0oneData);
				m0.put("twoData", m0twoData);
				m0.put("threeData", m0threeData);
			staticsMap.put("m0", m0);	
			
			
			JSONObject json = JSONObject.fromObject(staticsMap);
			String staticsData = json.toString();
			
//			Date today = new Date();
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String handleTime = sf.format(today);
			
			if (rmse >= m0oneData) {
				resultMap.put("status_" + map.get("monitor_type"), 1);
			} else if (rmse < m0oneData && rmse >= m0twoData) {
				resultMap.put("status_" + map.get("monitor_type"), 2);
			} else if (rmse < m0twoData && rmse >= m0threeData) {
				resultMap.put("status_" + map.get("monitor_type"), 3);
			} else {
				resultMap.put("status_" + map.get("monitor_type"), 5);
			}
			resultMap.put("staticsData", staticsData);//统计数据
		}
		
		resultMap.put("cpu", cpus);
		resultMap.put("mem", memus);*/

		return resultMap;
	}
	
	
	
	/**
	 * 调用matlab
	 * 利用卡尔曼进行预测
	 * @param commonPredictDao
	 * @param map
	 * @return
	 * @throws SigarException 
	 */
	public static void predictMethod5(
			ICommonPredictDao commonPredictDao,ISIN_PRE_AUTODao sin_preAutoDao, Map<String, Object> map) throws Exception {
		String cDate = (String) map.get("dt");
		String addDate = (String) map.get("dtAdd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(cDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.get(Calendar.DAY_OF_MONTH) - 1);// 让日期减1
		String date1 = sdf.format(calendar.getTime());
		
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.get(Calendar.DAY_OF_MONTH) - 1);// 让日期加1
		String date2 = sdf.format(calendar.getTime());
		
		List<PREDICT_DATA2> cRealData = commonPredictDao.getWL(map);
		
		Double cWl = null;
//		Float cRealR1 = null;
//		Float cRealR2 = null;
		cWl = cRealData.get(0).getData().doubleValue();
//		cRealR1 = cRealData.get(0).getR1();
//		cRealR2 = cRealData.get(0).getR2();
		
		map.put("dt",date1);
		List<T_SIN_PRE_AUTO> r1D1 = sin_preAutoDao.getR1(map);
		List<T_SIN_PRE_AUTO> r2D1 = sin_preAutoDao.getR2(map);
		
		List<CommonPredict> realData1 = commonPredictDao.getRealAllData(map);
		Double wl1 = null;
		Double inputR1D1 = null;
		Double inputR2D1 = null;
		T_SIN_PRE tmp1 = null;
		List<T_SIN_PRE> preData1 = sin_preAutoDao.get2Pre(map);
		if(preData1.isEmpty()){
			inputR1D1 = commonPredictDao.getRealR1(map);
			if (inputR1D1 == null) inputR1D1 = 0.0;
			inputR2D1 = commonPredictDao.getRealR2(map);
			if (inputR2D1 == null) inputR2D1 = 0.0;
		} else {
		tmp1 = preData1.get(0);
		if(tmp1.getPreVal_r1() == null){
			inputR1D1 = tmp1.getPrehandle_r1().doubleValue();
		} else {
			inputR1D1 = tmp1.getPreVal_r1().doubleValue();
		}
		if(tmp1.getPreVal_r2() == null){
			inputR2D1 = tmp1.getPrehandle_r2().doubleValue();
		} else {
			inputR2D1 = tmp1.getPreVal_r2().doubleValue();
		}}
		if(realData1.isEmpty()){
			List<PREDICT_DATA2> cData1 = commonPredictDao.getWL(map);
			wl1 = cData1.get(0).getData().doubleValue();
			double wlD2 = Math.abs(wl1 - cWl);
			
			
			
			double[][] a = new double[][] { { 1, 0 }, { 0, 1 } };
			RealMatrix stateMatrix = new Array2DRowRealMatrix(a);
			// create a controlMatrix input matrix: B
			double[][] b = new double[][] { { 0.84 }, { 0.008 } };
			RealMatrix controlMatrix = new Array2DRowRealMatrix(b);
			// create a process noise covariance matrix: Q
			double[][] q = new double[][] { { 0.0113, 0 }, { 0, 0.2656 } };
			RealMatrix processNoise = new Array2DRowRealMatrix(q);
			// create an initial state estimate vector: mu0 {R1,R2}
			double[] m2 = new double[] { inputR1D1, inputR2D1 };
			RealVector initialStateEstimate = MatrixUtils.createRealVector(m2);
			// create an initial error covariance matrix: P0
			double[][] p0 = new double[][] { { 1, 0 }, { 0, 100 } };
			RealMatrix initialErrorCovariance = new Array2DRowRealMatrix(p0);
			ProcessModel process = new DefaultProcessModel(stateMatrix,
					controlMatrix, processNoise, initialStateEstimate,
					initialErrorCovariance);
			
			double[][] h = new double[][] { { 1, 0 }, { 0, 1 } };
			RealMatrix measMatrix = new Array2DRowRealMatrix(h);

			// create a measurement noise covariance matrix: R
			double[][] r = new double[][] { { 0.0375, 0 }, { 0, 0.2210 } };
			RealMatrix measNoise = MatrixUtils.createRealMatrix(r);
			MeasurementModel measurement = new DefaultMeasurementModel(measMatrix,
					measNoise);
			
			/**
			 * 创建卡尔曼滤波
			 */
			KalmanFilter kalmanFilter = new KalmanFilter(process, measurement);
			
			RealVector u = new ArrayRealVector(new double[] { wlD2 });// 先验水位变化
			kalmanFilter.predict(u);
			initialStateEstimate = stateMatrix.operate(initialStateEstimate).add(
					controlMatrix.operate(u));
			System.out.println(kalmanFilter.getStateEstimationVector());// R1,R2(先验)
			
			double[] beforeV = kalmanFilter.getStateEstimationVector().toArray();
			
			map.put("dt",cDate);
			
			map.put("monitor_type","r1");
			List<PREDICT_DATA> realDataR1 = commonPredictDao.getRealDataByIdTm(map);
			if (realDataR1.isEmpty()) {
			} else {
			Double realR1 = null;
			realR1 = realDataR1.get(0).getData().doubleValue();
			
			Integer status = commonPredictDao.getStatusByIdTm(map);
			Double prehandle = commonPredictDao.getPreDataByIdTm(map);
			
			map.put("mark", status);
			map.put("prehandle", prehandle);
				map.put("preVal",beforeV[0]);
				double errR1 = Math.abs((realR1 - beforeV[0])/ realR1);
				map.put("err_rate",errR1);
					map.put("component","r1");
					map.put("realVal",realR1);
					map.put("wl",cWl);
					map.put("dt", addDate);
					try {
						sin_preAutoDao.add_res(map);
					} catch (Exception e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
					}
			}
			map.put("monitor_type","r2");
			List<PREDICT_DATA> realDataR2 = commonPredictDao.getRealDataByIdTm(map);
			if (realDataR2.isEmpty()) {
			} else {
			Double realR2 = null;
			realR2 = realDataR2.get(0).getData().doubleValue();
			Integer status = commonPredictDao.getStatusByIdTm(map);
			Double prehandle = commonPredictDao.getPreDataByIdTm(map);
					
					map.put("mark", status);
					map.put("prehandle", prehandle);
				map.put("preVal",beforeV[1]);
				double errR2 = Math.abs((realR2 - beforeV[1])/ realR2);
				map.put("err_rate",errR2);
					map.put("component","r2");
					map.put("realVal",realR2);
					map.put("wl",cWl);
					map.put("dt", addDate);
					try {
						sin_preAutoDao.add_res(map);
					} catch (Exception e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
					}
			}
		} else {
			Float real1R1 = null;
			Float real1R2 = null;
			wl1 = realData1.get(0).getWl().doubleValue();
			real1R1 = realData1.get(0).getR1();
			real1R2 = realData1.get(0).getR2();
			double wlD2 = Math.abs(wl1 - cWl);
			
			map.put("dt",date2);
			List<T_SIN_PRE> preData2 = sin_preAutoDao.get2Pre(map);
			
//			List<CommonPredict> realData2 = commonPredictDao.getRealAllData(map);
			List<PREDICT_DATA2> realData2 = commonPredictDao.getWL(map);
			Double wl2 = null;
			wl2 = realData2.get(0).getData().doubleValue();
//			Float real2R1 = null;
//			Float real2R2 = null;
//			wl1 = realData2.get(0).getWl().doubleValue();
//			real2R1 = realData2.get(0).getR1();
//			real2R2 = realData2.get(0).getR2();
			
//			Double inputR1D2 = null;
//			Double inputR2D2 = null;
			/*if(preData2.isEmpty()){
				inputR1D2 = real2R1.doubleValue();
				inputR2D2 = real2R2.doubleValue();
			} else {*/
			/*T_SIN_PRE tmp2 = preData2.get(0);
			
			if(tmp2.getPreVal_r1() == null){
				inputR1D2 = tmp2.getPrehandle_r1().doubleValue();
			} else {
				inputR1D2 = tmp2.getPreVal_r1().doubleValue();
			}
			if(tmp2.getPreVal_r2() == null){
				inputR2D2 = tmp2.getPrehandle_r2().doubleValue();
			} else {
				inputR2D2 = tmp2.getPreVal_r2().doubleValue();
			}*/
//			}
			double wlD1 = Math.abs(wl1 - wl2);
			
			
			
			// create a state transition matrix: A		
			double[][] a = new double[][] { { 1, 0 }, { 0, 1 } };
			RealMatrix stateMatrix = new Array2DRowRealMatrix(a);
			// create a controlMatrix input matrix: B
			double[][] b = new double[][] { { 0.84 }, { 0.008 } };
			RealMatrix controlMatrix = new Array2DRowRealMatrix(b);
			// create a process noise covariance matrix: Q
			double[][] q = new double[][] { { 0.0113, 0 }, { 0, 0.2656 } };
			RealMatrix processNoise = new Array2DRowRealMatrix(q);
			// create an initial state estimate vector: mu0 {R1,R2}
			double[] m2 = new double[] { inputR1D1, inputR2D1 };
			RealVector initialStateEstimate = MatrixUtils.createRealVector(m2);
			// create an initial error covariance matrix: P0
			double[][] p0 = new double[][] { { 1, 0 }, { 0, 100 } };
			RealMatrix initialErrorCovariance = new Array2DRowRealMatrix(p0);
			ProcessModel process = new DefaultProcessModel(stateMatrix,
					controlMatrix, processNoise, initialStateEstimate,
					initialErrorCovariance);

			/**
			 * 创建一个观测模型
			 */
			// create a measurement matrix: H
			double[][] h = new double[][] { { 1, 0 }, { 0, 1 } };
			RealMatrix measMatrix = new Array2DRowRealMatrix(h);

			// create a measurement noise covariance matrix: R
			double[][] r = new double[][] { { 0.0375, 0 }, { 0, 0.2210 } };
			RealMatrix measNoise = MatrixUtils.createRealMatrix(r);
			MeasurementModel measurement = new DefaultMeasurementModel(measMatrix,
					measNoise);
			/**
			 * 创建卡尔曼滤波
			 */
			KalmanFilter kalmanFilter = new KalmanFilter(process, measurement);

			RealVector u = new ArrayRealVector(new double[] { wlD1 });// 后验水位变化
			
			/**
			 * 修改更新校验y
			 * @author qihai
			 */
			double er1 = Math.abs((inputR1D1 - real1R1) / real1R1);
			double er2 = Math.abs((inputR2D1 - real1R2) / real1R2);
			if(er1>0.2){
				Map<String, Object> maper1 = new HashMap<String, Object>();
				maper1.put("tablename", map.get("tableName"));
				maper1.put("instr_no", map.get("instr_no"));
				maper1.put("dt", date1);
				maper1.put("component", "r1");
				if(commonPredictDao.getAvgValue7(maper1)!=null)
					real1R1 = commonPredictDao.getAvgValue7(maper1);
			}
			if(er2>0.2){
				Map<String, Object> maper2 = new HashMap<String, Object>();
				maper2.put("tablename", map.get("tableName"));
				maper2.put("instr_no", map.get("instr_no"));
				maper2.put("dt", date1);
				maper2.put("component", "r2");
				if(commonPredictDao.getAvgValue7(maper2)!=null)
					real1R2 = commonPredictDao.getAvgValue7(maper2);
			}
			
			double[] y = new double[] { real1R1, real1R2 };
			kalmanFilter.correct(y);

			System.out.println(kalmanFilter.getStateEstimationVector());// R1,R2(后验)
			System.out.println("-------------------");

			double[] afterV = kalmanFilter.getStateEstimationVector().toArray();
//			map.put("preVal",afterV[0]);
			
//			for(int k=0 ; k < 2 ; k++){
			map.put("dt",cDate);	
			map.put("preVal",afterV[0]);
				double err1 = Math.abs((real1R1 - afterV[0])/ real1R1);
				map.put("err_rate",err1);
				if (r1D1 == null || r1D1.size() == 0){
					if(tmp1!=null){
						map.put("prehandle",tmp1.getPrehandle_r1());
						map.put("mark",tmp1.getMark_r1());
					} else {
						Integer status = commonPredictDao.getStatusByIdTm(map);
						Double prehandle = commonPredictDao.getPreDataByIdTm(map);
								
								map.put("mark", status);
								map.put("prehandle", prehandle);
					}
					map.put("component","r1");
					map.put("realVal",real1R1);
					map.put("wl",wl1);
					
					try {
						sin_preAutoDao.add_res(map);
					} catch (Exception e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
					}
				} else {
					try {
						sin_preAutoDao.updPre_r1(map);
					} catch (Exception e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
					}
				}
				
				map.put("preVal",afterV[1]);
				double err2 = Math.abs((real1R1 - afterV[0])/ real1R1);
				map.put("err_rate",err2);
				if (r2D1 == null || r2D1.size() == 0){
					if(tmp1!=null){
						map.put("prehandle",tmp1.getPrehandle_r2());
						map.put("mark",tmp1.getMark_r2());
					} else {
						Integer status = commonPredictDao.getStatusByIdTm(map);
						Double prehandle = commonPredictDao.getPreDataByIdTm(map);
								
								map.put("mark", status);
								map.put("prehandle", prehandle);
					}
					map.put("component","r2");
					map.put("realVal",real1R2);
					map.put("wl",wl1);
					try {
						sin_preAutoDao.add_res(map);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						sin_preAutoDao.updPre_r2(map);
					} catch (Exception e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
					}
				}
//			}
			
			
			/**
			 * 使用卡尔曼滤波预测
			 */
			
				
				double[] m1 = new double[] { afterV[0], afterV[1] };
				initialStateEstimate = MatrixUtils.createRealVector(m1);
				process = new DefaultProcessModel(stateMatrix,
						controlMatrix, processNoise, initialStateEstimate,
						initialErrorCovariance);
//			System.out.println(kalmanFilter.getStateEstimationVector());// R1,R2
			u = new ArrayRealVector(new double[] { wlD2 });// 先验水位变化
			kalmanFilter.predict(u);
			initialStateEstimate = stateMatrix.operate(initialStateEstimate).add(
					controlMatrix.operate(u));
			System.out.println(kalmanFilter.getStateEstimationVector());// R1,R2(先验)
			
			double[] beforeV = kalmanFilter.getStateEstimationVector().toArray();
//			map.put("preVal",afterV[0]);
			
//			for(int k=0 ; k < 2 ; k++){
			map.put("dt",cDate);
			
			map.put("monitor_type","r1");
			List<PREDICT_DATA> realDataR1 = commonPredictDao.getRealDataByIdTm(map);
			if (realDataR1.isEmpty()) {
			} else {
			Double realR1 = null;
			realR1 = realDataR1.get(0).getData().doubleValue();
			
			Integer status = commonPredictDao.getStatusByIdTm(map);
			Double prehandle = commonPredictDao.getPreDataByIdTm(map);
			
			map.put("mark", status);
			map.put("prehandle", prehandle);
				map.put("preVal",beforeV[0]);
				double errR1 = Math.abs((real1R1 - beforeV[0])/ real1R1);
				map.put("err_rate",errR1);
					map.put("component","r1");
					map.put("realVal",realR1);
					map.put("wl",cWl);
					map.put("dt", addDate);
					try {
						sin_preAutoDao.add_res(map);
					} catch (Exception e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
					}
			}
			map.put("monitor_type","r2");
			List<PREDICT_DATA> realDataR2 = commonPredictDao.getRealDataByIdTm(map);
			if (realDataR2.isEmpty()) {
			} else {
			Double realR2 = null;
			realR2 = realDataR2.get(0).getData().doubleValue();
			Integer status = commonPredictDao.getStatusByIdTm(map);
			Double prehandle = commonPredictDao.getPreDataByIdTm(map);
					
					map.put("mark", status);
					map.put("prehandle", prehandle);
				map.put("preVal",beforeV[1]);
				double errR2 = Math.abs((real1R1 - beforeV[1])/ real1R1);
				map.put("err_rate",errR2);
					map.put("component","r2");
					map.put("realVal",realR2);
					map.put("wl",cWl);
					map.put("dt", addDate);
					try {
						sin_preAutoDao.add_res(map);
					} catch (Exception e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
					}
			}

		}
	}
	
}
