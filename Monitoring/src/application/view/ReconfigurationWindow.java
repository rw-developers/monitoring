package application.view;


import java.lang.*;
import java.util.*;

import application.include.Model;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import monitoring.elements.Configuration;

public  class ReconfigurationWindow extends Stage {

    protected  Button button;
    protected  ChoiceBox<Configuration> choiceBox;
    protected  ChoiceBox<Configuration> choiceBox0;
    protected  Label label;
    protected  Label label0;
    protected AnchorPane p ;
    
    
    public ReconfigurationWindow() {}
    public ReconfigurationWindow(Model data) {
    	
    
			choiceBox = new ChoiceBox<Configuration>(	data.getConfigurationProperty());
			choiceBox0 = new ChoiceBox<Configuration>(	data.getConfigurationProperty());
			
		

        button = new Button();
     
        label = new Label();
        label0 = new Label();
      p = new AnchorPane();
        p.setId("AnchorPane");
        p.setPrefHeight(185.0);
        p.setPrefWidth(600.0);

        button.setLayoutX(190.0);
        button.setLayoutY(123.0);
        button.setMnemonicParsing(false);
        button.setPrefHeight(25.0);
        button.setPrefWidth(203.0);
        button.setText("Verefication AND Validation ");

        choiceBox.setLayoutX(436.0);
        choiceBox.setLayoutY(50.0);
        choiceBox.setPrefWidth(150.0);

        choiceBox0.setLayoutX(141.0);
        choiceBox0.setLayoutY(50.0);
        choiceBox0.setPrefWidth(150.0);

        label.setLayoutX(14.0);
        label.setLayoutY(54.0);
        label.setText("Source Configuration :");

        label0.setLayoutX(310.0);
        label0.setLayoutY(54.0);
        label0.setText("Target Configuration :");

        p.getChildren().add(button);
        p.getChildren().add(choiceBox);
        p.getChildren().add(choiceBox0);
        p.getChildren().add(label);
        p.getChildren().add(label0);
        Scene scene = new Scene(p, 600,185);
		this.setScene(scene);
		
		

    }

	
}
