package util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Dao.IMidCharacteristicDao;
import Dao.IMonitorItemTypeDao;
import Entity.MidCharacteristic;



public class LsCharacteristic extends Action{
	private IMidCharacteristicDao midCharacterDao;
	private IMonitorItemTypeDao monitorItemTypeDao;
	
	public IMidCharacteristicDao getMidCharacterDao() {
		return midCharacterDao;
	}

	public void setMidCharacterDao(IMidCharacteristicDao midCharacterDao) {
		this.midCharacterDao = midCharacterDao;
	}

	public IMonitorItemTypeDao getMonitorItemTypeDao() {
		return monitorItemTypeDao;
	}

	public void setMonitorItemTypeDao(IMonitorItemTypeDao monitorItemTypeDao) {
		this.monitorItemTypeDao = monitorItemTypeDao;
	}

	public void execute() throws IOException {
		List<String> tableNameList = monitorItemTypeDao.getTableNames();
		for (String tableName : tableNameList) {
			midCharacter(tableName);
		}
	}
	
	public void midCharacter(String tableName) {
		String sDate = "";
		String eDate = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName",tableName);
		map.put("monitor_type","r1");
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
			
			map.put("INSTR_NO",midTemp.getInstr_no());
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
			midCharacterDao.addMidChar(insMap);
		}
//		lsCharacteristic(tableName);
	}
	
	public void lsCharacteristic(String tableName) {
		String sDate = "";
		String eDate = "";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName",tableName);
		map.put("monitor_type","r1");
		midCharacterDao.addCharRes(map);
	}
	
	public void dynamicCharacteristic(){
		
	}

}
