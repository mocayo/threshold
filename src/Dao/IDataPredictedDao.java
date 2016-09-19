package Dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import Entity.LstzzPrint;

public interface IDataPredictedDao {
	
	public int isPredicted0(Map<String, Object> map);

	public int isPredicted1(Map<String, Object> map);
	
	public void addPredicted(Map<String, Object> map);

	public void updPredicted(Map<String, Object> map);
	
}
