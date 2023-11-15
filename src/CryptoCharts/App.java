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
        // TODO clean the mess
        AuthWindow auth = new AuthWindow();
        DB_Manager db = DB_Manager.getInstance();
        if (false) {
        String path = "src\\CryptoCharts\\AXSUSDT_d.csv";
        ReadCSV.download("https://www.cryptodatadownload.com/cdd/Binance_AXSUSDT_d.csv", path);
        ReadCSV csv = new ReadCSV("src\\CryptoCharts\\AXSUSDT_d.csv", ",");
        Vector<String> datesVector = csv.getAllColumnValues("Date");  
        Vector<String> priceVector = csv.getAllColumnValues("Close");
        Vector<Float> priceVectorFloat = new Vector<Float>(priceVector.size());

        for (int i = 0; i < priceVector.size(); i++) priceVectorFloat.add(Float.parseFloat(priceVector.get(i)));

        ObservableList<String> currencies = FXCollections.observableArrayList();
        String[] arr = {"BTCUSDT", "AVXUSDT", "XAUUSDT", "SOLUSDT"};
        currencies.addAll(arr);
        ComboBox<String> currenciesComboBox = new ComboBox<String>(currencies);

        ObservableList<String> dates = FXCollections.observableArrayList();
        Collections.reverse(datesVector);
        Collections.reverse(priceVector);
        dates.addAll(datesVector);

        NumberAxis yAxis = new NumberAxis("price", 0, Collections.max(priceVectorFloat), 10);
        CategoryAxis xAxis = new CategoryAxis(dates);
        LineChart linechart = new LineChart(xAxis,yAxis);
        XYChart.Series series = new XYChart.Series(); 
        series.setName("VERY FANCY NAME");
        for (int i = 0; i < priceVector.size() ; i++) series.getData().add(new XYChart.Data(datesVector.get(i), priceVectorFloat.get(i)));

        linechart.getData().add(series);

        StackPane root=new StackPane();  
        root.getChildren().add(linechart); 
        root.getChildren().add(currenciesComboBox);
        Scene scene=new Scene(root,900,600);      
        primaryStage.setTitle("First JavaFX Application");  
        primaryStage.setScene(scene);  
        primaryStage.show();  
        }
    }  
    public static void main (String[] args)  
    {  
        launch(args);  
    }  
  
}  