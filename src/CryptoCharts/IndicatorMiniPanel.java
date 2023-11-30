package CryptoCharts;

import javafx.event.*;

public class IndicatorMiniPanel extends MiniPanel 
{
    IndicatorMiniPanel(String name)
    {
        super(name);

        switch (name)
        {
            case "RSI": indicator = new RSI(); break;
            case "SMA": indicator = new SMA(); break;
            case "EMA": indicator = new EMA(); break;
            case "MACD": indicator = new MACD(); break;
        }

        IndicatorConfigMenu m = new IndicatorConfigMenu(this, indicator.getParams());
        for (String s : indicator.getParams())
        {
            m.setValue(s, indicator.getValue(s));
        }
        setOnMouseClicked(e -> {       
            getChildren().add(m);
        });
    
        
    }

    public Indicator getIndicator() {return indicator;}

    private Indicator indicator;
}
