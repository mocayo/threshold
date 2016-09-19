package Dao;

import java.util.List;
import java.util.Map;

import Entity.CharacteristicEntity;
import Entity.MidDtCharacteristic;

public interface IDtCharacteristicDao {
	public List<MidDtCharacteristic> getDynamicCharP1(Map<String, Object> map);
	
	public List<MidDtCharacteristic> getDynamicCharP2(Map<String, Object> map);
	
	public List<MidDtCharacteristic> getDynamicCharP3(Map<String, Object> map);
	
	public void addMidDynamic(Map<String, Object> map);
	
	public void addDynamicRes(Map<String, Object> map);
	
	public void removeMidChar(Map<String, Object> map);
	
	public void updateMidChar(Map<String, Object> map);
}
