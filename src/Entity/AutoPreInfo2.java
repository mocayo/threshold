package Entity;

//2016年4月26日17:01:23
//测点分类修改之后的自动预测查询
public class AutoPreInfo2 {

	private String INSTR_NO;
	private String DT;
	private String ModelName;
	private String component;
	private String realVal;
	private String preVal;
	private String abs_err;
	private String err_rate;
	private String Rs;

	public String getRs() {
		return Rs;
	}

	public void setRs(String rs) {
		Rs = rs;
	}

	public String getINSTR_NO() {
		return INSTR_NO;
	}

	public void setINSTR_NO(String iNSTR_NO) {
		INSTR_NO = iNSTR_NO;
	}

	public String getDT() {
		if (DT.length() >= 10)
			return DT.substring(0, 10);
		else
			return DT;
	}

	public void setDT(String dT) {
		DT = dT;
	}

	public String getModelName() {
		return ModelName;
	}

	public void setModelName(String modelName) {
		ModelName = modelName;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getRealVal() {
		return realVal;
	}

	public void setRealVal(String realVal) {
		this.realVal = realVal;
	}

	public String getPreVal() {
		return preVal;
	}

	public void setPreVal(String preVal) {
		this.preVal = preVal;
	}

	public String getAbs_err() {
		return abs_err;
	}

	public void setAbs_err(String abs_err) {
		this.abs_err = abs_err;
	}

	public String getErr_rate() {
		return err_rate;
	}

	public void setErr_rate(String err_rate) {
		this.err_rate = err_rate;
	}

}
