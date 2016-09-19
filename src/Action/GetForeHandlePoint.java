package Action;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import Dao.IDEFINSSORTDao;
import Dao.IT_ZB_PL_ForeHandleDao;
import Entity.T_ZB_PL_ForeHandle;

import util.Action;

public class GetForeHandlePoint extends Action {
	public IDEFINSSORTDao definssortDao;
	public IT_ZB_PL_ForeHandleDao t_zb_pl_forehandleDao;
	public IT_ZB_PL_ForeHandleDao getT_zb_pl_forehandleDao() {
		return t_zb_pl_forehandleDao;
	}

	public void setT_zb_pl_forehandleDao(
			IT_ZB_PL_ForeHandleDao t_zb_pl_forehandleDao) {
		this.t_zb_pl_forehandleDao = t_zb_pl_forehandleDao;
	}
	public IDEFINSSORTDao getDefinssortDao() {
		return definssortDao;
	}

	public void setDefinssortDao(IDEFINSSORTDao definssortDao) {
		this.definssortDao = definssortDao;
	}
	
	public void execute() throws IOException {
		try {
			int type =Integer.parseInt(request.getParameter("Type"));
			List<String > pointlList=definssortDao.getDesignCode(type);
			int size=pointlList.size();
			String result[][]=new String[size][2];
			for (int i = 0; i < size; i++) {
				result[i][0]=pointlList.get(i).trim();
				String foreHandletime=t_zb_pl_forehandleDao.getForeHandle_Time(result[i][0]);
				if(foreHandletime==null){
					result[i][1]="";
				}
				else result[i][1]=foreHandletime.split(" ")[0];
			}
			JSONArray jsonArray=new JSONArray();
			jsonArray.add(result);
			response.getWriter().write(jsonArray.toString());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return;
		}
	}
}
