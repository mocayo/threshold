package Dao;

import java.util.List;
import java.util.Map;

import Entity.T_PRE_RESULT;

public interface IPRERESULTDao {
	public List<T_PRE_RESULT> getAllResult();//获取含有该监测项的监测点
	
	public List<T_PRE_RESULT> getInfoById(String id);//获取含有该监测项的监测点
	
	public void updStatById(String id);//更新批次状态
	
	public List<T_PRE_RESULT> getResultByTime(Map<String, Object> map);//根据多个测点编号拿出对应的数据

	/**
	 * 添加一次处理记录
	 * @param map
	 * @return
	 */
	public void addPreResult(Map<String, Object> map);

	public List<T_PRE_RESULT> getResultByPoint(String point);//按测点查询预处理结果
	
	public void deletePreResult(String id);//按批次ID删除预处理批次
}
