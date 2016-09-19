package util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import Dao.ICommonPreHandleDao;
import Entity.CommonPreHandle;


public class PointInfoDao{
	
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

	// 获取点数据
	public JSONObject getPointData(String tableName, String pointID,
			String monitorItem, String startTime, String endTime,ICommonPreHandleDao commonPreHandleDao) {

		/*Timestamp startTimestamp = Timestamp.valueOf(startTime + " 00:00:00");
		Timestamp endTimestamp = Timestamp.valueOf(endTime + " 00:00:00");*/
		
		Timestamp startTimestamp = Timestamp.valueOf(startTime);
		Timestamp endTimestamp = Timestamp.valueOf(endTime);

		System.out.println("startTime:"+startTime);
		System.out.println("endTime:"+endTime);
		
		int days = (int) ((endTimestamp.getTime() - startTimestamp.getTime()) / 1000 / (24 * 3600)) + 1;
		System.out.println("days:"+days);
		String[] times = new String[days];
		double[] pointData = new double[days];
		times[0] = sf.format(startTimestamp);
		Timestamp currentTimestamp = new Timestamp(startTimestamp.getTime());
		int i = 0;
		int sum=0;//记录数据库中数据条数，如果少于天数50%，则返回空
		if(pointID.contains("'"))
			pointID = pointID.replaceAll("'", "''");
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("tableName",tableName);
		map.put("id",pointID);
		map.put("startTime",startTime);
		map.put("endTime",endTime);
		map.put("type",monitorItem);
		List<CommonPreHandle> dataList = commonPreHandleDao.getDataByIdTm(map);
		
		/*String sql = "SELECT DT," + monitorItem
				+ " FROM [LCRiver_xwdh_2].[dbo].[" + tableName
				+ "] where [INSTR_NO]='" + pointID.replaceAll("'", "''") + "' and [DT] between '"
				+ startTime + "' and '" + endTime + "' order by dt";*/
		try {
//			stmt = conn.prepareStatement(sql);
//			rs = stmt.executeQuery();
			JSONObject json = new JSONObject();
			CommonPreHandle tmp ;
			Timestamp timestamp ;
			for (int j = 0;j < dataList.size();j++) {
				tmp = dataList.get(j);
				timestamp = tmp.getDt();
				while (!times[i].equals(sf.format(timestamp))) {
					pointData[i]=Double.NaN;
					currentTimestamp = new Timestamp(
							currentTimestamp.getTime() + 24 * 3600 * 1000);
					try {
						times[++i] = sf.format(currentTimestamp);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("id:"+pointID);
					}
				}
				if(monitorItem.equalsIgnoreCase("r1")){
					pointData[i] = tmp.getR1();
				}else if(monitorItem.equalsIgnoreCase("r2")){
					pointData[i] = tmp.getR2();
				}else if(monitorItem.equalsIgnoreCase("r3")){
					pointData[i] = tmp.getR3();
				}
				sum++;

				currentTimestamp = new Timestamp(
						currentTimestamp.getTime() + 24 * 3600 * 1000);
				if(i+1 < days)
					times[++i] = sf.format(currentTimestamp);
			}
			if(sum<days/2)return null;
			if(i==0)return null;
			endTimestamp=new Timestamp(endTimestamp.getTime()+24 * 3600 * 1000);
			while (!times[i].equals(sf.format(endTimestamp))) {
				pointData[i]=Double.NaN;
				currentTimestamp = new Timestamp(
						currentTimestamp.getTime() + 24 * 3600 * 1000);
				if((i+1)==times.length)break;
				times[++i] = sf.format(currentTimestamp);
			}
			json.put("times", times);
			json.put("pointData", pointData);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dataList = null;
			map.clear();
			pointData = null;
			times = null;
		}
		return null;
	}

	//将预处理结果存入表中
	public void insertIntoTable(String tableName, String pointID,
			String[] times, String monitorItem, String[] original,
			double[] data, double[] bool,ICommonPreHandleDao commonPreHandleDao) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", tableName);
		map.put("instr_no", pointID);
		map.put("component", monitorItem);

		// 循环每条数据
		for (int i = 0; i < times.length - 1; i++) {
			if (times[i] == null)
				continue;
			// 获取参数数组
			map.put("DT", times[i]);
			map.put("original", original[i]);
			map.put("prehandle", data[i]);
			map.put("mark", bool[i] == 0.0 ? 0 : 1);
			try {
				commonPreHandleDao.add_auto(map);
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
			// System.out.println("导入数据：" + index);
		}
		map.clear();
	}

}
