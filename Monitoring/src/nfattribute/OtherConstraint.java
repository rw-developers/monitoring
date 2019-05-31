package nfattribute;

public class OtherConstraint extends NFConstraint {
	public String type;
	public String fonction ;
	public String attribute ;
	public String op ;
	public float value;
	public NFAttribute NFattrbute;
	public OtherConstraint(int ID ,String type, String fonction, String attribute, String op, float value) {
		super();
		IDC = ID;
		this.type = type;
		this.fonction = fonction;
		this.attribute = attribute;
		this.op = op;
		this.value = value;
	}
	public String getType() { 
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFonction() {
		return fonction;
	}
	public void setFonction(String fonction) {
		this.fonction = fonction;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public NFAttribute getNFattrbute() {
		return NFattrbute;
	}
	public void setNFattrbute(NFAttribute nFattrbute) {
		NFattrbute = nFattrbute;
	}

}
