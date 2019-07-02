package nfattribute;

import java.util.ArrayList;

public class Automata {
	
	Node initialState;
	ArrayList<Node> states;
	ArrayList<Node> finalStates;
	


	public Automata handle(ArbreBinaire<String> tree) {
		Automata a = new Automata();
		if (tree.getaBD() == null && tree.getaBG() == null) {
			generateSimpleAutomata(a,tree);

		} else {
			ArbreBinaire<String> tree1 = tree.getaBG();
			ArbreBinaire<String> tree2 = tree.getaBD();
			String op = tree.getNeoud();
			switch (op) {
			case "->":
				concatenate(a,handle(tree1), handle(tree2));
				break;
			case ";":
				concatenate(a,handle(tree1), handle(tree2));
				break;
			case "[]":
				choose(a,handle(tree1), handle(tree2));
				break;

			default:
				break;
			}

		}
		return a;

	}

	public Automata generateSimpleAutomata(Automata a, ArbreBinaire<String> tree) {
		if(a.initialState == null) {
			a.initialState = new Node(tree.getNeoud());
		}
		else {
			if(a.initialState.transitions.size() == 0) {
				a.initialState.transitions.add(new Node(tree.getNeoud()));
			}
			else {
				Node n = a.initialState.transitions.get(0);
				while(n.transitions.size()>0) {
					n = n.transitions.get(0);
				}
				n.transitions.add(new Node(tree.getNeoud()));
				Node f = n.transitions.get(0);
				a.finalStates.remove(n);
				a.finalStates.add(f);
			}
			
		}
		return a;
	}
	
	public Automata concatenate(Automata a,Automata a1,Automata a2) {
		a1.finalStates.forEach(f->{
			f.transitions.add(a2.initialState);
		});
		if(a.initialState == null) {
			a.initialState = a1.initialState;
		}
		else {
			a.finalStates.forEach(f->{
				f.transitions.add(a1.initialState);
			});
		}
		a.finalStates =  a2.finalStates;
		a.states.addAll(a1.states);
		a.states.addAll(a2.states);
		a.states.addAll(a1.finalStates);
		
		return a;
	}
	
	public Automata choose(Automata a, Automata a1,Automata a2) {
		a.finalStates.forEach(f->{
			f.transitions.add(a1.initialState);
			f.transitions.add(a2.initialState);
			a.states.add(f);
		});
		a.states.addAll(a1.states);
		a.states.addAll(a2.states);
		a.finalStates = a1.finalStates;
		a.finalStates.addAll(a2.finalStates);
		return a;
	}
	
	public void afficheAutomata(Node n) {
		System.out.println(n.name+"->");
		afficheAutomata(n.transitions.get(0));
	}

}
