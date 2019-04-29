package application.view.context;

import application.include.Model;
import application.view.NewPortWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import monitoring.elements.ArchitectureElement;

public class FieldMenu extends ContextMenu {

	Model data;
	ArchitectureElement elem;

	MenuItem newClass = new MenuItem("New Class Block...");
	MenuItem newLink = new MenuItem("New Link...");

	EventHandler<ActionEvent> newClassEvent = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			NewPortWindow dialog = new NewPortWindow(-1, data,elem);
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.show();
			e.consume();
		}
	};

	public FieldMenu(Model dataIn) {
		data = dataIn;

		newClass.setOnAction(newClassEvent);

		this.getItems().add(newClass);
		this.getItems().add(newLink);
	}
}
