package Action;

import java.io.IOException;

import Dao.IMODEL_NAMEDao;
import util.Action;

public class ModifyModelState extends Action {
	private IMODEL_NAMEDao model_nameDao;

	public IMODEL_NAMEDao getModel_nameDao() {
		return model_nameDao;
	}

	public void setModel_nameDao(IMODEL_NAMEDao model_nameDao) {
		this.model_nameDao = model_nameDao;
	}

	public void execute() throws IOException{
		try {
			int mid=Integer.parseInt(request.getParameter("MID"));
			model_nameDao.modifymodelstate(mid);
			response.getWriter().write("1");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			response.getWriter().write("0");
		}
	}
}
