package application.view;

import java.io.File;
import java.io.IOException;

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
import javafx.event.EventHandler;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import monitoring.elements.ArchitectureElement;

public class ProgramWindow extends Stage {

	final private double DEFAULT_HEIGHT = 720.0;
	final private double DEFAULT_WIDTH = 1280.0;
	private Model data;
	public ArchitectureElement elem;
	private int color = 0;

	// Main Window elements
	private BorderPane root = new BorderPane();
	private GridPane tools = new GridPane();
	private ScrollPane center = new ScrollPane();
	public Pane mainPanel = new Pane();
	private MenuBar menu = new MenuBar();

	// Define menu elements
	private Menu file = new Menu("File");
	private Menu edit = new Menu("Edit");
	private Menu view = new Menu("View");
	public MenuItem save = new MenuItem("Save...");
	public MenuItem load = new MenuItem("Load...");
	public MenuItem export = new MenuItem("Export...");
	public MenuItem clear = new MenuItem("Clear elements");
	public MenuItem clearLinks = new MenuItem("Clear links");
	public Menu skins = new Menu("Skins...");
	public MenuItem normal = new MenuItem("Normal");
	public MenuItem night = new MenuItem("Night Mode");
	public MenuItem h4ck3r = new MenuItem("h4ck3r m0d3");
	public MenuItem winxp = new MenuItem("Windows XP");
	public MenuItem undo = new MenuItem("Undo...");
	public MenuItem redo = new MenuItem("Redo...");

	// Define tool panel elements

	public Button newComponent = new Button("Add component");
	public Button newImplementation = new Button("Add component\nImplementation");
	public Button verif = new Button("Check Architecture");

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
		center.setContent(mainPanel);
		root.getStyleClass().add("root");
		center.getStyleClass().add("center");
		mainPanel.getStyleClass().add("mainPanel");
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
		file.getItems().addAll(save, load, export);
		edit.getItems().addAll(undo, redo, clear, clearLinks);
		skins.getItems().addAll(normal, night, h4ck3r, winxp);
		view.getItems().add(skins);
		menu.getMenus().addAll(file, edit, view);

		// Construct tool panel
		newComponent.getStyleClass().addAll("toolbarButtons", "toolbarButtonsColor");
		newImplementation.getStyleClass().addAll("toolbarButtons", "toolbarButtonsColor");
		verif.getStyleClass().addAll("toolbarButtons", "toolbarButtonsColor");
		dragMode.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");
		linkMode.getStyleClass().addAll("toolbarButtonsHalf", "toolbarButtonsColor");

		tools.add(newComponent, 0, 0, 2, 1);
		tools.add(newImplementation, 0, 1, 2, 1);
		tools.add(verif, 0, 2, 2, 1);
		tools.add(dragMode, 0, 3);
		dragMode.setSelected(true);
		tools.add(linkMode, 1, 3);

//
		// Creates a new class dialog upon click
//		EventHandler<ActionEvent> newPortEvent = new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent e) {
//				NewPortWindow dialog = new NewPortWindow(-1, data,elem);
//				dialog.initModality(Modality.APPLICATION_MODAL);
//				dialog.show();
//				e.consume();
//			}
//		};

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
				NewImplementationWindow dialog = new NewImplementationWindow(-1, data);
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
				boolean flag = true;
				String msg = "";
				try {
					data.saveXml(new File("conf.xml"));
					Validator.validate("conf.xml", "model.xsd");
				} catch (SAXException | IOException e1) {
					flag = false;
					msg = e1.getMessage();
				}
				if (flag == true) {
					Alert.display("Message", "valid Configuration ");
				} else {
					Alert.display("Message", "invalid Configuration: " + msg);
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
					mainPanel.getChildren().clear();
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

		// Apply handlers
		verif.setOnAction(verifEvent);
		newComponent.setOnAction(newComponentEvent);
		newImplementation.setOnAction(newImplementationEvent);
		linkMode.setOnAction(toggleLinkEvent);
		dragMode.setOnAction(toggleDragEvent);

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
		root.setTop(menu);
		root.setLeft(tools);
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
		mainPanel.getChildren().add(in);
	}

	public void addComponent(ComponentBlock in) {
		mainPanel.getChildren().add(in);
	}
	
	public void addImplementation(ImplementationBlock in) {
		mainPanel.getChildren().add(in);
	}

	public void removePort(PortBlock in) {
		mainPanel.getChildren().remove(in);
	}

	public void removeComponent(ComponentBlock in) {
		mainPanel.getChildren().remove(in);
	}
	
	public void removeImplementation(ImplementationBlock in) {
		mainPanel.getChildren().remove(in);
	}

	public void addLink(Link in) {
		mainPanel.getChildren().add(in);
	}

	public void addArrow(Arrow in) {
		mainPanel.getChildren().add(in);
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
		mainPanel.getChildren().remove(in);
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
		mainPanel.getChildren().remove(in);
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
