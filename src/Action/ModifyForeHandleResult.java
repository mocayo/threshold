package Action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Dao.IT_ZB_PL_ForeHandleDao;

import util.Action;

public class ModifyForeHandleResult extends Action{
	private IT_ZB_PL_ForeHandleDao t_zb_pl_forehandleDao;
	
	public void execute() throws IOException{
		try {
			String instr_no=request.getParameter("Instr_no");
			String forehandle_time=request.getParameter("ForeHandle_Time");
			int result =Integer.parseInt(request.getParameter("People_Result"));
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("instr_no", instr_no);
			map.put("forehandle_time", forehandle_time);
			map.put("result", result);
			t_zb_pl_forehandleDao.modifyForeHandleResult(map);
			response.getWriter().write("true");
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return;
		}
	}

	public IT_ZB_PL_ForeHandleDao getT_zb_pl_forehandleDao() {
		return t_zb_pl_forehandleDao;
	}

	public void setT_zb_pl_forehandleDao(
			IT_ZB_PL_ForeHandleDao t_zb_pl_forehandleDao) {
		this.t_zb_pl_forehandleDao = t_zb_pl_forehandleDao;
	}

}
