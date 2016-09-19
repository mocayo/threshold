package Dao;

import java.util.Map;

import Entity.LSSET;

public interface IConfHisValDao {
	
	public LSSET getConf();
	
	public int updateConf(Map<String,Object> map);
	
}
