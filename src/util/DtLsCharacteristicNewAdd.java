package util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Dao.IDtCharacteristicDao;
import Dao.IMonitorItemTypeDao;
import Entity.MidCharacteristic;
import Entity.MidDtCharacteristic;



public class DtLsCharacteristicNewAdd extends Action{
	private IDtCharacteristicDao dtCharacterDao;
	private IMonitorItemTypeDao monitorItemTypeDao;
	
	public IDtCharacteristicDao getDtCharacterDao() {
		return dtCharacterDao;
	}

	public void setDtCharacterDao(IDtCharacteristicDao dtCharacterDao) {
		this.dtCharacterDao = dtCharacterDao;
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
			dtMidCharacter(tableName);
		}
	}
	
	public void dtMidCharacter(String tableName) {
		String sDate = "";
		String eDate = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName",tableName);
		map.put("monitor_type","r1");
		dtCharacterDao.removeMidChar(map);
		List<MidDtCharacteristic> midList = dtCharacterDao.getDynamicCharP1(map);
		for (int i=0;i<midList.size();i++){
			MidDtCharacteristic midTemp = midList.get(i);
			Map<String, Object> insMap = new HashMap<String, Object>();
			if (midTemp.getInstr_no().contains("'")){
				String tmpStr = midTemp.getInstr_no();
				tmpStr = tmpStr.replaceAll("'", "''");
				midTemp.setInstr_no(tmpStr);
			}
			insMap.put("tableName",tableName);
			insMap.put("INSTR_NO",midTemp.getInstr_no());
			insMap.put("WL",midTemp.getWl());
			insMap.put("avgDV",midTemp.getAvgDV());
			insMap.put("minDV",midTemp.getMinDV());
			insMap.put("maxDV",midTemp.getMaxDV());
			
			map.put("INSTR_NO",midTemp.getInstr_no());
			map.put("WL",midTemp.getWl());
			map.put("avgDV",midTemp.getAvgDV());
			List<MidDtCharacteristic> minAVList = dtCharacterDao.getDynamicCharP2(map);
			List<MidDtCharacteristic> maxAVList = dtCharacterDao.getDynamicCharP3(map);
			if(minAVList.isEmpty()){
				insMap.put("minDAV",midTemp.getAvgDV());
			} else {
				MidDtCharacteristic minAvTemp = minAVList.get(0);
				insMap.put("minDAV",minAvTemp.getMinDAV());
			}
			if(maxAVList.isEmpty()){
				insMap.put("maxDAV",midTemp.getAvgDV());
			} else {
				MidDtCharacteristic maxAvTemp = maxAVList.get(0);
				insMap.put("maxDAV",maxAvTemp.getMaxDAV());
			}
			try {
				dtCharacterDao.addMidDynamic(insMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		dtLsCharacteristic(tableName);
	}
	
	public void dtLsCharacteristic(String tableName) {
		String sDate = "";
		String eDate = "";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName",tableName);
		map.put("monitor_type","r1");
		dtCharacterDao.addDynamicRes(map);
	}
	
}
