package Dao;

import java.util.List;
import java.util.Map;

import Entity.MODEL_PARA;

public interface IMODEL_PARADao {
	public List<MODEL_PARA> parameterQuery(Map<String, Object> map);//模型参数查询
	
	public List<MODEL_PARA> sinForcast(Map<String, Object> map);
	
	public int countPara (Map<String,Object> map);
	
	public void modifymodelpara(MODEL_PARA model_PARA);//修改模型参数

}
