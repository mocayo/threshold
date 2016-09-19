package Dao;

import java.util.List;
import java.util.Map;

import Entity.ThresholdConf;

public interface IThresholdConfDao {
	public List<ThresholdConf> getConfByItem(Map<String,String> map);
	
	public int updateConf(Map<String,String> map);
	
	public int addConf(Map<String,String> map);
	
	public int delConf(Map<String,String> map);
	
	public String getIndexErrorRate();
	
	public List<String> getAllTableName();
	
	public void addTableField(Map<String,String> map);
	
	public int delData(Map<String,String> map);

}
