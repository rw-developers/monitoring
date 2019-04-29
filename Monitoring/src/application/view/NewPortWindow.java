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

public class NewPortWindow extends Stage {
	// Dialog box elements
	private GridPane newClassInterface = new GridPane();
	private Text newClassTitle = new Text();
	private TextField newClassName = new TextField();
	private Text csptitle = new Text();
	private TextField cspexp = new TextField();
	private Label cspmessage = new Label();

	public Button newClassSubmit = new Button("Submit");
	public Button deleteClass = new Button("Delete");
	public Button cspval = new Button("Check CSP");

	ObservableList<String> options = FXCollections.observableArrayList("IN", "OUT");
	private ComboBox<String> newPortType = new ComboBox<String>(options);

	/**
	 * Constructs a NewPortWindow instance
	 * 
	 * @param editIndex
	 *            The index of the class being edited. If a new class is being
	 *            created, this value is -1.
	 * @param data
	 *            The Model to write data to.
	 */
	public NewPortWindow(int editIndex, Model data,ArchitectureElement elem) {
		// Set window title
		newClassTitle.setText((editIndex == -1) ? "Create Port Block" : "Edit Port Block");
		csptitle.setText("CSP Expression");
        cspexp.setPromptText("CSP");

		// Attach elements to window
		if (editIndex == -1) {
			newClassName.setPromptText("Port name...");

		} else {
			newClassName.setText(data.getPortModel(editIndex).getName());
			cspexp.setText(data.getPortModel(editIndex).getCsp());
			newPortType.getSelectionModel().select(data.getPortModel(editIndex).getType());

		}
		
		// Place elements on stage
		
		newClassInterface.add(newClassTitle, 0, 0, 2, 1);
		newClassInterface.add(newClassName, 0, 1, 2, 1);
		newClassInterface.add(newPortType, 0, 2, 2, 1);
		newClassInterface.add(csptitle, 0, 3, 2, 1);
		newClassInterface.add(cspexp, 0, 4, 2, 1); newClassInterface.add(cspval, 7,4,2,1);
		newClassInterface.add(newClassSubmit, 1, 5);
		newClassInterface.add(cspmessage, 1 ,9);

		// Check for new 
		if (editIndex != -1) {
			newClassInterface.add(deleteClass, 1, 7);
		}

		
		// Handler to submit the selected class
		EventHandler<ActionEvent> submitClassEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.saveUndoState();
				data.clearRedoState();

				if (editIndex == -1) {
					if(checkcsp()==1)
						{
						Alert.display("Error", "CSP Invalid !!! ");
						}else {
							
					data.addPortModel(new int[] { data.getPortTail(),((Component)elem).getXPos()+90,
							((Component)elem).getYPos()+((Component)elem).getPorts().size()*20+10
							, 100, 100 },
							new String[] { newClassName.getText(), newPortType.getSelectionModel().getSelectedItem(),cspexp.getText() },elem);
				}
				} else {
					if(checkcsp()==1)
					{
						Alert.display("Error", "CSP Invalid !!! ");
					}
				else {
					data.getPortModel(editIndex).setName(newClassName.getText());
					data.getPortModel(editIndex).setType(newPortType.getSelectionModel().getSelectedItem());
					data.getPortModel(editIndex).setCsp(cspexp.getText());
				}		
				}
				closeWindow();
				e.consume();
			}
		};

	EventHandler<ActionEvent> cspvalidation=new EventHandler<ActionEvent>(){@Override public void handle(ActionEvent e){

	int checker=checkcsp();

	}};

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
		newClassSubmit.setOnAction(submitClassEvent);
		deleteClass.setOnAction(deleteClassEvent);
		cspval.setOnAction(cspvalidation);

		// Display scene
	Scene scene = new Scene(newClassInterface, 300, 230);this.setScene(scene);

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
