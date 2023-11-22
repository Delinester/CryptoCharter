package CryptoCharts;

import javafx.event.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ChartDrawerEventHandler implements EventHandler
{
 
     public ChartDrawerEventHandler(MainWindow mainWindow) {
        mainWindowRef = mainWindow;
    }

    public void setWindowField(TextField windowField) {
        windowFieldRef = windowField;
    }

    public void setSymbolsList(SymbolsListView symbolsList) {
        symbolsListRef = symbolsList;
    }

    public void setFrequencyBox(ComboBox frequencyBox) {
        frequencyComboBoxRef = frequencyBox;
    }

    public void handle(Event event)
    {
        if (event.getSource() == symbolsListRef || event.getSource() == frequencyComboBoxRef)
        {
            MiniPanel selectedItem = (MiniPanel) symbolsListRef.getSelectionModel().getSelectedItem();
            mainWindowRef.constructChart(selectedItem.getText(), 
                frequencyComboBoxRef.getSelectionModel().getSelectedItem(),
                windowFieldRef.getText());
        }
    }

    private TextField windowFieldRef;
    private ComboBox<String> frequencyComboBoxRef;
    private SymbolsListView symbolsListRef;
    private MainWindow mainWindowRef;
}
