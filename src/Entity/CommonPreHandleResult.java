package Entity;

import java.sql.Timestamp;

public class CommonPreHandleResult {
	private String instr_no;
	private Timestamp dt;
	private String result_id;
	
	private Timestamp handle_time;
	private float r1;
	private float r2;
	private float r3;
	
	private int handle_r1_result;
	private int handle_r2_result;
	private int handle_r3_result;
	private int people_r1_handle;
	private int people_r2_handle;
	private int people_r3_handle;
	private int total_result;
	
	public int getTotal_result() {
		return total_result;
	}
	public void setTotal_result(int total_result) {
		this.total_result = total_result;
	}
	public int getHandle_r3_result() {
		return handle_r3_result;
	}
	public void setHandle_r3_result(int handle_r3_result) {
		this.handle_r3_result = handle_r3_result;
	}
	public int getPeople_r3_handle() {
		return people_r3_handle;
	}
	public void setPeople_r3_handle(int people_r3_handle) {
		this.people_r3_handle = people_r3_handle;
	}
	public float getR3() {
		return r3;
	}
	public void setR3(float r3) {
		this.r3 = r3;
	}
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
	public String getHandle_time() {
		return handle_time.toString();
	}
	public void setHandle_time(Timestamp handle_time) {
		this.handle_time = handle_time;
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
	public int getHandle_r1_result() {
		return handle_r1_result;
	}
	public void setHandle_r1_result(int handle_r1_result) {
		this.handle_r1_result = handle_r1_result;
	}
	public int getHandle_r2_result() {
		return handle_r2_result;
	}
	public void setHandle_r2_result(int handle_r2_result) {
		this.handle_r2_result = handle_r2_result;
	}
	public int getPeople_r1_handle() {
		return people_r1_handle;
	}
	public void setPeople_r1_handle(int people_r1_handle) {
		this.people_r1_handle = people_r1_handle;
	}
	public int getPeople_r2_handle() {
		return people_r2_handle;
	}
	public void setPeople_r2_handle(int people_r2_handle) {
		this.people_r2_handle = people_r2_handle;
	}
	
	
}
