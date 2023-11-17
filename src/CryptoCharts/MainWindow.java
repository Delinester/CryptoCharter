package CryptoCharts;

import java.io.IOException;
import java.util.Collections;
import java.util.Stack;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.*;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MainWindow extends Scene {
    public MainWindow() {
        // TODO ADD ANOTHER LAYOUT
        super(new GridPane(), windowWidth, windowHeight);
        rootLayout = (GridPane) this.getRoot();
        TextField frequencyField = new TextField();
        frequencyComboBox = new ComboBox<String>();
        ObservableList<String> freqList = FXCollections.observableArrayList(frequencies);
        frequencyComboBox.setItems(freqList);
        frequencyComboBox.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                constructChart("BTCUSDT", "d", frequencyField.getText());
            }
        });
        rootLayout.add(frequencyComboBox, 11, 7, 1, 1);
        rootLayout.add(frequencyField, 12, 7, 1, 1);
    }
    //TODO REFACTOR THE METHOD
    public void constructChart(String symbol, String frequency, String windowString) {
        cleanChart();
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
        Vector<Float> closePriceVector = csv.getColumnAsFloat("Close");
        Vector<Float> openPriceVector = csv.getColumnAsFloat("Open");
        Vector<Float> highPriceVector = csv.getColumnAsFloat("High");
        Vector<Float> lowPriceVector = csv.getColumnAsFloat("Low");

        int window = 0;
        int numberOfEntries = datesVector.size();
        if (windowString.equals("full"))
            window = datesVector.size();
        else
            window = Integer.parseInt(windowString);

        Collections.reverse(datesVector);
        Collections.reverse(closePriceVector);
        //Collections.reverse(openPriceVector);
        //Collections.reverse(highPriceVector);
        //Collections.reverse(lowPriceVector);

        ObservableList<Float> closePrices = FXCollections.observableArrayList();
        for (int i = numberOfEntries - window; i < numberOfEntries; i++)
             closePrices.add(closePriceVector.get(i));

        ObservableList<String> dates = FXCollections.observableArrayList();
        for (int i = numberOfEntries - window; i < numberOfEntries; i++)
             dates.add(datesVector.get(i));

        float maxPrice = Collections.max(closePriceVector);
        float minPrice = Collections.min(closePriceVector);

        NumberAxis yAxis = new NumberAxis("price",
                minPrice - minPrice / 100, maxPrice + maxPrice / 100, Collections.max(closePriceVector) / 100);
        yAxis.setSide(Side.RIGHT);
        CategoryAxis xAxis = new CategoryAxis(dates);

        LineChart<String, Float> linechart = new LineChart(xAxis, yAxis);
        XYChart.Series<String, Float> series = new XYChart.Series<String, Float>();
        for (int i = 0; i < window; i++)
        {
            float close = closePrices.get(i);
            float open = openPriceVector.get(window - 1 - i);
            float high = highPriceVector.get(window - 1 - i);
            float low = lowPriceVector.get(window - 1 - i);
            String date = dates.get(i);
            XYChart.Data<String, Float> data = new XYChart.Data<String, Float>(date, close);
            data.setNode(new ChartHoverInfo(date, close, open, high, low));
            series.getData().add(data);
        }

        xAxis.setTickLabelRotation(90);
        linechart.setLegendVisible(false);
        linechart.setCreateSymbols(false);
        linechart.getData().add(series);
        linechart.setCursor(Cursor.CROSSHAIR);
        mainChart = linechart;

        ScrollableChart scrollableChart = new ScrollableChart(linechart, window);
        
        rootLayout.add(scrollableChart, 0, 1, 8, 8);

    }

    private void cleanChart() {
        rootLayout.getChildren().remove(mainChart);
    }

    private final static int windowWidth = 1200;
    private final static int windowHeight = 800;
    private GridPane rootLayout;

    private LineChart mainChart;

    private ComboBox<String> frequencyComboBox;
    private final String[] frequencies = { "d", "m", "full" };
}
