package monitoring.elements;

public class Method {
	
	private String name;
	private Component component;
	
	public Method(String name, Component component) {
		super();
		this.name = name;
		this.component = component;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setComponent(Component component) {
		this.component = component;
	}
	
	public String getName() {
		return name;
	}
	
	public Component getComponent() {
		return component;
	}
	
	
	
	

}
