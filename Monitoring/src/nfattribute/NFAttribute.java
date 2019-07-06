package nfattribute;

import java.io.Serializable;

public class NFAttribute implements Serializable {
	private int id;
	private String name;
	private String value;
	
	 
	public NFAttribute(int id,String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}

}
