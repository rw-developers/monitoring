package nfattribute;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import monitoring.elements.Component;
import monitoring.elements.Connector;
import monitoring.elements.Methode;

public class TimedConstraint extends NFConstraint implements Serializable {
	public StringProperty name = new SimpleStringProperty();
	public Component COMP1 ;  
	public Component COMP2 ;
	public Methode Meth1;
	public Methode Meth2;
	//public Connector conn; 
	public String event1 ,event2;
	public int Value;
	public String part1;
	public String part2;
	public String opC ;
	public String opL;
	int back = 1;
	 
	public TimedConstraint(Component cOMP1, Component cOMP2, Methode meth1, Methode meth2, String event1, String event2,
			int value, String opC, String opL) {
		IDC += 1;
		COMP1 = cOMP1;
		COMP2 = cOMP2;
		Meth1 = meth1;
		Meth2 = meth2;
		this.event1 = event1;
		this.event2 = event2;
		Value = value;
		this.opC = opC;
		this.opL = opL;
		 String str = "Constraint N° "+this.IDC+" { "+this.COMP1.getName()+".["+this.event1+"]."+this.Meth1.getMethodeName().getValue()+"  "+this.opC+" ";
		 String str2 = this.COMP2.getName()+".["+this.event2+"]."+this.Meth2.getMethodeName().getValue()+"  "+this.opL+"  "+this.Value+"  }";
		 name.set(str+str2);
	} 
	public Component getCOMP1() {
		return COMP1;
	}
	public void setCOMP1(Component cOMP1) {
		COMP1 = cOMP1;
	}
	public Component getCOMP2() {
		return COMP2;
	}
	public void setCOMP2(Component cOMP2) {
		COMP2 = cOMP2;
	}
	public Methode getMeth1() {
		return Meth1;
	}
	public void setMeth1(Methode meth1) {
		Meth1 = meth1;
	}
	public Methode getMeth2() {
		return Meth2;
	}
	public void setMeth2(Methode meth2) {
		Meth2 = meth2;
	}
	
	public float getValue() {
		return Value;
	}
	public void setValue(int value) {
		Value = value;
	}
	public String getPart1() {
		return part1;
	}
	public void setPart1(String part1) {
		this.part1 = part1;
	}
	public String getPart2() {
		return part2;
	}
	public void setPart2(String part2) {
		this.part2 = part2;
	}
	public String getOpC() {
		return opC;
	}
	public void setOpC(String opC) {
		this.opC = opC;
	}
	public String getOpL() {
		return opL;
	}
	public void setOpL(String opL) {
		this.opL = opL;
	}

}
