package Entity;

public class ModelPoint {
	
	private String Instr_no;
	private String R = "";
	private String R1="";
	private String R2="";
	private String R3="";
	private String MonitorItem;
	private String PointType;
	
	public String getPointType() {
		return PointType;
	}
	public void setPointType(String pointType) {
		PointType = pointType;
	}
	public String getInstr_no() {
		return Instr_no;
	}
	public void setInstr_no(String instr_no) {
		Instr_no = instr_no;
	}
	public String getR() {
		return R;
	}
	public void setR(String r) {
		R = r;
	}
	public String getR1() {
		return R1;
	}
	public void setR1(String r1) {
		R1 = r1;
	}
	public String getR2() {
		return R2;
	}
	public void setR2(String r2) {
		R2 = r2;
	}
	public String getR3() {
		return R3;
	}
	public void setR3(String r3) {
		R3 = r3;
	}
	public String getMonitorItem() {
		return MonitorItem;
	}
	public void setMonitorItem(String monitorItem) {
		MonitorItem = monitorItem;
	}
	
}
