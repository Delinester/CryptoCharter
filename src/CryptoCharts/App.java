package CryptoCharts;

import javafx.application.Application;
import javafx.stage.Stage;  

public class App extends Application{  
  
    @Override  
    public void start(Stage primaryStage) throws Exception { 
        primaryStage.setResizable(true);
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