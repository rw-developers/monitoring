package application.view;


import application.include.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NewLinkWindow extends Stage {

	// Dialog box elements
	private GridPane newLinkInterface = new GridPane();
	private Text newLinkTitle = new Text("Create Connector");;
	private Button newLinkSubmit = new Button("Submit");
	private Button deleteLink = new Button("Delete");

	private int srcIn;
	private int destIn;
	
	ObservableList<String> options = FXCollections.observableArrayList("Binding","Connector");
	private ComboBox<String> newLinkArrow = new ComboBox<String>(options);

/*	private UnaryOperator<Change> multiplicities = change -> {
		String text = change.getText();
		return (text.matches("\\*|[0-9]*") ? change : null);
	};
*/

	/**
	 * Constructs a NewLinkWindow instance
	 * 
	 * @param data
	 *            The model to write data to
	 */
	public NewLinkWindow(int editIndex, Model data,ProgramWindow window) {


		// Place elements on Dialog
		newLinkInterface.add(newLinkTitle, 0, 0, 2, 1);
		newLinkInterface.add(newLinkArrow, 0, 1, 2, 1);
	

		if(editIndex != -1) {
			newLinkInterface.add(deleteLink, 0, 8);
		}
		newLinkInterface.add(newLinkSubmit, 1, 8);
		
		if(editIndex == -1) {
			newLinkArrow.setPromptText("Select link type...");

		} else {
			newLinkArrow.getSelectionModel().select(data.getLinkModel(editIndex).getType());
		}

		// Handler to submit a new Link
		EventHandler<ActionEvent> submitLinkEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.saveUndoState();
				data.clearRedoState();
				if(editIndex == -1)
				{

					try {

						int confId = 0;
						for (int i = 1; i < window.appPanel.getTabs().size(); i++) {
							if (window.appPanel.getTabs().get(i).isSelected()) {
								confId = i - 1;
							}
						}
						if (srcIn <= data.maxLink() && srcIn >= 0 && destIn <= data.maxLink() && destIn >= 0) {
							data.addLinkModel(
									new int[] { data.getLinkTail(), newLinkArrow.getSelectionModel().getSelectedIndex(),
											srcIn, destIn, -2, -2, -2, -2
											},
									"",data.getConfigurationModel(confId));
						}
					} catch (NumberFormatException ex) {
					}
				} else {
					data.getLinkModel(editIndex).setType(newLinkArrow.getSelectionModel().getSelectedIndex());
				
				}				
				closeWindow();
				e.consume();
			}
		};
		
		EventHandler<ActionEvent> deleteLinkEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.saveUndoState();
				data.clearRedoState();
				data.removeLinkModel(editIndex);
				closeWindow();
				e.consume();
			}
		};
		
		newLinkSubmit.setOnAction(submitLinkEvent);
		deleteLink.setOnAction(deleteLinkEvent);
		
		// Display dialog
		Scene scene = new Scene(newLinkInterface);
		this.setScene(scene);
	}

	/**
	 * Closes the dialog box
	 */
	private void closeWindow() {
		this.close();
	}
	
	public void setSrc(int s) {
		srcIn = s;
	}
	
	public void setDest(int d) {
		destIn = d;
	}
}
