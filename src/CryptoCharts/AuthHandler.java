package CryptoCharts;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;  
import java.util.regex.*;

public class AuthHandler implements EventHandler<MouseEvent>
{
    public AuthHandler(Button loginBtn, Button signUpButton, TextField usernameField, TextField passwordField, Text infoMessage)
    {
        loginBtnRef = loginBtn;
        signUpBtnRef = signUpButton;
        infoMessageRef = infoMessage;
        usernameFieldRef = usernameField;
        passwordFieldRef = passwordField;
    }

    @Override
    public void handle(MouseEvent event)
    {
        Object source = event.getSource();

        if (source == loginBtnRef)
        {
            String username = usernameFieldRef.getText();
            String password = passwordFieldRef.getText();
            if (!isFieldCorrect(username)) 
            {
                infoMessageRef.setText("CHECK USERNAME!\n" + PERMITTED_CHARACTERS_MSG);
                return;
            }
            else if (!isFieldCorrect(password))
            {
                infoMessageRef.setText("CHECK PASSWORD!\n" + PERMITTED_CHARACTERS_MSG);
                return;
            }

            DB_Manager databaseManager = DB_Manager.getInstance();
            System.out.println("ATTEMPTING LOG IN: " + username + " " + password);
            if (databaseManager.isLoginSuccessful(username, password))
                infoMessageRef.setText("Successful Log In");
            else infoMessageRef.setText(INCORRECT_CREDENTIALS_MSG);
        }
        else if (source == signUpBtnRef)
        {
            String username = usernameFieldRef.getText().strip();
            String password = passwordFieldRef.getText();

            if (!isFieldCorrect(username)) 
            {
                infoMessageRef.setText("CHECK USERNAME!" + PERMITTED_CHARACTERS_MSG);
                return;
            }
            else if (!isFieldCorrect(password))
            {
                infoMessageRef.setText("CHECK PASSWORD!" + PERMITTED_CHARACTERS_MSG);
                return;
            }

            DB_Manager databaseManager = DB_Manager.getInstance();
            if (!databaseManager.doesUserExist(username)) 
            {
                databaseManager.addUser(username, password);
                infoMessageRef.setText(SIGNUP_ON_SUCCESS_MSG);
            }
            else
                infoMessageRef.setText(USER_ALREADY_EXISTS_MSG);
        }
    }

    private boolean isFieldCorrect(String field)
    {
        if (field.length() > DB_Manager.MAX_CHARACTERS_LENGTH) return false;
        Pattern forbiddenCharactersPattern = Pattern.compile("[^a-zA-Z0-9\\@\\$\\#\\%\\^\\&\\*\\!]");
        Matcher matcher = forbiddenCharactersPattern.matcher(field);
        return !matcher.find();
    }

    private Button loginBtnRef;
    private Button signUpBtnRef;
    private Text infoMessageRef;
    private TextField usernameFieldRef;
    private TextField passwordFieldRef;

    private final String SIGNUP_ON_SUCCESS_MSG = "Succesfully signed up! Now you can login!";
    private final String USER_ALREADY_EXISTS_MSG = "Cannot sign up! User already exists";
    private final String PERMITTED_CHARACTERS_MSG = "Permitted characters are (a-z A-Z 0-9 !@#$%^&*)";
    private final String INCORRECT_CREDENTIALS_MSG = "Incorrect credentials!";
}
