package Entity;

public class PointDetailedResultInfo {
	private String INSTR_NO;
	private String DT;
	private String component;
	private int modelId;
	private String WL;//水位
	private String realVal;
	private String prehandle;//预处理值
	private int mark;
	private int step;
	private String preVal;//预测值
	private String err_rate;
	private int isBest;
	private String status;
	private String STATICS;
	public String getINSTR_NO() {
		return INSTR_NO;
	}
	public void setINSTR_NO(String iNSTR_NO) {
		INSTR_NO = iNSTR_NO;
	}
	public String getDT() {
		return DT;
	}
	public void setDT(String dT) {
		DT = dT;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public String getWL() {
		return WL;
	}
	public void setWL(String wL) {
		WL = wL;
	}
	public String getRealVal() {
		return realVal;
	}
	public void setRealVal(String realVal) {
		this.realVal = realVal;
	}
	public String getPrehandle() {
		return prehandle;
	}
	public void setPrehandle(String prehandle) {
		this.prehandle = prehandle;
	}
	public int getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public String getPreVal() {
		return preVal;
	}
	public void setPreVal(String preVal) {
		this.preVal = preVal;
	}
	public String getErr_rate() {
		return err_rate;
	}
	public void setErr_rate(String err_rate) {
		this.err_rate = err_rate;
	}
	public int getIsBest() {
		return isBest;
	}
	public void setIsBest(int isBest) {
		this.isBest = isBest;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSTATICS() {
		return STATICS;
	}
	public void setSTATICS(String sTATICS) {
		STATICS = sTATICS;
	}
	
}
