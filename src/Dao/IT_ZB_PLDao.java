package Dao;

import java.util.List;
import java.util.Map;

import Entity.T_ZB_PL;

public interface IT_ZB_PLDao {
	/**
	 * 根据坝段查询多个测点
	 * @param map
	 * @return
	 */
	public List<T_ZB_PL> getPointPL(Map<String, String> map);
	
	/**
	 * 根据多个测点编号拿出对应的数据
	 * @param map
	 * @return
	 */
	public List<T_ZB_PL> getPointDataPL(Map<String, Object> map);
	
	/**
	 * 根据预测批次id查询预测结果
	 * @param map
	 * @return
	 */
	public List<T_ZB_PL> resultById(String preId);
	
	public List<T_ZB_PL> resultByParas(Map<String, Object> map);
	
	public List<T_ZB_PL> getExceptionDetial(Map<String, Object> map);
	
	public int countResultById(String preId);
	
	public int countResultByParas(Map<String, Object> map);
	
	/**
	 * 添加预测结果_x
	 * @param map
	 */
	public void add_pl_x_res(Map<String, Object> map);
	
	/**
	 * 添加预测结果_y
	 * @param map
	 */
	public void add_pl_y_res(Map<String, Object> map);
	
	
	/**
	 * 查询时间段内的中间结果
	 * @param map
	 */
	public List<String> getMidModel(String prediction_id);
	
}
