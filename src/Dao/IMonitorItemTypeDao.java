package Dao;

import java.util.List;

import Entity.MonitorItemType;

public interface IMonitorItemTypeDao {

	public MonitorItemType getItemTypeByTableName(String tableName); //通过表名获取监测项类型

	//得到所有表名
	public List<String> getTableNames();

}
