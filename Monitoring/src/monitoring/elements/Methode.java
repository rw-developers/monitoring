package monitoring.elements;

import javafx.beans.property.StringProperty;

public class Methode {
	public  StringProperty MethodeName ;

	public Methode(StringProperty methodeName) {
		super();
		MethodeName = methodeName;
	}

	public StringProperty getMethodeName() {
		return MethodeName;
	}

	public void setMethodeName(StringProperty methodeName) {
		MethodeName = methodeName;
	}

	

}
