package application.objects;

import javafx.scene.layout.VBox;
import monitoring.elements.Component;

public class ComponentBlock extends VBox {
	
	/*
	 * Private variables stored in the ComponentBlock object
	 */
	private Panel label = new Panel();
	private Panel name = new Panel();
	private PortNode node = new PortNode();

	public ComponentBlock(Component compIn) {
		// Set initial variables
		this.getStyleClass().add("componentBlock");
		this.setHeight(100);
		this.setWidth(300);

		// Generate and add title block
		label.updateText("<< Component >>");
		
		name.updateText(compIn.getName());
		
		
		label.getStyleClass().add("componentBlockLabel");
		name.getStyleClass().add("componentBlockPanel");
		

		this.getChildren().add(label);
		this.getChildren().add(name);

		node.setX(compIn.getXPos());
		node.setY(compIn.getYPos());

	}
	

	
	public void setName(String nameIn) {
		name.updateText(nameIn);
	}
	
	public String getName() {
		return name.getText().getText();
	}
	
	/**
	 * Returns the PortNode to attach ports to
	 * 
	 * @return the LinkNode stored in the PortBlock
	 */
	public PortNode getNode() {
		return node;
	}
	
	/**
	 * Updates the ComponentBlock's PortNode with correct height & width, now that it
	 * has been displayed.
	 * 
	 */
	public void initWidthHeight() {
		node.setX(node.getX() + (int) (this.getWidth() / 2));
		node.setY(node.getY() + (int) (this.getHeight() / 2));
	}
	
	/**
	 * Remove the width & height from the PortNode
	 * 
	 * Prerequisite before refreshing the Width & Height. Must be completed before the CSS is altered or the ComponentBlock dimensions otherwise change.
	 * 
	 */
	public void prepWidthHeight() {
		node.setX(node.getX() - (int) (this.getWidth() / 2));
		node.setY(node.getY() - (int) (this.getHeight() / 2));
	}

}
