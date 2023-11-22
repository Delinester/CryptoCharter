package CryptoCharts;

import javafx.geometry.Pos;
import javafx.scene.chart.Axis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ConfigurableChart extends VBox
{
    public ConfigurableChart(String name, Axis x, Axis y, XYChart.Series series, int window, int width, int height)
    {
        chartName = new Text(name);
        chart = ChartBuilder.makeChart(x, y, series, window, width, height);
        setMaxWidth(width);

        backgroundColorPicker.getStyleClass().add("button");
        lineColorPicker.getStyleClass().add("button");
        configurationComponentsBox.getChildren().addAll(backgroundColorPicker, lineColorPicker);

        
        setAlignment(Pos.CENTER);
        getChildren().addAll(chartName, configurationComponentsBox, chart);
    }

    private Text chartName;  
    private ScrollableChart chart;
    private HBox configurationComponentsBox = new HBox();  

    private ColorPicker backgroundColorPicker = new ColorPicker();;
    private ColorPicker lineColorPicker = new ColorPicker();;
}
