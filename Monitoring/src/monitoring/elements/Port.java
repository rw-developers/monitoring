package monitoring.elements;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Port implements Serializable  {

	/*
	 * intData: [index] [Position (x)] [Position (y)] [Width] [Height] Index is used
	 * for reference. Positions x & y are used for class block placement. I have
	 * them set to automatically round any given value to the nearest 10, but
	 * changing STEP will change the grid size if 10 doesn't look right. Width and
	 * Height should be obvious as well. They're using the same formula as X and Y,
	 * so they should adhere to the grid as well. stringData: [Name] [Attributes]
	 * [Operations] [Descriptions] The four elements in stringData should be pretty
	 * self explanatory. Depending on how we need to display the data, it might be
	 * easier to have a lone string (Name) and an array[3] of linked lists, so the
	 * lists of entries can be expanded indefinitely.
	 */
	private int[] intData = new int[5];
	public StringProperty name = new SimpleStringProperty();
	private StringProperty type = new SimpleStringProperty();
	
	String type1;
    public Csp cspExpression=new Csp("","");
    public Csp cspExpressionModify =new Csp("","");

    
    private Object parent;
    public Object getParent() {
		return parent;
	}

	public void setParent(Object parent) {
		this.parent = parent;
	}

	public String instanceParent ;
    public ArrayList<Connector> listArc = new ArrayList<>();
    public ArrayList<Connector> listArc2 = new ArrayList<>();
    public ArrayList<Connector> getListArc() {
		return listArc;
	}

	public void setListArc(ArrayList<Connector> listArc) {
		this.listArc = listArc;
	}

	public ArrayList<Connector> getListArc2() {
		return listArc2;
	}

	public void setListArc2(ArrayList<Connector> listArc2) {
		this.listArc2 = listArc2;
	}


    String evt;
    

private final int STEP = 1;

	private ArchitectureElement element;
	    String nom ;
	    public String getNom() {
			return name.getValue();
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public String getType1() {
			return type1;
		}

		public void setType1(String type1) {
			this.type1 = type1;
		}

		public Csp getCspExpression() {
			return cspExpression;
		}

		public void setCspExpression(Csp cspExpression) {
			this.cspExpression = cspExpression;
		}

		public Csp getCspExpressionModify() {
			return cspExpressionModify;
		}

		public void setCspExpressionModify(Csp cspExpressionModify) {
			this.cspExpressionModify = cspExpressionModify;
		}

	

	/**
	 * Constructs an instance of Port
	 * 
	 * @constructor
	 * @param intsIn    the array of ints to be stored in the model
	 * @param stringsIn the array of Strings to be stored in the model
	 */
	public Port(int[] intsIn, String[] stringsIn, ArchitectureElement e) {
		if (intsIn.length == 5) {
			intData = intsIn;
		}

		if (stringsIn.length == 3) {
			name.set(stringsIn[0]);
			type.set(stringsIn[1]);
			cspExpression = new Csp(stringsIn[0],stringsIn[2]);
		}

		this.element = e;
		 if (this.element instanceof Component) {
			 ((Component) element).setPorts(this);	
		}
		 else if(this.element instanceof ComponentImplementation) {
			 this.instanceParent = ((ComponentImplementation) element).getName();
			 this.parent = ((ComponentImplementation) element);
			 ((ComponentImplementation) element).setPorts(this);
		 }
		
	}

	/*****************************
	 * SETTERS
	 ****************************/

	/**
	 * Sets the index value of the Port
	 * 
	 * @param i the index to be stored
	 */
	public void setIndex(int i) {
		intData[0] = i;
	}

	/**
	 * Sets the x position value of the Port
	 * 
	 * @param x the x position value to be stored
	 */
	public void setXPos(int x) {
		if (x >= 0) {
			intData[1] = (x % STEP < (STEP / 2) ? x - (x % STEP) : x + STEP - (x % STEP));
		} else {
			intData[1] = 0;
		}
	}

	/**
	 * Sets the y position value of the Port
	 * 
	 * @param y the y position value to be stored
	 */
	public void setYPos(int y) {
		if (y >= 0) {
			intData[2] = (y % STEP < (STEP / 2) ? y - (y % STEP) : y + STEP - (y % STEP));
		} else {
			intData[2] = 0;
		}
	}

	/**
	 * Sets the width value of the Port
	 * 
	 * @param w the width value to be stored
	 */
	public void setWidth(int w) {
		intData[3] = (w % STEP < (STEP / 2) ? w - (w % STEP) : w + STEP - (w % STEP));
	}

	/**
	 * Sets the height value of the Port
	 * 
	 * @param h the height value to be stored
	 */
	public void setHeight(int h) {
		intData[4] = (h % STEP < (STEP / 2) ? h - (h % STEP) : h + STEP - (h % STEP));
	}

	/**
	 * Sets the name value of the Port
	 * 
	 * @param n the name value to be stored
	 */
	public void setName(String n) {
		name.set(n);
	}

	/**
	 * Sets the attributes value of the Port
	 * 
	 * @param a the attributes value to be stored
	 */
	public void setType(String t) {
		type.set(t);
	}

	public void setElement(ArchitectureElement element) {
		this.element = element;
	}
	
	public void setCsp(String csp) {
		//this.csp.set(csp); 
	}

	/*****************************
	 * GETTERS
	 ****************************/

	/**
	 * Returns the index value of the Port
	 * 
	 * @return the index value of the Port
	 */
	public int getIndex() {
		return intData[0];
	}

	/**
	 * Returns the x position value of the Port
	 * 
	 * @return the x position value of the Port
	 */
	public int getXPos() {
		return intData[1];
	}

	/**
	 * Returns the y position value of the Port
	 * 
	 * @return the y position value of the Port
	 */
	public int getYPos() {
		return intData[2];
	}

	/**
	 * Returns the width value of the Port
	 * 
	 * @return the width value of the Port
	 */
	public int getWidth() {
		return intData[3];
	}

	/**
	 * Returns the height value of the Port
	 * 
	 * @return the height value of the Port
	 */
	public int getHeight() {
		return intData[4];
	}

	/**
	 * Returns the name value of the Port
	 * 
	 * @return the name value of the Port
	 */
	public String getName() {
		return name.get();
	}

	/**
	 * Returns the attributes value of the Port
	 * 
	 * @return the attributes value of the Port
	 */
	public String getType() {
		return type.get();
	}
	
	public String getCsp() {
		return "";
	}

	/**
	 * Returns the StringProperty associated with the Port's name
	 * 
	 * @return the StringProperty associated with the Port's name
	 */
	public StringProperty getNameProp() {
		return name;
	}

	/**
	 * Returns the StringProperty associated with the Port's attributes
	 * 
	 * @return the StringProperty associated with the Port's attributes
	 */
	public StringProperty getTypeProp() {
		return type;
	}

	public ArchitectureElement getElement() {
		return element;
	}
	
	public StringProperty getCspProp() {
		return null;
	}
}
