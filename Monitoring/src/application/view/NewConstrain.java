package application.view;




import java.lang.*;
import java.util.*;

import application.include.Model;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import application.include.Alert;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import monitoring.elements.Component;
import monitoring.elements.Method;
import monitoring.elements.Methode;
import monitoring.elements.VerificationFDR;
import nfattribute.OtherConstraint;
import nfattribute.TimedConstraint;
 
public  class NewConstrain extends Stage {
	AnchorPane p = new AnchorPane();
    protected  Label label;
    protected  TabPane tabPane;
    protected  Tab tab;
    protected  AnchorPane anchorPane;
    protected  ComboBox comboBox;
    protected  ComboBox comboBox0;
    protected  ComboBox comboBox1;
    protected  Button button;
    protected  TextField textField;
    protected  TextField textField0;
    protected  Button button0;
    protected  Separator separator;
    protected  TableView<OtherConstraint> tableView;
    protected  TableColumn<OtherConstraint,String> tableColumn;
    protected  TableColumn <OtherConstraint,String>tableColumn0;
    protected  Tab tab0;
    protected  AnchorPane anchorPane0;
    protected  Separator separator0;
    protected  TableView<TimedConstraint> tableView0;
    protected  TableColumn tableColumn1;
    protected  TableColumn<TimedConstraint,String> tableColumn2;
    protected  TextField textField1;
    protected  Label label0;
    protected  Button button1;
    protected  Button button2;
    protected  Button button3;
    protected  ComboBox <Component> choiceBox;
    protected  ComboBox  comboBox5;
    protected  ComboBox <Component> choiceBox0;
    protected ComboBox<Methode> choiceBox1;
    protected  ComboBox<Methode>choiceBox2;
    protected  ChoiceBox choiceBox3;
    protected  ChoiceBox choiceBox4;
    protected  Label label1;
    protected  Label label2;
    protected  ChoiceBox choiceBox5;
    protected  ChoiceBox choiceBox6;
    protected  Label label3;
    protected  Label label4;
    protected  Tab tab1;
    protected  AnchorPane anchorPane1;
    protected  Separator separator1;

    @SuppressWarnings("unchecked")
	public NewConstrain(Model data) {

        label = new Label();
        tabPane = new TabPane();
        tab = new Tab();
        anchorPane = new AnchorPane();
        choiceBox = new ComboBox<Component>( data.getComponentProperty());
        choiceBox0 = new ComboBox<Component>( data.getComponentProperty());
     
        
    	ObservableList<String> options = FXCollections.observableArrayList("HARD", "BEST EFFORT","Lite");
      	 comboBox = new ComboBox(options);
      	 ObservableList<String> options2 = FXCollections.observableArrayList("SOMME", "AVG","MIN","MAX");
          comboBox0 = new ComboBox(options2);
          ObservableList<String> options3 = FXCollections.observableArrayList("==", "<",">","<=",">=");
          comboBox1 = new ComboBox(options3);
          ObservableList<String> options5 = FXCollections.observableArrayList("MemoryComsuption", "BandWidth");
          comboBox5 = new ComboBox(options5);
     
        button = new Button();
        textField = new TextField();
        textField0 = new TextField();
        button0 = new Button();
        separator = new Separator();
        tableView = new TableView();
        tableColumn = new TableColumn();
        tableColumn0 = new TableColumn();
        tab0 = new Tab();
        anchorPane0 = new AnchorPane();
        separator0 = new Separator();
        tableView0 = new TableView();
        tableColumn1 = new TableColumn();
        tableColumn2 = new TableColumn();
        textField1 = new TextField();
        label0 = new Label();
        button1 = new Button();
        button2 = new Button();
        button3 = new Button();
        choiceBox2 = new ComboBox<Methode>();
        choiceBox1 = new ComboBox<Methode>();
      
        ObservableList<String> optionsevent = FXCollections.observableArrayList("D", "F");
        ObservableList<String> optionop = FXCollections.observableArrayList("+", "-");
     
        
 
 

     
        choiceBox3 = new ChoiceBox(optionsevent);
        choiceBox4 = new ChoiceBox(optionsevent);
        label1 = new Label();
        label2 = new Label();
        choiceBox5 = new ChoiceBox(optionop);
        choiceBox6 = new ChoiceBox(options3);
        label3 = new Label();
        label4 = new Label();
        tab1 = new Tab();
        anchorPane1 = new AnchorPane();
        separator1 = new Separator();

       
       
        
        
        
        
        
        p.setId("AnchorPane");
        p.setPrefHeight(591.0);
        p.setPrefWidth(653.0);

        label.setLayoutX(238.0);
        label.setLayoutY(14.0);
        label.setText("Constraint NON Fonctionnel");

        tabPane.setLayoutX(3.0);
        tabPane.setLayoutY(61.0);
        tabPane.setPrefHeight(528.0);
        tabPane.setPrefWidth(646.0);
        tabPane.setTabClosingPolicy(javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE);

        tab.setClosable(false);
        tab.setText("Generique Constraint");

        anchorPane.setMinHeight(0.0);
        anchorPane.setMinWidth(0.0);
        anchorPane.setPrefHeight(180.0);
        anchorPane.setPrefWidth(200.0);

        comboBox.setLayoutX(14.0);
        comboBox.setLayoutY(66.0);
        comboBox.setPrefHeight(25.0);
        comboBox.setPrefWidth(85.0);
        comboBox.setPromptText("Type");

        comboBox0.setLayoutX(107.0);
        comboBox0.setLayoutY(66.0);
        comboBox0.setPrefHeight(25.0);
        comboBox0.setPrefWidth(100.0);
        comboBox0.setPromptText("Fonction");

        comboBox1.setLayoutX(398.0);
        comboBox1.setLayoutY(66.0);
        comboBox1.setPrefHeight(25.0);
        comboBox1.setPrefWidth(121.0);
        comboBox1.setPromptText("OP Arithmetic");

        button.setLayoutX(527.0);
        button.setLayoutY(121.0);
        button.setMnemonicParsing(false);
        button.setPrefHeight(25.0);
        button.setPrefWidth(97.0);
        button.setText("Submit");

        textField.setLayoutX(537.0);
        textField.setLayoutY(66.0);
        textField.setPrefHeight(25.0);
        textField.setPrefWidth(95.0);
        textField.setPromptText("Value");

        comboBox5.setLayoutX(218.0);
        comboBox5.setLayoutY(66.0);
        comboBox5.setPrefHeight(25.0);
        comboBox5.setPrefWidth(165.0);
        comboBox5.setPromptText("NF ATTRIBUTE");
       

        button0.setLayoutX(437.0);
        button0.setLayoutY(121.0);
        button0.setMnemonicParsing(false);
        button0.setPrefHeight(25.0);
        button0.setPrefWidth(67.0);
        button0.setText("Exemple");

        separator.setLayoutX(14.0);
        separator.setLayoutY(176.0);
        separator.setPrefHeight(6.0);
        separator.setPrefWidth(628.0);

        tableView.setLayoutX(6.0);
        tableView.setLayoutY(191.0);
        tableView.setPrefHeight(258.0);
        tableView.setPrefWidth(635.0);

        tableColumn.setPrefWidth(50.0);
        tableColumn.setText("ID");

        tableColumn0.setPrefWidth(580.0);
        tableColumn0.setText("Constraint");
        tab.setContent(anchorPane);

        tab0.setClosable(false);
        tab0.setText("Timed Constraint");

        anchorPane0.setMinHeight(0.0);
        anchorPane0.setMinWidth(0.0);
        anchorPane0.setPrefHeight(180.0);
        anchorPane0.setPrefWidth(200.0);

        separator0.setLayoutX(4.0);
        separator0.setLayoutY(211.0);
        separator0.setPrefHeight(6.0);
        separator0.setPrefWidth(639.0);

        tableView0.setLayoutX(6.0);
        tableView0.setLayoutY(227.0);
        tableView0.setPrefHeight(258.0);
        tableView0.setPrefWidth(635.0);

        tableColumn1.setPrefWidth(50.0);
        tableColumn1.setText("ID");

        tableColumn2.setPrefWidth(580.0);
        tableColumn2.setText("Constraint");

        textField1.setLayoutX(159.0);
        textField1.setLayoutY(166.0);
        textField1.setPrefHeight(25.0);
        textField1.setPrefWidth(184.0);
        textField1.setPromptText("Value");

        label0.setLayoutX(14.0);
        label0.setLayoutY(26.0);
        label0.setPrefHeight(17.0);
        label0.setPrefWidth(582.0);
        label0.setText("Syntax :  ComponentName.[D/F].MethodName Op ComponentName.[D/F].MethodName OPLogique Valeur ");

        button1.setLayoutX(578.0);
        button1.setLayoutY(178.0);
        button1.setMnemonicParsing(false);
        button1.setText("Submit");

        button2.setLayoutX(509.0);
        button2.setLayoutY(178.0);
        button2.setMnemonicParsing(false);
        button2.setText("Exemple");

        button3.setLayoutX(392.0);
        button3.setLayoutY(178.0);
        button3.setMnemonicParsing(false);
        button3.setText("Check Connector");
     //   ObservableList<Component> optionsc = data.getComponentProperty();

        choiceBox.setLayoutX(14.0);
        choiceBox.setLayoutY(121.0);
        choiceBox.setPrefHeight(25.0);
        choiceBox.setPrefWidth(114.0);

        choiceBox0.setLayoutX(14.0);
        choiceBox0.setLayoutY(68.0);
        choiceBox0.setPrefHeight(25.0);
        choiceBox0.setPrefWidth(116.0);

        choiceBox1.setLayoutX(143.0);
        choiceBox1.setLayoutY(68.0);
        choiceBox1.setPrefHeight(25.0);
        choiceBox1.setPrefWidth(110.0);

        choiceBox2.setLayoutX(143.0);
        choiceBox2.setLayoutY(121.0);
        choiceBox2.setPrefHeight(25.0);
        choiceBox2.setPrefWidth(111.0);

        choiceBox3.setLayoutX(268.0);
        choiceBox3.setLayoutY(68.0);
        choiceBox3.setPrefHeight(25.0);
        choiceBox3.setPrefWidth(87.0);

        choiceBox4.setLayoutX(268.0);
        choiceBox4.setLayoutY(121.0);
        choiceBox4.setPrefHeight(25.0);
        choiceBox4.setPrefWidth(87.0);

        label1.setLayoutX(14.0);
        label1.setLayoutY(51.0);
        label1.setPrefHeight(17.0);
        label1.setPrefWidth(340.0);
        label1.setText("Component 1                 Method 1                       Event1");

        label2.setLayoutX(14.0);
        label2.setLayoutY(106.0);
        label2.setPrefHeight(17.0);
        label2.setPrefWidth(341.0);
        label2.setText("Component 2                Method 2                       Event 2");

        choiceBox5.setLayoutX(371.0);
        choiceBox5.setLayoutY(94.0);
        choiceBox5.setPrefHeight(25.0);
        choiceBox5.setPrefWidth(65.0);

        choiceBox6.setLayoutX(14.0);
        choiceBox6.setLayoutY(166.0);
        choiceBox6.setPrefHeight(25.0);
        choiceBox6.setPrefWidth(117.0);

        label3.setLayoutX(378.0);
        label3.setLayoutY(72.0);
        label3.setText("OP");

        label4.setLayoutX(14.0);
        label4.setLayoutY(146.0);
        label4.setText("OPLogique");
        tab0.setContent(anchorPane0);

        tab1.setText("Untitled Tab 2");

        anchorPane1.setMinHeight(0.0);
        anchorPane1.setMinWidth(0.0);
        anchorPane1.setPrefHeight(180.0);
        anchorPane1.setPrefWidth(200.0);
        tab1.setContent(anchorPane1);

        separator1.setLayoutX(3.0);
        separator1.setLayoutY(48.0);
        separator1.setPrefHeight(6.0);
        separator1.setPrefWidth(646.0);

        p.getChildren().add(label);
        anchorPane.getChildren().add(comboBox);
        anchorPane.getChildren().add(comboBox0);
        anchorPane.getChildren().add(comboBox1);
        anchorPane.getChildren().add(button);
        anchorPane.getChildren().add(textField);
        anchorPane.getChildren().add(comboBox5);
      //  anchorPane.getChildren().add(textField0);
      //  anchorPane.getChildren().add(button0);
        anchorPane.getChildren().add(separator);
       // tableView.getColumns().add(tableColumn);
        tableView.getColumns().add(tableColumn0);
        anchorPane.getChildren().add(tableView);
        tabPane.getTabs().add(tab);
        anchorPane0.getChildren().add(separator0);
      //  tableView0.getColumns().add(tableColumn1);
        tableView0.getColumns().add(tableColumn2);
        anchorPane0.getChildren().add(tableView0);
        anchorPane0.getChildren().add(textField1);
        anchorPane0.getChildren().add(label0);
        anchorPane0.getChildren().add(button1);
       // anchorPane0.getChildren().add(button2);
       // anchorPane0.getChildren().add(button3);
        anchorPane0.getChildren().add(choiceBox);
        anchorPane0.getChildren().add(choiceBox0);
        anchorPane0.getChildren().add(choiceBox1);
        anchorPane0.getChildren().add(choiceBox2);
        anchorPane0.getChildren().add(choiceBox3);
        anchorPane0.getChildren().add(choiceBox4);
        anchorPane0.getChildren().add(label1);
        anchorPane0.getChildren().add(label2);
        anchorPane0.getChildren().add(choiceBox5);
        anchorPane0.getChildren().add(choiceBox6);
        anchorPane0.getChildren().add(label3);
        anchorPane0.getChildren().add(label4);
        tabPane.getTabs().add(tab0);
      //  tabPane.getTabs().add(tab1);
        p.getChildren().add(tabPane);
        p.getChildren().add(separator1);
        
        
       

        
      /*  choiceBox.getSelectionModel().selectedItemProperty().addListener(
        		( ov, oldVal, newVal ) -> {
        		   Component c = newVal;
        		    // change label                
       choiceBox1 = new ComboBox<Methode>( methodlist(c));
        		 });
        */
        choiceBox0.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
            	//Alert.display("", ((Component)t1).getName());
               ObservableList<Methode> combox2 = FXCollections.observableArrayList( methodlist((monitoring.elements.Component) t1) );
               choiceBox1.setItems((ObservableList<Methode>)combox2);
               
               
            }
        });
        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
            //	Alert.display("", ((Component)t1).getName());
               ObservableList<Methode> combox2 = FXCollections.observableArrayList( methodlist((monitoring.elements.Component) t1) );
               choiceBox2.setItems((ObservableList<Methode>)combox2);
               
            }
        });

        
        EventHandler<ActionEvent> Submit1 = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// type
		String s1 = (String) comboBox.getValue();
				// fonction
		String s2 = (String)		comboBox0.getValue();
				// op arithme
		String s3 = (String)		comboBox1.getValue();
				// nfattribute
		String s4 = (String)		comboBox5.getValue();
		int val =  Integer.parseInt(textField.getText())  ;
		OtherConstraint oc = new OtherConstraint(s1,s2,s4,s3,val);
		data.setOtherConstraint(oc);
		
			
		remplirTable(data);
		
				
				e.consume();
			}
		};  
		EventHandler<ActionEvent> Submit2 = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Component c1 = choiceBox0.getValue();
				Methode  m1 = choiceBox1.getValue();
				String event1 = (String) choiceBox3.getValue();
				String op  =  (String)choiceBox5.getValue();
				Component c2 = choiceBox.getValue();
				Methode  m2 = choiceBox2.getValue();
				String event2 = (String)choiceBox4.getValue();
				String oplogique = (String) choiceBox6.getValue();
				int value = Integer.parseInt(textField1.getText());
				TimedConstraint t = new TimedConstraint(c1,c2,m1,m2,event1,event2,value,op,oplogique);
				data.setTimedConstraint(t);
				remplirTable2(data);
			
				e.consume();
			}
		};
		remplirTable(data);
		remplirTable2(data);
		button.setOnAction(Submit1);
		button1.setOnAction(Submit2);
        Scene scene = new Scene(p, 653,591);
     		this.setScene(scene);

    }
    public ObservableList<Methode> methodlist(Component c){
    	ObservableList<Methode> list =  FXCollections.observableArrayList();
    	for(int i=0 ; i<c.getMethode().size();i++) {
    		list.add(c.getMethode().get(i));
    		
    	}
    	return list;
    	
    	
    	
    }
    public void NFconstraint() {
    	
    	
    	
    	
    }
    public void NFTimedconstraint() {
    	
    	
    	
    }
    private void closeWindow() {
  		this.close();
  	}     
  public void remplirTable(Model data) {
    	
    	ObservableList<String> oc = FXCollections.observableArrayList();
    	
    	 for(int i = 0 ;i< data.getOtherConstraint().size();i++) {
    		 OtherConstraint c = data.getOtherConstraint().get(i);
    		 String str = " "+c.type+" "+c.fonction+" "+c.attribute+" "+c.value;
    		 oc.add(str);
         }
    	 tableView.setItems(data.getOtherConstraint());
        for(int i = 0 ;i< oc.size();i++) {
        	String s= oc.get(i);
        	
        	 tableColumn0.setCellValueFactory(cellData -> cellData.getValue().name);
        }
    	
    }
  public void remplirTable2(Model data) {
  	
  	ObservableList<String> oc = FXCollections.observableArrayList();
  	
  	 for(int i = 0 ;i< data.getOtherConstraint().size();i++) {
  		 OtherConstraint c = data.getOtherConstraint().get(i);
  		 String str = " "+c.type+" "+c.fonction+" "+c.attribute+" "+c.value;
  		 oc.add(str);
      }
  	 tableView0.setItems(data.getTimedConstraint());
      for(int i = 0 ;i< data.getTimedConstraint().size();i++) {
      	 tableColumn2.setCellValueFactory(cellData -> cellData.getValue().name);
      }
  	
  }
}
