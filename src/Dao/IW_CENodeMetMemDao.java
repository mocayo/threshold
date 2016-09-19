package Dao;

import java.util.List;
import java.util.Map;

import Entity.W_CENodeMetMem_Current;
import Entity.W_CENodeMetMem_Log;

public interface IW_CENodeMetMemDao {
	public void addCurrentGreen(Map<String, Object> map);//添加正常评判结果
	
	public void addCurrentOrange(Map<String, Object> map);//添加基本正常评判结果
	
	public void addCurrentRed(Map<String, Object> map);//添加異常评判结果
	
	public void addSigemaGreen(Map<String, Object> map);//添加正常评判结果
	
	public void addSigemaOrange(Map<String, Object> map);//添加基本正常评判结果
	
	public void addSigemaRed(Map<String, Object> map);//添加異常评判结果
	
	public void addZero(Map<String, Object> map);//添加異常结果点
	
	public void dataTransfer();
	
	public void addCurrent();
	
	public void reSetCurrent();
	
	public void reSetTemp();
	
	public void addlstzz(Map<String, Object> map);//添加历史特征值
	
	public void addlixue3(Map<String, Object> map);//添加结构力学3
	
	public void addlixue12(Map<String, Object> map);//添加结构力学1&2
	
	public void addLog(String dt);//添加评判体制·日志
	
	public void updLog(String dt);//更新评判体制·日志
	
	public int countPreDate();
}
