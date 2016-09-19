package util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.json.JSONObject;

import Dao.ICommonPreHandleBatchDao;
import Dao.ICommonPreHandleDao;
import Dao.ICommonPreHandleFinalDao;
import Dao.ICommonPreHandleResultDao;
import Dao.IDEFINSSORTDao;
import Dao.IDataCollectionDao;
import Dao.IModelPointDao;
import Dao.IMonitorItemTypeDao;
import Entity.CommonPreHandle;
import Entity.CommonPreHandleFinal;
import Entity.CommonPreHandleResult;
import Entity.DEFINSSORT;
import Entity.ModelPoint;
import Entity.MonitorItemType;
import PreTreatNewset.PreTreat;

public class PreHandleAutomatic {

//	private ICommonPreHandleResultDao commonPreHandleResultDao;
//	private ICommonPreHandleBatchDao commonPreHandleBatchDao;
	private ICommonPreHandleDao commonPreHandleDao;
	private IMonitorItemTypeDao monitorItemTypeDao;
//	private IDEFINSSORTDao definssortDao;
//	private ICommonPreHandleFinalDao commonPreHandleFinalDao;
	private IModelPointDao modelPointDao;
	private IDataCollectionDao dataCollectionDao;
	
	public PreHandleAutomatic(ICommonPreHandleDao commonPreHandleDao,IMonitorItemTypeDao monitorItemTypeDao,IModelPointDao modelPointDao,IDataCollectionDao dataCollectionDao) {
		this.commonPreHandleDao = commonPreHandleDao;
		this.monitorItemTypeDao = monitorItemTypeDao;
		this.modelPointDao = modelPointDao;
		this.dataCollectionDao = dataCollectionDao;
	}
	
	public void preHandleAll(String pointTime) throws Exception {
		System.out.println("处理所有数据");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stateDate",pointTime);
		map.put("stateName", "prehandle");
		map.put("stateStr", "0");
		int countCaculated = 0;
		/*try {
			int countState = dataCollectionDao.queryWorkState(map);
			if(countState == 0){
				dataCollectionDao.addWorkState(map);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} */
		PreHandle preHandle = new PreHandle();
		PointInfoDao pointInfoDao = new PointInfoDao();
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat pointsf = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = sf.parse(pointTime+" 00:00:00");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sDate);
		/*calendar.set(Calendar.DAY_OF_MONTH, calendar
				.get(Calendar.DAY_OF_MONTH) + 1);
		String eTime = pointsf.format(calendar.getTime());*/
		calendar.set(Calendar.DAY_OF_MONTH, calendar
				.get(Calendar.DAY_OF_MONTH) - 10);
		String sTime = pointsf.format(calendar.getTime());
		
		/*Date today = new Date();
		
		
		String handleTime = sf.format(today);
		String pointTime = pointsf.format(today);//处理该时间段的点的数据，从今天0点到23点
		*/
		/*String startTime = "2016-01-28";
		String endTime = "2016-03-15";*/
		
		String startTime = sTime+" 00:00:00";
//		String endTime = eTime+" 00:00:00";
		String endTime = pointTime+" 23:59:59";
		
		List<String> tables = monitorItemTypeDao.getTableNames();// 获取所有的表
		JSONObject json = null;
		JSONObject json2 = null;
		double[] data ;
		PreTreat preTreat = new PreTreat();
		for (String tableInfo : tables) {
			List<ModelPoint> iswhereList = modelPointDao
					.getCaculatePoints(tableInfo);
				// 根据表名得到所有测点
			if (iswhereList.size() == 0)
				continue;
			countCaculated += iswhereList.size();
			String[] pointList = new String[iswhereList.size()];
			for (int i = 0; i < iswhereList.size(); i++) {
				pointList[i] = iswhereList.get(i).getInstr_no().trim();
			}
			
				MonitorItemType itemType = monitorItemTypeDao
						.getItemTypeByTableName(tableInfo);
				List<String> monitorItem = new ArrayList<String>();// 获取该表的监测项
				if (!itemType.getR1().equals("0")){
						monitorItem.add("r1");
				}
				if (!itemType.getR2().equals("0")){
					monitorItem.add("r2");
				}
				if (!itemType.getR3().equals("0")){
					monitorItem.add("r3");
				}
				for (int i = 0; i < monitorItem.size(); i++) {
					String monitorItemString = monitorItem.get(i);
					for (String point : pointList) {
						json = pointInfoDao.getPointData(
								tableInfo, point, monitorItemString,
								startTime, endTime,commonPreHandleDao);// 获取该分量的所有数据
						if (null == json)
							continue;
						try {
							data = (double[]) json.get("pointData");// 原始数据
							if (data.length <= 0)
								continue;
							System.out.println("Before prehandle point:"+point);
							json2 = preHandle.preHandle(data,preTreat);// 预处理后的数据
							json2.put("times", (String[]) json.get("times"));// 时间
							json2.put("originalData", data);
							// 将预处理结果写入数据库对应表中
							/* preHandleDao.truncateTable(tableInfo.getTable()); */
							String[] input = new String[data.length];
							for (int i1 = 0; i1 < data.length; i1++) {
								if (Double.isNaN(data[i1]))
									input[i1] = "";
								else
									input[i1] = String.valueOf(data[i1]);
							}
							System.out.println("After prehandle point:"+point);
							pointInfoDao.insertIntoTable(tableInfo,
									point, (String[]) json.get("times"),
									monitorItemString, input,
									(double[]) json2.get("data"),
									(double[]) json2.get("bool"),commonPreHandleDao);
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							json = null;
							json2 = null;
							data = null;
						}
					}
				}
				iswhereList = null;
				pointList = null;
				System.out.println("-------表" + tableInfo
						+ "结束--------");

		}
		
		PreTreat.disposeAllInstances();
		preTreat = null;
		map.clear();
		map.put("stateDate",pointTime);
		map.put("stateName", "prehandle");
		map.put("stateStr", "1");
		map.put("countCaculated", countCaculated);
		Date tmp = new Date();
		String updTime = sf.format(tmp);
		map.put("updTime",updTime);
		/*try {
			dataCollectionDao.updWorkState(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		System.out.println("预处理结束!");
		System.gc();
	}


/*	public ICommonPreHandleResultDao getCommonPreHandleResultDao() {
		return commonPreHandleResultDao;
	}


	public void setCommonPreHandleResultDao(
			ICommonPreHandleResultDao commonPreHandleResultDao) {
		this.commonPreHandleResultDao = commonPreHandleResultDao;
	}


	public ICommonPreHandleBatchDao getCommonPreHandleBatchDao() {
		return commonPreHandleBatchDao;
	}


	public void setCommonPreHandleBatchDao(
			ICommonPreHandleBatchDao commonPreHandleBatchDao) {
		this.commonPreHandleBatchDao = commonPreHandleBatchDao;
	}*/


	public ICommonPreHandleDao getCommonPreHandleDao() {
		return commonPreHandleDao;
	}


	public void setCommonPreHandleDao(ICommonPreHandleDao commonPreHandleDao) {
		this.commonPreHandleDao = commonPreHandleDao;
	}


	public IMonitorItemTypeDao getMonitorItemTypeDao() {
		return monitorItemTypeDao;
	}


	public void setMonitorItemTypeDao(IMonitorItemTypeDao monitorItemTypeDao) {
		this.monitorItemTypeDao = monitorItemTypeDao;
	}


/*	public IDEFINSSORTDao getDefinssortDao() {
		return definssortDao;
	}


	public void setDefinssortDao(IDEFINSSORTDao definssortDao) {
		this.definssortDao = definssortDao;
	}
*/

/*	public ICommonPreHandleFinalDao getCommonPreHandleFinalDao() {
		return commonPreHandleFinalDao;
	}


	public void setCommonPreHandleFinalDao(
			ICommonPreHandleFinalDao commonPreHandleFinalDao) {
		this.commonPreHandleFinalDao = commonPreHandleFinalDao;
	}
*/

	public IModelPointDao getModelPointDao() {
		return modelPointDao;
	}


	public void setModelPointDao(IModelPointDao modelPointDao) {
		this.modelPointDao = modelPointDao;
	}

	public IDataCollectionDao getDataCollectionDao() {
		return dataCollectionDao;
	}

	public void setDataCollectionDao(IDataCollectionDao dataCollectionDao) {
		this.dataCollectionDao = dataCollectionDao;
	} 
	
}
