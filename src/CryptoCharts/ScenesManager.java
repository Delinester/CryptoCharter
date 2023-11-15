package CryptoCharts;
import javafx.scene.*;
import javafx.stage.*;

public class ScenesManager 
{
    public ScenesManager(Stage mainStage)
    {
        mainStageRef = mainStage;
        authWindow = new AuthWindow();

        mainStageRef.setScene(authWindow);
    }

    private AuthWindow authWindow;
    private Stage mainStageRef;
    
}
