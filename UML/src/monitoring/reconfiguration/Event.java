package monitoring.reconfiguration;

import java.util.ArrayList;
import java.util.List;

public class Event {
	private int ID;
	private List<Action> actions = new ArrayList<Action>();
	
	public void setID(int id) {
		ID = id;
	}
	
	public void setActions(Action action) {
		this.actions.add(action);
	}
	
	public int getID() {
		return ID;
	}
	
	public List<Action> getActions() {
		return actions;
	}

}
