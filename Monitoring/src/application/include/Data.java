package application.include;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import monitoring.elements.Component;
import monitoring.elements.ComponentImplementation;
import monitoring.elements.Configuration;
import monitoring.elements.Connector;
import monitoring.elements.Csp;
import monitoring.elements.Methode;
import monitoring.elements.Port;
import nfattribute.NFAttribute;

public class Data implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7469028436759190642L;
	private ArrayList<DConfiguration> configurationList;
	private ArrayList<DComponent> componentList;


	
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
					componentList.get(componentList.size()-1).methods.add(new DMethod(m.MethodeName.get()));
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
	
	public void load(String dir,Model data) {
		
		try {
			String fileSeparator = System.getProperty("file.separator");
			
			
			ObjectInputStream dataFile = new ObjectInputStream(new FileInputStream(dir+ fileSeparator +"data.obj"));
			Data d = (Data) dataFile.readObject();
			dataFile.close();
	
			d.loadData(data);
			
			
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
	
	public void loadData(Model data) {
		componentList.forEach(cp->{
			data.addComponentModel(cp.intData,cp.name);
			data.getComponentModel(cp.intData[0]).setExpGlobale(cp.expGlobale);
			data.getComponentModel(cp.intData[0]).setExpMethod(cp.expMethod);;
			cp.ports.forEach(p->{
				data.addPortModel(p.intData,new String[] {p.name,p.type,p.cspExpression.getExpression()	}, data.getComponentModel(cp.intData[0]));
				data.getPortModel(cp.intData[0]).setCspExpressionModify(p.cspExpressionModify);
			});
			cp.methods.forEach(m->{
				data.getComponentModel(cp.intData[0]).ADDMethode(new Methode(new SimpleStringProperty(m.name)));
			});
			
		});
		
		configurationList.forEach(conf->{
			data.addConfigurationModel(conf.index,conf.name);
			data.getConfigurationModel(conf.index).fomule = conf.fomule;
			data.getConfigurationModel(conf.index).TextConfig = conf.TextConfig;
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
		private static final long serialVersionUID = 1L;

		public DMethod(String name) {
			this.name = name;
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
	
	

}
