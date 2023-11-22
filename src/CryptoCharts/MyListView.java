package CryptoCharts;

import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.event.*;

public class MyListView extends ListView<Pane>
{
    public MyListView(Vector<String> symbols)
    {
        ObservableList<Pane> panelsList  = FXCollections.observableArrayList();
        for (String symbol : symbols)
        {
            panelsList.add(new MiniPanel(symbol));            
        }
        setItems(panelsList);
    }

    public MyListView(String[] symbols)
    {
        ObservableList<Pane> panelsList  = FXCollections.observableArrayList();
        for (String symbol : symbols)
        {
            panelsList.add(new MiniPanel(symbol));            
        }
        setItems(panelsList);
    }
    
}
