package Action;

import java.io.IOException;

import util.Action;
import Dao.ISIN_PRE_AUTODao;
import Dao.ITestDao;

public class TestAction extends Action{
	private ISIN_PRE_AUTODao sin_preAutoDao;
	
	
	
//	private ITestDao testDao;
//	
//	public ITestDao getTestDao() {
//		return testDao;
//	}
//
//	public void setTestDao(ITestDao testDao) {
//		this.testDao = testDao;
//	}
//
//	public void execute() throws IOException {
//		testDao.StoredProcedure();
//		response.getWriter().write(0);
//	}

	public ISIN_PRE_AUTODao getSin_preAutoDao() {
		return sin_preAutoDao;
	}

	public void setSin_preAutoDao(ISIN_PRE_AUTODao sin_preAutoDao) {
		this.sin_preAutoDao = sin_preAutoDao;
	}
}
