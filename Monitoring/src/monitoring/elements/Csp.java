package monitoring.elements;

import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

public class Csp {
	 private String name;
	    private String expression;

	    public Csp(String name, String expression) {
	        this.name = name;
	        this.expression = expression;
	    }

	    public Csp() {
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getExpression() {
	        return expression;
	    }

	    public void setExpression(String expression) {
	        this.expression = expression;
	    }

	    public String toString(){
	        return name+expression;
	    }
	    
	    
}