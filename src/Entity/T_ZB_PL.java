package Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class T_ZB_PL {
	private String instr_no ;
	private Timestamp dt;
	private BigDecimal wl;
	private BigDecimal displace_x;
	private BigDecimal displace_y;
	private int displace_x_step;
	private String displace_x_model;
	private BigDecimal displace_x_pred;
	private BigDecimal displace_x_error;
	private BigDecimal displace_x_err_rate;
	private BigDecimal displace_x_err_level;
	private int displace_y_step;
	private String displace_y_model;
	private BigDecimal displace_y_pred;
	private BigDecimal displace_y_error;
	private BigDecimal displace_y_err_rate;
	private BigDecimal displace_y_err_level;
	private String displace_x_mid_result;
	private String displace_y_mid_result;
	private String note;
	private String prediction_id;
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
	public BigDecimal getDisplace_x() {
		return displace_x;
	}
	public void setDisplace_x(BigDecimal displace_x) {
		this.displace_x = displace_x;
	}
	public BigDecimal getDisplace_y() {
		return displace_y;
	}
	public void setDisplace_y(BigDecimal displace_y) {
		this.displace_y = displace_y;
	}
	public int getDisplace_x_step() {
		return displace_x_step;
	}
	public void setDisplace_x_step(int displace_x_step) {
		this.displace_x_step = displace_x_step;
	}
	public String getPrediction_id() {
		return prediction_id;
	}
	public void setPrediction_id(String prediction_id) {
		this.prediction_id = prediction_id;
	}
	public String getDisplace_x_model() {
		return displace_x_model;
	}
	public void setDisplace_x_model(String displace_x_model) {
		this.displace_x_model = displace_x_model;
	}
	public BigDecimal getDisplace_x_pred() {
		return displace_x_pred;
	}
	public void setDisplace_x_pred(BigDecimal displace_x_pred) {
		this.displace_x_pred = displace_x_pred;
	}
	public BigDecimal getDisplace_x_error() {
		return displace_x_error;
	}
	public void setDisplace_x_error(BigDecimal displace_x_error) {
		this.displace_x_error = displace_x_error;
	}
	public BigDecimal getDisplace_x_err_rate() {
		return displace_x_err_rate;
	}
	public void setDisplace_x_err_rate(BigDecimal displace_x_err_rate) {
		this.displace_x_err_rate = displace_x_err_rate;
	}
	public BigDecimal getDisplace_x_err_level() {
		return displace_x_err_level;
	}
	public void setDisplace_x_err_level(BigDecimal displace_x_err_level) {
		this.displace_x_err_level = displace_x_err_level;
	}
	public int getDisplace_y_step() {
		return displace_y_step;
	}
	public void setDisplace_y_step(int displace_y_step) {
		this.displace_y_step = displace_y_step;
	}
	public String getDisplace_y_model() {
		return displace_y_model;
	}
	public void setDisplace_y_model(String displace_y_model) {
		this.displace_y_model = displace_y_model;
	}
	public BigDecimal getDisplace_y_pred() {
		return displace_y_pred;
	}
	public void setDisplace_y_pred(BigDecimal displace_y_pred) {
		this.displace_y_pred = displace_y_pred;
	}
	public BigDecimal getDisplace_y_error() {
		return displace_y_error;
	}
	public void setDisplace_y_error(BigDecimal displace_y_error) {
		this.displace_y_error = displace_y_error;
	}
	public BigDecimal getDisplace_y_err_rate() {
		return displace_y_err_rate;
	}
	public void setDisplace_y_err_rate(BigDecimal displace_y_err_rate) {
		this.displace_y_err_rate = displace_y_err_rate;
	}
	public BigDecimal getDisplace_y_err_level() {
		return displace_y_err_level;
	}
	public void setDisplace_y_err_level(BigDecimal displace_y_err_level) {
		this.displace_y_err_level = displace_y_err_level;
	}
	public String getDisplace_x_mid_result() {
		return displace_x_mid_result;
	}
	public void setDisplace_x_mid_result(String displace_x_mid_result) {
		this.displace_x_mid_result = displace_x_mid_result;
	}
	public String getDisplace_y_mid_result() {
		return displace_y_mid_result;
	}
	public void setDisplace_y_mid_result(String displace_y_mid_result) {
		this.displace_y_mid_result = displace_y_mid_result;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public BigDecimal getWl() {
		return wl;
	}
	public void setWl(BigDecimal wl) {
		this.wl = wl;
	}
	
}
