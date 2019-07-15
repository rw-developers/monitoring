package nfattribute;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import application.include.Alert;
import application.include.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import monitoring.elements.Configuration;
import monitoring.elements.Methode;

public class VerificationNF {
	private ObservableList<OtherConstraint> OtherConstraint = FXCollections.observableArrayList();
	private ObservableList<TimedConstraint> TimedConstraint = FXCollections.observableArrayList();

	public VerificationNF(Model data) {
		super();
		OtherConstraint = data.getOtherConstraint();
		TimedConstraint = data.getTimedConstraint();

	}

	public ArrayList<String> CheckOtherConstraint(Configuration c) {
		ArrayList<String> resultat = new ArrayList<String>();
		int type = 0;
		int fonction;
		int value;
		String OP;
		int Attribut = 0;
		for (int i = 0; i < OtherConstraint.size(); i++) {
			if (OtherConstraint.get(i).type.compareTo("HARD") == 0) {
				type = 1;
			}
			if (OtherConstraint.get(i).type.compareTo("BEST EFFORT") == 0) {
				type = 2;
			}
			if (OtherConstraint.get(i).type.compareTo("Lite") == 0) {
				type = 3;
			}
			value = OtherConstraint.get(i).value;
			if (OtherConstraint.get(i).attribute.compareTo("MemoryComsuption") == 0) {
				Attribut = 1;
			}
			if (OtherConstraint.get(i).attribute.compareTo("BandWidth") == 0) {
				Attribut = 2;
			}

			if (OtherConstraint.get(i).fonction.compareTo("SOMME") == 0) {
				boolean result = somme(Attribut, c, value, OtherConstraint.get(i).op);
				if (result) {
					resultat.add("VALID CONSTRAINT    : " + OtherConstraint.get(i).name.getValue());
				} else {
					resultat.add("INVALID CONSTRAINT  : " + OtherConstraint.get(i).name.getValue());
				}
			}

			if (OtherConstraint.get(i).fonction.compareTo("MIN") == 0) {
				boolean result = min(Attribut, c, value, OtherConstraint.get(i).op);
				if (result) {
					resultat.add("VALID CONSTRAINT    : " + OtherConstraint.get(i).name.getValue());
				} else {
					resultat.add("INVALID CONSTRAINT  : " + OtherConstraint.get(i).name.getValue());
				}

			}
			if (OtherConstraint.get(i).fonction.compareTo("MAX") == 0) {
				boolean result = max(Attribut, c, value, OtherConstraint.get(i).op);
				if (result) {
					resultat.add("VALID CONSTRAINT    : " + OtherConstraint.get(i).name.getValue());

				} else {
					resultat.add("INVALID CONSTRAINT  : " + OtherConstraint.get(i).name.getValue());
				}

			}
			if (OtherConstraint.get(i).fonction.compareTo("AVG") == 0) {
				boolean result = AVG(Attribut, c, value, OtherConstraint.get(i).op);
				if (result) {
					resultat.add("VALID CONSTRAINT    : " + OtherConstraint.get(i).name.getValue());

				} else {
					resultat.add("INVALID CONSTRAINT  : " + OtherConstraint.get(i).name.getValue());
				}

			}

		}

		return resultat;
	}

	public boolean max(int att, Configuration c, int val, String op) {
		int somme = 0;
		int v = 0;
		if (att == 1) {
			for (int i = 0; i < c.getImplementations().size(); i++) {
				v = c.getImplementations().get(i).getComponentType().Memory;

				if (v <= val)
					return true;
				else
					return false;

			}

		} else {

			for (int i = 0; i < c.connectors.size(); i++) {
				v = c.connectors.get(i).Bandwidth;
				if (v <= val)
					return true;
				else
					return false;

			}

		}

		return false;
	}

	public boolean min(int att, Configuration c, int val, String op) {
		int somme = 0;
		int v = 0;
		if (att == 1) {
			for (int i = 0; i < c.getImplementations().size(); i++) {
				v = c.getImplementations().get(i).getComponentType().Memory;

				if (v >= val)
					return true;
				else
					return false;

			}

		} else {

			for (int i = 0; i < c.connectors.size(); i++) {
				v = c.connectors.get(i).Bandwidth;
				if (v >= val)
					return true;
				else
					return false;

			}

		}

		return false;
	}

	public boolean somme(int att, Configuration c, int val, String op) {
		int somme = 0;
		if (att == 1) {
			for (int i = 0; i < c.getImplementations().size(); i++) {
				somme += c.getImplementations().get(i).getComponentType().Memory;
			}

		} else {
			for (int i = 0; i < c.connectors.size(); i++) {
				somme += c.connectors.get(i).Bandwidth;
			}
		}
		if (op.compareTo("==") == 0) {
			if (somme == val)
				return true;
			else
				return false;
		}
		if (op.compareTo("<=") == 0) {
			if (somme <= val)
				return true;
			else
				return false;
		}
		if (op.compareTo("<") == 0) {
			if (somme < val)
				return true;
			else
				return false;
		}
		if (op.compareTo(">") == 0) {
			if (somme > val)
				return true;
			else
				return false;
		}
		if (op.compareTo(">=") == 0) {
			if (somme >= val)
				return true;
			else
				return false;
		}
		return false;

	}

	public boolean AVG(int att, Configuration c, int val, String op) {
		int somme = 0;
		int avg = 0;
		if (att == 1) {
			for (int i = 0; i < c.getImplementations().size(); i++) {
				somme += c.getImplementations().get(i).getComponentType().Memory;
			}
			avg = somme / c.getImplementations().size();

		} else {
			for (int i = 0; i < c.connectors.size(); i++) {
				somme += c.connectors.get(i).Bandwidth;
			}
			avg = somme / c.connectors.size();
		}
		if (op.compareTo("==") == 0) {
			if (avg == val)
				return true;
			else
				return false;
		}
		if (op.compareTo("<=") == 0) {
			if (avg <= val)
				return true;
			else
				return false;
		}
		if (op.compareTo("<") == 0) {
			if (avg < val)
				return true;
			else
				return false;
		}
		if (op.compareTo(">") == 0) {
			if (avg > val)
				return true;
			else
				return false;
		}
		if (op.compareTo(">=") == 0) {
			if (avg >= val)
				return true;
			else
				return false;
		}
		return false;

	}
///////////////////////////////////////////////////////////////////////////

//	public ArrayList<String> check(ObservableList<TimedConstraint> constraints , ArrayList<Sequence> sq) {
//		 
//		
//		
//		
//		
//	}

	public void parcours_profondeur(Node n, String s, ArrayList<String> k) {

		s = s + "+" + n.name;
		Iterator<Node> list = n.transitions.iterator();
		int m = 0;
		while (list.hasNext()) {
			m++;
			parcours_profondeur(list.next(), s, k);
		}
		if (m == 0) {
			k.add(s);
		}

	}

	public ArrayList<Sequence> getSequences(Automata a) {

		ArrayList<Sequence> sq2 = new ArrayList<Sequence>();
		ArrayList<String> sq3 = new ArrayList<String>();

		parcours_profondeur(a.initialState, "", sq3);
		sq3.forEach(k -> {
			Sequence sss = new Sequence();
			k = k.substring(1);
			String[] f = k.split("\\+");
			for (int i = 1; i < f.length; i++) {
				sss.Seq.add(f[i]);
			}
			sq2.add(sss);
		});

		return sq2;

	}

	public ArrayList<Sequence> generateAllSequence(ArrayList<Sequence> sq)   {
		 File f2 = new File("SequenceLOG.txt");try {
		    BufferedWriter bw1 = new BufferedWriter(new FileWriter(f2,true));
		    
		ArrayList<Sequence> sq2 = new ArrayList<Sequence>();
		for (int i = 0; i < sq.size(); i++) {
			for (int j = 0; j < sq.size(); j++) {
				if (i != j) {
					ArrayList<String> tmp = sq.get(i).Seq;
					Sequence cpt = new Sequence();
					cpt.Seq.addAll(tmp);
					cpt.Seq.addAll(sq.get(j).Seq);
					
						bw1.newLine();
						bw1.write(cpt.Seq+"");
					
					sq2.add(cpt);
				}
			}
		}	
		sq2.forEach(s->{
			System.out.println(s.Seq);
		});
		return sq2;} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		return null;
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
	//////////////////////////////////////
	
	
	
	public ArrayList<String> SuperAlgo(ObservableList<TimedConstraint> constraints ,ArrayList<Sequence> seq ,Model data ) {
		
		ArrayList<String> result = new ArrayList<String>();
		for(int i =0 ;i<constraints.size();i++) {
				int j=0;
				while(j<seq.size() && !checktimedconstraint(constraints.get(i),seq.get(j),data)) {
				j++;
			}
				if(j== seq.size()) {
					result.add(constraints.get(i).name.get()+": Invalid Constraint");
				}
				else {
					result.add(constraints.get(i).name.get()+": Valid Constraint");
				}
		}
		
		return result;
		
	}

	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

public boolean checktimedconstraint(TimedConstraint t , Sequence s,Model data) {
	String result ="";
	int horloge =0;
	String event = "";
	String method ="";
	Hseq met1 = null ;
	Hseq met2 = null ;
	ArrayList<Hseq> hor = gethorloge( s,data);
	boolean b1 = true;
	boolean b2 = true;
	for (int i = 0; i < hor.size(); i++) {
		//System.out.println(t.event1+""+t.Meth1.getName().get()); System.out.println(""+hor.get(i).methode);
	if((t.event1+""+t.Meth1.getMethodeName().get()).compareTo(hor.get(i).methode)==0 & b1 ) { met1 = hor.get(i); b1 = false;}
	if((t.event2+""+t.Meth2.getMethodeName().get()).compareTo(hor.get(i).methode)==0  & b2) { met2 = hor.get(i); b2 = false;}	
	}
	//System.out.println(met1.methode);
	//System.out.println(met2.methode);
	if((met1 != null) & (met2 != null) ) {
		
	boolean res =	check(met1,met2,t);
	if(res) {
		return true ;
	}else { return false;}
		
	}
	return false;
  
	
}
public boolean check(Hseq h1 ,Hseq h2 ,TimedConstraint t) {
	String op = t.opL;
	int val = t.Value;
	int r =0;
	if(t.opC.compareTo("\\+")== 0) { r = h1.horloge + h2.horloge; 
	//System.out.println("r:"+r+"h1:"+h1+"h2:"+h2);
	}
	if(t.opC.compareTo("-")== 0) { r = h1.horloge - h2.horloge ;
	//System.out.println("r:"+r+"h1:"+h1+"h2:"+h2);
	}
	
	
	if(op.compareTo("==")== 0) { 
		if(r == val)return true ;else return false ;
	}
	if(op.compareTo("<=")== 0) { 
		if(r <= val)return true ;else return false ;
	}
	if(op.compareTo("<")== 0) { 
		if(r < val)return true ;else return false ;
	}
	if(op.compareTo(">")== 0) { 
		if(r > val)return true ;else return false ;
	}
	if(op.compareTo(">=")== 0) { 
		if(r >= val)return true ;else return false ;
	}
	return false;
	
	
}

public ArrayList<Hseq> gethorloge(Sequence s,Model data){
	ArrayList<Hseq> temp = new ArrayList<Hseq>();
	String result ="";
	int horloge =0;
	String event = "";
	String method ="";
	

	for (int i = 0; i < s.Seq.size(); i++) {
		
		Hseq t = new Hseq();
		if(s.Seq.get(i).compareTo("SKIP")!= 0) { 
			event = s.Seq.get(i).charAt(0)+"";
		
			//System.out.println(event);
			method = s.Seq.get(i).substring(1);
			
			//System.out.println(method);
			Methode m = getmethode(method , data);
			//System.out.println(method+"   et la methode est    "+m.getMethodeName().get());
			
			if(event.compareTo("D")==0) {    t.methode = "D"+method; t.horloge=horloge; }
			else
		{  horloge += m.ExecutionTime;  t.methode = "F"+method; t.horloge=horloge; }
			temp.add(t);
			
			
		}
		
		
		
	}
//	for (int j = 0; j <temp.size(); j++) {
//		System.out.println(temp.get(j).methode+"  "+temp.get(j).horloge);
//		
//	}
	return temp;
	
	
	
}

public Methode getmethode(String m ,Model data) {
	
	for (int i = 0; i < data.getComponentTail(); i++) {
		for (int j = 0; j < data.getComponentModel(i).getMethode().size(); j++) {
			
			Methode s = data.getComponentModel(i).getMethode().get(j);
			if(m.compareTo(s.getMethodeName().get())==0) return s;
		}
		
	}
	return null;
	
	
}

}
