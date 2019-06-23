package application.view;



import javafx.scene.text.*;
import javafx.stage.Stage;
import monitoring.elements.Method;
import monitoring.elements.Methode;
import application.include.Alert;

import java.lang.*;
import java.util.*;

import application.include.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public  class NewMethodeWindow extends Stage {

    protected final Button button;
    protected final TextField textField;
    protected final Label label;
    protected final Label label0;
    protected AnchorPane p = new AnchorPane();

    public NewMethodeWindow(int editIndex, Model data) {

        button = new Button();
        textField = new TextField();
        label = new Label();
        label0 = new Label();

        p.setId("AnchorPane");
        p.setPrefHeight(137.0);
        p.setPrefWidth(600.0);

        button.setLayoutX(436.0);
        button.setLayoutY(66.0);
        button.setMnemonicParsing(false);
        button.setPrefHeight(25.0);
        button.setPrefWidth(149.0);
        button.setText("ADD Method");

        textField.setLayoutX(140.0);
        textField.setLayoutY(66.0);
        textField.setPrefHeight(25.0);
        textField.setPrefWidth(269.0);

        label.setLayoutX(14.0);
        label.setLayoutY(70.0);
        label.setText("Method Name :");

        label0.setLayoutX(237.0);
        label0.setLayoutY(14.0);
        label0.setText("New Method ");
        label0.setFont(new Font(16.0));

        p.getChildren().add(button);
        p.getChildren().add(textField);
        p.getChildren().add(label);
        p.getChildren().add(label0);
        
        
        
        EventHandler<ActionEvent> ADDmethode = new EventHandler<ActionEvent>() {
			@Override
			
			
			public void handle(ActionEvent e) {
				StringProperty s = new SimpleStringProperty();  ;
			//	Alert.display("", data.getComponentModel(editIndex).getName());
				s.setValue(textField.getText());
		Methode m = new Methode(s);
				data.getComponentModel(editIndex).ADDMethode(m);
			
				closeWindow();
				e.consume();
			}
		};
        
        
        
        
        button.setOnAction(ADDmethode);
        
        
        
        
        
        
        
        
        
        
        
        Scene scene = new Scene(p, 600,137);
     		this.setScene(scene);

    }
    private void closeWindow() {
		this.close();
	}
}

