package Action;

import java.io.IOException;
import java.util.List;

import Dao.IINS_SORTDao;
import Entity.INS_SORT;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import util.Action;

public class GetInsSort extends Action {
	private IINS_SORTDao ins_sortDao;

	public IINS_SORTDao getIns_sortDao() {
		return ins_sortDao;
	}

	public void setIns_sortDao(IINS_SORTDao ins_sortDao) {
		this.ins_sortDao = ins_sortDao;
	}

	public void execute() throws IOException {
		try {
			int table_type=Integer.parseInt(request.getParameter("table_type"));
			String dam_no=request.getParameter("dam_no");
			
			List<INS_SORT> list;
				list=ins_sortDao.getIns_Sorts(table_type,dam_no);
			JSONArray jsonArray=new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				JSONObject jsObj=JSONObject.fromObject(list.get(i));
				jsonArray.add(jsObj);
			}
			response.getWriter().write(jsonArray.toString());
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return;
		}
	}
}