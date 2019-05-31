package application.objects;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PortNode {

	private IntegerProperty xPos = new SimpleIntegerProperty();
	private IntegerProperty yPos = new SimpleIntegerProperty();
	private List<PortBlock> connectedPorts = new ArrayList<PortBlock>();
	private IntegerProperty xPosL = new SimpleIntegerProperty();
	private IntegerProperty xPosR = new SimpleIntegerProperty();
	private IntegerProperty yPosU = new SimpleIntegerProperty();
	private IntegerProperty yPosD = new SimpleIntegerProperty();

	public PortNode() {

	} 

	public PortNode(int x, int y) {
		xPos.set(x);
		yPos.set(y);
	}

	public void setX(int x) {
		xPos.set(x);
	}

	/**
	 * Sets the y position of the PortNode
	 * 
	 * @param y the y position to be set
	 */
	public void setY(int y) {
		yPos.set(y);
	}

	/**
	 * Returns the x position of the PortNode
	 * 
	 * @return the x position of the PortNode
	 */
	public int getX() {
		return xPos.get();
	}

	/**
	 * Returns the y position of the PortNode
	 * 
	 * @return the y position of the PortNode
	 */
	public int getY() {
		return yPos.get();
	}

	/**
	 * Returns the xPos property of the PortNode
	 * 
	 * @return the PortNode's xPos property
	 */
	public IntegerProperty getXProperty() {
		return xPos;
	}

	/**
	 * Returns the yPos property of the PortNode
	 * 
	 * @return the PortNode's yPos property
	 */
	public IntegerProperty getYProperty() {
		return yPos;
	}

	/**
	 * Add the parent Port to the list of Ports that originate from this PortNode
	 * 
	 * @param one of the Ports that uses this PortNode
	 */
	public void giveParent(PortBlock dad) {
		connectedPorts.add(dad);
	}
	
	public void setBounds(int xL, int xR, int yU, int yD) {
		xPosL.set(xL);
		xPosR.set(xR);
		yPosU.set(yU);
		yPosD.set(yD);
	}

	/**
	 * Returns the left bound of the PortNode
	 * 
	 * @return the left bound of the PortNode
	 */
	public int getL() {
		return xPosL.get();
	}

	/**
	 * Returns the right bound of the PortNode
	 * 
	 * @return the right bound of the PortNode
	 */
	public int getR() {
		return xPosR.get();
	}

	/**
	 * Returns the upper bound of the PortNode
	 * 
	 * @return the upper bound of the PortNode
	 */
	public int getU() {
		return yPosU.get();
	}

	/**
	 * Returns the lower bound of the PortNode
	 * 
	 * @return the lower bound of the PortNode
	 */
	public int getD() {
		return yPosD.get();
	}
	
	public void setConnectedPort(PortBlock connectedPort) {
		this.connectedPorts.add(connectedPort);
	}
	
	public List<PortBlock> getConnectedPort() {
		return connectedPorts;
	}

	/**
	 * Tell each parent Port to update its line coordinates
	 * 
	 */
//	public void updatePort() {
//		
//		connectedPort.updatePort();
//	}


}
