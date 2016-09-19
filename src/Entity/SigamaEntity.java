package Entity;

import java.sql.Timestamp;

public class SigamaEntity {
	private String instr_no;
	private String component;
	private Timestamp dt;
	private String stdevpVal;
	private int SV;
	
	
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
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getStdevpVal() {
		return stdevpVal;
	}
	public void setStdevpVal(String stdevpVal) {
		this.stdevpVal = stdevpVal;
	}
	public int getSV() {
		return SV;
	}
	public void setSV(int sV) {
		SV = sV;
	}
	
}
