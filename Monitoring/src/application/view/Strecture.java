package application.view;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import monitoring.elements.Component;
import monitoring.elements.Methode;

public class Strecture {
	public TextField val = new TextField() ;
  public   Label memory = new Label();
  public   Label c = new Label();
  public  Label f = new Label();
  public Component comp ;
    
  
  public TextField val2 = new TextField() ;
  public   Label d = new Label();
  public   Label m = new Label();
  public  Label f2 = new Label();
  public Methode method  ; 
  public Component comp2;
public Strecture(TextField val2, Label d, Label m, Label f2, Methode method,Component comp2) {
	super();
	this.val2 = val2;
	this.d = d;
	this.m = m;
	this.f2 = f2;
	this.method = method;
	this.comp2 = comp2;
}

public Strecture(Component comp ,TextField val, Label memory, Label c, Label f) {
	super();
	this.val = val;
	this.memory = memory;
	this.c = c;
	this.f = f;
	this.comp = comp;
}

}
