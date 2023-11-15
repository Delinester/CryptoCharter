package CryptoCharts;

import java.sql.*;

public class DB_Manager implements AutoCloseable {
    public static DB_Manager manager;
    public final static int MAX_CHARACTERS_LENGTH = 20;

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
                    "jdbc:mysql://localhost:3306/users?autoReconnect=true&useSSL=false", "root", "Delinester1429");
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
            String payload = "INSERT INTO DATA (username, password) VALUES (" + "\'" + userName + "\'" + "," + "\'" + password + "\'" + ")";
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
            String payload = "SELECT * FROM DATA WHERE username = BINARY " + "\'" + username + '\'';
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
            String payload = "SELECT * FROM DATA WHERE username = BINARY " + "\'" + username + '\'';
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

    @Override
    public void close() {
        try {
            connection.close();
            System.out.println("CONNECTION CLOSE: SUCCESS");
        } catch (SQLException e) {
            System.out.println("Coudn't close the connection: " + e.getMessage());
        }
    }

    private Connection connection;    
    private DB_Manager() {}
}
