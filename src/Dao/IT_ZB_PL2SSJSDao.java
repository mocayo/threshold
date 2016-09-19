package Dao;

import java.util.List;
import java.util.Map;

import Entity.T_ZB_PL2;

public interface IT_ZB_PL2SSJSDao
{
	public List<T_ZB_PL2> getAllT_ZB_PL2SSJS(Map<String, Object> map);

	public List<String> getAllInstr_no();
	
}
