package application.include;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import application.view.ProgramWindow;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import monitoring.elements.Component;
import monitoring.elements.ComponentImplementation;
import monitoring.elements.Configuration;
import monitoring.elements.Csp;
import monitoring.elements.Methode;
import monitoring.elements.Port;
import nfattribute.NFAttribute;
import nfattribute.OtherConstraint;
import nfattribute.TimedConstraint;


public class Data implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7469028436759190642L;
	private ArrayList<DConfiguration> configurationList;
	public static ArrayList<DConfiguration> configurationList2;
	private ArrayList<DComponent> componentList;

	private ArrayList<DOther> OtherList = new ArrayList<DOther>();
	private ArrayList<Timed> timedList = new ArrayList<Timed>();
	static String sfile = "";
 

	
	public void save(String dir,Model data) {
		
		try {
			String fileSeparator = System.getProperty("file.separator");
			
			componentList = new ArrayList<DComponent>();
			data.getComponentProperty().forEach(comp->{
				componentList.add(new DComponent(new int[] {comp.getIndex(),comp.getXPos(),comp.getYPos(),comp.getWidth(),comp.getHeight()},
						comp.getName(), comp.getExpGlobale(),comp.getExpMethod()));
				comp.getPorts().forEach(p->{
					componentList.get(componentList.size()-1).ports.add(new DPort(new int[] {p.getIndex(),p.getXPos(),p.getYPos(),p.getWidth(),p.getHeight()},

							p.getName(),p.getType(),p.getCspExpression(),p.getCspExpressionModify()));	
				});
				comp.getMethode().forEach(m->{
					componentList.get(componentList.size()-1).methods.add(new DMethod(m.getMethodeName().get(),m.ExecutionTime));
				});
				
			});
			configurationList = new ArrayList<DConfiguration>();
			data.getConfigurationProperty().forEach(conf->{
				configurationList.add(new DConfiguration(conf.getIndex() ,conf.getName()));
				configurationList.get(configurationList.size()-1).fomule = conf.fomule;
				configurationList.get(configurationList.size()-1).TextConfig = conf.TextConfig;
				
				conf.getImplementations().forEach(impl->{
					configurationList.get(configurationList.size()-1).implementations.add(new DImplementation(new int[] {impl.getIndex(),impl.getXPos(),impl.getYPos(),impl.getWidth(),impl.getHeight()},
							impl.getName() ,new DComponent(new int[] {impl.getComponentType().getIndex(),impl.getComponentType().getXPos(),impl.getComponentType().getYPos(),impl.getComponentType().getWidth(),impl.getComponentType().getHeight()},
									impl.getComponentType().getName(), impl.getComponentType().getExpGlobale(), impl.getComponentType().getExpMethod())));
					impl.getPorts().forEach(p->{
						configurationList.get(configurationList.size()-1).implementations.get(configurationList.get(configurationList.size()-1).implementations.size()-1)
						.ports.add(new DPort(new int[] {p.getIndex(),p.getXPos(),p.getYPos(),p.getWidth(),p.getHeight()},
								p.getName(),p.getType(),p.getCspExpression(),p.getCspExpressionModify()));	
					});
				});
				
				conf.getConnectors().forEach(c->{
					configurationList.get(configurationList.size()-1).connectors.add(new DConnector(c.getIndex(),c.getType(),c.getSource(),c.getDest()));
					configurationList.get(configurationList.size()-1).connectors.get(configurationList.get(configurationList.size()-1).connectors.size()-1)
					.inPort = new DPort(new int[] {c.inPort.getIndex(),c.inPort.getXPos(),c.inPort.getYPos(),c.inPort.getWidth(),c.inPort.getHeight()},
							c.inPort.getName(),c.inPort.getType(),c.inPort.getCspExpression(),c.inPort.getCspExpressionModify());
					
					configurationList.get(configurationList.size()-1).connectors.get(configurationList.get(configurationList.size()-1).connectors.size()-1)
					.outPort = new DPort(new int[] {c.outPort.getIndex(),c.outPort.getXPos(),c.outPort.getYPos(),c.outPort.getWidth(),c.outPort.getHeight()},
							c.outPort.getName(),c.outPort.getType(),c.outPort.getCspExpression(),c.outPort.getCspExpressionModify());
					configurationList.get(configurationList.size()-1).connectors.get(configurationList.get(configurationList.size()-1).connectors.size()-1)
					.Bandwidth = c.Bandwidth;
					
					configurationList.get(configurationList.size()-1).connectors.get(configurationList.get(configurationList.size()-1).connectors.size()-1)
					.formule = c.getFormule();
				});
			});
			
		
          data.getOtherConstraint().forEach(o->{
				OtherList.add(new DOther(o.type,o.fonction,o.attribute,o.op,o.value,o.NFattrbute));
			});
			///Alert.display("", "dfbdfb");
			
	          data.getTimedConstraint().forEach(t->{
	        	  
			timedList.add(new Timed(t.COMP1.getName(),t.COMP2.getName(),t.Meth1.MethodeName.getValue(),t.Meth2.getMethodeName().getValue(),t.event1,t.event2,t.Value,t.part1,t.part2,t.opC,t.opL));
					
					
				});
			
			ObjectOutputStream dataFile = new ObjectOutputStream(new FileOutputStream(dir+ fileSeparator +"data.obj"));
			dataFile.writeObject(this);
			dataFile.close();
			
			
		}
		catch (FileNotFoundException e) {
			// TODO: handle exception
		}
		catch (IOException e) {
			// TODO: handle exception
		}
		
	}
	
	public void load(String dir,Model data,TabPane window) {
		
		try {
			String fileSeparator = System.getProperty("file.separator");
			sfile = dir;
			System.out.println(sfile);
			
			ObjectInputStream dataFile = new ObjectInputStream(new FileInputStream(dir+ fileSeparator +"data.obj"));
			Data d = (Data) dataFile.readObject();
			configurationList2 = d.configurationList;
			dataFile.close();
	
			d.loadData(data,window);
			
			
		}
		catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException");
		}

		
	}
	
	public void loadData(Model data, TabPane window) {
		
		componentList.forEach(cp->{
			data.addComponentModel(cp.intData,cp.name);
			data.getComponentModel(cp.intData[0]).setExpGlobale(cp.expGlobale);
			data.getComponentModel(cp.intData[0]).setExpMethod(cp.expMethod);;
			cp.ports.forEach(p->{
				data.addPortModel(p.intData,new String[] {p.name,p.type,p.cspExpression.getExpression()	}, data.getComponentModel(cp.intData[0]));
				data.getPortModel(cp.intData[0]).setCspExpressionModify(p.cspExpressionModify);
			});
			cp.methods.forEach(m->{
				data.getComponentModel(cp.intData[0]).ADDMethode(new Methode(new SimpleStringProperty(m.name),m.ExecutionTime));
			});
			
		});
		
		configurationList.forEach(conf->{
			
			data.addConfigurationModel(conf.index,conf.name);
			data.getConfigurationModel(conf.index).fomule = conf.fomule;
			data.getConfigurationModel(conf.index).TextConfig = conf.TextConfig;
			SingleSelectionModel<Tab> singleselectionModel = window.getSelectionModel();
			
			singleselectionModel.select(window.getTabs().get(window.getTabs().size()-1));
	
		
			
			conf.implementations.forEach(impl->{
				        
				data.addImplementationModel(impl.intData,impl.name,data.getComponentModel(impl.componentType.intData[0]),data.getConfigurationModel(conf.index));
				impl.ports.forEach(p->{
					data.addPortModel(p.intData, new String[] {p.name,p.type,p.cspExpression.getExpression()},data.getImplementationModel(impl.intData[0]));
				});
			});
			
			conf.connectors.forEach(c->{
				data.addLinkModel(new int[] {c.index,c.type,c.src,c.dest,-2,-2,-2,-2},"",data.getConfigurationModel(conf.index),c.formule,
						c.Bandwidth,data.getPortModel(c.inPort.intData[0]),data.getPortModel(c.outPort.intData[0]));				
			});
			
		});
		if(timedList.size() != 0) {timedList.forEach(t ->{
			
			
			data.getTimedConstraint().add(new TimedConstraint(getcomp(data,t.COMP1),getcomp(data,t.COMP2),getmeth(getcomp(data,t.COMP1),t.Meth1),getmeth(getcomp(data,t.COMP2),t.Meth2),t.event1,t.event2,t.Value,t.opC,t.opL));
			
		});}
		if(OtherList.size() !=0) {OtherList.forEach(o ->{
			
			data.getOtherConstraint().add(new OtherConstraint(o.type,o.fonction,o.attribute,o.op,o.value));
		});}
	}
	
public void loadData2(Model data, TabPane window, Configuration f,int idex ,String name) {
		
	String fileSeparator = System.getProperty("file.separator");
	
	Data d = null;
	ObjectInputStream dataFile;
	try {
		dataFile = new ObjectInputStream(new FileInputStream(sfile+ fileSeparator +"data.obj"));
		 d = (Data) dataFile.readObject();
	dataFile.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		
		d.configurationList.forEach(conf->{
			
			if(conf.name.compareTo(f.getName())==0) {
			
			data.addConfigurationModel(idex,name);
			data.getConfigurationModel(idex).fomule = conf.fomule;
			data.getConfigurationModel(idex).TextConfig = conf.TextConfig;
			SingleSelectionModel<Tab> singleselectionModel = window.getSelectionModel();
			
			singleselectionModel.select(window.getTabs().get(window.getTabs().size()-1));
	
			
			
			conf.implementations.forEach(impl->{
				int id = data.getImplementationTail();    
				data.addImplementationModel(new int[] { id, 0, 0, 100, 100 },impl.name,data.getComponentModel(impl.componentType.intData[0]),data.getConfigurationModel(idex));
				impl.ports.forEach(p->{
					data.addPortModel(
							new int[] { data.getPortTail(),data.getImplementationProperty().get(id).getXPos() + 240,
									data.getImplementationProperty().get(id).getYPos() + data.getImplementationProperty().get(id).getPorts().size() * 25 + 15,
									100, 100 },
							new String[] { p.name,
									p.type, p.cspExpression.getExpression() },
							data.getImplementationProperty().get(id));
					
					
				});
					/*data.addPortModel(p.intData, new String[] {p.name,p.type,p.cspExpression.getExpression()},data.getImplementationModel(impl.intData[0]));
				});*/
			});
			
			conf.connectors.forEach(c->{
	//data.addLinkModel(new int[] {c.index,c.type,c.src,c.dest,-2,-2,-2,-2},"",data.getConfigurationModel(idex),c.formule,
	//c.Bandwidth,data.getPortModel(c.inPort.intData[0]),data.getPortModel(c.outPort.intData[0]));
	
	data.addLinkModel(
			new int[] { data.getLinkTail(), 1,
					getp(c.outPort.name,data.getConfigurationModel(idex)).getIndex(),getp(c.inPort.name,data.getConfigurationModel(idex)).getIndex(), -2, -2, -2, -2
					},
			"",data.getConfigurationModel(idex),c.formule,c.Bandwidth,getp(c.inPort.name,data.getConfigurationModel(idex)),getp(c.outPort.name,data.getConfigurationModel(idex)));
	
	
	
			});/**/
			
			}});
	}
	
	
	private class DComponent implements Serializable{
		
		
		public int[] intData = new int[5];
		public String name = new String();
		public  Csp expGlobale=new Csp("","");
		public String expMethod ;
		public ArrayList<DPort> ports = new ArrayList<DPort>();
		public ArrayList<DMethod> methods = new ArrayList<DMethod>();
		private static final long serialVersionUID = 1L;
	
		
		public DComponent(int[] intsIn,String nameIn, Csp expGlobal,String expMethod) {
			if (intsIn.length == 5) {
				intData = intsIn;
			}
			this.name = nameIn;
			this.expGlobale = expGlobal;
			this.expMethod = expMethod;			
		}

		
	}
	
	private class DPort implements Serializable{
	
	
		public int[] intData = new int[5];
		public String name = new String();
		public String type = new String();
		public Csp cspExpression=new Csp("","");
	    public Csp cspExpressionModify =new Csp("","");
	    public ArrayList<DPort> ports = new ArrayList<DPort>();
		private static final long serialVersionUID = 1L;
	    
	    public DPort(int[] intsIn,String name,String type,Csp cspExpression,Csp cspExpressionModify) {
	    	if (intsIn.length == 5) {
				intData = intsIn;
			}
	    	this.name = name;
	    	this.type = type;
	    	this.cspExpression = cspExpression;
	    	this.cspExpressionModify = cspExpressionModify;
	    	
	    }
		
		
	}
	
	private class DMethod implements Serializable {
		
		private String name;
		private int ExecutionTime;
		private static final long serialVersionUID = 1L;

		public DMethod(String name,int e) {
			this.name = name;
			this.ExecutionTime = e;
		}
	}
	
private class DOther implements Serializable {
	private String type;
	private static final long serialVersionUID = 1L;
	private String fonction ;
	private String attribute ;
	private String op ;
	private int value;
	private NFAttribute NFattrbute;
	public DOther(String type, String fonction, String attribute, String op, int value, NFAttribute nFattrbute) {
	
		this.type = type;
		this.fonction = fonction;
		this.attribute = attribute;
		this.op = op;
		this.value = value;
		NFattrbute = nFattrbute;
	}
		
	}
private class Timed implements Serializable {
	public String COMP1 ;  
	public String COMP2 ;
	public String Meth1;
	public String Meth2;
	 
	public String event1 ,event2;
	public int Value;
	public String part1;
	public String part2;
	public String opC ;
	public String opL;
	public Timed(String cOMP1, String cOMP2, String meth1, String meth2, String event1, String event2,
			int value, String part1, String part2, String opC, String opL) {
		super();
		COMP1 = cOMP1;
		COMP2 = cOMP2;
		Meth1 = meth1;
		Meth2 = meth2;
		this.event1 = event1;
		this.event2 = event2;
		Value = value;
		this.part1 = part1;
		this.part2 = part2;
		this.opC = opC;
		this.opL = opL;
	}
	
		
	}
	
	private class DConfiguration implements Serializable{
		public int index;
		public String name = new String();
		public ArrayList<DImplementation> implementations = new ArrayList<DImplementation>();
		public ArrayList<DConnector> connectors = new ArrayList<DConnector>();
	    public Csp fomule;
	    public String TextConfig ="";
		
		public DConfiguration(int index,String name) {
			this.index = index;
			this.name = name;
		}
	}
	
	private class DImplementation implements Serializable{
		public int[] intData = new int[5];
		public String name = new String();
		public ArrayList<DPort> ports = new ArrayList<DPort>();
		public DComponent componentType;
		
		public DImplementation(int[] intsIn, String nameIn,DComponent parent) {
			if (intsIn.length == 5) {
				intData = intsIn;
			}	
			name = nameIn;
			this.componentType = parent;

		}

		
		
	}
	
	private class DConnector implements Serializable{
		
		public int index;
		public int type;
		public int src;
		public int dest;
		public DPort outPort;
		public DPort inPort;
		public int Bandwidth;
		public Csp formule;
		
		public DConnector(int index,int type,int src,int dest) {
			this.index = index;
			this.type = type;
			this.src = src;
			this.dest = dest;
		}
		
	}
	public Port getp(String p, Configuration con) {
		
		for(int i=0 ;i< con.getImplementations().size();i++) {
			for(int j =0 ; j<con.getImplementations().get(i).getPorts().size();j++ ) {
				
				if(p.compareTo(con.getImplementations().get(i).getPorts().get(j).getName())== 0) { 
					//Alert.display("", data.getPortModel(i).getName()+"  "+ data.getPortModel(i).getIndex() );
					return con.getImplementations().get(i).getPorts().get(j);}
			}
		
				
			
			
		}

			
		
		return null;
		
	}
	public Component getcomp(Model data , String c) {
		for(int i = 0 ; i<data.getComponentTail();i++) {
			if(data.getComponentModel(i).getName().compareTo(c) == 0) return data.getComponentModel(i);
			
		}
		return null;
	}
	public Methode getmeth(Component c , String M) {
		for(int i = 0 ; i<c.getMethode().size();i++) {
			if(c.getMethode().get(i).getMethodeName().get().compareTo(M) == 0) return c.getMethode().get(i);
			
		}
		return null;
		
	}
	
	

}
