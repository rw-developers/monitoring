package application.view;



import java.lang.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import monitoring.elements.ComponentImplementation;
import monitoring.elements.Connector;
import monitoring.elements.Csp;
import monitoring.elements.Port;
import application.include.Alert;
import application.include.Model;

public  class NewConnectorWindow extends Stage {

    protected final Label label;
    protected final Label label0;
    protected final Label label1;
    protected final TextField textField;
    protected final TextField textField0;
    protected final Separator separator;
    protected final TextField textField1;
    protected final Label label2;
    protected final Label label3;
    protected final Label label4;
    protected final TextField textField2;
    protected final TextField textField3;
    protected final Separator separator0;
    protected final Button button;
    protected final Button button0;
    protected final AnchorPane p ;
    
    
    public static Port port;
    public static Port firsPort, secondPort;
    public static Connector arc = new Connector();;
    public static ComponentImplementation instance;
    String port1, port2,instance1,instance2;

	private int srcIn;
	private int destIn;
	
	
	ObservableList<String> options = FXCollections.observableArrayList("Binding","Connector");
	private ComboBox<String> newLinkArrow = new ComboBox<String>(options);

    public NewConnectorWindow(int editIndex, Model data,ProgramWindow window ,Port s1,Port s2) {
        p = new AnchorPane();
        label = new Label();
        label0 = new Label();
        label1 = new Label();
        textField = new TextField();
        textField0 = new TextField();
        separator = new Separator();
        textField1 = new TextField();
        label2 = new Label();
        label3 = new Label();
        label4 = new Label();
        textField2 = new TextField();
        textField3 = new TextField();
        separator0 = new Separator();
        button = new Button();
        button0 = new Button();

      firsPort = s1;
      secondPort = s2 ;
        textField2.setText(data.getPortModel(s1.getIndex()).getCspExpression().getExpression());
		textField3.setText(data.getPortModel(s2.getIndex()).getCspExpression().getExpression());
		
		getexpcsp(data,s1,s2);
        p.setId("AnchorPane");
        p.setPrefHeight(344.0);
        p.setPrefWidth(600.0);

        label.setLayoutX(212.0);
        label.setLayoutY(24.0);
        label.setText("Connector CSP Expression");

        label0.setLayoutX(14.0);
        label0.setLayoutY(77.0);
        label0.setText("Name :");

        label1.setLayoutX(184.0);
        label1.setLayoutY(77.0);
        label1.setText("CSP Expression :");

        textField.setLayoutX(14.0);
        textField.setLayoutY(96.0);
        textField.setPrefHeight(34.0);
        textField.setPrefWidth(149.0);

        textField0.setLayoutX(184.0);
        textField0.setLayoutY(96.0);
        textField0.setPrefHeight(34.0);
        textField0.setPrefWidth(374.0);

        separator.setLayoutX(8.0);
        separator.setLayoutY(151.0);
        separator.setPrefHeight(7.0);
        separator.setPrefWidth(589.0);

        textField1.setLayoutX(138.0);
        textField1.setLayoutY(295.0);

        label2.setLayoutX(52.0);
        label2.setLayoutY(168.0);
        label2.setText("First Port");

        label3.setLayoutX(479.0);
        label3.setLayoutY(168.0);
        label3.setText("Second Port");

        label4.setLayoutX(24.0);
        label4.setLayoutY(299.0);
        label4.setText("Bandwidth :");

        textField2.setLayoutX(14.0);
        textField2.setLayoutY(200.0);

        textField3.setLayoutX(437.0);
        textField3.setLayoutY(200.0);

        separator0.setLayoutX(4.0);
        separator0.setLayoutY(258.0);
        separator0.setPrefHeight(7.0);
        separator0.setPrefWidth(589.0);

        button.setLayoutX(479.0);
        button.setLayoutY(295.0);
        button.setMnemonicParsing(false);
        button.setPrefHeight(25.0);
        button.setPrefWidth(92.0);
        button.setText("Submit");

        button0.setLayoutX(355.0);
        button0.setLayoutY(295.0);
        button0.setMnemonicParsing(false);
        button0.setPrefHeight(25.0);
        button0.setPrefWidth(92.0);
        button0.setText("Delete");

        p.getChildren().add(label);
        p.getChildren().add(label0);
        p.getChildren().add(label1);
        p.getChildren().add(textField);
        p.getChildren().add(textField0);
        p.getChildren().add(separator);
        p.getChildren().add(textField1);
        p.getChildren().add(label2);
        p.getChildren().add(label3);
        p.getChildren().add(label4);
        p.getChildren().add(textField2);
        p.getChildren().add(textField3);
        p.getChildren().add(separator0);
        p.getChildren().add(button);
        p.getChildren().add(button0);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        EventHandler<ActionEvent> submitLinkEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.saveUndoState();
				data.clearRedoState();
				/*
				if(editIndex == -1)
				{

					try {

						int confId = 0;
						for (int i = 1; i < window.appPanel.getTabs().size(); i++) {
							if (window.appPanel.getTabs().get(i).isSelected()) {
								confId = i - 1;
							}
						}
						if (srcIn <= data.maxLink() && srcIn >= 0 && destIn <= data.maxLink() && destIn >= 0) {
							data.addLinkModel(
									new int[] { data.getLinkTail(), newLinkArrow.getSelectionModel().getSelectedIndex(),
											srcIn, destIn, -2, -2, -2, -2
											},
									"",data.getConfigurationModel(confId));
						}
					} catch (NumberFormatException ex) {
					}
				} else {
					data.getLinkModel(editIndex).setType(newLinkArrow.getSelectionModel().getSelectedIndex());
				
				}*/				
				//closeWindow();
				e.consume();
			}
		};
		
        
        
        
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        EventHandler<ActionEvent> deleteLinkEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.saveUndoState();
				data.clearRedoState();
				data.removeLinkModel(editIndex);
				//closeWindow();
				e.consume();
			}
		};
		
		
		
		 EventHandler<ActionEvent> ValidateCSPEvent = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					
					      port1 = firsPort.getName();
					 
				      instance1=firsPort.instanceParent;
				        port2 = secondPort.getName();
				      instance2=secondPort.instanceParent;	
				      arc.formule = null;
						
						if(editIndex == -1)
						{
//Alert.display("", textField2.getText());
							try {

								int confId = 0;
								for (int i = 1; i < window.appPanel.getTabs().size(); i++) {
									if (window.appPanel.getTabs().get(i).isSelected()) {
										confId = i - 1;
									}
								}
								if (srcIn <= data.maxLink() && srcIn >= 0 && destIn <= data.maxLink() && destIn >=  0 && checkCsp()) {
									data.addLinkModel(
											new int[] { data.getLinkTail(), newLinkArrow.getSelectionModel().getSelectedIndex(),
													srcIn, destIn, -2, -2, -2, -2
													},
											"",data.getConfigurationModel(confId),arc.formule,Integer.parseInt(textField1.getText()),s2,s1);
								}
							} catch (NumberFormatException ex) {
								Alert.display("", "bfdbfd");
							}
						} else {
							data.getLinkModel(editIndex).setType(newLinkArrow.getSelectionModel().getSelectedIndex());
						
						}
					
					//closeWindow();
					e.consume();
				}
			};
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		button.setOnAction(ValidateCSPEvent);
		button0.setOnAction(deleteLinkEvent);
		
		Scene scene = new Scene(p, 600,344);
		this.setScene(scene);

    }
    
    
    public boolean checkCsp() {
        String cspName = textField.getText();
        String cspFormule = textField0.getText();
        Pattern pName = Pattern.compile("[a-zA-Z]+[0-9]*");
        Matcher mName = pName.matcher(cspName);
        Pattern pExpOut = Pattern.compile("([a-zA-Z]+[0-9]*)![a-zA-Z]+[0-9]*((?:[-]>[a-zA-Z]+[0-9]*(?:[?!][a-zA-Z]+[0-9]*))+)(?:[-]>[a-zA-Z]+[0-9]*)?");
        Matcher cspExpressionOutMatcher1 = pExpOut.matcher(cspFormule);
        if (!mName.matches() && (!cspExpressionOutMatcher1.matches())) {
     
           Alert.display("","your CSP formula name and your CSP expression are invalid" );
           return false;
          
        } else if (!mName.matches()) {
        	  Alert.display("","your CSP formula name is invalid" );
        	  return false;
        } else if (!cspExpressionOutMatcher1.matches()) {
        	  Alert.display("","your CSP expression are invalid" );
        	  return false;
        } else if (cspExpressionOutMatcher1.matches() && (mName.matches())) {
            String firstPartExp = cspExpressionOutMatcher1.group(1);
            String secondPartExp = cspExpressionOutMatcher1.group(2);
            ArrayList<String> portsInCspExpression = new ArrayList<>();
            ArrayList<String> newPortsInCspExpression = new ArrayList<>();
            Pattern firstGroupPortNamePattern1 = Pattern.compile("([a-zA-Z]+[0-9]*)");
            Matcher firstGroupPortNameMatcher = firstGroupPortNamePattern1.matcher(firstPartExp);
            //first group
            if (firstGroupPortNameMatcher.matches()) {
                portsInCspExpression.add(firstPartExp);

            }

            //second group
            Pattern secondGroupPortNamePattern = Pattern.compile("([a-zA-Z]+[0-9]*)(?=[!?])");
            Matcher secondGroupPortNameMatcher = secondGroupPortNamePattern.matcher(secondPartExp);
            while (secondGroupPortNameMatcher.find()) {
                portsInCspExpression.add(secondGroupPortNameMatcher.group(1));
                newPortsInCspExpression.add("_"+secondGroupPortNameMatcher.group(1));
            }
            //instance.getName()+
            System.out.println(port1+" "+port2);
            System.out.println("yo je ss la liste des ports"+portsInCspExpression);
            int l;
            //for (i=0; (i < portsInCspExpression.size()); i++) {
            for ( l = 0; l <portsInCspExpression.size()&&(portsInCspExpression.get(l).equals(port1)||portsInCspExpression.get(l).equals(port2)) ; l++);
            if(l <portsInCspExpression.size()){
            	  Alert.display("","Please check your ports name" );
            	  return false;
            }
            else  {
                int index1 = portsInCspExpression.indexOf(port1);
                int index2 = portsInCspExpression.indexOf(port2);
                System.out.println("index" + index1 + "" + index2);
                Alert.display("","Valid CSP expression" );
              
                cspFormule = cspFormule.replaceAll(portsInCspExpression.get(index1), instance1 + "_" + port1);
                cspFormule = cspFormule.replaceAll(portsInCspExpression.get(index2), instance2 + "_" + port2);

                arc.formule = new Csp(cspName, cspFormule);
                return true;
            }

        }
		return false;
    }
    
	private void closeWindow() {
		this.close();
	}
	
	public void setSrc(int s) {
		srcIn = s;
	}
	
	public void setDest(int d) {
		destIn = d;
	}
	

	public void getexpcsp(Model data , Port s1 , Port s2) {
String cspname = "Bind"+s1.getIndex()+""+s2.getIndex();
textField.setText(cspname);
String p1 = data.getPortModel(s1.getIndex()).getCspExpression().getExpression().split("-")[0];
String p2 = data.getPortModel(s2.getIndex()).getCspExpression().getExpression().split("-")[0];
String t = p1+"->"+p2+"->"+cspname;
textField0.setText(t);


}
}
