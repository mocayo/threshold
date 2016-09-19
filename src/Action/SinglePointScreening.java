package Action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Action;
import Dao.IDEFINSSORTDao;
import Entity.DEFINSSORT;

public class SinglePointScreening extends Action{
	private IDEFINSSORTDao definssortDao;
	public IDEFINSSORTDao getDefinssortDao() {
		return definssortDao;
	}
	public void setDefinssortDao(IDEFINSSORTDao definssortDao) {
		this.definssortDao = definssortDao;
	}

	public void execute() throws IOException {
		String dam=request.getParameter("dam");
		String title=request.getParameter("title");
		String type=request.getParameter("type");
		Map<String, String> map=new HashMap<String, String>();
		map.put("dam", dam);
		map.put("title", title);
		map.put("type", type);
		List<DEFINSSORT> iswhereList=definssortDao.getPoint(map);
		JSONArray jsonArray=new JSONArray();
		for (int i = 0; i < iswhereList.size(); i++) {
			JSONObject jsObj=new JSONObject();
			jsObj.put("code",iswhereList.get(i).getDesigncode().trim());
			jsonArray.add(jsObj);
		}
		
		response.setCharacterEncoding("utf8");
		response.getWriter().write(jsonArray.toString());
	}

}

/*JSONArray jsonArrayAll=new JSONArray();  //存放所有JSON数组
JSONArray jsonArray1=new JSONArray();  //存放测点实际值
JSONArray jsonArray2=new JSONArray();  //存放测点模型值
JSONArray jsonArray3=new JSONArray();//存放测点相对误差
JSONArray jsonArray4=new JSONArray();//存放测点绝对误差
JSONObject jsObj1=new JSONObject();
JSONObject jsObj2=new JSONObject();
JSONObject jsObj3=new JSONObject();
JSONObject jsObj4=new JSONObject();

jsObj1.put("code",iswhereList.get(i).getInstr_no().trim());
jsonArray1.add(jsObj1);
jsObj2.put("actualvalue",iswhereList.get(i).getPressure_pred().toString());
jsonArray2.add(jsObj2);
jsObj3.put("predvalue",iswhereList.get(i).getPressure_pred().toString());
jsonArray3.add(jsObj3);
jsObj4.put("rate",iswhereList.get(i).getPressure_err_rate().toString());
jsonArray4.add(jsObj4);
jsObj5.put("error",iswhereList.get(i).getPressure_error().toString());
jsonArray5.add(jsObj5);

jsonArrayAll.add(jsonArray1);
jsonArrayAll.add(jsonArray2);
jsonArrayAll.add(jsonArray3);
jsonArrayAll.add(jsonArray4);
jsonArrayAll.add(jsonArray5);*/