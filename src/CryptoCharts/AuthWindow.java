package CryptoCharts;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.*;

public class AuthWindow extends Scene
{
    public AuthWindow()
    {
        super(new BorderPane(), windowWidth, windowHeight);
        authHandler = new AuthHandler(loginButton, signUpButton, usernameTextField, passwordTextField, infoMessage);
        BorderPane rootLayout = (BorderPane)this.getRoot();
        
        applicationHeader.setFont(Font.font(headerFontName, FontWeight.BOLD, headerFontSize));

        // Header 
        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().add(applicationHeader);
                
        rootLayout.setTop(headerBox);
        //

        usernameTextField.setMaxWidth(maxInputFieldWidth);
        passwordTextField.setMaxWidth(maxInputFieldWidth);        

        infoMessage.setFont(Font.font(infoMessageFontName, FontWeight.NORMAL, headerFontSize / 3));
        infoMessage.setFill(Paint.valueOf("red")) ;

        loginButton.setOnMouseClicked(authHandler);
        signUpButton.setOnMouseClicked(authHandler);

        // HBox for the username label and text field
        HBox usernameBox = new HBox();
        usernameBox.setAlignment(Pos.CENTER);
        usernameBox.setSpacing(10);
        usernameBox.getChildren().addAll(usernameLabel, usernameTextField);

        // HBox for the password label and text field
        HBox passwordBox = new HBox();
        passwordBox.setAlignment(Pos.CENTER);
        passwordBox.setSpacing(10);
        passwordBox.getChildren().addAll(passwordLabel, passwordTextField);

        // VBox for the login and sign up buttons
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(loginButton, signUpButton);

        // VBox for all boxes
        VBox centerBox = new VBox();
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(10);
        centerBox.getChildren().addAll(usernameBox, passwordBox, buttonBox, infoMessage);

        rootLayout.setCenter(centerBox);
    }

    private final static int windowWidth = 600;
    private final static int windowHeight = 400;

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
