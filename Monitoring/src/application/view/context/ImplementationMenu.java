package application.view.context;

import application.include.Model;
import application.view.CspglobaleImp;
import application.view.NewImplementationWindow;
import application.view.ProgramWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;

public class ImplementationMenu extends ContextMenu{
	
	int index;
	Model data;
	ProgramWindow window;
	MenuItem edit = new MenuItem("Change Behaviore");
	MenuItem delete = new MenuItem("Delete");
		

	/**
	 * Handler to generate an edit class window
	 */
	EventHandler<ActionEvent> csp = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			CspglobaleImp dialog = new CspglobaleImp(index, data);
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
			data.removeImplementationModel(index);
			e.consume();
		}
	};
	



	/*
	 * Constructs a ComponentMenu instance
	 * 
	 * WARNING: index contains stale data (index is never updated, no easy way to update)
	 */
	public ImplementationMenu(int i, Model dataIn,ProgramWindow w) {
		index = i;
		data = dataIn;
		window = w;

		edit.setOnAction(csp);
		delete.setOnAction(deleteEvent);

		this.getItems().add(edit);
		this.getItems().add(delete);
	}
	
	
	

	public void updateIndex(int l) {
		index = l;		
	}

}
