package Dao;

import java.util.List;
import java.util.Map;

import Entity.W_CENodeMetMem_Current;
import Entity.W_CENodeMetMem_Log;

public interface IDataCollectionDao {
	
	public void addWorkState(Map<String, Object> map);
	
	public int queryWorkState(Map<String, Object> map);
	
	public void updWorkState(Map<String, Object> map);
}
