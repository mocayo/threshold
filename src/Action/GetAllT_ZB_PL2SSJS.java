package Action;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Action;
import Dao.IT_ZB_PL2SSJSDao;
import Entity.T_ZB_PL2;

public class GetAllT_ZB_PL2SSJS extends Action
{
	private IT_ZB_PL2SSJSDao t_zb_pl2SSJSDao;

	public IT_ZB_PL2SSJSDao getT_zb_pl2SSJSDao()
	{
		return t_zb_pl2SSJSDao;
	}

	public void setT_zb_pl2SSJSDao(IT_ZB_PL2SSJSDao tZbPl2SSJSDao)
	{
		t_zb_pl2SSJSDao = tZbPl2SSJSDao;
	}

	public void execute() throws Exception
	{
		String instr_no = request.getParameter("instr_no");
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("instr_no", instr_no);
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		
		List<T_ZB_PL2> list = t_zb_pl2SSJSDao.getAllT_ZB_PL2SSJS(map);

		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < list.size(); i++)
		{
			JSONObject jsonObject = JSONObject.fromObject(list.get(i));
			jsonArray.add(jsonObject);
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonArray.toString());
	}

	public void myExecute() throws Exception
	{
		List<String> list = t_zb_pl2SSJSDao.getAllInstr_no();

		JSONArray jsonArray = JSONArray.fromObject(list);

		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonArray.toString());
	}

	public void save2Local() throws Exception
	{
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String manage_seq = request.getParameter("manage_seq");

		//param2json做为配置参数保存到本地，拼接param2json字符串
		String param2json = "{ 'instr_nos':'";

		JSONArray jsonArray = JSONArray.fromObject(manage_seq);
		for (int i = 0; i < jsonArray.size(); i++)
		{
			param2json += jsonArray.getString(i) + ",";
		}
		param2json = param2json.substring(0, param2json.length() - 1);
		param2json += "', 'starttime':'";
		param2json += starttime + "', 'endtime':'";
		param2json += endtime + "'}";
		
		//将单引号替换为双引号，严格的json字符串形式
		param2json = param2json.replace("'", "\"");
		System.out.println(param2json);

		//写文件
		File file = new File(request.getRealPath("/SSJS") + "/manageParams.json");
		FileWriter filew = new FileWriter(file);
		filew.write(param2json);
		filew.flush();
		filew.close();

		response.setCharacterEncoding("utf-8");
		response.getWriter().write("成功");
	}

}
