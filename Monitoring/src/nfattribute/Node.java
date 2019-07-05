package nfattribute;
import java.util.ArrayList;

public class Node {
	String name = new String();
	boolean isFinalState;
	ArrayList<Node> transitions = new ArrayList<Node>();

	public Node() {
	}
	
	public Node(String name) {
		this.name = name;

	}

}
