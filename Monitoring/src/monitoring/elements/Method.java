package monitoring.elements;


import javafx.beans.property.StringProperty;

public class Method  {
	
	private StringProperty name;
	private Component component;
	
	public Method(StringProperty n, Component component) {
		super();
		this.name = n;
		this.component = component;
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	public void setComponent(Component component) {
		this.component = component;
	}
	
	public String getName() {
		return this.name.get();
	}
	
	public StringProperty getNameProp() {
		return this.name;
	}
	
	public Component getComponent() {
		return component;
	}
	
	
	
	

}
