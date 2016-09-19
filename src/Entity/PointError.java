/**
 * @author qihai
 * @time 2016年5月5日20:53:45
 */
package Entity;

public class PointError {
	private String DT;
	private String err_rate;

	public String getDT() {
		if (DT.length() >= 10)
			return DT.substring(0, 10);
		else
			return DT;
	}

	public void setDT(String dT) {
		DT = dT;
	}

	public String getErr_rate() {
		return err_rate;
	}

	public void setErr_rate(String err_rate) {
		this.err_rate = err_rate;
	}
	
}
