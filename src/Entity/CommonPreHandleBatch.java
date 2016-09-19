package Entity;

import java.sql.Timestamp;

public class CommonPreHandleBatch {
	private String id;
	private Timestamp dt;
	private String resultdiscript;
	private String tablename;
	private Integer statu;
	private int setaside1;
	private int setaside2;
	
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDt() {
		String temp[]=String.valueOf(dt).split(":");
		return temp[0]+":"+temp[1];
	}
	public void setDt(Timestamp dt) {
		this.dt = dt;
	}
	public String getResultdiscript() {
		return resultdiscript;
	}
	public void setResultdiscript(String resultdiscript) {
		this.resultdiscript = resultdiscript;
	}
	public Integer getStatu() {
		return statu;
	}
	public void setStatu(Integer statu) {
		this.statu = statu;
	}
	public int getSetaside1() {
		return setaside1;
	}
	public void setSetaside1(int setaside1) {
		this.setaside1 = setaside1;
	}
	public int getSetaside2() {
		return setaside2;
	}
	public void setSetaside2(int setaside2) {
		this.setaside2 = setaside2;
	}
}
