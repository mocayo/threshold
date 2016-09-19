package Dao;

import java.util.List;
import java.util.Map;

import Entity.DEFLOG;

public interface IDefLogDao {
	/**
	 * 获取日志表日期，不重复
	 * @return
	 */
	public List<String> getLogDate();
	
	/**
	 * 根据日期，获取当前日期的日志信息
	 * @return
	 */
	public List<DEFLOG> getLogByDate(String time);
	
	/**
	 * 获取时间段日志信息
	 * @return
	 */
	public List<DEFLOG> getLogByTime(Map<String, String> map);//通过开始结束时间获取日志
}
