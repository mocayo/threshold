package Entity;

import java.sql.Timestamp;

public class LstzzParas {
	private int step;
	private Timestamp nextDate;
	
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public String getNextDate() {
		return nextDate.toString();
	}
	public void setNextDate(Timestamp nextDate) {
		this.nextDate = nextDate;
	}
	
	
}
