package monitoring.reconfiguration;

import java.util.ArrayList;
import java.util.List;

import monitoring.elements.ArchitectureElement;

public class ReconfigurationManager {
	
	private List<ArchitectureElement> architectureElements = new ArrayList<ArchitectureElement>();
	private List<ReconfigurationRule> reconfigurationRules = new ArrayList<ReconfigurationRule>();
	
	public void setArchitectureElements(ArchitectureElement architectureElement) {
		this.architectureElements.add(architectureElement);
	}
	
	public void setReconfigurationRules(ReconfigurationRule reconfigurationRule) {
		this.reconfigurationRules.add(reconfigurationRule);
	}
	
	public List<ArchitectureElement> getArchitectureElements() {
		return architectureElements;
	}
	
	public List<ReconfigurationRule> getReconfigurationRules() {
		return reconfigurationRules;
	}

}
