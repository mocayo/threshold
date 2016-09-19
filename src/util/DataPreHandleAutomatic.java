package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Dao.ICommonPreHandleBatchDao;
import Dao.ICommonPreHandleDao;
import Dao.ICommonPreHandleFinalDao;
import Dao.ICommonPreHandleResultDao;
import Dao.IDEFINSSORTDao;
import Dao.IMonitorItemTypeDao;
import Entity.CommonPreHandle;
import Entity.CommonPreHandleFinal;
import Entity.CommonPreHandleResult;
import Entity.DEFINSSORT;
import Entity.MonitorItemType;

public class DataPreHandleAutomatic {

	private ICommonPreHandleResultDao commonPreHandleResultDao;
	private ICommonPreHandleBatchDao commonPreHandleBatchDao;
	private ICommonPreHandleDao commonPreHandleDao;
	private IMonitorItemTypeDao monitorItemTypeDao;
	private IDEFINSSORTDao definssortDao;
	private ICommonPreHandleFinalDao commonPreHandleFinalDao;

	
	public void jobTimer() {
		System.out.println("定时器启动");
		
		List<String> tableNameList = monitorItemTypeDao.getTableNames();
		
		for (String tableName : tableNameList) {
			/*if(!tableName.equals("T_ZB_EA"))
				continue;*/
			DataPreProcess dpp = new DataPreProcess();
			//修改批次表，添加一个批次
			IdGenerator ig = new IdGenerator();
			String genId = ig.generateId();
			Date today = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat pointsf = new SimpleDateFormat("yyyy-MM-dd");
			String handleTime = sf.format(today);
			String pointTime = pointsf.format(today);//处理该时间段的点的数据，从今天0点到23点
			
			//TODO 修改为根据表名得到所有测点
			List<DEFINSSORT> iswhereList=definssortDao.getPointByTableName(tableName);
			String[] pointList = new String[iswhereList.size()];
			for (int i = 0; i < iswhereList.size(); i++) {
				pointList[i]=iswhereList.get(i).getDesigncode().trim();
			}
			
			
			String startTime = null;
			String endTime = null; 
			
			String type = "1";		
			if(type.equals("1"))
			{
				Map<String, Object> map = new HashMap<String, Object>();
				List<CommonPreHandle> dataList;
				double[] old_data;
				Map<String, Integer> total_result = new HashMap<String,Integer>();//保存一条记录的最终结果，0异常，1正常,键为点ID和DT组合的字符串,值为结果
				List<Double> result_list;
				for (String id : pointList) {//得到该点所有数据
					if(id.contains("'"))
						id=id.replaceAll("'", "''");
					
					//TODO 确定预处理起始时间
					//startTime = pointTime+" 00:00:00";
					//endTime = pointTime+" 23:59:59";

					startTime = "2000-01-01";
					endTime = "2015-04-30";
					
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
						for (int i = 0; i < old_data.length; i++) {
							old_data[i] = dataList.get(i).getR1();
							//total_result[i]=-1;					//-1表示没有记录结果
						}
						
						//四分法处理,得到异常值
						result_list = dpp.outlier_clear(old_data);
	System.out.println(result_list.size()+"--R1-Exception-----"+id);
						
						for (CommonPreHandle data : dataList) {
							//int i=dataList.indexOf(data);
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
								total_result.put(data.getInstr_no()+data.getDt(), 0);
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
								total_result.put(data.getInstr_no()+data.getDt(), 0);
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
			map.put("tableName", tableName);
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

			//将该批次处理的点的值写入最终表里
			map.clear();
			map.put("batchId", genId);
			map.put("tableName", tableName);
			List<CommonPreHandleResult> list=commonPreHandleResultDao.getAllResultByResultId(map);
			for (CommonPreHandleResult result : list) {
				map.clear();
				String instr_no=result.getInstr_no().trim();
				if(instr_no.contains("'"))
					instr_no=instr_no.replaceAll("'", "''");
				map.put("instr_no", instr_no);
				map.put("dt", result.getDt());
				map.put("result_id", result.getResult_id());
				map.put("handle_time", result.getHandle_time());
				map.put("r1", result.getR1());
				map.put("r2", result.getR2());
				map.put("r3", result.getR3());
				map.put("tableName", tableName);
				if(result.getPeople_r1_handle()==-1)
					map.put("handle_r1_result", result.getHandle_r1_result());
				else
					map.put("handle_r1_result", result.getPeople_r1_handle());
				if(result.getPeople_r2_handle()==-1)
					map.put("handle_r2_result", result.getHandle_r2_result());
				else
					map.put("handle_r2_result", result.getPeople_r2_handle());
				if(result.getPeople_r3_handle()==-1)
					map.put("handle_r3_result", result.getHandle_r3_result());
				else
					map.put("handle_r3_result", result.getPeople_r3_handle());
				
				List<CommonPreHandleFinal> list2= commonPreHandleFinalDao.findPreHandleResult(map);
				if(list2.size()>0){
					commonPreHandleFinalDao.updatePreHandleResult(map);
				}
				else {
					commonPreHandleFinalDao.insertPreHandleResult(map);
				}
			}
		}
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


	public IMonitorItemTypeDao getMonitorItemTypeDao() {
		return monitorItemTypeDao;
	}


	public void setMonitorItemTypeDao(IMonitorItemTypeDao monitorItemTypeDao) {
		this.monitorItemTypeDao = monitorItemTypeDao;
	}


	public IDEFINSSORTDao getDefinssortDao() {
		return definssortDao;
	}


	public void setDefinssortDao(IDEFINSSORTDao definssortDao) {
		this.definssortDao = definssortDao;
	}


	public ICommonPreHandleFinalDao getCommonPreHandleFinalDao() {
		return commonPreHandleFinalDao;
	}


	public void setCommonPreHandleFinalDao(
			ICommonPreHandleFinalDao commonPreHandleFinalDao) {
		this.commonPreHandleFinalDao = commonPreHandleFinalDao;
	} 
	
}
