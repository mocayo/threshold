package Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class T_ZB_PL_RES {
	private String instr_no;
	private Timestamp dt;
	private String result_id;
	
	private Timestamp handle_time;
	private BigDecimal displace_x;
	private BigDecimal displace_y;
	private int handle_x_result;
	private int people_x_handle;
	private int handle_y_result;
	private int people_y_handle;
	
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
	public String getResult_id() {
		return result_id;
	}
	public void setResult_id(String resultId) {
		result_id = resultId;
	}
	public Timestamp getHandle_time() {
		return handle_time;
	}
	public void setHandle_time(Timestamp handleTime) {
		handle_time = handleTime;
	}
	public BigDecimal getDisplace_x() {
		return displace_x;
	}
	public void setDisplace_x(BigDecimal displaceX) {
		displace_x = displaceX;
	}
	public BigDecimal getDisplace_y() {
		return displace_y;
	}
	public void setDisplace_y(BigDecimal displaceY) {
		displace_y = displaceY;
	}
	public int getHandle_x_result() {
		return handle_x_result;
	}
	public void setHandle_x_result(int handleXResult) {
		handle_x_result = handleXResult;
	}
	public int getPeople_x_handle() {
		return people_x_handle;
	}
	public void setPeople_x_handle(int peopleXHandle) {
		people_x_handle = peopleXHandle;
	}
	public int getHandle_y_result() {
		return handle_y_result;
	}
	public void setHandle_y_result(int handleYResult) {
		handle_y_result = handleYResult;
	}
	public int getPeople_y_handle() {
		return people_y_handle;
	}
	public void setPeople_y_handle(int peopleYHandle) {
		people_y_handle = peopleYHandle;
	}
}
