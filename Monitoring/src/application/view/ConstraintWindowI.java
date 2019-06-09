package application.view;



import java.lang.*;
import java.util.*;

import application.include.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import monitoring.elements.Component;
import monitoring.elements.Configuration;

public  class ConstraintWindowI extends Stage {
	 protected  AnchorPane p;
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
    protected  TableView tableView;
    protected  TableColumn tableColumn;
    protected  TableColumn tableColumn0;
    protected  Tab tab0;
    protected  AnchorPane anchorPane0;
    protected  Separator separator0;
    protected  TableView tableView0;
    protected  TableColumn tableColumn1;
    protected  TableColumn tableColumn2;
    protected  TextField textField1;
    protected  ChoiceBox comboBox2;
    protected  ChoiceBox<Component> comboBox3;
    protected  Label label0;
    protected  Button button1;
    protected  Button button2;
    protected  Button button3;
    protected  ComboBox<Component> comboBox4;
    protected  ComboBox comboBox5;
    protected  ComboBox comboBox6;
    protected  ComboBox comboBox7;
    protected  ComboBox comboBox8;
    protected  ComboBox comboBox9;
    protected  Tab tab1;
    protected  AnchorPane anchorPane1;
    protected  Separator separator1;

    public ConstraintWindowI(Model data) {
    	 p = new AnchorPane();
        label = new Label();
        tabPane = new TabPane();
        tab = new Tab();
        anchorPane = new AnchorPane();
        
    	ObservableList<String> options = FXCollections.observableArrayList("HARD", "BEST EFFORT","Lite");
   	 comboBox = new ComboBox(options);
   	 ObservableList<String> options2 = FXCollections.observableArrayList("SOMME", "AVG","DIFF","MIN","MAX");
       comboBox0 = new ComboBox(options2);
       ObservableList<String> options3 = FXCollections.observableArrayList("==", "<",">","<=",">=");
       comboBox1 = new ComboBox(options3);
       
      
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
        
        
        
        comboBox2 =  new ChoiceBox<Component>(data.getComponentProperty()); 
        comboBox3 =  new ChoiceBox<Component>(data.getComponentProperty());
        label0 = new Label();
        button1 = new Button();
        button2 = new Button();
        button3 = new Button();
        ObservableList<Component> optionsc = data.getComponentProperty();
        comboBox4 = new ComboBox(optionsc);
        comboBox5 = new ComboBox();
        comboBox6 = new ComboBox();
        comboBox7 = new ComboBox();
        comboBox8 = new ComboBox();
        comboBox9 = new ComboBox();
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

        textField0.setLayoutX(218.0);
        textField0.setLayoutY(66.0);
        textField0.setPrefHeight(25.0);
        textField0.setPrefWidth(165.0);
        textField0.setPromptText("NF ATTRIBUTE");

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

        comboBox2.setLayoutX(162.0);
        comboBox2.setLayoutY(121.0);
        comboBox2.setPrefHeight(25.0);
        comboBox2.setPrefWidth(95.0);
      //  comboBox2.setPromptText("Method2");

        comboBox3.setLayoutX(14.0);
        comboBox3.setLayoutY(121.0);
        comboBox3.setPrefHeight(25.0);
        comboBox3.setPrefWidth(111.0);
      //  comboBox3.setPromptText("Component2");

        label0.setLayoutX(14.0);
        label0.setLayoutY(26.0);
        label0.setPrefHeight(17.0);
        label0.setPrefWidth(582.0);
        label0.setText("Syntax :  ComponentName.[S/F].MethodName Op ComponentName.[S/F].MethodName OPLogique Valeur ");

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

        comboBox4.setLayoutX(14.0);
        comboBox4.setLayoutY(68.0);
        comboBox4.setPrefHeight(25.0);
        comboBox4.setPrefWidth(113.0);
        comboBox4.setPromptText("Component1");

        comboBox5.setLayoutX(161.0);
        comboBox5.setLayoutY(68.0);
        comboBox5.setPrefHeight(25.0);
        comboBox5.setPrefWidth(96.0);
        comboBox5.setPromptText("Method1");

        comboBox6.setLayoutX(267.0);
        comboBox6.setLayoutY(68.0);
        comboBox6.setPrefHeight(25.0);
        comboBox6.setPrefWidth(80.0);
        comboBox6.setPromptText("Event1");

        comboBox7.setLayoutX(267.0);
        comboBox7.setLayoutY(121.0);
        comboBox7.setPrefHeight(25.0);
        comboBox7.setPrefWidth(81.0);
        comboBox7.setPromptText("Event2");

        comboBox8.setLayoutX(359.0);
        comboBox8.setLayoutY(93.0);
        comboBox8.setPrefHeight(25.0);
        comboBox8.setPrefWidth(100.0);
        comboBox8.setPromptText("OP");

        comboBox9.setLayoutX(14.0);
        comboBox9.setLayoutY(166.0);
        comboBox9.setPrefHeight(25.0);
        comboBox9.setPrefWidth(111.0);
        comboBox9.setPromptText("OPLogique");
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
        anchorPane.getChildren().add(textField0);
        anchorPane.getChildren().add(button0);
        anchorPane.getChildren().add(separator);
        tableView.getColumns().add(tableColumn);
        tableView.getColumns().add(tableColumn0);
        anchorPane.getChildren().add(tableView);
        tabPane.getTabs().add(tab);
        anchorPane0.getChildren().add(separator0);
        tableView0.getColumns().add(tableColumn1);
        tableView0.getColumns().add(tableColumn2);
        anchorPane0.getChildren().add(tableView0);
        anchorPane0.getChildren().add(textField1);
        anchorPane0.getChildren().add(comboBox2);
        anchorPane0.getChildren().add(comboBox3);
        anchorPane0.getChildren().add(label0);
        anchorPane0.getChildren().add(button1);
        anchorPane0.getChildren().add(button2);
        anchorPane0.getChildren().add(button3);
        anchorPane0.getChildren().add(comboBox4);
        anchorPane0.getChildren().add(comboBox5);
        anchorPane0.getChildren().add(comboBox6);
        anchorPane0.getChildren().add(comboBox7);
        anchorPane0.getChildren().add(comboBox8);
        anchorPane0.getChildren().add(comboBox9);
        tabPane.getTabs().add(tab0);
        tabPane.getTabs().add(tab1);
        p.getChildren().add(tabPane);
        p.getChildren().add(separator1);
        
        Scene scene = new Scene(p, 670,460);
     		this.setScene(scene);


    }
}

