package Dao;

import java.util.Map;

import Entity.T_Water_Level;

public interface IT_Water_LevelDao {
	public T_Water_Level getWL_latest();//获取最新水位
	
	public T_Water_Level getWL(Map<String,Object> map);//获取指定日期的水位信息
	
	public void convertWL(String dt);

}
