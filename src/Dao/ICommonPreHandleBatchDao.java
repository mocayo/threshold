/**
 * @author qihai
 * @time 2016年5月17日14:18:13
 */
package Dao;

import java.util.List;
import java.util.Map;

import Entity.AutoPreHandleInfo;
import Entity.CommonPreHandleBatch;

public interface ICommonPreHandleBatchDao {

	public void addPreResult(Map<String, Object> map);  //增加批次

	public List<CommonPreHandleBatch> getBatchInfoByBatchId(String batchId);  //通过批次ID获取该批次

	public List<CommonPreHandleBatch> getAllPreHandleBatch();  //获取所有预处理批次结果
	
	public List<CommonPreHandleBatch> getAllPreHandleBatchBypage(Map<String, Object> map);  //获取所有预处理批次结果
	
	public int getPreHandleCount();//获取预处理批次记录总数
	
	public int getPreHandleTimeCount(Map<String, Object> map);//获取预处理批次记录总数by time
	
	public int getPreHandlePointCount(Map<String, Object> map);//获取预处理批次记录总数by point

	public String getTableNameByBatchId(String batchId);   //通过批次Id获取表名

	public void deletePreHandleBatchByBatchId(String batchId);    //通过批次ID删除该批次          

	public void updateBatchStateByBatchId(String batchId);   //修改该批次的状态为已经确认

	public List<CommonPreHandleBatch> getPreHandleResultByTime(Map<String, Object> map); //用时间段查询预处理批次

	public List<CommonPreHandleBatch> getPreHandleResultByPoint(Map<String, Object> map);  //根据测点查询预处理批次
	
	public List<CommonPreHandleBatch> getPreHandleResultByTimeByPage(Map<String, Object> map); //用时间段查询预处理批次(分页)
	
	public List<AutoPreHandleInfo> getPreHandleResultByPointByPage(Map<String, Object> map);  //根据测点查询预处理批次(分页)
	
	//获取原始值和预处理之后的值
	public List<AutoPreHandleInfo> getPreHandleResult2(Map<String, Object> map);
}
