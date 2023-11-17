package CryptoCharts;

import javafx.scene.chart.LineChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class ScrollableChart extends ScrollPane
{
    public ScrollableChart(LineChart linechart, int window)
    {
        setContent(linechart);
        final int width = viewPortWidth + window * tickWidth;
        setPrefViewportHeight(viewPortHeight);
        setPrefViewportWidth(viewPortWidth);
        linechart.setMinSize(viewPortWidth, viewPortHeight);

        setOnScroll(event -> {
            double zoomValue = event.getDeltaY() / width * zoomingSpeed;
            if (currentZoomFactor < 1 && currentZoomFactor > 0
                    || currentZoomFactor >= 1 && zoomValue < 0
                    || currentZoomFactor <= 0 && zoomValue > 0)
                currentZoomFactor += zoomValue;
            setHvalue(1 - currentZoomFactor);    
            double newWidth = width * currentZoomFactor;
            linechart.setMinWidth(newWidth < width ? newWidth : width);
            
            // TODO Make the price axis fixed!!!
            //double scrollValue = scrollPane.getHvalue();
            //double xPos = -scrollValue * newWidth + viewPortWidth;            
            //System.out.println("Scroll: " + scrollValue + " XPos: " + xPos);
            //yAxis.setTranslateX(xPos);
        });
    }

    
    private final int tickWidth = 14;
    
    final int viewPortWidth = 600;
    final int viewPortHeight = 400;

    private double currentZoomFactor = 1;
    private double zoomingSpeed = 3;
}
