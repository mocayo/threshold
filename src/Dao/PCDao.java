package Dao;

import java.util.ArrayList;
import java.util.List;

import javax.management.Query;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import util.PageControl;

public class PCDao {
	private static SqlSessionFactory sqlSessionFactory;
	
	
	public PageControl getAllData(String query, int cpage, int len)
	{
		// TODO Auto-generated method stub
		if (cpage == 0)
			cpage = 1;
		PageControl pc = new PageControl();
		int start = (cpage - 1) * len;
		pc.setPagesize(len);
		pc.setCpage(cpage);
		pc.setTotalitem(getCount(query));
		pc.setList(query(query, start, len));
		return pc;
	}
	
	private int getCount(String query)
	{
		List list = new ArrayList();
		list = excute(query,0,0,"count");
		return list.size();
	}
	
	private List query(String query, int start, int len){
		List list = new ArrayList();
		list = excute(query,start,len,"list");
		return list;
	}
	
	private List excute(String query, int start, int len,String type) {
		SqlSession session = sqlSessionFactory.openSession();
		List list = new ArrayList();
		try {
			if (query.equals("IT_ZB_PZ")){
				IT_ZB_PZDao dao = session.getMapper(IT_ZB_PZDao.class);
//				list = dao.multiPointScreening("");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

}
