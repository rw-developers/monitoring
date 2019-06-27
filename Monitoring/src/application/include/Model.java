package application.include;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

//import application.include.Model.classStackData;
import application.objects.PortBlock;
import application.objects.ComponentBlock;
import application.objects.ImplementationBlock;
import application.objects.Link;
import application.view.ProgramWindow;
//import application.view.context.PortMenu;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.util.Callback;
import monitoring.elements.ArchitectureElement;
import monitoring.elements.Component;
import monitoring.elements.ComponentImplementation;
import monitoring.elements.Configuration;
import monitoring.elements.Connector;
import monitoring.elements.Csp;
//import monitoring.elements.Method;
import monitoring.elements.Port;
import nfattribute.ExecutionTime;
import nfattribute.NFAttribute;
import nfattribute.OtherConstraint;
import nfattribute.*;


public class Model {

	private Stack<classStackData> classUndoStack = new Stack<classStackData>();
	private Stack<classStackData> classRedoStack = new Stack<classStackData>();
	private Stack<Integer> classUndoStackSize = new Stack<Integer>();
	private Stack<Integer> classRedoStackSize = new Stack<Integer>();
	private Stack<linkStackData> linkUndoStack = new Stack<linkStackData>();
	private Stack<linkStackData> linkRedoStack = new Stack<linkStackData>();
	private Stack<Integer> linkUndoStackSize = new Stack<Integer>();
	private Stack<Integer> linkRedoStackSize = new Stack<Integer>();

	private Boolean duringUndo = false;
	private Boolean duringRedo = false;
	private Boolean clearing = false;

	// project
	public boolean saved = false;
	public String project_dir = new String();

	public class classStackData {
		private int[] intData = new int[5];
		private String name;
		private String type;
	}

	public class linkStackData {
		private int[] intData = new int[8];
		private String label;  
	}
   
	private ObservableList<Configuration> configurationList;
	private ObservableList<Port> portList;
	private ObservableList<Component> componentList;
	private ObservableList<ComponentImplementation> implementationList;
	private ObservableList<OtherConstraint> OtherConstraint =FXCollections.observableArrayList();
	private ObservableList<TimedConstraint> TimedConstraint  = FXCollections.observableArrayList();
	public ObservableList<OtherConstraint> getOtherConstraint() {
		return OtherConstraint;
	}

	public void setOtherConstraint(OtherConstraint otherConstraint) {
		OtherConstraint.add(otherConstraint);
	}

	public ObservableList<TimedConstraint> getTimedConstraint() {
		return TimedConstraint;
	}

	public void setTimedConstraint(TimedConstraint timedConstraint) {
		TimedConstraint.add(timedConstraint);
	}

	//private ObservableList<Method> methodList;
	private ObservableList<Connector> linkList;
	private ObservableList<NFAttribute> nFAttributeList;

	private List<PortBlock> ports;
	private List<ComponentBlock> components;
	private List<ImplementationBlock> implementations;
	private List<Link> links;
	private boolean createLinkMode = false;

	/*
	 * This class uses the portList and connectionList ports to represent all the
	 * elements being stored in the diagram. Using two separate ports to store this
	 * data will, I think, make it easier to extend if we get to the point where we
	 * want to add additional features, as well as providing a relatively simple
	 * structure to dump and reload in order to save and load files.
	 */

	/**
	 * Constructs an instance of Model
	 * 
	 * @constructor
	 */
	public Model() {

		configurationList = FXCollections.observableArrayList(new Callback<Configuration, Observable[]>() {
			@Override
			public Observable[] call(Configuration param) {
				return new Observable[] { param.getNameProp() };
			}
		});

		componentList = FXCollections.observableArrayList(new Callback<Component, Observable[]>() {
			@Override
			public Observable[] call(Component param) {
				return new Observable[] { param.getNameProp() };
			}
		});

		implementationList = FXCollections.observableArrayList(new Callback<ComponentImplementation, Observable[]>() {
			@Override
			public Observable[] call(ComponentImplementation param) {
				return new Observable[] { param.getNameProp() };
			}
		});

		portList = FXCollections.observableArrayList(new Callback<Port, Observable[]>() {
			@Override
			public Observable[] call(Port param) {
				return new Observable[] { param.getNameProp(), param.getTypeProp() };
			}
		});

		linkList = FXCollections.observableArrayList(new Callback<Connector, Observable[]>() {
			@Override
			public Observable[] call(Connector param) {
				return new Observable[] { param.getSourceProp(), param.getDestProp() };
			}
		});

		ports = new ArrayList<PortBlock>();
		components = new ArrayList<ComponentBlock>();
		implementations = new ArrayList<ImplementationBlock>();
		links = new ArrayList<Link>();
	}

	public ObservableList<Port> getPortProperty() {
		return portList;
	}

	public ObservableList<Configuration> getConfigurationProperty() {
		return configurationList;
	}

	public ObservableList<Component> getComponentProperty() {
		return componentList;
	}

	public ObservableList<ComponentImplementation> getImplementationProperty() {
		return implementationList;
	}

	public ObservableList<Connector> getLinkProperty() {
		return linkList;
	}
	
	public ObservableList<NFAttribute> getNFAttributeProperty() {
		return nFAttributeList;
	}

	public int getPortTail() {
		return portList.size();
	}

	public int getComponentTail() {
		return componentList.size();
	}

	public int getConfigurationTail() {
		return configurationList.size();
	}

	public int getImplementationTail() {
		return implementationList.size();
	}

	public int getLinkTail() {
		return linkList.size();
	}
	
	public int getNFAttributeTail() {
		return linkList.size();
	}

	public Port getPortModel(int i) {
		return portList.get(i);
	}

	public Configuration getConfigurationModel(int i) {
		return configurationList.get(i);
	}

	public Component getComponentModel(int i) {
		return componentList.get(i);
	}

	public ComponentImplementation getImplementationModel(int i) {
		return implementationList.get(i);
	}
	
	public NFAttribute getNFAttributeModel(int i) {
		return nFAttributeList.get(i);
	}

	public Connector getLinkModel(int i) {
		return linkList.get(i);
	}

	public PortBlock getPort(int i) {
		return ports.get(i);
	}

	public ComponentBlock getComponent(int i) {
		return components.get(i);
	}

	public ImplementationBlock getImplementation(int i) {
		return implementations.get(i);
	}

	public Link getLink(int i) {
		return links.get(i);
	}

	public int addPortModel(int[] ints, String[] strings, ArchitectureElement e) {
		if (ints.length == 5 && strings.length == 3) {
			portList.add(new Port(ints, strings, e));
		}
		return (portList.size() - 1);
	}

	public void removePortModel(int i) {
		portList.remove(i);

		refreshLines();
	}

	public int addConfigurationModel(int id, String name) {
		configurationList.add(new Configuration(id, name));
		return (configurationList.size() - 1);
	}

	public void removeConfigurationModel(int i) {
		configurationList.remove(i);
		// deleteImplementation

	}

	public int addComponentModel(int[] ints, String name) {
		if (ints.length == 5) {
			componentList.add(new Component(ints, name));
		}
		return (componentList.size() - 1);
	}

	public void removeComponentModel(int i) {
		componentList.remove(i);
		refreshPortBlocks();

	}

	public int addImplementationModel(int[] ints, String name, Component parent, Configuration conf) {
		if (ints.length == 5) {
			implementationList.add(new ComponentImplementation(ints, name, parent, conf));
		}
		return (implementationList.size() - 1);
	}

	public void removeImplementationModel(int i) {
		implementationList.remove(i);
		refreshPortBlocks();

	}
	
//	public int addNFAttributeETModel(int id, String name, String value, Method m) {
//			nFAttributeList.add(new ExecutionTime(id,name,value,m));
//		return (nFAttributeList.size() - 1);
//	}

	public void removeNFAttributeModel(int i) {
		nFAttributeList.remove(i);
	}

	public void refreshLines() {
		for (Link link : links)
			link.updateLine();
	}

	public void prepPortBlocks() {
		for (PortBlock p : ports)
			p.prepWidthHeight();
	}

	public void refreshPortBlocks() {
		for (int i = 0; i < ports.size(); i++) {
			ports.get(i).initWidthHeight();
			ports.get(i).getNode().setBounds((int) (portList.get(i).getXPos()),
					(int) (portList.get(i).getXPos() + ports.get(i).getWidth()), (int) (portList.get(i).getYPos()),
					(int) (portList.get(i).getYPos() + ports.get(i).getHeight()));
		}
	}

	public void prepComponentBlocks() {
		for (ComponentBlock comp : components)
			comp.prepWidthHeight();
	}

	public void refreshComponentBlocks() {
		for (int i = 0; i < components.size(); i++) {
			components.get(i).initWidthHeight();
			components.get(i).getNode().setBounds((int) (componentList.get(i).getXPos()),
					(int) (componentList.get(i).getXPos() + components.get(i).getWidth()),
					(int) (componentList.get(i).getYPos()),
					(int) (componentList.get(i).getYPos() + components.get(i).getHeight()));
		}
	}

	public void prepImplementationBlocks() {
		for (ImplementationBlock imp : implementations)
			imp.prepWidthHeight();
	}

	public void refreshImplementationBlocks() {
		for (int i = 0; i < implementations.size(); i++) {
			implementations.get(i).initWidthHeight();
			implementations.get(i).getNode().setBounds((int) (implementationList.get(i).getXPos()),
					(int) (implementationList.get(i).getXPos() + implementations.get(i).getWidth()),
					(int) (implementationList.get(i).getYPos()),
					(int) (implementationList.get(i).getYPos() + implementations.get(i).getHeight()));
		}
	}

	/**
	 * Removes the Connector object stored at index i and decrements later indices
	 * 
	 * @param i the index of the Connector to be removed
	 */
	public void removeLinkModel(int i) {
//		links.get(i).warnLinkNodes();
		linkList.remove(i);
		for (int l = i; l != linkList.size(); ++l) {
			linkList.get(l).setIndex(l);
//			links.get(i).warnLinkNodes();
		}
	}

	/**
	 * Creates a new Connector object and places it at the end of the list.
	 * 
	 * @param ints  A list of int arguments to be passed to the ConnectionModel
	 *              constructor.
	 * @param label The label to be passed to the ConnectionModel constructor.
	 * @return the index of the new ConnectionModel object
	 */
	public int addLinkModel(int[] ints, String label, Configuration config,Csp csp,int bandwidth,Port in,Port out) {
		if (ints.length == 8) {
			linkList.add(new Connector(ints, label, config,csp,bandwidth,in,out));
		}
		return (linkList.size() - 1);
	}

	public void addPort(PortBlock in) {
		ports.add(in);
	}

	public void removePort(int i) {
		ports.remove(i);
		// menuUpdate(i);
	}

	public void addComponent(ComponentBlock in) {
		components.add(in);
	}

	public void removeComponent(int i) {
		components.remove(i);
		// menuUpdate(i);
	}

	public void addImplementation(ImplementationBlock in) {
		implementations.add(in);
	}

	public void removeImplementation(int i) {
		implementations.remove(i);
		// menuUpdate(i);
	}

	public void addLink(Link in) {
		links.add(in);
	}

	public void removeLink(int i) {
		links.remove(i);
	}

	/**
	 * menu container system - disabled
	 * 
	 * (would allow correct right-click delete fucntionality)
	 * 
	 * requires updating the save/load and all undo/redo functions to maintain
	 * PortMenus
	 */

	/*
	 * 
	 * 
	 * @param index
	 * 
	 * @param classContextMenu
	 * 
	 * public void addMenu(int i, PortMenu classContextMenu) { menuData menu = new
	 * menuData(); menu.index = i; menu.menu = classContextMenu; menus.add(menu); }
	 */

	/**
	 * 
	 * 
	 * @param i
	 * 
	 *          public void menuUpdate(int i) { System.out.println("removing menu
	 *          index " + i); menus.remove(i);
	 * 
	 *          for (int l = i; l != menus.size(); ++l) { menus.get(l).index = l;
	 *          menus.get(l).menu.updateIndex(l); } }
	 */

	/**
	 * Clears all Links from the links list.
	 * 
	 */
	public void clearLinks() {
		clearing = true;
		for (Link linky : links)
			linky.warnLinkNodes();

		links.clear();
		linkList.clear();

		clearing = false;
	}

	/**
	 * 
	 * @return the index of the highest numbered class
	 */
	public int maxLink() {
		return portList.size() - 1;
	}

	/**
	 * Tells if it is safe to saveUndoState.
	 * 
	 * @return is false if model is in the middle of a undo or redo
	 */
	public Boolean safeToSave() {
		return !(duringUndo || duringRedo);
	}

	/**
	 * Tells if model is currently being cleared.
	 * 
	 * @return is true if model is in the middle of a clear
	 */
	public Boolean isClearing() {
		return clearing;
	}

	/**
	 * Sets flag that undo is in process.
	 * 
	 */
	public void setUndoState() {
		duringUndo = true;

	}

	/**
	 * Sets flag that redo is in process.
	 * 
	 */
	public void setRedoState() {
		duringRedo = true;
	}

	/**
	 * clears the entire Redo stack (because of a branch in user choices).
	 * 
	 */
	public void clearRedoState() {
		while (!classRedoStackSize.isEmpty()) {
			int size = classRedoStackSize.pop();
			for (int i = 0; i != size; ++i)
				classRedoStack.pop();
		}

		while (!linkRedoStackSize.isEmpty()) {
			int size = linkRedoStackSize.pop();

			for (int i = 0; i != size; ++i)
				linkRedoStack.pop();
		}
	}

	/**
	 * 
	 * @return returns true if the Undo Stack is empty
	 */
	public Boolean isUndoEmpty() {
		return classUndoStackSize.size() == 0 ? true : false;
	}

	/**
	 * 
	 * @return returns true if the Redo stack is empty
	 */
	public Boolean isRedoEmpty() {
		return classRedoStackSize.size() == 0 ? true : false;
	}

	/**
	 * Saves an undoState onto the classUndoStack.
	 * 
	 */
	public void saveUndoState() {
		if (portList.size() != 0) {
			classUndoStackSize.push(portList.size());

			for (int i = portList.size() - 1; i != -1; --i) {
				classStackData state = new classStackData();

				state.intData[0] = portList.get(i).getIndex();
				state.intData[1] = portList.get(i).getXPos();
				state.intData[2] = portList.get(i).getYPos();
				state.intData[3] = portList.get(i).getWidth();
				state.intData[4] = portList.get(i).getHeight();
				state.name = portList.get(i).getName();
				state.type = portList.get(i).getType();

				classUndoStack.push(state);
			}

			linkUndoStackSize.push(linkList.size());

			for (int i = linkList.size() - 1; i != -1; --i) {
				linkStackData lstate = new linkStackData();

				lstate.intData[0] = linkList.get(i).getIndex();
				lstate.intData[1] = linkList.get(i).getType();
				lstate.intData[2] = linkList.get(i).getSource();
				lstate.intData[3] = linkList.get(i).getDest();
				lstate.intData[4] = linkList.get(i).getSourceMin();
				lstate.intData[5] = linkList.get(i).getSourceMax();
				lstate.intData[6] = linkList.get(i).getDestMin();
				lstate.intData[7] = linkList.get(i).getDestMax();
				lstate.label = linkList.get(i).getLabel();

				linkUndoStack.push(lstate);
			}
		}
	}

	/**
	 * Saves the current model state to the Redo Stack.
	 * 
	 */
	public void saveRedoState() {
		if (portList.size() != 0) {
			classRedoStackSize.push(portList.size());

			for (int i = portList.size() - 1; i != -1; --i) {
				classStackData state = new classStackData();

				state.intData[0] = portList.get(i).getIndex();
				state.intData[1] = portList.get(i).getXPos();
				state.intData[2] = portList.get(i).getYPos();
				state.intData[3] = portList.get(i).getWidth();
				state.intData[4] = portList.get(i).getHeight();
				state.name = portList.get(i).getName();
				state.type = portList.get(i).getType();

				classRedoStack.push(state);
			}

			linkRedoStackSize.push(linkList.size());

			for (int i = linkList.size() - 1; i != -1; --i) {
				linkStackData lstate = new linkStackData();

				lstate.intData[0] = linkList.get(i).getIndex();
				lstate.intData[1] = linkList.get(i).getType();
				lstate.intData[2] = linkList.get(i).getSource();
				lstate.intData[3] = linkList.get(i).getDest();
				lstate.intData[4] = linkList.get(i).getSourceMin();
				lstate.intData[5] = linkList.get(i).getSourceMax();
				lstate.intData[6] = linkList.get(i).getDestMin();
				lstate.intData[7] = linkList.get(i).getDestMax();
				lstate.label = linkList.get(i).getLabel();

				linkRedoStack.push(lstate);
			}
		}
	}

	/**
	 * Undoes the latest action done by the user.
	 * 
	 */
	public void undo() {
		this.saveRedoState();

		if (!classUndoStackSize.isEmpty()) {
			int size = classUndoStackSize.pop();

			for (int i = 0; i != size; ++i) {
				classStackData state = classUndoStack.pop();

				int[] ints = { state.intData[0], state.intData[1], state.intData[2], state.intData[3],
						state.intData[4] };

				String[] strings = { state.name, state.type };
				portList.add(new Port(ints, strings, null));
			}
		}

		if (!linkUndoStackSize.isEmpty()) {
			int size = linkUndoStackSize.pop();

			for (int i = 0; i != size; ++i) {
				linkStackData lstate = linkUndoStack.pop();

				int[] ints = { lstate.intData[0], lstate.intData[1], lstate.intData[2], lstate.intData[3],
						lstate.intData[4], lstate.intData[5], lstate.intData[6], lstate.intData[7] };

				String label = lstate.label;
				////////////////////////////////////////////////////////
				linkList.add(new Connector(ints, label, null,null,0,null,null));
			}
		}

		duringUndo = false;
	}

	/**
	 * Re-does what the user choose to undo.
	 * 
	 */
	public void redo() {
		if (!classRedoStackSize.isEmpty()) {
			int size = classRedoStackSize.pop();

			for (int i = 0; i != size; ++i) {
				classStackData state = classRedoStack.pop();

				int[] ints = { state.intData[0], state.intData[1], state.intData[2], state.intData[3],
						state.intData[4] };

				String[] strings = { state.name, state.type };
				portList.add(new Port(ints, strings, null));
			}
		}

		if (!linkRedoStackSize.isEmpty()) {
			int size = linkRedoStackSize.pop();

			for (int i = 0; i != size; ++i) {
				linkStackData lstate = linkRedoStack.pop();

				int[] ints = { lstate.intData[0], lstate.intData[1], lstate.intData[2], lstate.intData[3],
						lstate.intData[4], lstate.intData[5], lstate.intData[6], lstate.intData[7] };

				String label = lstate.label;
				linkList.add(new Connector(ints, label, null,null,0,null,null));
			}
		}
		duringRedo = false;
	}

	/**
	 * Saves the model data in a format that can be reread later.
	 * 
	 * @param file The file to be written to.
	 * @throws IOException Throws if the file can't be written to.
	 */
	public void saveComponent(File file) throws IOException {
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			writer.write("COMPONENT_LIST_START\n");
			writer.write(componentList.size() + "\n");
			for (int i = 0; i != componentList.size(); ++i) {
				writer.write(componentList.get(i).getIndex() + " ");
				writer.write(componentList.get(i).getXPos() + " ");
				writer.write(componentList.get(i).getYPos() + " ");
				writer.write(componentList.get(i).getWidth() + " ");
				writer.write(componentList.get(i).getHeight() + " \n\n");
				writer.write(componentList.get(i).getName() + " \n\n");
				writer.write("PORT_LIST_START\n");
				writer.write(componentList.get(i).getPorts().size() + "\n");
				componentList.get(i).getPorts().forEach(p -> {
					try {
						writer.write(p.getIndex() + " ");
						writer.write(p.getXPos() + " ");
						writer.write(p.getYPos() + " ");
						writer.write(p.getWidth() + " ");
						writer.write(p.getHeight() + " \n\n");
						writer.write(p.getName() + " ");
						writer.write(p.getType() + " ");
						writer.write(p.getCspExpression().getExpression() + " \n\n");
						writer.write("1 ");
						writer.write(((Component) p.getElement()).getIndex() + " \n\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				});
				writer.write("PORT_LIST_END\n");
			}
			writer.write("COMPONENT_LIST_END\n");
			writer.close();
		}
	}
	
	public void loadComponent(File file) throws IOException {

		Scanner reader = new Scanner(file);
		reader.next();

		int compSize = Integer.parseInt(reader.next().trim());
		for (int i = 0; i != compSize; i++) {

			reader.useDelimiter(" ");

			int[] ints = { Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()),
					Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()),
					Integer.parseInt(reader.next().trim()) };
			reader.useDelimiter("\n");
			reader.next();
			reader.next();
			String name = reader.next().trim();

			componentList.add(new Component(ints, name));
			reader.useDelimiter("\n");
			reader.next();
			reader.next();
			
			int portSize = Integer.parseInt(reader.next().trim());
			for (int j = 0; j != portSize; ++j) {

				reader.useDelimiter(" ");

				int[] ints2 = { Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()),
						Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()),
						Integer.parseInt(reader.next().trim()) };
				reader.useDelimiter("\n");
				reader.next();
				reader.next();
				reader.useDelimiter(" ");
				String[] strings = { reader.next().trim(), reader.next().trim(), reader.next().trim() };
				reader.useDelimiter("\n");
				reader.next();
				reader.next();
				reader.useDelimiter(" ");
				String token = reader.next().trim();
				String id = reader.next().trim();
				if (Integer.parseInt(token) == 1) {
					portList.add(new Port(ints2, strings, componentList.get(Integer.parseInt(id))));
				} 
				reader.useDelimiter("\n");
				reader.next();
				reader.next();
				
	
			}
			reader.next();
		}
		reader.close();
	}

	public void save(File file,int index) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));

		writer.write("CONFIGURATION_LIST_START\n");
			writer.write(configurationList.get(index).getIndex() + " ");
			writer.write(configurationList.get(index).getName() + " \n\n");
		
		writer.write("CONFIGURATION_LIST_END\n");

		writer.write("IMPLEMENTATION_LIST_START\n");
		writer.write(configurationList.get(index).getComponents().size() + "\n");
		configurationList.get(index).getComponents().forEach(imp->{
			try {
			writer.write(imp.getIndex() + " ");
			writer.write(imp.getXPos() + " ");
			writer.write(imp.getYPos() + " ");
			writer.write(imp.getWidth() + " ");
			writer.write(imp.getHeight() + " \n\n");
			writer.write(imp.getName() + " \n\n");
			writer.write(imp.getComponentType().getIndex() + "\n\n");
			writer.write("PORT_LIST_START\n");
			writer.write(imp.getPorts().size() + "\n");
			}catch (IOException e) {
				// TODO: handle exception
			}
			imp.getPorts().forEach(p -> {
				try {
					writer.write(p.getIndex() + " ");
					writer.write(p.getXPos() + " ");
					writer.write(p.getYPos() + " ");
					writer.write(p.getWidth() + " ");
					writer.write(p.getHeight() + " \n\n");
					writer.write(p.getName() + " ");
					writer.write(p.getType() + " ");
					writer.write(p.getCsp() + " \n\n");
					writer.write("2 ");
					writer.write(((ComponentImplementation) p.getElement()).getIndex() + " \n\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});
			try {
				writer.write("PORT_LIST_END\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		writer.write("IMPLEMENTATION_LIST_END\n");
	
	

		writer.write("LINKLIST_BEGIN\n");
		writer.write(configurationList.get(index).getConnectors().size() + "\n");
		configurationList.get(index).getConnectors().forEach(c->{
			try {
			writer.write(c.getIndex() + " ");
			writer.write(c.getType() + " ");
			writer.write(c.getSource() + " ");
			writer.write(c.getDest() + " ");
			writer.write(c.getSourceMin() + " ");
			writer.write(c.getSourceMax() + " ");
			writer.write(c.getDestMin() + " ");
			writer.write(c.getDestMax() + " \n");
			writer.write(c.getLabel() + "\n");
			}catch (IOException e) {
				// TODO: handle exception
			}
		});
			
		writer.write("LINKLIST_END\n");

		writer.close();
	}
	
	/**
	 * Reads in the model data and rebuilds the model.
	 * 
	 * @param file The file to be read from.
	 * @throws IOException Throws if the file can't be read from.
	 */
	public void load(File file,TabPane appPanel) throws IOException {

		Scanner reader = new Scanner(file);
		reader.next();
		

		
		reader.useDelimiter(" ");
		int index = Integer.parseInt(reader.next().trim());
		reader.useDelimiter(" ");
		String n = reader.next().trim();
		configurationList.add(new Configuration(index, n));
		
		SingleSelectionModel<Tab> singleselectionModel = appPanel.getSelectionModel();
		singleselectionModel.select(appPanel.getTabs().get(index+1));

		
		
		reader.useDelimiter("\n");
		reader.next();
		reader.next();
		reader.next();
		reader.next();



		int size = Integer.parseInt(reader.next().trim());
		for (int i = 0; i != size; ++i) {

			reader.useDelimiter(" ");

			int[] ints = { Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()),
					Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()),
					Integer.parseInt(reader.next().trim()) };
			reader.useDelimiter("\n");
			reader.next();
			reader.next();
			String name = reader.next().trim();
			// reader.next();
			reader.next();
			String id = reader.next().trim();
			int confId = appPanel.getSelectionModel().getSelectedIndex()-1;

			 implementationList.add(new ComponentImplementation(ints,
			 name,componentList.get(Integer.parseInt(id)),configurationList.get(confId)));
			 
			 reader.useDelimiter("\n");
				reader.next();
				reader.next();
				
				int portSize = Integer.parseInt(reader.next().trim());
				for (int j = 0; j != portSize; ++j) {

					reader.useDelimiter(" ");

					int[] ints2 = { Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()),
							Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()),
							Integer.parseInt(reader.next().trim()) };
					reader.useDelimiter("\n");
					reader.next();
					reader.next();
					reader.useDelimiter(" ");
					String[] strings = { reader.next().trim(), reader.next().trim(), reader.next().trim() };
					reader.useDelimiter("\n");
					reader.next();
					reader.next();
					reader.useDelimiter(" ");
					String token = reader.next().trim();
					String id2 = reader.next().trim();
					if (Integer.parseInt(token) == 2) {
						portList.add(new Port(ints2, strings, implementationList.get(Integer.parseInt(id2))));
					} 
					reader.useDelimiter("\n");
					reader.next();
					reader.next();
					
		
				}
				reader.next();

		}
		reader.next();
		reader.next();

		// From here down will almost certainly need to be rewritten once links are
		// implemented.
		size = Integer.parseInt(reader.next().trim());
		for (int i = 0; i != size; ++i) {
			reader.useDelimiter(" ");
			int[] ints = { Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()),
					Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()),
					Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()),
					Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()) };
			reader.useDelimiter("\n");
			reader.next();
			String label = reader.next().trim();
			int confId = appPanel.getSelectionModel().getSelectedIndex()-1;

			linkList.add(new Connector(ints, label, configurationList.get(confId),null,0,null,null));
		}
		reader.close();
	}

	public void saveXml(File file, Configuration conf) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));

		writer.write("<Architecture>\n");

		try {
			writer.write("<Configuration>\n");
		} catch (Exception e) {
		}
		conf.getComponents().forEach(comp -> {
			try {
				writer.write("<Component>\n");
				writer.write("<name>");
				writer.write(comp.getName());
				writer.write("</name>\n");
			} catch (Exception e) {
			}
			comp.getPorts().forEach(port -> {
				try {
					writer.write("<Port>\n");
					writer.write("<name>");
					writer.write(port.getName());
					writer.write("</name>\n");
					writer.write("<type>");
					writer.write(port.getType());
					writer.write("</type>\n");
					writer.write("</Port>\n");
				} catch (Exception e) {
				}
			});
			try {
				writer.write("</Component>\n");
			} catch (Exception e) {
			}

		});
		conf.getConnectors().forEach(c -> {
			try {
				writer.write("<Connector>\n");
				writer.write("<name>");
				writer.write(portList.get(c.getSource()).getName() + "->" + portList.get(c.getDest()).getName());
				writer.write("</name>\n");
				writer.write("<Port_In>\n");
				writer.write("<name>");
				writer.write(portList.get(c.getDest()).getName());
				writer.write("</name>\n");
				writer.write("<type>");
				writer.write(portList.get(c.getDest()).getType());
				writer.write("</type>\n");
				writer.write("</Port_In>\n");
				writer.write("<Port_Out>\n");
				writer.write("<name>");
				writer.write(portList.get(c.getSource()).getName());
				writer.write("</name>\n");
				writer.write("<type>");
				writer.write(portList.get(c.getDest()).getType());
				writer.write("</type>\n");
				writer.write("</Port_Out>\n");
				writer.write("</Connector>\n");
			} catch (Exception e) {
			}
		});

		try {
			writer.write("</Configuration>\n");
		} catch (Exception e) {
		}

		writer.write("</Architecture>\n");

		writer.close();
	}

	

	/**
	 * Clears the model of all data.
	 */
	public void clear() {
		clearing = true;
		configurationList.clear();
		componentList.clear();
		portList.clear();
		ports.clear();
		linkList.clear();
		for (Link linky : links)
			linky.warnLinkNodes();

		links.clear();

		clearing = false;
	}

	/**
	 * Hands window to the model for a moment so the window can be cleared of all
	 * Links
	 * 
	 * @param window The window to be cleared of all Links
	 */
	public void assistRemoveLinks(ProgramWindow window) {
		for (PortBlock classy : ports) {
			classy.getNode().clearLinks();
		}

		for (Link linky : links)
			window.remove(linky);
	}

	/**
	 * 
	 * @return returns createLinkMode value.
	 * 
	 *         If it's false, the blocks are draggable. If it's true, dragging
	 *         creates links.
	 * 
	 */

	public boolean isLinkable() {
		return createLinkMode;
	}

	/**
	 * Toggles createLinkMode.
	 */

	public void toggleLinkMode() {
		createLinkMode = !createLinkMode;
	}
}
