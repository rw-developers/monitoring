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
			ArbreBinaire<String> tree1 = tree.getaBD();
			ArbreBinaire<String> tree2 = tree.getaBG();
			String op = tree.getNeoud();
			switch (op) {
			case "->":
				
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
			a.finalStates.add(new Node(tree.getNeoud()));
		}
		else {
			a.finalStates.forEach(f->{
				f.transitions.add(new Node(tree.getNeoud()));
				a.finalStates.add(new Node(tree.getNeoud()));
				a.states.add(f);
				a.finalStates.remove(f);
			});
		}
		return a;
	}
	
	public Automata concatenate(Automata a1,Automata a2) {
		a1.finalStates.forEach(f->{
			f.transitions.add(a2.initialState);
		});
		Automata a = new Automata();
		a.initialState = a.initialState;
		a.finalStates =  a2.finalStates;
		a.states.addAll(a1.states);
		a.states.addAll(a1.finalStates);
		
		return a;
	}

}
