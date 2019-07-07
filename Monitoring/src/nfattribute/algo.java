package nfattribute;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import application.include.Model;

public class algo {
	String[] outExp = new String[100];
	int pos = 0;
	String exp;
	String corrent;

	String stack[] = new String[20];
	Stack<String> stk = new Stack<String>();
	char stack1[] = new char[20];
	int t;

	void push(char ch) {
		t++;
		stack1[t] = ch;
	}

	char pop() {
		char ch;
		ch = stack1[t];
		t--;
		return ch;
	}

	int pre(char ch) {
		switch (ch) {
		case '-':
			return 1;
		case '+':
			return 1;
		case '*':
			return 2;
		case '/':
			return 2;
		}
		return 0;
	}

	boolean operator(char ch) {
		if (ch == '/' || ch == '*' || ch == '+' || ch == '-')
			return true;
		else
			return false;
	}

	boolean isAlpha(char ch) {

		if (ch >= 'a' && ch <= 'z' || ch >= '0' && ch == '9' || ch >= 'A' && ch <= 'Z')
			return true;
		else
			return false;
	}

	void postfix(String s1) {
		char output[] = new char[s1.length()];
		char ch;
		int p = 0;
		for (int i = 0; i < s1.length(); i++) {
			ch = s1.charAt(i);
			if (ch == '(') {
				push(ch);
			} else if (isAlpha(ch)) {
				output[p++] = ch;
			} else if (operator(ch)) {
				if (stack1[t] == 0 || (pre(ch) > pre(stack1[t])) || stack1[t] == '(') {
					push(ch);
				}
			} else if (pre(ch) >= pre(stack1[t])) {
				output[p++] = pop();
				push(ch);
			} else if (ch == '(') {
				while ((ch = pop()) != '(') {
					output[p++] = ch;
				}
			}
		}
		while (t != 0) {
			output[p++] = pop();
		}

		for (int j = 0; j < s1.length(); j++) {
			System.out.print(output[j]);

		}
	}

	boolean isOperator(String s) {
		if (s.charAt(0) == '-') {
			outExp[pos] = s.substring(0, 2);
			this.corrent = s.substring(0, 2);
			this.exp = s.substring(2);
			this.pos++;
			return true;
		} else if (s.charAt(0) == '|') {
			outExp[pos] = s.substring(0, 3);
			this.corrent = s.substring(0, 3);
			this.exp = s.substring(3);
			this.pos++;
			return true;
		} else if (s.charAt(0) == '[') {
			outExp[pos] = s.substring(0, 2);
			this.corrent = s.substring(0, 2);
			this.exp = s.substring(2);
			this.pos++;
			return true;
		} else if (s.charAt(0) == ';') {
			outExp[pos] = s.substring(0, 1);
			this.corrent = s.substring(0, 1);
			this.exp = s.substring(1);
			this.pos++;
			return true;
		}

		else
			return false;
	}

	boolean isMethod(String s) {
		if (s.charAt(0) == 'D' || s.charAt(0) == 'F') {
			int i = 1;
			while (s.toLowerCase().charAt(i) >= 'a' && s.toLowerCase().charAt(i) <= 'z'
					|| s.charAt(i) >= '0' && s.charAt(i) == '9' && i < s.length()) {
				i++;
			}
			outExp[pos] = s.substring(0, i);
			this.corrent = s.substring(0, i);
			this.exp = s.substring(i);
			this.pos++;
			return true;
		} else
			return false;
	}

	boolean isPar(String s) {
		if (s.charAt(0) == '(') {
			return true;
		}
		return false;
	}

	public ArbreBinaire<String> getComponentTree(String s) {
		ArbreBinaire<String> tree = new ArbreBinaire<String>(null);
		this.exp = s;
		while (!this.exp.equals("SKIP))")) {

			if (isMethod(this.exp)) {
				if (stk.isEmpty()) {
					stk.push(this.corrent);
					// System.out.println(this.corrent);
				} else {
					tree.inserer(stk.pop());
					stk.push(this.corrent);
					// System.out.println(this.corrent);
				}

			} else if (isOperator(this.exp)) {
				tree.inserer(this.corrent);
				// System.out.println(this.corrent);
			} else if (isPar(s)) {
				this.exp = this.exp.substring(1);
			}
		}
		tree.inserer(stk.pop());
		this.exp = "SKIP";
		tree.inserer(this.exp);
		return tree;
	}

	public static void parcours_profondeur(Node n, String s, ArrayList<String> k) {

		s = s + "+" + n.name;
		Iterator<Node> list = n.transitions.iterator();
		int m = 0;
		while (list.hasNext()) {
			m++;
			parcours_profondeur(list.next(), s, k);
		}
		if (m == 0) {
			System.out.println(s);
		}
	}

//		public ArbreSyntaxique getConfigurationTree(String conf) {
//		   String [] formule = conf.split("|||");
//		   ArbreSyntaxique tree = new ArbreSyntaxique();
//		   for(int i=0;i<formule.length;i++) {
//			   tree.neoud = "|||";
//			   tree.child.add(getComponentTree(formule[i]));
//		   }
//		   return tree;
//		}
	public static void main(String[] args) throws IOException {
		String s;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		algo b = new algo();
		System.out.println("Please Enter input s1ing");

		s = "(Dcalcule->(Dadd[]Fcalcule;Fadd->SKIP))@(Dtest->(;Fshow->SKIP))";
		ArbreBinaire<String> t = b.getComponentTree("(Dcalcule|||(Dadd[]Fcalcule;Fadd->SKIP))");
		// System.out.println(t.getaBG().getNeoud());
		// t.afficherArbre();
		Automata a = new Automata();
		String[] componentMethod = s.split("@");
		GenerateTree g = new GenerateTree();
		ArbreBinaire<String> tree1 = g.getComponentTree(componentMethod[0]);
		
		ArbreBinaire<String> tree2 = g.getComponentTree(componentMethod[1]);
		tree2.afficherArbre();
		System.out.println("\n");


		Automata at = a.parallel2(a, a.handle(tree1), a.handle(tree2));
		parcours_profondeur(at.initialState, "", null);
			VerificationNF nf = new VerificationNF(new Model());
			ArrayList<Sequence> sq = nf.getSequences(at);
			sq.forEach(z -> {
				System.out.println(z.Seq);
			});
			System.out.println("all sequence");
			sq = nf.generateAllSequence(sq);
			sq.forEach(z -> {
				System.out.println(z.Seq);
			});

	}

}
