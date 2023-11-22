package CryptoCharts;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ChartMoveHandler implements EventHandler
{
    public ChartMoveHandler(Button leftBtn, Button rightBtn, ConfigurableChart chart)
    {
        leftBtnRef = leftBtn;
        rightBtnRef = rightBtn;
        chartRef = chart;
    }
    @Override
    public void handle(Event event)
    {
        if (event.getSource() == leftBtnRef && chartRef.isOnRight())
        {
            centerMainBoxRef.getChildren().remove(chartRef);
            leftMainBoxRef.getChildren().add(chartRef);
            chartRef.setOnRight(false);
            chartRef.setChartSize(chartRef.getChartWidth() / 2, chartRef.getChartHeight());
            chartRef.resetChartZoom();
        }

        else if (event.getSource() == rightBtnRef && !chartRef.isOnRight())
        {
            leftMainBoxRef.getChildren().remove(chartRef);
            centerMainBoxRef.getChildren().add(chartRef);
            chartRef.setOnRight(true);
            chartRef.setChartSize(chartRef.getChartWidth() * 2, chartRef.getChartHeight());
            chartRef.resetChartZoom();
        }
    }

    public static void setLeftBox(VBox leftBox)
    {
        leftMainBoxRef = leftBox;
    }

    public static void setCenterBox(VBox centerBox)
    {
        centerMainBoxRef = centerBox;
    }
    
    private static VBox leftMainBoxRef;
    private static VBox centerMainBoxRef;

    private Button leftBtnRef;
    private Button rightBtnRef;
    private ConfigurableChart chartRef;
}
