package application.view;

import application.include.Alert;
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
						data.addConfigurationModel(data.getConfigurationTail(),newComponentName.getText());
						if(conf.getSelectionModel().getSelectedItem() != null) {
							SingleSelectionModel<Tab> singleselectionModel = window.getSelectionModel();
							singleselectionModel.select(window.getTabs().get(window.getTabs().size()-1));
							Configuration config = conf.getSelectionModel().getSelectedItem(); 
							config.getImplementations().forEach(impl->{
						        
								data.addImplementationModel(new int[] { data.getImplementationTail(), 0, 0, 100, 100 }, impl.getName(),
										impl.getComponentType(),data.getConfigurationModel(data.getConfigurationTail()-1));
								
						impl.getPorts().forEach( p->{
							data.addPortModel(
									new int[] { data.getComponentTail(), impl.getXPos() + 240,
											impl.getYPos() + impl.getPorts().size() * 25 + 15,
												100, 100 },
									new String[] { p.getName(),
											p.getType(), p.cspExpression.getExpression() },
									data.getConfigurationModel(data.getConfigurationTail()-1).getImplementations().get(config.getImplementations().indexOf(impl)));
						}) ; 
							});
							
							config.getConnectors().forEach(c->{
								data.addLinkModel(new int[] {data.getLinkTail(),c.getType(),c.getSource(),c.getDest(),-2,-2,-2,-2},"",data.getConfigurationModel(data.getConfigurationTail()-1),c.formule,
										c.Bandwidth,
										getPort(c.getInPort(),data.getConfigurationModel(data.getConfigurationTail()-1)),
										getPort(c.getOutPort(),data.getConfigurationModel(data.getConfigurationTail()-1)));				
							});
	
						}
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
