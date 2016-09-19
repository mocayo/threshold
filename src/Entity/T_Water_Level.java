package Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class T_Water_Level {
	private Timestamp dt;
	private BigDecimal wl;
	public Timestamp getDt() {
		return dt;
	}
	public void setDt(Timestamp dt) {
		this.dt = dt;
	}
	public String getWl() {
		return String.valueOf(wl);
	}
	public void setWl(BigDecimal wl) {
		this.wl = wl;
	}
	
}
