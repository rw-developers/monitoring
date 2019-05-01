package application.view.context;

import application.include.Model;
import application.view.NewComponentWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;

public class ComponentMenu extends ContextMenu {

	int index;
	Model data;
	MenuItem edit = new MenuItem("Edit...");
	MenuItem delete = new MenuItem("Delete");
		

	/**
	 * Handler to generate an edit class window
	 */
	EventHandler<ActionEvent> editEvent = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			NewComponentWindow dialog = new NewComponentWindow(index, data);
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.show();
			e.consume();
		}
	};

	/**
	 * Handler to remove the selected class block
	 */
	EventHandler<ActionEvent> deleteEvent = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			data.removeComponentModel(index);
			e.consume();
		}
	};
	



	/*
	 * Constructs a ComponentMenu instance
	 * 
	 * WARNING: index contains stale data (index is never updated, no easy way to update)
	 */
	public ComponentMenu(int i, Model dataIn) {
		index = i;
		data = dataIn;

		edit.setOnAction(editEvent);
		delete.setOnAction(deleteEvent);

		this.getItems().add(edit);
		this.getItems().add(delete);
	}
	
	
	

	public void updateIndex(int l) {
		index = l;		
	}
}