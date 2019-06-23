package monitoring.elements;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.shape.Line;

public class Connector extends ArchitectureElement implements Serializable {
	/*
	 * intData: [Connection index] [Connection type] [Source] [Dest] [Source
	 * minimum][Source maximum] [Destination minimum] [Destination Maximum] Index is
	 * used for reference Connection type denotes the type of connection: 0 =
	 * "Dependency", 1 = "Association", 2 = "Generalization", 3 = "Aggregate", 4 =
	 * "Composition" Source and Destination store the indices of the Source and
	 * Destination blocks respectively. Source minimum and source maximum denote the
	 * cardinality of the connection with the source class block (ie. 0 - 1, 0 - *).
	 * Use -1 (or any negative) to denote ANY (*). Destination minimum and
	 * destination maximum denote the cardinality of the connection with the
	 * destination block. Same rules apply. Label is pretty straightforward.
	 */
	private int index;
	private IntegerProperty type = new SimpleIntegerProperty();
	private IntegerProperty src = new SimpleIntegerProperty();
	private IntegerProperty dest = new SimpleIntegerProperty();
	private IntegerProperty srcMin = new SimpleIntegerProperty();
	private IntegerProperty srcMax = new SimpleIntegerProperty();
	private IntegerProperty destMin = new SimpleIntegerProperty();
	private IntegerProperty destMax = new SimpleIntegerProperty();
<<<<<<< HEAD

=======
	public static int id =0;
	
>>>>>>> branch 'master' of https://github.com/rw-developers/monitoring
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	public Port outPort;
	public Port inPort;
	public int Bandwidth;
	public Csp formule = new Csp("", "");

	public void setOutPort(Port outPort) {
		this.outPort = outPort;

	}

	public Csp getFormule() {
		return formule;
	}

	public Csp getCSpArc() {
		return formule;
	}

	public void setFormule(Csp formule) {
		this.formule = formule;
	}

	public Port getOutPort() {
		return outPort;
	}

	public Port getInPort() {
		return inPort;
	}

	public void setInPort(Port inPort) {
		this.inPort = inPort;
	}

	private StringProperty label = new SimpleStringProperty();
	private Configuration configuration;

	/**
	 * Constructs an instance of Connector
	 * 
	 * @constructor
	 * @param data the integral data to be stored
	 * @param l    the label to be stored
	 */
	public Connector(int[] data, String l, Configuration config, Csp csp, int bandwidth, Port in, Port out) {
		if (data.length == 8) {
			index = data[0];
			type.set(data[1]);
			src.set(data[2]);
			dest.set(data[3]);
			srcMin.set(data[4]);
			srcMax.set(data[5]);
			destMin.set(data[6]);
			destMax.set(data[7]);

		}
		id += 1;
		label.set(l);
		this.configuration = config;
		this.configuration.setConnectors(this);
		this.formule = csp;
		this.Bandwidth = bandwidth;
		this.inPort = in;
		this.outPort = out;

	}

	/*****************************
	 * SETTERS
	 ****************************/

	public Connector() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Sets the index value of the Connector
	 * 
	 * @param i the index value to be stored
	 */
	public void setIndex(int i) {
		index = i;
	}

	/**
	 * Sets the type value of the Connector
	 * 
	 * @param t the type value to be stored
	 */
	public void setType(int t) {
		type.set(t);
	}

	/**
	 * Sets the source index of the Connector
	 * 
	 * @param s the source index to be stored
	 */
	public void setSource(int s) {
		src.set(s);
	}

	/**
	 * Sets the destination index of the Connector
	 * 
	 * @param d the destination index to be stored
	 */
	public void setDest(int d) {
		dest.set(d);
	}

	/**
	 * Sets the source minimum value of the Connector
	 * 
	 * @param s the source minimum value to be stored
	 */
	public void setSourceMin(int s) {
		srcMin.set(s);
	}

	/**
	 * Sets the source maximum value of the Connector
	 * 
	 * @param s the source maximum value to be stored
	 */
	public void setSourceMax(int s) {
		srcMax.set(s);
	}

	/**
	 * Sets the destination minimum value of the Connector
	 * 
	 * @param d the destination minimum value to be stored
	 */
	public void setDestMin(int d) {
		destMin.set(d);
	}

	/**
	 * Sets the destination maximum value of the Connector
	 * 
	 * @param d the destination maximum value to be stored
	 */
	public void setDestMax(int d) {
		destMax.set(d);
	}

	/**
	 * Sets the label of the Connector
	 * 
	 * @param l the label to be stored
	 */
	public void setLabel(String l) {
		label.set(l);
	}

	/*****************************
	 * GETTERS
	 ****************************/

	/**
	 * Returns the index value of the Connector
	 * 
	 * @return the index value of the Connector
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Returns the type value of the Connector
	 * 
	 * @return the type value of the Connector
	 */
	public int getType() {
		return type.get();
	}

	/**
	 * Returns the source index of the Connector
	 * 
	 * @return the source index of the Connector
	 */
	public int getSource() {
		return src.get();
	}

	/**
	 * Returns the destination index of the Connector
	 * 
	 * @return the destination index of the Connector
	 */
	public int getDest() {
		return dest.get();
	}

	/**
	 * Returns the source minimum value of the Connector
	 * 
	 * @return the source minimum value of the Connector
	 */
	public int getSourceMin() {
		return srcMin.get();
	}

	/**
	 * Returns the source maximum value of the Connector
	 * 
	 * @return the source maximum value of the Connector
	 */
	public int getSourceMax() {
		return srcMax.get();
	}

	/**
	 * Returns the destination minimum value of the Connector
	 * 
	 * @return the destination minimum value of the Connector
	 */
	public int getDestMin() {
		return destMin.get();
	}

	/**
	 * Returns the destination maximum value of the Connector
	 * 
	 * @return the destination maximum value of the Connector
	 */
	public int getDestMax() {
		return destMax.get();
	}

	/**
	 * Returns the label of the Connector
	 * 
	 * @return the label of the Connector
	 */
	public String getLabel() {
		return label.get();
	}

	/**
	 * Returns the IntegerProperty associated with the Connector's type
	 * 
	 * @return the IntegerProperty associated with the Connector's type
	 */
	public IntegerProperty getTypeProp() {
		return type;
	}

	/**
	 * Returns the IntegerProperty associated with the Connector's source
	 * 
	 * @return the IntegerProperty associated with the Connector's source
	 */
	public IntegerProperty getSourceProp() {
		return src;
	}

	/**
	 * Returns the IntegerProperty associated with the Connector's destination
	 * 
	 * @return the IntegerProperty associated with the Connector's destination
	 */
	public IntegerProperty getDestProp() {
		return dest;
	}

	/**
	 * Returns the IntegerProperty associated with the Connector's source min
	 * 
	 * @return the IntegerProperty associated with the Connector's source min
	 */
	public IntegerProperty getSourceMinProp() {
		return srcMin;
	}

	/**
	 * Returns the IntegerProperty associated with the Connector's source max
	 * 
	 * @return the IntegerProperty associated with the Connector's source max
	 */
	public IntegerProperty getSourceMaxProp() {
		return srcMax;
	}

	/**
	 * Returns the IntegerProperty associated with the Connector's dest min
	 * 
	 * @return the IntegerProperty associated with the Connector's dest min
	 */
	public IntegerProperty getDestMinProp() {
		return destMin;
	}

	/**
	 * Returns the IntegerProperty associated with the Connector's dest max
	 * 
	 * @return the IntegerProperty associated with the Connector's dest max
	 */
	public IntegerProperty getDestMaxProp() {
		return destMax;
	}

	/**
	 * Returns the StringProperty associated with the Connector's label
	 * 
	 * @return the StringProperty associated with the Connector's label
	 */
	public StringProperty getLabelProp() {
		return label;
	}

}
