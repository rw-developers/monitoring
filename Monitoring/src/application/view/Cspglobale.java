package application.view;



import java.lang.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.include.Alert;
import application.include.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import monitoring.elements.Csp;
import monitoring.elements.Methode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.include.Model;

import monitoring.elements.Component;
import monitoring.elements.Csp;
import monitoring.elements.Port;
import monitoring.elements.VerificationFDR;






public  class Cspglobale extends Stage {

    protected final Label label;
    protected final Label label0;
    protected final TextField textField;
    protected final TextField textField0;
    protected final Button button;
    protected final Button button0;
    protected final CheckBox checkBox;
    protected final Label label1;
    protected final Separator separator;
    protected final Label label2;
    protected final ScrollPane scrollPane;
    protected final TableView<Methode> tableView;
    protected final TableColumn<Methode,String> tableColumn;
    protected final TableColumn tableColumn0;
    
    protected final AnchorPane p = new AnchorPane() ;
    List<Port> portName=new ArrayList<>();
    ArrayList<String>port=new ArrayList<>();
    ArrayList<String>secondGroupPart=new ArrayList<>();

public  Component componentType;

    public Cspglobale(int editIndex, Model data) {

        label = new Label();
        label0 = new Label();
        textField = new TextField();
        textField0 = new TextField();
        button = new Button();
        button0 = new Button();
        checkBox = new CheckBox();
        label1 = new Label();
        separator = new Separator();
        label2 = new Label();
        scrollPane = new ScrollPane();
        tableView = new TableView();
        tableColumn = new TableColumn();
        tableColumn0 = new TableColumn();

        
        componentType = data.getComponentModel(editIndex) ;
       


        label.setLayoutX(126);
        label.setLayoutY(120);
        label.setMinHeight(16);
        label.setMinWidth(69);

        label0.setLayoutX(152.0);
        label0.setLayoutY(62.0);
        label0.setPrefHeight(17.0);
        label0.setPrefWidth(144.0);
        label0.setText("Expression Globale CSP :");

        textField.setLayoutX(14.0);
        textField.setLayoutY(89.0);
        textField.setPrefHeight(44.0);
        textField.setPrefWidth(118.0);
        textField.setPromptText("csp name");

        textField0.setLayoutX(152.0);
        textField0.setLayoutY(89.0);
        textField0.setPrefHeight(44.0);
        textField0.setPrefWidth(511.0);
        textField0.setPromptText("csp expression");

        button.setLayoutX(104.0);
        button.setLayoutY(180.0);
        button.setMnemonicParsing(false);
        button.setPrefHeight(25.0);
        button.setPrefWidth(163.0);
        button.setText("Verefication syntaxique");

        button0.setLayoutX(367.0);
        button0.setLayoutY(180.0);
        button0.setMnemonicParsing(false);
        button0.setPrefHeight(25.0);
        button0.setPrefWidth(184.0);
        button0.setText("Verefication Comportemental");

        checkBox.setLayoutX(10.0);
        checkBox.setLayoutY(148.0);
        checkBox.setMnemonicParsing(false);
        checkBox.setText("Generation Automatique");

        label1.setLayoutX(14.0);
        label1.setLayoutY(62.0);
        label1.setText("Expression Name : ");

        separator.setLayoutX(10.0);
        separator.setLayoutY(219.0);
        separator.setPrefHeight(17.0);
        separator.setPrefWidth(654.0);

        label2.setLayoutX(14.0);
        label2.setLayoutY(245.0);
        label2.setText("Liste des Methodes :");

        scrollPane.setLayoutX(4.0);
        scrollPane.setLayoutY(262.0);
        scrollPane.setPrefHeight(200.0);
        scrollPane.setPrefWidth(665.0);

        tableView.setPrefHeight(200.0);
        tableView.setPrefWidth(648.0);

        tableColumn.setPrefWidth(75.0);
        tableColumn.setText("Methode");

        tableColumn0.setPrefWidth(75.0);
        tableColumn0.setText("....");
        scrollPane.setContent(tableView);

       
        tableView.getColumns().add(tableColumn);
       // tableView.getColumns().add(tableColumn0);
        p.getChildren().add(label);
        p.getChildren().add(label0);
        p.getChildren().add(textField);
        p.getChildren().add(textField0);
        p.getChildren().add(button);
        p.getChildren().add(button0);
        p.getChildren().add(checkBox);
        p.getChildren().add(label1);
        p.getChildren().add(tableView);
        p.getChildren().add(scrollPane);
        p.getChildren().add(label2);
        p.getChildren().add(separator);
        remplirTable();
        
       //////////////////////////////////////////////////////////////// hendler /////////////////////////////// 
        EventHandler<ActionEvent> comportomental = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				VerificationFDR check = new VerificationFDR();
				check.valideCspComponent(componentType);
				
				closeWindow();
				e.consume();
			}
		};
		EventHandler<ActionEvent> syntaxique = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
		
			insertGcspExp();
			
				//closeWindow();
				e.consume();
			}
		};
        
        
        
        
        button.setOnAction(syntaxique);
        button0.setOnAction(comportomental);
        Scene scene = new Scene(p, 670,460);
		this.setScene(scene);

    }  
    public void insertGcspExp() {
        String gCspName = textField.getText();
        String gCspFormule = textField0.getText();
        Pattern pFormuleName = Pattern.compile("([a-zA-Z]+[0-9]*)");
        Matcher mFormuleName = pFormuleName.matcher(gCspName);
  
String b1 ="([a-zA-Z]+[0-9]*[?!][a-zA-Z]+[0-9]*(?:[-]>[a-zA-Z]+[0-9]*[?!][a-zA-Z]+[0-9]*)*)[-]>(?:[@]+[DF]+[a-zA-Z]+[0-9]*[-][>]*)*\\(";


String b2 = "([a-zA-Z]+[0-9]*)(?:[?!][a-zA-Z]+[0-9]*[-]>(?:[@]+[DF]+[a-zA-Z]+[0-9]*[-][>]*)*[A-Z]+)?(?:[@]+[DF]+[a-zA-Z]+[0-9]*)?((?:(?:\\|~\\||\\[\\]|;|[|]{3})";


String b3 = "[a-zA-Z]+[0-9]*(?:[@]+[DF]+[a-zA-Z]+[0-9]*)*(?:[?!][a-zA-Z]+[0-9]*[-]>(?:[@]+[DF]+[a-zA-Z]+[0-9]*[-][>]*)*[A-Z]+)?)*)\\)";
        
Pattern pm = Pattern.compile(b1+b2+b3);
Matcher macherm = pm.matcher(gCspFormule); 
if((!macherm.matches())&& (!checkBox.isSelected())) {Alert.display("Error","expression invalid csp or method");}else {
componentType.expMethod=getmethodeFormule(gCspFormule);
gCspFormule = getcspFormule(gCspFormule);        
        
        
        
        
      
            portName=componentType.getPorts();
        
 Pattern pExp1 = Pattern.compile("([a-zA-Z]+[0-9]*[?!][a-zA-Z]+[0-9]*(?:[-]>[a-zA-Z]+[0-9]*[?!][a-zA-Z]+[0-9]*)*)[-]>\\(([a-zA-Z]+[0-9]*)(?:[?!][a-zA-Z]+[0-9]*[-]>[A-Z]+)?((?:(?:\\|~\\||\\[\\]|;|[|]{3})[a-zA-Z]+[0-9]*(?:[?!][a-zA-Z]+[0-9]*[-]>[A-Z]+)?)*)\\)");
        Matcher cspExpressionMatcher = pExp1.matcher(gCspFormule);

        if(checkBox.isSelected()){
            int i;
            for ( i = 0; (i <portName.size()&& portName.get(i).cspExpression!=null); i++);
            if(i < portName.size()) Alert.display("","Please enter CSP expressions of the component's port");
            else {componentType.expGlobale=new Csp(" "," ");
            Alert.display("Auto Formula","Csp expression Automatique");
            }
        }
        else {
            if (!mFormuleName.matches() && (!cspExpressionMatcher.matches()))
            	Alert.display("","your CSP formula name and your CSP expression are invalid");
            else if (!mFormuleName.matches()) Alert.display("","your CSP formula name is invalid");
            else if ((!cspExpressionMatcher.matches())) {
               
                Alert.display("","your CSP expression is invalid");
            	
            } else if (cspExpressionMatcher.matches() && (mFormuleName.matches())) {
            	
            	
            	Alert.display("","Valid CSP expression");
            	 componentType.expGlobale = new Csp(gCspName, gCspFormule);
            	
            	
                ArrayList<String> portsInCspExpression = new ArrayList<>();
                for (int l = 0; l < portName.size(); l++) {
                    port.add(portName.get(l).name.get());
                   // Alert.display("",portName.get(l).name.get() );
                }
                
                String firstPartExp1 = cspExpressionMatcher.group(1);
                Pattern secondGroupPortNamePattern1 = Pattern.compile("([a-zA-Z]+[0-9]*)(?=[?!])");
                Matcher secondGroupPortNameMatcher1 = secondGroupPortNamePattern1.matcher(firstPartExp1);
                while(secondGroupPortNameMatcher1.find()){
                    secondGroupPart.add(secondGroupPortNameMatcher1.group(1));
                   // Alert.display("",secondGroupPortNameMatcher1.group(1) );
                }
                int i;
                for (i=0; (i < secondGroupPart.size()); i++) {
                    if(port.indexOf(secondGroupPart.get(i))==-1) break;
                }

                if (i == secondGroupPart.size()) {
                    String firstPartExp = cspExpressionMatcher.group(2);
                    String secondPartExp = cspExpressionMatcher.group(3);

                    Pattern firstGroupPortNamePattern1 = Pattern.compile("([a-zA-Z]+[0-9]*)(?=\\|~\\||\\[\\]|;|[|]{3})");
                    //first group
                    if (firstPartExp != null) portsInCspExpression.add(firstPartExp);
                    System.out.println("je suis group 1" + portsInCspExpression);

                    //second group
                    Pattern secondGroupPortNamePattern = Pattern.compile("(?:\\|~\\||\\[\\]|;|[|]{3})([a-zA-Z]+[0-9]*)(?=\\|~\\||\\[\\]|;|[|]{3}|$)");
                    Matcher secondGroupPortNameMatcher = secondGroupPortNamePattern.matcher(secondPartExp);
                    while (secondGroupPortNameMatcher.find()) {
                        portsInCspExpression.add(secondGroupPortNameMatcher.group(1));
                    }
                    System.out.println(portsInCspExpression);
                    ArrayList<String> newSecondGroupPart = new ArrayList<>();
                    for (int m = 0; m< portsInCspExpression.size(); m++) {
                        newSecondGroupPart.add(componentType.getName() + "_" + portsInCspExpression.get(m));
                    }
                    System.out.println("yoo" + newSecondGroupPart);



                    int k;
                    for (k = 0; (k < portsInCspExpression.size()); k++) {
                        if (port.indexOf(portsInCspExpression.get(k)) == -1) break;
                    }

                    if (k == portsInCspExpression.size()) {
                       
                    	Alert.display("","Valid CSP expression");
                        for (int j = 0; j < portsInCspExpression.size(); j++) {
                            gCspFormule = gCspFormule.replaceAll(portsInCspExpression.get(j), newSecondGroupPart.get(j));
                        }
                        System.out.println(gCspFormule);
                        componentType.expGlobale = new Csp(gCspName, gCspFormule);
                    } else {
                      
                    	Alert.display("","Please check your ports name");
                    }
                }else{
                
                	Alert.display("","Please check your ports name");
                }
                
            }
        }
}
    }
    
    private void closeWindow() {
		this.close();
	}
    public void remplirTable() {
    	
    	ObservableList<Methode> Methodes = FXCollections.observableArrayList();
    	 for(int i = 0 ;i< componentType.getMethode().size();i++) {
    		 
    		 Methodes.add(componentType.getMethode().get(i));
        }
    	 tableView.setItems(Methodes);
        for(int i = 0 ;i< componentType.getMethode().size();i++) {
        	 tableColumn.setCellValueFactory(cellData -> cellData.getValue().getMethodeName());
        }
    	
    }
    public String getcspFormule(String s) {
   	 String csp = s.replaceAll("(?:[@]+[DF]+[a-zA-Z]+[0-9]*[-][>]*)*", "");
   	 csp = csp.replaceAll("(?:[@]+[DF]+[a-zA-Z]*)*", "");
   	 return csp;
    }
    public String getmethodeFormule(String s) {
   	 String methode = s.replaceAll("([a-zA-Z]+[0-9]*[?!][a-zA-Z]+[0-9]*(?:[-]>[a-zA-Z]+[0-9]*[?!][a-zA-Z]+[0-9]*)*)[-]>", "");
   	        methode = methode.replaceAll("([a-zA-Z]+[0-9]*[@])", "");
   	        methode = methode.replaceAll("@", "");
   	        return methode;
    }
    

}
