package application.view;

import application.include.Alert;
import application.include.Data;
import application.include.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import monitoring.elements.Component;
import monitoring.elements.ComponentImplementation;
import monitoring.elements.Configuration;
import monitoring.elements.Port;

public class NewConfigurationWindow extends Stage{
	 
	// Dialog box elements
		private GridPane newComponentInterface = new GridPane();
		private Text newComponentTitle = new Text();
		private TextField newComponentName = new TextField();
		private Label extendConf = new Label("extend configuration");
		public Button newComponentSubmit = new Button("Submit");
		public Button deleteComponent = new Button("Delete");
		private ComboBox<Configuration> conf;

		public NewConfigurationWindow(int editIndex, Model data,TabPane window) {
			// Set window title
			newComponentTitle.setText((editIndex == -1) ? "Create New Configuration" : "Edit Configuration");

			// Attach elements to window
			if (editIndex == -1) {
				newComponentName.setPromptText("Coonfiguration name...");
				conf = new ComboBox<Configuration>(data.getConfigurationProperty());

			} else {
				newComponentName.setText(data.getConfigurationModel(editIndex).getName());
			}

			// Place elements on stage
			newComponentInterface.add(newComponentTitle, 0, 0, 2, 1);
			newComponentInterface.add(newComponentName, 0, 1, 2, 1);
			newComponentInterface.add(extendConf, 1 ,5);
			newComponentInterface.add(conf, 2,5);
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
						
						if(conf.getSelectionModel().getSelectedItem() != null) {
							Data d = new Data();
							
							
							
							Configuration co = conf.getValue();
							d.loadData2( data, window,co,data.getConfigurationTail(),newComponentName.getText());
	
						}else {data.addConfigurationModel(data.getConfigurationTail(),newComponentName.getText());}
					} else {
						data.getConfigurationModel(editIndex).setName(newComponentName.getText());
					}
					closeWindow();
					e.consume();
				}
			};

			// Handler to delete the selected class
			EventHandler<ActionEvent> deleteComponentEvent = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					data.removeConfigurationModel(editIndex);
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
		public Port getPort(Port p , Configuration c) {
			for(int i =0; i<c.getImplementations().size();i++) {
				
				for(int j=0 ;j<c.getImplementations().get(i).getPorts().size();j++ ) {
					
					if(c.getImplementations().get(i).getPorts().get(j).getName() == p.getName()) {
						System.out.println(p.getName());
						return c.getImplementations().get(i).getPorts().get(j);
						}
					}
					
				}
			
			
			return null;
			
		}

}
