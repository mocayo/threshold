package Dao;

import java.util.List;
import java.util.Map;

import Entity.PointDetailedResultInfo;
import Entity.PointError;

public interface PointDetailedResultDao {

	public List<PointDetailedResultInfo> getPointDetailedResultInfo(Map<String,Object> map);
	
	public int getPointDetailedResultInfoCount(Map<String,Object> map);
	
	public List<PointDetailedResultInfo> getErrorPointsByPage(Map<String,Object> map);
	
	public List<PointDetailedResultInfo> getErrorPoints(Map<String,Object> map);
	
	public List<PointDetailedResultInfo> getKeyPoints(Map<String,Object> map);
	
	public int getErrorPointsCount(Map<String,Object> map);
	
	public int getPointModelIdCount(Map<String,Object> map);
	
	public String getMaxDate();
	
	public List<PointError> getErrateByPoint(Map<String,Object> map);
	
	public void addErrorResultByDay(Map<String,Object> map);
	
	public int checkErrorResultByDay(Map<String,Object> map);
	
	public void delErrorResultByDay(String day);
	
	public List<String> getErrorResultByDay(Map<String,Object> map);
	
	public int getErrorResultTotalByDay(Map<String,Object> map);
}
