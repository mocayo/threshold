package Dao;

import java.util.List;
import java.util.Map;

import Entity.T_ZB_PL;
import Entity.T_ZB_PZ;

public interface IT_ZB_PZDao {
	public List<T_ZB_PL> singlePointScreening(String iswhere);//根据坝段查询多个测点
	
	public List<T_ZB_PL> multiPointScreening(Map<String, Object> map);//根据多个测点编号拿出对应的数据
//	public List<T_ZB_PZ> multiPointScreening(Map<String, Object> map);//根据多个测点编号拿出对应的数据
}
