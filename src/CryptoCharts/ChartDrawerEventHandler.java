package CryptoCharts;

import javafx.event.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ChartDrawerEventHandler implements EventHandler
{
 
     public ChartDrawerEventHandler(MainWindow mainWindow) {
        mainWindowRef = mainWindow;
    }

    public void setWindowField(TextField windowField) {
        windowFieldRef = windowField;
    }

    public void setSymbolsList(ListView<Pane> symbolsList) {
        symbolsListRef = symbolsList;
    }

    public void setFrequencyBox(ComboBox frequencyBox) {
        frequencyComboBoxRef = frequencyBox;
    }

    public void setIndicatorsList(ListView<Pane> indicatorsList)
    {
        indicatorsListRef = indicatorsList;
    }

    public void handle(Event event)
    {
        if (event.getSource() == symbolsListRef || event.getSource() == frequencyComboBoxRef || event.getSource() == windowFieldRef)
        {
            MiniPanel selectedItem = (MiniPanel) symbolsListRef.getSelectionModel().getSelectedItem();
            
            mainWindowRef.constructChart(selectedItem.getText(), 
                frequencyComboBoxRef.getSelectionModel().getSelectedItem(),
                windowFieldRef.getText());
        }
/*
        else if (event.getSource() == indicatorsListRef)
        {
            IndicatorMiniPanel selectedItem = (IndicatorMiniPanel) indicatorsListRef.getSelectionModel().getSelectedItem();
            mainWindowRef.constructIndicatorChart(selectedItem.getIndicator());
        } */
    }

    private TextField windowFieldRef;
    private ComboBox<String> frequencyComboBoxRef;
    private ListView<Pane> symbolsListRef;
    private ListView<Pane> indicatorsListRef;
    private MainWindow mainWindowRef;
}
