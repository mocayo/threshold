package Action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Action;
import Dao.IModelInfoDao;
import Entity.ModelRecord;

public class ModelInfoAction extends Action{
	
	private IModelInfoDao modelDao;
	private List<ModelRecord> modelrecord;
	private String table;
	private ModelRecord model;
	private int modelId;
	private int isDeltete;

	public int getIsDeltete() {
		return isDeltete;
	}

	public void setIsDeltete(int isDeltete) {
		this.isDeltete = isDeltete;
	}

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public ModelRecord getModel() {
		return model;
	}

	public void setModel(ModelRecord model) {
		this.model = model;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	/*public List<ModelRecord> getModelrecord() {
		return modelrecord;
	}

	public void setModelrecord(List<ModelRecord> modelrecord) {
		this.modelrecord = modelrecord;
	}*/

	public IModelInfoDao getModelDao() {
		return modelDao;
	}

	public void setModelDao(IModelInfoDao modelDao) {
		this.modelDao = modelDao;
	}
	
	private String createTable(List<ModelRecord> model,String page){
		String content;
//		String delPage = "http://localhost/new_LCJS/model/del";
		String delPage = "del";
		delPage += page + "model?modelId=";
//		String updatePage = "http://localhost/new_LCJS/model/update";
		String updatePage = "update";
		updatePage += page + "modelinput?modelId=";
		content = "<table class='table table-striped table-hover' id='mtable'>";
//		content += "<thead><th></th><th>ModelName</th><th>Method</th><th>IsDelete</th><th>Edit</th><th>Delete</th></thead>";
		content += "<thead><th>#</th><th>模型名称</th><th>对应方法</th><th>是否启用</th><th>编辑模型</th><th>启用/不启用"
				+ "(<a><span class='glyphicon glyphicon-ok-sign'></span></a>/<a><span class='glyphicon glyphicon-remove-sign'></span></a>)</th></thead>";
		for(int i=0;i<model.size();i++){
			ModelRecord record = model.get(i);
			content += "<tr>";
//			content += "<td>" + record.getModelid() + "</td>";
			content += "<td>" + (i+1) + "</td>";
			content += "<td>" + record.getModelname() +"</td>";
//			content += "<td>" + record.getModeltype() + "</td>";
			content += "<td>" + record.getMethod() + "</td>";
//			content += "<td>" + record.getIsdelete()+ "</td>";
			if(Integer.parseInt(record.getIsdelete())==0){
				content += "<td><a ><span class='glyphicon glyphicon-check'></span></a></td>";
				content += "<td><a href='"+ updatePage + record.getModelid() +"'><span class='glyphicon glyphicon-edit'></span><a></td>";
				content += "<td><a href='"+ delPage+record.getModelid() +"&isDeltete=1'><span class='glyphicon glyphicon-remove-sign'></span><a></td>";
			}else{
				content += "<td><a ><span class='glyphicon glyphicon-remove'></span></a></td>";
				content += "<td><a href='"+ updatePage + record.getModelid() +"'><span class='glyphicon glyphicon-edit'></span><a></td>";
				content += "<td><a href='"+ delPage+record.getModelid() +"&isDeltete=0'><span class='glyphicon glyphicon-ok-sign'></span><a></td>";
			}
			content += "</tr>";
		}
		content += "</table>";
		return content;
	}
	
	public String getPredictModelInfo(){
		modelrecord = modelDao.getPredictModel();
		table = createTable(modelrecord,"pre");
		session.setAttribute("table", table);
		return "success";
	}
	
	public String getPretreatmentModelInfo(){
		modelrecord = modelDao.getPretreatmentModel();
		table = createTable(modelrecord,"ptm");
		session.setAttribute("table", table);
		return "success";
	}
	
	//添加模型pro
	public String addModel(){
		/*if(model==null){
			System.out.println("========================");
			System.out.println("model is null");
			System.out.println("========================");
		}else{
			System.out.println(model.getModelname());
			System.out.println(model.getModeltype());
			System.out.println(model.getMethod());
		}*/
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modelname", model.getModelname());
		map.put("modeltype", model.getModeltype());
		map.put("method", model.getMethod());
		map.put("isdelete", 0);
		modelDao.addModel(map);
//		session.setAttribute("add", true);
		return "addsuccess";
	}
	
	public String delModel(){
//		System.out.println("del model where modelid = " + modelId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modelid", modelId);
		map.put("isDelete", isDeltete);
		modelDao.delModel(map);
		return "delsuccess";
	}
	
	public String updateInputModel(){
		ModelRecord mrecord = modelDao.getModelById(modelId);
		/*System.out.println("=========================");
		System.out.println("updating-------->");
		System.out.println(mrecord.getModelname());
		System.out.println(mrecord.getModeltype());
		System.out.println("=========================");*/
		session.setAttribute("mrecord", mrecord);
		return "success";
	}
	
	public String updateModel(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modelid", model.getModelid());
		map.put("modelname", model.getModelname());
		map.put("modeltype", model.getModeltype());
		map.put("method", model.getMethod());
		map.put("isdelete", 0);
		modelDao.updateModel(map);
		return "updatesuccess";
	}
	
}
