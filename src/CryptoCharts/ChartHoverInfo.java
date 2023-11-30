package CryptoCharts;

import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.*;
import javafx.scene.text.*;

import javafx.event.*;
import javafx.geometry.Pos;

// Node that is displayed when mouse hovers over the data point
public class ChartHoverInfo extends StackPane
{
    // Accepts the parameters, for example Date, Closing Price, Low Price or pretty much anything that is present on a data point
    public ChartHoverInfo(String ... args)
    {
        VBox vbox = new VBox();
        vbox.setSpacing(vboxSpacing);
        vbox.setAlignment(Pos.TOP_CENTER);

        backgroundRect = new Rectangle();
        int maxArgLen = 0;
        for (String arg : args) if (arg.length() > maxArgLen) maxArgLen = arg.length();

        backgroundRect.setWidth(rectWidthPerChar * maxArgLen);
        backgroundRect.setHeight((args.length / 2 + 1) * (fontSize + vboxSpacing ));
        backgroundRect.setFill(Paint.valueOf(rectColor));    

        String temp = "";
        for (int i = 0; i < args.length; i++)
        {
            if (i % 2 == 0) temp = args[i];
            else 
            {
                Text newText = getTextOf(temp + args[i]);
                vbox.getChildren().add(newText);
            }
        }

        setAlignment(Pos.TOP_CENTER);
        setPrefSize(hoverableAreaWidth, hoverableAreaHeight);
        setBackground(Background.EMPTY);

        // Draw the info panel when is hovered over
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

        // Close the info panel when hover is done
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                getChildren().removeAll(backgroundRect, vbox);
            }
        });        
    }

    // Creates the Text object with predetermined parameters
    private Text getTextOf(String s)
    {
        Text text = new Text(s);
        text.setFont(Font.font("SegoeUI", FontWeight.NORMAL, fontSize));
        text.setFill(Paint.valueOf(fontColor));
        return text;
    }

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
