package Entity;

public class ModelRecord {
	
	private String modelid;
	private String modelname;
	private String isdelete;
	private String modeltype;
	private String method;
	private String isadded;
	private String description;
	
	public String getIsadded() {
		return isadded;
	}
	public void setIsadded(String isadded) {
		this.isadded = isadded;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getModelid() {
		return modelid;
	}
	public void setModelid(String modelid) {
		this.modelid = modelid;
	}
	public String getModelname() {
		return modelname;
	}
	public void setModelname(String modelname) {
		this.modelname = modelname;
	}
	public String getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}
	public String getModeltype() {
		return modeltype;
	}
	public void setModeltype(String modeltype) {
		this.modeltype = modeltype;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
}
