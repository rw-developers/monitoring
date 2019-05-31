package application.objects;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Panel extends HBox {
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
	 * @param in Text to be stored
	 */
	public void updateText(String in) {
		text.setText(in);
	}
	
	public Text getText() {
		return text;
	}

	/**
	 * Checks whether the panel stores any text or not
	 * 
	 * @return Returns true if the panel stores text, false otherwise.
	 */
	public boolean isEmpty() {
		return (text.getText() == null || text.getText().isEmpty());
	}

}
