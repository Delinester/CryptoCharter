package CryptoCharts;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ChartMoveHandler implements EventHandler
{
    public ChartMoveHandler(Button leftBtn, Button rightBtn, Button closeBtn, ConfigurableChart chart)
    {
        leftBtnRef = leftBtn;
        rightBtnRef = rightBtn;
        closeBtnRef = closeBtn;
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

        else if (event.getSource() == closeBtnRef)
        {
            for (Pane p  : parentPanesRef) p.getChildren().remove(chartRef);
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

    public static void setLayouts(Pane ... p)
    {
        parentPanesRef = p;
    }
    
    private static VBox leftMainBoxRef;
    private static VBox centerMainBoxRef;
    private static Pane[] parentPanesRef;

    private Button leftBtnRef;
    private Button rightBtnRef;
    private Button closeBtnRef;
    private ConfigurableChart chartRef;
}
