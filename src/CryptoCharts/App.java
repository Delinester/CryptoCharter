package CryptoCharts;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;  
import javafx.event.EventHandler;
import javafx.print.Collation;
import javafx.scene.Scene;  
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;  
import javafx.scene.layout.StackPane;  

import javafx.scene.chart.LineChart;  
import javafx.scene.chart.NumberAxis;  
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;  

public class App extends Application{  
  
    @Override  
    public void start(Stage primaryStage) throws Exception { 
        //AuthWindow auth = new AuthWindow();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Crypto Charter");
        primaryStage.setX(200);
        primaryStage.setY(200);

        ScenesManager scenesManager = ScenesManager.getInstance();
        
        scenesManager.setMainStage(primaryStage);
        DB_Manager db = DB_Manager.getInstance();
        primaryStage.show();        
    }  
    public static void main (String[] args)  
    {  
        launch(args);  
    }  
  
}  