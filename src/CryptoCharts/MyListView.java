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
        values = new String[symbols.size()];
        symbols.copyInto(values);
        for (String symbol : symbols)
        {
            panelsList.add(new MiniPanel(symbol));            
        }
        setItems(panelsList);
        getSelectionModel().select(0);
    }

    public MyListView(String[] symbols)
    {
        ObservableList<Pane> panelsList  = FXCollections.observableArrayList();
        values = symbols;
        for (String symbol : symbols)
        {
            panelsList.add(new MiniPanel(symbol));            
        }
        setItems(panelsList);
        getSelectionModel().select(0);
    }

    public void search(String val)
    {
        ObservableList<Pane> panelsList  = FXCollections.observableArrayList();
        for (String value : values)
        {
            if (value.startsWith(val)) panelsList.add(new MiniPanel(value));            
        }
        setItems(panelsList);
    }

    private String[] values;
    
}
