package monitoring.elements;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ComponentImplementation extends ArchitectureElement {

	private int[] intData = new int[5];
	private StringProperty name = new SimpleStringProperty();
	private List<Port> ports = new ArrayList<Port>();
	private List<ComponentImplementation>  implementations = new ArrayList<ComponentImplementation>();
	private Component componentType;
	private Configuration configuration;
	
	private final int STEP = 1;
	
	public ComponentImplementation(int[] intsIn, String nameIn,Component parent,Configuration conf) {
		if (intsIn.length == 5) {
			intData = intsIn;
		}
		name.set(nameIn);
		this.componentType = parent;
		this.componentType.setImplementations(this);
		this.configuration = conf;
		this.configuration.setImplementations(this);
	}
	/*****************************
	 * SETTERS
	 ****************************/

	public void setIndex(int i) {
		intData[0] = i;
	}

	public void setXPos(int x) {
		if (x >= 0) {
			intData[1] = (x % STEP < (STEP / 2) ? x - (x % STEP) : x + STEP - (x % STEP));
		} else {
			intData[1] = 0;
		}
	}

	public void setYPos(int y) {
		if (y >= 0) {
			intData[2] = (y % STEP < (STEP / 2) ? y - (y % STEP) : y + STEP - (y % STEP));
		} else {
			intData[2] = 0;
		}
	}

	public void setWidth(int w) {
		intData[3] = (w % STEP < (STEP / 2) ? w - (w % STEP) : w + STEP - (w % STEP));
	}

	public void setHeight(int h) {
		intData[4] = (h % STEP < (STEP / 2) ? h - (h % STEP) : h + STEP - (h % STEP));
	}

	public void setName(String n) {
		name.set(n);
	}
	
	public void setPorts(Port port) {
		this.ports.add(port);
	}
	
	public void setImplementations(ComponentImplementation implementation) {
		this.implementations.add(implementation);
	}
	
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
	/*****************************
	 * GETTERS
	 ****************************/

	public int getIndex() {
		return intData[0];
	}

	public int getXPos() {
		return intData[1];
	}

	public int getYPos() {
		return intData[2];
	}

	public int getWidth() {
		return intData[3];
	}


	public int getHeight() {
		return intData[4];
	}

	public String getName() {
		return name.get();
	}

	public StringProperty getNameProp() {
		return name;
	}
	
	public List<Port> getPorts() {
		return ports;
	}
	
	public List<ComponentImplementation> getImplementations() {
		return implementations;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getName();
	}
	public void setComponentType(Component componentType) {
		this.componentType = componentType;
	}
	
	public Component getComponentType() {
		return componentType;
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}
}
