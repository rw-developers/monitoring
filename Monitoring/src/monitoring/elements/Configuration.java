package monitoring.elements;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Configuration extends ArchitectureElement {

	/*
	 * intData: [index] [Position (x)] [Position (y)] [Width] [Height] Index is used
	 * for reference. Positions x & y are used for Configuration placement. I have
	 * them set to automatically round any given value to the nearest 10, but
	 * changing STEP will change the grid size if 10 doesn't look right. Width and
	 * Height should be obvious as well. They're using the same formula as X and Y,
	 * so they should adhere to the grid as well. stringData: [Name]
	 */
	private int index;
	private StringProperty name = new SimpleStringProperty();
	private List<Component> components = new ArrayList<Component>();
	private List<Configuration> configurations = new ArrayList<Configuration>();
	

	public Configuration(int id,String nameIn) {
		this.index = id;
		name.set(nameIn);
	}
	
	/*****************************
	 * SETTERS
	 ****************************/

	public void setIndex(int index) {
		this.index = index;
	}

	public void setName(String n) {
		name.set(n);
	}
	
	public void setComponents(Component component) {
		this.components.add(component);
	}
	
	public void setConfigurations(Configuration configuration) {
		this.configurations.add(configuration);
	}
	
	
	/*****************************
	 * GETTERS
	 ****************************/

    public int getIndex() {
		return index;
	}

	public String getName() {
		return name.get();
	}

	public StringProperty getNameProp() {
		return name;
	}
	
	public List<Component> getComponents() {
		return components;
	}
	
	public List<Configuration> getConfigurations() {
		return configurations;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}

}
