package nfattribute;

import monitoring.elements.Method;

public class ExecutionTime extends NFAttribute {

	private Method method;
	public ExecutionTime(int id,String name, String value,Method m) {
		super(id,name, value);
		this.method = m;
		
	}
	
	public void setMethod(Method method) {
		this.method = method;
	}
	
	public Method getMethod() {
		return method;
	}
	
	

}
