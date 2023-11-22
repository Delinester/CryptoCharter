package CryptoCharts.Tools;

import java.io.IOException;
import java.util.Vector;

import CryptoCharts.DB_Manager;
import CryptoCharts.ReadCSV;

public class BinanceSymbolsUploader {
    public static void main(String[] args) {
        DB_Manager.getInstance().initializeConnection();
        ReadCSV symbols  = new ReadCSV("src\\\\CryptoCharts\\tools\\binance_symbols.csv", ",",-1);
        Vector<String> symbolsVector = symbols.getAllColumnValues("Symbol");
        DB_Manager.getInstance().uploadSymbolsToTable(symbolsVector);
    }
}
