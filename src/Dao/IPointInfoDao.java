package Dao;

import java.util.List;
import java.util.Map;

public interface IPointInfoDao {
	public List<String> getIsWhereByMonitorItem(String monitorItem);//通过监测项获取坝段种类

	public List<String> getPoint(Map<String, String> map);    //通过表名，坝段名获取所有的测点
	
	public String getTable(Map<String, Object> map);
	
	//基本部位
	public List<String> getBasicItem();
	
	//监测项目
	public List<String> getMonitor(Map<String, Object> map);
	
	//仪器类型
	public List<String> getInstrType(Map<String, Object> map);
	
	//获取测点
	public List<String> getObservePoints(Map<String, Object> map);
	
	//测点分量
	public String getComponent(Map<String, Object> map);
	
}
