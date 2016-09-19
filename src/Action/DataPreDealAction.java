package Action;


import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Action;
import util.DataPreProcess;
import util.IdGenerator;
import Dao.IDEFINSSORTDao;
import Dao.IPRERESULTDao;
import Dao.IT_ZB_PLDao;
import Dao.IT_ZB_PL_ForeHandleDao;
import Dao.IT_ZB_PL_ResDao;
import Entity.DEFINSSORT;
import Entity.T_ZB_PL;
import Entity.T_ZB_PL_ForeHandle;

public class DataPreDealAction extends Action{
	
	private static final long serialVersionUID = 1715775225157327295L;
	
	private IT_ZB_PL_ForeHandleDao t_zb_pl_forehandleDao;
	private IT_ZB_PL_ResDao t_zb_pl_resDao;
	private IPRERESULTDao preData;
	private IT_ZB_PLDao t_zb_plDao;
	private IDEFINSSORTDao definssortDao;

	public IDEFINSSORTDao getDefinssortDao() {
		return definssortDao;
	}

	public void setDefinssortDao(IDEFINSSORTDao definssortDao) {
		this.definssortDao = definssortDao;
	}

	public void execute() throws IOException {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String type = request.getParameter("type");
		String pointIdString = request.getParameter("pointIdString");
		
// TODO: 将检测仪器和坝段传回后台

// TODO:		判断测点是否为空，如果为空根据检测仪器和坝段取出对应的测点编号
		
		DataPreProcess dpp = new DataPreProcess();
		
		//修改批次表，添加一个批次
		IdGenerator ig = new IdGenerator();
		String genId = ig.generateId();
		Date today = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String handleTime = sf.format(today);
		
		
//		String[] pointList = pointIdString.split(",");
		String[] pointList;
		if(pointIdString.equals("null")){//如果传递监测点为空，则根据坝段和监测项查询所有的点
			String title=request.getParameter("title").split("_")[2];
			String dam=request.getParameter("dam");
			String monitoritemtype=request.getParameter("monitoritemtype");
			Map<String, String> map=new HashMap<String, String>();
			map.put("dam", dam);
			map.put("title", title);
			map.put("type", monitoritemtype);
			List<DEFINSSORT> iswhereList=definssortDao.getPoint(map);
			pointList=new String[iswhereList.size()];
			for (int i = 0; i < iswhereList.size(); i++) {
				pointList[i]=iswhereList.get(i).getDesigncode().trim();
			}
		}
		else
			pointList = pointIdString.split(",");
		//进行数据预处理
		try {
			if(type.equals("1"))
			{
				
				for (String id : pointList) {

					//得到该点所有数据
					
					//对DISPLACE_X进行处理					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", id);
					map.put("startTime", startTime);
					map.put("endTime", endTime);
					
					List<T_ZB_PL_ForeHandle> dataList = t_zb_pl_forehandleDao.getDataXByIdTm(map);
//System.out.println("----"+dataList.size());
					//没有数据
					if(dataList.size() == 0)
						continue;
					
					//得到一项
					double[] x_old = new double[dataList.size()];
					for (int i = 0; i < x_old.length; i++) {
						x_old[i] = dataList.get(i).getDisplace_x().doubleValue();
					}
					
					//四分法处理,得到异常值
					List<Double> result_x = dpp.outlier_clear(x_old);
					System.out.println(result_x.size()+"--------");
					
					
					for (T_ZB_PL_ForeHandle data : dataList) {
						map = new HashMap<String, Object>();
						double val = data.getDisplace_x().doubleValue();
						
						map.put("instr_no", id);
						map.put("dt", data.getDt());
						map.put("result_id", genId);
						map.put("handle_time", handleTime);
						map.put("displace_x", val);
						//异常值
						if(result_x.contains(val)) {
							map.put("handle_x_result", 0);							
						} else {
							map.put("handle_x_result", 1);	
						}
						map.put("people_x_handle", -1);
						t_zb_pl_resDao.add_pl_x_res(map);
					}

					//对DISPLACE_Y进行处理	
					map = new HashMap<String, Object>();
					map.put("id", id);
					map.put("startTime", startTime);
					map.put("endTime", endTime);
					dataList = t_zb_pl_forehandleDao.getDataYByIdTm(map);
					System.out.println("----y"+dataList.size());
					//没有数据
					if(dataList.size() == 0)
						continue;
					
					//得到一项
					x_old = new double[dataList.size()];
					for (int i = 0; i < x_old.length; i++) {
						x_old[i] = dataList.get(i).getDisplace_y().doubleValue();
					}
					
					//四分法处理,得到异常值
					result_x = dpp.outlier_clear(x_old);
					System.out.println(result_x.size()+"--------");
					
					for (T_ZB_PL_ForeHandle data : dataList) {
						map = new HashMap<String, Object>();
						double val = data.getDisplace_y().doubleValue();
						
						map.put("instr_no", id);
						map.put("dt", data.getDt());
						map.put("result_id", genId);
						map.put("handle_time", handleTime);
						map.put("displace_y", val);
						//异常值
						if(result_x.contains(val)) {
							map.put("handle_y_result", 0);							
						} else {
							map.put("handle_y_result", 1);	
						}
						map.put("people_y_handle", -1);
						t_zb_pl_resDao.update_pl_y_res(map);
					}

					
				}
				
			} 
			
			Map<String, Object> map = new HashMap<String, Object>();
//System.out.println("all"+t_zb_pl_resDao.getAllCountByResultId(genId));
//System.out.println("exception"+t_zb_pl_resDao.getExceptionCountByResultId(genId));;
			map.put("id", genId);
			map.put("dt", handleTime);
			int all = t_zb_pl_resDao.getAllCountByResultId(genId);
			int exception = t_zb_pl_resDao.getExceptionCountByResultId(genId);
			
			String desc = "处理时间" + startTime +"至"+ endTime +"，" +
							"测点"+pointList.length+"个，" +
							"处理记录"+all+"条，" +
							"正常记录"+(all-exception)+"条、" +
							"异常记录"+exception+"条。";
			map.put("resultdiscript", desc);
			//处理完成 1
			map.put("statu", "0");
			preData.addPreResult(map);
			String res = "preId="+genId;//+"&desc1=预处理时间: "+handleTime+
//						"&desc2=测点"+pointList.length+"个，" +
//						"处理记录"+all+"条，" +
//						"正常记录"+(all-exception)+"条、" +
//						"异常记录"+exception+"条。";//+"&startTime="+startTime+"&endTime="+endTime+
//					"&pointLength="+pointList.length+"&totalNum="+all+"&correctNum="+(all-exception)+
//					"&errorNum="+exception;
			JSONObject jsObj=new JSONObject();
			jsObj.put("res", res);
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(jsObj.toString());
		}
		catch (Exception e) {
				// TODO: handle exception
		}
	}

	public void getLatestTime() throws IOException{ //获取监测点最新的预处理的时间
		String pointIdString = request.getParameter("pointtemp");
		String[] pointList;
		if(pointIdString.equals("")){//如果传递监测点为空，则根据坝段和监测项查询所有的点
			String title=request.getParameter("title").split("_")[2];
			String dam=request.getParameter("dam");
			String monitoritemtype=request.getParameter("monitoritemtype");
			Map<String, String> map=new HashMap<String, String>();
			map.put("dam", dam);
			map.put("title", title);
			map.put("type", monitoritemtype);
			List<DEFINSSORT> iswhereList=definssortDao.getPoint(map);
			pointList=new String[iswhereList.size()];
			for (int i = 0; i < iswhereList.size(); i++) {
				pointList[i]=iswhereList.get(i).getDesigncode().trim();
			}
		}
		else
			pointList = pointIdString.split(",");
		String maxtime ="0";
		for(int i=0;i<pointList.length;i++){
			String time=t_zb_pl_resDao.getLatestTime(pointList[i]);
			if(time!=""&&time!=null&&time.compareTo(maxtime)>0)
				maxtime=time;
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(maxtime.split(" ")[0]);
		
	}
	
	public IT_ZB_PL_ForeHandleDao getT_zb_pl_forehandleDao() {
		return t_zb_pl_forehandleDao;
	}

	public void setT_zb_pl_forehandleDao(IT_ZB_PL_ForeHandleDao tZbPlForehandleDao) {
		t_zb_pl_forehandleDao = tZbPlForehandleDao;
	}
	
	public IT_ZB_PL_ResDao getT_zb_pl_resDao() {
		return t_zb_pl_resDao;
	}

	public void setT_zb_pl_resDao(IT_ZB_PL_ResDao tZbPlResDao) {
		t_zb_pl_resDao = tZbPlResDao;
	}

	public IPRERESULTDao getPreData() {
		return preData;
	}

	public void setPreData(IPRERESULTDao preData) {
		this.preData = preData;
	}

	public IT_ZB_PLDao getT_zb_plDao() {
		return t_zb_plDao;
	}

	public void setT_zb_plDao(IT_ZB_PLDao t_zb_plDao) {
		this.t_zb_plDao = t_zb_plDao;
	}

	
	
}
