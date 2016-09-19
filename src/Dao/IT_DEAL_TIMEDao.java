package Dao;

import java.util.List;
import java.util.Map;

import Entity.T_DEAL_TIME;

public interface IT_DEAL_TIMEDao {
	public List<T_DEAL_TIME> dataPreprocess(Map<String, Object> map);//根据坝段查询多个测点
	
	public int countPreDate();
}
