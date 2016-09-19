package Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class T_SIN_PRE {
	private String instr_no ;
	private Timestamp dt;
	private BigDecimal wl;
	private BigDecimal realVal_r1;
	private BigDecimal prehandle_r1;
	private BigDecimal preVal_r1;
	private BigDecimal err_rate_r1;
	private int modelId_r1;
	private int mark_r1;
	private int isBest_r1;
	
	private BigDecimal realVal_r2;
	private BigDecimal prehandle_r2;
	private BigDecimal preVal_r2;
	private BigDecimal err_rate_r2;
	private int modelId_r2;
	private int mark_r2;
	private int isBest_r2;
	
	public String getInstr_no() {
		return instr_no;
	}
	public void setInstr_no(String instrNo) {
		instr_no = instrNo;
	}
	public String getDt() {
		return dt.toString();
	}
	public void setDt(Timestamp dt) {
		this.dt = dt;
	}
	public BigDecimal getWl() {
		return wl;
	}
	public void setWl(BigDecimal wl) {
		this.wl = wl;
	}
	public BigDecimal getRealVal_r1() {
		return realVal_r1;
	}
	public void setRealVal_r1(BigDecimal realVal_r1) {
		this.realVal_r1 = realVal_r1;
	}
	public BigDecimal getPrehandle_r1() {
		return prehandle_r1;
	}
	public void setPrehandle_r1(BigDecimal prehandle_r1) {
		this.prehandle_r1 = prehandle_r1;
	}
	public BigDecimal getPreVal_r1() {
		return preVal_r1;
	}
	public void setPreVal_r1(BigDecimal preVal_r1) {
		this.preVal_r1 = preVal_r1;
	}
	public BigDecimal getErr_rate_r1() {
		return err_rate_r1;
	}
	public void setErr_rate_r1(BigDecimal err_rate_r1) {
		this.err_rate_r1 = err_rate_r1;
	}
	public int getModelId_r1() {
		return modelId_r1;
	}
	public void setModelId_r1(int modelId_r1) {
		this.modelId_r1 = modelId_r1;
	}
	public int getMark_r1() {
		return mark_r1;
	}
	public void setMark_r1(int mark_r1) {
		this.mark_r1 = mark_r1;
	}
	public int getIsBest_r1() {
		return isBest_r1;
	}
	public void setIsBest_r1(int isBest_r1) {
		this.isBest_r1 = isBest_r1;
	}
	public BigDecimal getRealVal_r2() {
		return realVal_r2;
	}
	public void setRealVal_r2(BigDecimal realVal_r2) {
		this.realVal_r2 = realVal_r2;
	}
	public BigDecimal getPrehandle_r2() {
		return prehandle_r2;
	}
	public void setPrehandle_r2(BigDecimal prehandle_r2) {
		this.prehandle_r2 = prehandle_r2;
	}
	public BigDecimal getPreVal_r2() {
		return preVal_r2;
	}
	public void setPreVal_r2(BigDecimal preVal_r2) {
		this.preVal_r2 = preVal_r2;
	}
	public BigDecimal getErr_rate_r2() {
		return err_rate_r2;
	}
	public void setErr_rate_r2(BigDecimal err_rate_r2) {
		this.err_rate_r2 = err_rate_r2;
	}
	public int getModelId_r2() {
		return modelId_r2;
	}
	public void setModelId_r2(int modelId_r2) {
		this.modelId_r2 = modelId_r2;
	}
	public int getMark_r2() {
		return mark_r2;
	}
	public void setMark_r2(int mark_r2) {
		this.mark_r2 = mark_r2;
	}
	public int getIsBest_r2() {
		return isBest_r2;
	}
	public void setIsBest_r2(int isBest_r2) {
		this.isBest_r2 = isBest_r2;
	}
	
}