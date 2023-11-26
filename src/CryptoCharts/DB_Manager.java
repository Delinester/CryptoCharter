package CryptoCharts;

import java.sql.*;
import java.util.Collections;
import java.util.Vector;

public class DB_Manager implements AutoCloseable {
    public static DB_Manager manager;
    public final static int MAX_CHARACTERS_LENGTH = 20;
    public final static int MIN_CHARACTERS_LENGTH = 4;

    public static DB_Manager getInstance()
    {
        if (manager == null) 
        {
            manager = new DB_Manager();
            manager.initializeConnection();
        }
        return manager;
    }

    public boolean initializeConnection() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?autoReconnect=true&useSSL=false", "root", "Delinester1429");
            System.out.println("Connection to DB: SUCCESS");
        } catch (SQLException e) {
            System.out.println("Coudn't create connection: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean addUser(String userName, String password) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String payload = "INSERT INTO " + USERS_DATA_TABLE_NAME + " (username, password) VALUES (" + "\'" + userName + "\'" + "," + "\'" + password + "\'" + ")";
            System.out.println("UPDATING: " + payload);
            statement.executeUpdate(payload);
            System.out.println("USER INSERTION: SUCCESS");
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't add user: " + e.getMessage());
            return false;
        }
    }

    public boolean doesUserExist(String username)
    {
        Statement statement = null;
        try{
            statement = connection.createStatement();
            String payload = "SELECT * FROM " + USERS_DATA_TABLE_NAME + " WHERE username = BINARY " + "\'" + username + '\'';
            ResultSet result = statement.executeQuery(payload);
            return result.next();
        }
        catch(SQLException e)
        {
            System.out.println("COULDN'T CHECK USER EXISTENCE: " + e.getMessage());
            return false;
        }
    }

    public boolean isLoginSuccessful(String username, String password)
    {
        Statement statement = null;
        try
        {
            statement = connection.createStatement();
            String payload = "SELECT * FROM " + USERS_DATA_TABLE_NAME + " WHERE username = BINARY " + "\'" + username + '\'';
            ResultSet result = statement.executeQuery(payload);       

            boolean status = result.next();
            String usernameInDb = result.getString("username");
            String passwordInDb = result.getString("password");
            return (status && usernameInDb.equals(username) && passwordInDb.equals(password));            
        }
        catch(SQLException e)
        {
            System.out.println("COULDN'T LOG IN: " + e.getMessage());
            return false;
        }
    }

    public Vector<String> getAvailableSymbols()
    {
        Statement statement = null;
        Vector<String> symbols = new Vector<>();
        try
        {
            statement = connection.createStatement();
            String payload = "SELECT * FROM " + SYMBOLS_DATA_TABLE_NAME;
            ResultSet result = statement.executeQuery(payload);

            while (result.next())
            {
                String resultSymbols = result.getString(SYMBOLS_COLUMN);
                symbols.add(resultSymbols);
            }
        }
        catch (SQLException e) 
        {
            System.out.println("SYMBOLS FETCHING ERROR: " + e.getMessage());
        }
        Collections.sort(symbols);
        return symbols;
    }

    public void uploadSymbolsToTable(Vector<String> symbols)
    {
        Statement statement = null;
        Vector<String> existingSymbols = getAvailableSymbols();
        
        for (String symbol : symbols)
        {
            for (String existingSymbol : existingSymbols) { if(existingSymbol == symbol) continue;}
            try
            {
                statement = connection.createStatement();
                String payload = "INSERT " + SYMBOLS_DATA_TABLE_NAME + " (" + SYMBOLS_COLUMN + ") VALUES ('" + symbol + "')";
                statement.executeUpdate(payload);
            }
            catch (SQLException e) 
            {   
                System.out.println("SYMBOLS ADDING ERROR: " + e.getMessage());
            }
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
            System.out.println("CONNECTION CLOSE: SUCCESS");
        } catch (SQLException e) {
            System.out.println("Coudn't close the connection: " + e.getMessage());
        }
    }

    private final String DATABASE_NAME = "cryptocharts";
    private final String USERS_DATA_TABLE_NAME = "users_data";
    private final String SYMBOLS_DATA_TABLE_NAME = "symbols_data";

    private final String SYMBOLS_COLUMN = "symbols_pair";

    private Connection connection;    
    private DB_Manager() {}
}
