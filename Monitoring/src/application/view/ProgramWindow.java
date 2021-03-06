package application.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import org.xml.sax.SAXException;

import application.include.Alert;
import application.include.Data;
import application.include.Model;
import application.include.Validator;
import application.objects.Arrow;
import application.objects.PortBlock;
import application.objects.ComponentBlock;
import application.objects.ImplementationBlock;
import application.objects.Label;
import application.objects.Link;
import application.objects.Multiplicity;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import monitoring.elements.ArchitectureElement;
import monitoring.elements.Component;
import monitoring.elements.Configuration;
import monitoring.elements.Reconfiguration;
import monitoring.elements.VerificationFDR;
import nfattribute.Automata;
import nfattribute.Sequence;
import nfattribute.VerificationNF;

//
public class ProgramWindow<MouseEvent> extends Stage {

	private static final EventType MouseEvent = null;
	final private double DEFAULT_HEIGHT = 720.0;
	final private double DEFAULT_WIDTH = 1280.0;
	private Model data;
	public ArchitectureElement elem;
	private int color = 0;
	public int i = 0;
 
	// Main Window elements
	  protected  ChoiceBox<Configuration> source;
	    protected  ChoiceBox<Configuration> target;
	private BorderPane root = new BorderPane();
	private ToolBar tools = new ToolBar();
	private SplitPane center = new SplitPane();
	private VBox top = new VBox();
	public Pane mainPanel = new Pane();
	public TabPane appPanel = new TabPane();
	public Label l1= new Label("Source :");
	public Label l2 = new Label("Target :");
	public Tab mainComponentTab = new Tab("Main Components");
	public ScrollPane mainComponentScroll = new ScrollPane();
	public Pane mainComponentTabPanel = new Pane();

	public ScrollPane consol = new ScrollPane();
	public Pane consolPanel = new Pane();
	private MenuBar menu = new MenuBar();

	// Define menu elements 
	private Menu file = new Menu("File");
	private Menu edit = new Menu("Edit");
	private Menu view = new Menu("View");
	private Menu exemples = new Menu("Exemples");
	private Menu newModel = new Menu("New ...");
	public MenuItem save = new MenuItem("Save...");
	public MenuItem load = new MenuItem("Load...");
	public MenuItem export = new MenuItem("Export...");
	public MenuItem clear = new MenuItem("Clear elements");
	public MenuItem clearLinks = new MenuItem("Clear links");
	public MenuItem newConfigMenu = new MenuItem("New Configuration");
	public MenuItem newComponentMenu = new MenuItem("New Component");
	public MenuItem newImplementMenu = new MenuItem("new Implementation");
	public Menu skins = new Menu("Skins...");
	public MenuItem normal = new MenuItem("Normal");
	public MenuItem Shema = new MenuItem("Shema");
	public MenuItem night = new MenuItem("Night Mode");
	public MenuItem h4ck3r = new MenuItem("h4ck3r m0d3");
	public MenuItem winxp = new MenuItem("Windows XP");
	public MenuItem undo = new MenuItem("Undo...");
	public MenuItem redo = new MenuItem("Redo...");
	public Menu hospitalExemple = new Menu("Hospital Management...");
	public MenuItem hospitalComponents = new MenuItem("Components");
	public MenuItem hospitalConfigurations = new MenuItem("Configurations");

	// Define tool panel elements
	public Button newComponent = new Button("Add component");
	public Button newConfiguration = new Button("Add configuration");
	public Button newImplementation = new Button("Add Implementation");
	public Button NFAttr = new Button("Non-Functional Attributes");
	public Button NFConstraint = new Button("Non-Functional Constraint");
	public SplitMenuButton verif = new SplitMenuButton();
	//public SplitMenuButton source = new SplitMenuButton();
	//public SplitMenuButton target = new SplitMenuButton();
	public MenuItem StructurelVerif = new MenuItem("Structurel Verification");
	public MenuItem fVerif = new MenuItem("Behavioral verification");
	public MenuItem nfVerif = new MenuItem("Non-Functional Verification");
	public MenuItem Conf = new MenuItem("Choose Configuration ");

	// define Tree
	private TreeView<TreeView> tree = new TreeView<TreeView>();
	private TreeItem mainTree = new TreeItem();
	private TreeItem confTree = new TreeItem();
	private TreeItem componentTree = new TreeItem();

	// public Button removePort = new Button("Delete...");
	// public Button newLink = new Button("New link...");
	public ToggleButton dragMode = new ToggleButton("");

	public ToggleButton linkMode = new ToggleButton("");
	// public Button undo = new Button("Undo...");
	// public Button redo = new Button("Redo...");

	public ProgramWindow(Model dataIn) {

		Stage ref = this;
		Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);

		ref.setTitle("MONTORING TOOL  ");
		data = dataIn;
		source = new ChoiceBox<Configuration>(	data.getConfigurationProperty());
		//source.setSelectionModel("S");
		target = new ChoiceBox<Configuration>(	data.getConfigurationProperty());
		this.setMinHeight(DEFAULT_HEIGHT);
		this.setMinWidth(DEFAULT_WIDTH);
		mainTree.setValue("Project");
		confTree.setValue("Configurations");
		componentTree.setValue("Main Components");

		top.getChildren().addAll(menu, tools);
		mainComponentScroll.setContent(appPanel);
		appPanel.getTabs().addAll(mainComponentTab);
		mainComponentTab.setContent(mainComponentTabPanel);

		consol.setContent(consolPanel);

		root.getStyleClass().add("root");
		center.getStyleClass().add("center");
		mainComponentScroll.getStyleClass().add("mainPanel");
		mainComponentTabPanel.getStyleClass().add("tabPanel");
		consol.getStyleClass().add("mainPanel");
		consolPanel.getStyleClass().add("tabPanel");

		SplitPane.setResizableWithParent(mainPanel, Boolean.TRUE);
		center.getItems().addAll(mainComponentScroll, consol);
		center.setOrientation(javafx.geometry.Orientation.VERTICAL);

		menu.getStyleClass().add("menuColors");
		file.getStyleClass().add("menuColors");
		edit.getStyleClass().add("menuColors");
		save.getStyleClass().add("menuColors");
		load.getStyleClass().add("menuColors");
		export.getStyleClass().add("menuColors");
		clear.getStyleClass().add("menuColors");
		clearLinks.getStyleClass().add("menuColors");
		skins.getStyleClass().add("menuColors");
		normal.getStyleClass().add("menuColors");
		Shema.getStyleClass().add("menuColors");
		night.getStyleClass().add("menuColors");
		h4ck3r.getStyleClass().add("menuColors");
		winxp.getStyleClass().add("menuColors");

		// Construct Menu bar
		file.getItems().addAll(newModel, save, load, export);
		edit.getItems().addAll(undo, redo, clear, clearLinks);
		skins.getItems().addAll(normal, night, h4ck3r, winxp);
		view.getItems().addAll(skins, Shema);
		hospitalExemple.getItems().addAll(hospitalComponents,hospitalConfigurations);
		exemples.getItems().add(hospitalExemple);
		newModel.getItems().addAll(newConfigMenu, newComponentMenu, newImplementMenu);
		menu.getMenus().addAll(file, edit, view,exemples);
		

		// Construct tool panel
		newComponent.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");
		newConfiguration.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");
		newImplementation.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");
		verif.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");
		source.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");
		target.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");
		NFAttr.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");
		NFConstraint.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");
		dragMode.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");
		linkMode.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");

		tools.getItems().add(newConfiguration);
		tools.getItems().add(newComponent);
		tools.getItems().add(newImplementation);
		tools.getItems().add(NFAttr);
		tools.getItems().add(NFConstraint);
		tools.getItems().add(dragMode);
		tools.getItems().add(linkMode);
		tools.getItems().add(verif);tools.getItems().add(l1); 
		tools.getItems().add(source);
		
		tools.getItems().add(l2);
		tools.getItems().add(target);
		
		

		// Button
		verif.setText("Check Architecture");
		//((Text) source).setText("Source Configuration");
		//target.setText("Target Configuration");
		verif.getItems().addAll(StructurelVerif, fVerif, nfVerif);

		// Creates a new configuration dialog upon click
		EventHandler<ActionEvent> newConfigurationEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				NewConfigurationWindow dialog = new NewConfigurationWindow(-1, data,appPanel);
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.show();
				e.consume();
			}
		};

		// Creates a new component dialog upon click
		EventHandler<ActionEvent> newComponentEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				NewComponentWindow dialog = new NewComponentWindow(-1, data);
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.show();
				e.consume();
			}
		};

		EventHandler<ActionEvent> newImplementationEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				NewImplementationWindow dialog = new NewImplementationWindow(-1, data, new ProgramWindow(dataIn));
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.show();
				e.consume();
			}
		};

		EventHandler<ActionEvent> NFEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				NFattributWindow dialog = new NFattributWindow( data);
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.show();
				e.consume();
			}
		};
		EventHandler<ActionEvent> NFCEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				NewConstrain dialog = new NewConstrain(data);
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.show();
				e.consume();
			}
		};

		// Creates a new link dialog upon click
		EventHandler<ActionEvent> toggleLinkEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.toggleLinkMode();
				dragMode.setSelected(!dragMode.isSelected());
			}
		};

		// Creates a new link dialog upon click
		EventHandler<ActionEvent> toggleDragEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.toggleLinkMode();
				linkMode.setSelected(!linkMode.isSelected());
			}
		};

		EventHandler<ActionEvent> ChooseConf = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				ReconfigurationWindow dialog = new ReconfigurationWindow(data);
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.show();
				e.consume();
			}
		};

		EventHandler<ActionEvent> verifEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				Text title = new Text("Structural verification report:\n");
				title.relocate(5, 5);
				consolPanel.getChildren().clear();
				i = 0;
				List<Label> labels = new ArrayList<Label>();
				data.getConfigurationProperty().forEach(conf -> {
					//System.out.println(conf.methodFormula(1));
					i += 30;
					boolean flag = true;
					String msg = "";

					try {
						data.saveXml(new File("conf.xml"), conf);
						Validator.validate("conf.xml", "model.xsd");
					} catch (SAXException | IOException e1) {
						flag = false;
						msg = e1.getMessage();
					}
					if (flag == true) {
						Label configName = new Label(conf.getName() + ":\n");
						Label message = new Label("Valid Configuration: ");
						configName.relocate(10, i + 20);
						message.relocate(20, i + 35);
						message.setFill(Color.GREEN);
						labels.add(configName);
						labels.add(message);

					} else {
						Label configName = new Label(conf.getName() + ":\n");
						Label message = new Label("Invalid Configuration: " + msg);
						configName.relocate(10, i + 20);
						message.relocate(20, i + 35);
						message.setFill(Color.RED);
						labels.add(configName);
						labels.add(message);

					}
				});
				consolPanel.getChildren().addAll(title);
				consolPanel.getChildren().addAll(labels);
			}

		};
		EventHandler<ActionEvent> FDRverif = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				int m = 0;
				consolPanel.getChildren().clear();
				ArrayList<String> resultat = new ArrayList<String>();
				ArrayList<String> resultat2 = new ArrayList<String>();
			Configuration c = source.getValue();
			Configuration t = target.getValue();
	
			VerificationFDR F = new VerificationFDR ();
			if(c != null) {
			for(int i =0;i<c.getImplementations().size();i++) {
				Component comp = c.getImplementations().get(i).getComponentType();
				resultat.add(F.valideCspComponent2(comp)+c.getImplementations().get(i).getName()+" :instance of "+comp.getName()+" ]");
			}
			
			 m = consolFDR(resultat,c,0);
			 m= conf(c, m);
			}
			if(t != null) {
			for(int i =0;i<t.getImplementations().size();i++) {
				Component comp = t.getImplementations().get(i).getComponentType();
				resultat2.add(F.valideCspComponent2(comp)+c.getImplementations().get(i).getName()+" :instance of "+comp.getName()+" ]");
			}
			
			 m= consolFDR(resultat2,t ,m);
			 m = conf(t,m);
			 
			Reconfiguration re = new Reconfiguration(c,t);
			m = reconf(re ,m);
			}
			}
		};

		// Clears the main panel upon click
		EventHandler<ActionEvent> clearEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.saveUndoState();
				data.clearRedoState();
				data.clear();
				mainPanel.getChildren().clear();
			}
		};

		EventHandler<ActionEvent> clearLinksEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.saveUndoState();
				data.clearRedoState();
				removeLinks();
				data.clearLinks();
			}
		};

		EventHandler<ActionEvent> undoEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.setUndoState();
				data.saveRedoState();
				data.clear();
				mainPanel.getChildren().clear();
				data.undo();
				if (data.isUndoEmpty())
					undo.setDisable(true);
			}
		};

		EventHandler<ActionEvent> redoEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.setRedoState();
				data.saveUndoState();
				data.clear();
				mainPanel.getChildren().clear();
				data.redo();
				if (data.isRedoEmpty())
					redo.setDisable(true);
			}
		};

		// Saves the current state of the Model
		EventHandler<ActionEvent> saveEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (data.saved == false) {
					NewProjectWindow dialog = new NewProjectWindow(-1, data);
					dialog.initModality(Modality.APPLICATION_MODAL);
					dialog.show();
				} else {
					Data d = new Data();
					d.save(data.project_dir, data);

				}
				e.consume();
			}
		};

		// Loads the saved Model state
		EventHandler<ActionEvent> loadEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				JFileChooser chooser = new JFileChooser();
				String choosertitle = "";

				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle(choosertitle);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				//
				// disable the "All files" option.
				//
				chooser.setAcceptAllFileFilterUsed(false);
				//
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					data.project_dir = chooser.getSelectedFile() + "";

					data.saved = true;
					mainComponentTabPanel.getChildren().clear();
				//	((Pane) appPanel.getTabs().get(1).getContent()).getChildren().clear();
					for (int i = 1; i < appPanel.getTabs().size(); i++) {
						appPanel.getTabs().remove(i);
					}

					Data d = new Data();
					String dir = data.project_dir;
					data.getConfigurationProperty().clear();
					d.load(dir, data,appPanel);
					System.out.println(dir);
				}
				e.consume();
			}
		};

		EventHandler<ActionEvent> loadExp1 = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				
					data.saved = true;
					mainComponentTabPanel.getChildren().clear();
					for (int i = 1; i < appPanel.getTabs().size(); i++) {
						appPanel.getTabs().remove(i);
					}
					Data d = new Data();
					data.getConfigurationProperty().clear();
					d.load("C:\\Users\\ramzi\\git\\monitoring\\Monitoring\\Exemple1\\Hospital",data,appPanel);
				
				e.consume();
			}
		};
		EventHandler<ActionEvent> loadExp2 = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				
					data.saved = true;
					mainComponentTabPanel.getChildren().clear();
					for (int i = 1; i < appPanel.getTabs().size(); i++) {
						appPanel.getTabs().remove(i);
					}
					Data d = new Data();
					data.getConfigurationProperty().clear();
					d.load("C:\\Users\\ramzi\\git\\monitoring\\Monitoring\\Exemple2\\Hospital",data,appPanel);
				
				e.consume();
			}
		};

		// Export the main panel to a print dialog
		EventHandler<ActionEvent> exportEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				PrinterJob job = PrinterJob.createPrinterJob();
				PageLayout layout = Printer.getDefaultPrinter().createPageLayout(Paper.TABLOID,
						PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
				if (job != null) {
					job.showPrintDialog(ref);
					job.getJobSettings().setPageLayout(layout);

					job.printPage(mainPanel);
					job.endJob();
				}
				e.consume();
			}
		};

		EventHandler<ActionEvent> normalEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				color = 0;
				data.prepPortBlocks();
				becomeStylish(scene);
				applyCss();
				data.refreshPortBlocks();
				data.refreshLines();
			}
		};
		EventHandler<ActionEvent> nonfonctionelverif = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				consolPanel.getChildren().clear();
			VerificationNF  nf = new VerificationNF(data);
			Configuration c = source.getValue();
			ArrayList<String> resultat = nf.CheckOtherConstraint(c);
			consolPanel.getChildren().clear();
			consolother(resultat, 0);
			
			
			}
		};
		EventHandler<ActionEvent> ShemaEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				ShemaWindow dialog = new ShemaWindow();
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.show();

			}
		};

		EventHandler<ActionEvent> nightEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				color = 1;
				data.prepPortBlocks();
				becomeStylish(scene);
				applyCss();
				data.refreshPortBlocks();
				data.refreshLines();
			}
		};

		EventHandler<ActionEvent> h4ck3rEvent = new EventHandler<ActionEvent>() {
			/* CRASH */ @Override
			public void handle(ActionEvent e) {
				color = 2;
				data.prepPortBlocks();
				becomeStylish(scene);
				applyCss();
				data.refreshPortBlocks();
				data.refreshLines();
			}
		};

		EventHandler<ActionEvent> winXPEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				color = 3;
				data.prepPortBlocks();
				becomeStylish(scene);
				applyCss();
				data.refreshPortBlocks();
				data.refreshLines();
			}
		};
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		EventHandler<ActionEvent> Bilan = new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent e) {
				int m= 0;
				ProgressDemo demo = new ProgressDemo() ;
				demo.initModality(Modality.APPLICATION_MODAL);
				demo.show();
			Text title2 = new Text("Behavioral verification report:\n");
			title2.setFill(Color.BLUE);
			Text title1 = new Text("Structural verification report:\n");
			title1.setFill(Color.BLUE);
			Text title3 = new Text("Non-Fonctional verification report:\n");
			title3.setFill(Color.BLUE);
			
			/*********************************************************************************************************/
			
			title1.relocate(20,m+10);
			consolPanel.getChildren().clear();
			i = m;
			List<Label> labels = new ArrayList<Label>();
			data.getConfigurationProperty().forEach(conf -> {
				//System.out.println(conf.methodFormula(1));
				i += 30;
				boolean flag = true;
				String msg = "";

				try {
					data.saveXml(new File("conf.xml"), conf);
					Validator.validate("conf.xml", "model.xsd");
				} catch (SAXException | IOException e1) {
					flag = false;
					msg = e1.getMessage();
				}
				if (flag == true) {
					Label configName = new Label(conf.getName() + ":\n");
					Label message = new Label("Valid Configuration: ");
					configName.relocate(20, i + 20);
					message.relocate(20, i + 35);
					message.setFill(Color.GREEN);
					labels.add(configName);
					labels.add(message);

				} else {
					Label configName = new Label(conf.getName() + ":\n");
					Label message = new Label("Invalid Configuration: " + msg);
					configName.relocate(20, i + 20);
					message.relocate(20, i + 35);
					message.setFill(Color.RED);
					labels.add(configName);
					labels.add(message);

				}
			});
			m = i;
			consolPanel.getChildren().addAll(title1);
			consolPanel.getChildren().addAll(labels);
			
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			m+= 60;
			title2.relocate(20,m+10);
			consolPanel.getChildren().add(title2);
			//consolPanel.getChildren().clear();
			ArrayList<String> resultat = new ArrayList<String>();
			ArrayList<String> resultat2 = new ArrayList<String>();
		Configuration c = source.getValue();
		Configuration t = target.getValue();
m+=20;
		VerificationFDR F = new VerificationFDR ();
		if(c != null) {
		for(int i =0;i<c.getImplementations().size();i++) {
			Component comp = c.getImplementations().get(i).getComponentType();
			resultat.add(F.valideCspComponent2(comp)+" [ "+c.getImplementations().get(i).getName()+" :instance of "+comp.getName()+" ]");
		}
		
		 m = consolFDR(resultat,c,m);
		 m= conf(c, m);
		}
		if(t != null) {
		for(int i =0;i<t.getImplementations().size();i++) {
			Component comp = t.getImplementations().get(i).getComponentType();
			resultat2.add(F.valideCspComponent2(comp)+" [ "+t.getImplementations().get(i).getName()+" :instance of "+comp.getName()+" ]");
		}
		
		 m= consolFDR(resultat2,t ,m);
		 m = conf(t,m);
		 
		Reconfiguration re = new Reconfiguration(c,t);
		m = reconf(re ,m);
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
		
		VerificationNF  nf = new VerificationNF(data);
		ArrayList<String> resultat6 = nf.CheckOtherConstraint(c);
		m+= 30;
		title3.relocate(20,m+30);
		consolPanel.getChildren().add(title3);
		consolother(resultat6,m);
		if(t != null) {
			 nf = new VerificationNF(data);
			ArrayList<String> resultat7 = nf.CheckOtherConstraint(t);
			m+= 60;
			title3.relocate(20,m+10);
			//consolPanel.getChildren().add(title3);
			m+=30;
			//consolother(resultat6,m);

		}
			
		
				demo.close();
			}
			
		};
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		tree.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				try {
					MultipleSelectionModel selectionModel = tree.getSelectionModel();
					TreeItem selectedTreeItem = (TreeItem) selectionModel.getSelectedItem();

					appPanel.getTabs().forEach(t -> {
						if (t.getText() == selectedTreeItem.getValue()) {
							SingleSelectionModel<Tab> singleselectionModel = appPanel.getSelectionModel();
							singleselectionModel.select(t);
						}
					});
                            data.getComponentProperty().forEach(c->{
                            	if(c.getName().equals(selectedTreeItem.getValue())) {
                            		NewComponentWindow dialog = new NewComponentWindow(c.getIndex(), data);
                        			dialog.initModality(Modality.APPLICATION_MODAL);
                        			dialog.show();
                        			event.consume();
                            	}
                            });
                            
                            data.getImplementationProperty().forEach(c->{
                            	if(c.getName().equals(selectedTreeItem.getValue())) {
                            		NewImplementationWindow dialog = new NewImplementationWindow(c.getIndex(), data,new ProgramWindow<>(data));
                        			dialog.initModality(Modality.APPLICATION_MODAL);
                        			dialog.show();
                        			event.consume();
                            	}
                            });
				} catch (Exception e) {

				}

			}
		});

		// Apply handlers
		verif.setOnAction(Bilan);
		StructurelVerif.setOnAction(verifEvent);
		newComponent.setOnAction(newComponentEvent);
		newImplementation.setOnAction(newImplementationEvent);
		newConfiguration.setOnAction(newConfigurationEvent);
		NFAttr.setOnAction(NFEvent);
		NFConstraint.setOnAction(NFCEvent);
		Conf.setOnAction(ChooseConf);
		linkMode.setOnAction(toggleLinkEvent);
		dragMode.setOnAction(toggleDragEvent);
		nfVerif.setOnAction(nonfonctionelverif);

		// menu
		newComponentMenu.setOnAction(newComponentEvent);
		newImplementMenu.setOnAction(newImplementationEvent);
		newConfigMenu.setOnAction(newConfigurationEvent);

		Image dragIcon = new Image(getClass().getResourceAsStream("/application/include/drag.png"));
		ImageView i1 = new ImageView(dragIcon);
		i1.setFitWidth(25);
		i1.setFitHeight(25);

		Image linkIcon = new Image(getClass().getResourceAsStream("/application/include/link.png"));
		ImageView i2 = new ImageView(linkIcon);
		i2.setFitWidth(25);
		i2.setFitHeight(25);

		dragMode.setGraphic(i1);
		linkMode.setGraphic(i2);

		clear.setOnAction(clearEvent);
		clearLinks.setOnAction(clearLinksEvent);
		undo.setOnAction(undoEvent);
		redo.setOnAction(redoEvent);
		save.setOnAction(saveEvent);
		load.setOnAction(loadEvent);
		export.setOnAction(exportEvent);
		Shema.setOnAction(ShemaEvent);
		normal.setOnAction(normalEvent);
		night.setOnAction(nightEvent);
		h4ck3r.setOnAction(h4ck3rEvent);
		winxp.setOnAction(winXPEvent);
		fVerif.setOnAction(FDRverif);
		hospitalComponents.setOnAction(loadExp1);
		hospitalConfigurations.setOnAction(loadExp2);

		redo.setDisable(true);
		undo.setDisable(true);

		// Place items on stage
		mainTree.getChildren().addAll(componentTree, confTree);
		tree.setRoot(mainTree);
		root.setTop(top);
		root.setLeft(tree);
		root.setCenter(center);

		root.getCenter().getStyleClass().add("pad");

		// mainPanel.prefHeightProperty().bind(scene.heightProperty());
		// mainPanel.prefWidthProperty().bind(scene.widthProperty());

		scene.getStylesheets().add(getClass().getResource("/application/include/application.css").toExternalForm());
		scene.getStylesheets().add(getClass().getResource("/application/include/normal.css").toExternalForm());
		this.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				
			}
		});
        
		this.setScene(scene);
	}

	public void becomeStylish(Scene scene) {
		scene.getStylesheets().clear();
		scene.getStylesheets().add(getClass().getResource("/application/include/application.css").toExternalForm());
		switch (color) {
		case 0:
			scene.getStylesheets().add(getClass().getResource("/application/include/normal.css").toExternalForm());
			break;
		case 1:
			scene.getStylesheets().add(getClass().getResource("/application/include/night.css").toExternalForm());
			break;
		case 2:
			scene.getStylesheets().add(getClass().getResource("/application/include/h4ck3r.css").toExternalForm());
			break;
		case 3:
			scene.getStylesheets().add(getClass().getResource("/application/include/WinXP.css").toExternalForm());
			break;
		}
	}

	public void clear() {
		mainPanel.getChildren().clear();
	}

	public void addPort(PortBlock in) {
		if (mainComponentTab.isSelected()) {
			mainComponentTabPanel.getChildren().add(in);
		} else {
			appPanel.getTabs().forEach(t -> {
				if (t.isSelected() && !t.equals(appPanel.getTabs().get(0))) {

					((Pane) t.getContent()).getChildren().add(in);
				}
			});

		}
		// mainPanel.getChildren().add(in);
	}

	public void addConfiguration(Tab in) {

		appPanel.getTabs().add(in);
		Pane p = new Pane();
		p.getStyleClass().add("tabPanel");
		in.setContent(p);
		TreeItem conf = new TreeItem();
		conf.setValue(in.getText());
		confTree.getChildren().add(conf);

		// mainPanel.getChildren().add(in);
	}

	public void addComponent(ComponentBlock in) {

		mainComponentTabPanel.getChildren().add(in);
		TreeItem comp = new TreeItem();
		comp.setValue(in.getName());
		componentTree.getChildren().add(comp);

		// mainPanel.getChildren().add(in);
	}

	public void addImplementation(ImplementationBlock in) {
		if (appPanel.getTabs().get(0).isSelected()) {
			((Pane) appPanel.getTabs().get(1).getContent()).getChildren().add(in);
			TreeItem imp = new TreeItem();
			imp.setValue(in.getName());
			((TreeItem) confTree.getChildren().get(0)).getChildren().add(imp);
		}
		for (int i = 1; i < appPanel.getTabs().size(); i++) {
			if (appPanel.getTabs().get(i).isSelected()) {

				((Pane) appPanel.getTabs().get(i).getContent()).getChildren().add(in);
				TreeItem imp = new TreeItem();
				imp.setValue(in.getName());
				((TreeItem) confTree.getChildren().get(i - 1)).getChildren().add(imp);
				return;
			}
		}
		;
		// mainPanel.getChildren().add(in);
	}

	public void removePort(PortBlock in) {
		if (mainComponentTab.isSelected()) {
			mainComponentTabPanel.getChildren().remove(in);
		} else {
			appPanel.getTabs().forEach(t -> {
				if (t.isSelected() && !t.equals(appPanel.getTabs().get(0))) {

					((Pane) t.getContent()).getChildren().remove(in);
				}
			});

		}
		// mainPanel.getChildren().remove(in);
	}

	public void removeConfiguration(int in) {
		appPanel.getTabs().remove(in);
	}

	public void removeComponent(ComponentBlock in) {
		mainComponentTabPanel.getChildren().remove(in);
	}

	public void removeImplementation(ImplementationBlock in) {
		appPanel.getTabs().forEach(t -> {
			if (t.isSelected() && !t.equals(appPanel.getTabs().get(0))) {

				((Pane) t.getContent()).getChildren().remove(in);
			}
		});
	}

	public void addLink(Link in) {
		if (mainComponentTab.isSelected()) {
			mainComponentTabPanel.getChildren().add(in);
		} else {
			appPanel.getTabs().forEach(t -> {
				if (t.isSelected()) {

					((Pane) t.getContent()).getChildren().add(in);
				}
			});
		}
		// mainPanel.getChildren().add(in);
	}

	public void addArrow(Arrow in) {
		if (mainComponentTab.isSelected()) {
			mainComponentTabPanel.getChildren().add(in);
		} else {
			appPanel.getTabs().forEach(t -> {
				if (t.isSelected() && !t.equals(appPanel.getTabs().get(0))) {

					((Pane) t.getContent()).getChildren().add(in);
				}
			});
		}
		// mainPanel.getChildren().add(in);
	}

	/**
	 * Add a Multiplicity to the main panel.
	 * 
	 * @param mult The multiplicity to be added
	 */
	public void addMultiplicity(Multiplicity mult) {
		mainPanel.getChildren().add(mult);

	}

	/**
	 * Add a Label to the main panel.
	 * 
	 * @param mult The multiplicity to be added
	 */
	public void addLabel(Label label) {
		mainPanel.getChildren().add(label);

	}

	/**
	 * Removes the given element from the main panel.
	 * 
	 * @param in The element to be removed
	 */
	public void remove(Link in) {
		if (mainComponentTab.isSelected()) {
			mainComponentTabPanel.getChildren().remove(in);
		} else {
			appPanel.getTabs().forEach(t -> {
				if (t.isSelected() && !t.equals(appPanel.getTabs().get(0))) {

					((Pane) t.getContent()).getChildren().remove(in);
				}
			});

		}
		// mainPanel.getChildren().remove(in);
	}

	/**
	 * Hands the model this window temporarily so it can properly remove all Links.
	 * 
	 * (run before removing Links from model)
	 * 
	 */
	public void removeLinks() {
		data.assistRemoveLinks(this);
	}

	/**
	 * Removes the given element from the main panel.
	 * 
	 * @param in The element to be removed
	 */
	public void remove(Arrow in) {
		if (mainComponentTab.isSelected()) {
			mainComponentTabPanel.getChildren().remove(in);
		} else {
			appPanel.getTabs().forEach(t -> {
				if (t.isSelected() && !t.equals(appPanel.getTabs().get(0))) {

					((Pane) t.getContent()).getChildren().remove(in);
				}
			});

		}
		// mainPanel.getChildren().remove(in);
	}

	/**
	 * Removes the given element from the main panel.
	 * 
	 * @param in The element to be removed
	 */
	public void remove(Multiplicity in) {
		mainPanel.getChildren().remove(in);

	}

	/**
	 * Removes the given element from the main panel.
	 * 
	 * @param in The element to be removed
	 */
	public void remove(Label in) {
		mainPanel.getChildren().remove(in);
	}

	/**
	 * Refreshes the main panel's layout information.
	 */
	public void applyCss() {
		mainPanel.applyCss();
		mainPanel.layout();
	}

	public void chooseFile() {
		String choosertitle = "";
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle(choosertitle);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//
		// disable the "All files" option.
		//
		chooser.setAcceptAllFileFilterUsed(false);
		//
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

			System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
			System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
			boolean success = (new File(chooser.getSelectedFile() + "dir")).mkdirs();
		} else {
			System.out.println("No Selection ");
		}
	}
	
	public void consolother(ArrayList<String> resultat, int m ) {

		Text title = new Text("Non-Fonctional verification report:\n");
		title.relocate(20, m+35);
		m+=30;
		
		
		List<Label> labels2 = new ArrayList<Label>();
		List<Label> labels = new ArrayList<Label>();
		for(int i=0 ;i<resultat.size();i++) {
			m+= 90;
			if(resultat.get(i).startsWith("V")) {
				
				
				Label message = new Label(resultat.get(i));
				message.relocate(20, m + 35);
				message.setFill(Color.GREEN);
				
				labels.add(message);
				
				
			}else {
				
				Label message = new Label(resultat.get(i));
				message.relocate(20, m + 35);
				message.setFill(Color.RED);
				
				labels.add(message);
				
				
			}
			
			
		}
		
		
	
		m+= 70;
		ArrayList<Text> automataResult = new ArrayList<Text>();
		ArrayList<Text> confTitle = new ArrayList<Text>();
		ArrayList<ArrayList<Sequence>> tmp = new ArrayList<ArrayList<Sequence>>();
		for (int j = 0; j < data.getConfigurationProperty().size(); j++) {
			confTitle.add(new Text("======= "+data.getConfigurationProperty().get(j).getName()+": =======")) ;
			confTitle.get(j).relocate(20,m+ 5);
			m+= 10;
			int sqnumber =0;
			Automata at = new Automata();
			VerificationNF nf = new VerificationNF(new Model());
			ArrayList<Sequence> sq = at.generateConfSequences(data.getConfigurationProperty().get(j));
			ArrayList<Sequence> sq2 = nf.generateAllSequence(sq);
			tmp.add(sq2);
		                                                                                                                                                                                                                                                           	sqnumber *=27;                                                                                                                                                                                                                                                               sqnumber*= 27 ;
			sqnumber =sq2.size();
				
				if(sqnumber > 0) {
				automataResult.add(new Text("\nSyntax Tree generated \n\nAutomata Generated \nAutomata Sequences number: "+sqnumber));
				automataResult.get(j).relocate(20,m+ 5);
				automataResult.get(j).setFill(Color.GREEN);
				m+= 70;
				}
				else {
					automataResult.add(new Text("\n No Syntax Tree \n\n No Automata \nAutomata Sequences number: "+sqnumber));
					automataResult.get(j).relocate(20,m+ 5);
					automataResult.get(j).setFill(Color.RED);
					m+= 70;
				}
		}
		
		
	m+=20;
	Text valTl = new Text("==== Timed Constraints Validity ==== :\n");
	valTl.relocate(20,m+ 5);
	m+= 30;
	ArrayList<Text> automataResult2 = new ArrayList<Text>();
	ArrayList<Text> confTitle2 = new ArrayList<Text>();
	for (int j = 0; j < data.getConfigurationProperty().size(); j++) {
		confTitle2.add(new Text("======= "+data.getConfigurationProperty().get(j).getName()+": =======")) ;
		confTitle2.get(j).relocate(20,m+ 5);
		m+= 20;
		Automata at = new Automata();
		VerificationNF nf = new VerificationNF(new Model());
		
		ArrayList<Sequence> sq2 = tmp.get(j);
		ArrayList<String> r = nf.SuperAlgo(data.getTimedConstraint(), sq2, data);
		for (int a = 0; a < r.size(); a++) {
			if(r.get(a).contains("Valid")) {
				automataResult2.add(new Text(r.get(a)));
				automataResult2.get(j).relocate(20,m+ 5);
				automataResult2.get(j).setFill(Color.GREEN);
				
			}
			else {
				automataResult2.add(new Text(r.get(a)));
				automataResult2.get(j).relocate(20,m+ 5);
				automataResult2.get(j).setFill(Color.RED);
			}
			m+= 50;

		}
				
			
	}
	
		
		
	
		//consolPanel.getChildren().addAll(title);
		consolPanel.getChildren().addAll(labels);
		//consolPanel.getChildren().addAll(title2);
		consolPanel.getChildren().addAll(labels2);
		consolPanel.getChildren().addAll(confTitle);
		consolPanel.getChildren().addAll(automataResult);
		consolPanel.getChildren().addAll(confTitle2);
		consolPanel.getChildren().addAll(automataResult2);
		consolPanel.getChildren().addAll(valTl);
	}
	public int consolFDR(ArrayList<String> resultat ,Configuration s,int pos) {
         int m = pos;
	//	Text title = new Text("Behavioral verification report:\n");
		Text title2 = new Text("=======  Configuration :"+s.getName()+" ========");
//		 title.relocate(20,m +35);
		// m+=20;
		title2.relocate(20,m +35);
		//consolPanel.getChildren().clear();
		
		List<Label> labels = new ArrayList<Label>();
		for(int i=0 ;i<resultat.size();i++) {
			m+= 30;
			if(resultat.get(i).startsWith("V")) {
				
				
				Label message = new Label(resultat.get(i));
				message.relocate(20, m + 35);
				message.setFill(Color.GREEN);
				
				labels.add(message);
				
				
			}else {
				
				Label message = new Label(resultat.get(i));
				message.relocate(20, m + 35);
				message.setFill(Color.RED);
				
				labels.add(message);
				
				
			}
			
			
		}
		
		
	
		//if(pos == 0) consolPanel.getChildren().addAll(title);
		consolPanel.getChildren().addAll(title2);
		consolPanel.getChildren().addAll(labels);
		return m;
	}
	public int reconf(Reconfiguration r,int m) {
		try {
			VerificationFDR f = new VerificationFDR ();
		String res = f.reconfig(r);
		Text title2 = new Text("\n======================================================================================================================================================");
		title2.relocate(20,m +35);
		List<Label> labels = new ArrayList<Label>();
		
			m+= 30;
			if(res.startsWith("V")) {
				
				
				Label message = new Label(res);
				message.relocate(20, m + 35);
				message.setFill(Color.GREEN);
				
				labels.add(message);
				
				
			}else {
				
				Label message = new Label(res);
				message.relocate(20, m + 35);
				message.setFill(Color.RED);
				
				labels.add(message);
				
				
			}
			
			consolPanel.getChildren().addAll(labels);
			return m+30;
			
		
		
		
		
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return m+30;
	}
	
	public int conf(Configuration conf,int m) {
		VerificationFDR f = new VerificationFDR ();
		ArrayList<String> methode = f.ValidateConfiguration(conf);
		for(int k =0 ; k<methode.size();k++) { System.out.println("\n"+methode.get(k)+"\n");}
		ArrayList<String> resultat = f.ValidateConfiguration2(conf);
Text title2 = new Text("\n Configuration Behavior  : "+conf.getName()+" ");
m+=35;
title2.relocate(20,m +35);
List<Label> labels = new ArrayList<Label>();

		m+= 30;
		for(int i=0 ;i<resultat.size();i++) {
			m+= 30;
			if(resultat.get(i).startsWith("V")) {
				
				
				Label message = new Label(resultat.get(i));
				message.relocate(20, m + 35);
				message.setFill(Color.GREEN);
				
				labels.add(message);
				
				
			}else {
				
				Label message = new Label(resultat.get(i));
				message.relocate(20, m + 35);
				message.setFill(Color.RED);
				
				labels.add(message);
				
				
			}
			
			
		}
		consolPanel.getChildren().addAll(title2);
		consolPanel.getChildren().addAll(labels);
		return m+30;
		
	}
	
	
	
	
	
	public void niv1(int p) {
		int m = p;
		m+=20;
		consolPanel.getChildren().clear();
		ArrayList<String> resultat = new ArrayList<String>();
		ArrayList<String> resultat2 = new ArrayList<String>();
	Configuration c = source.getValue();if(c == null ) { Alert.display("Warning", "Configuration source is empty");} 
	Configuration t = target.getValue();

	VerificationFDR F = new VerificationFDR ();
	if(c != null) {
	for(int i =0;i<c.getImplementations().size();i++) {
		Component comp = c.getImplementations().get(i).getComponentType();
		resultat.add(F.valideCspComponent2(comp)+" [instance : "+c.getImplementations().get(i).getName()+" ]");
	}
	
	 m = consolFDR(resultat,c,m);
	 m= conf(c, m);
	}
	if(t != null) {
	for(int i =0;i<t.getImplementations().size();i++) {
		Component comp = t.getImplementations().get(i).getComponentType();
		resultat2.add(F.valideCspComponent2(comp)+" [instance : "+t.getImplementations().get(i).getName()+" ]");
	}
	
	 m= consolFDR(resultat2,t ,m);
	 m = conf(t,m);
	 
	Reconfiguration re = new Reconfiguration(c,t);
	m = reconf(re ,m);
	}
	}
	
	
	
}
