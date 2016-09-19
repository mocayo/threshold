package Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class T_OUTLIER {
	
	private String instr_no;
	private Timestamp dt;
	private BigDecimal displace_x;
	private BigDecimal displace_y;
	
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
}
