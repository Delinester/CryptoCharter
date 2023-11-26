package CryptoCharts;

import java.util.Vector;

import javafx.geometry.Pos;
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
        setAlignment(Pos.CENTER);
    }
    public ListViewPanel(String name, Vector<String> values)
    {
        this();
        listView = new MyListView(values);
        nameLabel.setText(name);
        getChildren().addAll(nameLabel, searchField, listView);
    }

    public ListViewPanel(String name, String[] values)
    {
        this();
        listView = new MyListView(values);
        nameLabel.setText(name);     
        getChildren().addAll(nameLabel, searchField, listView);
    }

    public MyListView getListView() {return listView;}
    
    private MyListView listView;
    private TextField searchField = new TextField();
    private Label nameLabel = new Label();
}
