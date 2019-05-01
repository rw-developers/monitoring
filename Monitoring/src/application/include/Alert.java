/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.include;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author DELL INSPIRON
 */
public class Alert {
    static  boolean answer;
    public static boolean display(String title,String message)
    {
        
        Stage window =new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        
        GridPane layout =new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        layout.setVgap(10);
        layout.setHgap(10);
        
        Label label =new Label(message);
        GridPane.setConstraints(label, 1, 0);
        
        Button button1 =new Button("OK");
        GridPane.setConstraints(button1, 1 , 4);
        
        
        button1.setOnAction(e ->{
       answer=true;
        window.close();
        });
       
        
        layout.getChildren().addAll(button1,label);
        
        Scene scene =new Scene(layout,250,125);
        
        window.setScene(scene);
        window.showAndWait();
        
        return answer;
    }
    
    public static boolean displayMultiple(String title,String message)
    {
        
        Stage window =new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        
        GridPane layout =new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        layout.setVgap(10);
        layout.setHgap(10);
        
        Label label =new Label(message);
        GridPane.setConstraints(label, 1, 0);
        
        Button button1 =new Button("Yes");
        Button button2=new Button("No");
        GridPane.setConstraints(button1, 1 , 4);
        GridPane.setConstraints(button2, 2 , 4);
        
        button1.setOnAction(e ->{
       answer=true;
        window.close();
        });
        
        button2.setOnAction(e ->{
            answer=false;
             window.close();
             });
       
        
        layout.getChildren().addAll(button1,button2,label);
        
        Scene scene =new Scene(layout,250,125);
        
        window.setScene(scene);
        window.showAndWait();
        
        return answer;
    }
}
