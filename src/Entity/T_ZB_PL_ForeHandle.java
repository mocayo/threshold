package Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class T_ZB_PL_ForeHandle {
	private String instr_no;
	private Timestamp dt;
	private  BigDecimal displace_x;
	private  BigDecimal displace_y;
	private Timestamp handle_time;
	private boolean handle_result;
	private int people_handle;
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
	public Timestamp getHandle_time() {
		return handle_time;
	}
	public void setHandle_time(Timestamp handle_time) {
		this.handle_time = handle_time;
	}
	public boolean isHandle_result() {
		return handle_result;
	}
	public void setHandle_result(boolean handle_result) {
		this.handle_result = handle_result;
	}
	public int getPeople_handle() {
		return people_handle;
	}
	public void setPeople_handle(int people_handle) {
		this.people_handle = people_handle;
	}
	
	
}
