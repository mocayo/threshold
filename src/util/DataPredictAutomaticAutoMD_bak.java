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
import Dao.ISigamaDao;
import Dao.IW_CENodeMetMemDao;
import Entity.DEFINSSORT;
import Entity.LstzzParas;
import Entity.MidCharacteristic;
import Entity.ModelPoint;
import Entity.MonitorItemType;
import Entity.T_SIN_PRE_AUTO;

public class DataPredictAutomaticAutoMD_bak extends Action{

	private String genId;
	private IMonitorItemTypeDao monitorItemTypeDao;
	private IDEFINSSORTDao definssortDao;
	private ICommonPredictDao commonPredictDao;
	private ISIN_PRE_AUTODao sin_preAutoDao;
	private IDataPredictedDao dataPredictedDao;
	private ICommonPreHandleDao commonPreHandleDao;
	private IModelPointDao modelPointDao;
	private IMidCharacteristicDao midCharacterDao;
	private ILstzzParasDao lstzzParasDao;
	private IW_CENodeMetMemDao ceNodeMetMemDao;
	private ISigamaDao sigamaDao;
	
	public static BestInterp anlys2;
	public static autoModelSelect autoModelSelect;
	public static expFitModel expFit;
	public static poly2FitModel poly2Fit;
	
		public void execute() throws IOException {
			/*try {
				PreHandleAutomatic pre = new PreHandleAutomatic(commonPreHandleDao,monitorItemTypeDao,modelPointDao);
				pre.preHandleAll("");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			jobTimer();
		}

		public DataPredictAutomaticAutoMD_bak(IMonitorItemTypeDao monitorItemTypeDao,
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

			genId = new IdGenerator().generateId();

			Map<String, Object> map = new HashMap<String, Object>();

			// Date today = new Date();
			// SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			// String dt = sf.format(today);

			try {
				anlys2 = new BestInterp();
				expFit = new expFitModel();
				poly2Fit = new poly2FitModel();
				// autoModelSelect = new autoModelSelect();
			} catch (MWException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String str = "2012-5-30";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdf.parse(str);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar calendar = Calendar.getInstance();
//			List<String> tableNameList = new ArrayList<String>();
//			List<String> topList = monitorItemTypeDao.getTableNames();// new
//			tableNameList.addAll(topList);

//			for (String tableName : tableNameList) {
			
			for(int h = 1 ; h < 11 ; h ++ ){
			List<String> mapList =  modelPointDao.getMapIds(h+"");
			
			for (String mapId : mapList){
				List<ModelPoint> iswhereList =  modelPointDao.getPointsInfo(mapId);
//			String tableName = "T_ZB_PL";
				
				// 根据表名得到所有测点
//				List<ModelPoint> iswhereList = modelPointDao
//						.getCaculatePoints(tableName);
				/*if (iswhereList.size() == 0)
					continue;*/
				// String[] pointList = new String[iswhereList.size()];
				for (int i = 0; i < iswhereList.size(); i++) {
					String id = iswhereList.get(i).getInstr_no().trim();
					String tableName = iswhereList.get(i).getMonitorItem().trim();
					/*if (id.equals("C4-A04-PL-03")||id.equals("C4-A04-PL-02")){
						continue;
					}
					String msR1Str = iswhereList.get(i).getR1().trim();
					String msR2Str = iswhereList.get(i).getR2().trim();
					String msR3Str = iswhereList.get(i).getR3().trim();
					String[] msR1 = msR1Str.split(",");
					String[] msR2 = msR2Str.split(",");
					String[] msR3 = msR3Str.split(",");*/
//					if (msR1Str.equals("") && msR2Str.equals("") && msR3Str.equals("")) {
						try {
							method2(map, date, calendar, sdf, tableName, id, anlys2,
									expFit, poly2Fit, "r1,r2,r3");
							method3(map, date, calendar, sdf, tableName, id, anlys2,
									expFit, poly2Fit, "r1,r2,r3");
							method4(map, date, calendar, sdf, tableName, id, anlys2,
									expFit, poly2Fit, "r1,r2,r3");
							/*method5(map, date, calendar, sdf, tableName, id, anlys2,
									expFit, poly2Fit, "r1,r2,r3");*/
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}}
			
			List<String> tableNameList = monitorItemTypeDao.getTableNames();// new
			for (String tableName : tableNameList) {
				method6(tableName,date,calendar, sdf);
			}
			System.out.println("共需要时间" + (System.nanoTime() / 1000000L - st)
					/ 1000L);
			anlys2.dispose();
			// autoModelSelect.dispose();
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
		private void method2(Map<String, Object> map, Date date, Calendar calendar,
				SimpleDateFormat sdf, String tableName, String id, BestInterp anlys2,expFitModel useless1,poly2FitModel useless2,String component) {
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

			calendar.setTime(date);
			long sTime = System.nanoTime() / 1000000L;
			for (int j = 0; j < 1411; j++) {
				calendar.set(Calendar.DAY_OF_MONTH,
						calendar.get(Calendar.DAY_OF_MONTH) + 1);// 让日期加1
				String dt = sdf.format(calendar.getTime());
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
				if(component.contains("r1")){
					liR1 = sin_preAutoDao.getDataByIdTmR1(map);
				}
				if(component.contains("r2")){
					liR2 = sin_preAutoDao.getDataByIdTmR2(map);
				}
				if(component.contains("r3")){
					liR3 = sin_preAutoDao.getDataByIdTmR3(map);
				}

				MonitorItemType itemType = monitorItemTypeDao
						.getItemTypeByTableName(tableName);
				if ((!itemType.getR1().equals("0"))
						&& (liR1 == null || liR1.size() == 0)) {
					map.put("monitor_type", "r1");
					// 对R1进行处理
					try {
						resultMap = DataPredictUtil.predictMethod2(commonPredictDao,
								map, anlys2);
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
						resultMap = DataPredictUtil.predictMethod2(commonPredictDao,
								map, anlys2);
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
						resultMap = DataPredictUtil.predictMethod2(commonPredictDao,
								map, anlys2);
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
			}
			System.out.println(id + "方法2需要时间:"
					+ (System.nanoTime() / 1000000L - sTime) / 1000L);
//			dataPredictedDao.updPredicted(map);
			// }
		}

		private void method3(Map<String, Object> map, Date date, Calendar calendar,
				SimpleDateFormat sdf, String tableName, String id,BestInterp useless1,
				expFitModel anlys2,poly2FitModel useless2,String component) {
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

			calendar.setTime(date);
			long sTime = System.nanoTime() / 1000000L;
			for (int j = 0; j < 1411; j++) {
				calendar.set(Calendar.DAY_OF_MONTH,
						calendar.get(Calendar.DAY_OF_MONTH) + 1);// 让日期加1
				String dt = sdf.format(calendar.getTime());
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
				if(component.contains("r1")){
					liR1 = sin_preAutoDao.getDataByIdTmR1(map);
				}
				if(component.contains("r2")){
					liR2 = sin_preAutoDao.getDataByIdTmR2(map);
				}
				if(component.contains("r3")){
					liR3 = sin_preAutoDao.getDataByIdTmR3(map);
				}

				MonitorItemType itemType = monitorItemTypeDao
						.getItemTypeByTableName(tableName);
				if ((!itemType.getR1().equals("0"))
						&& (liR1 == null || liR1.size() == 0)) {
					map.put("monitor_type", "r1");
					// 对R1进行处理
					try {
						resultMap = DataPredictUtil.predictMethod3(commonPredictDao,
								map, anlys2);
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
						resultMap = DataPredictUtil.predictMethod3(commonPredictDao,
								map, anlys2);
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
						resultMap = DataPredictUtil.predictMethod3(commonPredictDao,
								map, anlys2);
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
			}
			System.out.println(id + "方法3需要时间:"
					+ (System.nanoTime() / 1000000L - sTime) / 1000L);
//			dataPredictedDao.updPredicted(map);
			// }
		}

		private void method4(Map<String, Object> map, Date date, Calendar calendar,
				SimpleDateFormat sdf, String tableName, String id, BestInterp useless1,expFitModel useless2,
				poly2FitModel anlys2,String component) {
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

			calendar.setTime(date);
			long sTime = System.nanoTime() / 1000000L;
			for (int j = 0; j < 1411; j++) {
				calendar.set(Calendar.DAY_OF_MONTH,
						calendar.get(Calendar.DAY_OF_MONTH) + 1);// 让日期加1
				String dt = sdf.format(calendar.getTime());
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
				if(component.contains("r1")){
					liR1 = sin_preAutoDao.getDataByIdTmR1(map);
				}
				if(component.contains("r2")){
					liR2 = sin_preAutoDao.getDataByIdTmR2(map);
				}
				if(component.contains("r3")){
					liR3 = sin_preAutoDao.getDataByIdTmR3(map);
				}

				MonitorItemType itemType = monitorItemTypeDao
						.getItemTypeByTableName(tableName);
				if ((!itemType.getR1().equals("0"))
						&& (liR1 == null || liR1.size() == 0)) {
					map.put("monitor_type", "r1");
					// 对R1进行处理
					try {
						resultMap = DataPredictUtil.predictMethod4(commonPredictDao,
								map, anlys2);
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
						resultMap = DataPredictUtil.predictMethod4(commonPredictDao,
								map, anlys2);
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
						resultMap = DataPredictUtil.predictMethod4(commonPredictDao,
								map, anlys2);
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
			}
			System.out.println(id + "方法4需要时间:"
					+ (System.nanoTime() / 1000000L - sTime) / 1000L);
//			dataPredictedDao.updPredicted(map);
			// }
		}

		private void method5(Map<String, Object> map, Date date, Calendar calendar,
				SimpleDateFormat sdf, String tableName, String id,BestInterp useless1,
				expFitModel useless2,poly2FitModel useless3,String component) throws Exception{
			String[] comS = component.split(",");
			for (String comonentStr : comS){
			map.put("tableName",tableName);
			map.put("monitor_type",comonentStr);
			map.put("instr_no",id);
			calendar.setTime(date);//当前要计算的日期
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.get(Calendar.DAY_OF_MONTH) - 1);// 让日期-1
			for (int j = 0; j < 1411; j++) {
				String dateStr = sdf.format(calendar.getTime());
				calendar.set(Calendar.DAY_OF_MONTH,
						calendar.get(Calendar.DAY_OF_MONTH) + 1);// 让日期加1
				String resDateStr = sdf.format(calendar.getTime());
				map.put("nextDate",dateStr);
				midCharacterDao.removeMidChar(map);
				List<MidCharacteristic> midList = midCharacterDao.getCharDataP1(map);
				for (int i=0;i<midList.size();i++){
					MidCharacteristic midTemp = midList.get(i);
					Map<String, Object> insMap = new HashMap<String, Object>();
					if (midTemp.getInstr_no().contains("'")){
						String tmpStr = midTemp.getInstr_no();
						tmpStr = tmpStr.replaceAll("'", "''");
						midTemp.setInstr_no(tmpStr);
					}
					insMap.put("tableName",tableName);
					insMap.put("INSTR_NO",midTemp.getInstr_no());
					insMap.put("WL",midTemp.getWl());
					insMap.put("avgV",midTemp.getAvgV());
					insMap.put("minV",midTemp.getMinV());
					insMap.put("maxV",midTemp.getMaxV());
					insMap.put("monitor_type",comonentStr);
					
//					map.put("INSTR_NO",midTemp.getInstr_no());
					map.put("WL",midTemp.getWl());
					map.put("avgV",midTemp.getAvgV());
					List<MidCharacteristic> minAVList = midCharacterDao.getCharDataP2(map);
					List<MidCharacteristic> maxAVList = midCharacterDao.getCharDataP3(map);
					if(minAVList.isEmpty()){
						insMap.put("minAV",midTemp.getAvgV());
					} else {
						MidCharacteristic minAvTemp = minAVList.get(0);
						insMap.put("minAV",minAvTemp.getMinAV());
					}
					if(maxAVList.isEmpty()){
						insMap.put("maxAV",midTemp.getAvgV());
					} else {
						MidCharacteristic maxAvTemp = maxAVList.get(0);
						insMap.put("maxAV",maxAvTemp.getMaxAV());
					}
					try {
						midCharacterDao.addMidChar(insMap);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					lsCharacteristic(tableName,id,comonentStr,resDateStr);
				}
			}
			}
		}
		
		/**
		 * sigema判定法
		 * @param tableName
		 */
		private void method6(String tableName,Date date, Calendar calendar,
				SimpleDateFormat sdf){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tableName",tableName);
			calendar.setTime(date);
			long sTime = System.nanoTime() / 1000000L;
			for (int j = 0; j < 1411; j++) {
				calendar.set(Calendar.DAY_OF_MONTH,
						calendar.get(Calendar.DAY_OF_MONTH) + 1);// 让日期加1
				String dt = sdf.format(calendar.getTime());
			map.put("dt",dt);
			sigamaDao.addGreen(map);
			sigamaDao.addOrange(map);
			sigamaDao.addRed(map);
			for (int i = 0;i < 2 ; i ++){
				map.put("component","r"+i);
				ceNodeMetMemDao.addCurrentGreen(map);
				ceNodeMetMemDao.addCurrentOrange(map);
				ceNodeMetMemDao.addCurrentRed(map);
			}
			}
			/*for(int i=1 ; i< 4;i++){
				
			}*/
		}
		
		public void method7(Map<String, Object> map, String tableName,
				String id,Date date, Calendar calendar,
				SimpleDateFormat sdf){
			map.put("tableName",tableName);
			calendar.setTime(date);
			for (int j = 0; j < 1411; j++) {
				calendar.set(Calendar.DAY_OF_MONTH,
						calendar.get(Calendar.DAY_OF_MONTH) + 1);// 让日期加1
				String dt = sdf.format(calendar.getTime());
			map.put("dt",dt);
			if (id == null) {
				return;
			}
			map.clear();
			map.put("tableName", tableName);
			map.put("instr_no", id);
			map.put("modelId", "7");

			long sTime = System.nanoTime() / 1000000L;
			// map.clear();
			if (id.contains("'")) {
				id = id.replaceAll("'", "''");
			}
			map.put("dt", dt);

			List<T_SIN_PRE_AUTO> liR1 = null;
			List<T_SIN_PRE_AUTO> liR2 = null;
			List<T_SIN_PRE_AUTO> liR3 = null;
				liR1 = sin_preAutoDao.getDataByIdTmR1(map);
				liR2 = sin_preAutoDao.getDataByIdTmR2(map);

			MonitorItemType itemType = monitorItemTypeDao
					.getItemTypeByTableName(tableName);
			
			if (((!itemType.getR1().equals("0"))
					&& (liR1 != null && liR1.size() == 0))&&
					((!itemType.getR2().equals("0"))
							&& (liR2 != null && liR2.size() == 0))){
				try {
					DataPredictUtil.predictMethod5(commonPredictDao,sin_preAutoDao, map);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
		}
		
		public void lsCharacteristic(String tableName,String instr_no,String component,String date) {
			String sDate = "";
			String eDate = "";
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tableName",tableName);
			map.put("monitor_type",component);
			map.put("instr_no",instr_no);
			map.put("date",date);
			try {
				midCharacterDao.addCharRes(map);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	public IW_CENodeMetMemDao getCeNodeMetMemDao() {
		return ceNodeMetMemDao;
	}

	public void setCeNodeMetMemDao(IW_CENodeMetMemDao ceNodeMetMemDao) {
		this.ceNodeMetMemDao = ceNodeMetMemDao;
	}

	public ISigamaDao getSigamaDao() {
		return sigamaDao;
	}

	public void setSigamaDao(ISigamaDao sigamaDao) {
		this.sigamaDao = sigamaDao;
	} 

}
