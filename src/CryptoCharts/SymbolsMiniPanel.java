package CryptoCharts;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class SymbolsMiniPanel extends StackPane
{
    public SymbolsMiniPanel(String symbol)
    {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        symbolText = new Text(symbol);
        hbox.getChildren().add(symbolText);
        getChildren().add(hbox);
    }

    private Text symbolText;
}
