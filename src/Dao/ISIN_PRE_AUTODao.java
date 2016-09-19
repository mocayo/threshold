/**
 * @author qihai
 * @time 2016年5月19日11:13:37
 */
package Dao;

import java.util.List;
import java.util.Map;

import Entity.AutoPreInfo2;
import Entity.T_SIN_PRE;
import Entity.T_SIN_PRE_AUTO;
import Entity.AutoPreInfo;;

public interface ISIN_PRE_AUTODao {
	public void insertWorkState(Map<String,Object> map);//插入工作状态
	
	public int queryDataPreState(Map<String,Object> map);//工作状态
	
	public int checkWorkstate(Map<String,Object> map);//检测工作状态
	
	public List<AutoPreInfo> getAllData(Map<String, Object> map);//获取含有该监测项的监测点
	
	public List<AutoPreInfo> getAllDataByPage(Map<String, Object> map);
	
	public List<AutoPreInfo2> getAllDataByPage2(Map<String, Object> map);

	public int getAllDataCount(Map<String, Object> map);

	public void add_res(Map<String, Object> predictMethod);
	
	public void add_res_r1(Map<String, Object> predictMethod);
	
	public void update_res_r1(Map<String, Object> predictMethod);

	public void add_res_r2(Map<String, Object> predictMethod);

	public void update_res_r2(Map<String, Object> predictMethod);

	public void add_res_r3(Map<String, Object> predictMethod);

	public void update_res_r3(Map<String, Object> predictMethod);

	public void clearData(Map<String, Object> map);

	public List<T_SIN_PRE_AUTO> getDataByIdTm(Map<String, Object> map);

	public List<T_SIN_PRE_AUTO> getDataByIdTmR1(Map<String, Object> map);
	
	public List<T_SIN_PRE_AUTO> getDataByIdTmR2(Map<String, Object> map);
	
	public List<T_SIN_PRE_AUTO> getDataByIdTmR3(Map<String, Object> map);
	
	public List<T_SIN_PRE_AUTO> getDataToExcel(Map<String, Object> map);

	//获取画图数据
	public List<T_SIN_PRE_AUTO> getDrawChartData(Map<String, Object> map);
	
	public List<T_SIN_PRE> get2Pre(Map<String, Object> map);
	
	public List<T_SIN_PRE_AUTO> getR1(Map<String, Object> map);
	
	public List<T_SIN_PRE_AUTO> getR2(Map<String, Object> map);
	
	public void updPre_r1(Map<String, Object> predictMethod);

	public void updPre_r2(Map<String, Object> predictMethod);

	public List<AutoPreInfo2> getTestAutoInfo();

}

/*public interface ISIN_PRE_AUTODao {
	public List<T_SIN_PRE_AUTO> getAllData(Map<String, Object> map);//获取含有该监测项的监测点

	public void add_res_r1(Map<String, Object> predictMethod);

	public void add_res_r2(Map<String, Object> predictMethod);

	public void update_res_r2(Map<String, Object> predictMethod);

	public void add_res_r3(Map<String, Object> predictMethod);

	public void update_res_r3(Map<String, Object> predictMethod);

	public List<T_SIN_PRE_AUTO> getDataByIdTm(Map<String, Object> map);
}
*/