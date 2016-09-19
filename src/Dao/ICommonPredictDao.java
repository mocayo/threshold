package Dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import Entity.CommonPredict;
import Entity.PREDICT_DATA;
import Entity.PREDICT_DATA2;

public interface ICommonPredictDao {

	/**
	 * 得到时间列表
	 * 
	 * @param map
	 * @return
	 */
	public List<String> getTimeListByIdTm(Map<String, Object> map);
	
	/**
	 * 得到平均值 7天的
	 */
	public Float getAvgValue7(Map<String, Object> map);

	/**
	 * 得到预处理之后的数据
	 * 
	 * @param map
	 * @return
	 */
	public List<PREDICT_DATA> getDataByIdTm(Map<String, Object> map);

	public List<PREDICT_DATA2> getDataByIdTm2(Map<String, Object> map);

	public List<PREDICT_DATA> getDataByIdTm3(Map<String, Object> map);

	public List<PREDICT_DATA2> getDataByIdTm4(Map<String, Object> map);

	/**
	 * 得到真实值
	 * 
	 * @param map
	 * @return
	 */
	public List<PREDICT_DATA> getRealDataByIdTm(Map<String, Object> map);
	
	public List<CommonPredict> getRealAllData(Map<String, Object> map);
	
	/**
	 * 添加预测结果
	 * 
	 * @param map
	 */
	public void add_res(Map<String, Object> map);

	/**
	 * 更新结果
	 */
	public void update_res(Map<String, Object> map);

	public Integer getStatusByIdTm(Map<String, Object> map);

	public Double getPreDataByIdTm(Map<String, Object> map);

	/**
	 * 根据主键得到对应的项
	 * 
	 * @param map
	 * @return
	 */
	public List<CommonPredict> getDataByKeys(Map<String, Object> map);

	/**
	 * 查询中间结果
	 * 
	 * @param monitor_type
	 * @param prediction_id
	 * @return
	 */
	public List<String> getMidModel(Map<String, Object> map);

	/**
	 * 查询自动预测中的数据
	 * 
	 * @param map
	 * @return
	 */
	public List<String> getRsme(Map<String, Object> map);

	/**
	 * 查询模型2的相对误差
	 * 
	 * @param map
	 * @return
	 */
	public List<String> getRsme0(Map<String, Object> map);

	/**
	 * 查询模型3的相对误差
	 * 
	 * @param map
	 * @return
	 */
	public List<String> getRsme1(Map<String, Object> map);

	/**
	 * 查询模型4的相对误差
	 * 
	 * @param map
	 * @return
	 */
	public List<String> getRsme2(Map<String, Object> map);

	/**
	 * 根据批次号查询记录
	 * 
	 * @param prediction_id
	 * @return
	 */
	public List<CommonPredict> resultById(Map<String, Object> mpici);

	/**
	 * 根据批次号查询记录（分页）
	 * 
	 * @param prediction_id
	 * @return
	 */
	public List<CommonPredict> resultFYById(Map<String, Object> mpici);

	// public List<CommonPredict> resultByParas(Map<String, Object> map);
	//
	public int countResultById(Map<String, Object> resParas);

	//
	/**
	 * 查询问题记录
	 * 
	 * @param map
	 * @return
	 */
	public List<CommonPredict> getExceptionDetial(Map<String, Object> map);

	/**
	 * 查询模型2的结果
	 * 
	 * @param mpici
	 * @return
	 */
	public List<CommonPredict> resultsM0(Map<String, Object> mpici);

	/**
	 * 查询模型3的结果
	 * 
	 * @param mpici
	 * @return
	 */
	public List<CommonPredict> resultsM1(Map<String, Object> mpici);

	/**
	 * 查询模型4的结果
	 * 
	 * @param mpici
	 * @return
	 */
	public List<CommonPredict> resultsM2(Map<String, Object> mpici);

	public Collection<? extends CommonPredict> resultById2(
			Map<String, Object> map);

	public void addRes_auto(Map<String, Object> map);

	public void addRes(Map<String, Object> map);
	
	public void updRes(Map<String, Object> map);
	
	public List<PREDICT_DATA2> getWL(Map<String, Object> map);
	
	public Double getRealR1(Map<String, Object> map);
	
	public Double getRealR2(Map<String, Object> map);
	
	public String getWLMaxDate();
	
	/**
	 * soda & qihai
	 */
	public String getCurrentTDay();
	
	public void addCurrentTDay(String day);
}
