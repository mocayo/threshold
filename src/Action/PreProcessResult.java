package Action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import Dao.ICommonPreHandleBatchDao;
import Dao.ICommonPreHandleFinalDao;
import Dao.ICommonPreHandleResultDao;
import Dao.IMonitorItemTypeDao;
import Entity.AutoPreHandleInfo;
import Entity.CommonPreHandleBatch;
import Entity.CommonPreHandleFinal;
import Entity.CommonPreHandleResult;
import Entity.MonitorItemType;
import util.Action;
import util.PageControl;

/**
 * 查询预处理结果
 * @author Tony-think
 *
 */
public class PreProcessResult extends Action{
	
	private int len = 10;//每页条数
	private PageControl pc;//页面控制
	private int cpage;//当前页
	private ICommonPreHandleBatchDao commonPreHandleBatchDao;
	private ICommonPreHandleResultDao commonPreHandleResultDao ;
	private ICommonPreHandleFinalDao commonPreHandleFinalDao;
	private IMonitorItemTypeDao monitorItemTypeDao ;
	/**
	 * 人工修改数据预处理结果
	 * @throws IOException
	 */
	public void modifyPreHandleRes() throws IOException {
		try {
			int value_type = Integer.parseInt(request.getParameter("value_type"));  //分量类型
			String dt = request.getParameter("dt");
			String batchId = request.getParameter("resultId");
			String instr_no = request.getParameter("instr_no");
			int modify_result = Integer.parseInt(request.getParameter("modify_result"));    //修改后的结果
			String tableName=request.getParameter("tableName");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value_type",value_type);
			map.put("dt", dt);
			map.put("batchId", batchId);
			map.put("instr_no", instr_no);
			map.put("modify_result", modify_result);
			map.put("tableName", tableName);
			int total_result = 0;
			if(modify_result==0){      //该分量修改为异常，则直接修改最终结果为异常
				total_result=0;
			}
			else{                      //该分量修改结果为正常
				CommonPreHandleResult commonPreHandleResult=commonPreHandleResultDao.getPreHandleResByMap(map);
				MonitorItemType monitorItemType= monitorItemTypeDao.getItemTypeByTableName(tableName);
				switch (value_type) {
				case 1:    //该分量为分量1
					if(monitorItemType.getR2().equals("0")){   //没有分量2
						if(monitorItemType.getR3().equals("0")){  //没有分量3
							total_result=1;                        //总结果为分量1结果，正常   
						}
						else{   //有分量3
							if(commonPreHandleResult.getPeople_r3_handle()!=-1)//分量3有人工处理结果
								total_result=commonPreHandleResult.getPeople_r3_handle();//总结果为分量3人工结果
							else{     //分量3没有人工处理结果
								total_result=commonPreHandleResult.getHandle_r3_result();//总结果为分量3自动结果
							}
									
						}
					}
					else{                                          //有分量2
						if(monitorItemType.getR3().equals("0")){  //没有分量3
							if(commonPreHandleResult.getPeople_r2_handle()!=-1)//分量2有人工处理结果
								total_result=commonPreHandleResult.getPeople_r2_handle();//总结果为分量2人工结果
							else{     //分量2没有人工处理结果
								total_result=commonPreHandleResult.getHandle_r2_result();//总结果为分量2自动结果
							} 
						}
						else{   //有分量3
							if(commonPreHandleResult.getPeople_r2_handle()==-1)//分量2没有人工处理结果
								if(commonPreHandleResult.getPeople_r3_handle()==-1)//分量3没有人工处理结果
									if(commonPreHandleResult.getHandle_r2_result()==1
										&&commonPreHandleResult.getHandle_r3_result()==1)//分量2,3自动结果都正常
										total_result=1;
									else//分量2,3有一个结果异常
										total_result=0;
								else//分量3有人工结果
									total_result=commonPreHandleResult.getPeople_r3_handle();
							else{ //分量2有人工处理结果
								if(commonPreHandleResult.getPeople_r3_handle()==-1)//分量3没有人工处理结果
									total_result=commonPreHandleResult.getPeople_r2_handle();
								else//分量3有人工结果
									if(commonPreHandleResult.getPeople_r2_handle()==1
										&&commonPreHandleResult.getPeople_r3_handle()==1)
										total_result=1;
									else
										total_result=0;
							}
						}
					}
					break;
				case 2:    //该分量为分量2
					if(monitorItemType.getR1().equals("0")){   //没有分量1
						if(monitorItemType.getR3().equals("0")){  //没有分量3
							total_result=1;                        //总结果为分量2结果，正常   
						}
						else{   //有分量3
							if(commonPreHandleResult.getPeople_r3_handle()!=-1)//分量3有人工处理结果
								total_result=commonPreHandleResult.getPeople_r3_handle();//总结果为分量3人工结果
							else{     //分量3没有人工处理结果
								total_result=commonPreHandleResult.getHandle_r3_result();//总结果为分量3自动结果
							}
						}
					}
					else{                                          //有分量1
						if(monitorItemType.getR3().equals("0")){  //没有分量3
							if(commonPreHandleResult.getPeople_r1_handle()!=-1)//分量1有人工处理结果
								total_result=commonPreHandleResult.getPeople_r1_handle();//总结果为分量1人工结果
							else{     //分量1没有人工处理结果
								total_result=commonPreHandleResult.getHandle_r1_result();//总结果为分量1自动结果
							} 
						}
						else{   //有分量3
							if(commonPreHandleResult.getPeople_r1_handle()==-1)//分量1没有人工处理结果
								if(commonPreHandleResult.getPeople_r3_handle()==-1)//分量3没有人工处理结果
									if(commonPreHandleResult.getHandle_r1_result()==1
										&&commonPreHandleResult.getHandle_r3_result()==1)//分量1,3自动结果都正常
										total_result=1;
									else//分量1,3有一个结果异常
										total_result=0;
								else//分量3有人工结果
									total_result=commonPreHandleResult.getPeople_r3_handle();
							else{ //分量1有人工处理结果
								if(commonPreHandleResult.getPeople_r3_handle()==-1)//分量3没有人工处理结果
									total_result=commonPreHandleResult.getPeople_r1_handle();
								else//分量3有人工结果
									if(commonPreHandleResult.getPeople_r1_handle()==1
										&&commonPreHandleResult.getPeople_r3_handle()==1)
										total_result=1;
									else
										total_result=0;
							}
						}
					}
					break;
				case 3:    //该分量为分量3
					if(monitorItemType.getR1().equals("0")){   //没有分量1
						if(monitorItemType.getR1().equals("0")){  //没有分量2
							total_result=1;                        //总结果为分量3结果，正常   
						}
						else{   //有分量2
							if(commonPreHandleResult.getPeople_r2_handle()!=-1)//分量2有人工处理结果
								total_result=commonPreHandleResult.getPeople_r2_handle();//总结果为分量2人工结果
							else{     //分量2没有人工处理结果
								total_result=commonPreHandleResult.getHandle_r2_result();//总结果为分量2自动结果
							}
									
						}
					}
					else{                                          //有分量1
						if(monitorItemType.getR2().equals("0")){  //没有分量2
							if(commonPreHandleResult.getPeople_r1_handle()!=-1)//分量1有人工处理结果
								total_result=commonPreHandleResult.getPeople_r1_handle();//总结果为分量1人工结果
							else{     //分量1没有人工处理结果
								total_result=commonPreHandleResult.getHandle_r1_result();//总结果为分量1自动结果
							} 
						}
						else{   //有分量2
							if(commonPreHandleResult.getPeople_r1_handle()==-1)//分量1没有人工处理结果
								if(commonPreHandleResult.getPeople_r2_handle()==-1)//分量2没有人工处理结果
									if(commonPreHandleResult.getHandle_r1_result()==1
										&&commonPreHandleResult.getHandle_r2_result()==1)//分量1,2自动结果都正常
										total_result=1;
									else//分量1,2有一个结果异常
										total_result=0;
								else//分量2有人工结果
									total_result=commonPreHandleResult.getPeople_r2_handle();
							else{ //分量1有人工处理结果
								if(commonPreHandleResult.getPeople_r2_handle()==-1)//分量2没有人工处理结果
									total_result=commonPreHandleResult.getPeople_r1_handle();
								else//分量2有人工结果
									if(commonPreHandleResult.getPeople_r1_handle()==1
										&&commonPreHandleResult.getPeople_r2_handle()==1)
										total_result=1;
									else
										total_result=0;
							}
						}
					}
					break;
				}
			}
			map.put("total_result", total_result);
			commonPreHandleResultDao.modifyPreHandleRes(map);
			response.getWriter().write("1");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().write("0");
		}
	}

	/**
	 * 查询处理中间结果（暂存状态）
	 * @throws IOException
	 */
	public void getPreHandleResByType() throws IOException {
		String sjTypes = request.getParameter("sjTypes");
		String batchId = request.getParameter("resultId");
		String cpageStr = request.getParameter("cpage");
		if (cpageStr!=null&&cpageStr!=""){
			cpage = Integer.parseInt(cpageStr);
		} else {
			cpage = 1;
		}
		if (pc == null){
			pc = new PageControl();
		}
		int start = (cpage - 1) * len;
		pc.setPagesize(len);
		pc.setCpage(cpage);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start",start);
		map.put("len", len);
		map.put("batchId", batchId);
		String tableName=commonPreHandleBatchDao.getTableNameByBatchId(batchId);
		map.put("tableName", tableName);
		List<CommonPreHandleResult> outlierList= new ArrayList<CommonPreHandleResult>();
		if (sjTypes.equals("1")){
			outlierList = commonPreHandleResultDao.getAllPreHandleResByBatchId(map);
			pc.setTotalitem(commonPreHandleResultDao.getAllCountByBatchId(map));
		} else if (sjTypes.equals("2")){
			outlierList = commonPreHandleResultDao.getNormalPreHandleResByBatchId(map);
			pc.setTotalitem(commonPreHandleResultDao.getNormalCountByBatchId(map));
		} else if (sjTypes.equals("3")){
			outlierList = commonPreHandleResultDao.getExceptionPreHandleResByBatchId(map);
			pc.setTotalitem(commonPreHandleResultDao.getExceptionCountByBatchId(map));
		}
		map.clear();
		JSONArray jsonArray=new JSONArray();
		JSONObject jsObj1 =new  JSONObject(pc);
		jsonArray.put(jsObj1);
		JSONObject jsObj2=new JSONObject();
		MonitorItemType itemType=monitorItemTypeDao.getItemTypeByTableName(tableName);
		jsonArray.put(new  JSONObject(itemType));
		for (int i = 0; i < outlierList.size(); i++) {
			jsObj2 =new  JSONObject(outlierList.get(i));
			jsonArray.put(jsObj2);
		}
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsonArray.toString());
	}
	
	//通过批次Id删除该批次，包括结果表和最终表
	public void deletePreHandleResByBatchId() throws IOException {
		String batchId = request.getParameter("id");
		String tableName=commonPreHandleBatchDao.getTableNameByBatchId(batchId);
		Map<String, String> map=new HashMap<String, String>();
		map.put("batchId", batchId);
		map.put("tableName", tableName);
		commonPreHandleResultDao.deletePreHandleResultByBatchId(map);//删除结果表
		//commonPreHandleFinalDao.deletePreHandleFinalByBatchId(map);//删除最终表
		commonPreHandleBatchDao.deletePreHandleBatchByBatchId(batchId);//删除该批次
		response.getWriter().write("0");
	}
	
	//根据批次Id查询该批次的信息
	public void getPreHandleInfoByBatchId() throws Exception {
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
	
	//查询所有的预处理批次
	public void getAllPreHandleBatch() throws Exception {
		Map<String, Object> map = new HashMap<String,Object>();
		int pageindex = Integer.parseInt(request.getParameter("pageindex"));
		int pagesize = Integer.parseInt(request.getParameter("pagesize"));
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);
//		System.out.println("===========================================");
//		System.out.println("pageindex = " + pageindex);
//		System.out.println("===========================================");
		JSONArray jsonArray = new JSONArray();
		List<CommonPreHandleBatch> result = commonPreHandleBatchDao.getAllPreHandleBatchBypage(map);
		for (int i = 0; i < result.size(); i++) {
			JSONObject jsObj = new JSONObject();
			jsObj.put("id", result.get(i).getId());
			jsObj.put("dt", result.get(i).getDt().toString());
			jsObj.put("resultdiscript", result.get(i).getResultdiscript());
			jsObj.put("statu", result.get(i).getStatu());// 1 处理完成，0 正在处理
			jsonArray.put(jsObj);
		}
//		JSONObject jsObj = new JSONObject();
//		jsObj.put("totalrecord", commonPreHandleBatchDao.getPreHandleCount());
//		jsonArray.put(jsObj);
//		System.out.println("=========================================");
//		System.out.println(jsonArray);
//		System.out.println("=========================================");
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsonArray.toString());
	}
	
	//确认预处理，将该批次预处理结果写入最终表里
	public void confirmPreHandleBatch() throws IOException {
		try {
			String batchId = request.getParameter("batchId");
			String tableName=commonPreHandleBatchDao.getTableNameByBatchId(batchId);
			//将该批次处理的点的值写入最终表里
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("batchId", batchId);
			map.put("tableName", tableName);
			List<CommonPreHandleResult> list=commonPreHandleResultDao.getAllResultByResultId(map);
			for (CommonPreHandleResult commonPreHandleResult : list) {
				map.put("instr_no", commonPreHandleResult.getInstr_no());
				map.put("dt", commonPreHandleResult.getDt());
				map.put("result_id", commonPreHandleResult.getResult_id());
				map.put("handle_time", commonPreHandleResult.getHandle_time());
				map.put("r1", commonPreHandleResult.getR1());
				map.put("r2", commonPreHandleResult.getR2());
				map.put("r3", commonPreHandleResult.getR3());
				if(commonPreHandleResult.getPeople_r1_handle()==-1)
					map.put("handle_r1_result", commonPreHandleResult.getHandle_r1_result());
				else
					map.put("handle_r1_result", commonPreHandleResult.getPeople_r1_handle());
				if(commonPreHandleResult.getPeople_r2_handle()==-1)
					map.put("handle_r2_result", commonPreHandleResult.getHandle_r2_result());
				else
					map.put("handle_r2_result", commonPreHandleResult.getPeople_r2_handle());
				if(commonPreHandleResult.getPeople_r3_handle()==-1)
					map.put("handle_r3_result", commonPreHandleResult.getHandle_r3_result());
				else
					map.put("handle_r3_result", commonPreHandleResult.getPeople_r3_handle());
				
				List<CommonPreHandleFinal> list2= commonPreHandleFinalDao.findPreHandleResult(map);
				if(list2.size()>0){
					commonPreHandleFinalDao.updatePreHandleResult(map);
				}
				else {
					commonPreHandleFinalDao.insertPreHandleResult(map);
				}
			}
			commonPreHandleBatchDao.updateBatchStateByBatchId(batchId);//该批次已经确认处理
			response.getWriter().write("1");
		} catch (NumberFormatException e) {
			response.getWriter().write("0");
		}
	}
	
	//根据时间段查询预处理结果
	public void getPreHandleResultByTime() throws IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		String time = df.format(new Date());// new Date()为获取当前系统时间
		String starttime = request.getParameter("StartTime"), endtime = request
				.getParameter("EndTime");
		if (starttime == null || starttime == "")
			starttime = time;
		if (endtime == null || endtime == "")
			endtime = time;
		JSONArray jsonArray = new JSONArray();
		
		int pageindex = Integer.parseInt(request.getParameter("pageindex"));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("starttime", starttime + " 00:00:00");
		map.put("endtime", endtime + " 23:59:59");
		map.put("pageindex", pageindex);
		map.put("pagesize", 8);

//		List<CommonPreHandleBatch> commonPreHandleBatchs = commonPreHandleBatchDao.getPreHandleResultByTime(map);
		
		List<CommonPreHandleBatch> commonPreHandleBatchs = commonPreHandleBatchDao.getPreHandleResultByTimeByPage(map);

		for (int i = 0; i < commonPreHandleBatchs.size(); i++) {
			JSONObject jsonObject =new JSONObject(commonPreHandleBatchs.get(i));
			jsonArray.put(jsonObject);
		}
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsonArray.toString());
	}
	
	// 按测点查询预处理结果
	public void getPreHandleResultByPoint() throws IOException {
		String point = request.getParameter("point");
		String tableName = request.getParameter("tableName");
//		String[] point = request.getParameter("point").split("#");
//		String[] tableName = request.getParameter("tableName").split("#");
//		System.out.println("=============================================================================");
//		System.out.println("point = " + Arrays.toString(point));
//		System.out.println("tableName = " + Arrays.toString(tableName));
//		System.out.println("=============================================================================");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("tableName", tableName+"_AUTO");
		map.put("point", point);
		List<AutoPreHandleInfo> result = commonPreHandleBatchDao.getPreHandleResultByPointByPage(map);
//		List<AutoPreHandleInfo> result = null;
//		for(int i=0;i<tableName.length;i++){
//			Map<String, Object> map=new HashMap<String, Object>();
//			map.put("tableName", tableName[i]+"_AUTO");
//			map.put("point", point[i]);
//			if(result==null)
//				result = commonPreHandleBatchDao.getPreHandleResultByPointByPage(map);
//			else
//				result.addAll(commonPreHandleBatchDao.getPreHandleResultByPointByPage(map));
//		}
		JSONArray jsonArray = new JSONArray();
		//List<CommonPreHandleBatch> result = commonPreHandleBatchDao.getPreHandleResultByPoint(map);
		Map<String,List<AutoPreHandleInfo>> resultMap = new HashMap<String,List<AutoPreHandleInfo>>();
//		List<String> comps = new ArrayList<String>();
		for(AutoPreHandleInfo info:result){
			if(info.getDT().length()>10){
				info.setDT(info.getDT().substring(0,10));
			}
			if(resultMap.containsKey(info.getComponent())){
				resultMap.get(info.getComponent()).add(info);
			}else{
				List<AutoPreHandleInfo> list = new ArrayList<AutoPreHandleInfo>();
				list.add(info);
				resultMap.put(info.getComponent(), list);
			}
		}
//		for (int i = 0; i < result.size(); i++) {
//			AutoPreHandleInfo tmp = result.get(i);
//			if(tmp.getDT().length()>10){
//				tmp.setDT(tmp.getDT().substring(0,10));
//			}
//			if(resultMap.containsKey(tmp.getComponent())){
//				
//			}
//			JSONObject jsonObject =new  JSONObject(tmp);
//			jsonArray.put(jsonObject);
//		}
		Map<String,String> fmap = new HashMap<String,String>();
		for(String key:resultMap.keySet()){
			List<AutoPreHandleInfo> list = resultMap.get(key);
			JSONArray tmpJson = new JSONArray();
			for(AutoPreHandleInfo ap : list){
				JSONObject jsonObject = new  JSONObject(ap);
				tmpJson.put(jsonObject);
			}
			fmap.put(key, tmpJson.toString());
		}
		jsonArray.put(fmap);
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsonArray.toString());
	}

	public ICommonPreHandleBatchDao getCommonPreHandleBatchDao() {
		return commonPreHandleBatchDao;
	}

	public void setCommonPreHandleBatchDao(
			ICommonPreHandleBatchDao commonPreHandleBatchDao) {
		this.commonPreHandleBatchDao = commonPreHandleBatchDao;
	}

	public ICommonPreHandleResultDao getCommonPreHandleResultDao() {
		return commonPreHandleResultDao;
	}

	public void setCommonPreHandleResultDao(
			ICommonPreHandleResultDao commonPreHandleResultDao) {
		this.commonPreHandleResultDao = commonPreHandleResultDao;
	}

	public IMonitorItemTypeDao getMonitorItemTypeDao() {
		return monitorItemTypeDao;
	}

	public void setMonitorItemTypeDao(IMonitorItemTypeDao monitorItemTypeDao) {
		this.monitorItemTypeDao = monitorItemTypeDao;
	}

	public ICommonPreHandleFinalDao getCommonPreHandleFinalDao() {
		return commonPreHandleFinalDao;
	}

	public void setCommonPreHandleFinalDao(
			ICommonPreHandleFinalDao commonPreHandleFinalDao) {
		this.commonPreHandleFinalDao = commonPreHandleFinalDao;
	}
	
}
