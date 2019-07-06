package nfattribute;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OtherConstraint extends NFConstraint implements Serializable {
	public String type;
	public StringProperty name = new SimpleStringProperty();
	 
	public String fonction ;
	public String attribute ;
	public String op ;
	public int value;
	public NFAttribute NFattrbute;
	int back = 1;
	
	
	public StringProperty getName() {
		 String str = " "+this.type+" "+this.fonction+" "+this.attribute+" "+this.value;
		 name.set(str);
		return name;
	}
	public void setName(StringProperty name) {
		this.name = name;
	}
	public OtherConstraint(String type, String fonction, String attribute, String op, int value) {
		super();
		IDC += 1;
		this.type = type;
		this.fonction = fonction;
		this.attribute = attribute;
		this.op = op;
		this.value = value;
		 String str = "Constraint N° "+this.IDC+" Type : "+this.type+"  { "+this.fonction+" [ "+this.attribute+" ] "+this.op+" "+this.value+"  } ";
		 name.set(str);
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
	public void setValue(int value) {
		this.value = value;
	}
	public NFAttribute getNFattrbute() {
		return NFattrbute;
	}
	public void setNFattrbute(NFAttribute nFattrbute) {
		NFattrbute = nFattrbute;
	}

}
