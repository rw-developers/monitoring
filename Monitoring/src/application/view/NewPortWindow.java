package application.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.include.Alert;
import application.include.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import monitoring.elements.ArchitectureElement;
import monitoring.elements.Component;
import monitoring.elements.ComponentImplementation;

public class NewPortWindow extends Stage {
	// Dialog box elements
	private GridPane newPortInterface = new GridPane();
	private Text newPortTitle = new Text();
	private TextField newPortName = new TextField();
	private Text csptitle = new Text();
	private TextField cspexp = new TextField();
	private Label cspmessage = new Label();

	public Button newPortSubmit = new Button("Submit");
	public Button deleteClass = new Button("Delete");
	public Button cspval = new Button("Check CSP");

	private Text typeTitle = new Text();
	ObservableList<String> options = FXCollections.observableArrayList("IN", "OUT");
	private ComboBox<String> newPortType = new ComboBox<String>(options);

	/**
	 * Constructs a NewPortWindow instance
	 * 
	 * @param editIndex The index of the class being edited. If a new class is being
	 *                  created, this value is -1.
	 * @param data      The Model to write data to.
	 */
	public NewPortWindow(int editIndex, Model data, ArchitectureElement elem) {
		// Set window title
		newPortTitle.setText((editIndex == -1) ? "Create Port Block" : "Edit Port Block");
		typeTitle.setText("Type");
		csptitle.setText("CSP Expression");
		cspexp.setPromptText("CSP");

		// Attach elements to window
		if (editIndex == -1) {
			newPortName.setPromptText("Port name...");

		} else {
			newPortName.setText(data.getPortModel(editIndex).getName());
			cspexp.setText(data.getPortModel(editIndex).getCsp());
			newPortType.getSelectionModel().select(data.getPortModel(editIndex).getType());

		}

		// Place elements on stage

		newPortInterface.add(newPortTitle, 0, 0, 2, 1);
		newPortInterface.add(newPortName, 0, 1, 2, 1);
		newPortInterface.add(typeTitle, 0 , 2, 2, 1);
		newPortInterface.add(newPortType,1, 2, 2, 1);
		newPortInterface.add(csptitle, 0, 3, 2, 1);
		newPortInterface.add(cspexp, 0, 4, 2, 1);
		newPortInterface.add(cspval, 7, 4, 2, 1);
		newPortInterface.add(newPortSubmit, 1, 5);
		newPortInterface.add(cspmessage, 1, 9);

		// Check for new
		if (editIndex != -1) {
			newPortInterface.add(deleteClass, 1, 7);
		}

		// Handler to submit the selected class
		EventHandler<ActionEvent> submitClassEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.saveUndoState();
				data.clearRedoState();

				if (editIndex == -1) {
					if (checkcsp() == 1) {
						Alert.display("Error", "CSP Invalid !!! ");
					} else {
                      Alert.display("", ((Component) elem).getName());
						data.addPortModel(
								new int[] { data.getPortTail(), ((Component) elem).getXPos() + 240,
										((Component) elem).getYPos() + ((Component) elem).getPorts().size() * 25 + 15,
										100, 100 },
								new String[] { newPortName.getText(),
										newPortType.getSelectionModel().getSelectedItem(), cspexp.getText() },
								elem);
						 
						data.getImplementationProperty().forEach(i->{
							if(i.getComponentType().getIndex() == ((Component)elem).getIndex() ) {
								
								data.addPortModel(
										new int[] { data.getPortTail(), ((ComponentImplementation) i).getXPos() + 240,
												((ComponentImplementation ) i).getYPos() + ((ComponentImplementation) i).getPorts().size() * 25 + 15,
												100, 100 },
										new String[] { newPortName.getText(),
												newPortType.getSelectionModel().getSelectedItem(), cspexp.getText() },
										i);
							}
						});
					}
				} else {
					if (checkcsp() == 1) {
						Alert.display("Error", "CSP Invalid !!! ");
					} else {
						data.getPortModel(editIndex).setName(newPortName.getText());
						data.getPortModel(editIndex).setType(newPortType.getSelectionModel().getSelectedItem());
						data.getPortModel(editIndex).setCsp(cspexp.getText());
					}
				}
				closeWindow();
				e.consume();
			}
		};

		EventHandler<ActionEvent> cspvalidation = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				checkcsp();
			}
		};

		// Handler to delete the selected class
		EventHandler<ActionEvent> deleteClassEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.removePortModel(editIndex);
				closeWindow();
				e.consume();
			}
		};

		// Attach handlers to buttons
		newPortSubmit.setOnAction(submitClassEvent);
		deleteClass.setOnAction(deleteClassEvent);
		cspval.setOnAction(cspvalidation);

		// Display scene
		Scene scene = new Scene(newPortInterface, 300, 230);
		this.setScene(scene);

	}

	/**
	 * Closes the dialog box
	 */
	private void closeWindow() {
		this.close();
	}

	private int checkcsp() {
		String cspFormule = cspexp.getText();

		Pattern pExpIn = Pattern.compile(
				"([a-zA-Z]+[0-9]*\\?[a-zA-Z]+[0-9]*(?:[-]>[a-zA-Z]+[0-9]*[?!][a-zA-Z]+[0-9]*)*)(?:[-]>([a-zA-Z]+[0-9]*))");
		Pattern pExpOut = Pattern.compile(
				"([a-zA-Z]+[0-9]*![a-zA-Z]+[0-9]*(?:[-]>[a-zA-Z]+[0-9]*[?!][a-zA-Z]+[0-9]*)*)(?:[-]>([a-zA-Z]+[0-9]*))");
		Matcher cspExpressionInMatcher = pExpIn.matcher(cspFormule);
		Matcher cspExpressionOutMatcher1 = pExpOut.matcher(cspFormule);
		String type = newPortType.getSelectionModel().getSelectedItem();
		if (type.compareTo("IN") == 0) {

			if ((!cspExpressionInMatcher.matches())) {
				cspmessage.setTextFill(Color.RED);
				cspmessage.setText(" CSP  invalid");
				return 1;
			} else {

				cspmessage.setTextFill(Color.GREEN);
				cspmessage.setText(" CSP valid");
				/// enregistrement de csp
				return 0;
			}

		} else {
			if (!cspExpressionOutMatcher1.matches()) {
				cspmessage.setTextFill(Color.RED);
				cspmessage.setText("CSP  invalid");
				return 1;
			} else {

				cspmessage.setTextFill(Color.GREEN);
				cspmessage.setText(" CSP valid");
				/// enregistrement de csp
				return 0;

			}

		}

	}
}
