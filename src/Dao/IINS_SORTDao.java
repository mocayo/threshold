package Dao;

import java.util.List;

import Entity.INS_SORT;

public interface IINS_SORTDao {
		public List<INS_SORT> getIns_Sorts(int table_type,String dam_no);

}
