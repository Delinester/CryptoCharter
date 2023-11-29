package CryptoCharts;

import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
// TODO ADD MORE INDICATORS
public class Indicators 
{

    public static ConfigurableChart RSI(Vector<Float> closePrices, Vector<String> dates, int window)
    {
        int indicatorWindow = 14;
        ObservableList<String> datesList = FXCollections.observableArrayList();
        for (int i = dates.size() - window; i < dates.size(); i++) datesList.add(dates.get(i));
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

        XYChart.Series<String, Float> series = new XYChart.Series<String, Float>();
        for (int i = indicatorWindow + 1; i < closePrices.size(); i++)
        {
            if (i < closePrices.size() - window) continue;
            String date = dates.get(i);
            float value = indicatorValues.get(i - indicatorWindow - 1);
            XYChart.Data<String, Float> data = new XYChart.Data<String, Float>(date, value);
            data.setNode(new ChartHoverInfo("Date: ", date, "RSI: ", Float.toString(value)));
            series.getData().add(data);
        }
        
        return new ConfigurableChart("RSI", xAxis, yAxis, series, window, MainWindow.getWindowWidth() / 2, MainWindow.getWindowHeight() / 4);
    }

    public static ConfigurableChart SMA(Vector<Float> closePrices, Vector<String> dates, int window)
    {
        int indicatorWindow = 200;
        String indicatorName = "SMA 200";

        ObservableList<String> datesList = FXCollections.observableArrayList();
        for (int i = dates.size() - window; i < dates.size(); i++) datesList.add(dates.get(i));
        CategoryAxis xAxis = new CategoryAxis(datesList);

        ObservableList<Float> indicatorValues = calculateSMA(closePrices, indicatorWindow);

        XYChart.Series<String, Float> series = new XYChart.Series<String, Float>();

        float minSMA = Float.POSITIVE_INFINITY;
        float maxSMA = Float.NEGATIVE_INFINITY;

        for (int i = indicatorWindow + 1; i < closePrices.size(); i++)
        {
            if (i < closePrices.size() - window) continue;
            String date = dates.get(i);
            float value = indicatorValues.get(i - indicatorWindow - 1);
            if (value > maxSMA) maxSMA = value;
            if (value < minSMA) minSMA = value;
            XYChart.Data<String, Float> data = new XYChart.Data<String, Float>(date, value);
            data.setNode(new ChartHoverInfo("Date: ", date, indicatorName + ": ", Float.toString(value)));
            series.getData().add(data);
        }

        
        NumberAxis yAxis = new NumberAxis(minSMA - (maxSMA - minSMA) / 2, maxSMA + (maxSMA - minSMA) / 2, maxSMA / 10);

        return new ConfigurableChart(indicatorName, xAxis, yAxis, series, window, MainWindow.getWindowWidth() / 2, MainWindow.getWindowHeight() / 4);
    }

    public static ConfigurableChart EMA(Vector<Float> closePrices, Vector<String> dates, int window)
    {
        int indicatorWindow = 12;
        String indicatorName = "EMA 12";

        ObservableList<String> datesList = FXCollections.observableArrayList();
        for (int i = dates.size() - window; i < dates.size(); i++) datesList.add(dates.get(i));
        CategoryAxis xAxis = new CategoryAxis(datesList);

        ObservableList<Float> indicatorValues = calculateEMA(closePrices, indicatorWindow);

        XYChart.Series<String, Float> series = new XYChart.Series<String, Float>();

        float minSMA = Float.POSITIVE_INFINITY;
        float maxSMA = Float.NEGATIVE_INFINITY;

        for (int i = indicatorWindow + 1; i < closePrices.size(); i++)
        {
            if (i < closePrices.size() - window) continue;
            String date = dates.get(i);
            float value = indicatorValues.get(i - indicatorWindow - 1);
            if (value > maxSMA) maxSMA = value;
            if (value < minSMA) minSMA = value;
            XYChart.Data<String, Float> data = new XYChart.Data<String, Float>(date, value);
            data.setNode(new ChartHoverInfo("Date: ", date, indicatorName + ": ", Float.toString(value)));
            series.getData().add(data);
        }

        
        NumberAxis yAxis = new NumberAxis(minSMA - (maxSMA - minSMA) / 2, maxSMA + (maxSMA - minSMA) / 2, maxSMA / 10);

        return new ConfigurableChart(indicatorName, xAxis, yAxis, series, window, MainWindow.getWindowWidth() / 2, MainWindow.getWindowHeight() / 4);
    }

    private static ObservableList<Float> calculateSMA(Vector<Float> closePrices, int indicatorWindow)
    {
        ObservableList<Float> indicatorValues = FXCollections.observableArrayList();

        for (int i = 0; i < closePrices.size(); i++)
        {
            int initIdx = i - indicatorWindow - 1;
            float avgPrice = 0;
            if (initIdx >= 0)
            {
                for (int j = initIdx + 1; j < i; j++)
                {
                    avgPrice += closePrices.get(j);              
                }

                avgPrice /= indicatorWindow;
                indicatorValues.add(avgPrice);
            }        
        }

        return indicatorValues;
    }

    private static ObservableList<Float> calculateEMA(Vector<Float> closePrices, int indicatorWindow)
    {
        ObservableList<Float> indicatorValues = FXCollections.observableArrayList();
        
        float smoothingFactor = 2;
        float emaMultiplier = smoothingFactor / (indicatorWindow + 1);        

        for (int i = 0; i < closePrices.size(); i++)
        {
            int initIdx = i - indicatorWindow ;

            float sma = 0;
            if (initIdx == 0) 
            {
                sma = calculateSMA(closePrices, indicatorWindow).get(0);
                indicatorValues.add(sma);
            }

            if (initIdx > 0)
            {
                
                float ema = (closePrices.get(i) - indicatorValues.getLast()) * emaMultiplier + indicatorValues.getLast() ;  
                indicatorValues.add(ema);            
                
            }        
        }

        return indicatorValues;
    }
}
