package CryptoCharts;

import java.awt.Color;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MiniPanel extends StackPane
{
    public MiniPanel(String text)
    {
        hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        mainText = new Text(text);
        hbox.getChildren().add(mainText);

        getChildren().addAll(hbox);        
    }    

    public void setText(String text)
    {
        mainText.setText(text);
    }
    public String getText()
    {
        return mainText.getText();
    }

    protected Text mainText;
    protected HBox hbox = new HBox();
}
