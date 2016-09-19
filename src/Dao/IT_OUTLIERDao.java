package Dao;

import java.util.List;
import java.util.Map;

import Entity.T_OUTLIER;

public interface IT_OUTLIERDao {
	public List<T_OUTLIER> dataOutlier(Map<String, Object> map);//根据坝段查询多个测点
	
	public int countOutlier();
}
