package CryptoCharts;

public class IndicatorMiniPanel extends MiniPanel 
{
    IndicatorMiniPanel(String name)
    {
        super(name);

        // Depending on the indicator, add it to the panel
        switch (name)
        {
            case "RSI": indicator = new RSI(); break;
            case "SMA": indicator = new SMA(); break;
            case "EMA": indicator = new EMA(); break;
            case "MACD": indicator = new MACD(); break;
            case "WMA": indicator = new WMA(); break;
        }

        IndicatorConfigMenu indicatorConfigMenu = new IndicatorConfigMenu(this, indicator.getParams());
        
        // Set the parameters for config menu
        for (String s : indicator.getParams())
        {
            indicatorConfigMenu.setValue(s, indicator.getValue(s));
        }

        // On click, if there is no config menu already, add the menu
        setOnMouseClicked(e -> {            
            if (!getChildren().contains(indicatorConfigMenu)) getChildren().add(indicatorConfigMenu);
        });       
    }

    public Indicator getIndicator() {return indicator;}

    private Indicator indicator;
}
