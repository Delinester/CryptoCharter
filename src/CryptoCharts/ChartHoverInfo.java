package CryptoCharts;

import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.event.*;
import javafx.geometry.Pos;

public class ChartHoverInfo extends StackPane
{
    public ChartHoverInfo(String date, float close, float open, float high, float low)
    {
        String closeValue = Float.toString(close);
        String openValue = Float.toString(open);
        String highValue = Float.toString(high);
        String lowValue = Float.toString(low);

        VBox vbox = new VBox();
        vbox.setSpacing(vboxSpacing);
        vbox.setAlignment(Pos.TOP_CENTER);

        backgroundRect = new Rectangle();
        backgroundRect.setWidth(rectWidthPerChar * date.length());
        backgroundRect.setHeight(6 * (fontSize + vboxSpacing ));
        backgroundRect.setFill(Paint.valueOf("grey"));          

        dateText = getTextOf("Date: " + date);
        closeText = getTextOf("Close: " + closeValue);
        openText = getTextOf("Open: " + openValue);
        highText = getTextOf("High: " + highValue);
        lowText = getTextOf("Low: " + lowValue);
        vbox.getChildren().addAll(dateText, closeText, openText, highText, lowText);

        setAlignment(Pos.TOP_CENTER);
        setPrefSize(hoverableAreaWidth, hoverableAreaHeight);
        setBackground(Background.EMPTY);

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                double offsetY = -vbox.getChildren().size() * fontSize * offsetMultiplier;
                vbox.setTranslateY(offsetY);
                backgroundRect.setTranslateY(offsetY);
                getChildren().addAll(backgroundRect, vbox);
                toFront();
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                getChildren().removeAll(backgroundRect, vbox);
            }
        });        
    }

    private Text getTextOf(String s)
    {
        Text text = new Text(s);
        text.setFont(Font.font("SegoeUI", FontWeight.NORMAL, fontSize));
        text.setFill(Paint.valueOf(fontColor));
        return text;
    }

    private Text dateText, closeText, openText, highText, lowText;

    private Rectangle backgroundRect;
    private int rectWidthPerChar = 10;
    private String rectColor = "grey";

    private int hoverableAreaWidth = 5;
    private int hoverableAreaHeight = 20;

    private int fontSize = 10;    
    private String fontColor = "black";

    private final double offsetMultiplier = 2;
    private final int vboxSpacing = 5;
}
