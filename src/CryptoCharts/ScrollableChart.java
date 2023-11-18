package CryptoCharts;

import javafx.geometry.Point2D;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.chart.NumberAxis;

public class ScrollableChart extends ScrollPane
{
    public ScrollableChart(LineChart linechart, int window)
    {
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollBarPolicy.NEVER);
        chart = linechart;
        setContent(linechart);
        final double width = viewPortWidth + window * tickWidth;
        setPrefViewportHeight(viewPortHeight);
        setPrefViewportWidth(viewPortWidth);
        linechart.setMinSize(viewPortWidth, viewPortHeight);

        setOnScroll(event -> {
            //TODO Scale number axis according to the zoom 
            NumberAxis numAxis = (NumberAxis)chart.getYAxis();
            double zoomValue = event.getDeltaY() / width * zoomingSpeed;
            if (currentZoomFactor < 1 && currentZoomFactor > 0
                    || currentZoomFactor >= 1 && zoomValue < 0
                    || currentZoomFactor <= 0 && zoomValue > 0)
                currentZoomFactor += zoomValue;
            setHvalue(1 - currentZoomFactor);    
            double newWidth = width * currentZoomFactor;
            linechart.setMinWidth(newWidth < width ? newWidth : width);        
        
        chart.setOnMousePressed(pressEvent ->
        {
            double x = pressEvent.getScreenX();
            double y = pressEvent.getScreenY();
            dragPosition = new Point2D(x, y);
        });

        chart.setOnMouseDragged(dragEvent ->
        {
            if (dragPosition == null) return;
            double deltaX = dragPosition.getX() - dragEvent.getScreenX(); 
            double deltaY = dragPosition.getY() - dragEvent.getScreenY();
            setHvalue(getHvalue() + deltaX * dragStrength);
            setVvalue(getVvalue() + deltaY * dragStrength);
            dragPosition = new Point2D(dragEvent.getScreenX(), dragEvent.getScreenY());
        });
            // TODO Make the price axis fixed!!!
            //double scrollValue = scrollPane.getHvalue();
            //double xPos = -scrollValue * newWidth + viewPortWidth;            
            //System.out.println("Scroll: " + scrollValue + " XPos: " + xPos);
            //yAxis.setTranslateX(xPos);
        });
    }

    public void setSize(double width, double height)
    {
        viewPortWidth = width;
        viewPortHeight = height;
        setPrefViewportWidth(width);
        setPrefHeight(height);
        chart.setMaxSize(width, height);
    }

    
    private final int tickWidth = 14;
    
    double viewPortWidth = 600;
    double viewPortHeight = 400;

    Point2D dragPosition;

    private double currentZoomFactor = 0.1;
    private double zoomingSpeed = 3;

    private double dragStrength = 0.0003;

    private LineChart chart;
}
