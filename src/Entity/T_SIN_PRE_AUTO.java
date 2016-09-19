package Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class T_SIN_PRE_AUTO {
	private String instr_no ;
	private Timestamp dt;
	private BigDecimal wl;
	private BigDecimal realVal;
	private BigDecimal prehandle;
	private BigDecimal preVal;
	private BigDecimal err_rate;
	private String component;
	private String prediction_id;
	private int modelId;
	private int mark;
	private int step;
	private int isBest;
	private String status;
	private String statics;
	
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
	public BigDecimal getRealVal() {
		return realVal;
	}
	public void setRealVal(BigDecimal realVal) {
		this.realVal = realVal;
	}
	public BigDecimal getPrehandle() {
		return prehandle;
	}
	public void setPrehandle(BigDecimal prehandle) {
		this.prehandle = prehandle;
	}
	public BigDecimal getPreVal() {
		return preVal;
	}
	public void setPreVal(BigDecimal preVal) {
		this.preVal = preVal;
	}
	public BigDecimal getErr_rate() {
		return err_rate;
	}
	public void setErr_rate(BigDecimal err_rate) {
		this.err_rate = err_rate;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getPrediction_id() {
		return prediction_id;
	}
	public void setPrediction_id(String prediction_id) {
		this.prediction_id = prediction_id;
	}
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public int getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int getIsBest() {
		return isBest;
	}
	public void setIsBest(int isBest) {
		this.isBest = isBest;
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