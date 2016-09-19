package Action;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import Dao.IMODEL_PARADao;
import Entity.MODEL_PARA;
import util.Action;

public class ModifyModelPara extends Action{
	private IMODEL_PARADao model_paraDao;
	public IMODEL_PARADao getModel_paraDao() {
		return model_paraDao;
	}

	public void setModel_paraDao(IMODEL_PARADao model_paraDao) {
		this.model_paraDao = model_paraDao;
	}
	public void execute() throws IOException {
		String instr_no=request.getParameter("INSTR_NO");
		Timestamp dt=new Timestamp(System.currentTimeMillis());
		try {
			/*Timestamp dt=Timestamp.valueOf(request.getParameter("DT"));*/
			int mid=Integer.parseInt(request.getParameter("MID"));
			BigDecimal a=new BigDecimal(request.getParameter("A"));
			BigDecimal b=new BigDecimal(request.getParameter("B"));
			BigDecimal c=new BigDecimal(request.getParameter("C"));
			BigDecimal d=new BigDecimal(request.getParameter("D"));
			BigDecimal e=new BigDecimal(request.getParameter("E"));
			BigDecimal f=new BigDecimal(request.getParameter("F"));
			BigDecimal g=new BigDecimal(request.getParameter("G"));
			BigDecimal h=new BigDecimal(request.getParameter("H"));
			BigDecimal i=new BigDecimal(request.getParameter("I"));
			BigDecimal j=new BigDecimal(request.getParameter("J"));
			
			MODEL_PARA model_PARA=new MODEL_PARA();
			model_PARA.setA(a);model_PARA.setB(b);model_PARA.setC(c);model_PARA.setD(d);
			model_PARA.setE(e);model_PARA.setF(f);model_PARA.setG(g);model_PARA.setH(h);
			model_PARA.setI(i);model_PARA.setJ(j);
			model_PARA.setInstr_no(instr_no);
			model_PARA.setMid(mid);
			model_PARA.setDt(dt);
			
			model_paraDao.modifymodelpara(model_PARA);
			JSONObject jsonObject=JSONObject.fromObject(model_PARA);
			JSONArray jsonArray=new JSONArray();
			jsonArray.add(jsonObject);
			response.getWriter().write(jsonArray.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return;
		}
	}
}
