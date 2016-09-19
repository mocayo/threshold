package Dao;

import java.util.List;
import java.util.Map;

import Entity.DEFINSSORT;

public interface IDEFINSSORTDao extends IDefLogDao {
	public List<String> getDesignCode(int type);//获取含有该监测项的监测点

	public List<DEFINSSORT> getPoint(Map<String, String> map);//根据监测项和坝段获取监测点
	
	public List<DEFINSSORT> getPointByTableName(String tableName);//根据表名获取所有的点

	public List<DEFINSSORT> getImportantPoint(String tableName);//根据表名获取所有重要测点

	public List<DEFINSSORT> getImportantPointByPoint(String tableName);//根据表名获取所有重要测点
}
