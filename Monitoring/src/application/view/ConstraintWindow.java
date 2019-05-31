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

public  class ConstraintWindow extends Stage {

    protected final AnchorPane p;
    protected final Label label;
    protected final TabPane tabPane;
    protected final Tab tab;
    protected final AnchorPane anchorPane;
    protected final ComboBox comboBox;
    protected final ComboBox comboBox0;
    protected final ComboBox comboBox1;
    protected final Button button;
    protected final TextField textField;
    protected final TextField textField0;
    protected final Button button0;
    protected final Separator separator;
    protected final TableView tableView;
    protected final TableColumn tableColumn;
    protected final TableColumn tableColumn0;
    protected final Tab tab0;
    protected final AnchorPane anchorPane0;
    protected final Separator separator0;
    protected final TableView tableView0;
    protected final TableColumn tableColumn1;
    protected final TableColumn tableColumn2;
    protected final TextField textField1;
    protected final ComboBox comboBox2;
    protected final ComboBox comboBox3;
    protected final TextField textField2;
    protected final TextField textField3;
    protected final Label label0;
    protected final Button button1;
    protected final Button button2;
    protected final Button button3;
    protected final Tab tab1;
    protected final AnchorPane anchorPane1;
    protected final Separator separator1;

    public ConstraintWindow(int index , Model data) {
         p = new AnchorPane();
        label = new Label();
        tabPane = new TabPane();
        tab = new Tab();
        anchorPane = new AnchorPane();
        
    	ObservableList<String> options = FXCollections.observableArrayList("HARD", "BEST EFFORT","Light");
    	 comboBox = new ComboBox(options);
    	 ObservableList<String> options2 = FXCollections.observableArrayList("SOMME", "AVG","DIFF");
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
        ObservableList<String> options4 = FXCollections.observableArrayList("+", "-","*");
        comboBox2 = new ComboBox(options3);
        
        comboBox3 = new ComboBox(options4);
        textField2 = new TextField();
        textField3 = new TextField();
        label0 = new Label();
        button1 = new Button();
        button2 = new Button();
        button3 = new Button();
        tab1 = new Tab();
        anchorPane1 = new AnchorPane();
        separator1 = new Separator();

        p.setId("AnchorPane");
        p.setPrefHeight(552.0);
        p.setPrefWidth(653.0);

        label.setLayoutX(238.0);
        label.setLayoutY(14.0);
        label.setText("Non-Functional Constraint");

        tabPane.setLayoutX(3.0);
        tabPane.setLayoutY(61.0);
        tabPane.setPrefHeight(483.0);
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
        separator0.setLayoutY(169.0);
        separator0.setPrefHeight(6.0);
        separator0.setPrefWidth(639.0);

        tableView0.setLayoutX(6.0);
        tableView0.setLayoutY(190.0);
        tableView0.setPrefHeight(258.0);
        tableView0.setPrefWidth(635.0);

        tableColumn1.setPrefWidth(50.0);
        tableColumn1.setText("ID");

        tableColumn2.setPrefWidth(580.0);
        tableColumn2.setText("Constraint");

        textField1.setLayoutX(557.0);
        textField1.setLayoutY(68.0);
        textField1.setPrefHeight(25.0);
        textField1.setPrefWidth(75.0);

        comboBox2.setLayoutX(486.0);
        comboBox2.setLayoutY(68.0);
        comboBox2.setPrefHeight(25.0);
        comboBox2.setPrefWidth(68.0);
        comboBox2.setPromptText("OpL");

        comboBox3.setLayoutX(192.0);
        comboBox3.setLayoutY(68.0);
        comboBox3.setPrefHeight(25.0);
        comboBox3.setPrefWidth(88.0);
        comboBox3.setPromptText("OpA");

        textField2.setLayoutX(289.0);
        textField2.setLayoutY(68.0);
        textField2.setPrefHeight(25.0);
        textField2.setPrefWidth(184.0);

        textField3.setLayoutX(14.0);
        textField3.setLayoutY(68.0);
        textField3.setPrefHeight(25.0);
        textField3.setPrefWidth(170.0);

        label0.setLayoutX(14.0);
        label0.setLayoutY(26.0);
        label0.setPrefHeight(17.0);
        label0.setPrefWidth(582.0);
        label0.setText("Syntax :  ComponentName.[S/F].MethodName Op ComponentName.[S/F].MethodName OPLogique Valeur ");

        button1.setLayoutX(570.0);
        button1.setLayoutY(134.0);
        button1.setMnemonicParsing(false);
        button1.setText("Submit");

        button2.setLayoutX(505.0);
        button2.setLayoutY(134.0);
        button2.setMnemonicParsing(false);
        button2.setText("Exemple");

        button3.setLayoutX(14.0);
        button3.setLayoutY(109.0);
        button3.setMnemonicParsing(false);
        button3.setText("Check Connector");
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
        anchorPane0.getChildren().add(textField2);
        anchorPane0.getChildren().add(textField3);
        anchorPane0.getChildren().add(label0);
        anchorPane0.getChildren().add(button1);
        anchorPane0.getChildren().add(button2);
        anchorPane0.getChildren().add(button3);
        tabPane.getTabs().add(tab0);
       // tabPane.getTabs().add(tab1);
        p.getChildren().add(tabPane);
        p.getChildren().add(separator1);
        
        
        
        
        
        Scene scene = new Scene(p, 670,460);
		this.setScene(scene);

    }
}

