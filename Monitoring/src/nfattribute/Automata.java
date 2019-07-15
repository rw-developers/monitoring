package nfattribute;

import java.util.ArrayList;

import application.include.Model;
import monitoring.elements.Configuration;
import monitoring.elements.VerificationFDR;



public class Automata {
	Node initialState;
	ArrayList<Node> states;
	ArrayList<Node> finalStates;

	public Automata() {

	}

	public Automata handle(ArbreBinaire<String> tree) {
		Automata a = new Automata();

		a.states = new ArrayList<Node>();
		a.finalStates = new ArrayList<Node>();
		if (tree.getaBD() == null && tree.getaBG() == null) {
			return generateSimpleAutomata(a, tree);

		} else {
			ArbreBinaire<String> tree1 = tree.getaBG();

			ArbreBinaire<String> tree2 = tree.getaBD();

			String op = tree.getNeoud(); // System.out.println(tree.getNeoud());

			switch (op) {
			case "->":
				return concatenate(a, handle(tree1), handle(tree2));

			case ";":
				return concatenate(a, handle(tree1), handle(tree2));

			case "[]":
				return choose(a, handle(tree1), handle(tree2));

			case "|||":
				return parallel(a, handle(tree1), handle(tree2));

			default:
				break;
			}

		}
		// System.out.println(a.initialState == null);
		return a;

	}

	public Automata generateSimpleAutomata(Automata a, ArbreBinaire<String> tree) {
		if (a.initialState == null) {
			a.initialState = new Node(tree.getNeoud());
			// System.out.println(a.initialState == null);
		} else {
			parcourTransition(a.initialState, new Node(tree.getNeoud()));
		}
		return a;
	}

	public void parcourTransition(Node n, Node n2) {
		if (n.transitions.size() == 0) {
			n.transitions.add(n2);
		} else {

			for (int i = 0; i < n.transitions.size(); i++) {
				if (n.transitions.get(i).transitions.size() > 0) {
					parcourTransition(n.transitions.get(i), n2);
				} else {
					n.transitions.get(i).transitions.add(n2);
				}
			}
		}
	}

	public ArrayList<Node> getEnds(Node n) {
		ArrayList<Node> ends = new ArrayList<Node>();
		if (n.transitions.size() == 0) {
			ends.add(n);
		} else {

			for (int i = 0; i < n.transitions.size(); i++) {
				if (n.transitions.get(i).transitions.size() > 0) {
					return getEnds(n.transitions.get(i));
				} else {
					ends.add(n.transitions.get(i));
				}
			}
		}
		return ends;
	}

	public Automata concatenate(Automata a, Automata a1, Automata a2) {
		if (a.initialState == null) {
			a = a1;
			if (a1.initialState == null) {
				a = a2;
			} else {
				parcourTransition(a1.initialState, a2.initialState);
			}
		} else { // System.out.println("hola");
			parcourTransition(a.initialState, a1.initialState);
			parcourTransition(a.initialState, a2.initialState);
		}

		return a;

	}

	public Automata choose(Automata a, Automata a1, Automata a2) {
		if (a.initialState == null) {
			a = a1;
			if (a1.initialState == null) {
				a = a2;
			} else {
				getEnds(a.initialState).forEach(n -> {
					n.transitions.add(a2.initialState);
					n.transitions.add(a2.initialState.transitions.get(0));
				});

			}
		} else {
			getEnds(a.initialState).forEach(n -> {
				n.transitions.add(a1.initialState);
				n.transitions.add(a2.initialState);
			});
		}
		return a;
	}
	
	public Automata parallel2(Automata a, Automata a1, Automata a2) {
		a.initialState = new Node("#");
		
		a.initialState.transitions.add(a1.initialState);
		a.initialState.transitions.add(a2.initialState);

		return a;
	}
	
	
	public Automata parallel(Automata a, Automata a1, Automata a2) {
		if (a.initialState == null) {
			a = a1;
			if (a1.initialState == null) {
				a = a2;
			} else {
				getEnds(a.initialState).forEach(n -> {
					n.transitions.add(a2.initialState);
				});
			}

		} else {
			getEnds(a.initialState).forEach(n -> {
				n.transitions.add(a1.initialState);
			});
			getEnds(a.initialState).forEach(n -> {
				n.transitions.add(a2.initialState);
			});
		}
		return a;
	}
	
	




	public void afficheAutomata(Node n) {
		System.out.print(n.name+"->");
		for (int i = 0; i < n.transitions.size(); i++) {
			if (n.transitions.get(i).transitions.size() > 0) {
				if(n.transitions.get(i).transitions.size()>1 ) {
					System.out.println("\n");
				}
				afficheAutomata(n.transitions.get(i));
			} else {
				System.out.println(n.transitions.get(i).name);
			}
		}
	}
	
	public ArrayList<Sequence> generateConfSequences(Configuration conf) {
		Automata a = new Automata();
		VerificationNF nf = new VerificationNF(new Model());
		VerificationFDR f = new VerificationFDR();
		ArrayList<String> methodForm = null;
		
			methodForm = f.ValidateConfiguration(conf);
		
		
		ArrayList<Sequence> sq = new ArrayList<Sequence>();
	
		for (int i = 0; i < methodForm.size(); i++) {
			String [] componentMethod = methodForm.get(i).split("@");
			GenerateTree g = new GenerateTree();
			ArbreBinaire<String> tree1 = g.getComponentTree(componentMethod[0]);
			ArbreBinaire<String> tree2 =  g.getComponentTree(componentMethod[1]);
			a = parallel2(new Automata(), handle(tree1), handle(tree2));
			sq.addAll(nf.getSequences(a));
			
		}
			
		return sq;
	}

}
