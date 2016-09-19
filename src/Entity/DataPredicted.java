package Entity;

import java.sql.Timestamp;

public class DataPredicted {
	private String instr_no;
	private String tableName;
	private String isCompleted;
	
	public String getInstr_no() {
		return instr_no;
	}
	public void setInstr_no(String instrNo) {
		instr_no = instrNo;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getIsCompleted() {
		return isCompleted;
	}
	public void setIsCompleted(String isCompleted) {
		this.isCompleted = isCompleted;
	}
	
}
