package Entity;

public class AutoPreHandleInfo {
	private String INSTR_NO;
	private String DT;
	private String Component;
	private String Original;
	private String PreHandle;
	private int mark;

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

	public String getComponent() {
		return Component;
	}

	public void setComponent(String component) {
		Component = component;
	}

	public String getOriginal() {
		return Original;
	}

	public void setOriginal(String original) {
		Original = original;
	}

	public String getPreHandle() {
		return PreHandle;
	}

	public void setPreHandle(String preHandle) {
		PreHandle = preHandle;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}
}
