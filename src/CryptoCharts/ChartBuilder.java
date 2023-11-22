package CryptoCharts;

import javafx.scene.Cursor;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class ChartBuilder 
{
    public static ScrollableChart makeChart(Axis x, Axis y, XYChart.Data series, int window)
    {
        LineChart linechart = new LineChart<>(x, y);
        linechart.getData().add(series);

        x.setTickLabelRotation(90);
        linechart.setLegendVisible(false);
        linechart.setCreateSymbols(false);
        linechart.getData().add(series);
        linechart.setCursor(Cursor.CROSSHAIR);

        ScrollableChart scrollableChart = new ScrollableChart(linechart, window);      
        scrollableChart.setSize(600,400);
    }
}
