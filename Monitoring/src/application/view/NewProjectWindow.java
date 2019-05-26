package application.view;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import application.include.Alert;
import application.include.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NewProjectWindow extends Stage {

	// Dialog box elements
	private GridPane newProjectInterface = new GridPane();
	private Text newProjectTitle = new Text();
	private TextField newProjectName = new TextField();
	public Button newProjectSubmit = new Button("Submit");

	// dir
	public String dir;

	public NewProjectWindow(int editIndex, Model data) {
		// Set window title
		newProjectTitle.setText((editIndex == -1) ? "Create Project" : "Edit Project Block");

		// Attach elements to window
		if (editIndex == -1) {
			newProjectName.setPromptText("Project name...");

		} else {

		}

		// Place elements on stage
		newProjectInterface.add(newProjectTitle, 0, 0, 2, 1);
		newProjectInterface.add(newProjectName, 0, 1, 2, 1);
		newProjectInterface.add(newProjectSubmit, 1, 6);

		// Handler to submit the selected class
		EventHandler<ActionEvent> submitProjectEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.saveUndoState();
				data.clearRedoState();
				JFileChooser chooser = new JFileChooser();
				if (editIndex == -1) {

					String choosertitle = "";

					chooser.setCurrentDirectory(new java.io.File("."));
					chooser.setDialogTitle(choosertitle);
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					//
					// disable the "All files" option.
					//
					chooser.setAcceptAllFileFilterUsed(false);
					//
					if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						boolean success = (new File(chooser.getSelectedFile() +"\\"+ newProjectName.getText())).mkdirs();
						data.project_dir = chooser.getSelectedFile() + "\\" + newProjectName.getText();
						String fileSeparator = System.getProperty("file.separator");
						File file = new File(data.project_dir+fileSeparator+"mainComponent.comp");
						if (file != null) {
							try {
								data.saveComponent(file);
								data.getConfigurationProperty().forEach(conf->{
									try {
										File file2 = new File(data.project_dir + fileSeparator +conf.getName()+".conf"); 
										if(file != null) {
										data.save(file2, conf.getIndex());
										}
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}	
									});
							} catch (IOException ex) {
								System.err.println("IO Failure: " + ex);
							}
						}
					} else {
						Alert.display("Warning", "No Selection ");
					}

				} else {

				}
				closeWindow(data);
				e.consume();
			}
		};

		// Attach handlers to buttons
		newProjectSubmit.setOnAction(submitProjectEvent);

		// Display scene
		Scene scene = new Scene(newProjectInterface, 300, 230);
		this.setScene(scene);

	}

	private void closeWindow(Model data) {
		data.saved = true;
		this.close();
	}

}
