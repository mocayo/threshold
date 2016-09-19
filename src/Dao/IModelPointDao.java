package Dao;

import java.util.List;
import java.util.Map;

import Entity.ModelPoint;

public interface IModelPointDao {
	
	public void addModelPoint(Map<String, Object> map);
	
	public ModelPoint getModelPoint(Map<String, Object> map);
	
	public List<ModelPoint> getModelPointByPage(Map<String, Object> map);
	
	public List<ModelPoint> getModelPointByType(String PointType); 
	
	public void updateModelPoint(Map<String, Object> map);
	
	public int countRecords(String PointType);
	
	public List<ModelPoint> getPointComponent(Map<String, Object> map);
	
	public List<ModelPoint> getCaculatePoints(String tableName);
	
	public List<ModelPoint> getPointsInfo(String mapId);
	
	public List<String> getMapIds(String basicId);
	
	public String getComponentByPoint(Map<String, Object> map);

}
