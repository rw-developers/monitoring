package nfattribute;

import java.util.Stack;

import monitoring.elements.Configuration;

public class GenerateTree {
	String[] outExp = new String[100];
	int pos = 0;
	String exp;
	String corrent;

	Stack<String> stk = new Stack<String>();

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
			this.exp = s.substring(2);
			this.pos++;
			return true;
		}

		else
			return false;
	}

	boolean isMethod(String s) {
		if (s.charAt(0) == '@') {
			int i = 1;
			while (s.charAt(i) >= 'a' && s.charAt(i) <= 'z'
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
		if(s.charAt(0) == '(') {
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
			}
		}
		tree.inserer(stk.pop());
		this.exp.replaceAll("SKIP))", "SKIP");
		tree.inserer(this.exp);
		return tree;
	}
	public ArbreSyntaxique getConfigurationTree(Configuration conf) {
	   String [] formule = conf.methodFormula(1).split("|||");
	   for(int i=0;i<formule.length;i++) {
		   
	   }
	   return null;
	}
}
