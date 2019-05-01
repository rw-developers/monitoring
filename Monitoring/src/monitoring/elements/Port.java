package monitoring.elements;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Port  {

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
	private StringProperty name = new SimpleStringProperty();
	private StringProperty type = new SimpleStringProperty();
	private StringProperty csp =  new SimpleStringProperty();
	private ArchitectureElement element;
	
	private final int STEP = 1;

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
			csp.set(stringsIn[2]);
		}

		this.element = e;
		 if (this.element instanceof Component) {
			 ((Component) element).setPorts(this);	
		}
		 else if(this.element instanceof ComponentImplementation) {
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
		this.csp.set(csp); 
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
		return csp.get();
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
		return csp;
	}
}
