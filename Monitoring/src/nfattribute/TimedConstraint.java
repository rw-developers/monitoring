package nfattribute;

import monitoring.elements.Component;
import monitoring.elements.Connector;
import monitoring.elements.Methode;

public class TimedConstraint extends NFConstraint {
	
	public Component COMP1 ;  
	public Component COMP2 ;
	public Methode Meth1;
	public Methode Meth2;
	public Connector conn; 
	public float Value;
	public String part1;
	public String part2;
	public String opC ;
	public String opL;
	public TimedConstraint(int ID ,float value, String part1, String part2, String opC, String opL) {
		super(); 
		IDC = ID;
		Value = value;
		this.part1 = part1;
		this.part2 = part2;
		this.opC = opC;
		this.opL = opL;
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
	public Connector getConn() {
		return conn;
	}
	public void setConn(Connector conn) {
		this.conn = conn;
	}
	public float getValue() {
		return Value;
	}
	public void setValue(float value) {
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
