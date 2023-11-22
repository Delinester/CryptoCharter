package CryptoCharts;

import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class ChartBuilder 
{
    public static ScrollableChart makeChart(Axis x, Axis y, XYChart.Series series, int window, int width, int height)
    {
        LineChart linechart = new LineChart<>(x, y);
        y.setSide(Side.RIGHT);
        x.setTickLabelRotation(90);
        linechart.setLegendVisible(false);
        linechart.setCreateSymbols(false);
        linechart.getData().add(series);
        linechart.setCursor(Cursor.CROSSHAIR);
        //
        linechart.setHorizontalGridLinesVisible(false);
        linechart.setVerticalGridLinesVisible(false);
        linechart.setHorizontalZeroLineVisible(false);
        linechart.setVerticalZeroLineVisible(false);

        ScrollableChart scrollableChart = new ScrollableChart(linechart, window);      
        scrollableChart.setSize(width,height);
        return scrollableChart;
    }
}
