package CryptoCharts;

import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.event.*;

public class SymbolsListView extends ListView<Pane>
{
    public SymbolsListView(Vector<String> symbols)
    {
        ObservableList<Pane> panelsList  = FXCollections.observableArrayList();
        for (String symbol : symbols)
        {
            panelsList.add(new SymbolsMiniPanel(symbol));            
        }
        setItems(panelsList);
    }
    
}
