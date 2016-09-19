package Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class T_ZB_PL_FINAL {
	private String instr_no;
	private Timestamp dt;
	private String result_id;
	
	private Timestamp handle_time;
	private BigDecimal displace_x;
	private BigDecimal displace_y;
	private int handle_x_result;
	private int handle_y_result;
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
	public String getResult_id() {
		return result_id;
	}
	public void setResult_id(String result_id) {
		this.result_id = result_id;
	}
	public Timestamp getHandle_time() {
		return handle_time;
	}
	public void setHandle_time(Timestamp handle_time) {
		this.handle_time = handle_time;
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
	public int getHandle_x_result() {
		return handle_x_result;
	}
	public void setHandle_x_result(int handle_x_result) {
		this.handle_x_result = handle_x_result;
	}
	public int getHandle_y_result() {
		return handle_y_result;
	}
	public void setHandle_y_result(int handle_y_result) {
		this.handle_y_result = handle_y_result;
	}
	
}
