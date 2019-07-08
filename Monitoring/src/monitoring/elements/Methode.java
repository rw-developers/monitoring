package monitoring.elements;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Methode {
	public Methode(StringProperty methodeName, int executionTime) {
		super();
		MethodeName = methodeName;
		ExecutionTime = executionTime;
	}
	public  StringProperty MethodeName ;

	private Component component;


	//private StringProperty name = new SimpleStringProperty();
	public int ExecutionTime =0;

	public Methode(StringProperty methodeName) {
		super();
		MethodeName = methodeName;
	//	name =methodeName ;
	}

	public StringProperty getMethodeName() {
		return MethodeName;
	}

	public void setMethodeName(StringProperty methodeName) {
		MethodeName = methodeName;
	}

	

	
	@Override
	public String toString() {
		return this.getMethodeName().get();
	}

	

}
