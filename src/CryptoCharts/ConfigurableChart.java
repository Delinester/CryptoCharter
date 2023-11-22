package CryptoCharts;

import javafx.geometry.Pos;
import javafx.scene.chart.Axis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ConfigurableChart extends VBox
{
    public ConfigurableChart(String name, Axis x, Axis y, XYChart.Series series, int window, int width, int height)
    {        
        chartName = new Text(name);
        chart = ChartBuilder.makeChart(x, y, series, window, width, height);
        setMaxWidth(width);

        leftMoveBtn = new Button("L");
        rightMoveBtn = new Button("R");

        chartMoveHandler = new ChartMoveHandler(leftMoveBtn, rightMoveBtn, this);
        leftMoveBtn.setOnMouseClicked(chartMoveHandler);
        rightMoveBtn.setOnMouseClicked(chartMoveHandler);

        backgroundColorPicker.getStyleClass().add("button");
        lineColorPicker.getStyleClass().add("button");
        lineColorPicker.setValue(Color.ORANGE);
        
        lineColorPicker.setOnAction((e) -> {
            Color color = lineColorPicker.getValue();
            chart.setStrokeColor(color.toString());
        });

        backgroundColorPicker.setOnAction(e -> {
            Color color = backgroundColorPicker.getValue();
            chart.setBackgroundColor(color.toString());
        });

        configurationComponentsBox.getChildren().addAll(leftMoveBtn, backgroundColorPicker, lineColorPicker, rightMoveBtn);
        configurationComponentsBox.setMaxHeight(lineColorPicker.getPrefHeight());
        
        setAlignment(Pos.CENTER);
        getChildren().addAll(chartName, configurationComponentsBox, chart);
    }

    public boolean isOnRight() {return isOnRight;}
    public void setOnRight(boolean val) {isOnRight = val;}

    public void setChartSize(double width, double height) {chart.setSize(width, height);}
    public double getChartWidth() { return chart.getWidth();};
    public double getChartHeight() {return chart.getHeight();};

    public void resetChartZoom() {chart.resetZoom();}

    private Text chartName;  
    private ScrollableChart chart;
    private HBox configurationComponentsBox = new HBox();  

    private ColorPicker backgroundColorPicker = new ColorPicker();
    private ColorPicker lineColorPicker = new ColorPicker();

    private Button leftMoveBtn;
    private Button rightMoveBtn;
    private boolean isOnRight = true;

    private ChartMoveHandler chartMoveHandler;
}
