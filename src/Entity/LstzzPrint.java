package Entity;

import java.sql.Timestamp;

public class LstzzPrint {
	private String instr_no;
	private String wl;
	private Timestamp dt;
	private float realV;
	private int gradation;
	
	public String getInstr_no() {
		return instr_no;
	}
	public void setInstr_no(String instrNo) {
		instr_no = instrNo;
	}
	public String getWl() {
		return wl;
	}
	public void setWl(String wl) {
		this.wl = wl;
	}
	public String getDt() {
		return dt.toString();
	}
	public void setDt(Timestamp dt) {
		this.dt = dt;
	}
	public float getRealV() {
		return realV;
	}
	public void setRealV(float realV) {
		this.realV = realV;
	}
	public int getGradation() {
		return gradation;
	}
	public void setGradation(int gradation) {
		this.gradation = gradation;
	}
	
	
}
