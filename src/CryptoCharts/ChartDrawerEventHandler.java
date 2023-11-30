package CryptoCharts;

import javafx.event.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

// Event handler for chart drawing
public class ChartDrawerEventHandler implements EventHandler
{
    // Keeping the references to crucial components
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
    //
    public void handle(Event event)
    {
        // Draw the main chart if Symbol is selected OR frequency is changed OR textField value changed
        if (event.getSource() == symbolsListRef || event.getSource() == frequencyComboBoxRef || event.getSource() == windowFieldRef)
        {
            MiniPanel selectedItem = (MiniPanel) symbolsListRef.getSelectionModel().getSelectedItem();
            
            mainWindowRef.constructChart(selectedItem.getText(), 
                frequencyComboBoxRef.getSelectionModel().getSelectedItem(),
                windowFieldRef.getText());
        }
    }

    private TextField windowFieldRef;
    private ComboBox<String> frequencyComboBoxRef;
    private ListView<Pane> symbolsListRef;
    private MainWindow mainWindowRef;
}
