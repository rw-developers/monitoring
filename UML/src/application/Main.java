/************************************************************************************\
 * This class describes the relationship between all the various elements of				*
 * the model and the view.  Once all the elements are in place, everything else			*
 * should operate in a pretty modular fashion.  By using properties and listeners		*
 * rather than bare data types (like String or int), the relationship between				*
 * a given piece of data can be established and then ignored.  Rather than having		*
 * to continually pass these pieces of data back and forth, a listener allows a 		*
 * change to propogate to the necessary places in other elements.  It's a much more	*
 * complex setup than we had before, but much cleaner and much more scalable.				*
 \***********************************************************************************/

package application;

import application.include.Alert;
import application.include.Model;
import application.objects.PortBlock;
import application.objects.ComponentBlock;
import application.objects.Link;
import application.view.NewPortWindow;
import application.view.NewComponentWindow;
import application.view.NewLinkWindow;
import application.view.ProgramWindow;
import application.view.context.PortMenu;
import application.view.context.ComponentMenu;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import monitoring.elements.Component;
import monitoring.elements.Connector;
import monitoring.elements.Port;

public class Main extends Application {

	int linkSrc = -1;
	Line line;

	/**
	 * Model: this class will handle all objects in the program. Data can be pulled
	 * from it at will and pushed to the view.
	 */
	static Model data = new Model();

	/**
	 * View: This class displays the data for user interaction. It holds a reference
	 * to the model to make passing information back into the model cleaner and
	 * easier.
	 */
	static ProgramWindow window = new ProgramWindow(data);

	@Override
	public void start(Stage primaryStage) {

		data.getPortProperty().addListener(portListener());
		data.getComponentProperty().addListener(componentListener());
		data.getLinkProperty().addListener(linkListener());

		try {
			/**
			 * Make the main window visible
			 */
			window.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ListChangeListener<Port> portListener() {
		ListChangeListener<Port> portListener = new ListChangeListener<Port>() {
			@Override
			public void onChanged(Change<? extends Port> c) {
				updateButtons();
				while (c.next()) {

					/*****************************
					 * ELEMENT MODIFIED
					 *****************************/

					if (c.wasUpdated()) {
						for (int i = c.getFrom(); i != c.getTo(); ++i) {
							// For each modified element, refresh the element's view
							data.getPort(i).setName(data.getPortModel(i).getName());

						}
					} else {
						/*****************************
						 * ELEMENT ADDED
						 *****************************/

						if (c.wasAdded()) {
							for (Port added : c.getAddedSubList()) {
								// Generate new class block
								int idComp = ((Component) added.getElement()).getIndex();
								PortBlock newPort = new PortBlock(added, data.getComponent(idComp).getNode());
								newPort.setLayoutX((double) added.getXPos());
								newPort.setLayoutY((double) added.getYPos());
								PortMenu classContextMenu = new PortMenu(added.getIndex(), data);
								// data.addMenu(added.getIndex(), classContextMenu);

								// Declare delta to be used with click events
								final Delta delta = new Delta();

								/*****************************
								 * SET UP LISTENERS
								 *****************************/

								// Name listener
								added.getNameProp().addListener(new ChangeListener<String>() {
									@Override
									public void changed(ObservableValue<? extends String> observable, String oldValue,
											String newValue) {
										newPort.setName(newValue);
									}
								});

								/*****************************
								 * CURSOR MODIFICATIONS
								 *****************************/

								// Turns the cursor into a hand over draggable elements
								newPort.setOnMouseEntered(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										if (!e.isPrimaryButtonDown()) {
											newPort.getScene().setCursor(Cursor.HAND);
										}
									}
								});

								// Turns the cursor normal after leaving a draggable element
								newPort.setOnMouseExited(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										newPort.getScene().setCursor(Cursor.DEFAULT);
									}
								});

								// Turn the cursor normal during a drag
								newPort.setOnMousePressed(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {

										if (data.safeToSave()) {
											data.saveUndoState();
											data.clearRedoState();
										}
										if (e.isPrimaryButtonDown()) {
											newPort.getScene().setCursor(Cursor.DEFAULT);
										}
										delta.x = e.getX();
										delta.y = e.getY();
										newPort.getScene().setCursor(Cursor.MOVE);

									}
								});

								// Turn cursor normal upon release of draggable item
								newPort.setOnMouseReleased(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										if (!e.isPrimaryButtonDown()) {
											newPort.getScene().setCursor(Cursor.DEFAULT);
										}

										if (!data.isUndoEmpty())
											window.undo.setDisable(false);
										else
											window.undo.setDisable(true);
										if (!data.isRedoEmpty())
											window.redo.setDisable(false);
										else
											window.redo.setDisable(true);

									}
								});

								/*****************************
								 * MAKE DRAGGABLE/SELECTABLE
								 *****************************/

								// Makes the class block draggable
								newPort.setOnMouseDragged(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {

										if (!data.isLinkable()) {
											// set stored X and Y positions
											added.setXPos((int) (newPort.getLayoutX() + e.getX() - delta.x));
											added.setYPos((int) (newPort.getLayoutY() + e.getY() - delta.y));

											// Set link node position
											newPort.getNode().setX((int) (added.getXPos() + (newPort.getWidth() / 2)
													+ e.getX() - delta.x));
											newPort.getNode().setY((int) (added.getYPos() + (newPort.getHeight() / 2)
													+ e.getY() - delta.y));
											newPort.setLayoutX(added.getXPos());
											newPort.setLayoutY(added.getYPos());

											// Set the bounds of the PortBlock within the LinkNode
											newPort.getNode().setBounds((int) (added.getXPos() + e.getX() - delta.x),
													(int) (added.getXPos() + (newPort.getWidth()) + e.getX() - delta.x),
													(int) (added.getYPos() + e.getY() - delta.y), (int) (added.getYPos()
															+ (newPort.getHeight()) + e.getY() - delta.y));
											newPort.getNode().updateLink();
										} else {
											if (window.mainPanel.getChildren().contains(line)) {
												line.setEndX(added.getXPos() + e.getX());
												line.setEndY(added.getYPos() + e.getY());
											}
										}
									}
								});

								// Makes the class block selectable
								newPort.setOnMouseClicked(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										// Check for double click coming from primary mouse button
										if (e.getButton().equals(MouseButton.PRIMARY)) {
											if (e.getClickCount() == 2) {
												// Launch class edit window
												NewPortWindow dialog = new NewPortWindow(added.getIndex(), data,
														added.getElement());
												dialog.initModality(Modality.APPLICATION_MODAL);
												dialog.show();
											}
											classContextMenu.hide();
											e.consume();
										} else if (e.getButton() == MouseButton.SECONDARY) {
											classContextMenu.show(newPort, e.getScreenX(), e.getScreenY());
										}

									}
								});

								/****************
								 * DRAG TO LINK *
								 ****************/

								// Sets linkSrc to wherever the drag started, creates line
								newPort.setOnDragDetected(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										newPort.startFullDrag();

										if (data.isLinkable()) {
											linkSrc = added.getIndex();

											// line = new Line(added.getXPos() + added.getWidth()/2, added.getYPos() +
											// added.getHeight()/2, e.getX() - delta.x, e.getY() - delta.y);
											line = new Line(added.getXPos() + e.getX(), added.getYPos() + e.getY(),
													added.getXPos() + e.getX(), added.getYPos() + e.getY());
											line.setStroke(Color.GRAY);
											line.getStrokeDashArray().addAll(3.0);
											window.mainPanel.getChildren().add(line);
											line.toBack();
										}
									}
								});

								// Sets destination and brings up new link editor
								newPort.setOnMouseDragReleased(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										if (data.isLinkable()) {
											if (data.getPortModel(linkSrc).getType().equals(added.getType())) {
												Alert.display("Error !!", "Invalid connection of ports");
											} else {
												if (data.getPortModel(linkSrc).getType().equals("IN")
														|| added.getType().equals("OUT")) {
													Alert.display("Error !!", "Invalid connection of ports");
												} else {
													if (linkSrc != added.getIndex() && linkSrc != -1) {
														// Create link window, with filled in src/dest
														window.mainPanel.getChildren().remove(line);
														NewLinkWindow dialog = new NewLinkWindow(-1, data);
														dialog.setSrc(linkSrc);
														dialog.setDest(added.getIndex());
														dialog.initModality(Modality.APPLICATION_MODAL);
														dialog.show();
													}
												}
											}

											// Reset src variable
											linkSrc = -1;

											updateButtons();
										}
									}
								});

								// Handles erasing temp line on mouse release
								window.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {

										if (window.mainPanel.getChildren().contains(line)) {
											window.mainPanel.getChildren().remove(line);
											window.applyCss();

										}

										// Reset src variable
										linkSrc = -1;
									}
								});

								// Display class
								addPort(newPort);
								newPort.toFront();
								window.applyCss();
								newPort.initWidthHeight();

								// Set the bounds of the PortBlock within the LinkNode
								newPort.getNode().setBounds((int) (added.getXPos()),
										(int) (added.getXPos() + newPort.getWidth()), (int) (added.getYPos()),
										(int) (added.getYPos() + newPort.getHeight()));
							}
						}

						/*****************************
						 * ELEMENT REMOVED
						 *****************************/

						else if (c.wasRemoved()) {
							if (c.getList().size() != 0) {
								for (Port removed : c.getRemoved()) {
									int pivot = removed.getIndex();
									int bound = data.getLinkTail();

									// Remove links that connected to removed block
									for (int i = 0; i != bound; ++i) {
										if (data.getLinkModel(i).getSource() == pivot
												|| data.getLinkModel(i).getDest() == pivot) {
											data.removeLinkModel(i);
											--bound;
											--i;
										}
									}

									// Shift all index references past the removed down one
									for (int i = 0; i != data.getLinkTail(); ++i) {
										data.getLinkModel(i).setIndex(i);
										if (data.getLinkModel(i).getSource() > pivot) {
											data.getLinkModel(i).setSource(data.getLinkModel(i).getSource() - 1);
										}
										if (data.getLinkModel(i).getDest() > pivot) {
											data.getLinkModel(i).setDest(data.getLinkModel(i).getDest() - 1);
										}
									}

									for (int i = pivot; i != data.getPortTail(); ++i) {
										data.getPortModel(i).setIndex(i);
									}

									// erase classes
									window.removePort(data.getPort(removed.getIndex()));
									data.removePort(removed.getIndex());
								}
							} else {

							}
						}
					}
				}
			}
		};
		return portListener;
	}

	private ListChangeListener<Component> componentListener() {
		ListChangeListener<Component> componentListener = new ListChangeListener<Component>() {
			@Override
			public void onChanged(Change<? extends Component> c) {
				updateButtons();
				while (c.next()) {

					/*****************************
					 * ELEMENT MODIFIED
					 *****************************/

					if (c.wasUpdated()) {
						for (int i = c.getFrom(); i != c.getTo(); ++i) {
							// For each modified element, refresh the element's view
							data.getComponent(i).setName(data.getComponentModel(i).getName());

						}
					} else {
						/*****************************
						 * ELEMENT ADDED
						 *****************************/

						if (c.wasAdded()) {
							for (Component added : c.getAddedSubList()) {
								// Generate new component block
								ComponentBlock newComponent = new ComponentBlock(added);
								newComponent.setLayoutX((double) added.getXPos());
								newComponent.setLayoutY((double) added.getYPos());
								ComponentMenu componentContextMenu = new ComponentMenu(added.getIndex(), data);
								// data.addMenu(added.getIndex(), componentContextMenu);

								// Declare delta to be used with click events
								final Delta delta = new Delta();

								/*****************************
								 * SET UP LISTENERS
								 *****************************/

								// Name listener
								added.getNameProp().addListener(new ChangeListener<String>() {
									@Override
									public void changed(ObservableValue<? extends String> observable, String oldValue,
											String newValue) {
										newComponent.setName(newValue);
									}
								});

								/*****************************
								 * CURSOR MODIFICATIONS
								 *****************************/

								// Turns the cursor into a hand over draggable elements
								newComponent.setOnMouseEntered(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										if (!e.isPrimaryButtonDown()) {
											newComponent.getScene().setCursor(Cursor.HAND);
										}
									}
								});

								// Turns the cursor normal after leaving a draggable element
								newComponent.setOnMouseExited(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										newComponent.getScene().setCursor(Cursor.DEFAULT);
									}
								});

								// Turn the cursor normal during a drag
								newComponent.setOnMousePressed(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {

										if (data.safeToSave()) {
											data.saveUndoState();
											data.clearRedoState();
										}
										if (e.isPrimaryButtonDown()) {
											newComponent.getScene().setCursor(Cursor.DEFAULT);
										}
										delta.x = e.getX();
										delta.y = e.getY();
										newComponent.getScene().setCursor(Cursor.MOVE);

									}
								});

								// Turn cursor normal upon release of draggable item
								newComponent.setOnMouseReleased(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										if (!e.isPrimaryButtonDown()) {
											newComponent.getScene().setCursor(Cursor.DEFAULT);
										}

										if (!data.isUndoEmpty())
											window.undo.setDisable(false);
										else
											window.undo.setDisable(true);
										if (!data.isRedoEmpty())
											window.redo.setDisable(false);
										else
											window.redo.setDisable(true);

									}
								});

								/*****************************
								 * MAKE DRAGGABLE/SELECTABLE
								 *****************************/

								// Makes the component block draggable
								newComponent.setOnMouseDragged(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {

										// set stored X and Y positions
										added.setXPos((int) (newComponent.getLayoutX() + e.getX() - delta.x));
										added.setYPos((int) (newComponent.getLayoutY() + e.getY() - delta.y));

										// Set link node position
										newComponent.getNode().setX((int) (added.getXPos()
												+ (newComponent.getWidth() / 2) + e.getX() - delta.x));
										newComponent.getNode().setY((int) (added.getYPos()
												+ (newComponent.getHeight() / 2) + e.getY() - delta.y));
										newComponent.setLayoutX(added.getXPos());
										newComponent.setLayoutY(added.getYPos());

										// Set the bounds of the ComponentBlock within the LinkNode
										newComponent.getNode().setBounds((int) (added.getXPos() + e.getX() - delta.x),
												(int) (added.getXPos() + (newComponent.getWidth()) + e.getX()
														- delta.x),
												(int) (added.getYPos() + e.getY() - delta.y), (int) (added.getYPos()
														+ (newComponent.getHeight()) + e.getY() - delta.y));
										for (int i = 0; i < newComponent.getNode().getConnectedPort().size(); i++) {
											Port port = null;
											for (int j = 0; j < data.getPortProperty().size(); j++) {
												if (((Component) data.getPortProperty().get(j).getElement())
														.getIndex() == added.getIndex()) {
													port = data.getPortProperty().get(j);
												}
											}
											newComponent.getNode().getConnectedPort().get(i)
													.setLayoutX(added.getXPos() + 90);
											newComponent.getNode().getConnectedPort().get(i)
													.setLayoutY(added.getYPos() + i * 20 + 10);

											// set stored X and Y positions
											port.setXPos(
													(int) (newComponent.getNode().getConnectedPort().get(i).getLayoutX()
															+ e.getX() - delta.x));
											port.setYPos(
													(int) (newComponent.getNode().getConnectedPort().get(i).getLayoutY()
															+ e.getY() - delta.y));

											// Set link node position
											newComponent.getNode().getConnectedPort().get(i).getNode()
													.setX((int) (added.getXPos() + 90 + (newComponent.getNode()
															.getConnectedPort().get(i).getWidth() / 2) + e.getX()
															- delta.x));
											newComponent.getNode().getConnectedPort().get(i).getNode()
													.setY((int) (added.getYPos() + i * 20 + 10 + (newComponent.getNode()
															.getConnectedPort().get(i).getHeight() / 2) + e.getY()
															- delta.y));
											newComponent.getNode().getConnectedPort().get(i)
													.setLayoutX(added.getXPos() + 90);
											newComponent.getNode().getConnectedPort().get(i)
													.setLayoutY(added.getYPos() + i * 20 + 10);

											// Set the bounds of the PortBlock within the LinkNode
											newComponent.getNode().getConnectedPort().get(i).getNode()
													.setBounds((int) (port.getXPos() + e.getX() - delta.x),
															(int) (port.getXPos() + (newComponent.getNode()
																	.getConnectedPort().get(i).getWidth()) + e.getX()
																	- delta.x),
															(int) (port.getYPos() + e.getY() - delta.y),
															(int) (port.getYPos() + (newComponent.getNode()
																	.getConnectedPort().get(i).getHeight()) + e.getY()
																	- delta.y));
											newComponent.getNode().getConnectedPort().get(i).getNode().updateLink();

											if (window.mainPanel.getChildren().contains(line)) {
												line.setEndX(port.getXPos() + e.getX());
												line.setEndY(port.getYPos() + e.getY());
											}

										}

										// newComponent.getNode().updatePort();

									}
								});

								// Makes the component block selectable
								newComponent.setOnMouseClicked(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										// Check for double click coming from primary mouse button
										if (e.getButton().equals(MouseButton.PRIMARY)) {
											if (e.getClickCount() == 1) {
												// Launch component edit window
												NewComponentWindow dialog = new NewComponentWindow(added.getIndex(),
														data);
												dialog.initModality(Modality.APPLICATION_MODAL);
												dialog.show();
											}
											componentContextMenu.hide();
											e.consume();
										} else if (e.getButton() == MouseButton.SECONDARY) {
											componentContextMenu.show(newComponent, e.getScreenX(), e.getScreenY());
										}

									}
								});

								newComponent.setOnMouseClicked(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										if (e.getButton().equals(MouseButton.PRIMARY)) {

											if (e.getClickCount() == 2) {

												NewPortWindow dialog = new NewPortWindow(-1, data, added);
												dialog.initModality(Modality.APPLICATION_MODAL);
												dialog.show();
											}

											componentContextMenu.hide();
											e.consume();
							
										} else if (e.getButton() == MouseButton.SECONDARY) {
											componentContextMenu.show(newComponent, e.getScreenX(), e.getScreenY());
										}
									}
								});

								// Display component
								addComponent(newComponent);
								newComponent.toFront();
								window.applyCss();
								newComponent.initWidthHeight();

								// Set the bounds of the ComponentBlock within the LinkNode
								newComponent.getNode().setBounds((int) (added.getXPos()),
										(int) (added.getXPos() + newComponent.getWidth()), (int) (added.getYPos()),
										(int) (added.getYPos() + newComponent.getHeight()));
							}
						}

						/*****************************
						 * ELEMENT REMOVED
						 *****************************/

						else if (c.wasRemoved()) {
							if (c.getList().size() != 0) {
								for (Component removed : c.getRemoved()) {
									int pivot = removed.getIndex();
									int bound = data.getLinkTail();

									// Remove links that connected to removed block
									for (int i = 0; i != bound; ++i) {
										if (data.getLinkModel(i).getSource() == pivot
												|| data.getLinkModel(i).getDest() == pivot) {
											data.removeLinkModel(i);
											--bound;
											--i;
										}
									}

									// Shift all index references past the removed down one
									for (int i = 0; i != data.getLinkTail(); ++i) {
										data.getLinkModel(i).setIndex(i);
										if (data.getLinkModel(i).getSource() > pivot) {
											data.getLinkModel(i).setSource(data.getLinkModel(i).getSource() - 1);
										}
										if (data.getLinkModel(i).getDest() > pivot) {
											data.getLinkModel(i).setDest(data.getLinkModel(i).getDest() - 1);
										}
									}

									for (int i = pivot; i != data.getComponentTail(); ++i) {
										data.getComponentModel(i).setIndex(i);
									}

									// erase componentes
									window.removeComponent(data.getComponent(removed.getIndex()));
									data.removeComponent(removed.getIndex());
								}
							} else {

							}
						}
					}
				}
			}

		};
		return componentListener;
	}

	/**
	 * Generates and returns a ChangeListener to operate on the Link Model
	 * 
	 * @return the ChangeListener to handle each individual Connector
	 */
	private ListChangeListener<Connector> linkListener() {
		ListChangeListener<Connector> linkListener = new ListChangeListener<Connector>() {
			@Override
			public void onChanged(Change<? extends Connector> c) {
				while (c.next()) {
					if (c.wasUpdated()) {
						for (int i = c.getFrom(); i != c.getTo(); ++i) {

						}
					} else {
						if (c.wasAdded()) {
							for (Connector added : c.getAddedSubList()) {
								int srcIndex = added.getSource();
								int destIndex = added.getDest();

								// if Source or Dest Min are < 0, entire string should be *, otherwise express a
								// range Min to Max (including * for int<0 Max)
								String srcMulti;
								String destMulti;

								srcMulti = stringifyMulti(added.getSourceMin(), added.getSourceMax());
								destMulti = stringifyMulti(added.getDestMin(), added.getDestMax());

								Link newLink = new Link(data.getPort(srcIndex).getNode(),
										data.getPort(destIndex).getNode(), added.getLabel(), added.getType(), srcMulti,
										destMulti);

								/*****************************
								 * SET UP LISTENERS
								 *****************************/

								// Label listener
								added.getLabelProp().addListener(new ChangeListener<String>() {
									@Override
									public void changed(ObservableValue<? extends String> observable, String oldValue,
											String newValue) {
										newLink.setLabel(newValue);

										updateButtons();
									}
								});

								// Src Min listener
								added.getSourceMinProp().addListener(new ChangeListener<Number>() {
									@Override
									public void changed(ObservableValue<? extends Number> observable, Number oldValue,
											Number newValue) {
										newLink.setSrcMultiplicity(
												stringifyMulti((int) newValue, added.getSourceMax()));

										updateButtons();
									}
								});

								// Src Max listener
								added.getSourceMaxProp().addListener(new ChangeListener<Number>() {
									@Override
									public void changed(ObservableValue<? extends Number> observable, Number oldValue,
											Number newValue) {
										newLink.setSrcMultiplicity(
												stringifyMulti(added.getSourceMin(), (int) newValue));

										updateButtons();
									}
								});

								// Dest Min listener
								added.getDestMinProp().addListener(new ChangeListener<Number>() {
									@Override
									public void changed(ObservableValue<? extends Number> observable, Number oldValue,
											Number newValue) {
										newLink.setDestMultiplicity(stringifyMulti((int) newValue, added.getDestMax()));

										updateButtons();
									}
								});

								// Dest Max listener
								added.getDestMaxProp().addListener(new ChangeListener<Number>() {
									@Override
									public void changed(ObservableValue<? extends Number> observable, Number oldValue,
											Number newValue) {
										newLink.setDestMultiplicity(stringifyMulti(added.getDestMin(), (int) newValue));

										updateButtons();
									}
								});

								// Type listener
								added.getTypeProp().addListener(new ChangeListener<Number>() {
									@Override
									public void changed(ObservableValue<? extends Number> observable, Number oldValue,
											Number newValue) {
										newLink.setType((int) newValue);

										updateButtons();
									}
								});

								// Source X position listener
								data.getPort(srcIndex).getNode().getXProperty()
										.addListener(new ChangeListener<Number>() {
											@Override
											public void changed(ObservableValue<? extends Number> observable,
													Number oldValue, Number newValue) {
												newLink.setStartX((int) newValue);

												updateButtons();
											}
										});

								// Source Y position listener
								data.getPort(srcIndex).getNode().getYProperty()
										.addListener(new ChangeListener<Number>() {
											@Override
											public void changed(ObservableValue<? extends Number> observable,
													Number oldValue, Number newValue) {
												newLink.setStartY((int) newValue);

												updateButtons();
											}
										});

								// Dest X Position listener
								data.getPort(destIndex).getNode().getXProperty()
										.addListener(new ChangeListener<Number>() {
											@Override
											public void changed(ObservableValue<? extends Number> observable,
													Number oldValue, Number newValue) {
												newLink.setEndX((int) newValue);

												updateButtons();
											}
										});

								// Dest Y position listener
								data.getPort(destIndex).getNode().getYProperty()
										.addListener(new ChangeListener<Number>() {
											@Override
											public void changed(ObservableValue<? extends Number> observable,
													Number oldValue, Number newValue) {
												newLink.setEndY((int) newValue);

												updateButtons();
											}
										});

								/*****************************
								 * CURSOR MODIFICATIONS
								 *****************************/

								// Turns the cursor into a hand over draggable elements
								newLink.setOnMouseEntered(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										if (!e.isPrimaryButtonDown()) {
											newLink.getScene().setCursor(Cursor.HAND);
										}
									}
								});

								// Turns the cursor normal after leaving a draggable element
								newLink.setOnMouseExited(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										newLink.getScene().setCursor(Cursor.DEFAULT);
									}
								});

								// Makes the link selectable
								newLink.setOnMouseClicked(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										// Check for double click coming from primary mouse button
										if (e.getButton().equals(MouseButton.PRIMARY)) {
											if (e.getClickCount() == 2) {
												// Launch link edit window
												NewLinkWindow dialog = new NewLinkWindow(added.getIndex(), data);
												dialog.initModality(Modality.APPLICATION_MODAL);
												dialog.show();
											}
											e.consume();
										}
									}
								});

								// Display Link
								addLink(newLink);
								newLink.toBack();

								newLink.updateLine();
							}
						} else if (c.wasRemoved()) {
							for (Connector removed : c.getRemoved()) {
								if (!data.isClearing()) {
									int pivot = removed.getIndex();

									data.getLink(pivot).warnLinkNodes();
									window.remove(data.getLink(pivot));
									data.removeLink(pivot);

									updateButtons();
								}
							}
						}

					}
				}
			}

			/**
			 * Take special flag values and mutate the output string based on their values (
			 * -2 means no entry, -1 means asterisk )
			 * 
			 * Ranges should appear in the form "min ... max" except when starting with "*"
			 * or if only one value has been entered
			 * 
			 * @param min The user's chosen min value of multiplicity (start of range)
			 * @param max The user's chosen max value of multiplicity (end of range)
			 * @return A correct complete string for the range
			 */
			private String stringifyMulti(int min, int max) {
				String multi;

				if (min == -1)
					multi = "*";
				else if (min == -2) {
					if (max == -2)
						multi = "";
					else
						multi = (max == -1 ? "*" : Integer.toString(max));
				} else if (max == -2)
					multi = (min == -1 ? "*" : Integer.toString(min));
				else
					multi = Integer.toString(min) + "..." + (max == -1 ? "*" : Integer.toString(max));

				return multi;
			}
		};

		return linkListener;
	}

	/**
	 * Used with the makeDraggable method Stores the x and y values on mouse down so
	 * they can be removed from the final position
	 */
	private class Delta {

		public double x;
		public double y;

		public Delta() {
		}

	}

	/**
	 * Update the redo and undo buttons on the ProgramWindow (they may have to be
	 * dimmed or lit)
	 * 
	 */
	private void updateButtons() {
		if (!data.isUndoEmpty())
			window.undo.setDisable(false);
		else
			window.undo.setDisable(true);
		if (!data.isRedoEmpty())
			window.redo.setDisable(false);
		else
			window.redo.setDisable(true);

	}

	/**
	 * Snaps the given values to a grid
	 * 
	 * @param i the value to be rounded
	 * @return the given value rounded to the nearest 10
	 *//*
		 * private int gridify(int i) { return (i >= 10 ? (i % 10 < 5 ? i - (i % 10) : i
		 * + (10 - (i % 10))) : 10); }
		 */

	private void addPort(PortBlock in) {
		data.addPort(in);
		window.addPort(in);
	}

	private void addComponent(ComponentBlock in) {
		data.addComponent(in);
		window.addComponent(in);
	}

	private void addLink(Link in) {
		data.addLink(in);
		window.addLink(in);
		window.addLabel(in.getLabel());
		window.addArrow(in.getArrow());
		window.addMultiplicity(in.getSrcMultiplicity());
		window.addMultiplicity(in.getDestMultiplicity());
	}

	public static void main(String[] args) {
		launch(args);
	}
}
