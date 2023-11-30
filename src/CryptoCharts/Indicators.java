package CryptoCharts;

import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
// TODO ADD MORE INDICATORS
// Classes that represent indicators
    class RSI extends Indicator {
        public RSI() {
            params.put("Window", 14);
            indicatorName = "RSI";
        }

        public ObservableList<Float> calculate(Vector<Float> closePrices, int indicatorWindow) {
            ObservableList<Float> indicatorValues = FXCollections.observableArrayList();

            for (int i = 0; i < closePrices.size(); i++) {
                int initIdx = i - indicatorWindow - 1;
                float avgGain = 0, avgLoss = 0;
                if (initIdx >= 0) {
                    for (int j = initIdx + 1; j < i; j++) {
                        float deltaPrice = closePrices.get(j) - closePrices.get(j - 1);
                        if (deltaPrice > 0)
                            avgGain += deltaPrice;
                        else
                            avgLoss += Math.abs(deltaPrice);
                    }
                    avgGain /= indicatorWindow;
                    avgLoss /= indicatorWindow;
                    float RS = avgGain / avgLoss;
                    float RSI = 100 - (100 / (1 + RS));

                    indicatorValues.add(RSI);
                }
            }
            return indicatorValues;
        }
    }

    class SMA extends Indicator {
        public SMA() {
            params.put("Window", 200);
            indicatorName = "SMA";
        }

        public ObservableList<Float> calculate(Vector<Float> closePrices, int indicatorWindow) {
            ObservableList<Float> indicatorValues = FXCollections.observableArrayList();

            for (int i = 0; i < closePrices.size(); i++) {
                int initIdx = i - indicatorWindow;
                float avgPrice = 0;
                if (initIdx >= 0) {
                    for (int j = initIdx; j < i; j++) {
                        avgPrice += closePrices.get(j);
                    }

                    avgPrice /= indicatorWindow;
                    indicatorValues.add(avgPrice);
                }
            }

            return indicatorValues;
        }
    }

    class EMA extends Indicator
    {
        public EMA()
        {
            params.put("Window", 12);
            params.put("Smoothing Factor", 2);
            indicatorName = "EMA";
        }

        public ObservableList<Float> calculate(Vector<Float> closePrices, int indicatorWindow) {
        ObservableList<Float> indicatorValues = FXCollections.observableArrayList();

        float smoothingFactor = params.get("Smoothing Factor");
        float emaMultiplier = smoothingFactor / (indicatorWindow + 1);

        for (int i = 0; i < closePrices.size(); i++) {
            int initIdx = i - indicatorWindow;

            float sma = 0;
            if (initIdx == 0) {
                sma = new SMA().calculate(closePrices, indicatorWindow).get(0);
                indicatorValues.add(sma);
            }

            if (initIdx > 0) {

                float ema = (closePrices.get(i) - indicatorValues.getLast()) * emaMultiplier
                        + indicatorValues.getLast();
                indicatorValues.add(ema);

            }
        }

        return indicatorValues;
    }
    }

    class MACD extends Indicator
    {
        public MACD()
        {
            params.put("Window", 26);
            params.put("EMA 12", 12);
            params.put("EMA 26", 26);
            indicatorName = "MACD";
        }

        public ObservableList<Float> calculate(Vector<Float> closePrices, int indicatorWindow)
        {
        ObservableList<Float> indicatorValues = FXCollections.observableArrayList();

        for (int i = 0; i < closePrices.size(); i++) {
            int initIdx = i - indicatorWindow;

            ObservableList<Float> ema26 = new EMA().calculate(closePrices, params.get("EMA 26"));
            ObservableList<Float> ema12 = new EMA().calculate(closePrices, params.get("EMA 12"));

            if (initIdx >= 0) {
                float macd = ema26.get(initIdx) - ema12.get(initIdx);
                indicatorValues.add(macd);
            }
        }

        return indicatorValues;
    }

    }    

    class WMA extends Indicator
    {
        public WMA()
        {
            params.put("Window", 200);
            indicatorName = "WMA";
        }

        public ObservableList<Float> calculate(Vector<Float> closePrices, int indicatorWindow)
        {
            ObservableList<Float> indicatorValues = FXCollections.observableArrayList();

            for (int i = 0; i < closePrices.size(); i++) {
                int initIdx = i - indicatorWindow ;
                float avgPrice = 0;
                if (initIdx >= 0) {
                    for (int j = initIdx; j < i; j++) {
                        avgPrice += closePrices.get(j) * (indicatorWindow - (j - initIdx + 1));
                    }

                    avgPrice /= (0.5 * (indicatorWindow * (indicatorWindow + 1)));
                    indicatorValues.add(avgPrice);
                }
            }

            return indicatorValues;
        }
    }


