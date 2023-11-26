package CryptoCharts;

import java.util.Vector;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ListViewPanel extends VBox
{
    public ListViewPanel()
    {
        searchField.setOnAction(event -> 
        {
            listView.search(searchField.getText());
        });
    }
    public ListViewPanel(String name, Vector<String> values)
    {
        listView = new MyListView(values);
        nameLabel = new Label(name);
    }

    public ListViewPanel(String name, String[] values)
    {
        listView = new MyListView(values);
        nameLabel = new Label(name);
    }
    
    private MyListView listView;
    private TextField searchField = new TextField();
    private Label nameLabel;
}
