package CryptoCharts;
import javafx.scene.*;
import javafx.stage.*;

public class ScenesManager 
{
    public static ScenesManager instance;

    public static ScenesManager getInstance()
    {
        if (instance == null) instance = new ScenesManager();
        return instance;
    }

    public void setMainStage(Stage mainStage) 
    {
        mainStageRef = mainStage;
        openAuthWindow();
    }

    public void openAuthWindow() 
    { 
        mainStageRef.setScene(authWindow);
    }


    private ScenesManager()
    {
        authWindow = new AuthWindow();
    }

    private AuthWindow authWindow;
    private Stage mainStageRef;
    
}
