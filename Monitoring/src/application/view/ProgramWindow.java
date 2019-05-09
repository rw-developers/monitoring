package application.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;

import application.include.Alert;
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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
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

public class ProgramWindow<MouseEvent> extends Stage {

	private static final EventType MouseEvent = null;
	final private double DEFAULT_HEIGHT = 720.0;
	final private double DEFAULT_WIDTH = 1280.0;
	private Model data;
	public ArchitectureElement elem;
	private int color = 0;
	public int i = 0;

	// Main Window elements
	private BorderPane root = new BorderPane();
	private ToolBar tools = new ToolBar();
	private SplitPane center = new SplitPane();
	private VBox top = new VBox();
	public Pane mainPanel = new Pane();
	public TabPane appPanel = new TabPane();
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
	public MenuItem night = new MenuItem("Night Mode");
	public MenuItem h4ck3r = new MenuItem("h4ck3r m0d3");
	public MenuItem winxp = new MenuItem("Windows XP");
	public MenuItem undo = new MenuItem("Undo...");
	public MenuItem redo = new MenuItem("Redo...");

	// Define tool panel elements
	public Button newComponent = new Button("Add component");
	public Button newConfiguration = new Button("Add configuration");
	public Button newImplementation = new Button("Add Implementation");
	public Button verif = new Button("Check Architecture");

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

		ref.setTitle("CheckIt [beta version]");
		data = dataIn;
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
		night.getStyleClass().add("menuColors");
		h4ck3r.getStyleClass().add("menuColors");
		winxp.getStyleClass().add("menuColors");

		// Construct Menu bar
		file.getItems().addAll(newModel, save, load, export);
		edit.getItems().addAll(undo, redo, clear, clearLinks);
		skins.getItems().addAll(normal, night, h4ck3r, winxp);
		view.getItems().add(skins);
		newModel.getItems().addAll(newConfigMenu, newComponentMenu, newImplementMenu);
		menu.getMenus().addAll(file, edit, view);

		// Construct tool panel
		newComponent.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");
		newConfiguration.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");
		newImplementation.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");
		verif.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");
		dragMode.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");
		linkMode.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");

		tools.getItems().add(newConfiguration);
		tools.getItems().add(newComponent);
		tools.getItems().add(newImplementation);
		tools.getItems().add(verif);
		tools.getItems().add(dragMode);
		tools.getItems().add(linkMode);

		// Creates a new configuration dialog upon click
		EventHandler<ActionEvent> newConfigurationEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				NewConfigurationWindow dialog = new NewConfigurationWindow(-1, data);
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

		EventHandler<ActionEvent> verifEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Text title = new Text("Structural verification report:\n");
				title.relocate(5, 5);
				consolPanel.getChildren().clear();
				i = 0;
				List<Label> labels = new ArrayList<Label>();
				data.getConfigurationProperty().forEach(conf -> {
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
				FileChooser dialog = new FileChooser();
				dialog.setTitle("Save file...");
				File file = dialog.showSaveDialog(ref);
				if (file != null) {
					try {
						data.save(file);
					} catch (IOException ex) {
						System.err.println("IO Failure: " + ex);
					}
				}
				e.consume();
			}
		};

		// Loads the saved Model state
		EventHandler<ActionEvent> loadEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				FileChooser dialog = new FileChooser();
				dialog.setTitle("Open UML file...");
				File file = dialog.showOpenDialog(ref);

				if (file != null) {
					mainComponentTabPanel.getChildren().clear();
					((Pane) appPanel.getTabs().get(1).getContent()).getChildren().clear();
					for (int i = 2; i < appPanel.getTabs().size(); i++) {
						appPanel.getTabs().remove(i);
					}
					data.clear();
					try {
						data.load(file);
					} catch (IOException ex) {
						System.err.println("IO Failure: " + ex);
					}
				}
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
//                            data.getComponentProperty().forEach(c->{
//                            	if(c.getName().equals(selectedTreeItem.getValue())) {
//                            		NewComponentWindow dialog = new NewComponentWindow(c.getIndex(), data);
//                        			dialog.initModality(Modality.APPLICATION_MODAL);
//                        			dialog.show();
//                        			event.consume();
//                            	}
//                            });
//                            
//                            data.getImplementationProperty().forEach(c->{
//                            	if(c.getName().equals(selectedTreeItem.getValue())) {
//                            		NewImplementationWindow dialog = new NewImplementationWindow(c.getIndex(), data);
//                        			dialog.initModality(Modality.APPLICATION_MODAL);
//                        			dialog.show();
//                        			event.consume();
//                            	}
//                            });
				} catch (Exception e) {

				}

			}
		});

		// Apply handlers
		verif.setOnAction(verifEvent);
		newComponent.setOnAction(newComponentEvent);
		newImplementation.setOnAction(newImplementationEvent);
		newConfiguration.setOnAction(newConfigurationEvent);
		linkMode.setOnAction(toggleLinkEvent);
		dragMode.setOnAction(toggleDragEvent);

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
		normal.setOnAction(normalEvent);
		night.setOnAction(nightEvent);
		h4ck3r.setOnAction(h4ck3rEvent);
		winxp.setOnAction(winXPEvent);

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
				boolean b = Alert.displayMultiple("Save File", "Do want to save ?");
				if (!b) {
					ref.close();
				} else {
					FileChooser dialog = new FileChooser();
					dialog.setTitle("Save file...");
					File file = dialog.showSaveDialog(ref);
					if (file != null) {
						try {
							data.save(file);
						} catch (IOException ex) {
							System.err.println("IO Failure: " + ex);
						}
					}
					ref.close();
				}
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

}
