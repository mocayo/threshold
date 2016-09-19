package Entity;

import java.sql.Date;

public class PointValue {
	private Date dt;
	private double prehandle;
	private double wl;

	public double getPrehandle() {
		return prehandle;
	}

	public void setPrehandle(double prehandle) {
		this.prehandle = prehandle;
	}

	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}

	public double getWl() {
		return wl;
	}

	public void setWl(double wl) {
		this.wl = wl;
	}

}
