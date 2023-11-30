package CryptoCharts;

import java.util.Vector;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

// A panel with search field and list view
public class ListViewPanel extends VBox
{
    public ListViewPanel()
    {
        nameLabel.setFont(Font.font(fontName, FontWeight.BOLD, fontSize));
        searchField.setOnAction(event -> 
        {
            listView.search(searchField.getText());
        });
        setAlignment(Pos.CENTER);
        setSpacing(5);
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

    private String fontName = "Tahoma";
    private int fontSize = 12;
}
