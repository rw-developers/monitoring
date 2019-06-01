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
	public StringProperty name = new SimpleStringProperty();
	List<ComponentImplementation> implementations = new ArrayList<ComponentImplementation>();
	public List<Connector> connectors = new ArrayList<Connector>();
	private List<Configuration> configurations = new ArrayList<Configuration>();
	
	
	    public Component parent;
	    public Csp fomule;
	    public String TextConfig ="";
	    private Boolean bool=false;



	    private Configuration motherConfig=null;
	

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
	
	public void setImplementations(ComponentImplementation implementation) {
		this.implementations.add(implementation);
	}
	
	public void setConfigurations(Configuration configuration) {
		this.configurations.add(configuration);
	}
	
	public void setConnectors(Connector connector) {
		this.connectors.add(connector);
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
	
	public List<ComponentImplementation> getComponents() {
		return implementations;
	}
	
	public List<Configuration> getConfigurations() {
		return configurations;
	}
	
	public List<Connector> getConnectors() {
		return connectors;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
	

}
