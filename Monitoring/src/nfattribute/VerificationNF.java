package nfattribute;

import java.util.ArrayList;
import java.util.Iterator;

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
	
	public ArrayList<String> CheckOtherConstraint(Configuration c) {
		ArrayList<String> resultat = new ArrayList<String>();
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
				if(result) {
				resultat.add("VALID CONSTRAINT    : "+OtherConstraint.get(i).name.getValue());
				}else {
				resultat.add("INVALID CONSTRAINT  : "+OtherConstraint.get(i).name.getValue());
				}
				}
			
			
			if(OtherConstraint.get(i).fonction.compareTo("MIN")== 0) {
				boolean result = min(Attribut,c,value,OtherConstraint.get(i).op);
				if(result) {
				resultat.add("VALID CONSTRAINT    : "+OtherConstraint.get(i).name.getValue());
				}else {
				resultat.add("INVALID CONSTRAINT  : "+OtherConstraint.get(i).name.getValue());
				}
				
			}
			if(OtherConstraint.get(i).fonction.compareTo("MAX")== 0) {
				boolean result = max(Attribut,c,value,OtherConstraint.get(i).op);
				if(result) {
				resultat.add("VALID CONSTRAINT    : "+OtherConstraint.get(i).name.getValue());
				
				}else {
				resultat.add("INVALID CONSTRAINT  : "+OtherConstraint.get(i).name.getValue());
				}
				
				
			}
			if(OtherConstraint.get(i).fonction.compareTo("AVG")== 0) {
				boolean result = AVG(Attribut,c,value,OtherConstraint.get(i).op);
				if(result) {
				resultat.add("VALID CONSTRAINT    : "+OtherConstraint.get(i).name.getValue());
				
				}else {
				resultat.add("INVALID CONSTRAINT  : "+OtherConstraint.get(i).name.getValue());
				}
				
				
			}
			
			
		
			
			
			
			
			
		}
		
		
		
		return resultat;
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
///////////////////////////////////////////////////////////////////////////

//	public void SuperAlgo(ObservableList<TimedConstraint> constraints , Configuration c , Automata A) {
//		ArrayList<Sequence> seqs = GenerateALLsequences( A );
//		ArrayList<String> result = new ArrayList<String>();
//		for(int i =0 ;i<constraints.size();i++) {
//			for(int j=0 ; j<seqs.size();j++) {
//				result.add(CheckConstraint(constraints.get(i) , seqs.get(j)));
//				
//			}
//		}
//		
//		
//		
//	}

	
	
	public  void parcours_profondeur(Node n,String s,ArrayList<String> k) {
		 
	  
	    s = s+"+"+n.name;
	    Iterator<Node> list =  n.transitions.iterator();
	    int m =0;
	    while(list.hasNext()){
	    	m++;
	        parcours_profondeur(list.next(),s,k);
	    } 
	   if(m == 0) {
	   k.add(s);}
    }
	
	public  ArrayList<Sequence> getSequences( Automata a ) {
		

		    
		      ArrayList<Sequence> sq2 = new ArrayList<Sequence>();
		      ArrayList<String> sq3 = new ArrayList<String>();
		   
		   
		      parcours_profondeur(a.initialState, "",sq3);
		      sq3.forEach(k->{
		    	   Sequence sss = new Sequence();
		    	  k = k.substring(1);
		    	  String []  f = k.split("\\+");
		    	  for(int i=1;i<f.length;i++) {
		    		   
		    		  sss.Seq.add(f[i]);
		    		 
		    	  } sq2.add(sss);
		      });
		      
		     
			return sq2;
		
    }



	public void afficheAutomata(Node n) {
		System.out.print(n.name + "->");
		for (int i = 0; i < n.transitions.size(); i++) {
			if (n.transitions.get(i).transitions.size() > 0) {
				if (n.transitions.get(i).transitions.size() > 1) {
					System.out.println("\n");
				}
				afficheAutomata(n.transitions.get(i));
			} else {
				System.out.println(n.transitions.get(i).name);
			}
		}
	}

}
