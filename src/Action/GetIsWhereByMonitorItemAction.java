package Action;


import java.io.IOException;
import java.util.List;

import util.Action;
import Dao.IPointInfoDao;

public class GetIsWhereByMonitorItemAction extends Action{
	private IPointInfoDao pointInfoDao;
	public void execute() throws IOException {
		String monitorItem=request.getParameter("monitorItem");
		List<String> iswhere=pointInfoDao.getIsWhereByMonitorItem(monitorItem);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(iswhere.toString());
	}
	public IPointInfoDao getPointInfoDao() {
		return pointInfoDao;
	}
	public void setPointInfoDao(IPointInfoDao pointInfoDao) {
		this.pointInfoDao = pointInfoDao;
	}
}
