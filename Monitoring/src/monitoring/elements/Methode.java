package monitoring.elements;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Methode {
	public  StringProperty MethodeName ;
	private StringProperty name = new SimpleStringProperty();

	public Methode(StringProperty methodeName) {
		super();
		MethodeName = methodeName;
		name =methodeName ;
	}

	public StringProperty getMethodeName() {
		return MethodeName;
	}

	public void setMethodeName(StringProperty methodeName) {
		MethodeName = methodeName;
	}

	public StringProperty getName() {
		return name;
	}

	public void setName(StringProperty name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return this.getName().get();
	}

	

}
