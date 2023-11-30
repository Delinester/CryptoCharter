package CryptoCharts;

import java.util.HashMap;
import java.util.Vector;

import javax.swing.plaf.ButtonUI;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class IndicatorConfigMenu extends VBox
{
    public IndicatorConfigMenu(Pane parentPane, String ... params_)
    {        
        parentPaneRef = parentPane;
        setAlignment(Pos.CENTER);
        for (String s: params_) params.put(s, 0);
        updateMenu();

        closeBtn.setOnMouseClicked(e -> {
            parentPane.getChildren().remove(this);
        });
        // TODO ADD ability to create N-windowed indicators
        submitBtn.setOnMouseClicked(e ->
        {
            Indicator indicator = ((IndicatorMiniPanel)parentPane).getIndicator();
            String[] parameters = indicator.getParams();
            for (int i = 0; i < parameters.length; i++)
            {
                indicator.setValue(parameters[i], Integer.parseInt(textFields.get(i).getText()));
            }
            mainWindowRef.constructIndicatorChart(indicator);
        });
    }

    public static void setMainWindowRef(MainWindow mainWindow) { mainWindowRef = mainWindow; }

    public void setValue(String key, Integer val)
    {
        params.replace(key, val);
        updateMenu();
    }

    public Integer getValue(String key)
    {
        return params.get(key);
    }

    public void updateMenu()
    {
        if (getChildren() != null)
        {
            getChildren().clear();
            labels.clear();
            textFields.clear();
        }

        for (String key : params.keySet())
        {
            labels.add(new Label(key + ": "));
            textFields.add(new TextField(Integer.toString(params.get(key))));
        }

        for (int i = 0; i < labels.size(); i++)
        {
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER_RIGHT);
            hbox.getChildren().addAll(labels.get(i), textFields.get(i));
            getChildren().add(hbox);
        }

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(submitBtn, closeBtn);
        getChildren().add(hBox);
    }

    public Button getSubmitButton() { return submitBtn; }
    public Button getCloseButton() {return closeBtn;}

    private HashMap<String, Integer> params = new HashMap<>();
    private Vector<Label> labels = new Vector<>();
    private Vector<TextField> textFields = new Vector<>();

    private Button submitBtn = new Button("Submit");
    private Button closeBtn = new Button("Close");

    private static MainWindow mainWindowRef;
    private Pane parentPaneRef;
}
