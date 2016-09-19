package Dao;

import java.util.List;
import java.util.Map;

import Entity.SigamaEntity;

public interface ISigamaDao {
	public void addGreen(Map<String, Object> map);//添加正常测点
	
	public void addOrange(Map<String, Object> map);//添加基本正常测点
	
	public void addRed(Map<String, Object> map);//添加异常测点
	
	public void delSigama(Map<String, Object> map);//删除异常测点
	
	public void updBySigama(Map<String, Object> map);//超3sigama点不做统计
	
	public void updByMark(Map<String, Object> map);//异常点不做统计
	
}
