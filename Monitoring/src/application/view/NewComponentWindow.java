package application.view;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.include.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import monitoring.elements.Component;
import monitoring.elements.Csp;
import monitoring.elements.Port;

public class NewComponentWindow extends Stage {

	// Dialog box elements
	private GridPane newComponentInterface = new GridPane();
	private Text newComponentTitle = new Text();
	private TextField newComponentName = new TextField();
	public Button newComponentSubmit = new Button("Submit");
	public Button deleteComponent = new Button("Delete");
/////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	 ArrayList<Port>portName=new ArrayList<>();
	    ArrayList<String>port=new ArrayList<>();
	    ArrayList<String>secondGroupPart=new ArrayList<>();
	
	public static Component componentType;
    TextField cspGExp = new TextField();
  
    TextField cspGName = new TextField();
   
    Label errorGExp = new Label();
   
    Button insertGExp = new Button("Insert Global CSP");
    
    public Button checksyntaxique = new Button("Verification syntaxique");
    public Button checkFDR = new Button("verification Comportemental");
   
    CheckBox generateCspAuto;

	public NewComponentWindow(int editIndex, Model data) {
		// Set window title
		newComponentTitle.setText((editIndex == -1) ? "Create Component Block" : "Edit Component Block");

		// Attach elements to window
		if (editIndex == -1) {
			newComponentName.setPromptText("Component name...");
			newComponentInterface.add(newComponentName, 0, 1, 2, 1);

		} else {
			cspGExp.setPromptText("CSP Exprission");
			cspGName.setPromptText("CSP NAME");
			newComponentName.setText(data.getComponentModel(editIndex).getName());
			//Component c = data.getComponentModel(editIndex);
			newComponentInterface.add(cspGName, 0, 2, 2, 1);
			newComponentInterface.add(cspGExp,  1, 2, 2, 1);
			newComponentInterface.setGridLinesVisible(true);
			newComponentInterface.setPrefHeight(200);
			newComponentInterface.setMaxHeight(300);
			
			newComponentInterface.add(checksyntaxique, 0,6);
			newComponentInterface.add(checkFDR, 1,6);
		}

		// Place elements on stage
		newComponentInterface.add(newComponentTitle, 0, 0, 2, 1);
		
		newComponentInterface.add(newComponentSubmit, 1, 7);

		// Check for new class
		if (editIndex != -1) {
			
			
			
			
			newComponentInterface.add(deleteComponent, 0, 7);
		}

		// Handler to submit the selected class
		EventHandler<ActionEvent> submitComponentEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.saveUndoState();
				data.clearRedoState();

				if (editIndex == -1) {
					data.addComponentModel(new int[] { data.getComponentTail(), 0, 0, 100, 100 },
							newComponentName.getText());
				} else {
					data.getComponentModel(editIndex).setName(newComponentName.getText());
				}
				closeWindow();
				e.consume();
			}
		};

		// Handler to delete the selected class
		EventHandler<ActionEvent> deleteComponentEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.removeComponentModel(editIndex);
				closeWindow();
				e.consume();
			}
		};

		// Attach handlers to buttons
		newComponentSubmit.setOnAction(submitComponentEvent);
		deleteComponent.setOnAction(deleteComponentEvent);

		// Display scene
		
		Scene scene = new Scene(newComponentInterface, 300, 230);
		this.setScene(scene);

	}

	private void closeWindow() {
		this.close();
	}
	
	 public void insertGcspExp(ActionEvent actionEvent) {
	        String gCspName = cspGName.getText();
	        String gCspFormule = cspGExp.getText();
	        Pattern pFormuleName = Pattern.compile("([a-zA-Z]+[0-9]*)");
	        Matcher mFormuleName = pFormuleName.matcher(gCspName);

	        Pattern pExp1 = Pattern.compile("([a-zA-Z]+[0-9]*[?!][a-zA-Z]+[0-9]*(?:[-]>[a-zA-Z]+[0-9]*[?!][a-zA-Z]+[0-9]*)*)[-]>\\(([a-zA-Z]+[0-9]*)(?:[?!][a-zA-Z]+[0-9]*[-]>[A-Z]+)?((?:(?:\\|~\\||\\[\\]|;|[|]{3})[a-zA-Z]+[0-9]*(?:[?!][a-zA-Z]+[0-9]*[-]>[A-Z]+)?)*)\\)");
	        Matcher cspExpressionMatcher = pExp1.matcher(gCspFormule);

	        if(generateCspAuto.isSelected()){
	            int i;
	            for ( i = 0; (i <portName.size()&& portName.get(i).cspExpression!=null); i++);
	            if(i < portName.size()) errorGExp.setText("Please enter CSP expressions of the component's port");
	            else componentType.expGlobale=new Csp(" "," ");
	        }
	        else {
	            if (!mFormuleName.matches() && (!cspExpressionMatcher.matches()))
	                errorGExp.setText("your CSP formula name and your CSP expression are invalid");
	            else if (!mFormuleName.matches()) errorGExp.setText("your CSP formula name is invalid");
	            else if ((!cspExpressionMatcher.matches())) {
	                errorGExp.setTextFill(Color.RED);
	                errorGExp.setText("your CSP expression is invalid");
	            } else if (cspExpressionMatcher.matches() && (mFormuleName.matches())) {
	                ArrayList<String> portsInCspExpression = new ArrayList<>();
	                for (int l = 0; l < portName.size(); l++) {
	                    port.add(portName.get(l).getNom());
	                }
	                String firstPartExp1 = cspExpressionMatcher.group(1);
	                Pattern secondGroupPortNamePattern1 = Pattern.compile("([a-zA-Z]+[0-9]*)(?=[?!])");
	                Matcher secondGroupPortNameMatcher1 = secondGroupPortNamePattern1.matcher(firstPartExp1);
	                while(secondGroupPortNameMatcher1.find()){
	                    secondGroupPart.add(secondGroupPortNameMatcher1.group(1));
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
	                        errorGExp.setTextFill(Color.GREEN);
	                        errorGExp.setText("Valid CSP expression");
	                        for (int j = 0; j < portsInCspExpression.size(); j++) {
	                            gCspFormule = gCspFormule.replaceAll(portsInCspExpression.get(j), newSecondGroupPart.get(j));
	                        }
	                        System.out.println(gCspFormule);
	                        componentType.expGlobale = new Csp(gCspName, gCspFormule);
	                    } else {
	                        errorGExp.setTextFill(Color.RED);
	                        errorGExp.setText("Please check your ports name");
	                    }
	                }else{
	                    errorGExp.setTextFill(Color.RED);
	                    errorGExp.setText("Please check your ports name");
	                }
	            }
	        }
	    }
	
	
	
}
