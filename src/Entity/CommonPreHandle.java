package Entity;

import java.sql.Timestamp;

public class CommonPreHandle {
	private String instr_no;
	private Timestamp dt;
	private float r1;
	private float r2;
	private float r3;
	private String note;
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
	public float getR1() {
		return r1;
	}
	public void setR1(float r1) {
		this.r1 = r1;
	}
	public float getR2() {
		return r2;
	}
	public void setR2(float r2) {
		this.r2 = r2;
	}
	public float getR3() {
		return r3;
	}
	public void setR3(float r3) {
		this.r3 = r3;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
