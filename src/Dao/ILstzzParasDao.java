package Dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import Entity.LstzzParas;

public interface ILstzzParasDao {
	
	public List<LstzzParas> getParas(Map<String, Object> map);
	
	public void updateParas(Map<String, Object> map);
}
