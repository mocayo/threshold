package Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SINGLE_PREDICTION {
	private String id;
	private Timestamp dt;//预测时间
	private String det_instr;//监测仪器
	private String dam_no;//坝段
	private String obser_no;//测点编号
	private String pre_component;//预测分量
	private String startTime;//预留1
	private String endTime;//预留2
	private String staticsData;//统计信息
	private String date1Str;//一级预警日期统计
	private String date2Str;//二级预警日期统计
	private String date3Str;//三级预警日期统计
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDt() {
		return dt.toString();
	}
	public void setDt(Timestamp dt) {
		this.dt = dt;
	}
	public String getDet_instr() {
		return det_instr;
	}
	public void setDet_instr(String det_instr) {
		this.det_instr = det_instr;
	}
	public String getDam_no() {
		return dam_no;
	}
	public void setDam_no(String dam_no) {
		this.dam_no = dam_no;
	}
	public String getObser_no() {
		return obser_no;
	}
	public void setObser_no(String obser_no) {
		this.obser_no = obser_no;
	}
	public String getPre_component() {
		return pre_component;
	}
	public void setPre_component(String pre_component) {
		this.pre_component = pre_component;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStaticsData() {
		return staticsData;
	}
	public void setStaticsData(String staticsData) {
		this.staticsData = staticsData;
	}
	public String getDate1Str() {
		return date1Str;
	}
	public void setDate1Str(String date1Str) {
		this.date1Str = date1Str;
	}
	public String getDate2Str() {
		return date2Str;
	}
	public void setDate2Str(String date2Str) {
		this.date2Str = date2Str;
	}
	public String getDate3Str() {
		return date3Str;
	}
	public void setDate3Str(String date3Str) {
		this.date3Str = date3Str;
	}
	
}
