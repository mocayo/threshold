package Dao;

import java.util.List;
import java.util.Map;

import Entity.CommonPreHandleResult;

public interface ICommonPreHandleResultDao {

	public void add_res(Map<String, Object> map);   //增加预处理结果(增加R1值)

	public void update_res(Map<String, Object> map);//更新与处理结果(增加R2值)

	public int getAllCountByBatchId(Map<String, Object> map);//获取该批次数量

	public int getExceptionCountByBatchId(Map<String, Object> map);//获取该批次错误数量

	public List<CommonPreHandleResult> getAllResultByResultId(Map<String, Object> map);//通过批次ID获取所有预处理记录
	
	public List<CommonPreHandleResult> getAllPreHandleResByBatchId(Map<String, Object> map);//通过批次ID获取表预处理记录

	public List<CommonPreHandleResult> getNormalPreHandleResByBatchId(
			Map<String, Object> map);     //通过批次ID获取表正常处理记录

	public void deletePreHandleResultByBatchId(Map<String, String> map);//通过批次Id删除该批次的结果

	public int getNormalCountByBatchId(Map<String, Object> map);   //通过批次Id获取正常记录数量

	public List<CommonPreHandleResult> getExceptionPreHandleResByBatchId(
			Map<String, Object> map);     //通过批次Id获取异常记录

	public void modifyPreHandleRes(Map<String, Object> map);   //修改测点预处理分量的结果以及总结果

	public CommonPreHandleResult getPreHandleResByMap(Map<String, Object> map);  //通过联合主键获取给条记录

}
