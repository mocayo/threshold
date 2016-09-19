package Action;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import util.Action;
import util.DataPreProcess;
import util.IdGenerator;
import Dao.ICommonPreHandleBatchDao;
import Dao.ICommonPreHandleDao;
import Dao.ICommonPreHandleResultDao;
import Dao.IDEFINSSORTDao;
import Dao.IMonitorItemTypeDao;
import Dao.IPointInfoDao;
import Entity.CommonPreHandle;
import Entity.CommonPreHandleBatch;
import Entity.MonitorItemType;

public class CommonPreHandleAction extends Action{
	
	private ICommonPreHandleResultDao commonPreHandleResultDao;
	private ICommonPreHandleBatchDao commonPreHandleBatchDao;
	private ICommonPreHandleDao commonPreHandleDao;
	private IMonitorItemTypeDao monitorItemTypeDao;
	private IDEFINSSORTDao definssortDao;
	private IPointInfoDao pointInfoDao;

	public void execute() throws IOException {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String type = request.getParameter("type");
		String pointIdString = request.getParameter("pointIdString");
		String tableNameStr=request.getParameter("title").trim();      //表名
		String[] tables = tableNameStr.split(",");
		String monitoritemtype=request.getParameter("monitoritemtype");
		//将检测仪器和坝段传回后台
		//判断测点是否为空，如果为空根据检测仪器和坝段取出对应的测点编号
		
		DataPreProcess dpp = new DataPreProcess();
		//修改批次表，添加一个批次
		IdGenerator ig = new IdGenerator();
		String genId = ig.generateId();
		Date today = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String handleTime = sf.format(today);
		
//		String[] pointList = pointIdString.split(",");
		String[] pointList = null;
		/*if(pointIdString==null||pointIdString.equals("null")){//如果传递监测点为空，则根据坝段和监测项查询所有的点
			String dam=request.getParameter("dam");
			String title=request.getParameter("title");
			Map<String, String> map=new HashMap<String, String>();
			if(dam!=""&&dam!=null)
				map.put("dam", dam);
			map.put("title", title);
			List<String> points=pointInfoDao.getPoint(map);
			pointList=new String[points.size()];
			for (int i = 0; i < points.size(); i++) {
				pointList[i]=points.get(i).trim();
			}*/
			/*String title=request.getParameter("title").split("_")[2];
			String dam=request.getParameter("dam");
			Map<String, String> map=new HashMap<String, String>();
			map.put("dam", dam);
			map.put("title", title);
			map.put("type", monitoritemtype);
			List<DEFINSSORT> iswhereList=definssortDao.getPoint(map);
			pointList=new String[iswhereList.size()];
			for (int i = 0; i < iswhereList.size(); i++) {
				pointList[i]=iswhereList.get(i).getDesigncode().trim();
			}*/
		/*}
		else*/
			pointList = pointIdString.split(",");
		//进行数据预处理
		try {
			if(type.equals("1"))
			{
				System.out.println("START..");	
				Map<String, Object> map = new HashMap<String, Object>();
				List<CommonPreHandle> dataList;
				double[] old_data;
				//int[] total_result = null;//保存一条记录的最终结果，0异常，1正常
				Map<String, Integer> total_result = new HashMap<String,Integer>();//保存一条记录的最终结果，0异常，1正常,键为点ID和DT组合的字符串,值为结果
				List<Double> result_list;
//				for (String id : pointList) {//得到该点所有数据
				for(int poindex=0;poindex<pointList.length;poindex++){
					String id = pointList[poindex];
					String tableName = tables[poindex];
					if(id.contains("'"))
						id=id.replaceAll("'", "''");
					
					//得到该点所有数据
					boolean bool=true;
					MonitorItemType itemType=monitorItemTypeDao.getItemTypeByTableName(tableName);
					if(!itemType.getR1().equals("0")){
						bool=false;
						//对R1进行处理					
						map.put("id", id);
						map.put("startTime", startTime);
						map.put("endTime", endTime);
						map.put("tableName",tableName);
						map.put("type","r1");
						dataList = commonPreHandleDao.getDataByIdTm(map);
			System.out.println(id+"----R1----"+dataList.size());
						//没有数据
						if(dataList.size() == 0)
							continue;
						
						//得到一项
						old_data= new double[dataList.size()];
						//total_result= new int[dataList.size()];
						for (int i = 0; i < old_data.length; i++) {
							old_data[i] = dataList.get(i).getR1();
							//total_result[i]=-1;					//-1表示没有记录结果
						}
						
						//四分法处理,得到异常值
						result_list = dpp.outlier_clear(old_data);
			System.out.println(result_list.size()+"--R1-Exception-----"+id);
						
						for (CommonPreHandle data : dataList) {
							int i=dataList.indexOf(data);
							map = new HashMap<String, Object>();
							double val = data.getR1();
							map.put("tableName",tableName);
							map.put("instr_no", id);
							map.put("dt", data.getDt());
							map.put("result_id", genId);
							map.put("handle_time", handleTime);
							map.put("val", val);
							map.put("type","r1");
							//异常值
							if(result_list.contains(val)) {
								map.put("handle_result", 0);
								map.put("total_result", 0);   
								total_result.put(data.getInstr_no().trim()+","+data.getDt(), 0);
								//total_result[i]=0;    //第I个数据异常
							} else {
								map.put("handle_result", 1);
								map.put("total_result", 1);
								total_result.put(data.getInstr_no().trim()+","+data.getDt(), 1);
								//total_result[i]=1;    //第I个数据正常
							}
							map.put("people_handle", -1);
							commonPreHandleResultDao.add_res(map);
						}
					}
					
					if(!itemType.getR2().equals("0")){
						map.clear();
						map.put("id", id);
						map.put("startTime", startTime);
						map.put("endTime", endTime);
						map.put("tableName",tableName);
						map.put("type","r2");
						dataList = commonPreHandleDao.getDataByIdTm(map);
			System.out.println(id+"----R2----"+dataList.size());
						//没有数据
						if(dataList.size() == 0)
							continue;
						//得到一项
						old_data= new double[dataList.size()];
						/*if(bool){//R1没有数据
							total_result= new int[dataList.size()];
							for (int i = 0; i < old_data.length; i++) {
								total_result[i]=-1;					//-1表示没有记录结果
							}
						}*/
						for (int i = 0; i < old_data.length; i++) {
							old_data[i] = dataList.get(i).getR2();
						}
						//四分法处理,得到异常值
						result_list = dpp.outlier_clear(old_data);
			System.out.println(result_list.size()+"--R2-Exception-----"+id);
						for (CommonPreHandle data : dataList) {
							//int i=dataList.indexOf(data);
							map.clear();
							double val = data.getR2();
							map.put("tableName",tableName);
							map.put("instr_no", id);
							map.put("dt", data.getDt());
							map.put("result_id", genId);
							map.put("handle_time", handleTime);
							map.put("val", val);
							map.put("type","r2");
							//异常值
							if(result_list.contains(val)) {
								map.put("handle_result", 0);
								total_result.put(data.getInstr_no().trim()+","+data.getDt(), 0);
								//total_result[i]=0;    //第I个数据异常
								map.put("total_result", 0);
							} else {
								map.put("handle_result", 1);
								if(total_result.containsKey(data.getInstr_no().trim()+","+data.getDt())){//包含
									map.put("total_result", 
											total_result.get(data.getInstr_no().trim()+","+data.getDt()));
								}
								else {
									total_result.put(data.getInstr_no().trim()+","+data.getDt(), 1);
									map.put("total_result", 1);   //第I个数据正常
								}
							}
							map.put("people_handle", -1);
							if(bool){
								bool=false;
								//对R2进行插入处理					
								commonPreHandleResultDao.add_res(map);
							}
							else{
								//对R2进行更新处理
								commonPreHandleResultDao.update_res(map);
							}
						}
					}
					if(!itemType.getR3().equals("0")){
						map.put("id", id);
						map.put("startTime", startTime);
						map.put("endTime", endTime);
						map.put("tableName",tableName);
						map.put("type","r3");
						dataList = commonPreHandleDao.getDataByIdTm(map);
			System.out.println(id+"----R3----"+dataList.size());
						//没有数据
						if(dataList.size() == 0)
							continue;
						//得到一项
						old_data= new double[dataList.size()];
						/*if(bool){//R1,R2没有数据
							total_result= new int[dataList.size()];
							for (int i = 0; i < old_data.length; i++) {
								total_result[i]=-1;					//-1表示没有记录结果
							}
						}*/
						for (int i = 0; i < old_data.length; i++) {
							old_data[i] = dataList.get(i).getR3();
						}
						//四分法处理,得到异常值
						result_list = dpp.outlier_clear(old_data);
			System.out.println(result_list.size()+"--R3-Exception-----"+id);
						for (CommonPreHandle data : dataList) {
							//int i=dataList.indexOf(data);
							map = new HashMap<String, Object>();
							double val = data.getR3();
							map.put("tableName",tableName);
							map.put("instr_no", id);
							map.put("dt", data.getDt());
							map.put("result_id", genId);
							map.put("handle_time", handleTime);
							map.put("val", val);
							map.put("type","r3");
							//异常值
							if(result_list.contains(val)) {
								map.put("handle_result", 0);
								total_result.put(data.getInstr_no().trim()+","+data.getDt(), 0);
								//total_result[i]=0;    //第I个数据异常
								map.put("total_result", 0);
							} else {
								map.put("handle_result", 1);
								if(total_result.containsKey(data.getInstr_no().trim()+","+data.getDt())){
									map.put("total_result", 
											total_result.get(data.getInstr_no().trim()+","+data.getDt()));
								}
								else {
									total_result.put(data.getInstr_no().trim()+","+data.getDt(),1);
									map.put("total_result", 1);   //第I个数据正常
								}
							}
							map.put("people_handle", -1);
							if(bool){
								bool=false;
								//对R3进行插入处理					
								commonPreHandleResultDao.add_res(map);
							}
							else{
								//对R3进行更新处理
								commonPreHandleResultDao.update_res(map);
							}
						}
					}
				}
			} 
			System.out.println("end");
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("batchId", genId);
			map.put("dt", handleTime);
			map.put("tableName", tableNameStr);
			int all = commonPreHandleResultDao.getAllCountByBatchId(map);
			int exception = commonPreHandleResultDao.getExceptionCountByBatchId(map);
			String desc = "处理时间" + startTime +"至"+ endTime +"，" +
							"测点"+pointList.length+"个，" +
							"处理记录"+all+"条，" +
							"正常记录"+(all-exception)+"条、" +
							"异常记录"+exception+"条。";
			map.put("resultdiscript", desc);
			//处理完成 1
			map.put("statu", "0");
			commonPreHandleBatchDao.addPreResult(map);
			String res = "preId="+genId;
			JSONObject jsObj=new JSONObject();
			jsObj.put("res", res);
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(jsObj.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void commonGetPreInfoByBatchId() throws IOException, JSONException {
		String batchId = request.getParameter("batchId");
		List<CommonPreHandleBatch> result = commonPreHandleBatchDao.getBatchInfoByBatchId(batchId);
		JSONObject jsObj = new JSONObject();
		jsObj.put("dt", result.get(0).getDt().toString().substring(0, 16));
		String[] details = result.get(0).getResultdiscript().split("，");
		jsObj.put("resultdiscript", details[1] + "，" + details[2] + "，"
				+ details[3]);
		response.setContentType("text/html;charset=GBK");
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsObj.toString());
	}
	
	public ICommonPreHandleResultDao getCommonPreHandleResultDao() {
		return commonPreHandleResultDao;
	}

	public void setCommonPreHandleResultDao(
			ICommonPreHandleResultDao commonPreHandleResultDao) {
		this.commonPreHandleResultDao = commonPreHandleResultDao;
	}

	public ICommonPreHandleBatchDao getCommonPreHandleBatchDao() {
		return commonPreHandleBatchDao;
	}

	public void setCommonPreHandleBatchDao(
			ICommonPreHandleBatchDao commonPreHandleBatchDao) {
		this.commonPreHandleBatchDao = commonPreHandleBatchDao;
	}

	public ICommonPreHandleDao getCommonPreHandleDao() {
		return commonPreHandleDao;
	}

	public void setCommonPreHandleDao(ICommonPreHandleDao commonPreHandleDao) {
		this.commonPreHandleDao = commonPreHandleDao;
	}
	public IDEFINSSORTDao getDefinssortDao() {
		return definssortDao;
	}

	public void setDefinssortDao(IDEFINSSORTDao definssortDao) {
		this.definssortDao = definssortDao;
	}


	public IMonitorItemTypeDao getMonitorItemTypeDao() {
		return monitorItemTypeDao;
	}


	public void setMonitorItemTypeDao(IMonitorItemTypeDao monitorItemTypeDao) {
		this.monitorItemTypeDao = monitorItemTypeDao;
	}


	public IPointInfoDao getPointInfoDao() {
		return pointInfoDao;
	}


	public void setPointInfoDao(IPointInfoDao pointInfoDao) {
		this.pointInfoDao = pointInfoDao;
	}
	
}
