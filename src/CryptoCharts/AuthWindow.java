package CryptoCharts;

import javafx.stage.Stage;

import javafx.event.EventHandler;  

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.*;

public class AuthWindow extends Stage
{
    public AuthWindow()
    {
        authHandler = new AuthHandler(loginButton, signUpButton, usernameTextField, passwordTextField, infoMessage);
        GridPane rootLayout = new GridPane();
        rootLayout.setAlignment(Pos.TOP_CENTER);
        rootLayout.setHgap(10);
        rootLayout.setVgap(10);        

        applicationHeader.setFont(Font.font(headerFontName, FontWeight.BOLD, headerFontSize));
        rootLayout.add(applicationHeader, 0,0,2,1);

        usernameTextField.setMaxWidth(maxInputFieldWidth);
        passwordTextField.setMaxWidth(maxInputFieldWidth);        

        infoMessage.setFont(Font.font(infoMessageFontName, FontWeight.NORMAL, headerFontSize / 3));
        //infoMessage.setTextAlignment(TextAlignment.CENTER);          
        infoMessage.setFill(Paint.valueOf("red")) ;
        GridPane.setHalignment(infoMessage, HPos.CENTER);
        
        GridPane.setHalignment(signUpButton, HPos.CENTER);
        GridPane.setHalignment(loginButton, HPos.CENTER);

        loginButton.setOnMouseClicked(authHandler);
        signUpButton.setOnMouseClicked(authHandler);

        //rootLayout.addRow(10, usernameLabel, usernameTextField);
        rootLayout.add(usernameLabel, 0,10,1,1);
        rootLayout.add(usernameTextField, 1,10,1,1);
        rootLayout.add(passwordLabel, 0,11,1,1);
        rootLayout.add(passwordTextField, 1,11,1,1);
        GridPane.setHalignment(usernameLabel, HPos.LEFT);
        GridPane.setHalignment(usernameTextField, HPos.CENTER);        
        GridPane.setHalignment(passwordLabel, HPos.LEFT);
        GridPane.setHalignment(passwordTextField, HPos.CENTER);
        //rootLayout.addRow(11, passwordLabel, passwordTextField);
       // rootLayout.addRow(12, loginButton, signUpButton);
        rootLayout.add(loginButton, 0,12,2,1);
        GridPane.setHalignment(loginButton, HPos.CENTER);
        rootLayout.add(signUpButton, 0,13,2,1);
        GridPane.setHalignment(signUpButton, HPos.CENTER);
        rootLayout.add(infoMessage, 0,14,2,1);
        

        Scene authScene = new Scene(rootLayout, 600, 400);
        this.setTitle("First JavaFX Application");  
        this.setScene(authScene);  
        this.show();  
    }

    private final double maxInputFieldWidth = 150D;
    private final int headerFontSize = 45;
    private final String headerFontName = "Tahoma";
    private String infoMessageFontName = "SegoeUI";

    private Text applicationHeader = new Text("Crypto Charter");
    private Label usernameLabel = new Label("Username: ");
    private Label passwordLabel = new Label("Password: ");
    private TextField usernameTextField = new TextField("User");
    private PasswordField passwordTextField = new PasswordField();

    private Text infoMessage = new Text();

    private Button loginButton = new Button("Login");
    private Button signUpButton = new Button("Sign Up");

    private AuthHandler authHandler;
}
