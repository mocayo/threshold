package Entity;

import java.sql.Timestamp;

public class W_CENodeMetMem_Current {
	private String nodeID;
	private String itemName;
	private String evaMethod;
	private int memGreen;
	private int memOrange;
	private int memRed;
	private Timestamp date;
	private String note;
	
	
	public String getNodeID() {
		return nodeID;
	}
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getEvaMethod() {
		return evaMethod;
	}
	public void setEvaMethod(String evaMethod) {
		this.evaMethod = evaMethod;
	}
	public int getMemGreen() {
		return memGreen;
	}
	public void setMemGreen(int memGreen) {
		this.memGreen = memGreen;
	}
	public int getMemOrange() {
		return memOrange;
	}
	public void setMemOrange(int memOrange) {
		this.memOrange = memOrange;
	}
	public int getMemRed() {
		return memRed;
	}
	public void setMemRed(int memRed) {
		this.memRed = memRed;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
