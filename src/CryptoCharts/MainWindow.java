package CryptoCharts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.*;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainWindow extends Scene {
    public MainWindow() {
        // TODO ADD ANOTHER LAYOUT
        super(new BorderPane(), windowWidth, windowHeight);
        rootLayout = (BorderPane) this.getRoot();    
        centerVbox = new VBox();   
        centerVbox.setAlignment(Pos.TOP_RIGHT);
        centerVbox.getChildren().add(mainChartName);
        rootLayout.setCenter(centerVbox);

        ChartDrawerEventHandler chartDrawerEventHandler = new ChartDrawerEventHandler(this);

        TextField frequencyField = new TextField();
        frequencyField.setText("full");

        frequencyComboBox = new ComboBox<String>();
        ObservableList<String> freqList = FXCollections.observableArrayList(frequencies);
        frequencyComboBox.setItems(freqList);   
        frequencyComboBox.setOnAction(chartDrawerEventHandler);
        frequencyComboBox.getSelectionModel().select(0);

        chartDrawerEventHandler.setFrequencyBox(frequencyComboBox);
        chartDrawerEventHandler.setWindowField(frequencyField);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        hbox.getChildren().addAll(frequencyComboBox, frequencyField);        
        rootLayout.setTop(hbox);

        rightVbox = new VBox();
        symbolsListView = new SymbolsListView(DB_Manager.getInstance().getAvailableSymbols());
        symbolsListView.setMaxHeight(windowHeight / 2);
        rightVbox.getChildren().add(symbolsListView);
        chartDrawerEventHandler.setSymbolsList(symbolsListView);
        symbolsListView.setOnMouseClicked(chartDrawerEventHandler);
        rootLayout.setRight(rightVbox);

        leftVbox = new VBox();
        rootLayout.setLeft(leftVbox);

    }
    //TODO REFACTOR THE METHOD
    public void constructChart(String symbol, String frequency, String windowString) {        
        cleanMainChart();
        String fileName = "Binance_" + symbol + "_" + frequency + ".csv";
        String path = "src\\CryptoCharts\\Charts\\" + fileName;
        try {
            ReadCSV.download("https://www.cryptodatadownload.com/cdd/" + fileName, path);
        } catch (IOException e) {
            System.out.println("FILE WRITING ERROR: " + e.getMessage());
            return;
        }
        ReadCSV csv = new ReadCSV("src\\CryptoCharts\\Charts\\" + fileName, ",", 0);

        mainChartName.setText(symbol);

        datesVector = csv.getAllColumnValues("Date");
        closePriceVector = csv.getColumnAsFloat("Close");
        openPriceVector = csv.getColumnAsFloat("Open");
        highPriceVector = csv.getColumnAsFloat("High");
        lowPriceVector = csv.getColumnAsFloat("Low");
        
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

        float maxPrice = Collections.max(closePrices);
        float minPrice = Collections.min(closePrices);

        NumberAxis yAxis = new NumberAxis("price",
                minPrice - minPrice / 100, maxPrice + maxPrice / 100, Collections.max(closePriceVector) / 100);
        yAxis.setSide(Side.RIGHT);
        CategoryAxis xAxis = new CategoryAxis(dates);

        LineChart<String, Float> linechart = new LineChart(xAxis, yAxis);
        XYChart.Series<String, Float> series = new XYChart.Series<String, Float>();
        for (int i = 0; i < window; i++)
        {
            float close = closePrices.get(i);
            String closeStr = Float.toString(close);
            String open = Float.toString(openPriceVector.get(window - 1 - i));
            String high = Float.toString(highPriceVector.get(window - 1 - i));
            String low = Float.toString(lowPriceVector.get(window - 1 - i));
            String date = dates.get(i);
            XYChart.Data<String, Float> data = new XYChart.Data<String, Float>(date, close);
            data.setNode(new ChartHoverInfo("Date: ", date, "Close: ", closeStr, "Open: ", open, "High: ", high, "Low: ", low));
            series.getData().add(data);
        }

        xAxis.setTickLabelRotation(90);
        linechart.setLegendVisible(false);
        linechart.setCreateSymbols(false);
        linechart.getData().add(series);
        linechart.setCursor(Cursor.CROSSHAIR);

        ScrollableChart scrollableChart = new ScrollableChart(linechart, window);      
        scrollableChart.setSize(600,400);

        charts.add(scrollableChart);
        centerVbox.getChildren().add(scrollableChart);

        ScrollableChart rsi = Indicators.RSI(closePriceVector, datesVector);
        rsi.setSize(600, 200);
        charts.add(rsi);
        centerVbox.getChildren().add(rsi);
    }

    private void cleanMainChart() {
        centerVbox.getChildren().removeAll(charts);
    }

    private final static int windowWidth = 1200;
    private final static int windowHeight = 800;
    private BorderPane rootLayout;

    private VBox centerVbox;
    private VBox rightVbox;
    private VBox leftVbox;
    private Text mainChartName = new Text();

    private ArrayList<ScrollableChart> charts = new ArrayList<ScrollableChart>();

    private SymbolsListView symbolsListView;

    private ComboBox<String> frequencyComboBox;
    private final String[] frequencies = { "d", "h" };

    private Vector<String> datesVector;
    private Vector<Float> closePriceVector;
    private Vector<Float> openPriceVector;
    private Vector<Float> highPriceVector;
    private Vector<Float> lowPriceVector;
}
