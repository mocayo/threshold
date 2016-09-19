package util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import jxl.read.biff.BiffException;
import BestInterp.BestInterp;
import expFitModel.expFitModel;//指数拟合
import poly2FitModel.poly2FitModel;//二次多项式
//import singlepre.Interp;










import com.mathworks.toolbox.javabuilder.MWException;

import autoModelSelect.autoModelSelect;
import Dao.ICommonPreHandleDao;
import Dao.ICommonPredictDao;
import Dao.IDEFINSSORTDao;
import Dao.IDataPredictedDao;
import Dao.ILstzzParasDao;
import Dao.IMidCharacteristicDao;
import Dao.IModelPointDao;
import Dao.IMonitorItemTypeDao;
import Dao.ISIN_PRE_AUTODao;
import Entity.DEFINSSORT;
import Entity.LstzzParas;
import Entity.MidCharacteristic;
import Entity.ModelPoint;
import Entity.MonitorItemType;
import Entity.T_SIN_PRE_AUTO;

public class DataPredictAutomatic_bak extends Action {

//	private String genId;
	private IMonitorItemTypeDao monitorItemTypeDao;
	private IDEFINSSORTDao definssortDao;
	private ICommonPredictDao commonPredictDao;
	private ISIN_PRE_AUTODao sin_preAutoDao;
	private IDataPredictedDao dataPredictedDao;
	private ICommonPreHandleDao commonPreHandleDao;
	private IModelPointDao modelPointDao;
	private IMidCharacteristicDao midCharacterDao;
	private ILstzzParasDao lstzzParasDao;

	public static BestInterp anlys2;
	public static autoModelSelect autoModelSelect;
	public static expFitModel expFit;
	public static poly2FitModel poly2Fit;

	public void execute() throws IOException {
		
		jobTimer();
	}

	public DataPredictAutomatic_bak(IMonitorItemTypeDao monitorItemTypeDao,
			IDEFINSSORTDao definssortDao, ICommonPredictDao commonPredictDao,
			ISIN_PRE_AUTODao sin_preAutoDao,
			IDataPredictedDao dataPredictedDao,
			ICommonPreHandleDao commonPreHandleDao, IModelPointDao modelPointDao) {
		this.monitorItemTypeDao = monitorItemTypeDao;
		this.definssortDao = definssortDao;
		this.commonPredictDao = commonPredictDao;
		this.sin_preAutoDao = sin_preAutoDao;
		this.dataPredictedDao = dataPredictedDao;
		this.commonPreHandleDao = commonPreHandleDao;
		this.modelPointDao = modelPointDao;
	}

	public void jobTimer() {
		System.out.println("定时器启动");
		long st = System.nanoTime() / 1000000L;

//		genId = new IdGenerator().generateId();

		Map<String, Object> map = new HashMap<String, Object>();

		 Date today = new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 String dt = sdf.format(today);

		/* try {
				PreHandleAutomatic pre = new PreHandleAutomatic(commonPreHandleDao,
						monitorItemTypeDao, modelPointDao);
				pre.preHandleAll(dt);
			} catch (Exception e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		 
		try {
			anlys2 = new BestInterp();
			expFit = new expFitModel();
			poly2Fit = new poly2FitModel();
			// autoModelSelect = new autoModelSelect();
		} catch (MWException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> tableNameList = new ArrayList<String>();
		List<String> topList = monitorItemTypeDao.getTableNames();// new
		tableNameList.addAll(topList);
//		String str = "2012-5-30";

			 for (String tableName : tableNameList) {
			// 根据表名得到所有测点
			List<ModelPoint> iswhereList = modelPointDao
					.getCaculatePoints(tableName);
			
			int reCaculateStr = 0;
			for (int i = 0; i < iswhereList.size(); i++) {
				try {
					String id = iswhereList.get(i).getInstr_no().trim();
					String msR1Str = iswhereList.get(i).getR1().trim();
					String msR2Str = iswhereList.get(i).getR2().trim();
					String msR3Str = iswhereList.get(i).getR3().trim();
					String[] msR1 = msR1Str.split(",");
					String[] msR2 = msR2Str.split(",");
					String[] msR3 = msR3Str.split(",");
					if (msR1Str.equals("") && msR2Str.equals("")
							&& msR3Str.equals("")) {
						method2(map, dt, tableName, id, anlys2, expFit, poly2Fit,
								"r1,r2,r3");
						method3(map, dt, tableName, id, anlys2, expFit, poly2Fit,
								"r1,r2,r3");
						method4(map, dt, tableName, id, anlys2, expFit, poly2Fit,
								"r1,r2,r3");
					} else {
						for (int j = 0; j < msR1.length; j++) {
							String methodName = msR1[j].trim();
							if (!methodName.equals("")) {
								try {
									Method m = DataPredictAutomatic_bak.class
											.getDeclaredMethod(methodName,
													Map.class, String.class,
													String.class, String.class,
													BestInterp.class,
													expFitModel.class,
													poly2FitModel.class,
													String.class);
									m.invoke(new DataPredictAutomatic_bak(
											monitorItemTypeDao, definssortDao,
											commonPredictDao, sin_preAutoDao,
											dataPredictedDao, commonPreHandleDao,
											modelPointDao), map, dt, tableName, id,
											anlys2, expFit, poly2Fit, "r1");
								} catch (NoSuchMethodException e) {
									// TODO Auto-generated catch block
									// call
									e.printStackTrace();
								} catch (SecurityException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IllegalArgumentException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						for (int j = 0; j < msR2.length; j++) {
							String methodName = msR2[j].trim();
							if (!methodName.equals("")) {
								try {
									Method m = DataPredictAutomatic_bak.class
											.getDeclaredMethod(methodName,
													Map.class, String.class,
													String.class, String.class,
													BestInterp.class,
													expFitModel.class,
													poly2FitModel.class,
													String.class);
									m.invoke(new DataPredictAutomatic_bak(
											monitorItemTypeDao, definssortDao,
											commonPredictDao, sin_preAutoDao,
											dataPredictedDao, commonPreHandleDao,
											modelPointDao), map, dt, tableName, id,
											anlys2, expFit, poly2Fit, "r2");
								} catch (NoSuchMethodException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (SecurityException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IllegalArgumentException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						for (int j = 0; j < msR3.length; j++) {
							String methodName = msR3[j].trim();
							if (!methodName.equals("")) {
								try {
									Method m = DataPredictAutomatic_bak.class
											.getDeclaredMethod(methodName,
													Map.class, String.class,
													String.class, String.class,
													BestInterp.class,
													expFitModel.class,
													poly2FitModel.class,
													String.class);
									m.invoke(new DataPredictAutomatic_bak(
											monitorItemTypeDao, definssortDao,
											commonPredictDao, sin_preAutoDao,
											dataPredictedDao, commonPreHandleDao,
											modelPointDao), map, dt, tableName, id,
											anlys2, expFit, poly2Fit, "r3");
								} catch (NoSuchMethodException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (SecurityException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IllegalArgumentException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					if(reCaculateStr == 0){
						reCaculateStr ++;
						i --;
					}else if(reCaculateStr == 1){
						reCaculateStr --;
					}
				}
			}
			map.put("tableName", tableName);
			map.put("dt", dt);
			try {
				commonPredictDao.addRes_auto(map);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("共需要时间" + (System.nanoTime() / 1000000L - st)
				/ 1000L);
		anlys2.dispose();
		expFit.dispose();
		poly2Fit.dispose();
	}

	/**
	 * matlab利用差值方法进行预测
	 * 
	 * @param map
	 * @param dt
	 * @param tableName
	 * @param pointList
	 */
	private void method2(Map<String, Object> map, String dt, String tableName,
			String id, BestInterp anlys2, expFitModel useless1,
			poly2FitModel useless2, String component) {
		Map<String, Object> resultMap = null;

		// for (String id : pointList) {
		if (id == null) {
			return;
		}
		map.clear();
		map.put("tableName", tableName);
		map.put("instr_no", id);
		map.put("modelId", "2");
		/*int predicted1 = dataPredictedDao.isPredicted1(map);
		if (predicted1 == 1) {
			return;
		}
		int predicted0 = dataPredictedDao.isPredicted0(map);
		if (predicted0 == 0) {
			dataPredictedDao.addPredicted(map);
		}*/

		long sTime = System.nanoTime() / 1000000L;
		// map.clear();
		if (id.contains("'")) {
			// continue;
			id = id.replaceAll("'", "''");
		}
		// System.out.println(id);
		// map.put("tableName", tableName);
		// map.put("instr_no", id);
		map.put("dt", dt);
		// map.put("modelId", "2");
		// 判断该点当天是否已经预测

		List<T_SIN_PRE_AUTO> liR1 = null;
		List<T_SIN_PRE_AUTO> liR2 = null;
		List<T_SIN_PRE_AUTO> liR3 = null;
		if (component.contains("r1")) {
			liR1 = sin_preAutoDao.getDataByIdTmR1(map);
		}
		if (component.contains("r2")) {
			liR2 = sin_preAutoDao.getDataByIdTmR2(map);
		}
		if (component.contains("r3")) {
			liR3 = sin_preAutoDao.getDataByIdTmR3(map);
		}

		MonitorItemType itemType = monitorItemTypeDao
				.getItemTypeByTableName(tableName);
		if ((!itemType.getR1().equals("0"))
				&& (liR1 == null || liR1.size() == 0)) {
			map.put("monitor_type", "r1");
			// 对R1进行处理
			try {
				resultMap = DataPredictUtil.predictMethod2(commonPredictDao, map,
						anlys2);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (resultMap != null) {
				resultMap.put("tableName", tableName);
				resultMap.put("component", "r1");
				resultMap.put("prediction_id", "0");
				try {
					sin_preAutoDao.add_res(resultMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if ((!itemType.getR2().equals("0"))
				&& (liR2 == null || liR2.size() == 0)) {
			map.put("monitor_type", "r2");
			// 对R2进行插入处理
			try {
				resultMap = DataPredictUtil.predictMethod2(commonPredictDao, map,
						anlys2);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (resultMap != null) {
				resultMap.put("tableName", tableName);
				resultMap.put("component", "r2");
				resultMap.put("prediction_id", "0");
				try {
					sin_preAutoDao.add_res(resultMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if ((!itemType.getR3().equals("0"))
				&& (liR3 == null || liR3.size() == 0)) {
			map.put("monitor_type", "r3");
			// 对R3进行插入处理
			try {
				resultMap = DataPredictUtil.predictMethod2(commonPredictDao, map,
						anlys2);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (resultMap != null) {
				resultMap.put("tableName", tableName);
				resultMap.put("component", "r3");
				resultMap.put("prediction_id", "0");
				try {
					sin_preAutoDao.add_res(resultMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// }
		System.out.println(id + "方法2需要时间:"
				+ (System.nanoTime() / 1000000L - sTime) / 1000L);
//		dataPredictedDao.updPredicted(map);
		// }
	}

	private void method3(Map<String, Object> map, String dt, String tableName,
			String id, BestInterp useless1, expFitModel anlys2,
			poly2FitModel useless2, String component) {
		Map<String, Object> resultMap = null;

		// for (String id : pointList) {
		if (id == null) {
			return;
		}
		map.clear();
		map.put("tableName", tableName);
		map.put("instr_no", id);
		map.put("modelId", "3");
		/*int predicted1 = dataPredictedDao.isPredicted1(map);
		if (predicted1 == 1) {
			return;
		}
		int predicted0 = dataPredictedDao.isPredicted0(map);
		if (predicted0 == 0) {
			dataPredictedDao.addPredicted(map);
		}*/

		long sTime = System.nanoTime() / 1000000L;
		// map.clear();
		if (id.contains("'")) {
			id = id.replaceAll("'", "''");
		}
		// System.out.println(tableName+"\t"+id+"\t"+dt);
		// map.put("tableName", tableName);
		// map.put("instr_no", id);
		map.put("dt", dt);
		// map.put("modelId", "3");
		// 判断该点当天是否已经预测
		List<T_SIN_PRE_AUTO> liR1 = null;
		List<T_SIN_PRE_AUTO> liR2 = null;
		List<T_SIN_PRE_AUTO> liR3 = null;
		if (component.contains("r1")) {
			liR1 = sin_preAutoDao.getDataByIdTmR1(map);
		}
		if (component.contains("r2")) {
			liR2 = sin_preAutoDao.getDataByIdTmR2(map);
		}
		if (component.contains("r3")) {
			liR3 = sin_preAutoDao.getDataByIdTmR3(map);
		}

		MonitorItemType itemType = monitorItemTypeDao
				.getItemTypeByTableName(tableName);
		if ((!itemType.getR1().equals("0"))
				&& (liR1 == null || liR1.size() == 0)) {
			map.put("monitor_type", "r1");
			// 对R1进行处理
			try {
				resultMap = DataPredictUtil.predictMethod3(commonPredictDao, map,
						anlys2);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (resultMap != null) {
				resultMap.put("tableName", tableName);
				resultMap.put("component", "r1");
				resultMap.put("prediction_id", "0");
				try {
					sin_preAutoDao.add_res(resultMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if ((!itemType.getR2().equals("0"))
				&& (liR2 == null || liR2.size() == 0)) {
			map.put("monitor_type", "r2");
			// 对R2进行插入处理
			try {
				resultMap = DataPredictUtil.predictMethod3(commonPredictDao, map,
						anlys2);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (resultMap != null) {
				resultMap.put("tableName", tableName);
				resultMap.put("component", "r2");
				resultMap.put("prediction_id", "0");
				try {
					sin_preAutoDao.add_res(resultMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if ((!itemType.getR3().equals("0"))
				&& (liR3 == null || liR3.size() == 0)) {
			map.put("monitor_type", "r3");
			// 对R3进行插入处理
			try {
				resultMap = DataPredictUtil.predictMethod3(commonPredictDao, map,
						anlys2);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (resultMap != null) {
				resultMap.put("tableName", tableName);
				resultMap.put("component", "r3");
				resultMap.put("prediction_id", "0");
				try {
					sin_preAutoDao.add_res(resultMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// }
		System.out.println(id + "方法3需要时间:"
				+ (System.nanoTime() / 1000000L - sTime) / 1000L);
//		dataPredictedDao.updPredicted(map);
		// }
	}

	private void method4(Map<String, Object> map, String dt, String tableName,
			String id, BestInterp useless1, expFitModel useless2,
			poly2FitModel anlys2, String component) {
		Map<String, Object> resultMap = null;

		// for (String id : pointList) {
		if (id == null) {
			return;
		}
		map.clear();
		map.put("tableName", tableName);
		map.put("instr_no", id);
		map.put("modelId", "4");
		/*int predicted1 = dataPredictedDao.isPredicted1(map);
		if (predicted1 == 1) {
			return;
		}
		int predicted0 = dataPredictedDao.isPredicted0(map);
		if (predicted0 == 0) {
			dataPredictedDao.addPredicted(map);
		}*/

		long sTime = System.nanoTime() / 1000000L;
		// map.clear();
		if (id.contains("'")) {
			id = id.replaceAll("'", "''");
		}
		// System.out.println(tableName+"\t"+id+"\t"+dt);
		// map.put("tableName", tableName);
		// map.put("instr_no", id);
		map.put("dt", dt);

		// map.put("modelId", "4");
		// 判断该点当天是否已经预测
		List<T_SIN_PRE_AUTO> liR1 = null;
		List<T_SIN_PRE_AUTO> liR2 = null;
		List<T_SIN_PRE_AUTO> liR3 = null;
		if (component.contains("r1")) {
			liR1 = sin_preAutoDao.getDataByIdTmR1(map);
		}
		if (component.contains("r2")) {
			liR2 = sin_preAutoDao.getDataByIdTmR2(map);
		}
		if (component.contains("r3")) {
			liR3 = sin_preAutoDao.getDataByIdTmR3(map);
		}

		MonitorItemType itemType = monitorItemTypeDao
				.getItemTypeByTableName(tableName);
		if ((!itemType.getR1().equals("0"))
				&& (liR1 == null || liR1.size() == 0)) {
			map.put("monitor_type", "r1");
			// 对R1进行处理
			try {
				resultMap = DataPredictUtil.predictMethod4(commonPredictDao, map,
						anlys2);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (resultMap != null) {
				resultMap.put("tableName", tableName);
				resultMap.put("component", "r1");
				resultMap.put("prediction_id", "0");
				try {
					sin_preAutoDao.add_res(resultMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if ((!itemType.getR2().equals("0"))
				&& (liR2 == null || liR2.size() == 0)) {
			map.put("monitor_type", "r2");
			// 对R2进行插入处理
			try {
				resultMap = DataPredictUtil.predictMethod4(commonPredictDao, map,
						anlys2);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (resultMap != null) {
				resultMap.put("tableName", tableName);
				resultMap.put("component", "r2");
				resultMap.put("prediction_id", "0");
				try {
					sin_preAutoDao.add_res(resultMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if ((!itemType.getR3().equals("0"))
				&& (liR3 == null || liR3.size() == 0)) {
			map.put("monitor_type", "r3");
			// 对R3进行插入处理
			try {
				resultMap = DataPredictUtil.predictMethod4(commonPredictDao, map,
						anlys2);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (resultMap != null) {
				resultMap.put("tableName", tableName);
				resultMap.put("component", "r3");
				resultMap.put("prediction_id", "0");
				try {
					sin_preAutoDao.add_res(resultMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// }
		System.out.println(id + "方法4需要时间:"
				+ (System.nanoTime() / 1000000L - sTime) / 1000L);
//		dataPredictedDao.updPredicted(map);
		// }
	}

	private void method5(Map<String, Object> map, String dt, String tableName,
			String id, BestInterp useless1, expFitModel useless2,
			poly2FitModel useless3, String component) {
		map.put("tableName", tableName);
		map.put("monitor_type", component);
		map.put("instr_no", id);
		List<LstzzParas> tmplist = lstzzParasDao.getParas(map);
		LstzzParas tmp = tmplist.get(0);
		Date nextDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;

		try {
			date = sdf.parse(dt);
			nextDate = sdf.parse(tmp.getNextDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (date.after(nextDate)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(nextDate);
			calendar.set(Calendar.DAY_OF_MONTH, calendar
					.get(Calendar.DAY_OF_MONTH)
					+ tmp.getStep());// 让日期加上设定的日期间隔
			String dateStr = sdf.format(calendar.getTime());
			map.put("nextDate", dateStr);
			lstzzParasDao.updateParas(map);
			midCharacterDao.removeMidChar(map);
			List<MidCharacteristic> midList = midCharacterDao
					.getCharDataP1(map);
			// for (int i=0;i<midList.size();i++){
			MidCharacteristic midTemp = midList.get(0);
			Map<String, Object> insMap = new HashMap<String, Object>();
			if (midTemp.getInstr_no().contains("'")) {
				String tmpStr = midTemp.getInstr_no();
				tmpStr = tmpStr.replaceAll("'", "''");
				midTemp.setInstr_no(tmpStr);
			}
			insMap.put("tableName", tableName);
			insMap.put("INSTR_NO", midTemp.getInstr_no());
			insMap.put("WL", midTemp.getWl());
			insMap.put("avgV", midTemp.getAvgV());
			insMap.put("minV", midTemp.getMinV());
			insMap.put("maxV", midTemp.getMaxV());
			insMap.put("monitor_type", component);

			// map.put("INSTR_NO",midTemp.getInstr_no());
			map.put("WL", midTemp.getWl());
			map.put("avgV", midTemp.getAvgV());
			List<MidCharacteristic> minAVList = midCharacterDao
					.getCharDataP2(map);
			List<MidCharacteristic> maxAVList = midCharacterDao
					.getCharDataP3(map);
			if (minAVList.isEmpty()) {
				insMap.put("minAV", midTemp.getAvgV());
			} else {
				MidCharacteristic minAvTemp = minAVList.get(0);
				insMap.put("minAV", minAvTemp.getMinAV());
			}
			if (maxAVList.isEmpty()) {
				insMap.put("maxAV", midTemp.getAvgV());
			} else {
				MidCharacteristic maxAvTemp = maxAVList.get(0);
				insMap.put("maxAV", maxAvTemp.getMaxAV());
			}
			midCharacterDao.addMidChar(insMap);
		}
		// }
		String resDateStr = sdf.format(date);
		lsCharacteristic(tableName, id, component, resDateStr);
	}

	public void lsCharacteristic(String tableName, String instr_no,
			String component, String date) {
		String sDate = "";
		String eDate = "";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", tableName);
		map.put("monitor_type", component);
		map.put("instr_no", instr_no);
		map.put("date", date);
		midCharacterDao.addCharRes(map);
	}

	public IMonitorItemTypeDao getMonitorItemTypeDao() {
		return monitorItemTypeDao;
	}

	public void setMonitorItemTypeDao(IMonitorItemTypeDao monitorItemTypeDao) {
		this.monitorItemTypeDao = monitorItemTypeDao;
	}

	public IDEFINSSORTDao getDefinssortDao() {
		return definssortDao;
	}

	public void setDefinssortDao(IDEFINSSORTDao definssortDao) {
		this.definssortDao = definssortDao;
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

	public void setSin_preAutoDao(ISIN_PRE_AUTODao sinPreAutoDao) {
		sin_preAutoDao = sinPreAutoDao;
	}

	public IDataPredictedDao getDataPredictedDao() {
		return dataPredictedDao;
	}

	public void setDataPredictedDao(IDataPredictedDao dataPredictedDao) {
		this.dataPredictedDao = dataPredictedDao;
	}

	public ICommonPreHandleDao getCommonPreHandleDao() {
		return commonPreHandleDao;
	}

	public void setCommonPreHandleDao(ICommonPreHandleDao commonPreHandleDao) {
		this.commonPreHandleDao = commonPreHandleDao;
	}

	public IModelPointDao getModelPointDao() {
		return modelPointDao;
	}

	public void setModelPointDao(IModelPointDao modelPointDao) {
		this.modelPointDao = modelPointDao;
	}

	public IMidCharacteristicDao getMidCharacterDao() {
		return midCharacterDao;
	}

	public void setMidCharacterDao(IMidCharacteristicDao midCharacterDao) {
		this.midCharacterDao = midCharacterDao;
	}

}
