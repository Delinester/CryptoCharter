package CryptoCharts;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

// TODO ADD MORE INDICATORS
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
                int initIdx = i - indicatorWindow - 1;
                float avgPrice = 0;
                if (initIdx >= 0) {
                    for (int j = initIdx + 1; j < i; j++) {
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
            indicatorName = "EMA";
        }

        public ObservableList<Float> calculate(Vector<Float> closePrices, int indicatorWindow) {
        ObservableList<Float> indicatorValues = FXCollections.observableArrayList();

        float smoothingFactor = 2;
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


