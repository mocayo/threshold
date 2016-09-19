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
import Dao.IT_OUTLIERDao;
import Entity.T_DEAL_TIME;
import Entity.T_OUTLIER;
import util.Action;
import util.PageControl;
import Entity.T_DEAL_TIMEshow;

public class DataProcess extends Action{
	
	private int len = 10;//每页条数
	private PageControl pc;//页面控制
	private int cpage;//当前页
	private String sjTypes;
	private IT_OUTLIERDao outLierDao;
	
	public IT_OUTLIERDao getOutLierDao() {
		return outLierDao;
	}

	public void setOutLierDao(IT_OUTLIERDao outLierDao) {
		this.outLierDao = outLierDao;
	}

	public void execute() throws IOException {
		sjTypes = request.getParameter("sjTypes");
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
		List<T_OUTLIER> outlierList=outLierDao.dataOutlier(map);
		pc.setTotalitem(outLierDao.countOutlier());
		JSONArray jsonArray=new JSONArray();
		JSONObject jsObj1 = JSONObject.fromObject(pc);
		jsonArray.add(jsObj1);
		for (int i = 0; i < outlierList.size(); i++) {
			JSONObject jsObj2 = JSONObject.fromObject(outlierList.get(i));
			jsonArray.add(jsObj2);
		}
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsonArray.toString());
	}
}
