package Dao;


import java.util.List;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import Entity.T_ZB_PL_RES;
import Entity.PREDICT_DATA;
import Entity.T_ZB_PL_RES;

public interface IT_ZB_PL_ResDao {
	
	/**
	 * 添加异常值检测结果_x
	 * @param map
	 */
	public void add_pl_x_res(Map<String, Object> map);
	
	/**
	 * 添加异常值检测结果_y
	 * @param map
	 */
	public void add_pl_y_res(Map<String, Object> map);
	
	/**
	 * 更新异常值检测结果_y
	 * @param map
	 */
	public void update_pl_y_res(Map<String, Object> map);
	
	/**
	 * 得到一个批次处理记录条数
	 * @param result_id
	 * @return
	 */
	public int getAllCountByResultId(String result_id);
	
	/**
	 * 得到一个批次处理异常记录条数
	 * @param result_id
	 * @return
	 */
	public int getExceptionCountByResultId(String result_id);
	
	/**
	 * 得到一个批次处理正常记录条数
	 * @param result_id
	 * @return
	 * @author Tony-think
	 */
	public int getNomoralCountByResultId(String result_id);
	
	/**
	 * 通过批次ID查询所有预处理结果
	 * @param result_id
	 * @return
	 * @author Tony-think
	 */
	public List<T_ZB_PL_RES> getAllByResultId(Map<String, Object> map);
	
	/**
	 * 通过批次ID查询所有正常预处理结果
	 * @param result_id
	 * @return
	 * @author Tony-think
	 */
	public List<T_ZB_PL_RES> getTByResultId(Map<String, Object> map);
	
	/**
	 * 通过批次ID查询所有异常预处理结果
	 * @param result_id
	 * @return
	 * @author Tony-think
	 */
	public List<T_ZB_PL_RES> getFByResultId(Map<String, Object> map);
	
	/**
	 * 通过主键来改变人工处理状态
	 * @param map
	 */
	public void handleUpdate(Map<String,Object> map);
	
	/**
	 * 根据id和时间取得预测所需 水位数据和 X分量
	 * @param map
	 * @return
	 */
	public List<PREDICT_DATA> getDataXByIdTm(Map<String, Object> map);
	
	/**
	 * 根据id和时间取得预测所需 水位数据和 X分量 实时
	 * @param map
	 * @return
	 */
	public List<PREDICT_DATA> getDataXByIdTm2(Map<String, Object> map);
	
	/**
	 * 根据id和时间取得预测所需 水位数据和 Y分量
	 * @param map
	 * @return
	 */
	public List<PREDICT_DATA> getDataYByIdTm(Map<String, Object> map);
	
	/**
	 * 根据id和时间取得预测所需 水位数据和 Y分量 实时
	 * @param map
	 * @return
	 */
	public List<PREDICT_DATA> getDataYByIdTm2(Map<String, Object> map);

	/**
	 * 根据id和时间段取得时间列表
	 * @param map
	 * @return
	 */
	public List<String> getTimeListByIdTm(Map<String, Object> map);

	/**
	 * 获取测点预测最新时间
	 */
	public String getLatestTime(String instr_no);
	
	/*
	 *通过批次ID获取所有预处理结果
	 */
	public List<T_ZB_PL_RES> getAllResultByResultId(String resultid);
}
