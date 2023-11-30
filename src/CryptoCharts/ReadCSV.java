package CryptoCharts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

// A class that can read and download csv data
public class ReadCSV {
    public ReadCSV(String filePath, String delim, int skipRow) {
        try {
            scanner = new Scanner(new File(filePath));
            table = new HashMap<String, Vector<String>>();
            int counter = 0;
            String[] columns = null;
            while (scanner.hasNext()) {
                String retrievedStr = scanner.nextLine();
                String[] separatedStr = retrievedStr.split(delim);
                if (counter == skipRow)
                {
                    counter++;
                    continue;
                }
                if (skipRow == 0 ? counter == 1 : counter == 0) {
                    columns = separatedStr;
                    for (String colName : separatedStr)
                        table.put(colName, new Vector<String>());
                    counter++;
                    continue;
                }
                for (int colIdx = 0; colIdx < columns.length; colIdx++) {
                    table.get(columns[colIdx]).add(separatedStr[colIdx]);
                    counter++;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.printf("Couldnt open file %S \n", filePath);
        }
    }

    public static long download(String url, String fileName) throws IOException {
        File file = new File(fileName);
        long lastModified = file.lastModified();
        long currentTime = System.currentTimeMillis();
        if (file.exists() && ((currentTime - lastModified) / 1000.0 / 3600.0 < 24) || file.isDirectory()) return 0;
        try (InputStream in = URI.create(url).toURL().openStream()) {
            if (file.exists()) file.delete();
            return Files.copy(in, Paths.get(fileName));
        }
    }

    public Vector<String> getAllColumnValues(String column) {
        return table.get(column);
    }

    public Vector<Float> getColumnAsFloat(String column)
    {
        Vector<String> stringVals = getAllColumnValues(column);
        int size = stringVals.size();
        Vector<Float> floatVals = new Vector<Float>(size);
        for (int i = 0; i < size; i++) floatVals.add(Float.parseFloat(stringVals.get(i)));
        return floatVals;
    }

    public boolean hasNext() {
        return scanner.hasNext();
    }

    public String getNextLine() {
        if (scanner.hasNext())
            return scanner.next();
        else
            return null;
    }

    private Scanner scanner;
    private HashMap<String, Vector<String>> table;
}
