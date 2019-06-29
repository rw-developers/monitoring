package nfattribute;

import java.util.ArrayList;

public class Node {
	String name;
	boolean isFinalState;
	ArrayList<Node> transitions;

	public Node(String name) {
		this.name = name;
	}

}
