package nfattribute;

import java.util.ArrayList;

import application.include.Alert;
import application.include.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import monitoring.elements.Configuration;


public class VerificationNF {
	private ObservableList<OtherConstraint> OtherConstraint =FXCollections.observableArrayList();
	private ObservableList<TimedConstraint> TimedConstraint  = FXCollections.observableArrayList();
	
	
	public VerificationNF(Model data) {
		super();
		OtherConstraint = data.getOtherConstraint();
		TimedConstraint = data.getTimedConstraint();
		
	}
	
	public void CheckOtherConstraint(Configuration c) {
		
		int type =0;
		int fonction;
		int value;
		String OP ;
		int Attribut =0 ;
		for(int i=0 ; i< OtherConstraint.size();i++) {
			if(OtherConstraint.get(i).type.compareTo("HARD")== 0) {type = 1;}
			if(OtherConstraint.get(i).type.compareTo("BEST EFFORT")== 0) {type = 2;}
			if(OtherConstraint.get(i).type.compareTo("Lite")== 0) {type =3;}
			value = OtherConstraint.get(i).value;
			if(OtherConstraint.get(i).attribute.compareTo("MemoryComsuption")== 0) {Attribut =1;}
			if(OtherConstraint.get(i).attribute.compareTo("BandWidth")== 0) {Attribut = 2;}
			
			if(OtherConstraint.get(i).fonction.compareTo("SOMME")== 0) {
				boolean result = somme(Attribut,c,value,OtherConstraint.get(i).op);
				if(result) {Alert.display("VALID",OtherConstraint.get(i).name.getValue());}else {Alert.display("INVALIDE",OtherConstraint.get(i).name.getValue());}
				}
			
			
			if(OtherConstraint.get(i).fonction.compareTo("MIN")== 0) {
				boolean result = min(Attribut,c,value,OtherConstraint.get(i).op);
				if(result) {Alert.display("VALID",OtherConstraint.get(i).name.getValue());}else {Alert.display("INVALIDE",OtherConstraint.get(i).name.getValue());}
				
			}
			if(OtherConstraint.get(i).fonction.compareTo("MAX")== 0) {
				boolean result = max(Attribut,c,value,OtherConstraint.get(i).op);
				if(result) {Alert.display("VALID",OtherConstraint.get(i).name.getValue());}else {Alert.display("INVALIDE",OtherConstraint.get(i).name.getValue());}
				
				
			}
			if(OtherConstraint.get(i).fonction.compareTo("AVG")== 0) {
				boolean result = AVG(Attribut,c,value,OtherConstraint.get(i).op);
				if(result) {Alert.display("VALID",OtherConstraint.get(i).name.getValue());}else {Alert.display("INVALIDE",OtherConstraint.get(i).name.getValue());}
				
				
			}
			
			
		
			
			
			
			
			
		}
		
		
		
		
	}
	public boolean  max(int att, Configuration c,int val ,String op) {
		int somme =0;
		int v =0;
		if(att == 1) {
		for(int i =0; i<c.getImplementations().size();i++) {
			v = c.getImplementations().get(i).getComponentType().Memory;
			
		   if(v <= val)return true ;else return false ;
			
			
		}
		
		}else {
			
			for(int i =0; i<c.connectors.size();i++) {
				v= c.connectors.get(i).Bandwidth;
		if(v <= val)return true ;else return false ;
			
			}
			
			
		}
			
			return false;
	}
	public boolean  min(int att, Configuration c,int val ,String op) {
		int somme =0;
		int v =0;
		if(att == 1) {
		for(int i =0; i<c.getImplementations().size();i++) {
			v = c.getImplementations().get(i).getComponentType().Memory;
			
		   if(v >= val)return true ;else return false ;
			
			
		}
		
		}else {
			
			for(int i =0; i<c.connectors.size();i++) {
				v= c.connectors.get(i).Bandwidth;
		if(v >= val)return true ;else return false ;
			
			}
			
			
		}
			
			return false;
	}
	
	public boolean  somme(int att, Configuration c,int val ,String op) {
		int somme =0;	
		if(att == 1) {
		for(int i =0; i<c.getImplementations().size();i++) {
			somme += c.getImplementations().get(i).getComponentType().Memory;
		}
		
		}else {
			for(int i =0; i<c.connectors.size();i++) {
				somme += c.connectors.get(i).Bandwidth;
			}
		}
			if(op.compareTo("==")== 0) { 
				if(somme == val)return true ;else return false ;
			}
			if(op.compareTo("<=")== 0) { 
				if(somme <= val)return true ;else return false ;
			}
			if(op.compareTo("<")== 0) { 
				if(somme < val)return true ;else return false ;
			}
			if(op.compareTo(">")== 0) { 
				if(somme > val)return true ;else return false ;
			}
			if(op.compareTo(">=")== 0) { 
				if(somme >= val)return true ;else return false ;
			}
			return false;
			
		
		
		
		
		
	}
	public boolean  AVG(int att, Configuration c,int val ,String op) {
		int somme =0;
		int avg =0;
		if(att == 1) {
		for(int i =0; i<c.getImplementations().size();i++) {
			somme += c.getImplementations().get(i).getComponentType().Memory;
		}
		avg = somme/ c.getImplementations().size();
		
		}else {
			for(int i =0; i<c.connectors.size();i++) {
				somme += c.connectors.get(i).Bandwidth;
			}
			avg = somme/ c.connectors.size();
		}
			if(op.compareTo("==")== 0) { 
				if(avg == val)return true ;else return false ;
			}
			if(op.compareTo("<=")== 0) { 
				if(avg <= val)return true ;else return false ;
			}
			if(op.compareTo("<")== 0) { 
				if(avg < val)return true ;else return false ;
			}
			if(op.compareTo(">")== 0) { 
				if(avg > val)return true ;else return false ;
			}
			if(op.compareTo(">=")== 0) { 
				if(avg >= val)return true ;else return false ;
			}
			return false;
			
		
		
		
		
		
	}
	//public boolean  avg() {}
	

}
