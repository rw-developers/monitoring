package application.objects;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import monitoring.elements.Port;

public class PortBlock extends VBox {

	/*
	 * Private variables stored in the PortBlock object
	 */
	private Panel name = new Panel();
	private PortNode component = new PortNode();
	private LinkNode node = new LinkNode();

	/**
	 * Constructs a PortBlock object using the data stored in modelIn
	 * 
	 * @param modelIn
	 *            The Port object storing the data to be used
	 * 
	 */
	public PortBlock(Port modelIn,PortNode comp) {
		// Set initial variables
		if(modelIn.getType() == "IN") {
			this.getStyleClass().add("portBlockIn");
		}
		else {
			this.getStyleClass().add("portBlockOut");
		}
		this.setHeight(50);
		this.setWidth(50);

		// Generate and add title block
		name.updateText(modelIn.getName());


		name.getStyleClass().add("portBlockPanel");


		this.getChildren().add(name);
		this.component = comp;
		this.component.setConnectedPort(this);


		node.setX(modelIn.getXPos());
		node.setY(modelIn.getYPos());

	}

	/**
	 * Dynamically allocate rows depending on which ones contain content
	 */
	
	/**
	 * Set the Class Block's name field
	 * 
	 * @param nameIn
	 *            The name to be set
	 */
	public void setName(String nameIn) {
		name.updateText(nameIn);
	}

	/**
	 * Set the Class Block's attr field
	 * 
	 * @param attrIn
	 *            The attributes to be set
	 */

	/**
	 * Returns the LinkNode to attach links to
	 * 
	 * @return the LinkNode stored in the PortBlock
	 */
	public LinkNode getNode() {
		return node;
	}
	
	public PortNode getComponent() {
		return component;
	}

	/**
	 * Updates the PortBlock's LinkNode with correct height & width, now that it
	 * has been displayed.
	 * 
	 */
	public void initWidthHeight() {
		node.setX(node.getX() + (int) (this.getWidth() / 2));
		node.setY(node.getY() + (int) (this.getHeight() / 2));
	}
	
	/**
	 * Remove the width & height from the LinkNode
	 * 
	 * Prerequisite before refreshing the Width & Height. Must be completed before the CSS is altered or the PortBlock dimensions otherwise change.
	 * 
	 */
	public void prepWidthHeight() {
		node.setX(node.getX() - (int) (this.getWidth() / 2));
		node.setY(node.getY() - (int) (this.getHeight() / 2));
	}
	

	/**
	 * Panels to be used in the Class Block object. Pretty straightforward.
	 */
	private class Panel extends HBox {
		// Stores the panel's text
		private Text text = new Text();

		/**
		 * Construct a Panel object
		 * 
		 */
		public Panel() {
			this.getChildren().add(text);
			text.getStyleClass().add("textColor");
		}

		/**
		 * Updates the text stored by the panel
		 * 
		 * @param in
		 *            Text to be stored
		 */
		public void updateText(String in) {
			text.setText(in);
		}

		/**
		 * Checks whether the panel stores any text or not
		 * 
		 * @return Returns true if the panel stores text, false otherwise.
		 */
		@SuppressWarnings("unused")
		public boolean isEmpty() {
			return (text.getText() == null || text.getText().isEmpty());
		}
	}
}
