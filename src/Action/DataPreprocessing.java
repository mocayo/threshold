package Action;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Action;
import util.PageControl;
import Dao.ICommonPreHandleBatchDao;
import Dao.ICommonPreHandleFinalDao;
import Dao.ICommonPreHandleResultDao;
import Dao.IPRERESULTDao;
import Dao.IT_DEAL_TIMEDao;
import Dao.IT_ZB_PL_FINALDao;
import Dao.IT_ZB_PL_ResDao;
import Entity.CommonPreHandleBatch;
import Entity.T_DEAL_TIME;
import Entity.T_DEAL_TIMEshow;
import Entity.T_PRE_RESULT;
import Entity.T_ZB_PL_FINAL;
import Entity.T_ZB_PL_RES;

public class DataPreprocessing extends Action {

	private int len = 38;// 每页条数
	private PageControl pc;// 页面控制
	private int cpage;// 当前页
	private String sjType;
	private IT_DEAL_TIMEDao dealTimeDao;
	private IPRERESULTDao preData;
	private IT_ZB_PL_ResDao t_zb_pl_resDao;
	private IT_ZB_PL_FINALDao t_zb_pl_finalDao;
	private ICommonPreHandleBatchDao commonPreHandleBatchDao;
	private ICommonPreHandleFinalDao commonPreHandleFinalDao;
	private ICommonPreHandleResultDao commonPreHandleResultDao;

	public void updateStatu() throws IOException {
		try {
			String resultId = request.getParameter("resultId");
			preData.updStatById(resultId);//该批次已经确认处理
			//将该批次处理的点的值写入最终表里
			List<T_ZB_PL_RES> list=t_zb_pl_resDao.getAllResultByResultId(resultId);
			for (T_ZB_PL_RES t_ZB_PL_RES : list) {
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("instr_no", t_ZB_PL_RES.getInstr_no());
				map.put("dt", t_ZB_PL_RES.getDt());
				map.put("result_id", t_ZB_PL_RES.getResult_id());
				map.put("handle_time", t_ZB_PL_RES.getHandle_time());
				map.put("displace_x", t_ZB_PL_RES.getDisplace_x());
				map.put("displace_y", t_ZB_PL_RES.getDisplace_y());
				if(t_ZB_PL_RES.getPeople_x_handle()==-1)
					map.put("handle_x_result", t_ZB_PL_RES.getHandle_x_result());
				else
					map.put("handle_x_result", t_ZB_PL_RES.getPeople_x_handle());
				if(t_ZB_PL_RES.getPeople_y_handle()==-1)
					map.put("handle_y_result", t_ZB_PL_RES.getHandle_y_result());
				else
					map.put("handle_y_result", t_ZB_PL_RES.getPeople_y_handle());
				
				List<T_ZB_PL_FINAL> list2= t_zb_pl_finalDao.findResult(map);
				if(list2.size()>0){
					t_zb_pl_finalDao.updateResult(map);
				}
				else {
					t_zb_pl_finalDao.insertResult(map);
				}
			}
			
			response.getWriter().write("1");
		} catch (NumberFormatException e) {
			response.getWriter().write("0");
		}
	}
	
	//根据批次Id查询该批次的信息
	public void getPreHandleInfoByBatchId() throws IOException {
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
	public void getAllPreHandleBatch() throws IOException {
		JSONArray jsonArray = new JSONArray();
		List<CommonPreHandleBatch> result = commonPreHandleBatchDao.getAllPreHandleBatch();
		for (int i = 0; i < result.size(); i++) {
			JSONObject jsObj = new JSONObject();
			jsObj.put("id", result.get(i).getId());
			jsObj.put("dt", result.get(i).getDt().toString());
			jsObj.put("resultdiscript", result.get(i).getResultdiscript());
			jsObj.put("statu", result.get(i).getStatu());// 1 处理完成，0 正在处理
			jsonArray.add(jsObj);
		}
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsonArray.toString());
	}

	public void getResultByTime() throws IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		String time = df.format(new Date());// new Date()为获取当前系统时间
		String starttime = request.getParameter("StartTime"), endtime = request
				.getParameter("EndTime");
		if (starttime == null || starttime == "")
			starttime = time;
		if (endtime == null || endtime == "")
			endtime = time;
		JSONArray jsonArray = new JSONArray();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("starttime", starttime + " 00:00:00");
		map.put("endtime", endtime + " 23:59:59");

		List<T_PRE_RESULT> result = preData.getResultByTime(map);

		for (int i = 0; i < result.size(); i++) {
			JSONObject jsonObject = JSONObject.fromObject(result.get(i));
			jsonArray.add(jsonObject);
		}
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsonArray.toString());
	}

	// 按测点查询预处理结果
	public void getResultByPoint() throws IOException {

		String point = request.getParameter("Point");
		JSONArray jsonArray = new JSONArray();

		List<T_PRE_RESULT> result = preData.getResultByPoint(point);

		for (int i = 0; i < result.size(); i++) {
			JSONObject jsonObject = JSONObject.fromObject(result.get(i));
			jsonArray.add(jsonObject);
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
		commonPreHandleFinalDao.deletePreHandleFinalByBatchId(map);//删除最终表
		commonPreHandleBatchDao.deletePreHandleBatchByBatchId(batchId);//删除该批次
		response.getWriter().write("0");
	}

	public String dataPreprocessList() {
		sjType = request.getParameter("sjType");
		String cpageStr = request.getParameter("cpage");
		if (cpageStr != null && cpageStr != "") {
			cpage = Integer.parseInt(cpageStr);
		} else {
			cpage = 1;
		}
		if (pc == null) {
			pc = new PageControl();
		}
		int start = (cpage - 1) * len;
		pc.setPagesize(len);
		pc.setCpage(cpage);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("len", len);
		List<T_DEAL_TIME> dealTimeList = dealTimeDao.dataPreprocess(map);
		pc.setTotalitem(dealTimeDao.countPreDate());
		List<T_DEAL_TIMEshow> newList = new ArrayList<T_DEAL_TIMEshow>();
		for (int i = 0; i < len; i = i + 2) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			T_DEAL_TIMEshow dealTimeshow = new T_DEAL_TIMEshow();
			dealTimeshow.setInstr_no1(dealTimeList.get(i).getInstr_no()
					.replaceAll(" ", ""));
			dealTimeshow.setDt1(sf.format(dealTimeList.get(i).getDt()));
			Date today = new Date();
			dealTimeshow.setNowDate1(sf.format(today));
			if (i + 1 < dealTimeList.size()) {
				dealTimeshow.setInstr_no2(dealTimeList.get(i + 1).getInstr_no()
						.replaceAll(" ", ""));
				dealTimeshow.setDt2(sf.format(dealTimeList.get(i + 1).getDt()));
				dealTimeshow.setNowDate2(sf.format(today));
			} else {
				dealTimeshow.setInstr_no2("");
				dealTimeshow.setDt2("");
				dealTimeshow.setNowDate2("");
				newList.add(dealTimeshow);
				break;
			}
			newList.add(dealTimeshow);
		}
		pc.setList(newList);
		response.setCharacterEncoding("utf8");
		return "dataPreprocess";
	}

	public IPRERESULTDao getPreData() {
		return preData;
	}

	public void setPreData(IPRERESULTDao preData) {
		this.preData = preData;
	}

	public ICommonPreHandleBatchDao getCommonPreHandleBatchDao() {
		return commonPreHandleBatchDao;
	}

	public void setCommonPreHandleBatchDao(
			ICommonPreHandleBatchDao commonPreHandleBatchDao) {
		this.commonPreHandleBatchDao = commonPreHandleBatchDao;
	}

	public IT_DEAL_TIMEDao getDealTimeDao() {
		return dealTimeDao;
	}

	public void setDealTimeDao(IT_DEAL_TIMEDao dealTimeDao) {
		this.dealTimeDao = dealTimeDao;
	}

	public IT_ZB_PL_ResDao getT_zb_pl_resDao() {
		return t_zb_pl_resDao;
	}

	public void setT_zb_pl_resDao(IT_ZB_PL_ResDao t_zb_pl_resDao) {
		this.t_zb_pl_resDao = t_zb_pl_resDao;
	}

	public IT_ZB_PL_FINALDao getT_zb_pl_finalDao() {
		return t_zb_pl_finalDao;
	}

	public void setT_zb_pl_finalDao(IT_ZB_PL_FINALDao t_zb_pl_finalDao) {
		this.t_zb_pl_finalDao = t_zb_pl_finalDao;
	}

	public ICommonPreHandleFinalDao getCommonPreHandleFinalDao() {
		return commonPreHandleFinalDao;
	}

	public void setCommonPreHandleFinalDao(
			ICommonPreHandleFinalDao commonPreHandleFinalDao) {
		this.commonPreHandleFinalDao = commonPreHandleFinalDao;
	}

	public ICommonPreHandleResultDao getCommonPreHandleResultDao() {
		return commonPreHandleResultDao;
	}

	public void setCommonPreHandleResultDao(
			ICommonPreHandleResultDao commonPreHandleResultDao) {
		this.commonPreHandleResultDao = commonPreHandleResultDao;
	}
	
	
}
