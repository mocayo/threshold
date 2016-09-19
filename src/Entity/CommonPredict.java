package Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CommonPredict {
	private String instr_no ;
	private Timestamp dt;
	private String prediction_id;
	
	private BigDecimal wl;
	
	private float r1;
	private int r1_step;
	private String r1_model;
	private BigDecimal r1_pred;
	private BigDecimal r1_error;
	private BigDecimal r1_err_rate;
	private BigDecimal r1_err_level;
	private String r1_mid_result;

	private float r2;
	private int r2_step;
	private String r2_model;
	private BigDecimal r2_pred;
	private BigDecimal r2_error;
	private BigDecimal r2_err_rate;
	private BigDecimal r2_err_level;
	private String r2_mid_result;
	
	private float r3;
	private int r3_step;
	private String r3_model;
	private BigDecimal r3_pred;
	private BigDecimal r3_error;
	private BigDecimal r3_err_rate;
	private BigDecimal r3_err_level;
	private String r3_mid_result;
	
	private String name_r1;
	private String name_r2;
	private String name_r3;
	
	private String note;

	public String getInstr_no() {
		return instr_no;
	}

	public void setInstr_no(String instrNo) {
		instr_no = instrNo;
	}

	public String getDt() {
		return dt.toString();
	}

	public void setDt(Timestamp dt) {
		this.dt = dt;
	}

	public String getPrediction_id() {
		return prediction_id;
	}

	public void setPrediction_id(String predictionId) {
		prediction_id = predictionId;
	}

	public BigDecimal getWl() {
		return wl;
	}

	public void setWl(BigDecimal wl) {
		this.wl = wl;
	}

	public float getR1() {
		return r1;
	}

	public void setR1(float r1) {
		this.r1 = r1;
	}

	public int getR1_step() {
		return r1_step;
	}

	public void setR1_step(int r1Step) {
		r1_step = r1Step;
	}

	public String getR1_model() {
		return r1_model;
	}

	public void setR1_model(String r1Model) {
		r1_model = r1Model;
	}

	public BigDecimal getR1_pred() {
		return r1_pred;
	}

	public void setR1_pred(BigDecimal r1Pred) {
		r1_pred = r1Pred;
	}

	public BigDecimal getR1_error() {
		return r1_error;
	}

	public void setR1_error(BigDecimal r1Error) {
		r1_error = r1Error;
	}

	public BigDecimal getR1_err_rate() {
		return r1_err_rate;
	}

	public void setR1_err_rate(BigDecimal r1ErrRate) {
		r1_err_rate = r1ErrRate;
	}

	public BigDecimal getR1_err_level() {
		return r1_err_level;
	}

	public void setR1_err_level(BigDecimal r1ErrLevel) {
		r1_err_level = r1ErrLevel;
	}

	public String getR1_mid_result() {
		return r1_mid_result;
	}

	public void setR1_mid_result(String r1MidResult) {
		r1_mid_result = r1MidResult;
	}

	public float getR2() {
		return r2;
	}

	public void setR2(float r2) {
		this.r2 = r2;
	}

	public int getR2_step() {
		return r2_step;
	}

	public void setR2_step(int r2Step) {
		r2_step = r2Step;
	}

	public String getR2_model() {
		return r2_model;
	}

	public void setR2_model(String r2Model) {
		r2_model = r2Model;
	}

	public BigDecimal getR2_pred() {
		return r2_pred;
	}

	public void setR2_pred(BigDecimal r2Pred) {
		r2_pred = r2Pred;
	}

	public BigDecimal getR2_error() {
		return r2_error;
	}

	public void setR2_error(BigDecimal r2Error) {
		r2_error = r2Error;
	}

	public BigDecimal getR2_err_rate() {
		return r2_err_rate;
	}

	public void setR2_err_rate(BigDecimal r2ErrRate) {
		r2_err_rate = r2ErrRate;
	}

	public BigDecimal getR2_err_level() {
		return r2_err_level;
	}

	public void setR2_err_level(BigDecimal r2ErrLevel) {
		r2_err_level = r2ErrLevel;
	}

	public String getR2_mid_result() {
		return r2_mid_result;
	}

	public void setR2_mid_result(String r2MidResult) {
		r2_mid_result = r2MidResult;
	}

	public float getR3() {
		return r3;
	}

	public void setR3(float r3) {
		this.r3 = r3;
	}

	public int getR3_step() {
		return r3_step;
	}

	public void setR3_step(int r3Step) {
		r3_step = r3Step;
	}

	public String getR3_model() {
		return r3_model;
	}

	public void setR3_model(String r3Model) {
		r3_model = r3Model;
	}

	public BigDecimal getR3_pred() {
		return r3_pred;
	}

	public void setR3_pred(BigDecimal r3Pred) {
		r3_pred = r3Pred;
	}

	public BigDecimal getR3_error() {
		return r3_error;
	}

	public void setR3_error(BigDecimal r3Error) {
		r3_error = r3Error;
	}

	public BigDecimal getR3_err_rate() {
		return r3_err_rate;
	}

	public void setR3_err_rate(BigDecimal r3ErrRate) {
		r3_err_rate = r3ErrRate;
	}

	public BigDecimal getR3_err_level() {
		return r3_err_level;
	}

	public void setR3_err_level(BigDecimal r3ErrLevel) {
		r3_err_level = r3ErrLevel;
	}

	public String getR3_mid_result() {
		return r3_mid_result;
	}

	public void setR3_mid_result(String r3MidResult) {
		r3_mid_result = r3MidResult;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getName_r1() {
		return name_r1;
	}

	public void setName_r1(String name_r1) {
		this.name_r1 = name_r1;
	}

	public String getName_r2() {
		return name_r2;
	}

	public void setName_r2(String name_r2) {
		this.name_r2 = name_r2;
	}

	public String getName_r3() {
		return name_r3;
	}

	public void setName_r3(String name_r3) {
		this.name_r3 = name_r3;
	}
	
}
