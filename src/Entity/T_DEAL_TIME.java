package Entity;

import java.sql.Timestamp;

public class T_DEAL_TIME {
	private String instr_no;
	private Timestamp dt;
	
	public String getInstr_no() {
		return instr_no;
	}
	public void setInstr_no(String instr_no) {
		this.instr_no = instr_no;
	}
	public Timestamp getDt() {
		return dt;
	}
	public void setDt(Timestamp dt) {
		this.dt = dt;
	}
	
}
