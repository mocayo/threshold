package Action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import poly2FitModel.poly2FitModel;
import net.sf.json.JSONObject;
import util.Action;
import util.DataSinglePredictUtil;
import util.IdGenerator;
import BestInterp.BestInterp;
import Dao.ICommonPreHandleDao;
import Dao.ICommonPredictDao;
import Dao.IDEFINSSORTDao;
import Dao.IDataPredictedDao;
import Dao.IINS_SORTDao;
import Dao.ILstzzParasDao;
import Dao.IMidCharacteristicDao;
import Dao.IModelPointDao;
import Dao.IMonitorItemTypeDao;
import Dao.ISINGLE_PREDICTIONDao;
import Dao.ISIN_PRE_AUTODao;
import Entity.CommonPredict;
import Entity.INS_SORT;
import Entity.MonitorItemType;
import Entity.PREDICT_DATA;
import Entity.T_SIN_PRE_AUTO;
import autoModelSelect.autoModelSelect;

import com.mathworks.toolbox.javabuilder.MWArray;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

import expFitModel.expFitModel;

/**
 * 预测
 * 
 * @author qihai
 * 
 */
public class ManualSinglePredictAction extends Action {

	private ISINGLE_PREDICTIONDao single_predictionDao;
	private IINS_SORTDao ins_sortDao;
	private IMonitorItemTypeDao monitorItemTypeDao;
	private IDEFINSSORTDao definssortDao;
	private ICommonPredictDao commonPredictDao;
	private ISIN_PRE_AUTODao sin_preAutoDao;
	private IDataPredictedDao dataPredictedDao;
	private ICommonPreHandleDao commonPreHandleDao;
	private IModelPointDao modelPointDao;
	private IMidCharacteristicDao midCharacterDao;
	private ILstzzParasDao lstzzParasDao;
	
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


	public ISIN_PRE_AUTODao getSin_preAutoDao() {
		return sin_preAutoDao;
	}


	public void setSin_preAutoDao(ISIN_PRE_AUTODao sin_preAutoDao) {
		this.sin_preAutoDao = sin_preAutoDao;
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


	public ILstzzParasDao getLstzzParasDao() {
		return lstzzParasDao;
	}


	public void setLstzzParasDao(ILstzzParasDao lstzzParasDao) {
		this.lstzzParasDao = lstzzParasDao;
	}

	public static BestInterp anlys2;
	public static autoModelSelect autoModelSelect;
	public static expFitModel expFit;
	public static poly2FitModel poly2Fit;

	public void execute() throws IOException {
		long st = System.currentTimeMillis();

		String premodel = request.getParameter("premodel");
		String startTime = request.getParameter("sTime");
		String endTime = request.getParameter("eTime");
		//String[] tables = request.getParameter("title").trim().split(","); // 表名
		String tableNameStr = request.getParameter("title").trim(); // 表名
		String[] tables = tableNameStr.split(",");
		String decType_Sel = request.getParameter("decType_Sel").trim(); // 类型
		String dam_Sel = request.getParameter("dam_Sel").trim(); // 坝段
		String pointIdString = request.getParameter("instr_Sel").trim(); // 测点编号
		String monitorStr = request.getParameter("component_Sel").trim(); // 分量 monitor_type

		// 批次号
		String genId = new IdGenerator().generateId();
		
		//需要删除a 
		System.out.println("==============传过来的参数 start==============");
		System.out.println("premodel = " + premodel);
		System.out.println("开始时间:" + startTime);
		System.out.println("至 :" + endTime);
		System.out.println("preId = " + genId);
		System.out.println("tableName = " + /*Arrays.toString(tables)*/tableNameStr);
		System.out.println("decType = " + decType_Sel);
		System.out.println("dam = " + dam_Sel);
		System.out.println("point = " + pointIdString);
		System.out.println("component = " + monitorStr);
		System.out.println("==============传过来的参数 end ===============");
		
		// 预测X分量
		// startTime = "2009-01-01";
		// endTime = "2013-01-01";
		// tableName = "T_ZB_EA";
		// pointIdString = "C2C-Lf12D-D3-C-01";
		// monitorStr = "R1";
		// decType_Sel = "6";

		Map<String, Object> staticsMap = new HashMap<String, Object>();
		Map<String, Object> date1Map = new HashMap<String, Object>();
		Map<String, Object> date2Map = new HashMap<String, Object>();
		Map<String, Object> date3Map = new HashMap<String, Object>();

		String[] pointList = pointIdString.split(",");
		/*if (pointIdString == null || pointIdString.equals("null")) {// 如果传递监测点为空，则根据坝段和监测项查询所有的点
			List<INS_SORT> iswhereList = ins_sortDao.getIns_Sorts(
					Integer.parseInt(decType_Sel), dam_Sel);
			pointList = new String[iswhereList.size()];
			for (int i = 0; i < iswhereList.size(); i++) {
				pointList[i] = iswhereList.get(i).getDesignCode().trim();
			}
		}
		else {
			pointList = pointIdString.split(",");
		}*/

		String[] componentList = monitorStr.split(";");
		/*if (pointIdString == null || pointIdString.equals("null")) {// 如果传递监测点为空，则根据坝段和监测项查询所有的点
			MonitorItemType monitorItem = monitorItemTypeDao
					.getItemTypeByTableName(tableName);
			componentList = new String[3];
			componentList[0] = "," + monitorItem.getR1();
			componentList[1] = "," + monitorItem.getR2();
			componentList[2] = "," + monitorItem.getR3();
		} else {
			componentList = monitorStr.split(";");
		}*/

		//修改完成需要删除
		//test输出测点以及分量
		System.out.println("the points");
		for(String strins:pointList)
			System.out.println(strins);
		System.out.println("the components");
		for(String strins2:componentList)
			System.out.println(strins2);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pre_id", genId);
		
		
		//开始修改
		try {
			anlys2 = new BestInterp();
			expFit = new expFitModel();
			poly2Fit = new poly2FitModel();
			// autoModelSelect = new autoModelSelect();
		} catch (MWException e) {
			e.printStackTrace();
		}
		System.out.println("=========================line 240===========================");
		//设置时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = sdf.parse(startTime);
			date2 = sdf.parse(endTime);
		} catch (ParseException e) {
			System.out.println("开始日期转换错误");
			e.printStackTrace();
		}
		Calendar start = Calendar.getInstance();
		start.setTime(date1);
		Calendar end = Calendar.getInstance();
		end.setTime(date2);
		//为了让最后一天也计算，结束日期加一天
		end.add(Calendar.DAY_OF_MONTH, 1);

		//每一个点开始，遍历所有日期
//		for(String the_point:pointList){
		for(int poindex=0;poindex<pointList.length;poindex++){
			String the_point = pointList[poindex].trim();
			String tableName = tables[poindex].trim();
			
			Map<String, Object> staticsMapcd = new HashMap<String, Object>();
			Map<String, Object> date1Mapcd = new HashMap<String, Object>();
			Map<String, Object> date2Mapcd = new HashMap<String, Object>();
			Map<String, Object> date3Mapcd = new HashMap<String, Object>();
			
			//根据premodel中的方法来进行预测
			String[] methods = premodel.split(",");
			String comp = "";
			for (int k = 0; k < componentList.length; k++) {
				if (componentList[k].contains(the_point)) {
//					comp += componentList[k].split(",")[1] + ",";
					comp = componentList[k].substring(componentList[k].indexOf(",")+1);
				}
			}
			System.out.println("======================================================================");
			System.out.println("测点：" + the_point);
			System.out.println("表：" + tableName);
			System.out.println("分量：" + comp);
			System.out.println("======================================================================");
			//日期循环开始
			while(start.before(end)){
				String the_dt = sdf.format(start.getTime());
				start.set(Calendar.DAY_OF_MONTH, start.get(Calendar.DAY_OF_MONTH) + 1);
				
				for(String method:methods){
					if("method2".equals(method)){
						System.out.println("method2 running!");
						method2(map, the_dt, tableName, the_point, anlys2, expFit, poly2Fit,
								comp);
					}
					if("method3".equals(method)){
						System.out.println("method3 running!");
						method3(map, the_dt, tableName, the_point, anlys2, expFit, poly2Fit,
								comp);
					}
					if("method4".equals(method)){
						System.out.println("method4 running!");
						method4(map, the_dt, tableName, the_point, anlys2, expFit, poly2Fit,
								comp);
					}
				}
			}
			//日期循环结束
			
			
			String[] compontents = comp.split(",");
			for (int i = 0; i <compontents.length; i++){
				Map<String, Object> staticsMapfl = new HashMap<String, Object>();
				Map<String, Object> date1Mapfl = new HashMap<String, Object>();
				Map<String, Object> date2Mapfl = new HashMap<String, Object>();
				Map<String, Object> date3Mapfl = new HashMap<String, Object>();
				
			String monitor_type = compontents[i];
			Map<String, Object> paramap = new HashMap<String, Object>();
			paramap.put("monitor_type", monitor_type);
			paramap.put("prediction_id", genId);
			paramap.put("tableName", tableName);
			List<String> midList0 = commonPredictDao.getRsme0(paramap);
			List<String> midList1 = commonPredictDao.getRsme1(paramap);
			List<String> midList2 = commonPredictDao.getRsme2(paramap);
			Double m2rmsePow = 0d;
			Double m1rmsePow = 0d;
			Double m0rmsePow = 0d;
			List<Double> m2rmseList = new ArrayList<Double>();
			List<Double> m1rmseList = new ArrayList<Double>();
			List<Double> m0rmseList = new ArrayList<Double>();
			
			for (int j = 0; j < midList0.size(); j++) {
				if (midList0.get(j) != null && !midList0.get(j).equals("")) {
					m0rmseList.add(Double.valueOf(midList0.get(j)));
					m0rmsePow += Math
					.pow(Double.valueOf(midList0.get(j)), 2);
				}
			}
			for (int j = 0; j < midList1.size(); j++) {
				if (midList1.get(j) != null && !midList1.get(j).equals("")) {
					m1rmseList.add(Double.valueOf(midList1.get(j)));
					m1rmsePow += Math
					.pow(Double.valueOf(midList1.get(j)), 2);
				}
			}
			for (int j = 0; j < midList2.size(); j++) {
				if (midList2.get(j) != null && !midList2.get(j).equals("")) {
					m2rmseList.add(Double.valueOf(midList2.get(j)));
					m2rmsePow += Math
					.pow(Double.valueOf(midList2.get(j)), 2);
				}
			}
			
			Collections.sort(m2rmseList);
			Collections.sort(m1rmseList);
			Collections.sort(m0rmseList);
			
			int m2oneIndex = (int) (m2rmseList.size() * 0.99);
			int m2twoIndex = (int) (m2rmseList.size() * 0.95);
			int m2threeIndex = (int) (m2rmseList.size() * 0.90);
			Double m2oneData = 0d;
			Double m2twoData = 0d;
			Double m2threeData = 0d;
			if (m2oneIndex != 0) {
				m2oneData = m2rmseList.get(m2oneIndex);
			}
			if (m2twoIndex != 0) {
				m2twoData = m2rmseList.get(m2twoIndex);
			}
			if (m2threeIndex != 0) {
				m2threeData = m2rmseList.get(m2threeIndex);
			}
			
			int m1oneIndex = (int) (m1rmseList.size() * 0.99);
			int m1twoIndex = (int) (m1rmseList.size() * 0.95);
			int m1threeIndex = (int) (m1rmseList.size() * 0.90);
			Double m1oneData = 0d;
			Double m1twoData = 0d;
			Double m1threeData = 0d;
			if (m1oneIndex != 0) {
				m1oneData = m1rmseList.get(m1oneIndex);
			}
			if (m1twoIndex != 0) {
				m1twoData = m1rmseList.get(m1twoIndex);
			}
			if (m1threeIndex != 0) {
				m1threeData = m1rmseList.get(m1threeIndex);
			}
			
			int m0oneIndex = (int) (m0rmseList.size() * 0.99);
			int m0twoIndex = (int) (m0rmseList.size() * 0.95);
			int m0threeIndex = (int) (m0rmseList.size() * 0.90);
			Double m0oneData = 0d;
			Double m0twoData = 0d;
			Double m0threeData = 0d;
			if (m0oneIndex != 0) {
				m0oneData = m0rmseList.get(m0oneIndex);
			}
			if (m0twoIndex != 0) {
				m0twoData = m0rmseList.get(m0twoIndex);
			}
			if (m0threeIndex != 0) {
				m0threeData = m0rmseList.get(m0threeIndex);
			}
			Map<String, Object> mpici = new HashMap<String, Object>();

			mpici.put("prediction_id", genId);
			mpici.put("tableName", tableName);
			mpici.put("instr_no", the_point);
			List<CommonPredict> paraList0 = commonPredictDao
					.resultsM0(mpici);
			List<CommonPredict> paraList1 = commonPredictDao
			.resultsM1(mpici);
			List<CommonPredict> paraList2 = commonPredictDao
			.resultsM2(mpici);

			int m2countOneData = 0;
			int m2countTwoData = 0;
			int m2countThreeData = 0;
			int m2countNomalData = 0;
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
			String m2date1 = "";
			String m2date2 = "";
			String m2date3 = "";

			for (int j = 0; j < paraList0.size(); j++) {
				CommonPredict tempObj = paraList0.get(j);
				String err_rate = "";
				if (monitor_type.equalsIgnoreCase("R1")) {
					err_rate = tempObj.getR1_err_rate()+"";
				} else if (monitor_type.equalsIgnoreCase("R2")) {
					err_rate = tempObj.getR2_err_rate()+"";
				} else if (monitor_type.equalsIgnoreCase("R3")) {
					err_rate = tempObj.getR3_err_rate()+"";
				}

				if (!err_rate.equals("")) {
					if (Double.parseDouble(err_rate) >= m0oneData) {
						m0countOneData++;
						m0date1 += "," + tempObj.getDt();
					} else if (Double.parseDouble(err_rate) < m0oneData
							&& Double.parseDouble(err_rate) >= m0twoData) {
						m0countTwoData++;
						m0date2 += "," + tempObj.getDt();
					} else if (Double.parseDouble(err_rate) < m0twoData
							&& Double.parseDouble(err_rate) >= m0threeData) {
						m0countThreeData++;
						m0date3 += "," + tempObj.getDt();
					} else {
						m0countNomalData++;
					}
				}
			}
			
			for (int j = 0; j < paraList1.size(); j++) {
				CommonPredict tempObj = paraList1.get(j);
				String err_rate = "";
				if (monitor_type.equalsIgnoreCase("R1")) {
					err_rate = tempObj.getR1_err_rate()+"";
				} else if (monitor_type.equalsIgnoreCase("R2")) {
					err_rate = tempObj.getR2_err_rate()+"";
				} else if (monitor_type.equalsIgnoreCase("R3")) {
					err_rate = tempObj.getR3_err_rate()+"";
				}

				if (!err_rate.equals("")) {
					if (Double.parseDouble(err_rate) >= m1oneData) {
						m1countOneData++;
						m1date1 += "," + tempObj.getDt();
					} else if (Double.parseDouble(err_rate) < m1oneData
							&& Double.parseDouble(err_rate) >= m1twoData) {
						m1countTwoData++;
						m1date2 += "," + tempObj.getDt();
					} else if (Double.parseDouble(err_rate) < m1twoData
							&& Double.parseDouble(err_rate) >= m1threeData) {
						m1countThreeData++;
						m1date3 += "," + tempObj.getDt();
					} else {
						m1countNomalData++;
					}
				}
			}
			
			for (int j = 0; j < paraList2.size(); j++) {
				CommonPredict tempObj = paraList2.get(j);
				String err_rate = "";
				if (monitor_type.equalsIgnoreCase("R1")) {
					err_rate = tempObj.getR1_err_rate()+"";
				} else if (monitor_type.equalsIgnoreCase("R2")) {
					err_rate = tempObj.getR2_err_rate()+"";
				} else if (monitor_type.equalsIgnoreCase("R3")) {
					err_rate = tempObj.getR3_err_rate()+"";
				}
				
				if (!err_rate.equals("")) {
					if (Double.parseDouble(err_rate) >= m2oneData) {
						m2countOneData++;
						m2date1 += "," + tempObj.getDt();
					} else if (Double.parseDouble(err_rate) < m2oneData
							&& Double.parseDouble(err_rate) >= m2twoData) {
						m2countTwoData++;
						m2date2 += "," + tempObj.getDt();
					} else if (Double.parseDouble(err_rate) < m2twoData
							&& Double.parseDouble(err_rate) >= m2threeData) {
						m2countThreeData++;
						m2date3 += "," + tempObj.getDt();
					} else {
						m2countNomalData++;
					}
				}
			}
			

			// 模型4		二次多项式
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("oneData", m2oneData);
			m2.put("twoData", m2twoData);
			m2.put("threeData", m2threeData);
			m2.put("rmsePow", Math.sqrt(m2rmsePow));
			m2.put("countOneData", m2countOneData);
			m2.put("countTwoData", m2countTwoData);
			m2.put("countThreeData", m2countThreeData);
			m2.put("countNomalData", m2countNomalData);
			if(m2oneData !=0 && m2twoData!=0 && m2threeData !=0)
				staticsMapfl.put("m2", m2);
			
			Map<String, Object> m2d1 = new HashMap<String, Object>();
			if (!m2date1.equals("")) {
//				m2d1.put("date1", m1date1.substring(1));
				m2d1.put("date1", m2date1.substring(1));
			}
			
			// m1d1.put("date2", m1date2.substring(1));
			// m1d1.put("date2", m1date3.substring(1));
			date1Mapfl.put("m2", m2d1);
			
			Map<String, Object> m2d2 = new HashMap<String, Object>();
			if (!m2date2.equals("")) {
				m2d2.put("date2", m2date2.substring(1));
			}
			
			date2Mapfl.put("m2", m2d2);
			
			Map<String, Object> m2d3 = new HashMap<String, Object>();
			if (!m2date3.equals("")) {
				m2d3.put("date3", m2date3.substring(1));
			}
			date3Mapfl.put("m2", m2d3);
			
			// 模型3		拟合
			Map<String, Object> m1 = new HashMap<String, Object>();
			m1.put("oneData", m1oneData);
			m1.put("twoData", m1twoData);
			m1.put("threeData", m1threeData);
			m1.put("rmsePow", Math.sqrt(m1rmsePow));
			m1.put("countOneData", m1countOneData);
			m1.put("countTwoData", m1countTwoData);
			m1.put("countThreeData", m1countThreeData);
			m1.put("countNomalData", m1countNomalData);
			if(m1oneData !=0 && m1twoData!=0 && m1threeData !=0)
				staticsMapfl.put("m1", m1);

			Map<String, Object> m1d1 = new HashMap<String, Object>();
			if (!m1date1.equals("")) {
				m1d1.put("date1", m1date1.substring(1));
			}

			// m1d1.put("date2", m1date2.substring(1));
			// m1d1.put("date2", m1date3.substring(1));
			date1Mapfl.put("m1", m1d1);

			Map<String, Object> m1d2 = new HashMap<String, Object>();
			if (!m1date2.equals("")) {
				m1d2.put("date2", m1date2.substring(1));
			}

			date2Mapfl.put("m1", m1d2);

			Map<String, Object> m1d3 = new HashMap<String, Object>();
			if (!m1date3.equals("")) {
				m1d3.put("date3", m1date3.substring(1));
			}
			date3Mapfl.put("m1", m1d3);

			// 模型2		插值
			Map<String, Object> m0 = new HashMap<String, Object>();
			m0.put("oneData", m0oneData);
			m0.put("twoData", m0twoData);
			m0.put("threeData", m0threeData);
			m0.put("rmsePow", Math.sqrt(m0rmsePow));
			m0.put("countOneData", m0countOneData);
			m0.put("countTwoData", m0countTwoData);
			m0.put("countThreeData", m0countThreeData);
			m0.put("countNomalData", m0countNomalData);
			if(m0oneData !=0 && m0twoData!=0 && m0threeData !=0)
				staticsMapfl.put("m0", m0);

			Map<String, Object> m0d1 = new HashMap<String, Object>();
			if (!m0date1.equals("")) {
				m0d1.put("date1", m0date1.substring(1));
			}
			// m0d1.put("date2", m0date2.substring(1));
			// m0d1.put("date3", m0date3.substring(1));
			date1Mapfl.put("m0", m0d1);

			Map<String, Object> m0d2 = new HashMap<String, Object>();
			if (!m0date2.equals("")) {
				m0d2.put("date2", m0date2.substring(1));
			}
			date2Mapfl.put("m0", m0d2);

			Map<String, Object> m0d3 = new HashMap<String, Object>();
			if (!m0date3.equals("")) {
				m0d3.put("date3", m0date3.substring(1));
			}
			date3Mapfl.put("m0", m0d3);

			staticsMapcd.put(monitor_type, staticsMapfl);
			date1Mapcd.put(monitor_type, date1Mapfl);
			date2Mapcd.put(monitor_type, date2Mapfl);
			date3Mapcd.put(monitor_type, date3Mapfl);

		}
		staticsMap.put(the_point, staticsMapcd);
		date1Map.put(the_point, date1Mapcd);
		date2Map.put(the_point, date2Mapcd);
		date3Map.put(the_point, date3Mapcd);
		}
		
		
		// 所有日期处理完
		// 统计批次的相关信息

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
		
		//sinTable中对应显示的统计数据
		map.clear();
		map.put("id", genId);
		map.put("dt", handleTime);
		map.put("pre_component", monitorStr);
//		map.put("det_type", decType_Sel);
		map.put("det_instr",tableNameStr);
		map.put("dam_no", dam_Sel);
		map.put("obser_no", pointIdString);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("staticsData", staticsData);// 统计数据
		map.put("date1Str", date1Str);
		map.put("date2Str", date2Str);
		map.put("date3Str", date3Str);
		
		single_predictionDao.add_singlepre(map);

		System.out.println("需要时间" + (System.currentTimeMillis() - st) / 1000);

		map.clear();

		JSONObject jsObj = new JSONObject();
		jsObj.put("preId", genId);
		response.setCharacterEncoding("utf-8");
		System.out.println(jsObj.toString());
		response.getWriter().write(jsObj.toString());
		}
		
		
//	}

	public ICommonPredictDao getCommonPredictDao() {
		return commonPredictDao;
	}

	public void setCommonPredictDao(ICommonPredictDao commonPredictDao) {
		this.commonPredictDao = commonPredictDao;
	}

	public ISINGLE_PREDICTIONDao getSingle_predictionDao() {
		return single_predictionDao;
	}

	public void setSingle_predictionDao(
			ISINGLE_PREDICTIONDao single_predictionDao) {
		this.single_predictionDao = single_predictionDao;
	}

	public IINS_SORTDao getIns_sortDao() {
		return ins_sortDao;
	}

	public void setIns_sortDao(IINS_SORTDao ins_sortDao) {
		this.ins_sortDao = ins_sortDao;
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
		String preId = (String) map.get("pre_id");
		Map<String, Object> resultMap = null;

		if (id == null) {
			return;
		}
		map.clear();
		map.put("pre_id", preId);
		map.put("tableName", tableName);
		map.put("instr_no", id);
		map.put("modelId", "2");

		long sTime = System.nanoTime() / 1000000L;

		if (id.contains("'")) {
			id = id.replaceAll("'", "''");
		}

		map.put("dt", dt);
		
		System.out.println("============插值=============");
		System.out.println("pre_id = " + preId);
		System.out.println("tableName = " + tableName);
		System.out.println("instr_no = " + id);
		System.out.println("modelId = 2");
		System.out.println("dt = " + dt);
		System.out.println("component =" + component);
		System.out.println("=========================");
		
		MonitorItemType itemType = monitorItemTypeDao
				.getItemTypeByTableName(tableName);
		
		if (!itemType.getR1().equals("0")) {
			map.put("monitor_type", "r1");
			System.out.println("处理r1");
			System.out.println(Arrays.toString(map.entrySet().toArray()));
			// 对R1进行处理
			resultMap = DataSinglePredictUtil.predictMethod2(commonPredictDao, map,
					anlys2);
			if (resultMap != null) {
				resultMap.put("tableName", tableName);
				resultMap.put("component", "r1");
				resultMap.put("prediction_id", preId);
				System.out.println("hahahha");
				try {
					commonPredictDao.add_res(resultMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if ((!itemType.getR2().equals("0"))) {
			map.put("monitor_type", "r2");
			System.out.println("处理r2");
			System.out.println(Arrays.toString(map.entrySet().toArray()));
			// 对R2进行插入处理
			resultMap = null;
			resultMap = DataSinglePredictUtil.predictMethod2(commonPredictDao, map,
					anlys2);
			if (resultMap != null) {
				resultMap.put("tableName", tableName);
				resultMap.put("component", "r2");
				resultMap.put("prediction_id", preId);
				System.out.println(Arrays.toString(resultMap.entrySet().toArray()));
				try {
					commonPredictDao.add_res(resultMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if ((!itemType.getR3().equals("0"))) {
			map.put("monitor_type", "r3");
			// 对R3进行插入处理
			resultMap = DataSinglePredictUtil.predictMethod2(commonPredictDao, map,
					anlys2);
			if (resultMap != null) {
				resultMap.put("tableName", tableName);
				resultMap.put("component", "r3");
				resultMap.put("prediction_id", preId);
				try {
					commonPredictDao.add_res(resultMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// }
		System.out.println(id + "方法2需要时间:"
				+ (System.nanoTime() / 1000000L - sTime) / 1000L);
		// }
	}

	private void method3(Map<String, Object> map, String dt, String tableName,
			String id, BestInterp useless1, expFitModel anlys2,
			poly2FitModel useless2, String component) {
		Map<String, Object> resultMap = null;
		
		String preId = (String) map.get("pre_id");
		// for (String id : pointList) {
		if (id == null) {
			return;
		}
		map.clear();
		map.put("pre_id", preId);
		map.put("tableName", tableName);
		map.put("instr_no", id);
		map.put("modelId", "3");

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
			resultMap = DataSinglePredictUtil.predictMethod3(commonPredictDao, map,
					anlys2);
			if (resultMap != null) {
				resultMap.put("tableName", tableName);
				resultMap.put("component", "r1");
				resultMap.put("prediction_id", preId);
				try {
					commonPredictDao.add_res(resultMap);
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
			resultMap = DataSinglePredictUtil.predictMethod3(commonPredictDao, map,
					anlys2);
			if (resultMap != null) {
				resultMap.put("tableName", tableName);
				resultMap.put("component", "r2");
				resultMap.put("prediction_id", preId);
				try {
					commonPredictDao.add_res(resultMap);
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
			resultMap = DataSinglePredictUtil.predictMethod3(commonPredictDao, map,
					anlys2);
			if (resultMap != null) {
				resultMap.put("tableName", tableName);
				resultMap.put("component", "r3");
				resultMap.put("prediction_id", preId);
				try {
					commonPredictDao.add_res(resultMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// }
		System.out.println(id + "方法3需要时间:"
				+ (System.nanoTime() / 1000000L - sTime) / 1000L);
		// }
	}

	private void method4(Map<String, Object> map, String dt, String tableName,
			String id, BestInterp useless1, expFitModel useless2,
			poly2FitModel anlys2, String component) {
		Map<String, Object> resultMap = null;
		
		String preId = (String) map.get("pre_id");
		// for (String id : pointList) {
		if (id == null) {
			return;
		}
		map.clear();
		map.put("pre_id", preId);
		map.put("tableName", tableName);
		map.put("instr_no", id);
		map.put("modelId", "4");

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
			resultMap = DataSinglePredictUtil.predictMethod4(commonPredictDao, map,
					anlys2);
			if (resultMap != null) {
				resultMap.put("tableName", tableName);
				resultMap.put("component", "r1");
				resultMap.put("prediction_id", preId);
				try {
					commonPredictDao.add_res(resultMap);
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
			resultMap = DataSinglePredictUtil.predictMethod4(commonPredictDao, map,
					anlys2);
			if (resultMap != null) {
				resultMap.put("tableName", tableName);
				resultMap.put("component", "r2");
				resultMap.put("prediction_id", preId);
				try {
					commonPredictDao.add_res(resultMap);
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
			resultMap = DataSinglePredictUtil.predictMethod4(commonPredictDao, map,
					anlys2);
			if (resultMap != null) {
				resultMap.put("tableName", tableName);
				resultMap.put("component", "r3");
				resultMap.put("prediction_id", preId);
				try {
					commonPredictDao.add_res(resultMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// }
		System.out.println(id + "方法4需要时间:"
				+ (System.nanoTime() / 1000000L - sTime) / 1000L);
		// }
	}

}
