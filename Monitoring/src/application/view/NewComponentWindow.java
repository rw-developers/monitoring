package application.view;

import application.include.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NewComponentWindow extends Stage {

	// Dialog box elements
	private GridPane newComponentInterface = new GridPane();
	private Text newComponentTitle = new Text();
	private TextField newComponentName = new TextField();
	public Button newComponentSubmit = new Button("Submit");
	public Button deleteComponent = new Button("Delete");

	public NewComponentWindow(int editIndex, Model data) {
		// Set window title
		newComponentTitle.setText((editIndex == -1) ? "Create Component Block" : "Edit Component Block");

		// Attach elements to window
		if (editIndex == -1) {
			newComponentName.setPromptText("Component name...");

		} else {
			newComponentName.setText(data.getComponentModel(editIndex).getName());
		}

		// Place elements on stage
		newComponentInterface.add(newComponentTitle, 0, 0, 2, 1);
		newComponentInterface.add(newComponentName, 0, 1, 2, 1);
		newComponentInterface.add(newComponentSubmit, 1, 6);

		// Check for new class
		if (editIndex != -1) {
			newComponentInterface.add(deleteComponent, 0, 6);
		}

		// Handler to submit the selected class
		EventHandler<ActionEvent> submitComponentEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.saveUndoState();
				data.clearRedoState();

				if (editIndex == -1) {
					data.addComponentModel(new int[] { data.getComponentTail(), 0, 0, 100, 100 },
							newComponentName.getText());
				} else {
					data.getComponentModel(editIndex).setName(newComponentName.getText());
				}
				closeWindow();
				e.consume();
			}
		};

		// Handler to delete the selected class
		EventHandler<ActionEvent> deleteComponentEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.removeComponentModel(editIndex);
				closeWindow();
				e.consume();
			}
		};

		// Attach handlers to buttons
		newComponentSubmit.setOnAction(submitComponentEvent);
		deleteComponent.setOnAction(deleteComponentEvent);

		// Display scene
		Scene scene = new Scene(newComponentInterface, 300, 230);
		this.setScene(scene);

	}

	private void closeWindow() {
		this.close();
	}
}
