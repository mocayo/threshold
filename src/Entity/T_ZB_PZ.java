package Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class T_ZB_PZ {
	private String instr_no ;
	private Timestamp dt;
	private BigDecimal pressure;
	private BigDecimal tem;
	private BigDecimal pressure_pred;
	private BigDecimal pressure_rmse;
	private BigDecimal pressure_error;
	private int pressure_step;
	private String pressure_model;
	private BigDecimal pressure_err_rate;
	private BigDecimal pressure_err_level;
	private String pressure_mid_result;
	private String note;
	
	
	
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
	public BigDecimal getPressure() {
		return pressure;
	}
	public void setPressure(BigDecimal pressure) {
		this.pressure = pressure;
	}
	public BigDecimal getTem() {
		return tem;
	}
	public void setTem(BigDecimal tem) {
		this.tem = tem;
	}
	public BigDecimal getPressure_pred() {
		return pressure_pred;
	}
	public void setPressure_pred(BigDecimal pressure_pred) {
		this.pressure_pred = pressure_pred;
	}
	public BigDecimal getPressure_rmse() {
		return pressure_rmse;
	}
	public void setPressure_rmse(BigDecimal pressure_rmse) {
		this.pressure_rmse = pressure_rmse;
	}
	public BigDecimal getPressure_error() {
		return pressure_error;
	}
	public void setPressure_error(BigDecimal pressure_error) {
		this.pressure_error = pressure_error;
	}
	public int getPressure_step() {
		return pressure_step;
	}
	public void setPressure_step(int pressure_step) {
		this.pressure_step = pressure_step;
	}
	public String getPressure_model() {
		return pressure_model;
	}
	public void setPressure_model(String pressure_model) {
		this.pressure_model = pressure_model;
	}
	public BigDecimal getPressure_err_rate() {
		return pressure_err_rate;
	}
	public void setPressure_err_rate(BigDecimal pressure_err_rate) {
		this.pressure_err_rate = pressure_err_rate;
	}
	public BigDecimal getPressure_err_level() {
		return pressure_err_level;
	}
	public void setPressure_err_level(BigDecimal pressure_err_level) {
		this.pressure_err_level = pressure_err_level;
	}
	public String getPressure_mid_result() {
		return pressure_mid_result;
	}
	public void setPressure_mid_result(String pressure_mid_result) {
		this.pressure_mid_result = pressure_mid_result;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
