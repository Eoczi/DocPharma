package pharmacy;

public class Pill {
	private int medID,medQTY;
	private String medName,medComp;
	private Double medPrice;
	
	public Pill(int medID, String medName, int medQTY, Double medPrice,  String medComp) {
		this.medID = medID;
		this.medQTY = medQTY;
		this.medName = medName;
		this.medComp = medComp;
		this.medPrice = medPrice;
	}

	@Override
	public String toString() {
		return "Pill [medID=" + medID + ", medQTY=" + medQTY + ", medName=" + medName + ", medComp=" + medComp
				+ ", medPrice=" + medPrice + "]";
	}

	public int getMedID() {
		return medID;
	}

	public int getMedQTY() {
		return medQTY;
	}

	public String getMedName() {
		return medName;
	}

	public String getMedComp() {
		return medComp;
	}

	public Double getMedPrice() {
		return medPrice;
	}

	public void setMedID(int medID) {
		this.medID = medID;
	}

	public void setMedQTY(int medQTY) {
		this.medQTY = medQTY;
	}

	public void setMedName(String medName) {
		this.medName = medName;
	}

	public void setMedComp(String medComp) {
		this.medComp = medComp;
	}

	public void setMedPrice(Double medPrice) {
		this.medPrice = medPrice;
	}
	
	
}
