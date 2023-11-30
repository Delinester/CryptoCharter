package CryptoCharts;

import java.util.HashMap;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public abstract class Indicator {
    public ConfigurableChart getChart(Vector<Float> closePrices, Vector<String> dates, int window) {
        int indicatorWindow = params.get("Window");
        String chartHeader = indicatorName + " " + Integer.toString(indicatorWindow) + " day";

        ObservableList<String> datesList = FXCollections.observableArrayList();
        for (int i = dates.size() - window; i < dates.size(); i++)
            datesList.add(dates.get(i));
        CategoryAxis xAxis = new CategoryAxis(datesList);

        ObservableList<Float> indicatorValues = calculate(closePrices, indicatorWindow);

        XYChart.Series<String, Float> series = new XYChart.Series<String, Float>();

        float minSMA = Float.POSITIVE_INFINITY;
        float maxSMA = Float.NEGATIVE_INFINITY;

        for (int i = indicatorWindow + 1; i < closePrices.size(); i++) {
            if (i < closePrices.size() - window)
                continue;
            String date = dates.get(i);
            float value = indicatorValues.get(i - indicatorWindow - 1);
            if (value > maxSMA)
                maxSMA = value;
            if (value < minSMA)
                minSMA = value;
            XYChart.Data<String, Float> data = new XYChart.Data<String, Float>(date, value);
            data.setNode(new ChartHoverInfo("Date: ", date, indicatorName + ": ", Float.toString(value)));
            series.getData().add(data);
        }

        NumberAxis yAxis = new NumberAxis(minSMA - (maxSMA - minSMA) / 2, maxSMA + (maxSMA - minSMA) / 2, maxSMA / 10);

        return new ConfigurableChart(chartHeader, xAxis, yAxis, series, window, MainWindow.getWindowWidth() / 2,
                MainWindow.getWindowHeight() / 4);
    }

    public String[] getParams() {
        String[] paramsArr = new String[params.keySet().size()];
        params.keySet().toArray(paramsArr);
        return paramsArr;
    }

    public void setValue(String key, Integer val)
    {
        params.replace(key, val);
    }

    public Integer getValue(String key)
    {
        return params.get(key);
    }

    public abstract ObservableList<Float> calculate(Vector<Float> closePrices, int indicatorWindow);

    public static String[] indicators = { "RSI", "MACD", "SMA", "EMA" };

    protected HashMap<String, Integer> params = new HashMap<>();
    protected String indicatorName;
}
