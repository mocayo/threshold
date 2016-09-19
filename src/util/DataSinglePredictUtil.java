package util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import poly2FitModel.poly2FitModel;//二次多项式
import BestInterp.BestInterp;
import Dao.ICommonPredictDao;
import Entity.CommonPredict;
import Entity.PREDICT_DATA;
import Entity.PREDICT_DATA2;

import com.mathworks.toolbox.javabuilder.MWArray;
import com.mathworks.toolbox.javabuilder.MWCellArray;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

import expFitModel.expFitModel;//指数拟合

public class DataSinglePredictUtil {
	
	/**
	 * matlab利用差值方法进行预测
	 * @param commonPredictDao
	 * @param map
	 * @return
	 */
	@SuppressWarnings("null")
	public static Map<String, Object> predictMethod2(ICommonPredictDao commonPredictDao, Map<String, Object> map, BestInterp anlys2 ) {
		
		// 取得100条预处理之后的记录
		List<PREDICT_DATA2> dataList = commonPredictDao.getDataByIdTm4(map);
		int n = dataList.size();
		if (n <= 40)
			return null;					
//		System.out.println("数据量：" + n);
		String[] x = new String[n];
		double[] y = new double[n];
		
		for (int i = 0; i < dataList.size(); i++) {
			x[i] = TimeFormat.format1(dataList.get(i).getDt());
			y[i] = dataList.get(i).getData().doubleValue();
		}
		
		MWNumericArray ay = null; // 用于保存矩阵
		ay = new MWNumericArray(y,MWClassID.DOUBLE);
			
		Object[] result = null; // 用于保存计算结果
		
		try {
			result = anlys2.BestInterp(1,ay);
		} catch (MWException e) {
			System.out.println("第446行（MATLAB数据处理错误）："+result[0]);
			e.printStackTrace();
		} finally {
			MWArray.disposeArray(ay);
		}
		
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
		
		
		Double pred = null;
		int step = 0;
		if(args.size() != 0)
		{
			try {
				pred = (Double) args.get(0);	
				/*Double reseve3=(Double) args.get(1);
				step = reseve3.intValue();*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("第511行（args数据读取错误）：pred:"+args.get(0)+";step:"+args.get(1));
				e.printStackTrace();
			}
		}
		
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
		
		Double rmse = null;
		try {
			rmse = Math.abs((real - pred)/ real);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("第534行（相对误差计算错误）：real："+real+";pred"+pred);
			e.printStackTrace();
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		/*resultMap.put("instr_no", map.get("instr_no"));
		resultMap.put("dt", map.get("dt"));
		resultMap.put("wl", wl);// 水位
		resultMap.put("realVal_" + map.get("monitor_type"), real);
		resultMap.put("preVal_" + map.get("monitor_type"), pred);*/
		
		resultMap.put("instr_no", map.get("instr_no"));
		resultMap.put("dt", map.get("dt"));
		resultMap.put("wl", wl);// 水位
		resultMap.put("modelId", 2);
//		resultMap.put("prediction_id", 0);
		resultMap.put("realVal", real);
		resultMap.put("preVal", pred);
		resultMap.put("step", step);
		resultMap.put("err_rate",rmse);
		
		//本身为异常值
		Integer status = commonPredictDao.getStatusByIdTm(map);
		double prehandle = commonPredictDao.getPreDataByIdTm(map);

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
	public static Map<String, Object> predictMethod3(ICommonPredictDao commonPredictDao, Map<String, Object> map,expFitModel anlys2) {
		
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
		long cpus = 0L;
		long memus = 0L;
		try {
//			anlys2 = new autoModelSelect();
			//1步长  2   预测值
			result = anlys2.expFitModel(2,ax,ay,wl);//autoModelSelect(5,ax,ay,wl);
		} catch (MWException e) {
			// TODO Auto-generated catch block
			System.out.println("第670行（MATLAB数据处理错误）：步长："+result[0]+";预测值："+result[1]);
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
			System.out.println("第686行（预测值String转Double）：预测值："+result[1]);
			e1.printStackTrace();
		}
		try {
			rmse = Math.abs((real - pre)/ real);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("第693行（相对误差计算错误）：real："+real+";预测值："+result[1]);
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
		resultMap.put("dt", map.get("dt"));
		resultMap.put("wl", wl);// 水位
		resultMap.put("modelId", 3);
//		resultMap.put("prediction_id", 0);
		resultMap.put("realVal", real);
		resultMap.put("preVal", Double.parseDouble(result[1].toString()));
		resultMap.put("step", Integer.parseInt(result[0].toString()));
		resultMap.put("err_rate",rmse);

		//本身为异常值
		Integer status = commonPredictDao.getStatusByIdTm(map);
		Double prehandle = commonPredictDao.getPreDataByIdTm(map);
		
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
		}
		
		//--------------------------
		//预测信息
		Map<String, Object> mpici = new HashMap<String, Object>();
		mpici.put("prediction_id",map.get("pre_id"));
		mpici.put("tableName",map.get("tableName"));
		mpici.put("instr_no",map.get("instr_no"));
		//--------------------------
*/		
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
	public static Map<String, Object> predictMethod4(ICommonPredictDao commonPredictDao, Map<String, Object> map,poly2FitModel anlys2) {
		
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
		long cpus = 0L;
		long memus = 0L;
		try {
//			anlys2 = new autoModelSelect();
			//1步长   2  预测值
			result = anlys2.poly2FitModel(2,ax,ay,wl);
		} catch (MWException e) {
			// TODO Auto-generated catch block
			System.out.println("第830行（MATLAB数据处理错误）：步长："+result[0]+";预测值："+result[1]);
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
			System.out.println("第845行（预测值String转Double）：预测值："+result[1]);
			e1.printStackTrace();
		}
		try {
			rmse = Math.abs((real - pre)/ real);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("第852行（相对误差计算错误）：real："+real+";预测值："+result[1]);
			e.printStackTrace();
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("instr_no", map.get("instr_no"));
		resultMap.put("dt", map.get("dt"));
		resultMap.put("wl", wl);// 水位
		resultMap.put("modelId", 4);
//		resultMap.put("prediction_id", 0);
		resultMap.put("realVal", real);
		resultMap.put("preVal", Double.parseDouble(result[1].toString()));
		resultMap.put("step", Integer.parseInt(result[0].toString()));
		resultMap.put("err_rate",rmse);

		//本身为异常值
		Integer status = commonPredictDao.getStatusByIdTm(map);
		Double prehandle = commonPredictDao.getPreDataByIdTm(map);
		
		resultMap.put("mark", status);
		resultMap.put("prehandle", prehandle);
		
	/*	if(status == null || status == 0) {
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
		}*/
		

		return resultMap;
	}
	
	
}
