package Entity;

import java.util.List;

public class SpecialValue {
	// format 2014-10-13~2015-07-31~2015-10-20
	private String waterYear;
	private int isUpper;
	private List<FiveValue> fvs;
	
	public String getWaterYear() {
		return waterYear;
	}

	public void setWaterYear(String waterYear) {
		this.waterYear = waterYear;
	}

	public int getIsUpper() {
		return isUpper;
	}

	public void setIsUpper(int isUpper) {
		this.isUpper = isUpper;
	}

	public List<FiveValue> getFvs() {
		return fvs;
	}

	public void setFvs(List<FiveValue> fvs) {
		this.fvs = fvs;
	}

}
