package application.view;



import javafx.scene.text.*;
import javafx.stage.Stage;
import monitoring.elements.Component;
import monitoring.elements.Methode;

import java.lang.*;
import java.util.*;
import application.include.Alert;
import application.include.Model;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public  class NFattributWindow extends Stage {
 	ArrayList<Strecture> list = new ArrayList<Strecture>();
 	ArrayList<Strecture> list2 = new ArrayList<Strecture>();
	AnchorPane p = new AnchorPane();
    protected final TabPane tabPane;
    protected final Tab tab;
    protected final AnchorPane anchorPane;
    protected ComboBox comboBox;
    protected final Label label;
    protected final Separator separator;
    protected final Button button;
    protected final Label label0;
    protected final Label label1;
    protected final Label label2;
    protected final TextField textField;
    protected final Tab tab0;
    protected final AnchorPane anchorPane0;
    protected final TextField textField0;
    protected final Button button0;
    protected final Label label3;
    protected final Label label4;
    protected final Label label5;
    protected final Tab tab1;
    protected final AnchorPane anchorPane1;
    protected final Label label6;

    @SuppressWarnings("unchecked")
	public NFattributWindow(Model data) {

        tabPane = new TabPane();
        tab = new Tab();
        anchorPane = new AnchorPane();
        comboBox = new ComboBox<Component>( data.getComponentProperty());
       
        label = new Label();
        separator = new Separator();
        button = new Button();
        label0 = new Label();
        label1 = new Label();
        label2 = new Label();
        textField = new TextField();
        tab0 = new Tab();
        anchorPane0 = new AnchorPane();
        textField0 = new TextField();
        button0 = new Button();
        label3 = new Label();
        label4 = new Label();
        label5 = new Label();
        tab1 = new Tab();
        anchorPane1 = new AnchorPane();
        label6 = new Label();

        p.setId("AnchorPane");
        p.setPrefHeight(617.0);
        p.setPrefWidth(749.0);

        tabPane.setLayoutX(8.0);
        tabPane.setLayoutY(52.0);
        tabPane.setPrefHeight(562.0);
        tabPane.setPrefWidth(741.0);
        tabPane.setTabClosingPolicy(javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE);

        tab.setText("Method Execution Time");

        anchorPane.setMinHeight(0.0);
        anchorPane.setMinWidth(0.0);
        anchorPane.setPrefHeight(180.0);
        anchorPane.setPrefWidth(200.0);

        comboBox.setLayoutX(305.0);
        comboBox.setLayoutY(26.0);
        comboBox.setPrefHeight(25.0);
        comboBox.setPrefWidth(214.0);

        label.setLayoutX(211.0);
        label.setLayoutY(30.0);
        label.setText("Component :");

        separator.setLayoutX(3.0);
        separator.setLayoutY(72.0);
        separator.setPrefHeight(3.0);
        separator.setPrefWidth(735.0);

        button.setLayoutX(579.0);
        button.setLayoutY(464.0);
        button.setMnemonicParsing(false);
        button.setPrefHeight(25.0);
        button.setPrefWidth(148.0);
        button.setText("Submit");

        label0.setLayoutX(14.0);
        label0.setLayoutY(97.0);
        label0.setPrefHeight(17.0);
        label0.setPrefWidth(96.0);
        label0.setText("Execution Time [");

        label1.setLayoutX(120.0);
        label1.setLayoutY(97.0);
        label1.setPrefHeight(17.0);
        label1.setPrefWidth(105.0);
        label1.setText(".....................");

        label2.setLayoutX(225.0);
        label2.setLayoutY(97.0);
        label2.setText("]   = ");

        textField.setLayoutX(263.0);
        textField.setLayoutY(93.0);
        textField.setPromptText("Value  (s)");
        tab.setContent(anchorPane);

        tab0.setText("Component Memory Comsuption");

        anchorPane0.setMinHeight(0.0);
        anchorPane0.setMinWidth(0.0);
        anchorPane0.setPrefHeight(180.0);
        anchorPane0.setPrefWidth(200.0);

        textField0.setLayoutX(335.0);
        textField0.setLayoutY(45.0);
        textField0.setPromptText("Value  (MB)");

        button0.setLayoutX(579.0);
        button0.setLayoutY(486.0);
        button0.setMnemonicParsing(false);
        button0.setPrefHeight(25.0);
        button0.setPrefWidth(129.0);
        button0.setText("Submit");

        label3.setLayoutX(14.0);
        label3.setLayoutY(49.0);
        label3.setPrefHeight(17.0);
        label3.setPrefWidth(138.0);
        label3.setText("Memory Comsuption [");

        label4.setLayoutX(141.0);
        label4.setLayoutY(49.0);
        label4.setPrefHeight(17.0);
        label4.setPrefWidth(149.0);
        label4.setText("...................");

        label5.setLayoutX(301.0);
        label5.setLayoutY(49.0);
        label5.setText("] =");
        tab0.setContent(anchorPane0);

        tab1.setText("Connector BandWidth ");

        anchorPane1.setMinHeight(0.0);
        anchorPane1.setMinWidth(0.0);
        anchorPane1.setPrefHeight(180.0);
        anchorPane1.setPrefWidth(200.0);
        tab1.setContent(anchorPane1);

        label6.setLayoutX(290.0);
        label6.setLayoutY(14.0);
        label6.setText("Non Fonctionel Attribut");
        label6.setFont(new Font(18.0));

        anchorPane.getChildren().add(comboBox);
        anchorPane.getChildren().add(label);
        anchorPane.getChildren().add(separator);
        anchorPane.getChildren().add(button);
      /*  anchorPane.getChildren().add(label0);
        anchorPane.getChildren().add(label1);
        anchorPane.getChildren().add(label2);
        anchorPane.getChildren().add(textField);
        */
        tabPane.getTabs().add(tab);
         anchorPane0.getChildren().add(button0);
       /* anchorPane0.getChildren().add(textField0);
       
        anchorPane0.getChildren().add(label3);
        anchorPane0.getChildren().add(label4);
        anchorPane0.getChildren().add(label5);
        */
        tabPane.getTabs().add(tab0);
       // tabPane.getTabs().add(tab1);
        p.getChildren().add(tabPane);
        p.getChildren().add(label6);
       this.list =    memory(data);
      
       comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
           @Override
           public void changed(ObservableValue ov, Object t, Object t1) {
        	 //  System.out.println("rbfsdbf");
        	list2 =    time(data,(Component)t1);
         
              
              
           }
       }); 
        EventHandler<ActionEvent> Submit1 = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				for(int j =0 ; j<list2.size();j++) {
			 		for(int i = 0 ; i<data.getComponentProperty().size();i++) {
			 			if(list2.get(j).comp2.equals(data.getComponentModel(i))) { 
			 				for(int k =0 ; k<data.getComponentModel(i).getMethode().size();k++) {
			 					
			 		if(list2.get(j).method.equals(data.getComponentModel(i).getMethode().get(k))) {
			 	data.getComponentModel(i).getMethode().get(k).ExecutionTime = Integer.parseInt( list2.get(j).val2.getText());
			 					}
			 				}
			 				
			 				
			 				
			 			}
			 		}
			 		
			 	}
				e.consume();
			}
		};
		 EventHandler<ActionEvent> Submit2 = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
				 	for(int j =0 ; j<list.size();j++) {
				 		for(int i = 0 ; i<data.getComponentProperty().size();i++) {
				 			if(list.get(j).comp.equals(data.getComponentModel(j))) {  
				 				data.getComponentModel(j).Memory =Integer.parseInt( list.get(i).val.getText());
				 			}
				 		}
				 		
				 	}
				 	Alert.display("Memoty"," Components Memory Added");
					e.consume();
				}
			};
	        
        
        
		button.setOnAction(Submit1);
		button0.setOnAction(Submit2);
		
		Scene scene = new Scene(p, 749,617);
		this.setScene(scene);
		
        
    }
    public ArrayList<Strecture> memory(Model data) {
    	ArrayList<Strecture> list = new ArrayList<Strecture>();
    	int tab = 0;
    	for(int i=0;i<data.getComponentProperty().size();i++) {
    		   TextField val = new TextField() ;
    		    Label memory = new Label();
    		    Label c = new Label();
    		    Label f = new Label();
    		   
    		    
    		  
    		    
    		    memory.setLayoutX(14.0);
    		    memory.setLayoutY(49.0 + tab);
    		    memory.setPrefHeight(17.0);
    		    memory.setPrefWidth(138.0);
            memory.setText("Memory Comsuption [");    
            c.setLayoutX(141.0 + 30);
           c.setLayoutY(49.0 + tab);
            c.setPrefHeight(17.0);
            c.setPrefWidth(149.0);
            c.setText(data.getComponentModel(i).getNameProp().getValue());
            
            f.setLayoutX(301.0);
            f.setLayoutY(49.0 + tab);
            f.setText("] =");
            
    		val.setLayoutX(335.0);
            val.setLayoutY(45.0 + tab);
           val.setText(data.getComponentModel(i).Memory+"");
            val.setPromptText("Value  (MB)");
            tab+=35;
            
            
           

            
            anchorPane0.getChildren().add(val);
            anchorPane0.getChildren().add(memory);
            anchorPane0.getChildren().add(c);
            anchorPane0.getChildren().add(f);
            Strecture s = new Strecture(data.getComponentModel(i),val,memory,c,f);
            list.add(s);
           
    		
    		
    	}
    	return list;
    	
    	
    }
    
    public ArrayList<Strecture>  time(Model data , Component c) {
    	anchorPane.getChildren().clear();
    	anchorPane.getChildren().add(comboBox);
        anchorPane.getChildren().add(label);
        anchorPane.getChildren().add(separator);
        anchorPane.getChildren().add(button);
    	ArrayList<Strecture> temp = new ArrayList<Strecture>();
    	int tab = 0;
    	for(int i=0;i<c.getMethode().size();i++) {
    	 TextField val = new TextField() ;
		    Label d = new Label();
		    Label m = new Label();
		    Label f = new Label();
    	
    	
    	  d.setLayoutX(14.0);
          d.setLayoutY(97.0 + tab);
          d.setPrefHeight(17.0);
          d.setPrefWidth(96.0);
          d.setText("Execution Time [");

          m.setLayoutX(120.0);
          m.setLayoutY(97.0 + tab);
          m.setPrefHeight(17.0);
          m.setPrefWidth(105.0);
          m.setText(c.getMethode().get(i).getMethodeName().getValue());

          f.setLayoutX(225.0);
          f.setLayoutY(97.0 + tab);
          f.setText("]   = ");

          val.setLayoutX(263.0);
          val.setLayoutY(93.0 +tab );
          val.setText(c.getMethode().get(i).ExecutionTime+"");
          val.setPromptText("Value  (s)");
          tab+= 35;
          
          
          anchorPane.getChildren().add(val);
          anchorPane.getChildren().add(d);
          anchorPane.getChildren().add(m);
          anchorPane.getChildren().add(f);
          Strecture s = new Strecture(val,d,m,f,c.getMethode().get(i),c);
          temp.add(s);
    	}
    	
    	return temp;
    	
    	
    }
}

