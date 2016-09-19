package Dao;

import java.util.List;

import Entity.TABLE_TYPE;

public interface ITABLE_TYPEDao {
		public List<TABLE_TYPE> getTable_Types();
		
		public String getTypesByInstr(String table);
		
}
