package Dao;

import java.util.List;
import java.util.Map;

import Entity.T_ZB_PL_ForeHandle;

public interface IT_ZB_PL_ForeHandleDao {
	public String getForeHandle_Time(String instr_no);//获取该点最大预处理时间
	
	public void modifyForeHandleResult(Map<String, Object> map);//人工修改预处理结果
	
	public List<String> getForeHandleModifyResult(Map<String, Object> map);//查询预处理修改时间
	
	public List<T_ZB_PL_ForeHandle> queryForeHandleModifyResult(String time);//查询预处理结果
	
	/**
	 * 根据测点编号和时间段查询结果X
	 * @param map
	 * @return
	 */
	public List<T_ZB_PL_ForeHandle> getDataXByIdTm(Map<String, Object> map);

	/**
	 * 根据测点编号和时间段查询结果Y
	 * @param map
	 * @return
	 */
	public List<T_ZB_PL_ForeHandle> getDataYByIdTm(Map<String, Object> map);
}
