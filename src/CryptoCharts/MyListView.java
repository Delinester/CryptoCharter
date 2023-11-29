package CryptoCharts;

import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class MyListView extends ListView<Pane>
{
    public MyListView(Vector<String> symbols)
    {
        ObservableList<Pane> panelsList  = FXCollections.observableArrayList();
        values = new String[symbols.size()];
        symbols.copyInto(values);
        for (String symbol : symbols)
        {
            boolean isIndicator = false;
            for (String ind : Indicator.indicators)
            {
                if (ind == symbol) isIndicator = true;
            }

            if (!isIndicator)
            panelsList.add(new MiniPanel(symbol));    
            else panelsList.add(new IndicatorMiniPanel(symbol));   
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
            boolean isIndicator = false;
            for (String ind : Indicator.indicators)
            {
                if (ind == symbol) isIndicator = true;
            }

            if (!isIndicator)
            panelsList.add(new MiniPanel(symbol));    
            else panelsList.add(new IndicatorMiniPanel(symbol));   
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
