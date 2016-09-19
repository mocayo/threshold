package Dao;

import java.util.List;
import java.util.Map;

import Entity.T_ZB_PL_FINAL;

public interface IT_ZB_PL_FINALDao {

	public void updateResult(Map<String, Object> map);  //更新最终表

	public List<T_ZB_PL_FINAL> findResult(Map<String, Object> map);  //寻找是否存在该条数据

	public void insertResult(Map<String, Object> map);  //插入该条数据

}
