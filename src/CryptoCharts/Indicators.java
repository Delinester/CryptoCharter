package CryptoCharts;

import java.util.Vector;

import javax.sound.sampled.Line;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Indicators 
{
    public static ScrollableChart RSI(Vector<Float> closePrices, Vector<String> dates)
    {
        int indicatorWindow = 14;
        ObservableList<String> datesList = FXCollections.observableArrayList(dates);
        CategoryAxis xAxis = new CategoryAxis(datesList);

        ObservableList<Float> indicatorValues = FXCollections.observableArrayList();

        for (int i = 0; i < closePrices.size(); i++)
        {
            int initIdx = i - indicatorWindow - 1;
            float avgGain = 0, avgLoss = 0;
            if (initIdx >= 0)
            {
                for (int j = initIdx + 1; j < i; j++)
                {
                    float deltaPrice = closePrices.get(j) - closePrices.get(j - 1);
                    if (deltaPrice > 0) avgGain += deltaPrice;
                    else avgLoss += Math.abs(deltaPrice);                    
                }
                avgGain /= indicatorWindow;
                avgLoss /= indicatorWindow;
                float RS = avgGain / avgLoss;
                float RSI = 100 - (100 / (1 + RS));

                indicatorValues.add(RSI);
            }            
        }

        NumberAxis yAxis = new NumberAxis("RSI", 0, 100, 20);
        LineChart indicatorChart = new LineChart(xAxis, yAxis);

        XYChart.Series<String, Float> series = new XYChart.Series<String, Float>();
        for (int i = indicatorWindow + 1; i < closePrices.size(); i++)
        {
            String date = dates.get(i);
            float value = indicatorValues.get(i - indicatorWindow - 1);
            XYChart.Data<String, Float> data = new XYChart.Data<String, Float>(date, value);
            data.setNode(new ChartHoverInfo("Date: ", date, "RSI: ", Float.toString(value)));
            series.getData().add(data);
        }

        xAxis.setTickLabelRotation(90);
        yAxis.setSide(Side.RIGHT);
        indicatorChart.setLegendVisible(false);
        indicatorChart.setCreateSymbols(false);
        indicatorChart.getData().add(series);
        indicatorChart.setCursor(Cursor.CROSSHAIR);

        ScrollableChart chart = new ScrollableChart(indicatorChart, closePrices.size());
        return chart;
    }
}
