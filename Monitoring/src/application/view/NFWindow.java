package application.view;

import java.util.ArrayList;
import java.util.List;

import application.include.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import monitoring.elements.Component;
import monitoring.elements.Port;

public class NFWindow extends Stage {

	private BorderPane root = new BorderPane();
	private GridPane NFInterface = new GridPane();
	private ToolBar NFtools = new ToolBar();
	private Text NFTitle = new Text();
	private List<TextField> NFNames = new ArrayList<TextField>();
	private List<TextField> NFValues = new ArrayList<TextField>();
	public Button NFExeTime = new Button("Execution Time");
	public Button NFBandeWidth = new Button("Bande Width");
	public Button NFMemoryConsp = new Button("Memory Consumption");
	public Button newComponentSubmit = new Button("Submit");
	public Button deleteComponent = new Button("Delete");
	
	//combo box
	private List<ComboBox<Component>> components = new ArrayList<ComboBox<Component>>();
	private List<ComboBox<Port>> ports = new ArrayList<ComboBox<Port>>();
	
	
	public NFWindow(int editIndex,Model data) {
		NFExeTime.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");
		NFBandeWidth.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");
		NFMemoryConsp.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");

		NFtools.getItems().addAll(NFExeTime);
		NFtools.getItems().addAll(NFBandeWidth);
		NFtools.getItems().addAll(NFMemoryConsp);
		
		if(editIndex == -1) {
		}
		else {
			
		}
		// Handler to submit the selected class
				EventHandler<ActionEvent> memoryConspEvent = new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {
						data.saveUndoState();
						data.clearRedoState();
						TextField txtFiel = new TextField();
						txtFiel.setPromptText("NF Attribute name");
						Label lab = new Label("Component");
						ChoiceBox<Component> comp = new ChoiceBox<Component>(data.getComponentProperty());
						TextField txtVal = new TextField();
						txtVal.setPromptText("Memory Consumption");
						
						

						
							NFInterface.add(txtFiel, 0, 0 );
							NFInterface.add(lab, 1, 0 );
							NFInterface.add(comp, 2, 0 );
							NFInterface.add(txtVal, 3, 0);
							NFInterface.setPadding(new Insets(30));

						
						e.consume();
					}
				};
				
		NFMemoryConsp.setOnAction(memoryConspEvent);		
		root.setTop(NFtools);
		root.setCenter(NFInterface);
		Scene scene = new Scene(root, 600, 360);
		this.setScene(scene);
		
	}
	
	private void closeWindow() {
		this.close();
	}

}
