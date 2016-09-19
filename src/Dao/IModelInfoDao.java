package Dao;

import java.util.List;
import java.util.Map;

import Entity.ModelRecord;

public interface IModelInfoDao {
	
	public List<ModelRecord> getPredictModel();
	
	public List<ModelRecord> getPretreatmentModel();
	
	//获取启用的模型
	public List<ModelRecord> getUsedModel(Map<String,Object> map);
	
	//获取要添加的模型名称
	public List<ModelRecord> getPremodelToAdd();
	
	public List<ModelRecord> getPtmmodelToAdd();
	
	public void addModel(Map<String, Object> map);
	
//	public void delModel(int modelid);
	
	public void delModel(Map<String, Object> map);
	
	public void updateModel(Map<String, Object> map);
	
	public int updateAddModel(Map<String, Object> map);
	
	public ModelRecord getModelById(int modelid);
	
}
