package Dao;

import java.util.List;
import java.util.Map;

import Entity.PointValue;
import Entity.PREDICT_DATA2;
import Entity.T_ZB_PL;
//import Entity.CharacteristicEntity;
import Entity.MidCharacteristic;

public interface IMidCharacteristicDao {
	public List<MidCharacteristic> getCharDataP1(Map<String, Object> map);
	
	public List<MidCharacteristic> getCharDataP2(Map<String, Object> map);
	
	public List<MidCharacteristic> getCharDataP3(Map<String, Object> map);
	
	public void addMidChar(Map<String, Object> map);
	
	public void addCharRes(Map<String, Object> map);
	
	public void addCharRes2(Map<String, Object> map);
	
	public void addCharRes3(Map<String, Object> map);
	
	public void removeMidChar(Map<String, Object> map);
	
	public void updateMidChar(Map<String, Object> map);

	public List<PointValue> queryData(Map<String, Object> map);
}
