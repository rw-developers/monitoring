package application.view;



import javafx.scene.image.*;
import java.lang.*;
import java.util.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public  class ShemaWindow extends Stage {

     

    public ShemaWindow() {
    	 
        
        AnchorPane p = new AnchorPane();
        p.setId("AnchorPane");
        p.setPrefHeight(531.0);
        p.setPrefWidth(1108.0);
        
        
          
        
       // imageView.setImage(new Image(getClass().getResourceAsStream("/application/include/arch.png")));
        Image dragIcon = new Image(getClass().getResourceAsStream("/application/include/meta.png"));
		ImageView imageView = new ImageView(dragIcon);
		 AnchorPane.setRightAnchor(imageView, 8.0);
        AnchorPane.setTopAnchor(imageView, 0.0);
        imageView.setFitHeight(529.0);
        imageView.setFitWidth(1097.0);
        imageView.setLayoutX(190.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
		
		
       // application.include.Alert.display("", "fbff");
        p.getChildren().add(imageView);
        Scene scene = new Scene(p, 1108,531);
		this.setScene(scene);

    }
}
