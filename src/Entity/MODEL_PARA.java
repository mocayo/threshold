package Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class MODEL_PARA {
	private String instr_no ;
	private Timestamp dt;
	private int mid;
	private boolean isemploy;
	private BigDecimal a;
	private BigDecimal b;
	private BigDecimal c;
	private BigDecimal d;
	private BigDecimal e;
	private BigDecimal f;
	private BigDecimal g;
	private BigDecimal h;
	private BigDecimal i;
	private BigDecimal j;
	private boolean isadjust;
	
	public String getInstr_no() {
		return instr_no;
	}
	public void setInstr_no(String instr_no) {
		this.instr_no = instr_no;
	}
	public String getDt() {
		return dt.toString();
	}
	public void setDt(Timestamp dt) {
		this.dt = dt;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public boolean isIsemploy() {
		return isemploy;
	}
	public void setIsemploy(boolean isemploy) {
		this.isemploy = isemploy;
	}
	public BigDecimal getA() {
		return a;
	}
	public void setA(BigDecimal a) {
		this.a = a;
	}
	public BigDecimal getB() {
		return b;
	}
	public void setB(BigDecimal b) {
		this.b = b;
	}
	public BigDecimal getC() {
		return c;
	}
	public void setC(BigDecimal c) {
		this.c = c;
	}
	public BigDecimal getD() {
		return d;
	}
	public void setD(BigDecimal d) {
		this.d = d;
	}
	public BigDecimal getE() {
		return e;
	}
	public void setE(BigDecimal e) {
		this.e = e;
	}
	public BigDecimal getF() {
		return f;
	}
	public void setF(BigDecimal f) {
		this.f = f;
	}
	public BigDecimal getG() {
		return g;
	}
	public void setG(BigDecimal g) {
		this.g = g;
	}
	public BigDecimal getH() {
		return h;
	}
	public void setH(BigDecimal h) {
		this.h = h;
	}
	public BigDecimal getI() {
		return i;
	}
	public void setI(BigDecimal i) {
		this.i = i;
	}
	public BigDecimal getJ() {
		return j;
	}
	public void setJ(BigDecimal j) {
		this.j = j;
	}
	public boolean isIsadjust() {
		return isadjust;
	}
	public void setIsadjust(boolean isadjust) {
		this.isadjust = isadjust;
	}
	
}
