package application.view;


import java.io.IOException;
import java.lang.*;
import java.util.*;

import application.include.Alert;
import application.include.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import monitoring.elements.Configuration;
import monitoring.elements.Reconfiguration;
import monitoring.elements.VerificationFDR;

public  class ReconfigurationWindow extends Stage {

    protected  Button button;
    protected  ChoiceBox<Configuration> choiceBox;
    protected  ChoiceBox<Configuration> choiceBox0;
    protected  Label label;
    protected  Label label0;
    protected AnchorPane p ;
    protected  Button button0;
    
    
    public ReconfigurationWindow() {}
    public ReconfigurationWindow(Model data) {
    	
    
			choiceBox = new ChoiceBox<Configuration>(	data.getConfigurationProperty());
			choiceBox0 = new ChoiceBox<Configuration>(	data.getConfigurationProperty());
			
			 button0 = new Button();

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
        p.getChildren().add(button0);
        p.getChildren().add(choiceBox);
        p.getChildren().add(choiceBox0);
        p.getChildren().add(label);
        p.getChildren().add(label0);
        
        button0.setLayoutX(384.0);
        button0.setLayoutY(154.0);
        button0.setMnemonicParsing(false);
        button0.setPrefHeight(25.0);
        button0.setPrefWidth(107.0);
        button0.setText("methode ");
        EventHandler<ActionEvent> methode = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
			String total="";
			Configuration Source = choiceBox.getValue();
			Configuration distination  = choiceBox0.getValue();
			 Alert.display("methode",distination.methodFormula(1));
			 Alert.display("methode",Source.methodFormula(1));
			}};
        
         EventHandler<ActionEvent> FDR = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
				String total="";
				Configuration Source = choiceBox0.getValue();
				Configuration distination  = choiceBox.getValue();
				
				
				
				VerificationFDR f = new VerificationFDR();
				
				
				if((distination == null)&&(Source == null)) {  Alert.display("Error","You must choose Configuration");
			//	data.getConfigurationModel(0).name.setValue("confi");
				
			//	f.ValidateConfiguration(data.getConfigurationModel(0));
				
			/*	Reconfiguration r = new Reconfiguration(Source , distination);
				try {
					f.reconfig(r);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				*/
				
				}
				if(distination == null) {  f.ValidateConfiguration(Source);   }else {
					Reconfiguration r = new Reconfiguration(Source , distination);
					try {
						f.reconfig(r);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					
					
				}
				
				
			
				/*
				for(int i = 0 ; i<c.getComponents().size();i++) {
				total+=	c.getComponents().get(i).getName()+"\n";
				total+=	c.getComponents().get(i).getComponentType().expGlobale.getExpression()+"\n";
				for(int j =0 ; j< c.getComponents().get(i).getPorts().size();j++ ) {
					
					total += c.getComponents().get(i).getPorts().get(j).getName()+"   hhh "+c.getComponents().get(i).getPorts().get(j).getCspExpression().getExpression()+"\n";
				}
				}
				for(int h = 0;h<c.getConnectors().size();h++) {
					total += c.getConnectors().get(h).formule.getExpression()+"\n";
				}
				Alert.display("", total);
				*/
				
					//closeWindow();
					e.consume();
				}
			};
        
        
        Scene scene = new Scene(p, 600,185);
		this.setScene(scene);
		
		
		button.setOnAction(FDR);
		button0.setOnAction(methode);
		
		
    }

	
}
