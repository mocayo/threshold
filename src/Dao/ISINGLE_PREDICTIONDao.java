package Dao;

import java.util.List;
import java.util.Map;

import Entity.SINGLE_PREDICTION;

public interface ISINGLE_PREDICTIONDao {
		public List<SINGLE_PREDICTION> getSingle_predictions(Map<String, Object> map);
		
		public List<SINGLE_PREDICTION> getPreById(String id);
		
		public List<SINGLE_PREDICTION> getAllforPrint();
		
		public void del_singlepre(String id);
		
		public void del_result(Map<String, Object> map);
		
		public void add_singlepre(Map<String, Object> map);
		
		public int countPre();
}
