package CryptoCharts;

import java.util.HashMap;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

// Base class for any indicator
// TODO some indicators require not only the close prices. 
// Suggestion* Refactor the method to accept the whole csv table of data
public abstract class Indicator {
    // Create a configurable chart with the given data 
    public ConfigurableChart getChart(Vector<Float> closePrices, Vector<String> dates, int window) {
        // Retrives the window argument from parameters table
        int indicatorWindow = params.get("Window");
        // Sets the header for the chart
        String chartHeader = indicatorName + " " + Integer.toString(indicatorWindow) + " day";

        // Puts the dates into observable list and creates an axis
        ObservableList<String> datesList = FXCollections.observableArrayList();
        for (int i = dates.size() - window; i < dates.size(); i++)
            datesList.add(dates.get(i));
        CategoryAxis xAxis = new CategoryAxis(datesList);

        // Empty list for indicator values
        ObservableList<Float> indicatorValues = calculate(closePrices, indicatorWindow);
        // Empty list for series data
        XYChart.Series<String, Float> series = new XYChart.Series<String, Float>();

        // Variables to determine the range of possible values on a chart
        float minVal = Float.POSITIVE_INFINITY;
        float maxVal = Float.NEGATIVE_INFINITY;

        // Adds data points to the chart 
        for (int i = indicatorWindow + 1; i < closePrices.size(); i++) {
            if (i < closePrices.size() - window)
                continue;
            String date = dates.get(i);
            float value = indicatorValues.get(i - indicatorWindow - 1);
            if (value > maxVal)
                maxVal = value;
            if (value < minVal)
                minVal = value;
            XYChart.Data<String, Float> data = new XYChart.Data<String, Float>(date, value);
            data.setNode(new ChartHoverInfo("Date: ", date, indicatorName + ": ", Float.toString(value)));
            series.getData().add(data);
        }

        float offset = (maxVal - minVal) / 2;
        float divider = 10;
        NumberAxis yAxis = new NumberAxis(minVal - offset, maxVal + offset, maxVal / divider);

        return new ConfigurableChart(chartHeader, xAxis, yAxis, series, window, MainWindow.getWindowWidth() / 2,
                MainWindow.getWindowHeight() / 4);
    }

    public String[] getParams() {
        String[] paramsArr = new String[params.keySet().size()];
        params.keySet().toArray(paramsArr);
        return paramsArr;
    }
    // Sets the value of specified key in the parameters table
    public void setValue(String key, Integer val)
    {
        params.replace(key, val);
    }

    public Integer getValue(String key)
    {
        return params.get(key);
    }
    // Calculates the indicator values. It is up to a certain indicator to implement the method properly
    public abstract ObservableList<Float> calculate(Vector<Float> closePrices, int indicatorWindow);

    // List of possible indicators
    public static String[] indicators = { "RSI", "MACD", "SMA", "EMA", "WMA" };

    protected HashMap<String, Integer> params = new HashMap<>();
    protected String indicatorName;
}
