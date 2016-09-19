/**
 * @author qihai
 * @time 2016年5月4日15:38:53
 */
package Dao;

import java.util.List;
import java.util.Map;

import Entity.LSTZZInfo;
import Entity.T_LSTZZ_RES;
import Entity.T_LSTZZ_RES_5;

public interface LSTZZInfoDao {
	
	public List<LSTZZInfo> getLSTZZInfo(Map<String,Object> map);
	
	public int getLSTZZInfoCount(Map<String,Object> map);
	
	public List<T_LSTZZ_RES> getLSTZZ_RES(Map<String,Object> map);
	
	public List<T_LSTZZ_RES_5> getLSTZZ_RES_5(Map<String,Object> map);
	
	public double getWaterLevel(String dt);
}
