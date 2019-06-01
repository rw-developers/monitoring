package application.view;

import application.include.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import monitoring.elements.Component;
import monitoring.elements.ComponentImplementation;

public class NewImplementationWindow extends Stage {

	// Dialog box elements
	private GridPane newImplementationInterface = new GridPane();
	private Text newImplementationTitle = new Text();
	private TextField newImplementationName = new TextField();
	public Button newImplementationSubmit = new Button("Submit");
	public Button deleteImplementation = new Button("Delete");

	@SuppressWarnings("unused")
	private ComboBox<Component> parent;

	public NewImplementationWindow(int editIndex, Model data, ProgramWindow window) {
		// Set window title
		newImplementationTitle.setText((editIndex == -1) ? "Create Implementation Block" : "Edit Implementation Block");

		// Attach elements to window
		if (editIndex == -1) {
			newImplementationName.setPromptText("Implementation name...");
			parent = new ComboBox<Component>(data.getComponentProperty());

		} else {
			newImplementationName.setText(data.getImplementationModel(editIndex).getName());
			parent = new ComboBox<Component>(data.getComponentProperty());
			parent.getSelectionModel().select(data.getImplementationModel(editIndex).getComponentType());
		}

		// Place elements on stage
		newImplementationInterface.add(newImplementationTitle, 0, 0, 2, 1);
		newImplementationInterface.add(newImplementationName, 0, 1, 2, 1);
		newImplementationInterface.add(parent, 0, 2, 2, 1);
		newImplementationInterface.add(newImplementationSubmit, 1, 6);

		// Check for new class
		if (editIndex != -1) {
			newImplementationInterface.add(deleteImplementation, 0, 6);
		}

		// Handler to submit the selected class
		EventHandler<ActionEvent> submitImplementationEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.saveUndoState();
				data.clearRedoState();

				if (editIndex == -1) {
					int confId = 0;
					for (int i = 1; i < window.appPanel.getTabs().size(); i++) {
						if (window.appPanel.getTabs().get(i).isSelected()) {
							confId = i - 1;
						}
					}
					int id = data.getImplementationTail();
					data.addImplementationModel(new int[] { id, 0, 0, 100, 100 }, newImplementationName.getText(),
							parent.getSelectionModel().getSelectedItem(), data.getConfigurationModel(confId));

					ComponentImplementation added = data.getImplementationProperty().get(id);
					added.getComponentType().getPorts().forEach(p -> {
						data.addPortModel(
								new int[] { data.getPortTail(), added.getXPos() + 240,
										added.getYPos() + added.getPorts().size() * 25 + 15, 100, 100 },
								new String[] { p.getName(), p.getType(), p.getCspExpression().getExpression() }, added);
					});
				} else {
					data.getImplementationModel(editIndex).setName(newImplementationName.getText());
				}
				closeWindow();
				e.consume();
			}
		};

		// Handler to delete the selected class
		EventHandler<ActionEvent> deleteImplementationEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.removeImplementationModel(editIndex);
				closeWindow();
				e.consume();
			}
		};

		// Attach handlers to buttons
		newImplementationSubmit.setOnAction(submitImplementationEvent);
		deleteImplementation.setOnAction(deleteImplementationEvent);

		// Display scene
		Scene scene = new Scene(newImplementationInterface, 300, 230);
		this.setScene(scene);

	}

	private void closeWindow() {
		this.close();
	}
}
