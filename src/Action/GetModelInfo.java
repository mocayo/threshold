package Action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Action;
import Dao.IModelInfoDao;
import Entity.ModelRecord;

public class GetModelInfo extends Action {
	private IModelInfoDao modelDao;

	public IModelInfoDao getModelDao() {
		return modelDao;
	}

	public void setModelDao(IModelInfoDao modelDao) {
		this.modelDao = modelDao;
	}
	
	public void getPtmModel() throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ModelType", 0);
//		List<ModelRecord> list = modelDao.getPretreatmentModel();
		List<ModelRecord> list = modelDao.getUsedModel(map);
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject jsObj = JSONObject.fromObject(list.get(i));
			jsonArray.add(jsObj);
		}
		response.getWriter().write(jsonArray.toString());
	}
	
	public void getPreModel() throws IOException{
//		List<ModelRecord> list = modelDao.getPredictModel();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ModelType", 1);
//		List<ModelRecord> list = modelDao.getPretreatmentModel();
		List<ModelRecord> list = modelDao.getUsedModel(map);
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject jsObj = JSONObject.fromObject(list.get(i));
			jsonArray.add(jsObj);
		}
		response.getWriter().write(jsonArray.toString());
	}
	
	//获取要添加的预测模型
	public void getPremodelToAdd() throws IOException{
		List<ModelRecord> list = modelDao.getPremodelToAdd();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject jsObj = JSONObject.fromObject(list.get(i));
			jsonArray.add(jsObj);
		}
		response.getWriter().write(jsonArray.toString());
	}
	
	//获取要添加的预处理模型
	public void getPtmmodelToAdd() throws IOException{
		List<ModelRecord> list = modelDao.getPtmmodelToAdd();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject jsObj = JSONObject.fromObject(list.get(i));
			jsonArray.add(jsObj);
		}
		response.getWriter().write(jsonArray.toString());
	}
	
	//添加预测模型
	public void updateAddPremodel() throws IOException{
		String modelid = request.getParameter("modelid");
//		String modelname = request.getParameter("modelname");
//		String method = request.getParameter("method");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("modelid", modelid);
//		map.put("modelname", modelname);
//		map.put("method", method);
		int result = 0;
		if(modelDao.updateAddModel(map)==1)
			result = 1;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsObj = new JSONObject();
		jsObj.put("added", result);
		jsonArray.add(jsObj);
		response.getWriter().write(jsonArray.toString());
	}
	
	//添加预处理模型
	public void updateAddPtmmodel() throws IOException{
		String modelid = request.getParameter("modelid");
		String modelname = request.getParameter("modelname");
		String method = request.getParameter("method");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("modelid", modelid);
		map.put("modelname", modelname);
		map.put("method", method);
		int result = 0;
		if(modelDao.updateAddModel(map)==1)
			result = 1;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsObj = new JSONObject();
		jsObj.put("added", result);
		jsonArray.add(jsObj);
		response.getWriter().write(jsonArray.toString());
	}
}