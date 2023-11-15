package CryptoCharts;

import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

public class MainWindow extends Scene {
    public MainWindow() {
        super(new GridPane(), windowWidth, windowHeight);
        rootLayout = (GridPane) this.getRoot();

    }

    public void constructChart(String symbol, String frequency) {
        String fileName = "Binance_" + symbol + "_" + frequency + ".csv";
        String path = "src\\CryptoCharts\\Charts\\" + fileName;
        try {
            ReadCSV.download("https://www.cryptodatadownload.com/cdd/" + fileName, path);
        } catch (IOException e) {
            System.out.println("FILE WRITING ERROR: " + e.getMessage());
            return;
        }
        ReadCSV csv = new ReadCSV("src\\CryptoCharts\\Charts\\" + fileName, ",");
        Vector<String> datesVector = csv.getAllColumnValues("Date");
        Vector<String> priceVector = csv.getAllColumnValues("Close");
        Vector<Float> priceVectorFloat = new Vector<Float>(priceVector.size());

        for (int i = 0; i < priceVector.size(); i++)
            priceVectorFloat.add(Float.parseFloat(priceVector.get(i)));

        ObservableList<String> currencies = FXCollections.observableArrayList();
        String[] arr = { "BTCUSDT", "AVXUSDT", "XAUUSDT", "SOLUSDT" };
        currencies.addAll(arr);
        ComboBox<String> currenciesComboBox = new ComboBox<String>(currencies);

        ObservableList<String> dates = FXCollections.observableArrayList();
        Collections.reverse(datesVector);
        Collections.reverse(priceVectorFloat);
        dates.addAll(datesVector);

        NumberAxis yAxis = new NumberAxis("price", 0, Collections.max(priceVectorFloat), Collections.max(priceVectorFloat) / 10);
        CategoryAxis xAxis = new CategoryAxis(dates);
        LineChart linechart = new LineChart(xAxis, yAxis);
        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < priceVector.size(); i++)
            series.getData().add(new XYChart.Data(datesVector.get(i), priceVectorFloat.get(i)));

        xAxis.setTickLabelRotation(90);
        linechart.setLegendVisible(false);
        linechart.setCreateSymbols(false);
        linechart.getData().add(series);

        rootLayout.add(linechart, 0,1,8,8);
    }

    private final static int windowWidth = 900;
    private final static int windowHeight = 600;
    private GridPane rootLayout;
}
