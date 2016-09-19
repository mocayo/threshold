package Action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import util.Action;
import Dao.IModelInfoDao;
import Dao.IModelPointDao;
import Entity.ModelPoint;
import Entity.ModelRecord;

public class ConfModelPoint extends Action {
	private ModelPoint modelpointrecord;
	private ModelPoint confmodelpoint;
	private IModelPointDao modelPointDao;
	private IModelInfoDao modelDao;
//	private ITABLE_TYPEDao table_typeDao;
//	private String selectmodel;
	private File pointExcel;
	
	private String pointTable;
	private String pointSelect;
	private String point_model_select;
	
	private String no;
	private String type;
	private String model_methods;
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModel_methods() {
		return model_methods;
	}

	public void setModel_methods(String model_methods) {
		this.model_methods = model_methods;
	}

	public String getPointTable() {
		return pointTable;
	}

	public void setPointTable(String pointTable) {
		this.pointTable = pointTable;
	}
	
	public String getPointSelect() {
		return pointSelect;
	}

	public void setPointSelect(String pointSelect) {
		this.pointSelect = pointSelect;
	}

	public String getPoint_model_select() {
		return point_model_select;
	}

	public void setPoint_model_select(String point_model_select) {
		this.point_model_select = point_model_select;
	}

	public IModelPointDao getModelpointDao() {
		return modelPointDao;
	}

	public void setModelPointDao(IModelPointDao modelPointDao) {
		this.modelPointDao = modelPointDao;
	}

	public IModelInfoDao getModelDao() {
		return modelDao;
	}

	public void setModelDao(IModelInfoDao modelDao) {
		this.modelDao = modelDao;
	}
	
	/*public ITABLE_TYPEDao getTable_typeDao() {
		return table_typeDao;
	}

	public void setTable_typeDao(ITABLE_TYPEDao table_typeDao) {
		this.table_typeDao = table_typeDao;
	}
	 */
	public File getPointExcel() {
		return pointExcel;
	}

	public void setPointExcel(File pointExcel) {
		this.pointExcel = pointExcel;
	}
	
	public ModelPoint getModelpointrecord() {
		return modelpointrecord;
	}

	public void setModelpointrecord(ModelPoint modelpointrecord) {
		this.modelpointrecord = modelpointrecord;
	}
	
	public ModelPoint getConfmodelpoint() {
		return confmodelpoint;
	}

	public void setConfmodelpoint(ModelPoint confmodelpoint) {
		this.confmodelpoint = confmodelpoint;
	}

	
	//添加预测模型测点
	public String addPreModelPoint(){
		//处理测点编号以及对应分量并存储
		String[] instrnos = modelpointrecord.getInstr_no().trim().split(",");
		String[] Rs = modelpointrecord.getR().trim().split(",");
		for(int i=0;i<instrnos.length;i++){
			Map<String,Object> map = new HashMap<String, Object>();
			String[] tmp1 = instrnos[i].trim().split("#");//tmp1[0]为表名,tmp1[1]为测点编号
			map.put("MonitorItem", tmp1[0]);
			map.put("Instr_no", tmp1[1]);
			map.put("PointType", "premodel");
			for(int j=0;j<Rs.length;j++){
				String[] tmp2 = Rs[j].trim().split("#");//tmp2[0]为测点编号,tmp2[1]为分量
				if(tmp1[1].equals(tmp2[0])){
					map.put(tmp2[1].trim(), model_methods);
				}
			}
			modelPointDao.addModelPoint(map);
		}
		return "confsuccess";
	}	

	//添加预处理模型测点
	public String addPtmModelPoint(){
		
		//处理测点编号以及对应分量并存储
		String[] instrnos = modelpointrecord.getInstr_no().trim().split(",");
		String[] Rs = modelpointrecord.getR().trim().split(",");
		for(int i=0;i<instrnos.length;i++){
			Map<String,Object> map = new HashMap<String, Object>();
			String[] tmp1 = instrnos[i].trim().split("#");//tmp1[0]为表名,tmp1[1]为测点编号
			map.put("MonitorItem", tmp1[0]);
			map.put("Instr_no", tmp1[1]);
			map.put("PointType", "ptmmodel");
			for(int j=0;j<Rs.length;j++){
				String[] tmp2 = Rs[j].trim().split("#");//tmp2[0]为测点编号,tmp2[1]为分量
				if(tmp1[1].equals(tmp2[0])){
					map.put(tmp2[1].trim(), model_methods);
				}
			}
			modelPointDao.addModelPoint(map);
		}
//		System.out.println(instrnos);
//		System.out.println(Rs);
//		for(int i=0;i<instrnos.length;i++){
//			Map<String,Object> map = new HashMap<String, Object>();
//			String no = instrnos[i];
//			map.put("Instr_no", no);
////			System.out.println("Instr_no = " + no);
//			for(int j=0;j<Rs.length;j++){
//				String r = Rs[j];
//				if(r.contains(no)){
//					if(r.contains("r1")){
//						map.put("R1", model_methods);
////						System.out.println("R1 = r1");
//					}
//					if(r.contains("r2")){
//						map.put("R2", model_methods);
////						System.out.println("R2 = r2");
//					}
//					if(r.contains("r3")){
//						map.put("R3", model_methods);
////						System.out.println("R3 = r3");
//					}
//				}else{
//					continue;
//				}
//			}
//			String monitoritem = modelpointrecord.getMonitorItem().trim();
//			if(monitoritem.contains(","))
//				monitoritem = monitoritem.substring(monitoritem.indexOf(",")+1);
//			map.put("MonitorItem", monitoritem);
//			map.put("PointType", "ptmmodel");
//			modelPointDao.addModelPoint(map);
//		}
		return "confsuccess";
	}
	
	public void getPoints() throws IOException{
		String pa = request.getParameter("pageindex");
		String model = request.getParameter("model");
		int pageindex = Integer.parseInt(pa);
		int pagesize = 10;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pagesize", pagesize);
		map.put("pageindex", pageindex);
		map.put("PointType", model);
		List<ModelPoint> modelpoints = modelPointDao.getModelPointByPage(map);
		int countRecords = modelPointDao.countRecords(model);
		JSONArray jsonArray = new JSONArray();
		for(ModelPoint po:modelpoints){
			JSONObject obj = JSONObject.fromObject(po);
			jsonArray.add(obj);
		}
		jsonArray.add(countRecords);
		response.getWriter().write(jsonArray.toString());
	}
	
	//建立选择模型的表单
	private String createSelect(String model){
		String content = "";
		Map<String, Object> map = new HashMap<String,Object>();
//		String _no = "";
//		try {
//			_no = new String(no.getBytes(),"utf-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		System.out.println(_no);
		String instrno = no;
		if(instrno.contains("horizontal")){
//			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			instrno = instrno.replace("horizontal", "水平");
		}else if(instrno.contains("vertical")){
//			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			instrno = instrno.replace("vertical", "垂直");
		}
//		map.put("Instr_no", no);
		map.put("Instr_no", instrno);
		map.put("PointType", type);
		ModelPoint model_point = modelPointDao.getModelPoint(map);
		String action  = "addconf" + model;
//		System.out.println("------------------");
//		System.out.println(model + "   action is " + action);
//		System.out.println("------------------");
		content += "<form role='form' action='"+ action+"' method='post'>";
		content += "<div class='form-group'>";
        content += "<label>测点编号</label>";
        content += "<p class='form-control-static'>"+model_point.getInstr_no()+"</p></div>";
        content += "<input type='hidden' name='confmodelpoint.Instr_no' value='" + model_point.getInstr_no()+ "'>";
        content += "<input type='hidden' name='confmodelpoint.PointType' value='" + model_point.getPointType()+ "'>";
//        content += "<div class='form-group'>";
//        content += "<label>测点类型</label>";
//        content += "<p class='form-control-static'>"+model_point.getPointType()+"</p></div>";
//        content += "<div class='form-group'>";
//        content += "<label>监测仪器</label>";
//        content += "<p class='form-control-static'>"+model_point.getMonitorItem()+"</p></div>";
//        String selectModel = "";
//        if("premodel".equals(model)){
//        	selectModel += createModelSelect(modelDao.getPredictModel());
//    	}else{
//    		selectModel += createModelSelect(modelDao.getPretreatmentModel());
//    	}
//        if("r1".equals(model_point.getR1())){
        if(!("".equals(model_point.getR1()))){
        	content += "<div class='form-group'>";
            content += "<label>配置分量R1:当前值为"+ model_point.getR1()+"</label>";
//            content += selectModel;
            content += createModelSelect(modelDao.getPredictModel(),"R1");
            content += "</div>";
        }
//        if("r2".equals(model_point.getR2())){
        if(!("".equals(model_point.getR2()))){
        	content += "<div class='form-group'>";
//            content += "<label>配置分量R2</label>";
            content += "<label>配置分量R2:当前值为"+ model_point.getR2()+"</label>";
//            content += selectModel;
            content += createModelSelect(modelDao.getPredictModel(),"R2");
            content += "</div>";
        }
//        if("r3".equals(model_point.getR3())){
        if(!("".equals(model_point.getR3()))){
        	content += "<div class='form-group'>";
//            content += "<label>配置分量R3</label>";
            content += "<label>配置分量R3:当前值为"+ model_point.getR3()+"</label>";
            content += "</div>";
//            content += selectModel;
            content += createModelSelect(modelDao.getPredictModel(),"R3");
            content += "</div>";
        }
		content += "<button type='submit' class='btn btn-primary btn-outline'>配置</button>";
//		content += "<button type='reset' class='btn btn-default'>清空</button>";
		content += "</form>";
		return content;
	}
	
	//建立选择模型和对应方法的表格
	private String createModelSelect(List<ModelRecord> records,String R){
		String select = "";
		select += "<table class='table table-striped table-bordered table-hover' id='dataTables-example'>";
		select += "<th>模型编号</th><th>模型名称</th><th>对应方法</th>";
		for(ModelRecord record:records){
			if(Integer.parseInt(record.getIsdelete())==1)
				continue;
			select += "<tr>";
			select += "<td>" + record.getModelid() + "</td>";
			select += "<td>" + record.getModelname() + "</td>";
			String[] methods = record.getMethod().trim().split(",");
			select += "<td>";
//			select += "<td><div class='form-group'>";
//			select += "<label>方法</label>";
			for(String method:methods){
				select += "<div class='checkbox'>";
				select += "<label><input type='checkbox' name='confmodelpoint."+R+"'value='"+ method +"'>"+method+"</label></div>";

			}
//			select += "</div></td>";
			select += "</td>";
			select += "</tr>";
		}
		select += "</table>";
		return select;
	}
	
	//所有预测测点
	public String showPreModelPoint(){
		return "success";
	}
	
	//所有预处理测点
	public String showPtmModelPoint(){
		return "success";
	}
	
	//配置预测测点，相当于对已经添加的测点进行编辑
	public String confPreModelPoint(){
		this.pointSelect = createSelect("premodel");
		return "success";
	}
	
	//配置预处理测点，相当于对已经添加的测点进行编辑
	public String confPtmModelPoint(){
		this.pointSelect = createSelect("ptmmodel");
		return "success";
	}
	
	//添加编辑预测的测点
	public String addConfPrePoint(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("R1", confmodelpoint.getR1());
		map.put("R2", confmodelpoint.getR2());
		map.put("R3", confmodelpoint.getR3());
		map.put("Instr_no", confmodelpoint.getInstr_no());
		map.put("PointType", confmodelpoint.getPointType());
		modelPointDao.updateModelPoint(map);
		return "success";
	}
	
	//添加编辑预处理的测点
	public String addConfPtmPoint(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("R1", confmodelpoint.getR1());
		map.put("R2", confmodelpoint.getR2());
		map.put("R3", confmodelpoint.getR3());
		map.put("Instr_no", confmodelpoint.getInstr_no());
		map.put("PointType", confmodelpoint.getPointType());
		modelPointDao.updateModelPoint(map);
		return "success";
	}
	
	//根据模型记录建立模型及方法的表格
	private String modelSelInput(List<ModelRecord> records){
		String select = "";
		select += "<table class='table table-striped table-hover' id='mtable'>";
		select += "<tr><th>#</th><th>模型名称</th><th>对应方法</th></tr>";
		int i = 1;
		for(ModelRecord record:records){
			if(Integer.parseInt(record.getIsdelete())==1)
				continue;
			select += "<tr>";
			select += "<td valign='middle'>" + i++ + "</td>";
			select += "<td valign='middle'>" + record.getModelname() + "</td>";
			String[] methods = record.getMethod().trim().split(",");
			select += "<td valign='middle'>";
			for(String method:methods){
				select += "<div class='checkbox'>";
				if(i==2)
					select += "<label><input type='checkbox'checked name='model_methods' value='"+ method +"'>"+method+"</label></div>";
				else
					select += "<label><input type='checkbox' name='model_methods' value='"+ method +"'>"+method+"</label></div>";
			}
			select += "</td>";
			select += "</tr>";
		}
		select += "</table>";
		return select;
	}
	
	//添加预测测点的输入页面
	public String addPrePointInput(){
		point_model_select = modelSelInput(modelDao.getPredictModel());
		return "success";
	}
	
	//添加预处理测点的输入页面
	public String addPtmPointInput(){
		point_model_select = modelSelInput(modelDao.getPretreatmentModel());
		return "success";
	}
	
	public String addPrePointFromExcel(){
		if(pointExcel!=null)
			addPointFromExcel(pointExcel,"premodel");
		return "success";
	}
	
	public String addPtmPointFromExcel(){
		if(pointExcel!=null)
			addPointFromExcel(pointExcel,"ptmmodel");
		return "success";
	}
	
	private void addPointFromExcel(File file,String type){
//		List<TABLE_TYPE> tbtypes = table_typeDao.getTable_Types();
//		System.out.println(tbtypes);
		try {
			FileInputStream excel = new FileInputStream(file);
			Workbook wb = null;
			if(excel!=null)
				wb = new HSSFWorkbook(excel);
			Sheet sheet = wb.getSheetAt(0);
			Iterator<Row> rows = sheet.rowIterator();
			//获取第一行的标题信息
			Row title = rows.next();
			String rowinfo = "";
			for(int i=0;i<title.getLastCellNum();i++){
				rowinfo += title.getCell(i).getStringCellValue()+",";
			}
			rowinfo = rowinfo.replaceAll("测点编号", "Instr_no");
			rowinfo = rowinfo.replaceAll("测点类型", "PointType");
			rowinfo = rowinfo.replaceAll("监测仪器", "MonitorItem");
			rowinfo = rowinfo.replaceAll("分量", "");
			String[] keys = rowinfo.trim().split(",");
//			for(String key:keys){
//				System.out.println(key);
//			}
			while(rows.hasNext()){
				Row row = rows.next();
				Map<String, Object> map = new HashMap<String, Object>();
				for(int index=0;index<keys.length;index++){
					String val = "";
					if(row.getCell(index)!=null)
						val += row.getCell(index).getStringCellValue();
					map.put(keys[index], val.trim());
//					if(row.getCell(index)==null){
//						System.out.println("");
//					}else{
//						System.out.println(row.getCell(index).getStringCellValue());
//					}
				}
				if(!type.equals(map.get("PointType")))
					map.put("PointType", type);
//				System.out.println(map.toString());
				modelPointDao.addModelPoint(map);
			}
		} catch (IOException e) {
			return;
		}
		return;
	}
	
}
