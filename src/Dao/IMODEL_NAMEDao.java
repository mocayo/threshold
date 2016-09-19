package Dao;

import java.util.List;
import java.util.Map;

import Entity.MODEL_NAME;

public interface IMODEL_NAMEDao {
		public List<MODEL_NAME> modelQuery(Map<String, Object> map);//查询模型
		
		public List<MODEL_NAME> getModel_NAMEs(int use_type);//获取模型名称

		public List<MODEL_NAME> modelQuery(String monitoritem);//获取监测项的模型
		
		public void modifymodelstate(int mid);//停止使用模型

		public List<MODEL_NAME> getForeHandleModel(String monitoritem);//获取特定监测项的预处理模型
		
}
