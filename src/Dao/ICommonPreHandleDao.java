package Dao;

import java.util.List;
import java.util.Map;

import Entity.CommonPreHandle;

public interface ICommonPreHandleDao {

	List<CommonPreHandle> getDataByIdTm(Map<String, Object> map);
	
	public void add_auto(Map<String, Object> map);
	
	public int countPre(Map<String, Object> map);
}
