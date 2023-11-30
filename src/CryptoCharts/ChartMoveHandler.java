package CryptoCharts;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

// Event handler that is responsible for moving charts left and right as well as deleting them
public class ChartMoveHandler implements EventHandler
{
    // Keeping the references of crucial objects
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
        // Move chart to the left if particular button is pressed
        if (event.getSource() == leftBtnRef && chartRef.isOnRight())
        {
            centerMainBoxRef.getChildren().remove(chartRef);
            leftMainBoxRef.getChildren().add(chartRef);
            chartRef.setOnRight(false);
            // Make the chart twice narrower
            chartRef.setChartSize(chartRef.getChartWidth() / 2, chartRef.getChartHeight());
            chartRef.resetChartZoom();
        }
        // Move chart to the right if particular button is pressed
        else if (event.getSource() == rightBtnRef && !chartRef.isOnRight())
        {
            leftMainBoxRef.getChildren().remove(chartRef);
            centerMainBoxRef.getChildren().add(chartRef);
            chartRef.setOnRight(true);
            // Make the chart twice wider
            chartRef.setChartSize(chartRef.getChartWidth() * 2, chartRef.getChartHeight());
            chartRef.resetChartZoom();
        }
        // Delete the chart if close button is pressed
        else if (event.getSource() == closeBtnRef)
        {
            for (Pane p  : parentPanesRef) p.getChildren().remove(chartRef);
        }
    }

    // Methods for initializing references
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
    //
    private static VBox leftMainBoxRef;
    private static VBox centerMainBoxRef;
    private static Pane[] parentPanesRef;

    private Button leftBtnRef;
    private Button rightBtnRef;
    private Button closeBtnRef;
    private ConfigurableChart chartRef;
}
