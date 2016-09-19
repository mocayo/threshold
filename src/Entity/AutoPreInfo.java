package Entity;

import java.math.BigDecimal;

public class AutoPreInfo {
	private String instr_no;
	private String dt;
	private String model;
	private BigDecimal wl;
	private BigDecimal realVal;
	private BigDecimal preVal;
	private String comp;
	private String err_rate;
	private String name_r1;
	private String name_r2;
	private String name_r3;
	private String status;
	private String statics;

	public String getComp() {
		return comp;
	}

	public void setComp(String comp) {
		this.comp = comp;
	}

	public String getErr_rate() {
		return err_rate;
	}

	public void setErr_rate(String err_rate) {
		this.err_rate = err_rate;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getInstr_no() {
		return instr_no;
	}

	public void setInstr_no(String instr_no) {
		this.instr_no = instr_no;
	}

	public String getDt() {
		if (dt.length() >= 10)
			return dt.substring(0, 10);
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	public BigDecimal getWl() {
		return wl;
	}

	public void setWl(BigDecimal wl) {
		this.wl = wl;
	}

	public BigDecimal getRealVal() {
		return realVal;
	}

	public void setRealVal(BigDecimal realVal) {
		this.realVal = realVal;
	}

	public String getName_r1() {
		return name_r1;
	}

	public void setName_r1(String name_r1) {
		this.name_r1 = name_r1;
	}

	public String getName_r2() {
		return name_r2;
	}

	public void setName_r2(String name_r2) {
		this.name_r2 = name_r2;
	}

	public String getName_r3() {
		return name_r3;
	}

	public void setName_r3(String name_r3) {
		this.name_r3 = name_r3;
	}

	public BigDecimal getPreVal() {
		return preVal;
	}

	public void setPreVal(BigDecimal preVal) {
		this.preVal = preVal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatics() {
		return statics;
	}

	public void setStatics(String statics) {
		this.statics = statics;
	}

}
