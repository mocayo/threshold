package Dao;

import java.util.List;
import java.util.Map;

import Entity.CommonPreHandleFinal;
import Entity.T_ZB_PL_FINAL;

public interface ICommonPreHandleFinalDao {

	public List<CommonPreHandleFinal> findPreHandleResult(Map<String, Object> map);// 查找是否存在该条记录

	public void updatePreHandleResult(Map<String, Object> map);   //若存在则更新该条记录

	public void insertPreHandleResult(Map<String, Object> map);   //若不存在则插入该条记录

	public void deletePreHandleFinalByBatchId(Map<String, String> map);  //通过批次Id删除该批次的数据

}
