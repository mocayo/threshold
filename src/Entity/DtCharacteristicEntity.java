package Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class DtCharacteristicEntity {
	private String instr_no ;
	private int wl;
	private Timestamp dt;
	private float realV;
	private int gradation;
	
	public String getInstr_no() {
		return instr_no;
	}
	public void setInstr_no(String instr_no) {
		this.instr_no = instr_no;
	}
	public int getGradation() {
		return gradation;
	}
	public void setGradation(int gradation) {
		this.gradation = gradation;
	}
	public Timestamp getDt() {
		return dt;
	}
	public void setDt(Timestamp dt) {
		this.dt = dt;
	}
	public int getWl() {
		return wl;
	}
	public void setWl(int wl) {
		this.wl = wl;
	}
	public float getRealV() {
		return realV;
	}
	public void setRealV(float realV) {
		this.realV = realV;
	}

}
